package fybug.nulll.pdfunctionlibrary.Util.Processing;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import fybug.nulll.pdfunctionlibrary.lang.CanEmpty;
/**
 * <h2>数组相关算法类.</h2>
 *
 * @author fybug
 * @version 0.0.5
 * @since PDF 1.2
 */
@SuppressWarnings( "all" )
final public
class ListTOOL {
    /** {@Hide} */
    @Deprecated
    private
    ListTOOL() {}

    /*
     * UnifiedList
     */

    /**
     * <p>修整数组.</p>
     *
     * @param t 要修整的数组
     *
     * @return 如果对象是空对象或空数组则会返回{@code null}
     */
    @Nullable
    public static
    <T> List<T> unifiedList(@Nullable final List<T> t)
    { return t == null || t.size() == 0 ? null : t; }

    /**
     * <p>清除列表中的空项.</p>
     * <p>如果是 {@link CanEmpty} 的子类<br/>
     * 将会调用 {@link CanEmpty#isNull()} 和 {@link CanEmpty#isEmpty()} 判断是否是空数据</p>
     *
     * @param t 要检查的列表<br/>
     *          会被过滤过程影响，因为是直接过滤该对象
     *
     * @return 返回清除了空项的列表，如果是空列表则直接返回
     *
     * @throws NullPointerException 参数为空
     * @see CanEmpty
     */
    @NotNull
    public static
    <T extends Object> List<T> trim(@Nullable final List<T> t) {
        if (t == null) {
            throw new NullPointerException("检查到传入的参数为空");
        } else if (t.size() == 0)
            return t;
        @NotNull //* 使用遍历器，保证不同数据对象的遍历速度 *//
                Iterator<T> i = t.iterator();
        /* 遍历所有输入 */
        for ( @Nullable T o = i.next(); // 当前对象
                i.hasNext(); o = i.next() ){
            if (o == null || CanEmpty.checkNull(o))
                /* 数据为空 */
                i.remove();
        }
        return t;
    }

    /**
     * <p>将新数据添加到旧数据后面.</p>
     *
     * @param ts     要添加数据的对象
     * @param append 添加的数据
     *
     * @return 返回要添加数据的对象
     */
    @NotNull
    public static
    <T> List<T> append(@NotNull List<T> ts, T... append) {
        for ( int i = 0; i < append.length; i++ )
            ts.add(append[i]);
        return ts;
    }
}
