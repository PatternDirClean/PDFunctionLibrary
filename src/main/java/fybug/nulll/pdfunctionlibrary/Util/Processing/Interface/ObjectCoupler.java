package fybug.nulll.pdfunctionlibrary.Util.Processing.Interface;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 对象转换器.
 * <p>
 * 用于在两个对象之间进行转换
 * 通常进行单方面转换
 *
 * @param <T> 要转换的类型
 * @param <U> 转换为该类型
 *
 * @author fybug
 * @version 0.0.1
 * @since Processing.Interface 0.0.1
 */
public
interface ObjectCoupler<U, T> {
    /** 在该方法进行对象转换. */
    @Nullable
    U conversionTo(@NotNull final T in);
}
