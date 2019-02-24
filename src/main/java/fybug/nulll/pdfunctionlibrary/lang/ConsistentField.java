package fybug.nulll.pdfunctionlibrary.lang;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import fybug.nulll.pdfunctionlibrary.Annotations.NoSynchronized;
import fybug.nulll.pdfunctionlibrary.Processing.CObject;
import fybug.nulll.pdfunctionlibrary.Processing.CheckObject;
/**
 * <h2>预检查对比对象.</h2>
 * <pre>
 * 该类在 {@link #equals(Object)} 方法中提供了对比对象的类型检查
 * 已检查 <b>是否空对象</b> <b>对象类型是否符合</b> <b>是否是同一个对象</b>
 * 仅需重写 {@link #consistent(Object)} 方法,在该方法中对比对象的字段即可
 * </pre>
 *
 * @author fybug
 * @version 0.0.5
 * @see Object#equals(Object)
 * @since PDF 1.2
 */
@SuppressWarnings( "all" )
@NoSynchronized
public abstract
class ConsistentField extends CObject {
    /** {@Hide} */
    protected
    ConsistentField() {}

    /**
     * <p>预检查对比对象.</p>
     * <p>后面交给 {@link #consistent(Object)} 检查字段</p>
     */
    @Override
    public final
    boolean equals(@Nullable final Object obj) {
        switch ( CheckObject.checkEquals(this, obj) ) {
            default:
                return false;
            case 1: // 同一个对象
                return true;
            case 2: // 同一个类的实例
                return consistent(obj);
        }
    }

    /**
     * <p>对比内部字段是否一致.</p>
     * <pre>
     * 用于检查其中的变量是否相同
     * 应在检查类型和空对象后调用
     * </pre>
     */
    protected abstract
    boolean consistent(@NotNull final Object obj);
}
