package cc.piner.commander.main;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReTest {
    @Test
    public void reTest() {
        EchoCommand echoCommand = new EchoCommand();
        Class<? extends EchoCommand> aClass = echoCommand.getClass();
        Field[] fields = aClass.getFields();
        for (Field field : fields) {
            System.out.println(field.getName() + "::" + field.getType());
        }
    }

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
        Register.register(new EchoCommand());
        System.out.println(Commander.handler("help echo xoxo help"));

    }
}
