package fybug.nulll.pdfunctionlibrary.Util.Processing;
import org.junit.Test;

import java.util.Arrays;

public
class StringTOOLTest {
    @Test
    public
    void getFindNmuber() {
        String data = "a s d sd s sa a a a sd s fd fd a fd xcvsd ds aaaaa";
        assert StringTOOL.getFindNmuber(data, "a") == 11;
        assert StringTOOL.getFindNmuber(data, " a") == 5;
    }

    @Test
    public
    void passArray() {
        String data = "[  a ,d,f,q  ,qwe ,  wqer,  try,  try]";
        assert Arrays.equals(new String[]{"a", "d", "f", "q", "qwe", "wqer", "try", "try"},
                             StringTOOL.passArray(data));

        data = "[  a ,d,f,q  ,qwe ,  wqer,  try,  try,]";
        assert Arrays.equals(new String[]{"a", "d", "f", "q", "qwe", "wqer", "try", "try"},
                             StringTOOL.passArray(data));

        data = "[  a ,d,f,q  ,qwe ,  wqer,  try,  try  ";
        assert Arrays.equals(new String[]{"a", "d", "f", "q", "qwe", "wqer", "try", "try"},
                             StringTOOL.passArray(data));

        data = "[  a ,d,f,q  ,qwe ,  wqer,  try,  try , ";
        assert Arrays.equals(new String[]{"a", "d", "f", "q", "qwe", "wqer", "try", "try"},
                             StringTOOL.passArray(data));

        data = "[ , a ,d,f,q  ,qwe ,  wqer,  try,  try , ";
        assert Arrays.equals(new String[]{"a", "d", "f", "q", "qwe", "wqer", "try", "try"},
                             StringTOOL.passArray(data));

        data = "  a ,d,f,q  ,qwe ,  wqer,  try,  try , ";
        assert Arrays.equals(new String[0], StringTOOL.passArray(data));
    }

    @Test
    public
    void ArrayToString() {
        String[] strings = {"a", "s", "f", "g", "", "s", null, "qw"};
        assert StringTOOL.ArrayToString(strings).equals("[a,s,f,g,s,qw]");

        strings = new String[]{"", null};
        assert StringTOOL.ArrayToString(strings).equals("[]");

        strings = new String[0];
        assert StringTOOL.ArrayToString(strings).equals("[]");
    }
}