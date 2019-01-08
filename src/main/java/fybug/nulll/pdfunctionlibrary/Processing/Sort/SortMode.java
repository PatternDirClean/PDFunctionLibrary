package fybug.nulll.pdfunctionlibrary.Processing.Sort;
/**
 * <h2>排序方式枚举存放.</h2>
 *
 * @author fybug
 * @version 0.0.1
 * @since PDF 1.3 expander 2
 */
public final
class SortMode {
    /**
     * <h2>文件排序的方式.</h2>
     *
     * @author fybug
     * @version 0.0.1
     * @since PDF 1.3 expander 2
     */
    public
    enum FileMode {
        /** <p>文件排序模式：文件夹置顶.</p> */
        MODE_DIR,
        /** <p>文件排序模式：文件置顶.</p> */
        MODE_FILE,
        /** <p>文件排序模式：仅对比字符.</p> */
        MODE_DEFAULT
    }

    /**
     * <h2>常规排序方式.</h2>
     *
     * @author fybug
     * @version 0.0.1
     * @since PDF 1.3 expander 2
     */
    public
    enum Mode {
        /** <p>排序模式：较小的在上.</p> */
        UP,
        /** <p>排序模式：较大的在上.</p> */
        DOWN
    }
}
