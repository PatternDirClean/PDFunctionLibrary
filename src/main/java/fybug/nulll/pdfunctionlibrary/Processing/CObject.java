package fybug.nulll.pdfunctionlibrary.Processing;
import com.sun.istack.internal.NotNull;
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
}
