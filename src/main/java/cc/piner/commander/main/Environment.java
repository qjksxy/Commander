package cc.piner.commander.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 解释器环境控制
 */
public class Environment {
    private static final String FILE_PATH = ".cmdpro";
    private static final File FILE = new File(FILE_PATH);

    private static final List<String> cmds = new ArrayList<>();
    private static boolean prepared = false;

    private Environment() {
    }

    public static void init() {
        if (!prepared) {
            try {
                if (!FILE.exists()) {
                    boolean newFile = FILE.createNewFile();
                }
                FileReader fileReader = new FileReader(FILE);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    cmds.add(line);
                    Commander.handle(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            prepared = true;
        }
    }

}
