package fybug.nulll.pdfunctionlibrary.Processing;
import com.sun.istack.internal.NotNull;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import static fybug.nulll.bdpos.BDPos.CLASS;
import static fybug.nulll.bdpos.BDPos.NAMEMARK;
import static fybug.nulll.bdpos.BDPos.RN;
/**
 * <h2>字符过滤工具.</h2>
 * <p>提供各种条件下的字符过滤</p>
 *
 * @author fybug
 * @version 0.0.7
 * @since PDF 1.3
 */
@SuppressWarnings( "all" )
public final
class FuckChar {
    /** <p>过滤表.</p> */
    @NotNull private static volatile SoftReference<HashMap<String, String>> map;

    @NotNull
    private static synchronized
    HashMap<String, String> getMap() {
        @NotNull HashMap m;

        if (map == null || (m = map.get()) == null) {
            map = new SoftReference<>(m = new HashMap() {{
                put(ENDMARK, "@000");
                put(ANDPR, "@001");
                put(GREATER, "@002");
                put(LESS, "@003");
                put(STR, "@004");
                put(NAME, "@005");
                put(ANNOTATION, "@006");
                put(ROD, "@007");
                put(PERCENTAGE, "@008");
                put(CLASS, "@009");
                put(RN, "@010");
                put(NAMEMARK, "@011");
                put("@", "@012");
                put(TABMAPVAL, "@013");
                put("/*", "@014");
                put("*/", "@015");
            }});
        }

        return m;
    }

    /**
     * <p>过滤 Sql 语句.</p>
     *
     * @param str 要过滤的数据
     *
     * @return 过滤后的数据
     */
    @NotNull
    public static
    String fuckSql(@NotNull final CharSequence s) {
        @NotNull String str = s.toString();
        @NotNull final HashMap<String, String> m = getMap();

        str = str.replaceAll("@", m.get("@"));
        str = str.replaceAll(ENDMARK, m.get(ENDMARK));
        str = str.replaceAll(ANDPR, m.get(ANDPR));
        str = str.replaceAll(GREATER, m.get(GREATER));
        str = str.replaceAll(LESS, m.get(LESS));
        str = str.replaceAll(STR, m.get(STR));
        str = str.replaceAll(NAME, m.get(NAME));
        str = str.replaceAll(ANNOTATION, m.get(ANNOTATION));
        str = str.replaceAll(ROD, m.get(ROD));
        str = str.replaceAll(PERCENTAGE, m.get(PERCENTAGE));
        str = str.replaceAll(CLASS, m.get(CLASS));
        str = str.replaceAll("/\\*", m.get("/*"));
        str = str.replaceAll("\\*/", m.get("*/"));

        return str;
    }

    /**
     * <p>恢复被过滤 Sql 语句.</p>
     *
     * @param str 要恢复的数据
     *
     * @return 恢复后的数据
     */
    @NotNull
    public static
    String refuckSql(@NotNull final CharSequence s) {
        @NotNull String str = s.toString();
        @NotNull final HashMap<String, String> m = getMap();

        str = str.replaceAll(m.get("*/"), "*/");
        str = str.replaceAll(m.get("/*"), "/*");
        str = str.replaceAll(m.get(CLASS), CLASS);
        str = str.replaceAll(m.get(PERCENTAGE), PERCENTAGE);
        str = str.replaceAll(m.get(ROD), ROD);
        str = str.replaceAll(m.get(ANNOTATION), ANNOTATION);
        str = str.replaceAll(m.get(NAME), NAME);
        str = str.replaceAll(m.get(STR), STR);
        str = str.replaceAll(m.get(LESS), LESS);
        str = str.replaceAll(m.get(GREATER), GREATER);
        str = str.replaceAll(m.get(ANDPR), ANDPR);
        str = str.replaceAll(m.get(ENDMARK), ENDMARK);
        str = str.replaceAll(m.get("@"), "@");

        return str;
    }

    /**
     * <p>过滤 TabMap 关键字.</p>
     *
     * @param s 要过滤的数据
     *
     * @return 过滤后的数据
     *
     * @since PDF 1.3 expander 2
     */
    @NotNull
    public static
    String fuckTabMap(@NotNull final CharSequence s) {
        @NotNull String str = s.toString();
        @NotNull final HashMap<String, String> m = getMap();

        str = str.replaceAll("@", m.get("@"));
        str = str.replaceAll(ANDPR, m.get(ANDPR));
        str = str.replaceAll(TABMAPVAL, m.get(TABMAPVAL));

        return str;
    }

    /**
     * <p>恢复被过滤的 TabMap 关键字.</p>
     *
     * @param s 要恢复的数据
     *
     * @return 恢复后的数据
     *
     * @since PDF 1.3 expander 2
     */
    @NotNull
    public static
    String refuckTabMap(@NotNull final CharSequence s) {
        @NotNull String str = s.toString();
        @NotNull final HashMap<String, String> m = getMap();

        str = str.replaceAll(m.get(TABMAPVAL), TABMAPVAL);
        str = str.replaceAll(m.get(ANDPR), ANDPR);
        str = str.replaceAll(m.get("@"), "@");

        return str;
    }

    /**
     * <p>过滤 BDPos 标识符.</p>
     *
     * @param s 要过滤的数据
     *
     * @return 过滤后的数据
     */
    @NotNull
    public static
    String fuckBDPos(@NotNull final CharSequence s) {
        @NotNull String str = s.toString();
        @NotNull final HashMap<String, String> m = getMap();

        str = str.replaceAll("@", "@012");
        str = str.replaceAll(ANDPR, m.get(ANDPR));
        str = str.replaceAll(CLASS, m.get(CLASS));
        str = str.replaceAll(RN, m.get(RN));
        str = str.replaceAll(NAMEMARK, m.get(NAMEMARK));

        return str;
    }

    /**
     * <p>恢复过滤的 BDPos 标识符.</p>
     *
     * @param s 要恢复的数据
     *
     * @return 恢复后的数据
     */
    @NotNull
    public static
    String refuckBDPos(@NotNull final CharSequence s) {
        @NotNull String str = s.toString();
        @NotNull final HashMap<String, String> m = getMap();

        str = str.replaceAll(m.get(NAMEMARK), NAMEMARK);
        str = str.replaceAll(m.get(RN), RN);
        str = str.replaceAll(m.get(CLASS), CLASS);
        str = str.replaceAll(m.get(ANDPR), ANDPR);
        str = str.replaceAll(m.get("@"), "@");

        return str;
    }

    @NotNull public static final String TABMAPVAL = "=";
    /* 过滤字段 */
    public final static String ENDMARK = ";", ANDPR = "&", GREATER = "<", LESS = ">", STR = "'",
            NAME = "`", ANNOTATION = "--", ROD = "/", PERCENTAGE = "%";
}