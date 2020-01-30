package fybug.nulll.pdfunctionlibrary.expandwith;
import org.jetbrains.annotations.NotNull;

import java.io.Closeable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <h2>异步任务驱动.</h2>
 *
 * @author fybug
 * @version 0.0.1
 * @since lang 0.0.3
 */
public final
class AsnyDirte implements Closeable {
    /** 全局驱动 */
    @NotNull public static final AsnyDirte GLODAL = new AsnyDirte();

    /** 线程池. */
    private volatile ExecutorService pool;

    public
    AsnyDirte() {this(Executors.newCachedThreadPool());}

    public
    AsnyDirte(@NotNull final ExecutorService service) {pool = service;}

    public synchronized
    void subim(Runnable runnable) {
        if (pool == null)
            throw new Error("is Close");
        pool.submit(runnable);
    }

    public
    void close() {
        if (this == GLODAL)
            return;

        pool.shutdown();
        pool = null;
    }
}
