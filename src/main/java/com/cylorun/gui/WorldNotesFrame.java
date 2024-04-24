package com.cylorun.gui;

import com.cylorun.Main;
import com.cylorun.io.Options;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;

public class WorldNotesFrame extends JFrame {

    private JTextField sheetIdField;
    private JTextField sheetNameField;
    private JTextField nicknameField;

    public WorldNotesFrame() {
        super("Options");
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

        add(sheetIdLabel, gbc);
        gbc.gridy++;
        add(sheetNameLabel, gbc);
        gbc.gridy++;
        add(nicknameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(sheetIdField, gbc);
        gbc.gridy++;
        add(sheetNameField, gbc);
        gbc.gridy++;
        add(nicknameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(saveButton, gbc);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveOptions();
            }
        });

        loadOptions();

        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
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