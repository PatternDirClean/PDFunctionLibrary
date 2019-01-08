package fybug.nulll.pdfunctionlibrary.Util.Processing;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
/**
 * <h2>字符串相关算法类.</h2>
 *
 * @author fybug
 * @version 0.0.1
 * @see String
 * @since PDF 1.2
 */
@SuppressWarnings( "all" )
public final
class Strings {
    /**
     * <p>获取字符串.</p>
     *
     * @param o 要获取其中字符串的对象
     *
     * @return 如果传入的 {@code null} 则返回的也是 {@code null}
     */
    @Nullable
    public static
    String getString(@Nullable final Object o) {
        if (o == null)
            return null;
        return o.toString();
    }

    /** <p>转化字符串.</p>
     * @param o 要转化为字符串的对象
     * @return 如果传入的是 {@code null} 则返回的是 {@code ""}*/
    @NotNull
    public static
    String valueOf(@Nullable final Object o) {
        if (o == null)
            return "";
        return o.toString();
    }

    /**
     * <p>返回在此字符串中出现指定字符处的次数.</p>
     *
     * @param srcText  被查找的字符串
     * @param findText 要查找的字符串
     *
     * @return 出现次数，如果传入的数据是空数据则返回{@code 0}
     */
    public static
    int getFindNmuber(@Nullable final String srcText, @Nullable final String findText) {
        if (srcText == null || srcText.isEmpty() || findText == null || findText.isEmpty())
            return 0;
        int count = 0;
        int index = 0;
        while( (index = srcText.indexOf(findText, index)) != -1 ){
            index = index + findText.length();
            count++;
        }
        return count;
    }
}
