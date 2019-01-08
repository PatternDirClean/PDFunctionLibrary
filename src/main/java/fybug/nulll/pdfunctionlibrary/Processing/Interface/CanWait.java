package fybug.nulll.pdfunctionlibrary.Processing.Interface;
import com.sun.istack.internal.Nullable;

import fybug.nulll.pdfunctionlibrary.Annotations.CanSynchronized;
/**
 * <h2>该对象需要等待.</h2>
 * <p>实现了该接口的对象可以直接调用 {@code waiting()} 方法使对象进入等待</p>
 * <p>已用同步锁包装</p>
 *
 * @author fybug
 * @version 0.0.3
 * @see #wait(long, int)
 * @since PDF 1.2
 */
@CanSynchronized
public
interface CanWait {
    /**
     * <p>让该对象进入等待.</p>
     * <p>如果输入的等待时间是 0 ，则是永久等待，需要唤醒</p>
     *
     * @param i    暂停时间
     * @param time 延迟时间
     */
    default
    void waiting(final long i, final int time) {
        try {
            synchronized ( this ){
                /* 永久暂停 */
                if (i == 0) {
                    // 暂停当前对象
                    this.wait();
                } else {
                    // 根据参数暂停当前对象
                    this.wait(i, time);
                }
            }

        } catch ( Exception e ) {
            waitExcepyion(e);
        }
    }

    /**
     * <p>等待异常事件.</p>
     *
     * @param i 异常
     */
    void waitExcepyion(@Nullable final Exception i);

    /**
     * <p>唤醒该对象的所有等待锁.</p>
     * <p>任何等待锁都会被唤醒，让他继续执行</p>
     */
    default
    void notifyAllNow() {
        synchronized ( this ){
            // 唤醒
            this.notifyAll();
        }
    }
}
