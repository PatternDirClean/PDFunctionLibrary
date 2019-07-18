package fybug.nulll.pdfunctionlibrary.Processing.Stop;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.io.Serializable;

import fybug.nulll.pdfunctionlibrary.Annotations.CanSynchronized;
import fybug.nulll.pdfunctionlibrary.Util.DataGrid;

/**
 * <p>中断.</p>
 * <pre>
 * 抛出该异常的时候代表无论是否运行下去结果都是一样
 * 用于中断函数运行,但不代表发生异常
 * 抛出该错误以强制中断函数，该错误应一直抛出直到模块对外接口或处理函数中。
 *
 * <b>请使用 {@link CallStop} 获取</b>
 * </pre>
 *
 * @author fybug
 * @version 0.0.6
 * @see MaybeStop
 * @see CallStop
 * @since PDF 1.1
 */
@SuppressWarnings( "all" )
@CanSynchronized
public
class Stop extends Error implements Serializable {
    private static final long serialVersionUID = -5863099734169161181L;

    Stop()
    { super("The error should be caught, indicating that the result is the same regardless of whether it is running or not. The interrupt function runs."); }

    /**
     * <p>运行可能会进行停止的操作.</p>
     * <p>使用接口进行异常处理</p>
     *
     * @param runnable 要运行的可能会中断的操作
     * @param fuck     中断处理
     *
     * @return 如果没有发生异常将会返回 {@code null} ，如果发生了将会返回一个 {@link DataGrid} 里面
     * 放入处理接口返回的东西
     *
     * @see StopFuck
     * @since PDF 1.3 expander 2
     */
    @CanSynchronized
    @Nullable
    public static
    DataGrid runMeberStop(@NotNull final Runnable runnable, @NotNull final StopFuck fuck) {
        try {
            runnable.run();
        } catch ( Stop e ) {
            return DataGrid.getDataGrid(fuck.stop());
        }

        return null;
    }
}