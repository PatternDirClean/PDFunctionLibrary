package fybug.nulll.pdfunctionlibrary.Processing;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import fybug.nulll.pdfunctionlibrary.Processing.Stop.CallStop;
import fybug.nulll.pdfunctionlibrary.Processing.Stop.MaybeStop;

/**
 * <h2>参数检查工具包.</h2>
 * <pre>
 * 提供对象检查和参数类型检查
 * </pre>
 *
 * @author fybug
 * @version 0.0.2
 * @since PDF 1.2
 */
@SuppressWarnings( "all" )
public final
class CheckObjectTOOL {
    /** {@Hide} */
    @Deprecated
    private
    CheckObjectTOOL() {}

    /**
     * <p>检查类型.</p>
     * <p>检查是否是指定类型的子类</p>
     *
     * @return 传入的对象
     */
    @NotNull
    @MaybeStop
    public static
    <T> T checkTyple(@Nullable final Object o, @NotNull final Class<T> tClass) {
        if (!tClass.isInstance(o))
            throw CallStop.INSTANCE.getInstance();
        return (T) o;
    }

    /**
     * <p>检查是否是空对象.</p>
     *
     * @return 传入的对象
     */
    @NotNull
    @MaybeStop
    public static
    <T> T checkNull(@Nullable final T o) {
        if (o == null)
            throw CallStop.INSTANCE.getInstance();
        return o;
    }

    /**
     * <p>检查对象是否相同.</p>
     *
     * @return {@code 1} 完全相同<br/>{@code 0} 类型相同<br/>{@code 2} 不相同
     */
    public static
    byte checkEquals(@NotNull final Object th, @Nullable final Object obj) {
        /* 检查地址 */
        if (obj == th)
            return 0b01;
        return (byte) (th.getClass().isInstance(obj) ? 0b0 : 0b10);
    }

    /** <p>对比两个参数是否相同.</p> */
    public static
    boolean equalsFidle(@Nullable final Object th, @Nullable final Object obj) {
        if (th == null)
            return obj == null;
        else {
            if (obj == null)
                return false;
            return th.equals(obj);
        }
    }
}
