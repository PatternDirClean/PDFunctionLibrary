package fybug.nulll.pdfunctionlibrary.Processing.Interface;
import com.sun.istack.internal.NotNull;

import fybug.nulll.pdfunctionlibrary.Annotations.CanSynchronized;

/**
 * <h2>该对象需要进行休眠.</h2>
 * <p>实现了该接口的对象可以直接调用 {@code sleep()} 方法将当前线程休眠.</p>
 *
 * @author fybug
 * @version 0.0.2
 * @see Thread#sleep(long, int)
 * @since PDF 1.2
 */
@CanSynchronized
public
interface CanSleep {
    /**
     * <p>休眠当前所运行在的线程.</p>
     * <p>将当前调用该函数的线程休眠<br/>
     * 且可指定休眠事件和延后时间</p>
     *
     * @param i    休眠时间(毫秒)
     * @param time 延迟时间(毫秒)
     *
     * @see Thread#sleep(long, int)
     */
    default
    void sleep(final long i, final int time) {
        try {
            // 根据参数进行休眠
            Thread.sleep(i, time);
        } catch ( Exception e ) {
            sleepExcepyion(e);
        }
    }

    /**
     * <p>休眠异常事件.</p>
     *
     * @param i 休眠中出现的异常
     */
    void sleepExcepyion(@NotNull final Exception i);
}
