package test.processing;
import org.junit.Test;

import fybug.nulll.pdfunctionlibrary.Processing.Sort.SelectionSort;
import fybug.nulll.pdfunctionlibrary.Processing.Sort.SortMode;
public final
class selectsort {
    @Test
    public
    void testint() {
        int[] is = {1, 2, 564, 1231, 5640, 0, 54, 54, 10, 540, 654868, 54, 0, 51};

        is = SelectionSort.sortInt(is, SortMode.Mode.UP);
        for ( int i = 0; i < is.length; i++ ){
            System.out.println(is[i]);
        }

        System.out.println("\n------------------\n");

        is = SelectionSort.sortInt(is, SortMode.Mode.DOWN);
        for ( int i = 0; i < is.length; i++ ){
            System.out.println(is[i]);
        }
    }

    @Test
    public
    void testchar() {
        String[] is =
                {"a", "b", "564", "ab3a", "5640", "0", "54", "54", "a0", "540", "654868", "54", "0",
                        "5a"};

        is = SelectionSort.sortString(is, SortMode.Mode.UP);
        for ( int i = 0; i < is.length; i++ ){
            System.out.println(is[i]);
        }

        System.out.println("\n------------------\n");

        is = SelectionSort.sortString(is, SortMode.Mode.DOWN);
        for ( int i = 0; i < is.length; i++ ){
            System.out.println(is[i]);
        }
    }
}
