package com.drawingstudio.manager;

import java.awt.*;
import java.io.File;

/**
 * Manages file dialogs for save and load operations
 * Provides consistent file dialog experience across the application
 */
public class FileDialogManager {
    
    /**
     * Show save dialog and return selected file
     * @param parent Parent frame for the dialog
     * @return Selected file, or null if cancelled
     */
    public static File showSaveDialog(Frame parent) {
        FileDialog fileDialog = new FileDialog(parent, "Save Drawing", FileDialog.SAVE);
        fileDialog.setFile("*.png");
        fileDialog.setVisible(true);
        
        String filename = fileDialog.getFile();
        if (filename != null) {
            String directory = fileDialog.getDirectory();
            return new File(directory, filename);
        }
        return null;
    }
    
    /**
     * Show load dialog and return selected file
     * @param parent Parent frame for the dialog
     * @return Selected file, or null if cancelled
     */
    public static File showLoadDialog(Frame parent) {
        FileDialog fileDialog = new FileDialog(parent, "Load Drawing", FileDialog.LOAD);
        fileDialog.setFile("*.png");
        fileDialog.setVisible(true);
        
        String filename = fileDialog.getFile();
        if (filename != null) {
            String directory = fileDialog.getDirectory();
            return new File(directory, filename);
        }
        return null;
    }
}
