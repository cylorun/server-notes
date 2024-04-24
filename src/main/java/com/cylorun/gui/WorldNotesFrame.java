package com.cylorun.gui;

import com.cylorun.Main;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;

public class WorldNotesFrame extends JFrame {


    public WorldNotesFrame() {
        super("World-Notes");
        JButton openJsonButton = new JButton("Open file");

        this.setLayout(new FlowLayout());
        this.setSize(new Dimension(400, 250));
        this.setVisible(true);
        this.add(openJsonButton);

        openJsonButton.addActionListener((e)->{
            if (Desktop.isDesktopSupported() && Files.exists(Main.OUTPUT_PATH)) {
                Desktop desktop = Desktop.getDesktop();

                try {
                    desktop.open(Main.OUTPUT_PATH.toFile());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Desktop not supported");
            }
        });
    }
}
