package fybug.nulll.pdfunctionlibrary.Util.Processing;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public
class ArraryTOOLTest {
    @Test
    public
    void unifiedArray() {
        assert ArraryTOOL.unifiedArray(new String[0]) == null;
        assert ArraryTOOL.unifiedArray(null) == null;

        assert ArraryTOOL.unifiedArray(null, String.class).getClass() == String[].class;
        assert ArraryTOOL.unifiedArray(new String[0], String.class).getClass() == String[].class;
    }

    @Test
    public
    void conversionArrayType() {
        Object[] objects = new Object[10];
        String[] strings = new String[objects.length];

        for ( int i = 0; i < objects.length; i++ ){
            objects[i] = new Object();
            strings[i] = objects[i].toString();
        }

        assert Arrays.equals(
                ArraryTOOL.conversionArrayType(objects, Object::toString, String.class), strings);
        assert Arrays.equals(
                ArraryTOOL.conversionArrayTypeForNaviter(objects, Object::toString, String.class),
                strings);
    }

    @Test
    public
    void append() {
        String[] strings = {"a", "b"};

        assert Arrays.equals((String[]) ArraryTOOL.append(strings, "c"),
                             new String[]{"a", "b", "c"});
        assert Arrays.equals((String[]) ArraryTOOL.append(strings, new String[]{"c"}),
                             new String[]{"a", "b", "c"});
        assert Arrays.equals((String[]) ArraryTOOL.append(strings, new ArrayList() {{
            add("c");
        }}), new String[]{"a", "b", "c"});
    }

    @Test
    public
    void trim() {
        String[] strings = {"Aasd", "fdsgd", "", null, "Asda", null, "were", null, "q"};

        assert ArraryTOOL.trim(strings).length == 6;

        assert Array.getLength(ArraryTOOL.trimForNaviter(strings)) == 6;
    }

    @Test
    public
    void copyofread() {
        String[] strings = {"A", "s", "f", "C", "v"};
        final int[] i = {0};

        ArraryTOOL.copyOfRead(strings, s -> {assert s == strings[i[0]++];});
    }

    @Test
    public
    void operation() {
        String[] s = {"A", "b", "c"};
        final int[] i = {0};

        assert Arrays.equals(new String[]{"A"}, ArraryTOOL.operation(s, v -> {
            if (i[0]++ == 0)
                return;
            v.delete();
        }));

        s = new String[]{"A", "b", "c"};
        i[0] = 0;

        assert Arrays.equals(new String[]{"A", null, null}, ArraryTOOL.operation(s, v -> {
            if (i[0]++ == 0)
                return;
            v.remove();
        }));
    }
}