package fybug.nulll.pdfunctionlibrary.Util;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import fybug.nulll.pdfunctionlibrary.Annotations.CanSynchronized;
import fybug.nulll.pdfunctionlibrary.Annotations.NoSynchronized;
import fybug.nulll.pdfunctionlibrary.lang.CanEmpty;

import static fybug.nulll.pdfunctionlibrary.Processing.CheckObject.equalsFidle;
import static fybug.nulll.pdfunctionlibrary.Util.DataContainer.cloneField;
/**
 * <h2>数据映射.</h2>
 * <p>提供一个存放一组键值的数据映射对象<br/>
 * 可用于作为映射类型的数据结构的内部支持</p>
 *
 * @param <K> 键
 * @param <V> 值
 *
 * @author fybug
 * @version 0.0.7
 * @see DataGrid
 * @see Synchronized
 * @see DataMAP
 * @since PDF 1.2
 */
@SuppressWarnings( "all" )
public abstract
class DataMap<K, V> extends DataGrid<V> {
    private static final long serialVersionUID = 6088419458345864876L;
    // K
    @Nullable protected K key;

    /** <p>构造一个空的数据映射.</p> */
    public
    DataMap() {this(null, null);}

    /** <p>构造一个数据映射并初始化键和值.</p> */
    public
    DataMap(@Nullable final K ke, @Nullable final V v) {
        super(v);
        key = ke;
    }

    @NotNull
    @Override
    @NoSynchronized
    public
    Object clone() throws CloneNotSupportedException {
        @NotNull final DataMap<K, V> dataContainer = (DataMap) super.clone();
        dataContainer.key = cloneField(key);
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
    { return super.consistent(obj) && equalsFidle(key, ((DataMap<K, V>) obj).key); }

    /*
     * Key
     */

    /**
     * <p>设置键的数据.</p>
     *
     * @since PDF 1.2 expanter 2
     */
    @NotNull
    public abstract
    DataMap<K, V> setKey(@Nullable final K k);

    /** <p>清除键的数据.</p> */
    @NotNull
    public
    DataMap<K, V> cleanKey() {return setKey(null);}

    /** <p>获取键数据.</p> */
    @Nullable
    public final
    K getKey() {return key;}

    /**
     * <p>检查当前键是否为空.</p>
     * <pre>
     * 检查键是否为 <b>NULL</b>
     * 如果键是实现了{@link CanEmpty} 的类,将会检查其是否逻辑为空
     * </pre>
     */
    public final
    boolean keyEmpty() {
        @NotNull final K t;
        t = getKey();
        if (t != null)
            return CanEmpty.checkNull(t);
        return true;
    }

    /*
     * Value
     */

    /**
     * <p>设置值数据.</p>
     *
     * @since PDF 1.2 expanter 2
     */
    @NotNull
    public abstract
    DataMap<K, V> setValue(@Nullable final V v);

    /** <p>清除值数据.</p> */
    @NotNull
    public
    DataMap<K, V> cleanValue() { return (DataMap<K, V>) super.cleanValue(); }

    /*
     * 释放
     */

    /** <p>清除键和值的数据.</p> */
    @Override
    public
    void clean() {
        super.clean();
        cleanKey();
    }

    /** <p>{@link #clean()}</p> */
    @Override
    public
    void free() {super.free();}

    /** <p>{@link #free()}</p> */
    @Override
    public
    void close() {super.close();}

    /*
     * CheckObject
     */

    @Override
    public
    boolean isNull() {return super.isNull() && getKey() == null;}

    @Override
    public
    boolean isEmpty() {return keyEmpty() && super.isEmpty();}

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
    DataMap<K, V> toSynchronized() {
        if (this instanceof Synchronized)
            return this;

        @NotNull final DataMap<K, V> s = new Synchronized();
        s.key = this.key;
        s.value = this.value;
        return s;
    }

    /**
     * <p>保证该对象为非并发处理对象.</p>
     * <p>如果该对象是非并发对象则会返回该对象，否则会进行数据转移<br/>
     * 在非并发的条件下，该类对象的速度通常较快</p>
     *
     * @see DataMAP
     * @since PDF 1.2 expander 2
     */
    @Override
    @NotNull
    public
    DataMap<K, V> removerSynchronized() {
        if (this instanceof DataMAP)
            return this;
        @NotNull final DataMAP<K, V> s = new DataMAP();
        s.key = this.key;
        s.value = this.value;
        return s;
    }

    /*
     * 工场方法
     */

    /**
     * <p>获取{@link DataMap} 的实现类.</p>
     *
     * @return
     *
     * @see DataMAP
     */
    @NotNull
    public static
    DataMap getDataMap()
    {return new DataMAP();}

    /**
     * <p>获取{@link DataMap} 的实现类.</p>
     * <p>并初始化数据</p>
     *
     * @param v 要初始化的数据
     *
     * @return
     *
     * @see DataMAP
     */
    @NotNull
    public static
    <K, V> DataMap<K, V> getDataMap(@Nullable final K k, @Nullable final V v)
    { return new DataMAP(k, v); }

    /**
     * <p>获取{@link DataMap} 的并发实现类.</p>
     *
     * @return
     *
     * @see Synchronized
     */
    @NotNull
    public static
    DataMap getSynchronized()
    { return new Synchronized(); }

    /**
     * <p>获取{@link DataMap} 的并发实现类.</p>
     * <p>并初始化数据</p>
     *
     * @param k
     * @param v 要初始化的数据
     *
     * @return
     *
     * @see Synchronized
     */
    @NotNull
    public static
    <K, V> DataMap<K, V> getSynchronized(@Nullable final V v, @Nullable final K k)
    { return new Synchronized(k, v); }

    /**
     * <h2>{@link DataMap} 的并发实现.</h2>
     * <p>已实现抽象并处理并发</p>
     *
     * @since PDF 1.2 expanter 2
     */
    @CanSynchronized
    public static
    class Synchronized<K, V> extends DataMap<K, V> {
        private static final long serialVersionUID = 4902826450042293331L;
        // 同步锁
        @NotNull private Object keyLock = new Object();

        public
        Synchronized() { super(); }

        public
        Synchronized(@Nullable final K ke, @Nullable final V v) { super(ke, v); }

        @Override
        @NoSynchronized
        public
        Object clone() throws CloneNotSupportedException {
            @NotNull final Synchronized<K, V> dataContainer =
                    (Synchronized<K, V>) super.clone();
            ((Synchronized) dataContainer).keyLock = new Object();
            return dataContainer;
        }

        /*
         * Key
         */

        @NotNull
        public
        DataMap<K, V> setKey(@Nullable final K k) {
            synchronized ( keyLock ){
                this.key = k;
            }
            return this;
        }

        /*
         * Value
         */

        @NotNull
        @Override
        public
        DataMap<K, V> setValue(@Nullable final V value) {
            synchronized ( this ){
                this.value = value;
            }
            return this;
        }
    }

    /**
     * <h2>{@link DataMap} 的实现.</h2>
     * <p>已实现抽象</p>
     *
     * @since PDF 1.2 expanter 2
     */
    @NoSynchronized
    public static
    class DataMAP<K, V> extends DataMap<K, V> {
        private static final long serialVersionUID = 7781533116209635011L;

        public
        DataMAP() { super(); }

        public
        DataMAP(@Nullable final K ke, @Nullable final V v) { super(ke, v); }

        /*
         * Key
         */

        @NotNull
        public
        DataMap<K, V> setKey(@Nullable final K k) {
            this.key = k;
            return this;
        }

        /*
         * Value
         */

        @NotNull
        @Override
        public
        DataMap<K, V> setValue(@Nullable final V value) {
            this.value = value;
            return this;
        }
    }
}