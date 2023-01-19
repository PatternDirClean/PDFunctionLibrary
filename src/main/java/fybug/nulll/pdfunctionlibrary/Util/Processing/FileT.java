package fybug.nulll.pdfunctionlibrary.Util.Processing;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * <h2>路径相关工具类.</h2>
 *
 * @author fybug
 * @version 0.0.5
 * @see File
 * @since Processing 0.0.1
 */
@SuppressWarnings( "all" )
public final
class FileT {
    /**
     * <p>路径分隔符.</p>
     *
     * @see File#pathSeparatorChar
     */
    public static final char FILESEPARATOR = File.separatorChar;

    /*--------------------------------------------------------------------------------------------*/

    /**
     * <h2>路径过滤任务类型.</h2>
     * <p>
     * {@link FileT#removeFile(Collection, RemoveTast)} 和 {@link FileT#removeFile(File[], RemoveTast)} 的执行参数
     */
    public
    enum RemoveTast {
        /** 留下文件夹 */
        Dir,
        /** 留下文件 */
        File
    }

    /**
     * 过滤路径组
     *
     * @param f 要过滤的路径列表<br/>
     *          会被过滤过程影响，直接返回该对象
     * @param r 过滤方式，指定的类型将会被删掉
     *
     * @return 返回传入的列表对象, 如果参数错误，或传入的是空对象则会直接返回
     *
     * @see RemoveTast
     */
    @NotNull
    public static
    Collection<File> removeFile(@Nullable Collection<File> f, @NotNull final RemoveTast r) {
        // 检查参数
        if (f == null || f.size() == 0 || r == null)
            return f;

        //* 使用遍历器保证速度和兼容性 *//
        @NotNull Iterator<File> iter = f.iterator();
        // 获取当前数据
        @NotNull File file;

        while( iter.hasNext() ){
            /* 过滤所有数据 */
            file = iter.next();

            // 空对象
            if (file == null)
                iter.remove();
            else
                /* 根据过滤方式进行过滤 */
                switch ( r ) {
                    case Dir:
                        if (!file.isDirectory())
                            iter.remove();// 删除匹配项
                        break;
                    case File:
                        if (!file.isFile())
                            iter.remove();// 删除匹配项
                        break;
                }
        }
        return f;
    }
 // todo 直接在File.list中获取指定类型
    /**
     * 过滤路径组
     *
     * @param f 要过滤的路径组<br/>
     *          不会被过滤过程所影响
     * @param r 过滤方式，指定的类型将会被删掉
     *
     * @return 返回过滤好的的数组, 如果参数错误，或传入的是空数组则会直接返回
     * @see RemoveTast
     * @see #removeFile(Collection<File>,RemoveTast)
     */
    @NotNull
    public static
    File[] removeFile(@Nullable File[] f, @NotNull final RemoveTast r) {
        // 无可过滤或者参数出错
        if (f == null || f.length == 0 || r == null)
            return f;

        //* 要检查的数据，读写量大的时候使用 LinkList 加快速度 *//
        LinkedList<File> arrayList = new LinkedList<>();
        // 加入当前数据
        for ( int i = 0; i < f.length; i++ ){
            arrayList.add(f[i]);
        }

        return removeFile(arrayList,r).toArray(File[]::new);
    }

    /*--------------------------------------------------------------------------------------------*/

    /**
     * 格式化路径
     * <p>
     * <pre>转化方式：
     * {@code "/a/s///" -> "a/s"}
     * {@code "/a/s/" -> "a/s"}
     * {@code "/a/s" -> "a/s"}</pre>
     *
     * @param s 要转化的路径
     *
     * @return 格式化过后的路径
     */
    @NotNull
    public static
    String integrationPath(@Nullable String data) {
        // 无可格式化
        if (data == null)
            data = "";
        // 字符串为空
        if ((data = data.trim()).isEmpty())
            return "";

        @NotNull //* 转成字符缓存区，减少过滤的内存开销 *//
                StringBuilder stringBuffer = new StringBuilder(data);
        // 要截取的位置
        int mark = 0;
        // 当前检查的字符
        char check;

        for ( int i = 0, length = stringBuffer.length(); i < length; i++ ){
            /* 从开头检查 */
            check = stringBuffer.charAt(i);
            if (check == FILESEPARATOR || check < '!') {
                // 过滤开头的路径分隔符和不可显示的字符
                continue;
            }
            // 标记当前位置
            mark = i;
            break;
        }

        // 删除开头到标记位置的部分
        stringBuffer.delete(0, mark);
        for ( int i = stringBuffer.length(); i > 0; i-- ){
            /* 从结尾检查 */
            //* 当前检查的字符 *//
            check = stringBuffer.charAt(i - 1);
            if (check == FILESEPARATOR || check < '!') {
                // 过滤开头的路径分隔符和不可显示的字符
                continue;
            }
            mark = i;// 标记当前位置
            break;
        }

        // 删除从结尾到标记位置的部分
        stringBuffer.delete(mark, stringBuffer.length());
        @NotNull String out = stringBuffer.toString();
        // 释放资源
        stringBuffer.delete(0, stringBuffer.length());
        return out;
    }

    /**
     * 检查一个路径是否是另一个路径的父路径
     *
     * @param dad 可能是父路径的路径
     * @param son 可能是子路径的路径
     *
     * @return 是否成立
     *
     * @see #integrationPath(File)
     */
    public static
    boolean isIn(@NotNull File dad, @NotNull File son) {
        String data = integrationPath(dad.toString()), sons = integrationPath(son.toString());
        int dadlenght = data.length();
        // 提取子路径的前端与父路径对比
        if (sons.length() >= dadlenght && sons.substring(0, dadlenght).equals(data))
            return true;
        return false;
    }

    /**
     * 检查是否是空路径
     * <p>
     * 会检查是否是空文件或空文件夹<br/>
     * 如果参数为空或路径为虚路径都会返回{@code true}
     *
     * @param file 要检查的路径
     *
     * @return 是否为空
     */
    public static
    boolean isEmptyPath(@Nullable File file) {
        if (file == null)
            return true;
        if (file.isDirectory()) {
            @Nullable File[] fs = file.listFiles();
            return fs == null || fs.length == 0;
        } else if (file.isFile()) {
            return file.length() == 0;
        } else
            return true;
    }

    /*--------------------------------------------------------------------------------------------*/

    /**
     * 保证目录的存在
     *
     * @param path 目录路径
     *
     * @return 成功后的路径
     *
     * @throws IOException 发生 IO 错误
     * @since FileT 0.0.5
     */
    public static
    void assuranceDir(@NotNull Path path) throws IOException {
        if (Files.exists(path)) {
            if (!Files.isDirectory(path))
                throw new IOException("目录路径：" + path.toAbsolutePath() + " 被文件占用");
        } else {
            Files.createDirectories(path);
        }
    }

    /**
     * 保证文件的存在
     *
     * @param path     目录路径
     * @param filename 文件名
     *
     * @return 成功后的路径
     *
     * @throws IOException 发生 IO 错误
     * @since FileT 0.0.5
     */
    public static
    Path assuranceFile(@NotNull Path path, @NotNull String filename) throws IOException {
        assuranceDir(path);

        /* 检查文件是否存在 */
        Path filepath = path.resolve(filename);
        if (Files.exists(filepath)) {
            if (Files.isDirectory(filepath))
                throw new IOException("文件路径：" + filepath.toAbsolutePath() + " 被目录占用");
        } else {
            Files.createFile(filepath);
        }

        return filepath;
    }

    /**
     * 分离文件拓展名
     * <p>
     * 等于{@code getsuffix(String, false);}
     *
     * @param filename 文件名
     *
     * @return 一个长度为2的数组，内容为拆分后的文件名和后缀名
     */
    public static
    String[] getsuffix(String filename) { return getsuffix(filename, false);}

    /**
     * 分离文件拓展名
     *
     * @param filename 文件名
     * @param getLast  是否从后往前查找
     *
     * @return 一个长度为2的数组，内容为拆分后的文件名和后缀名
     */
    public static
    String[] getsuffix(String filename, boolean getLast) {
        int index;
        if (getLast)
            index = filename.lastIndexOf('.');
        else
            index = filename.indexOf('.');

        if (index == 0 || index == -1 || index == filename.length() - 1)
            return new String[]{filename, ""};
        return new String[]{filename.substring(0, index), filename.substring(index + 1)};
    }
}
