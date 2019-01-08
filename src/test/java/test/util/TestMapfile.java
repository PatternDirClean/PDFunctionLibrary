package test.util;
import org.junit.Test;

import fybug.nulll.pdfunctionlibrary.Util.DataMap;
import fybug.nulll.pdfunctionlibrary.Util.Map.MapFile;
public final
class TestMapfile {
    private static MapFile<String, String> mf;

    @Test
    public
    void testLink() {
        mf = (MapFile<String, String>) MapFile.userLink();
        add(mf);
        mf.clear();
        show(mf);
        System.out.println("\n---------------------------\n");
        add(mf);
        show(mf);
        System.out.println("\n---------------------------\n");
        DataMap<String, String> sa;
        while( (sa = mf.poll()) != null ){
            System.out.println(sa.getKey() + " : " + sa.getValue());
        }
        System.out.println("\n---------------------------\n");
        mf.restart();
        show(mf);
    }

    @Test
    public
    void testArr() {
        mf = (MapFile<String, String>) MapFile.userArray();
        add(mf);
        mf.clear();
        show(mf);
        System.out.println("\n---------------------------\n");
        add(mf);
        show(mf);
        System.out.println("\n---------------------------\n");
        DataMap<String, String> sa;
        while( (sa = mf.poll()) != null ){
            System.out.println(sa.getKey() + " : " + sa.getValue());
        }
        System.out.println("\n---------------------------\n");
        mf.restart();
        show(mf);
    }

    private static
    void add(MapFile<String, String> s) {
        s.add("1", "as");
        s.add("2", "asasd");
        s.add("3", "a2s");
        s.add("4", "a");
        s.add("5", "aghs");
        s.add("1", "as");
    }

    private
    void show(MapFile<String, String> s) {
        DataMap<String, String> sa;
        while( (sa = s.get()) != null ){
            System.out.println(sa.getKey() + " : " + sa.getValue());
            s.next();
        }
    }
}