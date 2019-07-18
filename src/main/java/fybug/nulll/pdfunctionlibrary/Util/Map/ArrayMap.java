package fybug.nulll.pdfunctionlibrary.Util.Map;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.ArrayList;

import fybug.nulll.pdfunctionlibrary.Annotations.NoSynchronized;
import fybug.nulll.pdfunctionlibrary.Util.DataMap;

/**
 * <p>使用了数组实现的映射集.</p>
 *
 * @author fybug
 * @version 0.0.3
 * @see ArrayList
 * @see DataMap
 * @see MapFile
 * @since PDF 1.2
 */
@SuppressWarnings( "all" )
@NoSynchronized
public
class ArrayMap<K, V> extends ArrayList<DataMap<K, V>> implements MapFile<K, V> {
    protected int mark = 0;

    @Override
    @NotNull
    public
    ArrayMap<K, V> add(@Nullable final K k, @Nullable final V v) {
        add(DataMap.getDataMap(k, v));
        return this;
    }

    @Override
    public @Nullable
    DataMap<K, V> get() {
        if (mark >= size())
            return null;
        return super.get(mark);
    }

    @Override
    public @Nullable
    DataMap<K, V> poll() {
        if (size() == 0)
            return null;

        if (mark > 0)
            mark--;

        DataMap<K, V> f = super.get(0);
        remove(0);
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
    ArrayMap<K, V> next() {
        if (mark <= size())
            mark++;
        return this;
    }

    @NotNull
    @Override
    public
    MapFile<K, V> restart() {
        mark = 0;
        return this;
    }

    @Override
    public
    boolean isEmpty() { return size() == 0; }
}
