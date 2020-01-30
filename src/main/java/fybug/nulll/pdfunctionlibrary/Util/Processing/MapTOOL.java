package fybug.nulll.pdfunctionlibrary.Util.Processing;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
/**
 * <p>映射表相关算法类.</p>
 *
 * @author fybug
 * @version 0.0.1
 * @since PDF 1.4
 */
public final
class MapTOOL {
    @Deprecated
    private
    MapTOOL() {}

    /**
     * <p>交集映射键.</p>
     * <p>去除除指定键以外的键值</p>
     *
     * @param maps 要被处理的映射集
     * @param to   要取出的键的集合
     *
     * @return param maps
     */
    @NotNull
    public static
    <K, V, T extends Map<K, V>> Map<K, V> unionMap(@NotNull final T maps,
            @NotNull final Collection<K> to)
    {
        /* 判断是否为空 */
        if (maps.isEmpty() || to.isEmpty())
            return maps;
        HashMap<K, V> map = new HashMap<>();
        for ( K v : to ){
            if (maps.containsKey(v))
                map.put(v, maps.get(v));
        }
        return map;
    }

    /**
     * <p>映射表键值翻转.</p>
     *
     * @param maps 要翻转的映射表
     *
     * @return param maps
     */
    @NotNull
    public static
    <T extends Map> T MapKeyToValue(@NotNull final T maps) {
        if (maps.isEmpty())
            return maps;
        HashMap map = new HashMap(maps.size(), 1.0f);
        for ( Object es : maps.keySet() ){
            map.put(maps.get(es), es);
        }
        maps.clear();
        maps.putAll(map);
        map.clear();
        return maps;
    }

    /**
     * <p>映射表填充默认值.</p>
     * <p>如果表中没有对应的键，将会将默认值填充进去</p>
     *
     * @param maps 要填充的映射表
     * @param defa 要填充的默认值
     *
     * @return param maps
     */
    @NotNull
    public static
    <T extends Map> T MapFullDefault(@NotNull final T maps, @NotNull final T defa) {
        for ( Object es : defa.keySet() ){
            if (!maps.containsKey(es))
                maps.put(es, defa.get(es));
        }

        return maps;
    }

    /**
     * <p>检查映射中是否有指定的键.</p>
     * <pre>
     * 检查映射中是否有传入的指定键集合中的所有内容
     * 在检查前会使用默认值填充空键
     * </pre>
     *
     * @param maps  要检查的映射
     * @param keys  检查的键的集合
     * @param defau 检查钱填充的默认值
     *
     * @return 是否全都有
     */
    public static
    boolean MapCheck(@NotNull final Map<?, ?> maps, @NotNull final Collection<?> keys,
            @Nullable final Map<?, ?> defau)
    {
        MapFullDefault(maps, defau);
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        // 遍历要检查的键
        keys.forEach(v -> {
            /* 检查是否有该键 */
            if (!maps.containsKey(v))
                atomicBoolean.set(false);
        });

        return atomicBoolean.get();
    }

    /**
     * <p>删除映射中指定的键.</p>
     *
     * @param maps 要删除的映射
     * @param keys 要删除的键的集合
     *
     * @return param maps
     */
    @NotNull
    public static
    <T extends Map<?, ?>> T MapRremove(@NotNull final T maps, @NotNull final Collection<?> keys) {
        keys.forEach(v -> {
            maps.remove(v);
        });

        return maps;
    }
}