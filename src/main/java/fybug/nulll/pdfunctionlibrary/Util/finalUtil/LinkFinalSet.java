package fybug.nulll.pdfunctionlibrary.Util.finalUtil;
import com.sun.istack.internal.NotNull;

import java.util.LinkedHashSet;

import fybug.nulll.pdfunctionlibrary.Annotations.CanSynchronized;
import fybug.nulll.pdfunctionlibrary.Annotations.NoSynchronized;
/**
 * <h2>有序常量集合.</h2>
 * <pre>
 * 相比 {@link FinalSet} 内部维护的 {@link java.util.Set} 该类内部维护的是 {@link LinkedHashSet}
 * 可以保证其顺序
 * </pre>
 *
 * @author fybug
 * @version 0.0.1
 * @since PDF 1.3 expander 4
 */
@SuppressWarnings( "all" )
abstract
class LinkFinalSet<T> extends FinalSet<T> {
    LinkFinalSet() {
        super();
        set = new LinkedHashSet<>();
    }

    @NotNull
    @Override
    public
    LinkFinalSet<T> toSynchronized() {
        if (this instanceof Synchronized)
            return this;
        return (LinkFinalSet<T>) cl(this, new Synchronized());
    }

    @NotNull
    @Override
    public
    LinkFinalSet<T> removerSynchronized() {
        if (this instanceof noSynchronized)
            return this;
        return (LinkFinalSet<T>) cl(this, new noSynchronized());
    }

    /**
     * @author fybug
     * @version 0.0.1
     * @since PDF 1.3 expander 4
     */
    @CanSynchronized
    static
    class Synchronized<T> extends LinkFinalSet<T> {
        /** <p>锁.</p> */
        private final Object LOCK = new Object();

        Synchronized() { super(); }

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
    }

    /**
     * @author fybug
     * @version 0.0.1
     * @since PDF 1.3 expander 4
     */
    @NoSynchronized
    static
    class noSynchronized<T> extends LinkFinalSet<T> {
        noSynchronized() { super(); }

        @Override
        public
        ConstantsSet<T> put(T t) {
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