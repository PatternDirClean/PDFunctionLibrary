package fybug.nulll.pdfunctionlibrary.lang;
import com.sun.istack.internal.NotNull;

/**
 * <h2>多层数据容器.</h2>
 * <p>实现该接口代表该容器可能 <b>逻辑为空或可关闭</b><br/>
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
     * <p>检查容器内部引用是否丢失.</p>
     * <p>表示该数据类型的内部存储引用为空，用于检查是否未初始化或是否关闭</p>
     * <p>已关闭的容器内容存储引用通常也为空</p>
     *
     * @return boolean
     */
    boolean isNull();

    /**
     * <p>检查内部容器内数据是否为空.</p>
     * <p>表示该容器已经初始化，但没有数据传入</p>
     *
     * @return boolean
     */
    default
    boolean isEmpty() {return isNull();}

    /**
     * <p>检查对象是否为空.</p>
     * <p>判断该对象是否是空对象</p>
     * <p>如果对象为该类的实现则使用 {@link #isNull()} 和 {@link #isEmpty()} 判断</p>
     *
     * @param t1 要检查的数据
     *
     * @return 如果是空对象则返回 {@code true} 若是该类的实现则通过内部方法判断,如果不是空对象则返回 {@code false}
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