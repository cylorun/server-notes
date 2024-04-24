package com.cylorun;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.function.Consumer;

public class ClipboardReader extends Thread{

    private Consumer<String> consumer;
    private String lastClipboard = "";
    private Clipboard clipboard;
    public ClipboardReader(Consumer<String> consumer) {
        this.consumer = consumer;
        this.clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        this.start();
    }



    @Override
    public void run() {
        while (true) {
            String clipboardString = null;
            try {
                clipboardString = (String) this.clipboard.getData(DataFlavor.stringFlavor);
            } catch (UnsupportedFlavorException | IllegalStateException | IOException ignored) {
            }

            if (clipboardString != null && !this.lastClipboard.equals(clipboardString)) {
                this.consumer.accept(clipboardString);
                this.lastClipboard = clipboardString;
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }
        }
    }
}
