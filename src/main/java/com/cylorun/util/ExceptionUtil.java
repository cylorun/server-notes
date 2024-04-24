package com.cylorun.util;

import javax.swing.*;

public class ExceptionUtil {
    public static void showError(Throwable t){
        JOptionPane.showMessageDialog(null, t.toString());
        System.err.println(t);
    }
}
