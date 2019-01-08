package fybug.nulll.pdfunctionlibrary.Util.Map;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.LinkedList;

import fybug.nulll.pdfunctionlibrary.Annotations.NoSynchronized;
import fybug.nulll.pdfunctionlibrary.Util.DataMap;
/**
 * <p>使用了链表实现的映射集.</p>
 *
 * @author fybug
 * @version 0.0.3
 * @see LinkedList
 * @see DataMap
 * @see MapFile
 * @since PDF 1.2
 */
@SuppressWarnings( "all" )
@NoSynchronized
public
class LinkMap<K, V> extends LinkedList<DataMap<K, V>> implements MapFile<K, V> {
    protected int mark = 0;

    @Override
    protected
    void finalize() throws Throwable {
        super.finalize();
        close();
    }

    @Override
    @NotNull
    public
    LinkMap<K, V> add(@Nullable final K k, @Nullable final V v) {
        add(DataMap.getDataMap(k, v));
        return this;
    }

    @Override
    @Nullable
    public
    DataMap<K, V> get() {
        if (mark < size())
            return super.get(mark);
        return null;
    }

    @Override
    @Nullable
    public
    DataMap<K, V> poll() {
        if (size() == 0)
            return null;

        if (mark > 0)
            mark--;

        DataMap<K, V> f = super.poll();
        return f;
    }

    @NotNull
    @Override
    public
    DataMap<K, V>[] toArray()
    { return super.toArray(new DataMap[size()]); }

    @Override
    @NotNull
    public
    LinkMap<K, V> next() {
        if (mark <= size())
            mark++;
        return this;
    }

    @Override
    @NotNull
    public
    MapFile<K, V> restart() {
        mark = 0;
        return this;
    }

    @Override
    public
    boolean isEmpty() { return size() == 0; }

    @Override
    public
    boolean isNull() { return isEmpty(); }

    @Override
    public
    void clean() {
        clear();
        mark = 0;
    }

    @Override
    public
    void free() { clean(); }

    @Override
    public
    void close() { free(); }
}
