package fybug.nulll.pdfunctionlibrary.lang;
import com.sun.istack.internal.NotNull;
/**
 * <h2>多层数据容器.</h2>
 * <p>实现该接口代表该容器可能 <b>逻辑为空</b><br/>
 * 并且可检查<br/>
 * {@link #checkNull(Object)}:检查空数据</p>
 *
 * @author fybug
 * @version 0.0.6
 * @see #checkNull(Object)
 * @see Cleanable
 * @since PDF 1.2
 */
public
interface CanEmpty extends Cleanable {
    /**
     * <p>检查是否未初始化容器.</p>
     * <p>表示该数据类型的内部存储引用为空</p>
     *
     * @return 容器是否初始化
     */
    boolean isNull();

    /**
     * <p>检查内部容器内数据是否为空.</p>
     * <p>表示该容器的内部存储引用不为空，但应将其判断为空</p>
     *
     * @return 数据是否为空
     */
    boolean isEmpty();

    /**
     * <p>检查空数据容器.</p>
     * <p>检查该对象是否是空数据容器</p>
     *
     * @param t1 要检查的数据
     *
     * @return 对象是逻辑为空或实际为空都返回 {@code true} ，如果不是该接口的对象则必定返回 {@code false}
     */
    static
    boolean checkNull(@NotNull final Object t1) {
        if (t1 instanceof CanEmpty)
            // 使用内部函数检查是否是空数据
            return ((CanEmpty) t1).isNull() || ((CanEmpty) t1).isEmpty();
        else
            return t1 == null;
    }
}