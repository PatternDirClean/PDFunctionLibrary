package fybug.nulll.pdfunctionlibrary.Processing;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

import fybug.nulll.pdfunctionlibrary.Processing.Stop.CallStop;
import fybug.nulll.pdfunctionlibrary.Processing.Stop.MaybeStop;

import static fybug.nulll.pdfunctionlibrary.Processing.CheckObject.checkNull;
/**
 * <h2>容器检查工具包.</h2>
 * <pre>
 * 提供容器类的内容与合法性检查
 * </pre>
 *
 * @author fybug
 * @version 0.0.3
 * @since PDF 1.2
 */
@SuppressWarnings( "all" )
public final
class CheckUtil {
    /** {@Hide} */
    @Deprecated
    private
    CheckUtil() {}

    /**
     * <p>检查原子变量是否为空.</p>
     *
     * @return 该原子变量中的内容
     */
    @MaybeStop
    @NotNull
    public static
    <T> T checkAtomicReference(@Nullable final AtomicReference<T> r) {
        @Nullable T t = checkNull(r).get();
        if (t == null)
            throw CallStop.INSTANCE.getInstance();
        return t;
    }

    /**
     * <p>检查数组格是否为空.</p>
     *
     * @return 该数组格中的内容
     */
    @MaybeStop
    @NotNull
    public static
    <T> T checkArrayTable(@Nullable final T[] ts) {
        checkNull(ts);
        if (ts.length < 1)
            throw CallStop.INSTANCE.getInstance();
        return checkNull(ts[0]);
    }

    /** <p>对比数组格.</p> */
    public static
    <T> boolean equalsArrayTable(@Nullable final T[] th, @Nullable final T[] obj) {
        if (th == null)
            return obj == null;
        else if (th[0] == null)
            return obj[0] == null;
        else {
            if (obj == null || obj[0] == null)
                return false;
            return th[0].equals(obj[0]);
        }
    }

    /** <p>对比数组格.</p> */
    public static
    boolean equalsArrayTable(@Nullable final int[] th, @Nullable final int[] obj) {
        if (th == null)
            return obj == null;
        else {
            if (obj == null)
                return false;
            return th[0] == obj[0];
        }
    }

    /** <p>对比数组.</p> */
    public static
    <T> boolean equalsArrays(@Nullable final T[] th, @Nullable final T[] obj) {
        if (th == null)
            return obj == null;
        else {
            if (obj == null)
                return false;
            return Arrays.equals(th, obj);
        }
    }
}
