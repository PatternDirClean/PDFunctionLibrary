package fybug.nulll.pdfunctionlibrary.Util;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.lang.reflect.Array;

import fybug.nulll.pdfunctionlibrary.Annotations.CanSynchronized;
import fybug.nulll.pdfunctionlibrary.Annotations.NoSynchronized;
import fybug.nulll.pdfunctionlibrary.Processing.CObject;
import fybug.nulll.pdfunctionlibrary.Processing.CheckObject;
import fybug.nulll.pdfunctionlibrary.Processing.Stop.MaybeStop;
import fybug.nulll.pdfunctionlibrary.Processing.Stop.Stop;
import fybug.nulll.pdfunctionlibrary.lang.CanEmpty;
import fybug.nulll.pdfunctionlibrary.lang.Cleanable;
import fybug.nulll.pdfunctionlibrary.lang.ConsistentField;
import fybug.nulll.pdfunctionlibrary.lang.MaybeSynchronized;

import static fybug.nulll.pdfunctionlibrary.Processing.CheckObject.equalsFidle;
import static fybug.nulll.pdfunctionlibrary.Processing.CheckUtil.checkArrayTable;
/**
 * <h2>双状态数据容器类.</h2>
 * <pre>
 * 提供一个用于存放一个引用的数据容器
 * 子类无需再处理数据的存放或读取，并要求可选择是否使用其并发状态类
 * 可选择继承的状态
 * 无法获取数据的时候会抛出 {@link Stop}
 * 支持链式操作
 * </pre>
 * <p>该类用于拓展</p>
 *
 * @param <V> 要存放的对象类型
 *
 * @author fybug
 * @version 0.0.7
 * @see Stop
 * @see ConsistentField
 * @see CanEmpty
 * @see Cloneable
 * @see MaybeSynchronized
 * @see MaybeStop
 * @see Synchronized
 * @see DataCONTAINER
 * @since PDF 1.2 expander 2
 */
@SuppressWarnings( "All" )
public abstract
class DataContainer<V> extends ConsistentField implements Cloneable, CanEmpty {
    // V
    @Nullable protected Object[] value;

    /** <p>构造一个空的数据容器.</p> */
    protected
    DataContainer() {
        value = new Object[1];
    }

    /** <p>构造一个数据容器并初始化.</p> */
    protected
    DataContainer(@Nullable final V t) {
        this();
        setValue(t);
    }

    @Override
    protected
    void finalize() throws Throwable {
        super.finalize();
        close();
    }

    @NotNull
    @Override
    @NoSynchronized
    public
    Object clone() throws CloneNotSupportedException {
        @NotNull DataContainer<?> dataContainer;
        dataContainer = (DataContainer<?>) super.clone();
        dataContainer.value = cloneField(value);
        return dataContainer;
    }

    @Override
    @NoSynchronized
    protected
    boolean consistent(@NotNull final Object obj)
    { return equalsFidle(value, ((DataContainer<?>) obj).value); }

    /*
     * Value
     */

    /**
     * <p>设置内部数据.</p>
     * <p></p>
     * <b>如果未被关闭</b>
     *
     * @since PDF 1.2 expander 2
     */
    @NotNull
    protected abstract
    DataContainer<V> setValue(@Nullable final V value);

    /** <p>清除内部数据.</p> */
    @NotNull
    protected
    DataContainer<V> cleanValue() { return setValue(null); }

    /** <p>获取当前数据.</p> */
    @NotNull
    @MaybeStop
    protected
    V getValue() {
        Cleanable.checkClose(Cleanable.errmessage, value);
        return (V) checkArrayTable(value);
    }

    /*
     * Check
     */

    @Override
    public
    boolean isNull() { return value == null; }

    @Override
    public
    boolean isEmpty() { return isNull() || value[0] == null; }

    /*
     * 释放
     */

    /** <p>清除内部数据.</p> */
    @Override
    public
    void clean() { cleanValue(); }

    /** <p>{@link #clean()}</p> */
    @Override
    public
    void free() { clean(); }

    /**
     * <p>关闭该容器.</p>
     *
     * @since PDF 1.2 expander 2
     */
    @Override
    public abstract
    void close();

    /*
     * Clone
     */

    /**
     * <p>克隆可能为数组的字段.</p>
     *
     * @param t 要检查并克隆的对象
     *
     * @return 如为数组，则返回克隆的数组，不是则返回该对象
     */
    @NotNull
    public static
    <T> T cloneArrayField(@NotNull T t) {
        if (t == null)
            return null;

        @NotNull final Class<?> tClass = (Class<?>) t.getClass();

        if (tClass.isArray()) {
            // 克隆数组
            final int lenght = Array.getLength(t); // 数组长度
            @NotNull Object t1 = Array.newInstance(tClass.getComponentType(), lenght);
            System.arraycopy(t, 0, t1, 0, lenght);
            t = (T) t1;
        }

        return t;
    }

    /** <p>克隆字段.</p> */
    @NotNull
    @CanSynchronized
    public static
    <T> T cloneField(@Nullable T t) {
        if (t != null)
            t = cloneArrayField(t);

        if (t instanceof CObject) {
            try {
                t = (T) ((CObject) t).clone();
            } catch ( CloneNotSupportedException e ) {
                // fuck
            }
        }

        return t;
    }

    /**
     * <h2>{@link DataContainer} 并发抽象.</h2>
     * <p>已实现基本抽象并处理并发</p>
     *
     * @author fybug
     * @version 0.0.2
     * @see DataContainer
     * @since PDF 1.2 expander 2
     */
    @CanSynchronized
    public static abstract
    class Synchronized<V> extends DataContainer<V> {
        protected
        Synchronized() { super(); }

        protected
        Synchronized(@NotNull final V t)
        { super(t); }

        /*
         * Value
         */

        @NotNull
        protected
        DataContainer<V> setValue(@Nullable final V value) {
            Cleanable.checkClose(Cleanable.errmessage, (Object) this.value);
            @Nullable final Object[] v;

            synchronized ( this ){
                v = this.value;

                if (CheckObject.checkNull(v).length < 1)
                    return this;
                v[0] = value;
            }

            return this;
        }

        /*
         * 释放
         */

        @Override
        public
        void close() {
            free();

            synchronized ( this ){
                this.value = null;
            }
        }
    }

    /**
     * <h2>{@link DataContainer} 非并发抽象.</h2>
     * <p>已实现基本抽象但未处理并发</p>
     *
     * @author fybug
     * @version 0.0.2
     * @see DataContainer
     * @since PDF 1.2 expander 2
     */
    @NoSynchronized
    public static abstract
    class DataCONTAINER<V> extends DataContainer<V> {
        protected
        DataCONTAINER() {super();}

        protected
        DataCONTAINER(V t)
        { super(t); }

        /*
         * Value
         */

        @NotNull
        protected
        DataContainer<V> setValue(@Nullable final V value) {
            Cleanable.checkClose(Cleanable.errmessage, (Object) this.value);
            @Nullable final Object[] v;
            v = this.value;
            if (CheckObject.checkNull(v).length < 1)
                return this;
            v[0] = value;
            return this;
        }

        /*
         * 释放
         */

        @Override
        public
        void close() {
            free();
            this.value = null;
        }
    }
}