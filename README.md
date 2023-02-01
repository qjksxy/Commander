# Commander

## 摘要
这是一个 Java 语言的命令解析器

## 正文

### 开始使用
下载最新版 jar 包，或者从当前仓库的 master 分支构建打包。

在项目中使用需要以下三个步骤：

#### 1. 编写命令类
- 命令类需要继承自 Command 类，并重写以下方法：

  - `public int init()`: 此方法用于命令初始化，在命令被调用前，解析器将首先
调用此方法。需要在此方法中对命令类中的变量赋初值。方法返回值为 `int` 类型，
请使用 `Command` 类中定义的常量，如 `Command.SUCCESS` 。

  - `public String handle()`: 命令的执行体。返回命令的执行结果。

- 给命令添加参数
  
  命令解析器可以识别四种参数，布尔类型、字符串类型、整数类型、浮点类型。
  每种参数使用类字段来添加，并同时使用 `@Option` 注解标注。
  `@Option` 注解有两个属性：`String[] name` 用来说明此参数的名称，如 `-v` 等；
  `String desc` 用来为此属性添加可读的解释，此解释会被解析器内置的 `help` 命令提取。

- 给类添加 `@Cmd` 注解
  
  此注解包含两个属性，`String name` 用来指明方法名。此方法名必须唯一，否则可能导致方法注册失败（
  可以通过 `Register.forceRegister(Command command)` 方法强制注册）。`String desc` 用以添加可读的解释，
  同样，此解释会被 `help` 命令提取。


#### 2. 将命令添加到注册表
在项目中合适的地方调用方法 `Register.register(Command command)` 即可。

#### 3. 使用命令解析器解析并执行命令
调用方法 `Commander.handle(String cmd)` 即可

#### 代码示例
```java
package cc.piner.commander.command;

import cc.piner.commander.annotation.Cmd;
import cc.piner.commander.annotation.Option;
import cc.piner.commander.main.Command;
import cc.piner.commander.main.Register;

@Cmd(name = "acmd", desc = "示例命令")
public class ACommand extends Command {
    @Option(name = {"-s", "--string"}, desc = "输入字符串参数")
    String string;

    @Option(name = {"-i"})
    int i;

    @Option(name = {"-d"})
    double d;

    @Option(name = {"-b"})
    boolean bool;

    @Override
    public String handle() {
        return this.toString();
    }

    @Override
    public int init() {
        this.string = "hello, world!";
        i = 10;
        d = 3;
        bool = false;
        return Command.SUCCESS;
    }

    // toString 方法省略

    public static void main(String[] args) {
        Register.register(new ACommand());
        String result = Commander.handle("abc -s nihao -i 100 -d 10.22 -b abcdefg");
        System.out.println(result);
    }
}

```