package fybug.nulll.pdfunctionlibrary.Processing;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.lang.ref.SoftReference;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import fybug.nulll.pdfunctionlibrary.Util.Processing.MapTOOL;
/**
 * <h2>字符过滤工具.</h2>
 * <p>提供字符过滤方法和字符过滤规则</p>
 *
 * @author fybug
 * @version 0.0.8
 * @since PDF 1.3
 */
public final
class FuckCharTOOL {
    /** <p>过滤表.</p> */
    @NotNull private static volatile SoftReference<HashMap<String, String>> map;
    /**
     * <p>sql 过滤规则</p>
     *
     * @since PDF 1.4
     */
    @NotNull private static volatile SoftReference<String[]> sql;

    /**
     * <p>获取过滤数据表.</p>
     *
     * @return 返回过滤数据表
     */
    @NotNull
    private static synchronized
    HashMap<String, String> getMap() {
        @Nullable HashMap<String, String> maps;
        /* 检查是否有数据缓存 */
        if (map == null || (maps = map.get()) == null)
            // 生成数据映射
            map = new SoftReference<>(maps = new HashMap<String, String>() {{
                put("and", "@001");
                put("exec", "@002");
                put("execute", "@003");
                put("insert", "@004");
                put("select", "@005");
                put("delete", "@006");
                put("update", "@007");
                put("count", "@008");
                put("*", "@009");
                put("%", "@010");
                put("chr", "@011");
                put("mid", "@012");
                put("master", "@013");
                put("truncate", "@014");
                put("char", "@015");
                put("declare", "@016");
                put("sitename", "@017");
                put("net user", "@018");
                put("xp_cmdshell", "@019");
                put(";", "@020");
                put("or", "@021");
                put("-", "@022");
                put("+", "@023");
                put(",", "@024");
                put("like", "@025");
                put("create", "@026");
                put("drop", "@027");
                put("table", "@028");
                put("from", "@029");
                put("grant", "@030");
                put("use", "@031");
                put("group_concat", "@032");
                put("information_schema.columns", "@033");
                put("table_schema", "@034");
                put("union", "@035");
                put("where", "@036");
                put("order", "@037");
                put("by", "@038");
                put("/", "@039");
                put("#", "@040");
            }});

        return maps;
    }

    /**
     * <p>获取 sql 语句过滤规则.</p>
     *
     * @return 返回过滤规则数组
     */
    @NotNull
    public static synchronized
    String[] FUCKCHAR_SQL() {
        @Nullable String[] maps;
        /* 检查是否有缓存数据 */
        if (sql == null || (maps = sql.get()) == null) {
            // 重新缓存数据
            sql = new SoftReference<>(maps = new String[]{"and", "exec", "execute", "insert",
                    "select", "delete", "update", "count", "*", "%", "chr", "mid", "master",
                    "truncate", "char", "declare", "sitename", "net", "xp_cmdshell", ";", "or", "-",
                    "+", ",", "like", "create", "drop", "table", "from", "grant", "use",
                    "group_concat", "information_schema", "table_schema", "union", "where", "order",
                    "by", "/", "#"});
        }

        return maps;
    }

    /**
     * <p>过滤字符.</p>
     * <p>根据传入的规则进行过滤</p>
     *
     * @param s   要过滤的字符串
     * @param arr 字符过滤规则
     *
     * @return 过滤后的字符串
     */
    @NotNull
    public static
    String fuckChar(@NotNull final String s, @NotNull final String[] arr) {
        return replaceChar(s,
                           // 取出需要的数据
                           MapTOOL.unionMap(getMap(), Arrays.asList(arr)));
    }

    /**
     * <p>恢复过滤的字符.</p>
     * <p>根据过滤时用的规则进行恢复</p>
     *
     * @param s   要恢复的字符串
     * @param arr 过滤时用的规则
     *
     * @return 恢复后的字符串
     */
    @NotNull
    public static
    String refuckChar(@NotNull final String s, @NotNull final String[] arr) {
        return replaceChar(s,
                           MapTOOL.MapKeyToValue(MapTOOL.unionMap(getMap(), Arrays.asList(arr))));
    }

    /** <p>根据规则替换字符.</p> */
    private static
    String replaceChar(String s, Map<String, String> arr) {
        s = s.replaceAll("@", "@000");
        for ( Map.Entry<String, String> sarr : arr.entrySet() )
            s = s.replaceAll(sarr.getKey(), sarr.getValue());

        return s;
    }
}