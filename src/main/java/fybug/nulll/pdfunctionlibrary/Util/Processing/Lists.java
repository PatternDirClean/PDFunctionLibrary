package fybug.nulll.pdfunctionlibrary.Util.Processing;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import org.jetbrains.annotations.NonNls;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

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
class Lists {
    /** {@Hide} */
    @Deprecated
    private
    Lists() {}

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
     * <p>转化为字符串类型列表.</p>
     * <p>会调用每一个对象的 {@link Object#toString()} 方法<br/>
     * 如果遍历的对象为 <b>NULL<b> 则会转换为{@code null}</p>
     *
     * @param t 要转化的列表<br/>
     *          会被转化过程影响，因为是直接过滤该对象</p>
     *
     * @return 转化为字符串的列表，如果是空列表则直接返回
     *
     * @throws NullPointerException 参数为空
     * @see Object#toString()
     */
    @NotNull
    public static
    List<String> toString(@Nullable final List t) {
        if (t == null)
            throw new NullPointerException("检查到传入的参数为空");
        else if (t.size() == 0)
            return t;
        @NotNull //* 使用遍历器，保证不同数据对象的遍历速度 *//
                ListIterator iterator = t.listIterator();
        @Nullable String s;
        int i = 0;
        @Nullable Object o;
        /* 遍历所有输入 */
        while( iterator.hasNext() ){
            o = iterator.next();
            if (o == null || (s = o.toString()) == null)
                /* 转化为字符串后仍是 NULL */
                s = "";
            iterator.set(s);
        }
        return t;
    }

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
     * <p>转化为字符串列表并清除空项.</p>
     * <p>如果是{@link CanEmpty}的子类<br/>
     * 将会调用{@link CanEmpty#isNull()}和 {@link CanEmpty#isEmpty()}判断是否是空数据<br/>
     * 以及转化为字符串后仍是空字符串的数据</p>
     *
     * @param t 要转换的列表<br/>
     *          会被过滤过程影响，因为是直接过滤该对象</p>
     *
     * @return 转化后的列表，如果是空列表则直接返回
     *
     * @throws NullPointerException 参数为空
     * @see #toString(List)
     * @see #trim(List)
     */
    @NotNull
    public static
    List<String> toStringTrim(@Nullable final List t) {
        if (t == null) {
            throw new NullPointerException("检查到传入的参数为空");
        } else if (t.size() == 0)
            return t;
        @NotNull Iterator iterator = t.listIterator();
        @Nullable String s;
        boolean b;
        for ( @Nullable Object o; iterator.hasNext(); ){
            o = iterator.next();
            // 检查是否是空数据
            if (o == null) {
                iterator.remove();
                continue;
            }
            b = CanEmpty.checkNull(o);
            s = o.toString(); // 转化为字符串
            // 检查是否是空字符串
            b |= s == null || s.isEmpty() || (s = s.trim()).isEmpty();
            if (b)
                /* Fuck! */
                iterator.remove();
            else
                ((ListIterator) iterator).set(s);
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
    <T> List<T> append(@Nullable final List<T> ts, final List<T> append) {
        if (ts == null)
            return append;
        if (append.size() == 0)
            return ts;
        append.forEach(v -> {ts.add(v);});
        return ts;
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
    <T> List<T> append(@Nullable final List<T> ts, final T... append) {
        for ( int i = 0; i < append.length; i++ )
            ts.add(append[i]);
        return ts;
    }

    /**
     * <p>将列表中的元素转化为文本.</p>
     * <pre>
     * 将列表中的元素逐个使用 {@link String#valueOf(Object)} 转化
     * 并在每个元素转化后添加分割字符串
     * </pre>
     *
     * @param ls 要处理的列表
     * @param s  分割字符
     *
     * @return 生成的字符串
     *
     * @since PDF 1.3 expander 3
     */
    @NotNull
    @NonNls
    public static
    String getString(@NotNull final List ls, @NotNull final String s) {
        @NotNull final StringBuilder stringBuilder = new StringBuilder();

        if (ls instanceof RandomAccess)
            for ( int i = 0, lenght = ls.size(); i < lenght; i++ )
                stringBuilder.append(String.valueOf(ls.get(i))).append(s);
        else
            for ( Object l : ls )
                stringBuilder.append(String.valueOf(l)).append(s);

        stringBuilder.trimToSize();
        return stringBuilder.toString();
    }
}
