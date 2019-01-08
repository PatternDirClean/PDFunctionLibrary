package fybug.nulll.pdfunctionlibrary.Processing.Stop;
import com.sun.istack.internal.NotNull;
/**
 * <h2>中断处理接口.</h2>
 * <p>返回处理后的消息</p>
 *
 * @author fybug
 * @version 0.0.1
 * @see Stop#runMeberStop(Runnable, StopFuck)
 * @since PDF 1.3 expander 2
 */
@SuppressWarnings( "all" )
public
interface StopFuck {
    /**
     * <p>处理中断异常.</p>
     *
     * @return 处理后返回的对象
     */
    @NotNull
    Object stop();
}