package fybug.nulll.pdfunctionlibrary.Util.Processing;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;

import fybug.nulll.pdfunctionlibrary.Util.Processing.Interface.Element;
import fybug.nulll.pdfunctionlibrary.Util.Processing.Interface.ObjectCoupler;

/**
 * <h2>数组相关工具类.</h2>
 *
 * @author fybug
 * @version 0.0.8
 * @since Processing 0.0.1
 */
final public
class ArraryTOOL {
    /** {@Hide} */
    @Deprecated
    private
    ArraryTOOL() {}

    /**
     * 清除数组中的空项.
     * <p>
     * 清除数组中的 {@code null} 并整理剩余的数据
     *
     * @param ts 要检查的数组,不会被过滤过程影响
     *
     * @return 返回处理过的数组
     */
    @NotNull
    public static
    <T> T[] trim(@Nullable T... ts) {
        if (ts.length == 0)
            // 没有数据
            return ts;

        final int[] pushindex = {0}; // 填充的位置
        T[] newarr = (T[]) Array.newInstance(ts.getClass().getComponentType(), ts.length);

        /* 检查数据 */
        forEach(ts, t -> {
            if (t == null)
                // 丢弃
                return;

            // 保存
            newarr[pushindex[0]++] = t;
        });

        /* 长度冗余 */
        if (pushindex[0] < ts.length)
            return Arrays.copyOf(newarr, pushindex[0]);

        return newarr;
    }

    /**
     * 对未知对象进行 {@link #trim(Object[])} 操作
     *
     * @since ArraryTOOL 0.0.8
     */
    @NotNull
    public static
    Object trimForNaviter(@Nullable Object t) {
        if (t == null || !t.getClass().isArray())
            // 不是数组
            throw new ClassCastException("not array");

        int len; // 长度
        if ((len = Array.getLength(t)) == 0)
            // 莫得数据
            return t;

        final int[] pushindex = {0}; // 当前填充位置
        Object newarr = Array.newInstance(t.getClass().getComponentType(), len); // 返回的数组

        /* 检查 */
        forEachForNaviter(t, v -> {
            if (v == null)
                // 去除
                return;
            // 保存
            Array.set(newarr, pushindex[0]++, v);
        });

        /* 长度冗余 */
        if (pushindex[0] < len) {
            Object rearr = Array.newInstance(t.getClass().getComponentType(), pushindex);
            System.arraycopy(newarr, 0, rearr, 0, pushindex[0]);
            return rearr;
        }

        return newarr;
    }

    /**
     * 修整数组.
     *
     * @param t 要修整的数组
     *
     * @return 传入 {@code null | []} 返回 {@code null}
     *
     * @since ArraryTOOL 0.0.8
     */
    @Nullable
    public static
    Object unifiedArray(@Nullable Object t) {
        if (t == null)
            return null;

        Class c = t.getClass();
        if (!c.isArray())
            // 不是数组
            throw new ClassCastException("not array");

        if (Array.getLength(t) == 0)
            return null;

        return t;
    }

    /**
     * 修整数组.
     *
     * @param t      要修整的数组
     * @param tClass 数组对象类型
     *
     * @return 传入 {@code null} 返回 {@code []}
     *
     * @since ArraryTOOL 0.0.8
     */
    @NotNull
    public static
    <T> T[] unifiedArray(@Nullable Object t, Class<T> tClass) {
        if (t == null)
            return (T[]) Array.newInstance(tClass, 0);

        Class c = t.getClass();
        if (!c.isArray())
            // 不是数组
            throw new ClassCastException("not array");

        return (T[]) t;
    }

    /**
     * 数组类型转化.
     * <p>
     * 转化为另一种类型的数组
     * 需要使用接口对遍历到的对象进行转换
     *
     * @param <U>     转换后的类型
     * @param <T>     转换前的类型
     * @param in      要转换的数组
     * @param coupler 耦合器
     * @param outType 转换后的数组中的类型
     *
     * @return 转化后的数组，传入 {@code null | []} 都会返回新类型的空数组
     */
    @NotNull
    public static
    <T, U> U[] conversionArrayType(@NotNull T[] in, ObjectCoupler<U, T> coupler, Class<U> outType)
    {
        if (in == null || in.length == 0)
            return (U[]) Array.newInstance(outType, 0);

        final int[] index = {0};
        U[] newarr = (U[]) Array.newInstance(outType, in.length);

        forEach(in, i -> newarr[index[0]++] = coupler.conversionTo(i));

        return newarr;
    }

    /**
     * 对未知对象进行 {@link #conversionArrayType(Object[], ObjectCoupler, Class)} 操作
     *
     * @since ArraryTOOL 0.0.8
     */
    @NotNull
    public static
    <U> U[] conversionArrayTypeForNaviter(@NotNull Object in, ObjectCoupler<U, Object> coupler,
            Class<U> outType)
    {
        if (in == null || !in.getClass().isArray())
            // 不是数组
            throw new ClassCastException("not array");

        int len; // 长度
        if ((len = Array.getLength(in)) == 0)
            // 空数组
            return (U[]) Array.newInstance(outType, 0);

        final int[] i = {0}; // 当前填充位置
        U[] newarr = (U[]) Array.newInstance(outType, len); // 新的数组

        /* 逐个转换 */
        forEachForNaviter(in, v -> newarr[i[0]++] = coupler.conversionTo(v));

        return newarr;
    }

    /**
     * 追加数据到数组中
     * <p>
     * 旧数必须为数组，追加的数据根据其类型可能会有不同的追加形式
     * <p>
     * 当追加的数据为一个数组或 {@link Collection} 的实现类时，将会取出其中的数据追加到旧数据后
     * 其余情况将会直接追加到就数据后
     *
     * @param in  旧数据
     * @param add 追加的数据
     *
     * @return 与旧数据为一个类型的数组
     */
    @NotNull
    public static
    Object append(@NotNull Object in, Object add) {
        Class c;
        if (in == null || !(c = in.getClass()).isArray())
            // 不是数组
            throw new ClassCastException("not array");

        if (add == null)
            return in;

        Object newarr; // 组合数组
        int len = Array.getLength(in);
        int addlen;

        /* 追加数组 */
        if (add.getClass().isArray()) {
            addlen = Array.getLength(add);

            newarr = Array.newInstance(c.getComponentType(), len + addlen);

            System.arraycopy(in, 0, newarr, 0, len);
            System.arraycopy(add, 0, newarr, len, addlen);

            return newarr;
        }

        /* 追加数据对象 */
        if (add instanceof Collection) {
            addlen = ((Collection) add).size();

            newarr = Array.newInstance(c.getComponentType(), len + addlen);

            System.arraycopy(in, 0, newarr, 0, len);

            int i = len;
            for ( Object o : ((Collection) add) )
                Array.set(newarr, i++, o);

            return newarr;
        }

        newarr = Array.newInstance(c.getComponentType(), len + 1);

        System.arraycopy(in, 0, newarr, 0, len);
        Array.set(newarr, len, add);

        return newarr;
    }

    /**
     * 遍历数组
     * <p>
     * 使用接口遍历数组类似 {@link Collection#forEach(Consumer)}
     *
     * @param arr 要遍历的数组
     * @param run 遍历接口
     */
    public static
    <T> void forEach(@Nullable T[] arr, Consumer<T> run) {
        if (arr == null || arr.length == 0 || run == null)
            return;
        for ( int i = 0; i < arr.length; )
            run.accept(arr[i++]);
    }

    /** 对一个未知对象进行 {@link #forEach(Object[], Consumer)} 操作 */
    public static
    void forEachForNaviter(@NotNull Object arr, Consumer run) {
        if (arr == null || !arr.getClass().isArray())
            // 不是数组
            throw new ClassCastException("not array");

        int len; // 长度

        if ((len = Array.getLength(arr)) == 0)
            // 不用跑
            return;

        for ( int i = 0; i < len; )
            run.accept(Array.get(arr, i++));
    }

    /** 检查是否在数组中有该元素 */
    public static
    <T> boolean inArray(@Nullable T[] arr, @Nullable T o) {
        if (arr == null || o == null || arr.length == 0)
            return false;

        for ( int i = 0; i < arr.length; )
            if (arr[i++].equals(o))
                return true;
        return false;
    }

    /**
     * 快照风格的遍历数据
     * <p>
     * 遍历前使用 {@link Arrays#copyOf(Object[], int)} 拷贝一份数组进行。
     * 源数组的改变不会影响到遍历的结果
     *
     * @param arr 要遍历的数组
     * @param run 遍历接口
     *
     * @since ArraryTOOL 0.0.8
     */
    public static
    <T> void copyOfRead(@Nullable T[] arr, Consumer<T> run) {
        if (arr == null || arr.length == 0)
            // 无法遍历
            return;

        T[] newarr = Arrays.copyOf(arr, arr.length);

        for ( int i = 0; i < arr.length; )
            run.accept(newarr[i++]);
    }

    /**
     * 操作数组
     * <p>
     * 快照风格遍历同时操作数组，使用接口 {@link Element} 对当前指向的位置进行操作
     * 游标只可向前，不可回退
     *
     * @param ts  要操作的数组
     * @param run 允许接口
     *
     * @return 操作后的数组，与源数组不相同
     *
     * @since ArraryTOOL 0.0.8
     */
    @Nullable
    public static
    <T> T[] operation(@Nullable T[] ts, Consumer<Element<T>> run) {
        if (ts == null || ts.length == 0)
            return ts;

        int i = 0; // 当前位置
        T[] nowarr = Arrays.copyOf(ts, ts.length); // 当前快照
        operationElement<T> element; // 当前操作对象

        /* 遍历 */
        for ( ; i < nowarr.length; i++ ){
            element = new operationElement<>();
            element.telement = nowarr[i];
            element.i = i;
            element.nowarr = nowarr;

            run.accept(element);

            i = element.i;
            nowarr = element.nowarr;
        }

        return nowarr;
    }

    /**
     * <h2>操作用接口.</h2>
     *
     * @author fybug
     * @version 0.0.1
     * @since ArraryTOOL 0.0.8
     */
    private static
    class operationElement<T> implements Element<T> {
        /** 当前元素节点 */
        T telement;
        int i;
        T[] nowarr;

        public
        T get() { return telement; }

        public
        void delete() {
            T[] newarr = (T[]) Array.newInstance(nowarr.getClass().getComponentType(),
                                                 nowarr.length - 1);

            System.arraycopy(nowarr, 0, newarr, 0, i);

            if (i < nowarr.length - 1)
                // 不是最后一个
                System.arraycopy(nowarr, i + 1, newarr, i, newarr.length - i);

            --i;
            nowarr = newarr;
        }

        public
        void append(T t) {
            T[] newarr = (T[]) Array.newInstance(nowarr.getClass().getComponentType(),
                                                 nowarr.length + 1);

            newarr[i + 1] = t;
            System.arraycopy(nowarr, 0, newarr, 0, i + 1);

            if (i < nowarr.length - 1)
                // 不是最后一个
                System.arraycopy(nowarr, i + 1, newarr, i + 2, nowarr.length - i - 1);

            nowarr = newarr;
        }

        public
        void prepend(T t) {
            T[] newarr = (T[]) Array.newInstance(nowarr.getClass().getComponentType(),
                                                 nowarr.length + 1);

            newarr[i] = t;
            if (i > 0)
                System.arraycopy(nowarr, 0, newarr, 0, i);
            System.arraycopy(nowarr, i, newarr, i + 1, nowarr.length - i);

            ++i;

            nowarr = newarr;
        }

        public
        void set(T t) { nowarr[i] = t; }

        public
        void remove() { set(null); }

        public
        void removeIf(Predicate<T> predicate) {
            if (predicate.test(get()))
                remove();
        }

        public
        void deleteIf(Predicate<T> predicate) {
            if (predicate.test(get()))
                delete();
        }

        public
        void setIf(T t, Predicate<T> predicate) {
            if (predicate.test(get()))
                set(t);
        }
    }
}