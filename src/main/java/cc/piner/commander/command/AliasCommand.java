package cc.piner.commander.command;

import cc.piner.commander.annotation.Cmd;
import cc.piner.commander.annotation.Option;
import cc.piner.commander.main.Command;
import cc.piner.commander.main.Commander;
import cc.piner.commander.main.Environment;
import cc.piner.commander.main.Register;

@Cmd(name = "alias", desc = "为命令设置别名")
public class AliasCommand extends Command {
    @Option(name = {"-d", "--delete"}, desc = "删除别名")
    public boolean delete;

    @Option(name = {"-s", "--save"}, desc = "将此次操作持久化保存")
    public boolean save;

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
            StringBuilder value = new StringBuilder();
            for (int i = 1; i < params.size(); i++) {
                value.append(params.get(i)).append(" ");
            }
            // alias (alias) value
            int i = Register.addAlias(alias, value.toString());
            if (save) {
                Environment.saveCmd("alias" + alias + value);
            }
            if (i == Register.SUCCESS) {
                return "成功添加别名：" + alias + "=" + value;
            } else {
                return "失败：ErrCode=" + i;
            }
        }
    }

    @Override
    public int init() {
        delete = false;
        save = false;
        return Command.SUCCESS;
    }
}
