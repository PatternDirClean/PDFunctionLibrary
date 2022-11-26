package fybug.nulll.pdfunctionlibrary.Util.Processing;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.Random;

/**
 * <h2>字符串相关工具类.</h2>
 *
 * @author fybug
 * @version 0.0.4
 * @since Processing 0.0.1
 */
public final
class StringT {
    /**
     * 创建混合字符串
     * <p>
     * 产生指定长度的随机字符串<br/>
     * 字符串内容包含(a-z),(A-Z),(0-9)<br/><br/>
     * 使用 {@link Random#nextInt(int)} 生成每个随机字符，范围 i 为 0-62(不含62)<br/>
     * 字符的计算公式为 {@code i < 52 ? i < 26 ? i + 97 : i % 26 + 65 : i % 52 + 48}
     * <pre>公式说明
     * 字符段 52-61 为 0-9，公式为 i % 52 + 48
     * 字符段 0-25 为 a-z 公式为 i + 97
     * 字符段 26-51 为 A-Z 公式为 i % 26 + 65
     * </pre>
     *
     * @param len 字符串的长度
     *
     * @return 返回生成的字符串
     *
     * @since StringT 0.0.4
     */
    public static
    String createBlendString(int len) {
        StringBuilder code = new StringBuilder(len);
        // 随机工具
        var r = new Random();
        /* 生成随机字符串 */
        for ( int i = 0; i < len; ++i ){
            int temp = r.nextInt(62);
            char x = (char) (temp < 52 ? temp < 26 ? temp + 97 : temp % 26 + 65 : temp % 52 + 48);
            code.append(x);
        }
        String s = code.toString();

        // help gc
        r = null;
        code = null;
        return s;
    }

    /*--------------------------------------------------------------------------------------------*/

    /**
     * 检查字符串是否是空字符串
     * <p>
     * {@code null}、空字符串、只有空格的字符串 均被判定为空字符串，返回 {@code true}
     *
     * @return boolean 是否被判定为空字符串
     *
     * @see #stringIsEmpty(String, boolean)
     * @since StringT 0.0.3
     */
    public static
    boolean stringIsEmpty(@Nullable String s) { return stringIsEmpty(s, true); }

    /**
     * 检查字符串是否是空字符串
     * <p>
     * {@code null}、空字符串 会被判定为空字符串，返回 {@code true}
     *
     * @param s        要判断的字符串
     * @param canSpace 是否可空格，启用时 只有空格的字符串 也会被判定为空字符串并返回 {@code true}
     *
     * @return boolean 是否被判定为空字符串
     *
     * @since StringT 0.0.4
     */
    public static
    boolean stringIsEmpty(@Nullable String s, boolean canSpace) {
        if (s == null)
            return true;
        // 判断空格
        if (canSpace)
            s = s.trim();
        return s.length() == 0;
    }

    //---------------------------------------------

    /**
     * 缺省字符串过滤
     * <p>
     * {@code null}、空字符串、只有空格的字符串 均被判定为空字符串，将会返回缺省参数
     *
     * @param s   要检查的字符串
     * @param des 缺省情况下的字符串，该参数为 {@code null} 时会返回 {@code ""}
     *
     * @return String 判定为空返回 des 否则返回 s
     *
     * @since StringT 0.0.3
     */
    public static
    String nonEmptyElse(String s, String des) {
        // 检查是否为 null 或空字符串
        if (s == null || (s = s.trim()).length() == 0)
            return des == null ? "" : des;
        return s;
    }

    /*--------------------------------------------------------------------------------------------*/

    /**
     * 转化字符串
     * <p>
     * 传入 {@code null} 或最后转换结果为 {@code null} 将会返回 {@code ""}<br/>
     * 使用 {@link String#valueOf(Object)} 进行转换
     *
     * @param o 要转化为字符串的对象
     *
     * @return 转换后的字符串
     */
    public static
    String valueOf(@Nullable Object o) {
        if (o == null)
            return "";
        o = String.valueOf(o);
        if (o == null)
            return "";

        return (String) o;
    }

    /**
     * 返回在此字符串中出现指定字符串的次数
     * <p>
     * 通过 {@link String#indexOf(String, int)} 查找，并统计出现次数
     * <br/><br/>
     * 任意参数为空时均返回 {@code 0}<br/>
     * 判断标准为 {@link #stringIsEmpty(String, boolean)}，{@code canSpace} 参数为 {@code false}
     *
     * @param srcText  被查找的字符串
     * @param findText 要查找的字符串
     *
     * @return 出现次数
     */
    public static
    int getFindNmuber(@Nullable String srcText, @Nullable String findText) {
        // 没有可解析数据
        if (stringIsEmpty(srcText, false) || stringIsEmpty(findText, false))
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
     * 会忽略中途的空格，如果没有可以解析的字符串则会返回空数组
     *
     * @param s 要解析的字符
     *
     * @return 解析出来的字符串数组
     *
     * @since StringT 0.0.2
     */
    public static
    String[] passArray(@Nullable String s) {
        // 没数据
        if (s == null || s.length() == 0)
            return new String[0];

        int i; // 开始位置
        int end; // 结束位置

        // 没起始标识
        if ((i = s.indexOf('[')) == -1)
            return new String[0];
        else if ((end = s.indexOf(']')) == -1)
            // 没结束标识，定位到结尾
            end = s.length();

        // 字符缓存
        StringBuilder stringBuilder = new StringBuilder();
        // 数组缓存
        LinkedList<String> linkedList = new LinkedList<>();

        // 当前字符
        char c;
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

        // 生成数组并返回
        return linkedList.toArray(String[]::new);
    }

    /**
     * 转化字符串数组为字符串类型
     * <p>
     * 将数组生成为 {@code "[string,string]"} 样式的字符串，其中 {@code string} 为数组的值
     * <br/><br/>
     * 其中为空的值将被忽略，判断标准参考 {@link #stringIsEmpty(String)}，没有值可填充时返回 {@code "[]"}
     *
     * @param sarr 要转化的字符串数组
     *
     * @return 生成的字符串
     *
     * @since StringT 0.0.2
     */
    public static
    String ArrayToString(@Nullable String... sarr) {
        // 空数据
        if (sarr.length == 0)
            return "[]";

        // 字符缓冲区
        StringBuilder buff = new StringBuilder().append('[');

        for ( String s : sarr ){
            // 当前数据不符合规范
            if (stringIsEmpty(s))
                continue;

            // 拓展缓冲区空间
            buff.ensureCapacity(s.length() + 1);
            // 追加数据和分隔符 ","
            buff.append(s).append(',');
        }

        /* 处理多余分隔符 */
        int endindex = buff.length() - 1;
        if (buff.charAt(endindex) == ',')
            buff.setCharAt(endindex, ']');
        else
            buff.append(']');

        return buff.toString();
    }
}
