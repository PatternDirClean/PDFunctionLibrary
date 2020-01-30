package fybug.nulll.pdfunctionlibrary.lang;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.nio.file.StandardOpenOption.SYNC;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

/**
 * <h2>文件维护管理.</h2>
 * 使用监听外部文件内容变化的方式管理是否需要关闭程序
 * 启动后调用 {@link #start(Path)} 即可开始使用文件监听来维护程序
 * 如需关闭程序，可使用 {@link #exit(Path)} 来关闭
 * 这有助于使用 shutdown.sh 脚本关闭
 * <p>
 * 请通过修改 {@link #exitListern} 来更改关闭事件，默认为 {@link System#exit(int)}
 * 可在此前释放程序资源
 *
 * @author fybug
 * @version 0.0.1
 * @since lang 0.0.3
 */
public
class FileToManager {
    // 标识
    private static final String EXITMARK = "checkExitMark";
    /** 结束标识触发监听，默认为直接退出 */
    public static Runnable exitListern = () -> System.exit(0);

    public static
    void runofSing(Path path, String... sing) {
        Set<String> set = new HashSet<>(Arrays.asList(sing));

        // 检查标识
        if (set.contains("exit")) {
            // 触发退出
            exit(path);
        } else if (!start(path)) {
            System.exit(0);
        }
    }

    /**
     * 开启文件维护
     *
     * @param path 文件的路径
     *
     * @return 是否成功启动，在已启动的情况下会返回 false
     */
    public static
    boolean start(Path path) {
        // 检查是否已有运行
        if (Files.exists(path)) {
            return false;
        } else {
            // 创建
            crearPid(path);
            watchPid(path);
            return true;
        }
    }

    /** 通过写入关闭标识关闭程序 */
    public static
    void exit(Path path) {
        if (!Files.exists(path))
            return;

        writerExitMark(path);
    }

    /*--------------------------------------------------------------------------------------------*/

    /** 创建维护文件 */
    private static
    void crearPid(Path path) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> path.toFile().delete()));
        try {
            Files.createDirectories(path.getParent());
            Files.createFile(path);
        } catch ( IOException e ) {
            throw new RuntimeException(e);
        }
        // 结束时删除
    }

    /** 触发退出标识 */
    private static
    void writerExitMark(Path path) {
        List<String> l = List.of(EXITMARK);

        // 覆盖写入并强制同步
        try {
            Files.write(path, l, WRITE, TRUNCATE_EXISTING, SYNC);
        } catch ( IOException e ) {
            throw new RuntimeException(e);
        } finally {
            System.exit(0);
        }
    }

    /*--------------------------------------------------------------------------------------------*/

    /** 开启线程监听文件 */
    private static
    void watchPid(Path path) {
        WatchService watchService;
        try {
            watchService = FileSystems.getDefault().newWatchService();
            path.getParent().register(watchService, ENTRY_MODIFY);
        } catch ( IOException e ) {
            throw new RuntimeException(e);
        }

        /* 开启监听线程 */
        new Thread(() -> {
            for ( ; ; ){
                // 获取监听键
                WatchKey key;
                try {
                    key = watchService.take();
                } catch ( InterruptedException e ) {
                    continue;
                }

                // 处理事件
                for ( WatchEvent<?> event : key.pollEvents() )
                    // 处理修改事件
                    if (event.kind() == ENTRY_MODIFY)
                        checkExitMark(path, watchService, key);

                // reset the key
                if (!key.reset())
                    cleanWatch(watchService, key);
            }
        }).start();
        /* // */
    }

    /** 检查是否结束 */
    private static
    void checkExitMark(Path path, WatchService watchService, WatchKey key) {
        List<String> list;
        /* 检查是否是关闭标识 */
        try {
            list = Files.readAllLines(path); // 读取数据

            if (list.size() == 1 && list.get(0).equals(EXITMARK)) {
                cleanWatch(watchService, key);

                // 结束事件
                exitListern.run();
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    /** 解除文件监听 */
    private static
    void cleanWatch(WatchService watchService, WatchKey key) {
        if (key.isValid())
            key.cancel();
        try {
            watchService.close();
        } catch ( IOException e ) {
            // none
        }
    }
}
