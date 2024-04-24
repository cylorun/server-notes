package com.cylorun;

import com.cylorun.gui.WorldNotesFrame;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.google.gson.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.util.Scanner;

public class Main {
    public static final Path OUTPUT_PATH = Paths.get("output.json");
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new FlatDarculaLaf());
        new WorldNotesFrame();
         new ClipboardReader(s -> {
            if (isF3c(s)) {
                JsonObject o = loadJson(OUTPUT_PATH);

                int[] coords = getCoords(s);

                Toolkit.getDefaultToolkit().beep();
                System.out.println("Give this location a name OR 1 to cancel");
                String name = new Scanner(System.in).nextLine();
                if (name.equals("1")) return;

                if (o == null) {
                    o = new JsonObject();
                    o.add("overworld", new JsonObject());
                    o.add("the_nether", new JsonObject());
                    o.add("the_end", new JsonObject());
                }

                String dimension = getDimension(s);
                JsonObject dimensionObject = o.getAsJsonObject(dimension);
                dimensionObject.addProperty(name, gson.toJson(coords).replaceAll("\\s", "").strip());

                writeJson(o, OUTPUT_PATH);
            }
        });
    }

    public static boolean isF3c(String s) {
        return s.contains("/execute");
    }

    public static String getDimension(String s) {
        return s.split(" ")[2].split(":")[1];
    }

    public static int[] getCoords(String s) {
        String[] split = s.split(" ");
        int[] res = new int[3];

        for (int i = 6; i < 9; i++) {
            res[i - 6] = (int) Double.parseDouble(split[i]);
        }
        return res;
    }

    public static JsonObject loadJson(Path path) {
        try {
            if (Files.exists(path)) {
                try (FileReader reader = new FileReader(path.toFile())) {
                    return JsonParser.parseReader(reader).getAsJsonObject();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeJson(JsonObject jsonObject, Path path) {
        try (FileWriter writer = new FileWriter(path.toFile())) {
            gson.toJson(jsonObject, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
