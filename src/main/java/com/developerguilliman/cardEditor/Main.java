/*
 * Copyright (C) 2020 Developer Guilliman <developerguilliman@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.developerguilliman.cardEditor;

import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.developerguilliman.cardEditor.gui.MainWindow;

/**
 *
 * @author Developer Guilliman <developerguilliman@gmail.com>
 */
public class Main {

    private Main() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        if (args.length <= 0) {
            java.awt.EventQueue.invokeLater(() -> {
                MainWindow window = new MainWindow();
                window.setVisible(true);
            });
            return;
        }
        for (String filename : args) {
            /* Create and display the form */
            java.awt.EventQueue.invokeLater(() -> {
                MainWindow window = new MainWindow();
                window.setVisible(true);

                Thread.setDefaultUncaughtExceptionHandler((Thread t, Throwable e) -> {
                    JOptionPane.showMessageDialog(window, e.getLocalizedMessage(), "Unknown Error", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                });

                window.loadCards(new File(filename), "Loading...", true);
            });
        }
    }

}
