package fybug.nulll.pdfunctionlibrary.Processing.Stop;
import com.sun.istack.internal.NotNull;

import java.io.Serializable;

import fybug.nulll.pdfunctionlibrary.Annotations.CanSynchronized;
/**
 * <h2>获取 {@link Stop} 单例.</h2>
 * <p>使用 {@link CallStop#INSTANCE#getInstance()} 获取</p>
 *
 * @author fybug
 * @version 0.0.2
 * @see Stop
 * @see #INSTANCE#getInstance()
 * @since PDF 1.1
 */
@CanSynchronized
public
enum CallStop implements Serializable {
    INSTANCE;
    // 单例
    @NotNull private final Stop stop;

    CallStop() {stop = new Stop();}

    /**
     * <p>获取 {@link Stop} 对象的单例.</p>
     *
     * @return Stop 单例
     */
    @NotNull
    public
    Stop getInstance() {return stop;}
}