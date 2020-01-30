package fybug.nulll.pdfunctionlibrary.Util.Processing;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Objects;

import fybug.nulll.pdfunctionlibrary.Util.Processing.Interface.ObjectCoupler;

/**
 * <h2>数组相关算法类.</h2>
 *
 * @author fybug
 * @version 0.0.6
 * @since Processing 0.0.1
 */
final public
class CollectionTOOL {
    /** {@Hide} */
    @Deprecated
    private
    CollectionTOOL() {}

    /**
     * 修整集合.
     *
     * @param t 要修整的集合
     *
     * @return 传入 {@code null | size==0} 返回 {@code null}
     */
    @Nullable
    public static
    <T> Collection<T> unifiedCollection(@Nullable Collection<T> t)
    { return t == null || t.size() == 0 ? null : t; }

    /**
     * 清除集合中的空项.
     * <p>
     * 清除集合中的 {@code null}
     *
     * @param t 要检查的集合，会被过滤过程影响
     *
     * @return 处理后的集合
     */
    @Nullable
    public static
    <T> Collection<T> trim(@Nullable Collection<T> t) {
        if (t == null || t.size() == 0)
            // 不用过滤
            return t;

        /* 遍历 */
        t.removeIf(Objects::isNull);

        return t;
    }

    /**
     * 转化集合内部数据.
     *
     * @param t       要转化的集合
     * @param coupler 转化接口
     *
     * @return 处理后的集合
     *
     * @since CollectionTOOL 0.0.6
     */
    @Nullable
    public static
    Collection asOuther(@Nullable Collection t, ObjectCoupler<Object, Object> coupler) {
        if (t == null || t.size() == 0)
            return t;

        Object[] objects = t.toArray();
        t.clear();

        ArraryTOOL.forEach(objects, o -> t.add(coupler.conversionTo(o)));

        return t;
    }

    /**
     * 将新数据添加到旧数据后面.
     *
     * @param ts     要添加数据的对象
     * @param append 添加的数据
     */
    @NotNull
    public static
    void append(@NotNull Collection ts, Object append) {
        if (append == null)
            return;

        if (append.getClass().isArray()) {
            ArraryTOOL.forEachForNaviter(append, ts::add);
        } else if (append instanceof Collection) {
            ts.addAll((Collection<?>) append);
        } else {
            ts.add(append);
        }
    }

    // todo 操作，带锁操作
}
