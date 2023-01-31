package cc.piner.commander.command;

import cc.piner.commander.annotation.Cmd;
import cc.piner.commander.annotation.Option;
import cc.piner.commander.main.Command;
import cc.piner.commander.main.Commander;
import cc.piner.commander.main.Register;

@Cmd(desc = "为命令设置别名")
public class AliasCommand extends Command {
    public static final String COMMAND_NAME = "alias";
    @Option(name = {"-d", "--delete"}, desc = "删除别名")
    boolean delete;

    @Option(name = {"-s"}, desc = "将此次操作持久化保存")
    boolean save;


    @Override
    public String handle() {
        if (delete) {
            int i = Register.deleteAlias(params.get(0));
            if (i == Register.SUCCESS) {
                return "删除别名 " + params.get(0) + " 成功！";
                // 删除与 alias params.get(0) 有关的内容
            } else {
                return "删除别名" + params.get(0) + "失败！errCode=" + i;
            }
        } else {
            String alias = params.get(0);
            params.remove(0);
            StringBuilder value = new StringBuilder();
            for (String param : params) {
                value.append(param).append(" ");
            }
            // alias (alias) value
            int i = Register.addAlias(alias, value.toString());
            if (i == Register.SUCCESS) {
                return "成功添加别名：" + alias + "=" + value.toString();
            }
        }
        return "ERROR";
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public int init() {
        delete = false;
        save = false;
        return Command.SUCCESS;
    }
}
