package fybug.nulll.pdfunctionlibrary.Util;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.io.Serializable;

import fybug.nulll.pdfunctionlibrary.Annotations.CanSynchronized;
import fybug.nulll.pdfunctionlibrary.Annotations.NoSynchronized;
import fybug.nulll.pdfunctionlibrary.lang.CanEmpty;
import fybug.nulll.pdfunctionlibrary.lang.ConsistentField;
import fybug.nulll.pdfunctionlibrary.lang.MaybeSynchronized;

import static fybug.nulll.pdfunctionlibrary.Processing.CheckObject.equalsFidle;
import static fybug.nulll.pdfunctionlibrary.Util.DataContainer.cloneField;
import static fybug.nulll.pdfunctionlibrary.lang.CanEmpty.checkNull;
/**
 * <h2>数据格.</h2>
 * <pre>
 * 提供存放一格数据的对象
 * 可检查内容是否为空包括 <b>逻辑为空</b>
 * 但是如果要序列化则存放的数据也必须是 <b>可序列化</b> 的
 * </pre>
 *
 * @param <V> 存放的数据类型
 *
 * @author fybug
 * @version 0.0.3
 * @see ConsistentField
 * @see CanEmpty
 * @see Serializable
 * @see MaybeSynchronized
 * @see Synchronized
 * @since PDF 1.2
 */
@SuppressWarnings( "All" )
public abstract
class DataGrid<V> extends ConsistentField
        implements CanEmpty, Serializable, MaybeSynchronized, Cloneable
{
    private static final long serialVersionUID = 4768456080120637380L;
    // V
    @Nullable protected V value = null;

    /** <p>构造一个空的数据格.</p> */
    protected
    DataGrid() { this(null); }

    /** <p>构造一个数据格并初始化.</p> */
    protected
    DataGrid(@Nullable final V t)
    { setValue(t); }

    @Override
    @NoSynchronized
    public
    Object clone() throws CloneNotSupportedException {
        @NotNull final DataGrid<V> dataContainer = (DataGrid<V>) super.clone();
        dataContainer.value = cloneField(value);
        return dataContainer;
    }

    @Override
    protected
    void finalize() throws Throwable {
        super.finalize();
        close();
    }

    @Override
    @NoSynchronized
    protected
    boolean consistent(@NotNull final Object obj)
    { return equalsFidle(value, ((DataGrid<?>) obj).value); }

    /*
     * Value
     */

    /**
     * <p>设置值数据.</p>
     *
     * @since PDF 1.2 expander 2
     */
    @NotNull
    public abstract
    DataGrid<V> setValue(@Nullable final V value);

    /** <p>获取当前值数据.</p> */
    @Nullable
    public final
    V getValue() { return value; }

    /** <p>清除值数据.</p> */
    @NotNull
    public
    DataGrid<V> cleanValue() { return setValue(null); }

    /**
     * <p>检查当前值是否为空.</p>
     * <pre>
     * 检查值是否为 <b>NULL</b>
     * 如果值是实现了 {@link CanEmpty} 的类,将会检查其是否逻辑为空
     * </pre>
     */
    public final
    boolean valueEmpty() {
        @NotNull final V t;
        t = getValue();
        if (t != null)
            return checkNull(t);
        return true;
    }

    /*
     * CheckObject
     */

    @Override
    public
    boolean isNull() { return getValue() == null; }

    @Override
    public
    boolean isEmpty() { return valueEmpty(); }

    /*
     * 释放
     */

    /** 清除值的数据 */
    @Override
    public
    void clean() { setValue(null); }

    /** <p>{@link #clean()}</p> */
    @Override
    public
    void free() { clean(); }

    /** <p>{@link #free()}</p> */
    @Override
    public
    void close() { free(); }

    /*
     * 转化
     */

    /**
     * <p>保证该对象为并发处理对象.</p>
     * <p>如果该对象是并发对象则会返回该对象，否则会进行数据转移</p>
     *
     * @see Synchronized
     * @since PDF 1.2 expander 2
     */
    @Override
    @NotNull
    public
    DataGrid<V> toSynchronized() {
        if (this instanceof Synchronized<?>)
            return this;
        @NotNull final DataGrid<V> d = new Synchronized<>();
        d.value = this.value;
        return d;
    }

    /**
     * <p>保证该对象为非并发处理对象.</p>
     * <p>如果该对象是非并发对象则会返回该对象，否则会进行数据转移<br/>
     * 在非并发的条件下，该类对象的速度通常较快</p>
     *
     * @see DataGRID
     * @since PDF 1.2 expander 2
     */
    @Override
    @NotNull
    public
    DataGrid<V> removerSynchronized() {
        if (this instanceof DataGRID<?>)
            return this;
        @NotNull final DataGrid<V> d = new DataGRID<>();
        d.value = this.value;
        return d;
    }

    /*
     * 工场方法
     */

    /**
     * <p>获取{@link DataGrid} 的实现类.</p>
     *
     * @return
     *
     * @see DataGRID
     */
    @NotNull
    public static
    DataGrid<?> getDataGrid()
    { return new DataGRID<>(); }

    /**
     * <p>获取{@link DataGrid} 的实现类.</p>
     * <p>并初始化数据</p>
     *
     * @param v 要初始化的数据
     *
     * @return
     *
     * @see DataGRID
     */
    @NotNull
    public static
    <V> DataGrid<V> getDataGrid(@Nullable final V v)
    { return new DataGRID<>(v); }

    /**
     * <p>获取{@link DataGrid} 的并发实现类.</p>
     *
     * @return
     *
     * @see Synchronized
     */
    @NotNull
    public static
    DataGrid<?> getSynchronized()
    { return new Synchronized<>(); }

    /**
     * <p>获取{@link DataGrid} 的并发实现类.</p>
     * <p>并初始化数据</p>
     *
     * @param v 要初始化的数据
     *
     * @return
     *
     * @see Synchronized
     */
    @NotNull
    public static
    <V> DataGrid<V> getSynchronized(@Nullable final V v)
    { return new Synchronized<>(v); }

    /**
     * <h2>{@link DataGrid} 的并发实现类.</h2>
     * <p>已实现抽象并处理并发</p>
     *
     * @author fybug
     * @version 0.0.1
     * @see DataGrid
     * @since PDF 1.2 expander 2
     */
    @CanSynchronized
    public static
    class Synchronized<V> extends DataGrid<V> {
        private static final long serialVersionUID = -4075045200707176402L;

        protected
        Synchronized() { super(); }

        protected
        Synchronized(@Nullable final V t) { super(t); }

        /*
         * Value
         */

        @NotNull
        @Override
        public
        DataGrid<V> setValue(@Nullable final V value) {
            synchronized ( this ){
                this.value = value;
            }
            return this;
        }
    }

    /**
     * <h2>{@link DataGrid} 的实现类.</h2>
     * <p>已实现抽象但未处理并发</p>
     *
     * @author fybug
     * @version 0.0.1
     * @see DataGrid
     * @since PDF 1.2 expander 2
     */
    @NoSynchronized
    public static
    class DataGRID<V> extends DataGrid<V> {
        private static final long serialVersionUID = 8497014305823011082L;

        protected
        DataGRID() { super(); }

        protected
        DataGRID(@Nullable final V t) { super(t); }

        /*
         * Value
         */

        @NotNull
        @Override
        public
        DataGrid<V> setValue(@Nullable final V value) {
            this.value = value;
            return this;
        }
    }
}