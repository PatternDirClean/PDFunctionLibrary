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
 * @version 0.0.7
 * @since PDF 1.2
 */
@SuppressWarnings( "All" )
final public
class ArraryTOOL {
    /** {@Hide} */
    @Deprecated
    private
    ArraryTOOL() {}

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
            out[index++] = coupler.conversionTo(t);
        }

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
        for ( int i = 0; i < arrayList.length; ){
            ts = t[i++];
            if (!CanEmpty.checkNull(ts)) {
                /* 非空数据对象 */
                arrayList[idmark++] = ts;
            }
        }

        /* 有多余 */
        if (idmark < arrayList.length)
            arrayList = Arrays.copyOf(arrayList, idmark);
        return arrayList;
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
        // 拷贝
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
        for ( int i = ts.length - 1; i >= 0; ){
            t = ts[i--];
            if (t != null) {
                mark = ts.length - i;
                break;
            }
        }
        return mark;
    }

    /** <h2>遍历处理接口</h2> */
    public static
    interface Each<T> {
        void run(T v);
    }

    /** 遍历数组 */
    public static
    <T> void forEach(T[] arr, Each<T> run) {
        if (arr == null || arr.length == 0 || run == null)
            return;
        for ( int i = 0; i < arr.length; )
            run.run(arr[i++]);
    }

    /** 检查是否在数组中有该元素 */
    public static
    <T> boolean inArray(T[] arr, T o) {
        for ( int i = 0; i < arr.length; )
            if (arr[i++].equals(o))
                return true;
        return false;
    }
}