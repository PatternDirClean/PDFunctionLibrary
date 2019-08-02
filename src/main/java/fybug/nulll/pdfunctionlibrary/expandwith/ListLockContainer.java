package fybug.nulll.pdfunctionlibrary.expandwith;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import org.jetbrains.annotations.Nls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Predicate;

import fybug.nulll.pdfunctionlibrary.Processing.CheckObjectTOOL;
import fybug.nulll.pdfunctionlibrary.Processing.Stop.CallStop;
import fybug.nulll.pdfunctionlibrary.Processing.Stop.MaybeStop;
import fybug.nulll.pdfunctionlibrary.Processing.Stop.Stop;
import fybug.nulll.pdfunctionlibrary.Util.Processing.ArraryTOOL;
import fybug.nulll.pdfunctionlibrary.Util.Processing.Interface.ForEachList;
import fybug.nulll.pdfunctionlibrary.lang.CanEmpty;

import static fybug.nulll.pdfunctionlibrary.Util.Processing.ArraryTOOL.inArray;
import static fybug.nulll.pdfunctionlibrary.Util.Processing.ArraryTOOL.trim;

/**
 * <h2>带锁的列表容器.</h2>
 * <pre>
 * 内部使用列表存放数据和处理，提供遍历接口并使用 {@link Lock} 进行并发处理
 * 每一个列表都需要一个名称，并且每一个表都有一个 {@link ReentrantReadWriteLock} 控制
 * 全局也使用 {@link ReentrantReadWriteLock} 控制
 * 内部提供方法可快捷的使用锁
 * </pre>
 *
 * @author fybug
 * @version 0.0.1
 */
public abstract
class ListLockContainer<T extends ListLockContainer> implements CanEmpty {
    /** <p>是否关闭.</p> */
    protected volatile boolean close = false;
    /** <p>容器锁.</p> */
    @NotNull private final ReentrantReadWriteLock LOCK = new ReentrantReadWriteLock();
    /** <p>列表锁容器.</p> */
    @NotNull private final Map<String, ReentrantReadWriteLock> INTERFACE_LOCK = new HashMap<>();
    /** <p>列表容器.</p> */
    @NotNull private final Map<String, List<?>> INTERFACE = new HashMap<>();

    protected
    ListLockContainer() {}

    /**
     * <p>生成存储列表.</p>
     * <p>使用名称生成 {@link List} 并生成对应的 {@link ReentrantReadWriteLock}</p>
     *
     * @param name 列表名称
     *
     * @throws Stop 被关闭
     */
    @MaybeStop
    protected final
    void pushInterface(final @Nls @NotNull String name) {
        MapWriteLock();
        try {
            // 生成列表和锁
            INTERFACE.put(name, new ArrayList<>());
            INTERFACE_LOCK.put(name, new ReentrantReadWriteLock());
        } finally {
            MapWriteUn();
        }
    }

    /**
     * <p>添加数据到列表中.</p>
     *
     * @param listname 列表名称
     * @param o        数据
     *
     * @return this
     *
     * @throws Stop 没有该列表或被关闭
     */
    @NotNull
    @MaybeStop
    public
    <V> T addInterface(final @Nls @NotNull String listname, @Nullable V... o) {
        /* 检查数据 */
        if ((o = trim(o)).length == 0)
            return (T) this;

        List<V> li = (List<V>) writeInterface(listname);
        ArraryTOOL.forEach(o, li::add);
        // 解除锁定
        InterfaceWriteUn(listname);

        return (T) this;
    }

    /**
     * <p>从列表中移除指定的数据.</p>
     * <p>一个一个对比的方式进行移除，效率较低。当 {@code equals} 返回 {@code true} 时才会删除</p>
     *
     * @param listname 列表名称
     * @param o        要移除的数据
     *
     * @return this
     *
     * @throws Stop 没有该列表或被关闭
     */
    @NotNull
    @MaybeStop
    public
    <V> T delInterface(final @Nls @NotNull String listname, @Nullable V... o) {
        V[] os = trim(o);
        if (os.length == 0)
            return (T) this;

        List<V> li = (List<V>) writeInterface(listname);
        li.removeIf(v -> inArray(os, v));
        // 解除锁定
        InterfaceWriteUn(listname);

        return (T) this;
    }

    /**
     * <p>通过接口遍历指定列表.</p>
     *
     * @param li          列表
     * @param forEachList 处理接口
     * @param p           检查是否继续的接口
     *
     * @return this
     *
     * @throws Stop 没有该列表
     */
    @NotNull
    @MaybeStop
    public
    <V> T foreachInterface(final @NotNull List<V> li, final ForEachList<V> forEachList,
            Predicate<V> p)
    {
        V tmp;
        /* 遍历 */
        for ( int i = 0, len = li.size(); i < len; ){
            tmp = li.get(i++);
            forEachList.run(li, tmp);
            if (!p.test(tmp))
                break;
        }

        return (T) this;
    }

    /**
     * <p>通过接口遍历指定列表.</p>
     *
     * @param listname    列表名称
     * @param forEachList 处理接口
     * @param p           检查是否继续的接口
     *
     * @return this
     *
     * @throws Stop 没有该列表
     */
    @NotNull
    @MaybeStop
    public
    <V> T foreachInterface(final @Nls @NotNull String listname, final ForEachList<V> forEachList,
            Predicate<V> p)
    {
        foreachInterface((List<V>) readInterface(listname), forEachList, p);
        InterfaceReadUn(listname);
        return (T) this;
    }

    /**
     * <p>通过接口遍历指定列表.</p>
     *
     * @param listname    列表名称
     * @param forEachList 处理接口
     *
     * @return this
     *
     * @throws Stop 没有该列表
     */
    @NotNull
    @MaybeStop
    public
    <V> T foreachInterface(final @Nls @NotNull String listname, final ForEachList<V> forEachList)
    { return foreachInterface(listname, forEachList, v -> true); }

    /* MAPLOCK */

    /**
     * <p>获取容器锁对象.</p>
     *
     * @return 锁
     */
    @NotNull
    protected final
    ReentrantReadWriteLock MapLock() { return LOCK; }

    /**
     * <p>使用容器读取锁.</p>
     *
     * @return 该读取锁
     */
    @NotNull
    protected final
    Lock MapReadLock() {
        Lock r = MapLock().readLock();

        r.lock();
        return r;
    }

    /**
     * <p>解除容器读取锁.</p>
     *
     * @return 该读取锁
     */
    @NotNull
    protected final
    Lock MapReadUn() {
        Lock r = MapLock().readLock();

        r.unlock();
        return r;
    }

    /**
     * <p>使用容器写入锁.</p>
     *
     * @return 该写入锁
     */
    @NotNull
    protected final
    Lock MapWriteLock() {
        Lock w = MapLock().writeLock();

        w.lock();
        return w;
    }

    /**
     * <p>解除容器写入锁.</p>
     *
     * @return 该写入锁
     */
    @NotNull
    protected
    Lock MapWriteUn() {
        Lock w = MapLock().writeLock();

        w.unlock();
        return w;
    }

    /** <p>解除容器读写锁.</p> */
    protected final
    void MapUn() {
        ReentrantReadWriteLock lock = MapLock();
        lock.writeLock().unlock();
        lock.readLock().unlock();
    }

    /* // MAPLOCK */

    /**
     * <p>获取列表锁.</p>
     *
     * @param name 名称
     *
     * @return 如果没有该列表锁，则会返回 {@code null}
     *
     * @throws Stop 没有锁
     */
    @MaybeStop
    @Nullable
    protected
    ReentrantReadWriteLock InterfaceLock(@NotNull final String name) {
        MapReadLock();
        try {
            if (INTERFACE_LOCK.containsKey(name))
                return INTERFACE_LOCK.get(name);
            else
                throw CallStop.INSTANCE.getInstance();
        } finally {
            MapReadUn();
        }
    }

    /**
     * <p>使用列表读锁.</p>
     *
     * @param name 名称
     *
     * @return 锁
     *
     * @throws Stop 没有该锁
     */
    @NotNull
    @MaybeStop
    protected
    Lock InterfaceReadLock(@Nls String name) {
        Lock r = InterfaceLock(name).readLock();

        r.lock();
        return r;
    }

    /**
     * <p>释放列表读锁.</p>
     *
     * @param name 名称
     *
     * @return 锁
     *
     * @throws Stop 没有该锁
     */
    @NotNull
    @MaybeStop
    protected
    Lock InterfaceReadUn(@Nls String name) {
        Lock r = InterfaceLock(name).readLock();

        r.unlock();
        return r;
    }

    /**
     * <p>使用列表写锁.</p>
     *
     * @param name 名称
     *
     * @return 锁
     *
     * @throws Stop 没有该锁
     */
    @NotNull
    @MaybeStop
    protected
    Lock InterfaceWriteLock(@Nls String name) {
        Lock w = InterfaceLock(name).writeLock();

        w.lock();
        return w;
    }

    /**
     * <p>释放列表写锁.</p>
     *
     * @param name 名称
     *
     * @return 锁
     *
     * @throws Stop 没有该锁
     */
    @NotNull
    @MaybeStop
    protected
    Lock InterfaceWriteUn(@Nls String name) {
        Lock w = InterfaceLock(name).writeLock();

        w.unlock();
        return w;
    }

    /**
     * <p>释放所有列表锁.</p>
     * <p>会释放一次读锁和写锁</p>
     *
     * @param name 名称
     *
     * @throws Stop 没有该锁
     */
    @MaybeStop
    protected
    void InterfaceUn(@Nls String name) {
        ReentrantReadWriteLock lock = InterfaceLock(name);
        lock.writeLock().unlock();
        lock.readLock().unlock();
    }

    /**
     * <p>获取列表.</p>
     *
     * @param name 名称
     *
     * @return 获取的列表
     *
     * @throws Stop 被关闭
     */
    @NotNull
    @MaybeStop
    protected
    List<?> getInterface(@Nls String name) {
        MapReadLock();
        List<?> li = INTERFACE.get(name);
        MapReadUn();
        return CheckObjectTOOL.checkNull(li);
    }

    /**
     * <p>对列表进行读取操作.</p>
     * <p>获取列表并上读锁，操作完成后需要 {@link #InterfaceReadUn(String)}</p>
     *
     * @param name 名称
     *
     * @return 列表
     *
     * @throws Stop 没有该列表
     */
    @MaybeStop
    @NotNull
    protected
    List<?> readInterface(@Nls String name) {
        MapReadLock();

        try {
            checkclose();
            InterfaceReadLock(name);
            return getInterface(name);
        } finally {
            MapReadUn();
        }
    }

    /**
     * <p>对列表进行写操作.</p>
     * <p>获取列表并上写锁，操作完成后需要 {@link #InterfaceWriteUn(String)} </p>
     *
     * @param name 名称
     *
     * @return 列表
     *
     * @throws Stop 没有该列表
     */
    @MaybeStop
    @NotNull
    protected
    List<?> writeInterface(@Nls String name) {
        MapReadLock();

        try {
            checkclose();
            InterfaceWriteLock(name);
            return getInterface(name);
        } finally {
            MapReadUn();
        }
    }

    /**
     * <p>修改对应名称的列表.</p>
     *
     * @param name 名称
     * @param list 列表
     *
     * @throws Stop 被关闭
     */
    protected
    void setInterface(@Nls String name, @NotNull List<?> list) {
        MapWriteLock();

        try {
            checkclose();
            INTERFACE.put(name, list);
        } finally {
            MapWriteUn();
        }
    }

    /**
     * <p>移除对应列表.</p>
     *
     * @param name 列表名称
     */
    protected
    void removeInterface(@Nls String name) {
        MapWriteLock();
        INTERFACE.remove(name);
        INTERFACE_LOCK.remove(name);
        MapWriteUn();
    }

    @Override
    public
    void clean() {
        MapWriteLock();
        // 重置
        INTERFACE.keySet().forEach(v -> setInterface(v, new ArrayList<>()));

        MapWriteUn();
    }

    @Override
    public
    void free() {
        MapWriteLock();

        INTERFACE.clear();
        INTERFACE_LOCK.clear();

        MapWriteUn();
    }

    @Override
    public
    void close() {
        MapWriteLock();

        free();
        close = true;

        MapWriteUn();
    }

    @Override
    public
    boolean isNull() {
        MapReadLock();
        boolean b = close;
        MapReadUn();

        return b;
    }

    /** <p>检查是否关闭.</p> */
    protected
    void checkclose() {
        MapReadLock();
        boolean b = close;
        MapReadUn();

        if (b)
            throw CallStop.INSTANCE.getInstance();
    }
}
