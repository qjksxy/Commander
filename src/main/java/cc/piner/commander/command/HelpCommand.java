package cc.piner.commander.command;

import cc.piner.commander.annotation.Option;
import cc.piner.commander.main.Command;
import cc.piner.commander.main.Register;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class HelpCommand extends Command {
    public List<String> params;
    private static final String COMMAND_NAME = "help";

    @Override
    public String handler() {
        StringBuilder sb = new StringBuilder("---\n");
        for (String param : params) {
            Command command = Register.getCommand(param);
            if (command == null) {
                sb.append(param).append(" 命令不存在").append("\n---\n");
                continue;
            }
            Class<? extends Command> cmdClass = command.getClass();
            Field[] fields = cmdClass.getFields();
            sb.append(command.getName()).append(":\n");
            for (Field field : fields) {
                if (field.isAnnotationPresent(Option.class)) {
                    Option option = field.getAnnotation(Option.class);
                    sb.append("\t");
                    String[] name = option.name();
                    for (int i = 0; i < name.length; i++) {
                        if (i>0) {
                            sb.append(", ");
                        }
                        sb.append(name[i]);
                    }
                    sb.append(": ");
                    sb.append(field.getType().getName()).append("\n\t\t");
                    sb.append(option.desc()).append("\n---\n");
                }
            }
        }
        return sb.toString();
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public int init() {
        params = new ArrayList<>();
        return Command.SUCCESS;
    }

    @Override
    public void addParam(String param) {
        params.add(param);
    }
}
