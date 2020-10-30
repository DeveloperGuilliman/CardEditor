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

import com.developerguilliman.cardEditor.data.CardCollectionData;
import com.developerguilliman.cardEditor.output.PdfOutput;
import java.awt.Color;
import java.awt.Frame;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JSpinner;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 *
 * @author DeveloperGuilliman
 */
public class PdfCreateOptionsDialog extends javax.swing.JDialog {

    public static final String[] FONT_NAMES;
    public static final PDFont[] FONT_TYPES;

    public static final String[] PAGE_SIZE_NAMES;
    public static final PDRectangle[] PAGE_SIZE_RECTANGLE;

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
        ArrayList<String> fontNames = new ArrayList<>(fonts.size());
        ArrayList<PDFont> fontTypes = new ArrayList<>(fonts.size());
        for (Map.Entry<String, PDFont> e : fonts.entrySet()) {
            fontNames.add(e.getKey());
            fontTypes.add(e.getValue());
        }
        FONT_NAMES = fontNames.toArray(new String[0]);
        FONT_TYPES = fontTypes.toArray(new PDFont[0]);

        LinkedHashMap<String, PDRectangle> pageSizes = new LinkedHashMap<>();
        pageSizes.put("A0", PDRectangle.A0);
        pageSizes.put("A1", PDRectangle.A1);
        pageSizes.put("A2", PDRectangle.A2);
        pageSizes.put("A3", PDRectangle.A3);
        pageSizes.put("A4", PDRectangle.A4);
        pageSizes.put("Legal", PDRectangle.LEGAL);
        pageSizes.put("Letter", PDRectangle.LETTER);

        ArrayList<String> pageSizeNames = new ArrayList<>(pageSizes.size());
        ArrayList<PDRectangle> pageSizeRectangles = new ArrayList<>(pageSizes.size());
        for (Map.Entry<String, PDRectangle> e : pageSizes.entrySet()) {
            pageSizeNames.add(e.getKey());
            pageSizeRectangles.add(e.getValue());
        }
        PAGE_SIZE_NAMES = pageSizeNames.toArray(new String[0]);
        PAGE_SIZE_RECTANGLE = pageSizeRectangles.toArray(new PDRectangle[0]);

    }

    private static String getFontName(PDFont font) {

        for (int i = 0; i < FONT_TYPES.length; i++) {
            if (FONT_TYPES[i].equals(font)) {
                return FONT_NAMES[i];
            }
        }
        return null;
    }

    private static PDFont getFontType(Object o) {

        for (int i = 0; i < FONT_NAMES.length; i++) {
            if (FONT_NAMES[i].equals(o)) {
                return FONT_TYPES[i];
            }
        }
        return null;
    }

    private static String getPageName(PDRectangle rect) {

        for (int i = 0; i < PAGE_SIZE_RECTANGLE.length; i++) {
            if (PAGE_SIZE_RECTANGLE[i].equals(rect)) {
                return PAGE_SIZE_NAMES[i];
            }
        }
        return null;
    }

    private static PDRectangle getPageSize(Object o) {

        for (int i = 0; i < PAGE_SIZE_NAMES.length; i++) {
            if (PAGE_SIZE_NAMES[i].equals(o)) {
                return PAGE_SIZE_RECTANGLE[i];
            }
        }
        return null;
    }

    private static int toInt(JSpinner spinner) {
        return ((Number) spinner.getValue()).intValue();
    }

    private static float toFloat(JSpinner spinner) {
        return ((Number) spinner.getValue()).floatValue();
    }

    private static Color getColorIfChecked(JCheckBox checkBox, JColorChooserButton colorButton) {
        return checkBox.isSelected() ? colorButton.getSelectedColor() : null;
    }
    private final File actualFile;
    private final CardCollectionData cards;

    /**
     * Creates new form PdfCreateOptionsDialog
     *
     * @param parent
     * @param actualFile
     * @param cards
     */
    public PdfCreateOptionsDialog(java.awt.Frame parent, File actualFile, CardCollectionData cards) {
        super(parent, true);
        this.actualFile = actualFile;
        this.cards = cards;
        initComponents();

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
        pagePanel = new javax.swing.JPanel();
        pageFieldPanel = new javax.swing.JPanel();
        pageSizeLabel = new javax.swing.JLabel();
        pageSizeComboBox = new javax.swing.JComboBox<>();
        cardsPerXLabel = new javax.swing.JLabel();
        cardsPerXSpinner = new javax.swing.JSpinner();
        cardsPerYLabel = new javax.swing.JLabel();
        cardsPerYSpinner = new javax.swing.JSpinner();
        marginXLabel = new javax.swing.JLabel();
        marginXSpinner = new javax.swing.JSpinner();
        marginYLabel = new javax.swing.JLabel();
        marginYSpinner = new javax.swing.JSpinner();
        backgroundPagesCheckBox = new javax.swing.JCheckBox();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        foregroundGridCheckBox = new javax.swing.JCheckBox();
        backgroundGridCheckBox = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        gridColorChooserButton = new com.developerguilliman.cardEditor.gui.JColorChooserButton();
        centralPanel = new javax.swing.JPanel();
        fontsPanel = new javax.swing.JPanel();
        fontsFieldPanel = new javax.swing.JPanel();
        elementsHeaderLabel = new javax.swing.JLabel();
        fontSizeHeaderLabel = new javax.swing.JLabel();
        fontTypeHeaderLabel = new javax.swing.JLabel();
        fontColorHeaderLabel = new javax.swing.JLabel();
        titleLabel = new javax.swing.JLabel();
        titlefontSizeSpinner = new javax.swing.JSpinner();
        titleFontTypeCombo = new javax.swing.JComboBox<>();
        titleColorChooserButton = new com.developerguilliman.cardEditor.gui.JColorChooserButton();
        nameLabel = new javax.swing.JLabel();
        namefontSizeSpinner = new javax.swing.JSpinner();
        nameFontTypeCombo = new javax.swing.JComboBox<>();
        nameColorChooserButton = new com.developerguilliman.cardEditor.gui.JColorChooserButton();
        legendLabel = new javax.swing.JLabel();
        legendfontSizeSpinner = new javax.swing.JSpinner();
        legendFontTypeCombo = new javax.swing.JComboBox<>();
        legendColorChooserButton = new com.developerguilliman.cardEditor.gui.JColorChooserButton();
        rulesLabel = new javax.swing.JLabel();
        rulesfontSizeSpinner = new javax.swing.JSpinner();
        rulesFontTypeCombo = new javax.swing.JComboBox<>();
        rulesColorChooserButton = new com.developerguilliman.cardEditor.gui.JColorChooserButton();
        costLabel = new javax.swing.JLabel();
        costfontSizeSpinner = new javax.swing.JSpinner();
        costFontTypeCombo = new javax.swing.JComboBox<>();
        costColorChooserButton = new com.developerguilliman.cardEditor.gui.JColorChooserButton();
        cardSettingsPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        titleBarsCheckBox = new javax.swing.JCheckBox();
        titleBarsColorLabel = new javax.swing.JLabel();
        titleBarsColorChooserButton = new com.developerguilliman.cardEditor.gui.JColorChooserButton();
        outerBorderCheckBox = new javax.swing.JCheckBox();
        outerBorderColorLabel = new javax.swing.JLabel();
        outerBorderColorChooserButton = new com.developerguilliman.cardEditor.gui.JColorChooserButton();
        fillCheckBox = new javax.swing.JCheckBox();
        fillColorLabel = new javax.swing.JLabel();
        fillColorChooserButton = new com.developerguilliman.cardEditor.gui.JColorChooserButton();
        savePanel = new javax.swing.JPanel();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Create PDF");
        setResizable(false);

        pagePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Page settings"));
        pagePanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        pageFieldPanel.setLayout(new java.awt.GridLayout(8, 2, 3, 1));

        pageSizeLabel.setText("Page size");
        pageFieldPanel.add(pageSizeLabel);

        pageSizeComboBox.setModel(new javax.swing.DefaultComboBoxModel(PAGE_SIZE_NAMES));
        pageSizeComboBox.setSelectedItem(getPageName(PdfOutput.DEFAULT_PAGE_SIZE));
        pageFieldPanel.add(pageSizeComboBox);

        cardsPerXLabel.setLabelFor(cardsPerXSpinner);
        cardsPerXLabel.setText("Cards X");
        pageFieldPanel.add(cardsPerXLabel);

        cardsPerXSpinner.setModel(new javax.swing.SpinnerNumberModel(3, 1, 12, 1));
        pageFieldPanel.add(cardsPerXSpinner);

        cardsPerYLabel.setLabelFor(cardsPerYSpinner);
        cardsPerYLabel.setText("Cards Y");
        pageFieldPanel.add(cardsPerYLabel);

        cardsPerYSpinner.setModel(new javax.swing.SpinnerNumberModel(3, 1, 12, 1));
        pageFieldPanel.add(cardsPerYSpinner);

        marginXLabel.setText("Margin X %");
        pageFieldPanel.add(marginXLabel);

        marginXSpinner.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(10.0f), Float.valueOf(0.0f), Float.valueOf(50.0f), Float.valueOf(0.1f)));
        pageFieldPanel.add(marginXSpinner);

        marginYLabel.setText("Margin Y %");
        pageFieldPanel.add(marginYLabel);

        marginYSpinner.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(10.0f), Float.valueOf(0.0f), Float.valueOf(50.0f), Float.valueOf(0.1f)));
        pageFieldPanel.add(marginYSpinner);

        backgroundPagesCheckBox.setText("Background pages");
        backgroundPagesCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backgroundPagesCheckBoxActionPerformed(evt);
            }
        });
        pageFieldPanel.add(backgroundPagesCheckBox);
        pageFieldPanel.add(filler1);

        foregroundGridCheckBox.setSelected(true);
        foregroundGridCheckBox.setText("Foreground Grid");
        pageFieldPanel.add(foregroundGridCheckBox);

        backgroundGridCheckBox.setText("Background Grid");
        backgroundGridCheckBox.setEnabled(false);
        pageFieldPanel.add(backgroundGridCheckBox);

        jLabel1.setText("Grid color");
        pageFieldPanel.add(jLabel1);

        gridColorChooserButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        gridColorChooserButton.setSelectedColor(new java.awt.Color(231, 231, 231));
        gridColorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gridColorChooserButtonActionPerformed(evt);
            }
        });
        pageFieldPanel.add(gridColorChooserButton);

        pagePanel.add(pageFieldPanel);

        getContentPane().add(pagePanel, java.awt.BorderLayout.WEST);

        centralPanel.setLayout(new javax.swing.BoxLayout(centralPanel, javax.swing.BoxLayout.Y_AXIS));

        fontsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Text settings"));
        fontsPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        fontsFieldPanel.setLayout(new java.awt.GridLayout(6, 4, 3, 1));

        elementsHeaderLabel.setText("Element:");
        fontsFieldPanel.add(elementsHeaderLabel);

        fontSizeHeaderLabel.setText("Font size:");
        fontsFieldPanel.add(fontSizeHeaderLabel);

        fontTypeHeaderLabel.setText("Font type:");
        fontsFieldPanel.add(fontTypeHeaderLabel);

        fontColorHeaderLabel.setText("Font color:");
        fontsFieldPanel.add(fontColorHeaderLabel);

        titleLabel.setText("Title");
        fontsFieldPanel.add(titleLabel);

        titlefontSizeSpinner.setModel(new javax.swing.SpinnerNumberModel(1.0f, 1.0f, null, 0.5f));
        titlefontSizeSpinner.setValue(PdfOutput.DEFAULT_TITLE_FONT_SIZE);
        fontsFieldPanel.add(titlefontSizeSpinner);

        titleFontTypeCombo.setModel(new javax.swing.DefaultComboBoxModel(FONT_NAMES));
        titleFontTypeCombo.setSelectedItem(getFontName(PdfOutput.DEFAULT_TITLE_FONT_TYPE));
        fontsFieldPanel.add(titleFontTypeCombo);

        titleColorChooserButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        titleColorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                titleColorChooserButtonActionPerformed(evt);
            }
        });
        fontsFieldPanel.add(titleColorChooserButton);

        nameLabel.setText("Name");
        fontsFieldPanel.add(nameLabel);

        namefontSizeSpinner.setModel(new javax.swing.SpinnerNumberModel(1.0f, 1.0f, null, 0.5f));
        namefontSizeSpinner.setValue(PdfOutput.DEFAULT_NAME_FONT_SIZE);
        fontsFieldPanel.add(namefontSizeSpinner);

        nameFontTypeCombo.setModel(new javax.swing.DefaultComboBoxModel(FONT_NAMES));
        nameFontTypeCombo.setSelectedItem(getFontName(PdfOutput.DEFAULT_NAME_FONT_TYPE));
        fontsFieldPanel.add(nameFontTypeCombo);

        nameColorChooserButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        nameColorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameColorChooserButtonActionPerformed(evt);
            }
        });
        fontsFieldPanel.add(nameColorChooserButton);

        legendLabel.setText("Legend");
        fontsFieldPanel.add(legendLabel);

        legendfontSizeSpinner.setModel(new javax.swing.SpinnerNumberModel(1.0f, 1.0f, null, 0.5f));
        legendfontSizeSpinner.setValue(PdfOutput.DEFAULT_LEGEND_FONT_SIZE);
        fontsFieldPanel.add(legendfontSizeSpinner);

        legendFontTypeCombo.setModel(new javax.swing.DefaultComboBoxModel(FONT_NAMES));
        legendFontTypeCombo.setSelectedItem(getFontName(PdfOutput.DEFAULT_LEGEND_FONT_TYPE));
        fontsFieldPanel.add(legendFontTypeCombo);

        legendColorChooserButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        legendColorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                legendColorChooserButtonActionPerformed(evt);
            }
        });
        fontsFieldPanel.add(legendColorChooserButton);

        rulesLabel.setText("Rules");
        fontsFieldPanel.add(rulesLabel);

        rulesfontSizeSpinner.setModel(new javax.swing.SpinnerNumberModel(1.0f, 1.0f, null, 0.5f));
        rulesfontSizeSpinner.setValue(PdfOutput.DEFAULT_RULES_FONT_SIZE);
        fontsFieldPanel.add(rulesfontSizeSpinner);

        rulesFontTypeCombo.setModel(new javax.swing.DefaultComboBoxModel(FONT_NAMES));
        rulesFontTypeCombo.setSelectedItem(getFontName(PdfOutput.DEFAULT_RULES_FONT_TYPE));
        fontsFieldPanel.add(rulesFontTypeCombo);

        rulesColorChooserButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        rulesColorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rulesColorChooserButtonActionPerformed(evt);
            }
        });
        fontsFieldPanel.add(rulesColorChooserButton);

        costLabel.setText("Cost");
        fontsFieldPanel.add(costLabel);

        costfontSizeSpinner.setModel(new javax.swing.SpinnerNumberModel(1.0f, 1.0f, null, 0.5f));
        costfontSizeSpinner.setValue(PdfOutput.DEFAULT_COST_FONT_SIZE);
        fontsFieldPanel.add(costfontSizeSpinner);

        costFontTypeCombo.setModel(new javax.swing.DefaultComboBoxModel(FONT_NAMES));
        costFontTypeCombo.setSelectedItem(getFontName(PdfOutput.DEFAULT_COST_FONT_TYPE));
        fontsFieldPanel.add(costFontTypeCombo);

        costColorChooserButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        costColorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                costColorChooserButtonActionPerformed(evt);
            }
        });
        fontsFieldPanel.add(costColorChooserButton);

        fontsPanel.add(fontsFieldPanel);

        centralPanel.add(fontsPanel);

        cardSettingsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Card settings"));
        cardSettingsPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jPanel4.setLayout(new java.awt.GridLayout(3, 3, 3, 1));

        titleBarsCheckBox.setSelected(true);
        titleBarsCheckBox.setText("Title Bars");
        jPanel4.add(titleBarsCheckBox);

        titleBarsColorLabel.setText("Title bars color");
        jPanel4.add(titleBarsColorLabel);

        titleBarsColorChooserButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        titleBarsColorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                titleBarsColorChooserButtonActionPerformed(evt);
            }
        });
        jPanel4.add(titleBarsColorChooserButton);

        outerBorderCheckBox.setSelected(true);
        outerBorderCheckBox.setText("Outer Border");
        jPanel4.add(outerBorderCheckBox);

        outerBorderColorLabel.setText("Outer Border color");
        jPanel4.add(outerBorderColorLabel);

        outerBorderColorChooserButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        outerBorderColorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                outerBorderColorChooserButtonActionPerformed(evt);
            }
        });
        jPanel4.add(outerBorderColorChooserButton);

        fillCheckBox.setText("Fill Card");
        jPanel4.add(fillCheckBox);

        fillColorLabel.setText("Fill color");
        jPanel4.add(fillColorLabel);

        fillColorChooserButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        fillColorChooserButton.setSelectedColor(new java.awt.Color(255, 255, 255));
        fillColorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fillColorChooserButtonActionPerformed(evt);
            }
        });
        jPanel4.add(fillColorChooserButton);

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
                    .setPageSize(getPageSize(pageSizeComboBox.getSelectedItem()))
                    .setPerX(toInt(cardsPerXSpinner))
                    .setPerY(toInt(cardsPerYSpinner))
                    .setMarginPercentX(toFloat(marginXSpinner))
                    .setMarginPercentY(toFloat(marginYSpinner))
                    .setTitleFontSize(toFloat(titlefontSizeSpinner))
                    .setNameFontSize(toFloat(namefontSizeSpinner))
                    .setLegendFontSize(toFloat(legendfontSizeSpinner))
                    .setRulesFontSize(toFloat(rulesfontSizeSpinner))
                    .setCostFontSize(toFloat(costfontSizeSpinner))
                    .setTitleFontType(getFontType(titleFontTypeCombo.getSelectedItem()))
                    .setNameFontType(getFontType(nameFontTypeCombo.getSelectedItem()))
                    .setLegendFontType(getFontType(legendFontTypeCombo.getSelectedItem()))
                    .setRulesFontType(getFontType(rulesFontTypeCombo.getSelectedItem()))
                    .setCostFontType(getFontType(costFontTypeCombo.getSelectedItem()))
                    .setTitleFontColor(titleColorChooserButton.getSelectedColor())
                    .setNameFontColor(nameColorChooserButton.getSelectedColor())
                    .setLegendFontColor(legendColorChooserButton.getSelectedColor())
                    .setRulesFontColor(rulesColorChooserButton.getSelectedColor())
                    .setCostFontColor(costColorChooserButton.getSelectedColor())
                    .setCardBordersColor(getColorIfChecked(outerBorderCheckBox, outerBorderColorChooserButton))
                    .setTitleBarsColor(getColorIfChecked(titleBarsCheckBox, titleBarsColorChooserButton))
                    .setCardFillColor(getColorIfChecked(fillCheckBox, fillColorChooserButton))
                    .setForegroundGridColor(getColorIfChecked(foregroundGridCheckBox, gridColorChooserButton))
                    .setBackgroundGridColor(getColorIfChecked(backgroundGridCheckBox, gridColorChooserButton))
                    .setBackgroundPages(backgroundPagesCheckBox.isSelected())
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

    private void titleColorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_titleColorChooserButtonActionPerformed
        titleColorChooserButton.openSelectColorDialog(this, "Select title font color...");
    }//GEN-LAST:event_titleColorChooserButtonActionPerformed

    private void nameColorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameColorChooserButtonActionPerformed
        nameColorChooserButton.openSelectColorDialog(this, "Select name font color...");
    }//GEN-LAST:event_nameColorChooserButtonActionPerformed

    private void legendColorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_legendColorChooserButtonActionPerformed
        legendColorChooserButton.openSelectColorDialog(this, "Select legend font color...");
    }//GEN-LAST:event_legendColorChooserButtonActionPerformed

    private void rulesColorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rulesColorChooserButtonActionPerformed
        rulesColorChooserButton.openSelectColorDialog(this, "Select rules font color...");
    }//GEN-LAST:event_rulesColorChooserButtonActionPerformed

    private void costColorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_costColorChooserButtonActionPerformed
        costColorChooserButton.openSelectColorDialog(this, "Select cost font color...");
    }//GEN-LAST:event_costColorChooserButtonActionPerformed

    private void outerBorderColorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_outerBorderColorChooserButtonActionPerformed
        if (outerBorderColorChooserButton.openSelectColorDialog(this, "Select outer color...") != null) {
            outerBorderCheckBox.setSelected(true);
        }
    }//GEN-LAST:event_outerBorderColorChooserButtonActionPerformed

    private void titleBarsColorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_titleBarsColorChooserButtonActionPerformed
        if (titleBarsColorChooserButton.openSelectColorDialog(this, "Select title bars color...") != null) {
            titleBarsCheckBox.setSelected(true);
        }
    }//GEN-LAST:event_titleBarsColorChooserButtonActionPerformed

    private void fillColorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fillColorChooserButtonActionPerformed
        if (fillColorChooserButton.openSelectColorDialog(this, "Select fill color...") != null) {
            fillCheckBox.setSelected(true);
        }
    }//GEN-LAST:event_fillColorChooserButtonActionPerformed

    private void gridColorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gridColorChooserButtonActionPerformed
        gridColorChooserButton.openSelectColorDialog(this, "Select grid color...");
    }//GEN-LAST:event_gridColorChooserButtonActionPerformed

    private void backgroundPagesCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backgroundPagesCheckBoxActionPerformed
        backgroundGridCheckBox.setEnabled(backgroundPagesCheckBox.isSelected());
    }//GEN-LAST:event_backgroundPagesCheckBoxActionPerformed

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
    private javax.swing.JCheckBox backgroundGridCheckBox;
    private javax.swing.JCheckBox backgroundPagesCheckBox;
    private javax.swing.JButton cancelButton;
    private javax.swing.JPanel cardSettingsPanel;
    private javax.swing.JLabel cardsPerXLabel;
    private javax.swing.JSpinner cardsPerXSpinner;
    private javax.swing.JLabel cardsPerYLabel;
    private javax.swing.JSpinner cardsPerYSpinner;
    private javax.swing.JPanel centralPanel;
    private com.developerguilliman.cardEditor.gui.JColorChooserButton costColorChooserButton;
    private javax.swing.JComboBox<String> costFontTypeCombo;
    private javax.swing.JLabel costLabel;
    private javax.swing.JSpinner costfontSizeSpinner;
    private javax.swing.JLabel elementsHeaderLabel;
    private javax.swing.JCheckBox fillCheckBox;
    private com.developerguilliman.cardEditor.gui.JColorChooserButton fillColorChooserButton;
    private javax.swing.JLabel fillColorLabel;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler4;
    private javax.swing.JLabel fontColorHeaderLabel;
    private javax.swing.JLabel fontSizeHeaderLabel;
    private javax.swing.JLabel fontTypeHeaderLabel;
    private javax.swing.JPanel fontsFieldPanel;
    private javax.swing.JPanel fontsPanel;
    private javax.swing.JCheckBox foregroundGridCheckBox;
    private com.developerguilliman.cardEditor.gui.JColorChooserButton gridColorChooserButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private com.developerguilliman.cardEditor.gui.JColorChooserButton legendColorChooserButton;
    private javax.swing.JComboBox<String> legendFontTypeCombo;
    private javax.swing.JLabel legendLabel;
    private javax.swing.JSpinner legendfontSizeSpinner;
    private javax.swing.JLabel marginXLabel;
    private javax.swing.JSpinner marginXSpinner;
    private javax.swing.JLabel marginYLabel;
    private javax.swing.JSpinner marginYSpinner;
    private com.developerguilliman.cardEditor.gui.JColorChooserButton nameColorChooserButton;
    private javax.swing.JComboBox<String> nameFontTypeCombo;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JSpinner namefontSizeSpinner;
    private javax.swing.JCheckBox outerBorderCheckBox;
    private com.developerguilliman.cardEditor.gui.JColorChooserButton outerBorderColorChooserButton;
    private javax.swing.JLabel outerBorderColorLabel;
    private javax.swing.JPanel pageFieldPanel;
    private javax.swing.JPanel pagePanel;
    private javax.swing.JComboBox<String> pageSizeComboBox;
    private javax.swing.JLabel pageSizeLabel;
    private com.developerguilliman.cardEditor.gui.JColorChooserButton rulesColorChooserButton;
    private javax.swing.JComboBox<String> rulesFontTypeCombo;
    private javax.swing.JLabel rulesLabel;
    private javax.swing.JSpinner rulesfontSizeSpinner;
    private javax.swing.JButton saveButton;
    private javax.swing.JPanel savePanel;
    private javax.swing.JCheckBox titleBarsCheckBox;
    private com.developerguilliman.cardEditor.gui.JColorChooserButton titleBarsColorChooserButton;
    private javax.swing.JLabel titleBarsColorLabel;
    private com.developerguilliman.cardEditor.gui.JColorChooserButton titleColorChooserButton;
    private javax.swing.JComboBox<String> titleFontTypeCombo;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JSpinner titlefontSizeSpinner;
    // End of variables declaration//GEN-END:variables
}
