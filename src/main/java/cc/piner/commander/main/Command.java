package cc.piner.commander.main;

public abstract class Command {
    public static final int SUCCESS = 0;

    public abstract String handler();
    public abstract String getName();
    public abstract int init();
    public abstract void addParam(String param);
}
