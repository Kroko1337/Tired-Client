package beta.tiredb56.config;


import beta.tiredb56.interfaces.IHook;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import beta.tiredb56.tired.CheatMain;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class ConfigManager implements IHook {

    private static final ArrayList<Config> CONFIGS = new ArrayList<>();
    private static File CONFIG_DIRECTORY;
    private static File CONFIG_DIRECTORYONLINE;
    private Config activeConfig;


    public void init() {
        CONFIG_DIRECTORY = new File(MC.mcDataDir + "/" + CheatMain.INSTANCE.CLIENT_NAME, "configs");
        CONFIG_DIRECTORYONLINE = new File(MC.mcDataDir + "/" + CheatMain.INSTANCE.CLIENT_NAME, "onlineconfigs");
        if (!CONFIG_DIRECTORY.exists()) CONFIG_DIRECTORY.mkdirs();
        CONFIGS.clear();
        for (File file : Objects.requireNonNull(CONFIG_DIRECTORY.listFiles())) {
            CONFIGS.add(new Config(file.getName().replace(".json", "")));
        }

        for (File file : Objects.requireNonNull(CONFIG_DIRECTORYONLINE.listFiles())) {
            if (!file.getName().contains("git")) {
                CONFIGS.add(new Config(file.getName().replace(".json", "") + "Online"));
            }
        }
    }

    public boolean create(Config config) {
        CONFIGS.add(config);
        return save(config);
    }

    public boolean loadNoSet(Config config) {
        try {
            activeConfig = config;
            if (config !=  null) {

                return config.loadOnlyNoSet(new JsonParser().parse(new FileReader(CONFIG_DIRECTORY + "/" + config.name().replace("Online", "") + ".json")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean loadOnline(Config config) {
        try {
            activeConfig = config;
            if (config !=  null) {
                return config.deserialize(new JsonParser().parse(new FileReader(CONFIG_DIRECTORYONLINE + "/" + config.name().replace("Online", "") + ".json")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean load(Config config) {
        try {
            activeConfig = config;
            if (config !=  null) {
                loadOnline(config);
                return config.deserialize(new JsonParser().parse(new FileReader(CONFIG_DIRECTORY + "/" + config.name().replace("Online", "") + ".json")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean load(String name) {
        Config config = configBy(name);
        if (config == null) {
            System.out.println("Cannot find " + config.name());
            return false;
        }
        return load(config);
    }

    public boolean save(Config config) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileWriter fileWriter = new FileWriter(CONFIG_DIRECTORY + "\\" + config.name() + ".json");
            gson.toJson(config.serialize(), fileWriter);
            fileWriter.close();
            System.out.println("Saved " + config.name());
            return true;
        } catch (Exception e) {
            System.out.println("Failed to save " + config.name());
            e.printStackTrace();
        }
        return false;
    }

    public boolean save(String name) {
        Config config = configBy(name);
        if (config == null) {
            System.out.println("Cannot find " + config.name());
            return false;
        }
        return save(config);
    }

    public Config configBy(String name) {
        for (Config config : CONFIGS) {
            if (config.name().equalsIgnoreCase(name)) return config;
        }
        return null;
    }

    public ArrayList<Config> configs() {
        return CONFIGS;
    }

    public Config activeConfig() {
        return activeConfig;
    }
}