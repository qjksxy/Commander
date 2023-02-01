package cc.piner.commander.main;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {
    public static final int SUCCESS = 0;
    public List<String> params;

    public abstract String handle();

    public abstract String getName();

    public abstract int init();

    public void addParam(String param) {
        if (params == null) {
            params = new ArrayList<>();
        }
        params.add(param);
    }

    public void afterHanle() {
        params.clear();
    }
}
