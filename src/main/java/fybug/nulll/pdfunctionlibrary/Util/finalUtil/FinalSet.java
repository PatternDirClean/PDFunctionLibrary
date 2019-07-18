package fybug.nulll.pdfunctionlibrary.Util.finalUtil;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.HashSet;
import java.util.Set;

import fybug.nulll.pdfunctionlibrary.Annotations.CanSynchronized;
import fybug.nulll.pdfunctionlibrary.Annotations.NoSynchronized;
/**
 * <h2>常量集合.</h2>
 * <pre>
 * 内部在内容固化之前维护一个 {@link Set} 作为缓存
 * 固化内容之后便使用 {@code Object[]} 作为内容存储，并关闭写入接口
 * </pre>
 *
 * @param <T> 内部存放的常量的类型
 *
 * @author fybug
 * @version 0.0.1
 * @see ConstantsSet
 * @since PDF 1.3 expander 4
 */
@SuppressWarnings( "all" )
abstract
class FinalSet<T> implements ConstantsSet<T> {
    /** <p>常量缓存.</p> */
    @Nullable Set<T> set;
    /** <p>是否固化.</p> */
    boolean isfinal = false;
    /** <p>固化后的内容.</p> */
    @Nullable Object[] finalArray;

    FinalSet() {set = new HashSet<>();}

    @Override
    public
    void forEach(@NotNull final Consumer<T> action) {
        /* 检查是否固化 */
        if (isfinal)
            for ( int i = 0; i < finalArray.length; i++ )
                action.get((T) finalArray[i]);
        else
            for ( T t : set )
                action.get(t);
    }

    @Override
    public
    FinalSet<T> toSynchronized() {
        /* 检测类型 */
        if (this instanceof Synchronized<?>)
            return this;
        return cl(this, new Synchronized<>());
    }

    @Override
    public
    FinalSet<T> removerSynchronized() {
        /* 检测类型 */
        if (this instanceof noSynchronized<?>)
            return this;
        return cl(this, new noSynchronized<>());
    }

    /** 克隆字段 */
    static
    <T> FinalSet<T> cl(FinalSet<T> ob, FinalSet<T> clo) {
        /* Clone:克隆 */
        // 克隆缓存
        clo.set = ob.set == null ? null : (Set<T>) ((HashSet<T>) ob.set).clone();
        // 克隆固化数据
        clo.finalArray = ob.finalArray == null ? null : ob.finalArray.clone();
        // 克隆标识
        clo.isfinal = ob.isfinal;
        /* // Clone */
        return clo;
    }

    static
    void finla(final FinalSet<?> f) {
        if (f.isfinal)
            return;
        // 更改标识
        f.isfinal = true;
        // 生成固化内容数组
        f.finalArray = new Object[f.set.size()];
        int i = 0; // 拷贝的位置
        /* 拷贝 */
        for ( Object t : f.set ){
            f.finalArray[i++] = t;
        }
        /* FEED:释放缓存 */
        f.set.clear();
        f.set = null;
        /* // FEED */
    }

    static
    void finalSY(final FinalSet<?> f, final Object LOCK) {
        if (f.isfinal)
            return;
        int i = 0; // 拷贝的位置
        synchronized ( LOCK ){
            // 更改标识
            f.isfinal = true;
            // 生成固化内容数组
            f.finalArray = new Object[f.set.size()];
            /* 拷贝 */
            for ( Object t : f.set ){
                f.finalArray[i++] = t;
            }
        }
        /* FEED:释放缓存 */
        f.set.clear();
        f.set = null;
        /* // FEED */
    }

    /**
     * @author fybug
     * @version 0.0.1
     * @since PDF 1.3 expander 4
     */
    @CanSynchronized
    static final
    class Synchronized<T> extends FinalSet<T> {
        /** <p>锁.</p> */
        private final Object LOCK;

        Synchronized() {
            super();
            LOCK = new Object();
        }

        @Override
        public
        ConstantsSet<T> put(T t) {
            synchronized ( LOCK ){
                /* 检查是否固化内容 */
                if (!isfinal)
                    // 添加缓存
                    set.add(t);
            }
            return this;
        }

        @Override
        public
        void finals() { finalSY(this, LOCK); }

        @Override
        public
        void forEach(Consumer<T> action) {
            synchronized ( LOCK ){
                super.forEach(action);
            }
        }
    }

    /**
     * @author fybug
     * @version 0.0.1
     * @since PDF 1.3 expander 4
     */
    @NoSynchronized
    static final
    class noSynchronized<T> extends FinalSet<T> {
        noSynchronized() { super(); }

        @Override
        @NotNull
        public
        ConstantsSet<T> put(@Nullable final T t) {
            /* 检查是否固化内容 */
            if (!isfinal)
                // 添加缓冲
                set.add(t);
            return this;
        }

        @Override
        public
        void finals() { finla(this); }
    }
}