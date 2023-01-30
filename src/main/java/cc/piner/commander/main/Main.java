package cc.piner.commander.main;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static EchoCommand echoCommand = new EchoCommand();

    public static void main(String[] args) {
        Register.register(echoCommand);
        List<String> cmd = new ArrayList<>();
        cmd.add("echo");
        cmd.add("--name");
        cmd.add("zhangsan");
        cmd.add("hello,world");
        System.out.println(Commander.handler(cmd));
    }

}
