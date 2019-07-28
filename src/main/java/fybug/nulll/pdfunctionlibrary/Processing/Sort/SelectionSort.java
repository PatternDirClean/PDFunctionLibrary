package fybug.nulll.pdfunctionlibrary.Processing.Sort;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fybug.nulll.pdfunctionlibrary.Processing.Sort.SortMode.FileMode;
import fybug.nulll.pdfunctionlibrary.Processing.Sort.SortMode.Mode;
import fybug.nulll.pdfunctionlibrary.Util.Processing.ListTOOL;
/**
 * <h2>选择排序.</h2>
 * <p>用于对各种数据进行排序，排序方式为选择排序</p>
 *
 * @author fybug
 * @version 0.0.6
 * @see SortMode
 * @since PDF 1.2
 */
@SuppressWarnings( "all" )
public final
class SelectionSort {
    /** {@Hide} */
    @Deprecated
    private
    SelectionSort() {}

    /*
     * Int
     */
    /*
     * Array
     */

    /**
     * <p>排序整数数组.</p>
     * <pre>
     * 会对数组中内容按指定的方式进行排序
     * 不会影响传入的数组
     * </pre>
     *
     * @param is   要排序的整数数组
     * @param sort 排序方式
     *
     * @return 必定不为空
     *
     * @since PDF 1.3 expander 2
     */
    @NotNull
    public static
    int[] sortInt(@Nullable final int[] is, @NotNull final Mode sort) {
        if (is == null || is.length == 0)
            return new int[0];
        else if (is.length == 1)
            return is.clone();

        @NotNull int[] array = is.clone();
        int j, z, // 要交换的位置
                tmp; // 当前符合标准的变量

        switch ( sort ) {
            case DOWN:
                for ( int i = 0, length = array.length - 1; i < length; i++ ){
                    tmp = array[i];
                    z = i;

                    for ( j = i + 1; j < array.length; j++ ){
                        if (array[j] > tmp) {
                            z = j;
                            tmp = array[j];
                        }
                    }

                    // 交换
                    tmp = array[i];
                    array[i] = array[z];
                    array[z] = tmp;
                }
                break;
            case UP:
                for ( int i = 0, length = array.length - 1; i < length; i++ ){
                    tmp = array[i];
                    z = i;

                    for ( j = i + 1; j < array.length; j++ ){
                        if (array[j] < tmp) {
                            z = j;
                            tmp = array[j];
                        }
                    }

                    // 交换
                    tmp = array[i];
                    array[i] = array[z];
                    array[z] = tmp;
                }
                break;
        }

        return array;
    }

    /*
     * Long
     */
    /*
     * Array
     */

    /**
     * <p>排序长整数数组.</p>
     * <pre>
     * 会对数组中内容按指定的方式进行排序
     * 不会影响传入的数组
     * </pre>
     *
     * @param is   要排序的长整数数组
     * @param sort 排序方式
     *
     * @return 必定不为空
     *
     * @since PDF 1.3 expander 2
     */
    @NotNull
    public static
    long[] sortLong(@Nullable final long[] is, @NotNull final Mode sort) {
        if (is == null || is.length == 0)
            return new long[0];
        else if (is.length == 1)
            return is.clone();

        @NotNull long[] array = is.clone();
        int j, z; // 要交换的位置
        long tmp; // 当前符合标准的变量

        switch ( sort ) {
            case DOWN:
                for ( int i = 0, length = array.length - 1; i < length; i++ ){
                    tmp = array[i];
                    z = i;

                    for ( j = i + 1; j < array.length; j++ ){
                        if (array[j] > tmp) {
                            z = j;
                            tmp = array[j];
                        }
                    }

                    // 交换
                    tmp = array[i];
                    array[i] = array[z];
                    array[z] = tmp;
                }
                break;
            case UP:
                for ( int i = 0, length = array.length - 1; i < length; i++ ){
                    tmp = array[i];
                    z = i;

                    for ( j = i + 1; j < array.length; j++ ){
                        if (array[j] < tmp) {
                            z = j;
                            tmp = array[j];
                        }
                    }

                    // 交换
                    tmp = array[i];
                    array[i] = array[z];
                    array[z] = tmp;
                }
                break;
        }

        return array;
    }

    /*
     * Float
     */
    /*
     * Array
     */

    /**
     * <p>排序单浮点数组.</p>
     * <pre>
     * 会对数组中内容按指定的方式进行排序
     * 不会影响传入的数组
     * </pre>
     *
     * @param is   要排序的数组
     * @param sort 排序方式
     *
     * @return 必定不为空
     *
     * @since PDF 1.3 expander 2
     */
    @NotNull
    public static
    float[] sortFloat(@Nullable final float[] is, @NotNull final Mode sort) {
        if (is == null || is.length == 0)
            return new float[0];
        else if (is.length == 1)
            return is.clone();

        @NotNull float[] array = is.clone();
        int j, z; // 要交换的位置
        float tmp; // 当前符合标准的变量

        switch ( sort ) {
            case DOWN:
                for ( int i = 0, length = array.length - 1; i < length; i++ ){
                    tmp = array[i];
                    z = i;

                    for ( j = i + 1; j < array.length; j++ ){
                        if (array[j] > tmp) {
                            z = j;
                            tmp = array[j];
                        }
                    }

                    // 交换
                    tmp = array[i];
                    array[i] = array[z];
                    array[z] = tmp;
                }
                break;
            case UP:
                for ( int i = 0, length = array.length - 1; i < length; i++ ){
                    tmp = array[i];
                    z = i;

                    for ( j = i + 1; j < array.length; j++ ){
                        if (array[j] < tmp) {
                            z = j;
                            tmp = array[j];
                        }
                    }

                    // 交换
                    tmp = array[i];
                    array[i] = array[z];
                    array[z] = tmp;
                }
                break;
        }

        return array;
    }

    /*
     * Float
     */
    /*
     * Array
     */

    /**
     * <p>排序双浮点数组.</p>
     * <pre>
     * 会对数组中内容按指定的方式进行排序
     * 不会影响传入的数组
     * </pre>
     *
     * @param is   要排序的数组
     * @param sort 排序方式
     *
     * @return 必定不为空
     *
     * @since PDF 1.3 expander 2
     */
    @NotNull
    public static
    double[] sortDouble(@Nullable final double[] is, @NotNull final Mode sort) {
        if (is == null || is.length == 0)
            return new double[0];
        else if (is.length == 1)
            return is.clone();

        @NotNull double[] array = is.clone();
        int j, z; // 要交换的位置
        double tmp; // 当前符合标准的变量

        switch ( sort ) {
            case DOWN:
                for ( int i = 0, length = array.length - 1; i < length; i++ ){
                    tmp = array[i];
                    z = i;

                    for ( j = i + 1; j < array.length; j++ ){
                        if (array[j] > tmp) {
                            z = j;
                            tmp = array[j];
                        }
                    }

                    // 交换
                    tmp = array[i];
                    array[i] = array[z];
                    array[z] = tmp;
                }
                break;
            case UP:
                for ( int i = 0, length = array.length - 1; i < length; i++ ){
                    tmp = array[i];
                    z = i;

                    for ( j = i + 1; j < array.length; j++ ){
                        if (array[j] < tmp) {
                            z = j;
                            tmp = array[j];
                        }
                    }

                    // 交换
                    tmp = array[i];
                    array[i] = array[z];
                    array[z] = tmp;
                }
                break;
        }

        return array;
    }

    /*
     * String
     */
    /*
     * List
     */

    /**
     * <p>对字符集合进行排序.</p>
     * <p>排序不会影响传入的对象.</p>
     * <pre>
     * 排序方式：
     * {@link #DOWN} ： char较小的在上面
     * {@link #UP} ： char较大的在上面
     * {@code null} 对象会被放到最后面
     * </pre>
     *
     * @param a    即将进行排序的字符集合
     * @param sort 排序方式
     * @param <T>  必须是字符集合的子类
     *
     * @return 已排序的字符集合
     *
     * @see CharSequence
     */
    @NotNull
    public static
    <T extends CharSequence> List<T> sortString(@Nullable final List<T> a, @NotNull final Mode sort)
    {
        if (a == null || a.size() == 0)
            return new ArrayList<>();
        else if (a.size() == 1) {
            return a instanceof ArrayList ? (List<T>) ((ArrayList<T>) a).clone()
                    : new ArrayList() {{
                        add(a.get(0));
                    }};
        }
        @Nullable final List<T> arrayList =
                a instanceof ArrayList ? (List<T>) ((ArrayList<T>) a).clone() : new ArrayList() {
                    {
                        for ( T t : a )
                            add(t);
                    }
                };

        // [0] 当前对比字符串索引
        // [1] 当前被对比字符串索引
        @Nullable final char[] cs = getSortMode(sort);
        if (cs == null)
            return arrayList;

        int dataSize = arrayList.size();// 数据长度
        @NotNull final char[] chars = {0, 0};// 字符暂存区
        int index; // 要与当前数据对调的id
        int nowLeight; // 对比过的字符长度
        @NotNull T s1; // 当前数据
        @NotNull T s2; // 当前对比数据
        // 字符长度
        int i1;
        int i2;

        int stringLenght; // 要对比的长度

        /* 遍历所有内容 */
        for ( int i = 0; i < dataSize - 1; ){
            index = i;
            if (arrayList.get(i) == null) {
                arrayList.set(i, arrayList.get(dataSize - 1));
                arrayList.set(dataSize - 1, null);
                dataSize--;
                continue;
            }

            /* 当前数据与其他数据进行对比 */
            for ( int s = i + 1; s < dataSize; ){
                if ((s2 = arrayList.get(s)) == null) {
                    arrayList.set(s, arrayList.get(dataSize - 1));
                    arrayList.set(dataSize - 1, null);
                    dataSize--;
                    continue;
                }

                s1 = arrayList.get(index);
                nowLeight = 0;
                i1 = s1.length();
                i2 = s2.length();
                if (i1 <= i2)
                    stringLenght = i1;
                else
                    stringLenght = i2;

                /* 逐字符对比当前数据 */
                for ( int is = 0; is < stringLenght; is++ ){
                    chars[0] = s1.charAt(is);
                    chars[1] = s2.charAt(is);

                    if (chars[cs[0]] == chars[cs[1]]) {
                        /* 字符相同，跳过 */
                        nowLeight = nowLeight + 1;
                    } else if (chars[cs[0]] < chars[cs[1]]) {
                        /* 当前顺序正确 */
                        break;
                    } else {
                        /* 需要对调 */
                        index = s;
                        break;
                    }
                }

                /* 是否所有部分都相同 */
                index = checkIndex(sort, index, nowLeight, i1, i2, stringLenght, s);
                s++;
            }
            // 获取当前被对比数据
            s1 = arrayList.get(i);

            /* 与结果对调 */
            arrayList.set(i, arrayList.get(index));
            arrayList.set(index, s1);
            i++;
        }
        return arrayList;
    }

    /*
     * Array
     */

    /**
     * <p>对文本数组进行排序.</p>
     * <p>排序不会影响传入的对象.</p>
     * <pre>
     * 排序方式：
     * {@link #DOWN} ： char较小的在上面
     * {@link #UP} ： char较大的在上面
     * {@code null} 对象会被放到最后面
     * </pre>
     *
     * @param a    要排序的文本数组
     * @param sort 排序方式
     *
     * @return 返回排序后的文本
     */
    @NotNull
    public static
    String[] sortString(@Nullable final String[] a, @NotNull final Mode sort) {
        if (a == null || a.length == 0)
            return new String[0];
        else if (a.length == 1)
            return a.clone();

        @NotNull String[] arrayList = a.clone();

        // [0] 当前对比字符串索引
        // [1] 当前被对比字符串索引
        @Nullable final char[] cs = getSortMode(sort);
        if (cs == null)
            return arrayList;

        int dataSize = arrayList.length;// 可对比数据长度
        @NotNull final char[] chars = {0, 0};// 字符暂存区
        int index; // 要与当前数据对调的id
        int nowLeight; // 对比过的字符长度
        @NotNull String s1; // 当前数据
        @NotNull String s2; // 当前对比数据
        // 字符长度
        int i1;
        int i2;
        int stringLenght; // 要对比的长度

        /* 遍历所有内容 */
        for ( int i = 0; i < dataSize - 1; i++ ){
            index = i;
            if (arrayList[i] == null) {
                arrayList[i] = arrayList[dataSize - 1];
                arrayList[dataSize - 1] = null;
                dataSize--;
            }

            /* 当前数据与其他数据进行对比 */
            for ( int s = i + 1; s < dataSize; s++ ){
                if (arrayList[s] == null) {
                    arrayList[s] = arrayList[dataSize - 1];
                    arrayList[dataSize - 1] = null;
                    dataSize--;
                }

                nowLeight = 0;
                s1 = arrayList[index];
                s2 = arrayList[s];
                i1 = s1.length();
                i2 = s2.length();
                if (i1 <= i2)
                    stringLenght = i1;
                else
                    stringLenght = i2;

                /* 逐字符对比当前数据 */
                for ( int is = 0; is < stringLenght; is++ ){
                    chars[0] = s1.charAt(is);
                    chars[1] = s2.charAt(is);

                    if (chars[cs[0]] == chars[cs[1]]) {
                        /* 字符相同，跳过 */
                        nowLeight = nowLeight + 1;
                    } else if (chars[cs[0]] < chars[cs[1]]) {
                        /* 当前顺序正确 */
                        break;
                    } else {
                        /* 需要对调 */
                        index = s;
                        break;
                    }
                }

                /* 是否所有部分都相同 */
                index = checkIndex(sort, index, nowLeight, i1, i2, stringLenght, s);
            }
            // 获取当前被对比数据
            s1 = arrayList[i];

            /* 与结果对调 */
            arrayList[i] = arrayList[index];
            arrayList[index] = s1;
        }
        return arrayList;
    }

    /*
     * Help
     */

    /**
     * <p>解析排序规则.</p>
     *
     * @param sort 排序规则
     *
     * @return 错误规则返回 {@code null}
     */
    @Nullable
    private static
    char[] getSortMode(@NotNull final Mode sort) {
        @NotNull char[] cs = new char[]{0, 0};
        switch ( sort ) {
            case DOWN:
                cs[0] = 1;
                break;
            case UP:
                cs[1] = 1;
                break;
            default:
                /* 规则错误 */
                return null;
        }
        return cs;
    }

    /**
     * <p>检查是否所有数据都相同.</p>
     * <pre>
     * 检查是否以检查到最大可检查的长度
     * 如果是，则根据排序方式 <b>按照数据长度</b> 进行额外排序
     * </pre>
     *
     * @param sort         排序方式
     * @param index        当前标记位置
     * @param nowLeight    当前解析的长度
     * @param i1           第一个字符串的长度
     * @param i2           第二个字符串的长度
     * @param stringLenght 最大对比的长度
     * @param s            当前检查位置
     *
     * @return 检查后应使用的位置
     */
    private static
    int checkIndex(@NotNull final Mode sort, int index, final int nowLeight, final int i1,
            final int i2, final int stringLenght, final int s)
    {
        // 检查是否已对比完成
        if (nowLeight == stringLenght) {
            // 按照排序方式进行额外排序
            switch ( sort ) {
                case UP:
                    if (i1 <= i2)
                        break;
                case DOWN:
                    if (i2 <= i1)
                        break;
                    index = s;
            }
        }
        return index;
    }

    /*
     * File
     */
    /*
     * Array
     */

    /**
     * <p>对文件列表进行排序.</p>
     *
     * <pre>
     * 排序方式：
     * {@link #sortString(String[], int)}
     * </pre>
     *
     * <pre>
     * 可选模式：
     * {@link #MODE_DIR} ： 文件夹置顶
     * {@link #MODE_FILE} ： 文件置顶
     * {@link #MODE_DEFAULT} ： 无视类型
     * </pre>
     *
     * @param f    即将进行排序的文件表
     * @param mode 文件排序方式
     * @param sort 排序方式
     *
     * @return 返回被排序好的文件列表
     */
    @NotNull
    public static
    File[] sortFile(@Nullable final File[] f, @NotNull final FileMode mode,
            @NotNull final Mode sort)
    {
        if (f == null || f.length == 0) {
            return new File[0];
        } else if (f.length == 1) {
            return f.clone();
        }

        @NotNull //* 使用 LinkedList 加快存放速度 *//
        final LinkedList<File>[] file =
                new LinkedList[]{new LinkedList<>(), new LinkedList<>()}; // 根据文件/文件夹分组
        @NotNull LinkedList<File> dirs, // 文件夹存放数组
                files; // 文件存放数组

        /* 根据文件排序模式选择顺序 */
        {
            @Nullable LinkedList<File> sortList[] = getFileSortMode(mode, file);
            if (sortList == null)
                return f;
            files = sortList[0];
            dirs = sortList[1];
        }

        /* 分开文件/文件夹 */
        for ( int i = 0, length = f.length; i < length; i++ )
            separationFile(dirs, files, f[i]);

        /* 用选择排序对其进行排序 */
        sortFile(sort, file);

        if (file[0].size() == 0)
            /* 第一个分组大小为 0 直接返回第二个分组 */
            return file[1].toArray(new File[file[1].size()]);
        if (file[1].size() > 0) {
            /* 第二个分组的大小不为0 */
            // 转移数据到第一个分组
            file[0].addAll(file[1]);
            // 释放资源
            file[1].clear();
            file[1] = null;
        }
        return file[0].toArray(new File[file[0].size()]);
    }

    /*
     * List
     */

    /**
     * <p>转化为数组后用{@link #sortFile(File[], int, int)}进行排序.</p>
     *
     * @param sort 排序方式
     * @param mode 文件列表排序方式
     * @param f    要排序的文件列表
     *
     * @return 排序后的文件列表
     *
     * @throws NullPointerException 参数为空
     * @see #sortFile(File[], int, int)
     */
    @NotNull
    public static
    List<File> sortFile(@Nullable final List<File> f, @NotNull final FileMode mode,
            @NotNull final Mode sort)
    {
        if (f == null || f.size() == 0)
            return new LinkedList<>();
        else if (f.size() == 1) {
            ArrayList s = f instanceof ArrayList ? (ArrayList) ((ArrayList<File>) f).clone()
                    : new ArrayList() {{
                        add(f.get(0));
                    }};
            return s;
        }

        @NotNull final LinkedList<File>[] file =
                new LinkedList[]{new LinkedList<>(), new LinkedList<>()}; // 根据文件/文件夹分组
        @NotNull LinkedList<File> dirs, // 文件夹存放数组
                files; // 文件存放数组

        /* 根据文件排序模式选择顺序 */
        {
            @Nullable LinkedList<File> sortList[] = getFileSortMode(mode, file);
            if (sortList == null)
                return f;
            files = sortList[0];
            dirs = sortList[1];
        }

        /* 分开文件/文件夹 */
        for ( int i = 0, length = f.size(); i < length; i++ )
            separationFile(dirs, files, f.get(i));

        /* 用选择排序对其进行排序 */
        sortFile(sort, file);

        if (file[0].size() == 0)
            /* 第一个分组大小为 0 直接返回第二个分组 */
            return file[1];
        if (file[1].size() > 0) {
            /* 第二个分组的大小不为0 */
            // 转移数据到第一个分组
            file[0].addAll(file[1]);
            // 释放资源
            file[1].clear();
            file[1] = null;
        }
        return file[0];
    }

    /*
     * Help
     */

    /**
     * 排序文件
     */
    private static
    void sortFile(@NotNull Mode sort, LinkedList<File>[] file)
    {
        LinkedList<File> linkedList;
        LinkedList<String> list;
        for ( int index = 0, lenght = file.length; index < lenght; index++ ){
            linkedList = file[index];

            list = (LinkedList<String>) ListTOOL.toString(linkedList);
            // 腾出空间
            linkedList.clear();

            list = (LinkedList<String>) sortString(list, sort);

            /* 转化为文件路径并重新写入 */
            for ( String string : list ){
                linkedList.add(new File(string));
                string = null;
            }
        }
    }

    /**
     * <p>获取文件排序方式.</p>
     *
     * @param mode 排序方式
     * @param file 排序集合
     *
     * @return 已调整的集合
     */
    @Nullable
    private static
    LinkedList<File>[] getFileSortMode(@NotNull FileMode mode, LinkedList<File>[] file) {
        @NotNull LinkedList ls[] = new LinkedList[]{file[0], file[0]};
        switch ( mode ) {
            case MODE_DIR:
                ls[0] = file[1];
                break;
            case MODE_DEFAULT:
                break;
            case MODE_FILE:
                ls[1] = file[1];
                break;
            default:
                return null;
        }
        return ls;
    }

    /**
     * <p>拆分文件/文件夹.</p>
     *
     * @param dirs  存放文件夹的集合
     * @param files 存放文件的集合
     * @param tmp   当前文件
     */
    private static
    void separationFile(@NotNull final LinkedList<File> dirs, @NotNull final LinkedList<File> files,
            @NotNull final File tmp)
    {
        try {
            if (tmp.isDirectory()) {
                /* 是文件夹 */
                dirs.add(tmp); // 添加入文件夹组
            } else if (tmp.isFile()) {
                /* 是文件 */
                files.add(tmp); // 添加入文件组
            }
        } catch ( Exception e ) {
            // 虚路径，不处理
        }
    }
}