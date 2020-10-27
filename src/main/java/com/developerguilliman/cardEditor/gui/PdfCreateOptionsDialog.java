/*
 * Copyright (C) 2020 Developer Guilliman
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

import com.developerguilliman.cardEditor.data.CardData;
import com.developerguilliman.cardEditor.output.PdfOutput;
import java.awt.Frame;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 *
 * @author DeveloperGuilliman
 */
public class PdfCreateOptionsDialog extends javax.swing.JDialog {

    public static final String[] FONT_NAMES;
    public static final PDFont[] FONT_TYPES;

    static {
        LinkedHashMap<String, PDFont> fonts = new LinkedHashMap<>();
        fonts.put("Courier", PDType1Font.COURIER);
        fonts.put("Courier bold", PDType1Font.COURIER_BOLD);
        fonts.put("Courier italic", PDType1Font.COURIER_OBLIQUE);
        fonts.put("Courier bold italic", PDType1Font.COURIER_BOLD_OBLIQUE);
        fonts.put("Helvetica", PDType1Font.HELVETICA);
        fonts.put("Helvetica bold", PDType1Font.HELVETICA_BOLD);
        fonts.put("Helvetica italic", PDType1Font.HELVETICA_OBLIQUE);
        fonts.put("Helvetica bold italic", PDType1Font.HELVETICA_BOLD_OBLIQUE);
        fonts.put("Times", PDType1Font.TIMES_ROMAN);
        fonts.put("Times bold", PDType1Font.TIMES_BOLD);
        fonts.put("Times italic", PDType1Font.TIMES_ITALIC);
        fonts.put("Times bold italic", PDType1Font.TIMES_BOLD_ITALIC);
        ArrayList<String> names = new ArrayList<>(fonts.size());
        ArrayList<PDFont> types = new ArrayList<>(fonts.size());
        for (Map.Entry<String, PDFont> e : fonts.entrySet()) {
            names.add(e.getKey());
            types.add(e.getValue());
        }
        FONT_NAMES = names.toArray(new String[0]);
        FONT_TYPES = types.toArray(new PDFont[0]);
    }

    private static String getKeyForFont(PDFont font) {

        for (int i = 0; i < FONT_TYPES.length; i++) {
            if (FONT_TYPES[i].equals(font)) {
                return FONT_NAMES[i];
            }
        }
        return null;
    }

    private static PDFont getFontForKey(Object o) {

        for (int i = 0; i < FONT_NAMES.length; i++) {
            if (FONT_NAMES[i].equals(o)) {
                return FONT_TYPES[i];
            }
        }
        return null;
    }

    private static int toInt(Object o) {
        return ((Number) o).intValue();
    }

    private static float toFloat(Object o) {
        return ((Number) o).floatValue();
    }

    private final File actualFile;
    private final List<List<CardData>> cards;

    /**
     * Creates new form PdfCreateOptionsDialog
     *
     * @param parent
     * @param actualFile
     * @param cards
     */
    public PdfCreateOptionsDialog(java.awt.Frame parent, File actualFile, List<List<CardData>> cards) {
        super(parent, true);
        this.actualFile = actualFile;
        this.cards = cards;
        initComponents();

    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        pagePanel = new javax.swing.JPanel();
        pageFieldPanel = new javax.swing.JPanel();
        cardsPerXLabel = new javax.swing.JLabel();
        cardsPerXSpinner = new javax.swing.JSpinner();
        cardsPerYLabel = new javax.swing.JLabel();
        cardsPerYSpinner = new javax.swing.JSpinner();
        marginXLabel = new javax.swing.JLabel();
        marginXSpinner = new javax.swing.JSpinner();
        marginYLabel = new javax.swing.JLabel();
        marginYSpinner = new javax.swing.JSpinner();
        centralPanel = new javax.swing.JPanel();
        fontsPanel = new javax.swing.JPanel();
        fontsFieldPanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        titlefontSizeSpinner = new javax.swing.JSpinner();
        titleFontTypeCombo = new javax.swing.JComboBox<>();
        nameLabel = new javax.swing.JLabel();
        namefontSizeSpinner = new javax.swing.JSpinner();
        nameFontTypeCombo = new javax.swing.JComboBox<>();
        legendLabel = new javax.swing.JLabel();
        legendfontSizeSpinner = new javax.swing.JSpinner();
        legendFontTypeCombo = new javax.swing.JComboBox<>();
        rulesLabel = new javax.swing.JLabel();
        rulesfontSizeSpinner = new javax.swing.JSpinner();
        rulesFontTypeCombo = new javax.swing.JComboBox<>();
        costLabel = new javax.swing.JLabel();
        costfontSizeSpinner = new javax.swing.JSpinner();
        costFontTypeCombo = new javax.swing.JComboBox<>();
        cardSettingsPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        cardBorderCheckBox = new javax.swing.JCheckBox();
        titleBarsCheckBox = new javax.swing.JCheckBox();
        savePanel = new javax.swing.JPanel();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Create PDF");

        pagePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Page settings"));

        pageFieldPanel.setLayout(new java.awt.GridLayout(4, 2, 3, 1));

        cardsPerXLabel.setLabelFor(cardsPerXSpinner);
        cardsPerXLabel.setText("Cards X");
        pageFieldPanel.add(cardsPerXLabel);

        cardsPerXSpinner.setModel(new javax.swing.SpinnerNumberModel(3, 1, null, 1));
        pageFieldPanel.add(cardsPerXSpinner);

        cardsPerYLabel.setLabelFor(cardsPerYSpinner);
        cardsPerYLabel.setText("Cards Y");
        pageFieldPanel.add(cardsPerYLabel);

        cardsPerYSpinner.setModel(new javax.swing.SpinnerNumberModel(3, 1, null, 1));
        pageFieldPanel.add(cardsPerYSpinner);

        marginXLabel.setText("Margin X %");
        pageFieldPanel.add(marginXLabel);

        marginXSpinner.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(10.0f), Float.valueOf(0.0f), Float.valueOf(50.0f), Float.valueOf(0.25f)));
        pageFieldPanel.add(marginXSpinner);

        marginYLabel.setText("Margin Y %");
        pageFieldPanel.add(marginYLabel);

        marginYSpinner.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(10.0f), Float.valueOf(0.0f), Float.valueOf(50.0f), Float.valueOf(0.25f)));
        pageFieldPanel.add(marginYSpinner);

        pagePanel.add(pageFieldPanel);

        getContentPane().add(pagePanel, java.awt.BorderLayout.WEST);

        centralPanel.setLayout(new javax.swing.BoxLayout(centralPanel, javax.swing.BoxLayout.Y_AXIS));

        fontsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Texts fonts"));

        fontsFieldPanel.setLayout(new java.awt.GridLayout(5, 3, 3, 1));

        titleLabel.setText("Title Font");
        fontsFieldPanel.add(titleLabel);

        titlefontSizeSpinner.setValue(PdfOutput.DEFAULT_TITLE_FONT_SIZE);
        fontsFieldPanel.add(titlefontSizeSpinner);

        titleFontTypeCombo.setModel(new javax.swing.DefaultComboBoxModel(FONT_NAMES));
        titleFontTypeCombo.setSelectedItem(getKeyForFont(PdfOutput.DEFAULT_TITLE_FONT_TYPE));
        fontsFieldPanel.add(titleFontTypeCombo);

        nameLabel.setText("Name Font");
        fontsFieldPanel.add(nameLabel);

        namefontSizeSpinner.setValue(PdfOutput.DEFAULT_NAME_FONT_SIZE);
        fontsFieldPanel.add(namefontSizeSpinner);

        nameFontTypeCombo.setModel(new javax.swing.DefaultComboBoxModel(FONT_NAMES));
        nameFontTypeCombo.setSelectedItem(getKeyForFont(PdfOutput.DEFAULT_NAME_FONT_TYPE));
        fontsFieldPanel.add(nameFontTypeCombo);

        legendLabel.setText("Legend Font");
        fontsFieldPanel.add(legendLabel);

        legendfontSizeSpinner.setValue(PdfOutput.DEFAULT_LEGEND_FONT_SIZE);
        fontsFieldPanel.add(legendfontSizeSpinner);

        legendFontTypeCombo.setModel(new javax.swing.DefaultComboBoxModel(FONT_NAMES));
        legendFontTypeCombo.setSelectedItem(getKeyForFont(PdfOutput.DEFAULT_LEGEND_FONT_TYPE));
        fontsFieldPanel.add(legendFontTypeCombo);

        rulesLabel.setText("Rules Font");
        fontsFieldPanel.add(rulesLabel);

        rulesfontSizeSpinner.setValue(PdfOutput.DEFAULT_RULES_FONT_SIZE);
        fontsFieldPanel.add(rulesfontSizeSpinner);

        rulesFontTypeCombo.setModel(new javax.swing.DefaultComboBoxModel(FONT_NAMES));
        rulesFontTypeCombo.setSelectedItem(getKeyForFont(PdfOutput.DEFAULT_RULES_FONT_TYPE));
        fontsFieldPanel.add(rulesFontTypeCombo);

        costLabel.setText("Cost Font");
        fontsFieldPanel.add(costLabel);

        costfontSizeSpinner.setValue(PdfOutput.DEFAULT_COST_FONT_SIZE);
        fontsFieldPanel.add(costfontSizeSpinner);

        costFontTypeCombo.setModel(new javax.swing.DefaultComboBoxModel(FONT_NAMES));
        costFontTypeCombo.setSelectedItem(getKeyForFont(PdfOutput.DEFAULT_COST_FONT_TYPE));
        fontsFieldPanel.add(costFontTypeCombo);

        fontsPanel.add(fontsFieldPanel);

        centralPanel.add(fontsPanel);

        cardSettingsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Card settings"));

        jPanel4.setLayout(new java.awt.GridLayout(2, 1, 3, 1));

        cardBorderCheckBox.setSelected(true);
        cardBorderCheckBox.setText("Card Outer Border");
        jPanel4.add(cardBorderCheckBox);

        titleBarsCheckBox.setSelected(true);
        titleBarsCheckBox.setText("Title Bars");
        jPanel4.add(titleBarsCheckBox);

        cardSettingsPanel.add(jPanel4);

        centralPanel.add(cardSettingsPanel);

        getContentPane().add(centralPanel, java.awt.BorderLayout.CENTER);

        savePanel.setLayout(new javax.swing.BoxLayout(savePanel, javax.swing.BoxLayout.LINE_AXIS));
        savePanel.add(filler4);

        saveButton.setText("Create PDF");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });
        savePanel.add(saveButton);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        savePanel.add(cancelButton);

        getContentPane().add(savePanel, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        JFileChooser chooser = createPdfFileChooser();
        int returnVal = chooser.showSaveDialog(this);
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File file = MainWindow.getChooserSelectedFile(chooser, "pdf");
        Callable<Void> callable = () -> {
            PdfOutput output = new PdfOutput.Builder()
                    .setPerX(toInt(cardsPerXSpinner.getValue()))
                    .setPerY(toInt(cardsPerYSpinner.getValue()))
                    .setMarginPercentX(toFloat(marginXSpinner.getValue()))
                    .setMarginPercentY(toFloat(marginYSpinner.getValue()))
                    .setTitleFontSize(toFloat(titlefontSizeSpinner.getValue()))
                    .setNameFontSize(toFloat(namefontSizeSpinner.getValue()))
                    .setLegendFontSize(toFloat(legendfontSizeSpinner.getValue()))
                    .setRulesFontSize(toFloat(rulesfontSizeSpinner.getValue()))
                    .setCostFontSize(toFloat(costfontSizeSpinner.getValue()))
                    .setTitleFontType(getFontForKey(titleFontTypeCombo.getSelectedItem()))
                    .setNameFontType(getFontForKey(nameFontTypeCombo.getSelectedItem()))
                    .setLegendFontType(getFontForKey(legendFontTypeCombo.getSelectedItem()))
                    .setRulesFontType(getFontForKey(rulesFontTypeCombo.getSelectedItem()))
                    .setCostFontType(getFontForKey(costFontTypeCombo.getSelectedItem()))
                    .setCardBorders(cardBorderCheckBox.isSelected())
                    .setTitleBars(titleBarsCheckBox.isSelected())
                    .build();
            output.build(new FileOutputStream(file), cards);
            dispose();
            return null;
        };
        WaitingDialog.show((Frame) getParent(), "Creating pdf...", callable);
    }//GEN-LAST:event_saveButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    public JFileChooser createPdfFileChooser() {

        JFileChooser chooser = new JFileChooser();
        if (actualFile != null) {
            String filename = actualFile.getName();
            int lio = filename.lastIndexOf('.');
            filename = ((lio < 0) ? filename : filename.substring(0, lio)).concat(".pdf");
            File f = new File(actualFile.getParent(), filename);
            chooser.setSelectedFile(f);
        }
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "PDF file", "pdf");
        chooser.setFileFilter(filter);
        return chooser;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JCheckBox cardBorderCheckBox;
    private javax.swing.JPanel cardSettingsPanel;
    private javax.swing.JLabel cardsPerXLabel;
    private javax.swing.JSpinner cardsPerXSpinner;
    private javax.swing.JLabel cardsPerYLabel;
    private javax.swing.JSpinner cardsPerYSpinner;
    private javax.swing.JPanel centralPanel;
    private javax.swing.JComboBox<String> costFontTypeCombo;
    private javax.swing.JLabel costLabel;
    private javax.swing.JSpinner costfontSizeSpinner;
    private javax.swing.Box.Filler filler4;
    private javax.swing.JPanel fontsFieldPanel;
    private javax.swing.JPanel fontsPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JComboBox<String> legendFontTypeCombo;
    private javax.swing.JLabel legendLabel;
    private javax.swing.JSpinner legendfontSizeSpinner;
    private javax.swing.JLabel marginXLabel;
    private javax.swing.JSpinner marginXSpinner;
    private javax.swing.JLabel marginYLabel;
    private javax.swing.JSpinner marginYSpinner;
    private javax.swing.JComboBox<String> nameFontTypeCombo;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JSpinner namefontSizeSpinner;
    private javax.swing.JPanel pageFieldPanel;
    private javax.swing.JPanel pagePanel;
    private javax.swing.JComboBox<String> rulesFontTypeCombo;
    private javax.swing.JLabel rulesLabel;
    private javax.swing.JSpinner rulesfontSizeSpinner;
    private javax.swing.JButton saveButton;
    private javax.swing.JPanel savePanel;
    private javax.swing.JCheckBox titleBarsCheckBox;
    private javax.swing.JComboBox<String> titleFontTypeCombo;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JSpinner titlefontSizeSpinner;
    // End of variables declaration//GEN-END:variables
}
