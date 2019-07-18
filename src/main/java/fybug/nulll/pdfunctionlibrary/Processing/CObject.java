package fybug.nulll.pdfunctionlibrary.Processing;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.lang.reflect.Array;

import fybug.nulll.pdfunctionlibrary.Annotations.CanSynchronized;
/**
 * <h2>可克隆对象.</h2>
 * <p>暴露{@link Object} 的克隆方法</p>
 *
 * @author fybug
 * @version 0.0.1
 * @since PDF 1.3 expander 1
 */
public
class CObject implements Cloneable {
    @Override
    @NotNull
    public
    Object clone() throws CloneNotSupportedException { return super.clone(); }

    /**
     * <p>克隆可能为数组的字段.</p>
     *
     * @param t 要检查并克隆的对象
     *
     * @return 如为数组，则返回克隆的数组，不是则返回该对象
     */
    @NotNull
    public static
    <T> T cloneArrayField(@NotNull T t) {
        if (t == null)
            return null;

        @NotNull final Class<?> tClass = t.getClass();

        if (tClass.isArray()) {
            // 克隆数组
            final int lenght = Array.getLength(t); // 数组长度
            @NotNull Object t1 = Array.newInstance(tClass.getComponentType(), lenght);
            System.arraycopy(t, 0, t1, 0, lenght);
            t = (T) t1;
        }

        return t;
    }

    /** <p>克隆字段.</p> */
    @NotNull
    @CanSynchronized
    public static
    <T> T cloneField(@Nullable T t) {
        if (t != null)
            t = cloneArrayField(t);

        if (t instanceof CObject) {
            try {
                t = (T) ((CObject) t).clone();
            } catch ( CloneNotSupportedException e ) {
                // fuck
            }
        }

        return t;
    }
}
