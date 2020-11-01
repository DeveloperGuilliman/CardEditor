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
package com.developerguilliman.cardEditor.gui;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.concurrent.Callable;
import javax.swing.JOptionPane;

/**
 *
 * @author Developer Guilliman <developerguilliman@gmail.com>
 */
public class WaitingDialog extends javax.swing.JDialog {

    /**
     * Creates new form LoadingDialog
     *
     * @param parent
     * @param label
     */
    public WaitingDialog(java.awt.Frame parent, String label) {
        super(parent, true);
        setLocationRelativeTo(parent);
        initComponents();
        loadingLabel.setText(label);
    }

    public static void show(java.awt.Frame parent, String label, Callable<List<String>> callable) {
        show(parent, label, callable, null, null);
    }

    public static void show(java.awt.Frame parent, String label, Callable<List<String>> callable, Runnable postShow) {
        show(parent, label, callable, postShow, postShow);

    }

    public static void show(java.awt.Frame parent, String label, Callable<List<String>> callable, Runnable postWarnings, Runnable postNoWarnings) {
        WaitingDialog l = new WaitingDialog(parent, label);

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    List<String> warnings = callable.call();
                    l.dispose();

                    if (warnings != null && !warnings.isEmpty()) {
                        l.openWarningDialog(parent, warnings, postWarnings);
                    } else if (postNoWarnings != null) {
                        postNoWarnings.run();
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                    l.dispose();
                    Throwable cause = getCause(t);
                    String exClassName = cause.getClass().getName();
                    exClassName = exClassName.substring(exClassName.lastIndexOf('.') + 1);
                    String message = exClassName + " : " + cause.getLocalizedMessage();
                    JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        };
        t.setName("WaitingDialogTask");
        t.setDaemon(true);
        t.start();
        java.awt.EventQueue.invokeLater(() -> {
            l.setVisible(true);
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        loadingLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setAutoRequestFocus(false);
        setMinimumSize(new java.awt.Dimension(280, 30));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(180, 35));
        setResizable(false);
        setSize(new java.awt.Dimension(180, 35));
        getContentPane().setLayout(new java.awt.BorderLayout(1, 0));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new java.awt.GridLayout(0, 1));

        loadingLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        loadingLabel.setText("Loading");
        loadingLabel.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jPanel1.add(loadingLabel);

        progressBar.setIndeterminate(true);
        progressBar.setOpaque(true);
        progressBar.setString("");
        jPanel1.add(progressBar);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel loadingLabel;
    private javax.swing.JProgressBar progressBar;
    // End of variables declaration//GEN-END:variables

    public static Throwable getCause(Throwable t) {
        Throwable cause = t.getCause();
        while (cause != null) {
            t = cause;
            cause = t.getCause();
        }
        return t;
    }

    private void openWarningDialog(Frame parent, List<String> warnings, Runnable postWarnings) {

        java.awt.EventQueue.invokeLater(() -> {
            WarningsDialog wd = new WarningsDialog(parent, warnings, "building the pdf");
            if (postWarnings != null) {

                wd.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent arg0) {
                        postWarnings.run();
                    }
                });
            }
            wd.setVisible(true);
        });
    }

}
