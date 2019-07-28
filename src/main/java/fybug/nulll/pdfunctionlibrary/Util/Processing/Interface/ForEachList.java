package fybug.nulll.pdfunctionlibrary.Util.Processing.Interface;
import java.util.List;

/**
 * <p>列表遍历接口.</p>
 *
 * @author fybug
 * @version 0.0.1
 */
public
interface ForEachList {
    <T> void run(List<T> list, T v);
}
