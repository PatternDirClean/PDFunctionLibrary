package fybug.nulll.pdfunctionlibrary.Util.finalUtil;
import com.sun.istack.internal.Nullable;

import fybug.nulll.pdfunctionlibrary.Processing.Interface.NeedInit;
/**
 * <h2>数据遍历接口.</h2>
 *
 * @param <T> 遍历的数据类型的内部类型
 *
 * @author fybug
 * @version 0.0.1
 * @since PDF 1.3 expander 4
 */
@SuppressWarnings( "all" )
public
interface Consumer<T> extends NeedInit {
    @Override
    default
    void init() throws Exception {}

    @Override
    default
    void destroy() throws Exception {}

    /**
     * <p>获取方法.</p>
     * <p>遍历过程中的数据都会传给此方法.</p>
     *
     * @param t ....
     */
    void get(@Nullable final T t);
}