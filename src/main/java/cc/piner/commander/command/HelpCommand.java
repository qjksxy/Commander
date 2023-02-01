package cc.piner.commander.command;

import cc.piner.commander.annotation.Cmd;
import cc.piner.commander.annotation.Option;
import cc.piner.commander.main.Command;
import cc.piner.commander.main.Register;

import java.lang.reflect.Field;
import java.util.ArrayList;

@Cmd(name = "help", desc = "查看命令帮助")
public class HelpCommand extends Command {
    @Override
    public String handle() {
        StringBuilder sb = new StringBuilder("---\n");
        for (String param : params) {
            Command command = Register.getCommand(param);
            if (command == null) {
                sb.append(param).append(" 命令不存在").append("\n---\n");
                continue;
            }
            String cmdDesc = "";
            Class<? extends Command> cmdClass = command.getClass();
            if (cmdClass.isAnnotationPresent(Cmd.class)) {
                Cmd cmd = cmdClass.getAnnotation(Cmd.class);
                cmdDesc = cmd.desc();
            }

            Field[] fields = cmdClass.getFields();
            sb.append(command.getName()).append(": ").append(cmdDesc);
            for (Field field : fields) {
                if (field.isAnnotationPresent(Option.class)) {
                    Option option = field.getAnnotation(Option.class);
                    sb.append("\n\t");
                    String[] name = option.name();
                    for (int i = 0; i < name.length; i++) {
                        if (i > 0) {
                            sb.append(", ");
                        }
                        sb.append(name[i]);
                    }
                    sb.append(": ");
                    sb.append(field.getType().getName());
                    if (option.desc() != null && !option.desc().equals("")) {
                        sb.append("\n\t\t").append(option.desc());
                    }

                }
            }
            sb.append("\n---\n");
        }
        return sb.toString();
    }

    @Override
    public int init() {
        params = new ArrayList<>();
        return Command.SUCCESS;
    }
}
