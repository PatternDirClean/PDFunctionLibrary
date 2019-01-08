package test.lang;
import org.junit.Test;

import java.io.Closeable;

import fybug.nulll.pdfunctionlibrary.lang.CloseAll;
public final
class TestCloall {
    @Test
    public
    void test() {
        Closeable[] cs = {new cl(), new cl(), new cl(), new cl()};
        for ( int i = 0; i < cs.length; i++ ){
            System.out.println(((cl) cs[i]).s);
        }
        System.out.println("-------------");
        CloseAll ca = CloseAll.getCloseAll(cs);
        ca.close();
        for ( int i = 0; i < cs.length; i++ ){
            System.out.println(((cl) cs[i]).s);
        }
    }

    private static final
    class cl implements Closeable {
        private String s = "not close";

        @Override
        public
        void close() {
            s = "is close!";
        }
    }
}