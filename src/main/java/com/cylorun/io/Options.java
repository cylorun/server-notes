package com.cylorun.io;

import com.cylorun.util.ExceptionUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Options {


    public String sheet_id;
    public String sheet_name = "Coords";
    public String nickname = "User";

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = Paths.get("config.json");
    private static Options instance;

    private Options() {

    }

    public static Options getInstance() {
        if (instance == null) {
            if (Files.exists(CONFIG_PATH)) {
                try {
                    instance = GSON.fromJson(new String((Files.readAllBytes(CONFIG_PATH))), Options.class);
                } catch (IOException e) {
                    ExceptionUtil.showError(e);
                    throw new RuntimeException(e);
                }
            } else {
                instance = new Options();
            }

        }
        return instance;
    }

    public static void save() {
        FileWriter writer;
        try {
            writer = new FileWriter(CONFIG_PATH.toFile());
            GSON.toJson(instance, writer);
            writer.close();
        } catch (IOException e) {
            ExceptionUtil.showError(e);
            throw new RuntimeException();
        }
    }


}

