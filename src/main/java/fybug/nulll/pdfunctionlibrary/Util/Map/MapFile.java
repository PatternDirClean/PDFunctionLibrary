package fybug.nulll.pdfunctionlibrary.Util.Map;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.Iterator;

import fybug.nulll.pdfunctionlibrary.Annotations.NoSynchronized;
import fybug.nulll.pdfunctionlibrary.Util.DataMap;
import fybug.nulll.pdfunctionlibrary.lang.CanEmpty;
/**
 * <h2>数据映射集.</h2>
 * <pre>
 * 可存放空值，可存放重复数据
 * 不可对内部数据进行全部清空以外的操作
 * 同时也是遍历器，可获取当前数据
 * 是用{@link #next()} 进入下一个数据
 * </pre>
 *
 * @param <K> 键类型
 * @param <V> 值类型
 *
 * @author fybug
 * @version 0.0.3
 * @see CanEmpty
 * @see Iterator
 * @see DataMap
 * @since PDF 1.2
 */
@SuppressWarnings( "all" )
@NoSynchronized
public
interface MapFile<K, V> extends CanEmpty {
    /**
     * <p>添加一个映射.</p>
     *
     * @param k 键
     * @param v 值
     *
     * @return this
     */
    @NotNull
    MapFile<K, V> add(@Nullable final K k, @Nullable final V v);

    /**
     * <p>获取当前数据.</p>
     *
     * @return 当前数据, 如果读取完成会返回 {@code NUll}
     */
    @Nullable
    DataMap<K, V> get();

    /**
     * <p>弹出第一个数据.</p>
     *
     * @return 第一个数据, 如果读取完成会返回 {@code NULL}
     */
    @Nullable
    DataMap<K, V> poll();

    /**
     * <p>清空数据.</p>
     *
     * @return this
     */
    @NotNull
    void clear();

    /** <p>内部数据长度.</p> */
    int size();

    /** <p>是否为空.</p> */
    boolean isEmpty();

    /**
     * <p>获取所有数据.</p>
     *
     * @return 映射数组
     */
    @NotNull
    DataMap<K, V>[] toArray();

    /**
     * <p>指向下一个数据.</p>
     *
     * @return this
     */
    @NotNull
    MapFile<K, V> next();

    /**
     * <p>重置指针.</p>
     *
     * @return this
     *
     * @since PDF 1.3 expander 2
     */
    @NotNull
    MapFile<K, V> restart();

    /** <p>创建一个使用数组的映射集.</p> */
    @NotNull
    static
    ArrayMap<?, ?> userArray()
    { return new ArrayMap(); }

    /** <p>创建一个使用链表的映射集.</p> */
    @NotNull
    static
    LinkMap<?, ?> userLink()
    { return new LinkMap(); }

    @NotNull
    Object clone();
}
