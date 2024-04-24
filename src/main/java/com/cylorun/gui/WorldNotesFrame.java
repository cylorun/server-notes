package com.cylorun.gui;

import com.cylorun.Main;
import com.cylorun.io.Options;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.nio.file.Files;

public class WorldNotesFrame extends JFrame {

    private JTextField sheetIdField;
    private JTextField sheetNameField;
    private JTextField nicknameField;

    public WorldNotesFrame() {
        super("World Notes");
        JLabel sheetIdLabel = new JLabel("Sheet ID:");
        JLabel sheetNameLabel = new JLabel("Sheet Name:");
        JLabel nicknameLabel = new JLabel("Nickname:");
        JButton saveButton = new JButton("Save");

        sheetIdField = new JTextField(20);
        sheetNameField = new JTextField(20);
        nicknameField = new JTextField(20);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        this.add(sheetIdLabel, gbc);
        gbc.gridy++;
        this.add(sheetNameLabel, gbc);
        gbc.gridy++;
        this.add(nicknameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(sheetIdField, gbc);
        gbc.gridy++;
        this.add(sheetNameField, gbc);
        gbc.gridy++;
        this.add(nicknameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(saveButton, gbc);

        saveButton.addActionListener(e -> saveOptions());

        loadOptions();
        Options options = Options.getInstance();
        this.setSize(400, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(options.win_x, options.win_y);
        this.setResizable(false);
        this.setVisible(true);

        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                Options options = Options.getInstance();
                options.win_x = e.getWindow().getX();
                options.win_y = e.getWindow().getY();
                Options.save();
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

    }

    public String askName() {
        return JOptionPane.showInputDialog(this, "Input the name for this location");
    }

    private void loadOptions() {
        Options options = Options.getInstance();
        sheetIdField.setText(options.sheet_id);
        sheetNameField.setText(options.sheet_name);
        nicknameField.setText(options.nickname);
    }

    private void saveOptions() {
        Options options = Options.getInstance();
        options.sheet_id = sheetIdField.getText();
        options.sheet_name = sheetNameField.getText();
        options.nickname = nicknameField.getText();
        Options.save();
        JOptionPane.showMessageDialog(this, "Options saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}