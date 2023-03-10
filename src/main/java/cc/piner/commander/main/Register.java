package cc.piner.commander.main;

import cc.piner.commander.annotation.Cmd;
import cc.piner.commander.command.AliasCommand;
import cc.piner.commander.command.HelpCommand;

import java.util.HashMap;
import java.util.Map;

public class Register {
    public static final int SUCCESS = 0;
    public static final int DUPLICATE_ID = 1;
    public static final int NOT_FOUND = 2;
    public static final int NOT_COMMAND = 3;
    private static final Map<String, Command> commandMap = new HashMap<>();
    private static final Map<String, String> aliasMap = new HashMap<>();

    static {
        init();
    }

    private static void init() {
        register(new HelpCommand());
        register(new AliasCommand());
    }

    /**
     * 将 command 注册到注册表，与注册表中任一 name 字段均不同时才能成功注册
     *
     * @param command 待注册的 command
     * @return Register.SUCCESS 注册成功
     * Register.DUPLICATE_ID name 字段冲突
     */
    @SuppressWarnings("all")
    public static int register(Command command) {
        Class<? extends Command> cmdClass = command.getClass();
        boolean isCmd = cmdClass.isAnnotationPresent(Cmd.class);
        if (!isCmd) {
            return NOT_COMMAND;
        }
        Cmd annotation = cmdClass.getAnnotation(Cmd.class);
        String name = annotation.name();
        if (commandMap.get(name) != null) {
            return DUPLICATE_ID;
        }
        commandMap.put(name, command);
        return SUCCESS;
    }

    /**
     * 强制注册，若 command.name 重复则会替换掉原本 Command
     *
     * @param command 待注册的 command
     * @return 返回值恒为 Register.SUCCESS
     */
    public static int registerForce(Command command) {
        commandMap.put(command.getName(), command);
        return SUCCESS;
    }

    public static Command getCommand(String cmd) {
        return commandMap.get(cmd);
    }

    public static int addAlias(String alias, String value) {
        if (aliasMap.containsKey(alias)) {
            return DUPLICATE_ID;
        } else {
            aliasMap.put(alias, value);
            return SUCCESS;
        }
    }

    public static String getAliasCmd(String alias) {
        return aliasMap.get(alias);
    }

    public static int deleteAlias(String alias) {
        if (aliasMap.containsKey(alias)) {
            aliasMap.remove(alias);
            return SUCCESS;
        } else {
            return NOT_FOUND;
        }
    }

    public static Map<String, String> getAliasMap() {
        return aliasMap;
    }

}
