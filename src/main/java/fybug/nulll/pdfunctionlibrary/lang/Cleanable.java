package fybug.nulll.pdfunctionlibrary.lang;
import java.io.Closeable;
/**
 * <h2>可释放对象.</h2>
 * <pre>
 * 实现该接口代表该类可使用 <b>内释放</b> 功能
 * 实现该接口则代表该类拥有 可关闭、可复用 的特性
 * </pre>
 * <pre>
 * 要清空该类应使用 {@link #clean()}
 * 要进行内释放应使用 {@link #free()}
 * 要彻底关闭该类应使用 {@link #close()}
 * </pre>
 *
 * @author fybug
 * @version 0.0.6
 * @see Closeable
 * @since PDF 1.2
 */
public
interface Cleanable extends Closeable {
    /**
     * <p>清空.</p>
     * <pre>
     * 仅释放内部引用，如果内部对象应该是可关闭的对象则不会关闭
     * 清空后该对象应仍可复用
     *
     * <b>要注意为要清空的变量准备对象锁</b>
     * </pre>
     */
    void clean();

    /**
     * <p>内释放.</p>
     * <pre>
     * 如果内部对象应该是可关闭对象则会关闭并释放引用
     * 如果内部对象不一定是可关闭对象，则于 {@link #clean()} 相同
     * 关闭后应仍可复用
     *
     * <b>要注意要锁定要关闭的对象</b>
     * </pre>
     */
    void free();

    /**
     * <p>关闭.</p>
     * <pre>
     * 使用 {@link #free()} 进行释放后再释放内部数据容器
     * 关闭后应不可复用
     * 如果继续使用则应该是空处理或抛出异常
     *
     * <b>应注意要锁定准备关闭的对象</b>
     * </pre>
     */
    @Override
    void close();
}
