package fybug.nulll.pdfunctionlibrary.Util.Processing;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
/**
 * <h2>路径相关算法类.</h2>
 *
 * @author fybug
 * @version 0.0.4
 * @see File
 * @since PDF 1.2
 */
@SuppressWarnings( "all" )
final public
class FileTOOL {
    /** {@Hide} */
    @Deprecated
    private
    FileTOOL() {}

    /**
     * <p>路径分隔符.</p>
     *
     * @see File#pathSeparatorChar
     */
    public static final char FILESEPARATOR = File.separatorChar;

    /**
     * <h2>路径过滤任务类型.</h2>
     * {@link FileTOOL#removeFile(Collection, RemoveTast)} 和
     * {@link FileTOOL#removeFile(File[], RemoveTast)}的执行参数
     */
    public
    enum RemoveTast {
        /** <p>留下文件夹.</p> */
        Dir,
        /** <p>留下文件.</p> */
        File}

    /**
     * <p>过滤路径组.</p>
     *
     * @param f 要过滤的路径组<br/>
     *          会被过滤过程影响，直接返回该对象
     * @param r 过滤方式
     *
     * @return 返回过滤的对象, 如果参数错误，或传入的是空数组则会直接返回
     *
     * @see RemoveTast
     */
    @NotNull
    public static
    Collection<File> removeFile(final @Nullable Collection<File> f, final @NotNull RemoveTast r) {
        // 检查参数
        if (f == null || f.size() == 0 || r == null)
            return f;

        @NotNull //* 使用遍历器保证速度和兼容性 *//
                Iterator<File> iter = f.iterator();
        @NotNull File file; // 获取当前数据

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

    /**
     * <p>过滤路径组.</p>
     *
     * @param f 要过滤的路径组<br/>
     *          不会被过滤过程所影响
     * @param r 过滤方式
     *
     * @see RemoveTast
     */
    @NotNull
    public static
    File[] removeFile(final @Nullable File[] f, final @NotNull RemoveTast r) {
        if (f == null || f.length == 0 || r == null)
            /* 无可过滤或者参数出错 */
            return f;
        @NotNull // 读取量大的时候使用 ArrayList 加快速度
                ArrayList<File> arrayList = new ArrayList(Arrays.asList(f)); // 要检查的数据

        /* 遍历所有输入 */
        for ( int i = 0, length = arrayList.size(); i < length; length = arrayList.size() ){
            if (arrayList.get(i) == null) {
                arrayList.remove(i);
                if (i + 1 == length)
                    break;
            }

            /* 根据参数进行过滤 */
            switch ( r ) {
                case Dir:
                    if (!arrayList.get(i).isDirectory()) {
                        arrayList.remove(i);
                        continue;
                    }
                    break;
                case File:
                    if (!arrayList.get(i).isFile()) {
                        arrayList.remove(i);
                        continue;
                    }
                    break;
            }
            i++;
        }
        @NotNull //* 生成数组，准备返回 *//
                File[] out = new File[arrayList.size()];
        return arrayList.toArray(out);
    }

    /**
     * <p>格式化路径</p>
     * <pre>转化方式：
     * {@code "/a/s///" -> "a/s"}
     * {@code "/a/s/" -> "a/s"}
     * {@code "/a/s" -> "a/s"}
     * </pre>
     *
     * @param s 要转化的路径
     *
     * @return 格式化过后的路径
     */
    @NotNull
    public static
    String integrationPath(@Nullable String data) {
        /* 无可格式化 */
        if (data == null)
            data = "";
        /* 字符串为空 */
        if ((data = data.trim()).isEmpty())
            return "";

        @NotNull //* 转成字符缓存区，减少过滤的内存开销 *//
                StringBuilder stringBuffer = new StringBuilder(data);
        int mark = 0;// 要截取的位置
        char check; // 当前检查的字符

        for ( int i = 0, length = stringBuffer.length(); i < length; i++ ){
            /* 从开头检查 */
            check = stringBuffer.charAt(i);
            if (check == FILESEPARATOR || check < '!') {
                /* 过滤开头的路径分隔符和不可显示的字符 */
                continue;
            }
            mark = i;// 标记当前位置
            break;
        }

        // 删除开头到标记位置的部分
        stringBuffer.delete(0, mark);
        for ( int i = stringBuffer.length(); i > 0; i-- ){
            /* 从结尾检查 */
            check = stringBuffer.charAt(i - 1);// 当前检查的字符
            if (check == FILESEPARATOR || check < '!') {
                /* 过滤开头的路径分隔符和不可显示的字符 */
                continue;
            }
            mark = i;// 标记当前位置
            break;
        }

        // 删除从结尾到标记位置的部分
        stringBuffer.delete(mark, stringBuffer.length());
        @NotNull String out = stringBuffer.toString();
        /* 释放资源 */
        stringBuffer.delete(0, stringBuffer.length());
        return out;
    }

    /**
     * <p>检查一个路径是否是另一个路径的父路径.</p>
     *
     * @param dad 可能是父路径的路径
     * @param son 可能是子路径的路径
     *
     * @return 是否成立
     *
     * @see #integrationPath(File)
     */
    public static
    boolean isIn(@NotNull final File dad, @NotNull final File son) {
        @NotNull String data = integrationPath(dad.toString()), sons = integrationPath(
                son.toString());
        int dadlenght = data.length();
        if (sons.length() >= dadlenght && sons.substring(0, dadlenght).equals(data))
            return true;
        return false;
    }

    /**
     * <p>检查是否是空路径.</p>
     * <pre>
     * 会检查是否是空文件或空文件夹
     * 如果参数为空或路径为虚路径都会返回{@code True}
     * </pre>
     *
     * @param file 要检查的路径
     *
     * @return 是否为空
     */
    public static
    boolean isEmptyPath(@Nullable final File file) {
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
}
