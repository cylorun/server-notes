package com.cylorun;

import com.cylorun.gui.WorldNotesFrame;
import com.cylorun.io.ClipboardReader;
import com.cylorun.io.Options;
import com.cylorun.sheets.GoogleSheetsClient;
import com.cylorun.util.ExceptionUtil;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new FlatDarculaLaf());
        WorldNotesFrame frame = new WorldNotesFrame();

        new ClipboardReader(s -> {
            if (isF3c(s)) {
                int[] coords = getCoords(s);
                List<Object> data = new ArrayList<>();

                Toolkit.getDefaultToolkit().beep();
                String name = frame.askName();

                if(name == null) {
                    return;
                }
                String dimension = getDimension(s);
                data.add(Options.getInstance().nickname);
                data.add(dimension);
                data.add(Arrays.toString(coords));
                data.add(name);

                try {
                    GoogleSheetsClient.appendRowTop(data);
                } catch (IOException | GeneralSecurityException e) {
                    ExceptionUtil.showError(new Exception("Something went wrong when trying to push the data, probably wrong sheetID or sheetName\n" + e));
                    throw new RuntimeException(e);
                }

            }
        });
    }

    public static boolean isF3c(String s) {
//        String regex = "^\\/execute in minecraft:[^ ]+ run tp @s -?\\d+(\\.\\d+)? -?\\d+(\\.\\d+)? -?\\d+(\\.\\d+)? \\d+(\\.\\d+)? \\d+(\\.\\d+)? .+$\n";
//        Pattern pattern = Pattern.compile(regex);
//        return pattern.matcher(s).find();
        return s.contains("/execute in minecraft");
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

}
