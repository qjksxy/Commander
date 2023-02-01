package cc.piner.commander.main;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReTest {
    @Test
    public void strTest() {
        String str = "abcd  ab  hello kkk";
        String[] split = str.split("\\s+");
        List<String> strings = new ArrayList<>();
        Collections.addAll(strings, split);
        System.out.println(strings);
    }

    @Test
    public void cmdTest() {
        System.out.println(Commander.handle("h alias xo"));
        System.out.println(Commander.handle("alias xo alias"));
    }
}
