package fybug.nulll.pdfunctionlibrary.lang;
import com.sun.istack.internal.NotNull;

/**
 * <h2>多态接口 - 并发.</h2>
 * <pre>
 * 实现该接口的对象都应该是抽象类或接口
 * 并有其并发和非并发处理实现
 * 该接口可以进行该类的并发和非并发类型转化
 *
 * <b>应暴露其抽象父类，隐藏其子类</b>
 *
 * 转移数据时应只转移其引用
 * </pre>
 *
 * @author fybug
 * @version 0.0.1
 * @since PDF 1.2 expander 2
 */
@SuppressWarnings( "All" )
public
interface MaybeSynchronized {
    /**
     * <p>保证该对象为并发处理对象.</p>
     * <p>如果该对象是并发对象则会返回该对象，否则会进行数据转移</p>
     */
    @NotNull
    MaybeSynchronized toSynchronized();

    /**
     * <p>保证该对象为非并发处理对象.</p>
     * <p>如果该对象是非并发对象则会返回该对象，否则会进行数据转移<br/>
     * 在非并发的条件下，该类对象的运行效率通常较快</p>
     */
    @NotNull
    MaybeSynchronized removerSynchronized();
}