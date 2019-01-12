package fybug.nulll.pdfunctionlibrary.Util.Processing;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;

import fybug.nulll.pdfunctionlibrary.lang.CanEmpty;

import static java.lang.System.arraycopy;
import static java.lang.reflect.Array.newInstance;
/**
 * <h2>数组相关算法类.</h2>
 *
 * @author fybug
 * @version 0.0.6
 * @since PDF 1.2
 */
@SuppressWarnings( "All" )
final public
class Arrarys {
    /** {@Hide} */
    @Deprecated
    private
    Arrarys() {}

    /*
     * UnifiedArray
     */

    /**
     * <p>修整数组.</p>
     *
     * @param t 要修整的数组
     *
     * @return 如果对象是空对象或空数组则会返回{@code null}
     */
    @Nullable
    public static
    <T> T[] unifiedArray(@Nullable final T[] t) { return t == null || t.length == 0 ? null : t; }

    /**
     * <p>修整数组.</p>
     *
     * @param t 要修整的数组
     *
     * @return 如果对象是空对象或空数组则会返回{@code null}
     */
    @Nullable
    public static
    byte[] unifiedArray(@Nullable final byte[] t)
    { return t == null || t.length == 0 ? null : t; }

    /**
     * <p>修整数组.</p>
     *
     * @param t 要修整的数组
     *
     * @return 如果对象是空对象或空数组则会返回{@code null}
     */
    @Nullable
    public static
    short[] unifiedArray(@Nullable final short[] t)
    { return t == null || t.length == 0 ? null : t; }

    /**
     * <p>修整数组.</p>
     *
     * @param t 要修整的数组
     *
     * @return 如果对象是空对象或空数组则会返回{@code null}
     */
    @Nullable
    public static
    char[] unifiedArray(@Nullable final char[] t)
    { return t == null || t.length == 0 ? null : t; }

    /**
     * <p>修整数组.</p>
     *
     * @param t 要修整的数组
     *
     * @return 如果对象是空对象或空数组则会返回{@code null}
     */
    @Nullable
    public static
    int[] unifiedArray(@Nullable final int[] t) { return t == null || t.length == 0 ? null : t; }

    /**
     * <p>修整数组.</p>
     *
     * @param t 要修整的数组
     *
     * @return 如果对象是空对象或空数组则会返回{@code null}
     */
    @Nullable
    public static
    long[] unifiedArray(@Nullable final long[] t)
    { return t == null || t.length == 0 ? null : t; }

    /**
     * <p>修整数组.</p>
     *
     * @param t 要修整的数组
     *
     * @return 如果对象是空对象或空数组则会返回{@code null}
     */
    @Nullable
    public static
    double[] unifiedArray(@Nullable final double[] t)
    { return t == null || t.length == 0 ? null : t; }

    /**
     * <p>修整数组.</p>
     *
     * @param t 要修整的数组
     *
     * @return 如果对象是空对象或空数组则会返回{@code null}
     */
    @Nullable
    public static
    float[] unifiedArray(@Nullable final float[] t)
    { return t == null || t.length == 0 ? null : t; }

    /*
     * 算法
     */

    /**
     * <p>数组类型转化.</p>
     * <pre>
     * 转化为另一种类型的数组
     * 并会检查空数组
     * 需要使用接口对遍历到的对象进行转换
     * 会忽略转化过程中的空对象
     * </pre>
     *
     * @param <U>     转换后的类型
     * @param <T>     要转换的类型
     * @param in      要转换的数组
     * @param coupler 耦合器
     * @param outType 转换为的数组类型
     *
     * @return 转换并清除空对象后的数组，如果参数为空数组或{@code null} 则会返回空数组
     */
    @NotNull
    public static
    <T, U> U[] conversionArrayType(@Nullable final T[] in,
            @NotNull final ObjectCoupler<U, T> coupler, @NotNull final Class<U> outType)
    {
        int length = 0;
        /* 检查数据 */
        if (in == null || (length = in.length) == 0)
            return (U[]) newInstance(outType, 0);

        int index = 0; // 当前转化位置指针
        @NotNull U[] out = (U[]) newInstance(outType, length); // 新的数组
        @Nullable T t;

        for ( int i = 0; i < length; i++ ){
            t = in[i];
            if (t == null)
                continue;

            // 使用接口进行转化
            out[index] = coupler.conversionTo(t);
            index++;
        }

        /* 去除空数据 */
        out = Arrarys.trim(out);

        return out;
    }

    /**
     * <p>对象转换器.</p>
     * <pre>
     * 用于在两个对象之间进行转换
     * 通常进行单方面转换
     * </pre>
     *
     * @param <T> 要转换的类型
     * @param <U> 转换为该类型
     *
     * @author fybug
     * @version 0.0.1
     * @since PDF 1.2
     */
    public
    interface ObjectCoupler<U, T> {
        /** <p>在该方法进行对象转换.</p> */
        @Nullable
        U conversionTo(@NotNull final T in);
    }

    /**
     * <p>转化为字符串类型数组.</p>
     * <p>会调用每一个对象的{@link Object#toString()} 方法<br/>
     * 如果其中的对象为 <b>NULL</b> 则会转换为{@code ""}</p>
     *
     * @param t 要转化的数组<br/>
     *          不会被转化过程影响
     *
     * @return 转化为字符串的数组，如果传入的是空对象或空数组则会返回空的字符串数组
     *
     * @see Object#toString()
     */
    @NotNull
    public static
    <T extends Object> String[] toString(@Nullable final T[] t) {
        // 参数检查
        if (t == null || t.length == 0)
            return new String[0];
        @NotNull //* 输出结果长度必定与输入内容相同 *//
                String[] strings = new String[t.length]; // 输出的结果
        @Nullable String s;
        @Nullable T ts;
        /* 遍历输入的内容 */
        for ( int i = 0; i < strings.length; i++ ){
            ts = t[i];
            if (ts == null || (s = ts.toString()) == null)
                /* toString 后仍是 null */
                s = "";
            // 加入结果中
            strings[i] = s;
        }
        return strings;
    }

    /**
     * <p>清除数组中的空项.</p>
     * <p>如果是{@link CanEmpty}的子类<br/>
     * 将会调用{@link CanEmpty#isNull()}和{@link CanEmpty#isEmpty()}判断是否是空数据</p>
     *
     * @param t 要检查的数组<br/>
     *          不会被过滤过程影响
     *
     * @return 返回清除了空数据的数组，如果传入的是空对象或空数组则会返回空的数组
     *
     * @see CanEmpty
     */
    @NotNull
    public static
    <T extends Object> T[] trim(@Nullable final T[] t) {
        // 参数检查
        if (t == null || t.length == 0)
            return t;
        @NotNull T[] arrayList = (T[]) Array.newInstance(t.getClass().getComponentType(), t.length);
        @Nullable T ts;
        int idmark = 0; // 标记位置
        /* 遍历输入的数据 */
        for ( int i = 0; i < arrayList.length; i++ ){
            ts = t[i];
            if (!CanEmpty.checkNull(ts)) {
                /* 非空数据对象 */
                arrayList[idmark] = ts;
                idmark++;
            }
        }
        /* 有多余 */
        if (idmark < arrayList.length)
            arrayList = Arrays.copyOf(arrayList, idmark);
        return arrayList;
    }

    /**
     * <p>转化为字符串数组并清除空项.</p>
     * <p>如果是{@link CanEmpty}的子类<br/>
     * 将会调用{@link CanEmpty#isNull()}和 {@link CanEmpty#isEmpty()}判断是否是空数据<br/>
     * 以及转化为字符串后仍是空字符串的数据</p>
     *
     * @param t 要转换的数组 <br/>
     *          不会被转化和过滤过程影响
     *
     * @return 转化后的数组，如果传入的是空对象或空数组则会返回空的字符串数组
     *
     * @see #trim(Object[])
     * @see #toString(Object[])
     */
    @NotNull
    public static
    <T extends Object> String[] toStringTrim(@Nullable final T[] t) {
        if (t == null || t.length == 0)
            return new String[0];
        @Nullable String s, stringArray[] = new String[t.length];
        boolean b;
        int idmark = 0; // 当前标记
        @Nullable T ts;
        /* 遍历所有输入 */
        for ( int i = 0; i < t.length; i++ ){
            ts = t[i];
            // 检查是否是空数据
            b = CanEmpty.checkNull(ts);
            s = ts.toString(); // 转化字符串
            // 检查是否是空字符串
            b |= s == null || s.isEmpty() || (s = s.trim()).isEmpty();
            if (b)
                /* 告辞！*/
                continue;
            stringArray[idmark] = s;
            idmark++;
        }
        if (idmark < stringArray.length)
            stringArray = Arrays.copyOf(stringArray, idmark);
        return stringArray;
    }

    /**
     * <p>将新数据添加到旧数据后面.</p>
     *
     * @param ts     旧数据
     * @param append 新数据
     *
     * @return 添加数据后的的数组
     */
    @NotNull
    public static
    <T> T[] append(@Nullable final T[] ts, final T... append) {
        if (ts == null)
            return append;
        if (append.length == 0)
            return ts;

        // 新实例
        @NotNull final T[] newInstance = (T[]) newInstance(ts.getClass().getComponentType(),
                                                           ts.length + append.length);
        arraycopy(ts, 0, newInstance, 0, ts.length);
        arraycopy(append, 0, newInstance, ts.length, append.length);
        return newInstance;
    }

    /**
     * <p>将新数据添加到旧数据后面.</p>
     *
     * @param ts      旧数据
     * @param appends 新数据
     *
     * @return 添加数据后的数组
     */
    @NotNull
    public static
    <T> T[] append(@Nullable final T[] ts, @Nullable final Collection<T> appends) {
        @NotNull T[] append = (T[]) newInstance(ts.getClass().getComponentType(), appends.size());
        append = appends.toArray(append);
        return append(ts, append);
    }

    /**
     * <p>获取数组的内容长度.</p>
     * <pre>
     * 获取数组的实际内容长度
     *
     * 从最后的位置开始检查{@code NULL} 对象
     * 直到没有{@code NULL} 对象为止
     * </pre>
     *
     * @param ts 要检查的数组
     *
     * @return 如果参数为 {@code NULL} 则返回 {@code 0}
     */
    public static
    <T> int getContentLength(@Nullable final T[] ts) {
        if (ts == null)
            return 0;
        @Nullable T t;
        int mark = ts.length;
        for ( int i = ts.length - 1; i >= 0; i-- ){
            t = ts[i];
            if (t != null) {
                mark = ts.length - i;
                break;
            }
        }
        return mark;
    }
}
