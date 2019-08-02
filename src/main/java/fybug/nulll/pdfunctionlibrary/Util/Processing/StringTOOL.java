package fybug.nulll.pdfunctionlibrary.Util.Processing;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.LinkedList;
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
class StringTOOL {
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
        return String.valueOf(o);
    }

    /**
     * <p>转化字符串.</p>
     *
     * @param o 要转化为字符串的对象
     *
     * @return 如果传入的是 {@code null} 则返回的是 {@code ""}
     */
    @NotNull
    public static
    String valueOf(@Nullable final Object o) {
        if (o == null)
            return "";
        return String.valueOf(o);
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

    /**
     * <p>解析字符数组.</p>
     * <pre>
     * 解析样式为 [s,d,a,f,g,e,] 的字符串为字符串数组
     * </pre>
     *
     * @param s 要解析的字符
     *
     * @return 如果没有可以解析的字符串则会返回空数组
     *
     * @throws NullPointerException 传入空对象
     */
    @Nullable
    public static
    String[] passArray(String s) {
        int len = s.length(); // 长度
        /* 检查长度 */
        if (len == 0)
            return new String[0];

        int i = s.indexOf('['); // 开始位置
        /* 检查是否有开始符 */
        if (i == -1)
            return new String[0];
        /* 检查结尾 */
        if ((len = s.indexOf(']')) == -1)
            len = s.length();

        StringBuilder stringBuilder = new StringBuilder(); // 字符缓存
        LinkedList<String> linkedList = new LinkedList<>(); // 数组缓存
        char c; // 当前字符
        /* 解析字符 */
        for ( ; i < len; ){
            c = s.charAt(i++);
            /* 语法树解析 */
            switch ( c ) {
                /* 空格丢弃 */
                case ' ':
                    break;
                /* 分割符 */
                case ',':
                    /* 检查是否有字符 */
                    if (stringBuilder.length() != 0)
                        linkedList.add(stringBuilder.toString());
                    // 清除
                    stringBuilder.setLength(0);
                    break;
                default:
                    stringBuilder.append(c);
            }
        }
        /* 保存最后的数据 */
        if (stringBuilder.length() != 0)
            linkedList.add(stringBuilder.toString());
        stringBuilder = null;

        String[] retu = new String[linkedList.size()];
        linkedList.toArray(retu);

        return retu;
    }
}
