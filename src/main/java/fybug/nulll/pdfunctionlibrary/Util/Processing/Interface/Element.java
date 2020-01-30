package fybug.nulll.pdfunctionlibrary.Util.Processing.Interface;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

/**
 * <h2>元素操作单位.</h2>
 *
 * @author fybug
 * @version 0.0.1
 */
public
interface Element<T> {
    /**
     * 获取当前元素
     *
     * @return this element
     */
    @Nullable
    T get();

    /**
     * 删除当前元素
     * <p>
     * 删除后会将后续的元素往上移动
     */
    void delete();

    /**
     * 在当前位置的后面追加元素
     *
     * @param t 元素
     */
    void append(@Nullable T t);

    /**
     * 在当前位置的前面插入元素
     *
     * @param t 元素
     */
    void prepend(@Nullable T t);

    /**
     * 改变当前位置的元素
     *
     * @param t 元素
     */
    void set(@Nullable T t);

    /**
     * 移除当前位置的元素
     * <p>
     * 相当于 {@code set(null);}
     */
    void remove();

    /**
     * 移除符合的元素
     *
     * @param predicate 谓语
     */
    void removeIf(Predicate<T> predicate);

    /**
     * 删除符合的元素
     *
     * @param predicate 谓语
     */
    void deleteIf(Predicate<T> predicate);

    /**
     * 在合适的时候修改当前元素
     *
     * @param predicate 谓语
     */
    void setIf(@Nullable T t, Predicate<T> predicate);
}
