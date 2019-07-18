package fybug.nulll.pdfunctionlibrary.Util.finalUtil;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.io.Serializable;

import fybug.nulll.pdfunctionlibrary.lang.MaybeSynchronized;
/**
 * <h2>常量集合.</h2>
 * <pre>
 * 该接口实现的对象均作为可变的常量数据组
 * 通常该组常量是有同一个用处
 * 在 {@link #finals()} 后将不可增加数据
 * 且该集合不可进行数据删除和更改，在 {@link #finals()} 前亦只可增加数据
 *
 * 可改变并发状态
 * 可指定无序或有序
 * </pre>
 *
 * @param <T> 维护的常量的类型
 *
 * @author fybug
 * @version 0.0.1
 * @since PDF 1.3 expander 4
 */
//@SuppressWarnings( "all" )
public
interface ConstantsSet<T> extends Serializable, MaybeSynchronized {
    /**
     * <p>放入数据到常量集合中.</p>
     * <p>支持链式操作</p>
     *
     * @param t 常量
     *
     * @return this
     *
     * @see java.util.Set
     */
    @NotNull
    ConstantsSet<T> put(final @Nullable T t);

    /**
     * <p>固化内容.</p>
     * <p>在执行该函数后会无视所有更改</p>
     */
    void finals();

    /**
     * <p>遍历数据.</p>
     *
     * @param action 遍历数据用的接口
     *
     * @see Consumer
     */
    void forEach(@NotNull final Consumer<T> action);

    /** <p>获取集合对象.</p> */
    @NotNull
    static
    ConstantsSet<?> usrSet() { return new FinalSet.noSynchronized<>(); }

    /** <p>获取有序集合对象.</p> */
    @NotNull
    static
    ConstantsSet<?> usrLinkSet() { return new LinkFinalSet.noSynchronized<>(); }
}