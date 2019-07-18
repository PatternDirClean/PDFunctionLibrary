package fybug.nulll.pdfunctionlibrary.lang;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.LinkedList;

import fybug.nulll.pdfunctionlibrary.Annotations.CanSynchronized;
import fybug.nulll.pdfunctionlibrary.Annotations.NoSynchronized;
import fybug.nulll.pdfunctionlibrary.Processing.CheckObjectTOOL;
import fybug.nulll.pdfunctionlibrary.Util.Processing.ArraryTOOL;

/**
 * <h2>集中释放工具.</h2>
 * <pre>
 * 该工具用于存放多个可释放对象，在关闭的时候可批量操作
 * 可根据需要使用工场方法获取并发或去并发类
 * 关闭后也可继续使用
 * </pre>
 *
 * @author fybug
 * @version 0.0.7
 * @see ConsistentField
 * @see AutoCloseable
 * @see MaybeSynchronized
 * @see Synchronized
 * @see CloseALL
 * @since PDF 1.2
 */
@SuppressWarnings( "all" )
public abstract
class CloseAll extends ConsistentField implements AutoCloseable, MaybeSynchronized {
    /** <p>保存要关闭的对象.</p> */
    @NotNull protected LinkedList<AutoCloseable> linkedList;

    /** <p>构造一个空的容器.</p> */
    protected
    CloseAll() {linkedList = new LinkedList<>();}

    /**
     * <p>构造容器并添加要集中释放的对象.</p>
     *
     * @param close 要释放的对象
     */
    protected
    CloseAll(@Nullable final AutoCloseable... close) {
        this();
        append(close);
    }

    @NoSynchronized
    @Override
    protected
    boolean consistent(@NotNull final Object o)
    {return CheckObjectTOOL.equalsFidle(linkedList, ((CloseAll) o).linkedList);}

    /**
     * <p>追加要关闭的对象.</p>
     *
     * @param close 追加的要关闭的对象
     *
     * @return this
     */
    @NotNull
    public abstract
    CloseAll append(@Nullable final AutoCloseable... close);

    /**
     * <p>放入要关闭的对象.</p>
     *
     * @param t 要追加的要关闭的对象
     *
     * @return 追加的对象
     *
     * @since PDF 1.3
     */
    @Nullable
    public
    <T extends AutoCloseable> T put(@Nullable final T t) {
        append(t);
        return t;
    }

    /** <p>关闭该类下的所有可关闭对象.</p> */
    public abstract
    void close();

    /*
     * 转化
     */

    /**
     * @return {@link CloseAll} 的并发处理对象
     *
     * @see Synchronized
     */
    @NotNull
    public
    CloseAll toSynchronized() {
        Cleanable.checkClose(Cleanable.errmessage, linkedList);

        /* 检查类型 */
        if (this instanceof Synchronized)
            return this;

        @NotNull final CloseAll c = new Synchronized();
        c.linkedList = this.linkedList;
        return c;
    }

    /**
     * @return {@link CloseAll} 的非并发处理对象
     *
     * @see CloseALL
     */
    @NotNull
    public
    CloseAll removerSynchronized() {
        Cleanable.checkClose(Cleanable.errmessage, linkedList);

        /* 检查类型 */
        if (this instanceof CloseALL)
            return this;

        @NotNull final CloseAll c = new CloseALL();
        c.linkedList = this.linkedList;
        return c;
    }

    /*
     * 工场方法
     */

    /**
     * <p>获取加入并发处理的 {@link CloseAll} 对象.</p>
     *
     * @return
     *
     * @see CloseAll
     * @see Synchronized
     * @since PDF 1.2 expander 2
     */
    @NotNull
    public static
    CloseAll getSynchronized() {return new Synchronized();}

    /**
     * <p>获取加入并发处理的 {@link CloseAll} 对象.</p>
     * <p>并放入初始值</p>
     *
     * @param close 初始值
     *
     * @return
     *
     * @see CloseAll
     * @see Synchronized
     * @since PDF 1.2 expander 2
     */
    @NotNull
    public static
    CloseAll getSynchronized(@Nullable final AutoCloseable... close)
    {return new Synchronized(close);}

    /**
     * <p>获取 {@link CloseAll} 对象.</p>
     *
     * @return
     *
     * @see CloseAll
     * @see CloseALL
     * @since PDF 1.2 expander 2
     */
    @NotNull
    public static
    CloseAll getCloseAll() {return new CloseALL();}

    /**
     * <p>获取 {@link CloseAll} 对象.</p>
     * <p>并放入初始值</p>
     *
     * @param close 初始值
     *
     * @return
     *
     * @see CloseAll
     * @see CloseALL
     * @since PDF 1.2 expander 2
     */
    @NotNull
    public static
    CloseAll getCloseAll(@Nullable final AutoCloseable... close)
    {return new CloseALL(close);}

    /**
     * <h2> {@link CloseAll} 的并发处理类.</h2>
     *
     * @author fybug
     * @version 0.0.2
     * @see CloseAll
     * @since PDF 1.2 expander 2
     */
    @CanSynchronized
    private static
    class Synchronized extends CloseAll {
        private final Object lock = new Object();

        public
        Synchronized() {}

        public
        Synchronized(AutoCloseable... close) {super(close);}

        @NotNull
        public
        CloseAll append(@Nullable final AutoCloseable... close) {
            Cleanable.checkClose(Cleanable.errmessage, linkedList);
            ArraryTOOL.forEach(close, (v) -> {
                // 仅锁住该部分，可在添加的同时进行关闭
                synchronized ( lock ){
                    linkedList.add(v);
                }
            });
            return this;
        }

        @Override
        public
        void close() {
            @NotNull AutoCloseable closeable;

            ch:
            while( true ){
                // 仅锁住该部分，在进行关闭的时候可进行添加
                synchronized ( lock ){
                    if ((closeable = linkedList.poll()) == null)
                        break ch;
                }

                try {
                    closeable.close();
                } catch ( Exception e ) {
                    // 无视
                }
            }
        }
    }

    /**
     * <h2>{@link CloseAll} 的非并发处理类.</h2>
     *
     * @author fybug
     * @version 0.0.2
     * @see CloseAll
     * @since PDF 1.2 expander 2
     */
    @NoSynchronized
    public static
    class CloseALL extends CloseAll {
        public
        CloseALL() {}

        public
        CloseALL(AutoCloseable... close) {super(close);}

        @NotNull
        public
        CloseAll append(@Nullable final AutoCloseable... close) {
            Cleanable.checkClose(Cleanable.errmessage, linkedList);
            ArraryTOOL.forEach(close, (v) -> linkedList.add(v));
            return this;
        }

        @Override
        public
        void close() {
            @NotNull AutoCloseable closeable;

            while( true ){
                if ((closeable = linkedList.poll()) == null)
                    break;

                try {
                    closeable.close();
                } catch ( Exception ignored ) {
                    // 无视
                }
            }
        }
    }
}