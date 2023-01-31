package cc.piner.commander.main;

import cc.piner.commander.annotation.Option;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Commander {
    public static String handle(String cmd) {
        String[] split = cmd.split("\\s+");
        List<String> cmds = new ArrayList<>();
        Collections.addAll(cmds, split);
        return handle(cmds);
    }

    public static String handle(List<String> cmd) {
        Command command = Register.getCommand(cmd.get(0));
        cmd.remove(0);
        if (command.init() == Command.SUCCESS) {
            // 选项解析
            try {
                optionParse(command, cmd);
            } catch (IllegalAccessException e) {
                return "参数解析错误";
            }
            return command.handle();
        } else {
            return "命令初始化失败";
        }

    }

    public static void optionParse(Command command, List<String> options) throws IllegalAccessException {
        Class<? extends Command> cls = command.getClass();
        Field[] fields = cls.getFields();
        for (int i = 0; i < options.size(); i++) {
            if (options.get(i).startsWith("-")) {
                for (Field field : fields) {
                    boolean skip = false;
                    boolean annotationPresent = field.isAnnotationPresent(Option.class);
                    if (annotationPresent) {
                        Option annotation = field.getAnnotation(Option.class);
                        String[] names = annotation.name();
                        for (String name : names) {
                            if (name.equals(options.get(i))) {
                                field.setAccessible(true);
                                Class<?> type = field.getType();
                                if (type.equals(boolean.class)) {
                                    field.setBoolean(command, true);
                                } else if (type.equals(String.class)) {
                                    field.set(command, options.get(++i));
                                } else if (type.equals(int.class)) {
                                    field.set(command, Integer.parseInt(options.get(++i)));
                                } else if (type.equals(double.class)) {
                                    field.set(command, Double.parseDouble(options.get(++i)));
                                }
                                skip = true;
                                break;
                            }
                        }
                        if (skip) {
                            break;
                        }
                    }
                }
            } else {
                command.addParam(options.get(i));
            }
        }
    }
}
