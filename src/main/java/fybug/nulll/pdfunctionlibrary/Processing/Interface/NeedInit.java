package fybug.nulll.pdfunctionlibrary.Processing.Interface;
import fybug.nulll.pdfunctionlibrary.Processing.Stop.MaybeStop;
/**
 * <h2>代表该类对象可初始化和销毁.</h2>
 * <pre>
 * 代表该类对象可初始化和销毁
 * 通常由容器或处理对象调用初始化和销毁方法
 * </pre>
 *
 * @author fybug
 * @version 0.0.3
 * @since PDF 1.2
 */
public
interface NeedInit {
    NeedInit empy = new NeedInit() {
        @Override
        public
        void init() throws Exception { }

        @Override
        public
        void destroy() throws Exception { }
    };

    /** <p>初始化.</p> */
    @MaybeStop
    void init() throws Exception;

    /** <p>销毁.</p> */
    @MaybeStop
    void destroy() throws Exception;
}