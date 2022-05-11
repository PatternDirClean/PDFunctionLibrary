package fybug.nulll.pdfunctionlibrary.Util.Processing;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;

import static fybug.nulll.pdfunctionlibrary.Util.Processing.ArraryTOOL.forEach;

/**
 * <h2>字符串相关工具类.</h2>
 *
 * @author fybug
 * @version 0.0.3
 * @since Processing 0.0.1
 */
public final
class StringT {
    /**
     * 检查字符串是否是空字符串
     * <p>
     * 包含 {@code null}、{@code "   "}
     *
     * @return boolean
     *
     * @since StringT 0.0.3
     */
    public static
    boolean stringIsEmpty(@Nullable String s) {
        if (s == null)
            return true;
        s = s.trim();
        return s.isEmpty();
    }

    /**
     * 缺省字符串过滤
     * <p>
     * 包含 {@code null}、{@code ""}
     *
     * @param s   要检查的字符串
     * @param des 缺省情况下的字符串
     *
     * @return String
     *
     * @since StringT 0.0.3
     */
    public static
    String nonEmptyElse(String s, String des) {
        // 检查是否为 null 或空字符串
        if (s == null || "".equals(s = s.trim()))
            return des == null ? "" : des;
        return s;
    }

    /**
     * 转化字符串.
     *
     * @param o 要转化为字符串的对象
     *
     * @return 传入 {@code null} 返回 {@code ""},转换后为 {@code null} 也返回 {@code ""}
     */
    @NotNull
    public static
    String valueOf(@Nullable Object o) {
        if (o == null)
            return "";

        String s = String.valueOf(o);

        if (s == null)
            return "";

        return s;
    }

    /**
     * 返回在此字符串中出现指定字符串的次数.
     *
     * @param srcText  被查找的字符串
     * @param findText 要查找的字符串
     *
     * @return 出现次数，如果传入的数据是空数据则返回{@code 0}
     */
    public static
    int getFindNmuber(@Nullable String srcText, @Nullable String findText) {
        if (srcText == null || srcText.isEmpty() || findText == null || findText.isEmpty())
            // 没有可解析数据
            return 0;

        int count = 0; // 总次数
        int index = 0; // 当前位置

        /* 搜索并统计 */
        while( (index = srcText.indexOf(findText, index)) != -1 ){
            index = index + findText.length();
            count++;
        }

        return count;
    }

    /*--------------------------------------------------------------------------------------------*/

    /**
     * 解析字符数组.
     * <p>
     * 解析样式为 [s,d,a,f,g,e,] 的字符串为字符串数组<br/>
     * 结束符 ']' 可以没有，但是开始符 '[' 必须有<br/>
     * 会忽略中途的空格
     *
     * @param s 要解析的字符
     *
     * @return 如果没有可以解析的字符串则会返回空数组
     *
     * @throws NullPointerException 传入空对象
     * @since StringT 0.0.2
     */
    @Nullable
    public static
    String[] passArray(@Nullable String s) {
        if (s.length() == 0)
            // 没数据
            return new String[0];

        int i; // 开始位置
        int end; // 结束位置

        if ((i = s.indexOf('[')) == -1)
            // 没起始标识
            return new String[0];
        else if ((end = s.indexOf(']')) == -1)
            // 没结束标识，定位到结尾
            end = s.length();

        StringBuilder stringBuilder = new StringBuilder(); // 字符缓存
        LinkedList<String> linkedList = new LinkedList<>(); // 数组缓存

        char c; // 当前字符
        /* 解析字符 */
        for ( ++i; i < end; ){
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

                    // 重置
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

        /* 生成数组 */
        String[] retu = new String[linkedList.size()];
        linkedList.toArray(retu);

        return retu;
    }

    /**
     * 转化字符串数组为字符串类型
     *
     * @param sarr 要转化的字符串数组，可传入 {@code null | ""} ，会被忽视
     *
     * @return 以 {@code "[string,string]"} 方式返回
     *
     * @since StringT 0.0.2
     */
    @NotNull
    @NonNls
    public static
    String ArrayToString(@Nullable String... sarr) {
        if (sarr.length == 0)
            // 空数据
            return "[]";

        StringBuilder buff = new StringBuilder("["); // 缓冲区

        /* 填充数据到缓冲区 */
        forEach(sarr, v -> {
            if (stringIsEmpty(v))
                // 当前数据不符合规范
                return;

            buff.ensureCapacity(v.length() + 1);
            // 追加数据和分隔符 ","
            buff.append(v).append(',');
        });

        /* 处理多余分隔符 */
        int endindex = buff.length() - 1;
        if (buff.charAt(endindex) == ',')
            buff.setCharAt(endindex, ']');
        else
            buff.append(']');

        return buff.toString();
    }
}
