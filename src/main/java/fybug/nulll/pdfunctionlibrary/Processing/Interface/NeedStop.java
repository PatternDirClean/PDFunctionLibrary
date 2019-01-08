package fybug.nulll.pdfunctionlibrary.Processing.Interface;
import com.sun.istack.internal.NotNull;

import fybug.nulll.pdfunctionlibrary.Annotations.CanSynchronized;
/**
 * <h2>实现该类接口代表该类明确需要 休眠/等待.</h2>
 * <p>标识当前对象在运行时需要 进行休眠/等待</p>
 *
 * @author fybug
 * @version 0.0.6
 * @see CanSleep
 * @see CanWait
 * @since PDF 1.2
 */
@CanSynchronized
public
interface NeedStop extends CanSleep, CanWait {
    @Override
    default
    void sleepExcepyion(@NotNull final Exception i) {}

    @Override
    default
    void waitExcepyion(@NotNull final Exception i) {}
}
