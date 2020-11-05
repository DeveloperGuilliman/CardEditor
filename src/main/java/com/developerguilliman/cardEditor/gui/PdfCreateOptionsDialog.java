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

import com.developerguilliman.cardEditor.data.CardCollectionData;
import com.developerguilliman.cardEditor.output.PdfOutput;
import com.developerguilliman.cardEditor.warning.WarningArrayList;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Frame;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JSpinner;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 *
 * @author Developer Guilliman <developerguilliman@gmail.com>
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

    private static final float POINTS_PER_INCH = 72;

    private static final float POINTS_PER_MM = 1 / (10 * 2.54f) * POINTS_PER_INCH;

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

    private static void setCheckBoxAndColor(Color color, JCheckBox checkBox, JColorChooserButton colorButton) {
        if (color != null) {
            checkBox.setSelected(true);
            colorButton.setSelectedColor(color);
        } else {
            checkBox.setSelected(false);
            colorButton.setSelectedColor(Color.WHITE);
        }

    }

    private final File actualFile;
    private final CardCollectionData cards;
    private final PdfOutput.Builder builder;

    /**
     * Creates new form PdfCreateOptionsDialog
     *
     * @param parent
     * @param actualFile
     * @param cards
     */
    public PdfCreateOptionsDialog(java.awt.Frame parent, File actualFile, CardCollectionData cards, PdfOutput.Builder builder) {
        super(parent, true);
        this.actualFile = actualFile;
        this.cards = cards;
        this.builder = builder;
        initComponents();
        setDialogValuesFromBuider();
        saveButton.requestFocus();
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
        pageFieldPanel1 = new javax.swing.JPanel();
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
        cardSizeMMLabel = new javax.swing.JLabel();
        cardSizeMMTextField = new javax.swing.JTextField();
        cardSizeInchLabel = new javax.swing.JLabel();
        cardSizeInchTextField = new javax.swing.JTextField();
        pageFieldPanel2 = new javax.swing.JPanel();
        backgroundPagesCheckBox = new javax.swing.JCheckBox();
        foregroundGridCheckBox = new javax.swing.JCheckBox();
        backgroundGridCheckBox = new javax.swing.JCheckBox();
        pageFieldPanel3 = new javax.swing.JPanel();
        gridColorLabel = new javax.swing.JLabel();
        gridColorChooserButton = new com.developerguilliman.cardEditor.gui.JColorChooserButton();
        pageFieldPanel4 = new javax.swing.JPanel();
        fillUnusedCardSlotsCheckBox = new javax.swing.JCheckBox();
        fillUnusedCardSlotsBordersCheckBox = new javax.swing.JCheckBox();
        fillUnusedCardSlotsTitleBarsCheckBox = new javax.swing.JCheckBox();
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
        costValueLabel = new javax.swing.JLabel();
        costValueFontSizeSpinner = new javax.swing.JSpinner();
        costValueFontTypeCombo = new javax.swing.JComboBox<>();
        costValueColorChooserButton = new com.developerguilliman.cardEditor.gui.JColorChooserButton();
        costTypeLabel = new javax.swing.JLabel();
        costTypeFontSizeSpinner = new javax.swing.JSpinner();
        costTypeFontTypeCombo = new javax.swing.JComboBox<>();
        costTypeColorChooserButton = new com.developerguilliman.cardEditor.gui.JColorChooserButton();
        cardSettingsPanel = new javax.swing.JPanel();
        cardSettingsFieldsPanel = new javax.swing.JPanel();
        cardBackgroundCheckBox = new javax.swing.JCheckBox();
        cardBackgroundLabel = new javax.swing.JLabel();
        cardBackgroundColorChooserButton = new com.developerguilliman.cardEditor.gui.JColorChooserButton();
        titleBarsCheckBox = new javax.swing.JCheckBox();
        titleBarsColorLabel = new javax.swing.JLabel();
        titleBarsColorChooserButton = new com.developerguilliman.cardEditor.gui.JColorChooserButton();
        outerBorderCheckBox = new javax.swing.JCheckBox();
        outerBorderColorLabel = new javax.swing.JLabel();
        outerBorderColorChooserButton = new com.developerguilliman.cardEditor.gui.JColorChooserButton();
        fillCheckBox = new javax.swing.JCheckBox();
        fillColorLabel = new javax.swing.JLabel();
        fillColorChooserButton = new com.developerguilliman.cardEditor.gui.JColorChooserButton();
        costBorderCheckBox = new javax.swing.JCheckBox();
        costBorderColorLabel = new javax.swing.JLabel();
        costBorderColorChooserButton = new com.developerguilliman.cardEditor.gui.JColorChooserButton();
        costValueFillCheckBox = new javax.swing.JCheckBox();
        costValueFillColorLabel = new javax.swing.JLabel();
        costValueFillColorChooserButton = new com.developerguilliman.cardEditor.gui.JColorChooserButton();
        costTypeFillCheckBox = new javax.swing.JCheckBox();
        costTypeFillColorLabel = new javax.swing.JLabel();
        costTypeFillColorChooserButton = new com.developerguilliman.cardEditor.gui.JColorChooserButton();
        savePanel = new javax.swing.JPanel();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        saveButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Create PDF");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        pagePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Page settings"));
        pagePanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        pageFieldPanel.setLayout(new javax.swing.BoxLayout(pageFieldPanel, javax.swing.BoxLayout.Y_AXIS));

        pageFieldPanel1.setLayout(new java.awt.GridLayout(0, 2));

        pageSizeLabel.setText("Page size");
        pageFieldPanel1.add(pageSizeLabel);

        pageSizeComboBox.setModel(new javax.swing.DefaultComboBoxModel(PAGE_SIZE_NAMES));
        pageSizeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pageSizeComboBoxActionPerformed(evt);
            }
        });
        pageFieldPanel1.add(pageSizeComboBox);

        cardsPerXLabel.setLabelFor(cardsPerXSpinner);
        cardsPerXLabel.setText("Cards X");
        pageFieldPanel1.add(cardsPerXLabel);

        cardsPerXSpinner.setModel(new javax.swing.SpinnerNumberModel(3, 1, 12, 1));
        cardsPerXSpinner.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cardsPerXSpinnerPropertyChange(evt);
            }
        });
        pageFieldPanel1.add(cardsPerXSpinner);

        cardsPerYLabel.setLabelFor(cardsPerYSpinner);
        cardsPerYLabel.setText("Cards Y");
        pageFieldPanel1.add(cardsPerYLabel);

        cardsPerYSpinner.setModel(new javax.swing.SpinnerNumberModel(3, 1, 12, 1));
        cardsPerYSpinner.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cardsPerYSpinnerPropertyChange(evt);
            }
        });
        pageFieldPanel1.add(cardsPerYSpinner);

        marginXLabel.setText("Margin X %");
        pageFieldPanel1.add(marginXLabel);

        marginXSpinner.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(10.0f), Float.valueOf(0.0f), Float.valueOf(99.0f), Float.valueOf(0.05f)));
        marginXSpinner.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                marginXSpinnerPropertyChange(evt);
            }
        });
        pageFieldPanel1.add(marginXSpinner);

        marginYLabel.setText("Margin Y %");
        pageFieldPanel1.add(marginYLabel);

        marginYSpinner.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(11.15f), Float.valueOf(0.0f), Float.valueOf(99.0f), Float.valueOf(0.05f)));
        marginYSpinner.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                marginYSpinnerPropertyChange(evt);
            }
        });
        pageFieldPanel1.add(marginYSpinner);

        cardSizeMMLabel.setText("Card Size mm:");
        pageFieldPanel1.add(cardSizeMMLabel);

        cardSizeMMTextField.setEditable(false);
        cardSizeMMTextField.setMinimumSize(new java.awt.Dimension(300, 20));
        pageFieldPanel1.add(cardSizeMMTextField);

        cardSizeInchLabel.setText("Card Size inch:");
        pageFieldPanel1.add(cardSizeInchLabel);

        cardSizeInchTextField.setEditable(false);
        pageFieldPanel1.add(cardSizeInchTextField);

        pageFieldPanel.add(pageFieldPanel1);

        pageFieldPanel2.setLayout(new java.awt.GridLayout(0, 1));

        backgroundPagesCheckBox.setText("Background pages");
        backgroundPagesCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backgroundPagesCheckBoxActionPerformed(evt);
            }
        });
        pageFieldPanel2.add(backgroundPagesCheckBox);

        foregroundGridCheckBox.setSelected(true);
        foregroundGridCheckBox.setText("Foreground Grid");
        pageFieldPanel2.add(foregroundGridCheckBox);

        backgroundGridCheckBox.setText("Background Grid");
        backgroundGridCheckBox.setEnabled(false);
        pageFieldPanel2.add(backgroundGridCheckBox);

        pageFieldPanel.add(pageFieldPanel2);

        pageFieldPanel3.setLayout(new java.awt.GridLayout(0, 2));

        gridColorLabel.setText("Grid color");
        pageFieldPanel3.add(gridColorLabel);

        gridColorChooserButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        gridColorChooserButton.setSelectedColor(new java.awt.Color(231, 231, 231));
        gridColorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gridColorChooserButtonActionPerformed(evt);
            }
        });
        pageFieldPanel3.add(gridColorChooserButton);

        pageFieldPanel.add(pageFieldPanel3);

        pageFieldPanel4.setLayout(new java.awt.GridLayout(0, 1));

        fillUnusedCardSlotsCheckBox.setText("Fill empty card slots");
        fillUnusedCardSlotsCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fillUnusedCardSlotsCheckBoxActionPerformed(evt);
            }
        });
        pageFieldPanel4.add(fillUnusedCardSlotsCheckBox);

        fillUnusedCardSlotsBordersCheckBox.setText("Empty card slots Borders");
        fillUnusedCardSlotsBordersCheckBox.setEnabled(false);
        pageFieldPanel4.add(fillUnusedCardSlotsBordersCheckBox);

        fillUnusedCardSlotsTitleBarsCheckBox.setText("Empty card slots Title bars");
        fillUnusedCardSlotsTitleBarsCheckBox.setEnabled(false);
        pageFieldPanel4.add(fillUnusedCardSlotsTitleBarsCheckBox);

        pageFieldPanel.add(pageFieldPanel4);

        pagePanel.add(pageFieldPanel);

        getContentPane().add(pagePanel, java.awt.BorderLayout.WEST);

        centralPanel.setLayout(new javax.swing.BoxLayout(centralPanel, javax.swing.BoxLayout.Y_AXIS));

        fontsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Text settings"));
        fontsPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        fontsFieldPanel.setLayout(new java.awt.GridLayout(0, 4, 3, 1));

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
        fontsFieldPanel.add(titlefontSizeSpinner);

        titleFontTypeCombo.setModel(new javax.swing.DefaultComboBoxModel(FONT_NAMES));
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
        fontsFieldPanel.add(namefontSizeSpinner);

        nameFontTypeCombo.setModel(new javax.swing.DefaultComboBoxModel(FONT_NAMES));
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
        fontsFieldPanel.add(legendfontSizeSpinner);

        legendFontTypeCombo.setModel(new javax.swing.DefaultComboBoxModel(FONT_NAMES));
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
        fontsFieldPanel.add(rulesfontSizeSpinner);

        rulesFontTypeCombo.setModel(new javax.swing.DefaultComboBoxModel(FONT_NAMES));
        fontsFieldPanel.add(rulesFontTypeCombo);

        rulesColorChooserButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        rulesColorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rulesColorChooserButtonActionPerformed(evt);
            }
        });
        fontsFieldPanel.add(rulesColorChooserButton);

        costValueLabel.setText("Cost Value");
        fontsFieldPanel.add(costValueLabel);

        costValueFontSizeSpinner.setModel(new javax.swing.SpinnerNumberModel(1.0f, 1.0f, null, 0.5f));
        fontsFieldPanel.add(costValueFontSizeSpinner);

        costValueFontTypeCombo.setModel(new javax.swing.DefaultComboBoxModel(FONT_NAMES));
        fontsFieldPanel.add(costValueFontTypeCombo);

        costValueColorChooserButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        costValueColorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                costValueColorChooserButtonActionPerformed(evt);
            }
        });
        fontsFieldPanel.add(costValueColorChooserButton);

        costTypeLabel.setText("Cost Type");
        fontsFieldPanel.add(costTypeLabel);

        costTypeFontSizeSpinner.setModel(new javax.swing.SpinnerNumberModel(1.0f, 1.0f, null, 0.5f));
        fontsFieldPanel.add(costTypeFontSizeSpinner);

        costTypeFontTypeCombo.setModel(new javax.swing.DefaultComboBoxModel(FONT_NAMES));
        fontsFieldPanel.add(costTypeFontTypeCombo);

        costTypeColorChooserButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        costTypeColorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                costTypeColorChooserButtonActionPerformed(evt);
            }
        });
        fontsFieldPanel.add(costTypeColorChooserButton);

        fontsPanel.add(fontsFieldPanel);

        centralPanel.add(fontsPanel);

        cardSettingsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Card settings"));
        cardSettingsPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        cardSettingsFieldsPanel.setLayout(new java.awt.GridLayout(0, 3, 3, 1));

        cardBackgroundCheckBox.setText("Fill card background");
        cardSettingsFieldsPanel.add(cardBackgroundCheckBox);

        cardBackgroundLabel.setText("Card background color");
        cardSettingsFieldsPanel.add(cardBackgroundLabel);

        cardBackgroundColorChooserButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cardBackgroundColorChooserButton.setSelectedColor(new java.awt.Color(255, 255, 255));
        cardBackgroundColorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardBackgroundColorChooserButtonActionPerformed(evt);
            }
        });
        cardSettingsFieldsPanel.add(cardBackgroundColorChooserButton);

        titleBarsCheckBox.setSelected(true);
        titleBarsCheckBox.setText("Title Bars");
        cardSettingsFieldsPanel.add(titleBarsCheckBox);

        titleBarsColorLabel.setText("Title bars color");
        cardSettingsFieldsPanel.add(titleBarsColorLabel);

        titleBarsColorChooserButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        titleBarsColorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                titleBarsColorChooserButtonActionPerformed(evt);
            }
        });
        cardSettingsFieldsPanel.add(titleBarsColorChooserButton);

        outerBorderCheckBox.setSelected(true);
        outerBorderCheckBox.setText("Outer Border");
        outerBorderCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                outerBorderCheckBoxActionPerformed(evt);
            }
        });
        cardSettingsFieldsPanel.add(outerBorderCheckBox);

        outerBorderColorLabel.setText("Outer Border color");
        cardSettingsFieldsPanel.add(outerBorderColorLabel);

        outerBorderColorChooserButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        outerBorderColorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                outerBorderColorChooserButtonActionPerformed(evt);
            }
        });
        cardSettingsFieldsPanel.add(outerBorderColorChooserButton);

        fillCheckBox.setText("Fill OuterBorder");
        cardSettingsFieldsPanel.add(fillCheckBox);

        fillColorLabel.setText("Fill color");
        cardSettingsFieldsPanel.add(fillColorLabel);

        fillColorChooserButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        fillColorChooserButton.setSelectedColor(new java.awt.Color(255, 255, 255));
        fillColorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fillColorChooserButtonActionPerformed(evt);
            }
        });
        cardSettingsFieldsPanel.add(fillColorChooserButton);

        costBorderCheckBox.setSelected(true);
        costBorderCheckBox.setText("Cost Border");
        costBorderCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                costBorderCheckBoxActionPerformed(evt);
            }
        });
        cardSettingsFieldsPanel.add(costBorderCheckBox);

        costBorderColorLabel.setText("Cost Border color");
        cardSettingsFieldsPanel.add(costBorderColorLabel);

        costBorderColorChooserButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        costBorderColorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                costBorderColorChooserButtonActionPerformed(evt);
            }
        });
        cardSettingsFieldsPanel.add(costBorderColorChooserButton);

        costValueFillCheckBox.setText("Fill Cost Value");
        cardSettingsFieldsPanel.add(costValueFillCheckBox);

        costValueFillColorLabel.setText("Cost Value color");
        cardSettingsFieldsPanel.add(costValueFillColorLabel);

        costValueFillColorChooserButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        costValueFillColorChooserButton.setSelectedColor(new java.awt.Color(255, 255, 255));
        costValueFillColorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                costValueFillColorChooserButtonActionPerformed(evt);
            }
        });
        cardSettingsFieldsPanel.add(costValueFillColorChooserButton);

        costTypeFillCheckBox.setText("Fill Cost Type");
        cardSettingsFieldsPanel.add(costTypeFillCheckBox);

        costTypeFillColorLabel.setText("Cost Type color");
        cardSettingsFieldsPanel.add(costTypeFillColorLabel);

        costTypeFillColorChooserButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        costTypeFillColorChooserButton.setSelectedColor(new java.awt.Color(255, 255, 255));
        costTypeFillColorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                costTypeFillColorChooserButtonActionPerformed(evt);
            }
        });
        cardSettingsFieldsPanel.add(costTypeFillColorChooserButton);

        cardSettingsPanel.add(cardSettingsFieldsPanel);

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

        jButton1.setText("Reset settings");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        savePanel.add(jButton1);

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
        Frame parent = (Frame) getParent();
        File file = MainWindow.getChooserSelectedFile(chooser, "pdf");

        WaitingDialog.show(parent, "Creating pdf...", () -> {
            WarningArrayList warningArrayList = new WarningArrayList();
            setBuilderValuesFromDialog();
            PdfOutput output = builder.build();
            output.build(new FileOutputStream(file), cards, warningArrayList);
            return warningArrayList.getWarnings();
        }, "building the pdf.\nSome cards can have missing text or have broken borders.", () -> {
            disposeAndOpenPdf(file);
        });
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

    private void costValueColorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_costValueColorChooserButtonActionPerformed
        costValueColorChooserButton.openSelectColorDialog(this, "Select cost value font color...");
    }//GEN-LAST:event_costValueColorChooserButtonActionPerformed

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
            outerBorderCheckBox.setSelected(true);
            fillCheckBox.setSelected(true);
            fillCheckBox.setEnabled(true);
        }
    }//GEN-LAST:event_fillColorChooserButtonActionPerformed

    private void gridColorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gridColorChooserButtonActionPerformed
        gridColorChooserButton.openSelectColorDialog(this, "Select grid color...");
    }//GEN-LAST:event_gridColorChooserButtonActionPerformed

    private void backgroundPagesCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backgroundPagesCheckBoxActionPerformed
        backgroundGridCheckBox.setEnabled(backgroundPagesCheckBox.isSelected());
    }//GEN-LAST:event_backgroundPagesCheckBoxActionPerformed

    private void pageSizeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pageSizeComboBoxActionPerformed
        recalculateCardSize();
    }//GEN-LAST:event_pageSizeComboBoxActionPerformed

    private void cardsPerXSpinnerPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cardsPerXSpinnerPropertyChange
        recalculateCardSize();
    }//GEN-LAST:event_cardsPerXSpinnerPropertyChange

    private void cardsPerYSpinnerPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cardsPerYSpinnerPropertyChange
        recalculateCardSize();
    }//GEN-LAST:event_cardsPerYSpinnerPropertyChange

    private void marginXSpinnerPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_marginXSpinnerPropertyChange
        recalculateCardSize();
    }//GEN-LAST:event_marginXSpinnerPropertyChange

    private void marginYSpinnerPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_marginYSpinnerPropertyChange
        recalculateCardSize();
    }//GEN-LAST:event_marginYSpinnerPropertyChange

    private void costBorderColorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_costBorderColorChooserButtonActionPerformed
        if (costBorderColorChooserButton.openSelectColorDialog(this, "Select cost border color...") != null) {
            costBorderCheckBox.setSelected(true);
        }
    }//GEN-LAST:event_costBorderColorChooserButtonActionPerformed

    private void costTypeColorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_costTypeColorChooserButtonActionPerformed
        costTypeColorChooserButton.openSelectColorDialog(this, "Select cost type font color...");
    }//GEN-LAST:event_costTypeColorChooserButtonActionPerformed

    private void costValueFillColorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_costValueFillColorChooserButtonActionPerformed

        if (costValueFillColorChooserButton.openSelectColorDialog(this, "Select cost value fill color...") != null) {
            costBorderCheckBox.setSelected(true);
            costValueFillCheckBox.setSelected(true);
            costTypeFillCheckBox.setEnabled(true);
            costValueFillCheckBox.setEnabled(true);
        }

    }//GEN-LAST:event_costValueFillColorChooserButtonActionPerformed

    private void costTypeFillColorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_costTypeFillColorChooserButtonActionPerformed
        if (costTypeFillColorChooserButton.openSelectColorDialog(this, "Select cost type fill color...") != null) {
            costBorderCheckBox.setSelected(true);
            costTypeFillCheckBox.setSelected(true);
            costTypeFillCheckBox.setEnabled(true);
            costValueFillCheckBox.setEnabled(true);
        }
    }//GEN-LAST:event_costTypeFillColorChooserButtonActionPerformed

    private void outerBorderCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_outerBorderCheckBoxActionPerformed
        fillCheckBox.setEnabled(outerBorderCheckBox.isSelected());
    }//GEN-LAST:event_outerBorderCheckBoxActionPerformed

    private void costBorderCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_costBorderCheckBoxActionPerformed
        costValueFillCheckBox.setEnabled(costBorderCheckBox.isSelected());
        costTypeFillCheckBox.setEnabled(costBorderCheckBox.isSelected());
    }//GEN-LAST:event_costBorderCheckBoxActionPerformed

    private void fillUnusedCardSlotsCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fillUnusedCardSlotsCheckBoxActionPerformed
        fillUnusedCardSlotsBordersCheckBox.setEnabled(fillUnusedCardSlotsCheckBox.isSelected());
        fillUnusedCardSlotsTitleBarsCheckBox.setEnabled(fillUnusedCardSlotsCheckBox.isSelected());
    }//GEN-LAST:event_fillUnusedCardSlotsCheckBoxActionPerformed

    private void cardBackgroundColorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cardBackgroundColorChooserButtonActionPerformed
        if (cardBackgroundColorChooserButton.openSelectColorDialog(this, "Select card background color...") != null) {
            cardBackgroundCheckBox.setSelected(true);
        }
    }//GEN-LAST:event_cardBackgroundColorChooserButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        builder.reset();
        setDialogValuesFromBuider();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        setBuilderValuesFromDialog();
    }//GEN-LAST:event_formWindowClosed

    private void recalculateCardSize() {
        PDRectangle size = getPageSize(pageSizeComboBox.getSelectedItem());
        int perX = toInt(cardsPerXSpinner);
        int perY = toInt(cardsPerYSpinner);
        float marginX = toFloat(marginXSpinner) / 100;
        float marginY = toFloat(marginYSpinner) / 100;

        float x = size.getWidth() * (1 - marginX) / perX;
        float y = size.getHeight() * (1 - marginY) / perY;
        cardSizeMMTextField.setText(String.format("%.2f x %.2f", x / POINTS_PER_MM, y / POINTS_PER_MM));
        cardSizeInchTextField.setText(String.format("%.2f x %.2f", x / POINTS_PER_INCH, y / POINTS_PER_INCH));
    }

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
    private javax.swing.JCheckBox cardBackgroundCheckBox;
    private com.developerguilliman.cardEditor.gui.JColorChooserButton cardBackgroundColorChooserButton;
    private javax.swing.JLabel cardBackgroundLabel;
    private javax.swing.JPanel cardSettingsFieldsPanel;
    private javax.swing.JPanel cardSettingsPanel;
    private javax.swing.JLabel cardSizeInchLabel;
    private javax.swing.JTextField cardSizeInchTextField;
    private javax.swing.JLabel cardSizeMMLabel;
    private javax.swing.JTextField cardSizeMMTextField;
    private javax.swing.JLabel cardsPerXLabel;
    private javax.swing.JSpinner cardsPerXSpinner;
    private javax.swing.JLabel cardsPerYLabel;
    private javax.swing.JSpinner cardsPerYSpinner;
    private javax.swing.JPanel centralPanel;
    private javax.swing.JCheckBox costBorderCheckBox;
    private com.developerguilliman.cardEditor.gui.JColorChooserButton costBorderColorChooserButton;
    private javax.swing.JLabel costBorderColorLabel;
    private com.developerguilliman.cardEditor.gui.JColorChooserButton costTypeColorChooserButton;
    private javax.swing.JCheckBox costTypeFillCheckBox;
    private com.developerguilliman.cardEditor.gui.JColorChooserButton costTypeFillColorChooserButton;
    private javax.swing.JLabel costTypeFillColorLabel;
    private javax.swing.JSpinner costTypeFontSizeSpinner;
    private javax.swing.JComboBox<String> costTypeFontTypeCombo;
    private javax.swing.JLabel costTypeLabel;
    private com.developerguilliman.cardEditor.gui.JColorChooserButton costValueColorChooserButton;
    private javax.swing.JCheckBox costValueFillCheckBox;
    private com.developerguilliman.cardEditor.gui.JColorChooserButton costValueFillColorChooserButton;
    private javax.swing.JLabel costValueFillColorLabel;
    private javax.swing.JSpinner costValueFontSizeSpinner;
    private javax.swing.JComboBox<String> costValueFontTypeCombo;
    private javax.swing.JLabel costValueLabel;
    private javax.swing.JLabel elementsHeaderLabel;
    private javax.swing.JCheckBox fillCheckBox;
    private com.developerguilliman.cardEditor.gui.JColorChooserButton fillColorChooserButton;
    private javax.swing.JLabel fillColorLabel;
    private javax.swing.JCheckBox fillUnusedCardSlotsBordersCheckBox;
    private javax.swing.JCheckBox fillUnusedCardSlotsCheckBox;
    private javax.swing.JCheckBox fillUnusedCardSlotsTitleBarsCheckBox;
    private javax.swing.Box.Filler filler4;
    private javax.swing.JLabel fontColorHeaderLabel;
    private javax.swing.JLabel fontSizeHeaderLabel;
    private javax.swing.JLabel fontTypeHeaderLabel;
    private javax.swing.JPanel fontsFieldPanel;
    private javax.swing.JPanel fontsPanel;
    private javax.swing.JCheckBox foregroundGridCheckBox;
    private com.developerguilliman.cardEditor.gui.JColorChooserButton gridColorChooserButton;
    private javax.swing.JLabel gridColorLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
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
    private javax.swing.JPanel pageFieldPanel1;
    private javax.swing.JPanel pageFieldPanel2;
    private javax.swing.JPanel pageFieldPanel3;
    private javax.swing.JPanel pageFieldPanel4;
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

    private void disposeAndOpenPdf(File pdfFile) {
        dispose();
        try {
            Desktop.getDesktop().open(pdfFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void setDialogValuesFromBuider() {
        setCheckBoxAndColor(builder.getBackgroundGridColor(), backgroundGridCheckBox, gridColorChooserButton);
        setCheckBoxAndColor(builder.getCardBackgroundColor(), cardBackgroundCheckBox, cardBackgroundColorChooserButton);
        setCheckBoxAndColor(builder.getCardBordersColor(), outerBorderCheckBox, outerBorderColorChooserButton);
        setCheckBoxAndColor(builder.getCardFillColor(), fillCheckBox, fillColorChooserButton);
        setCheckBoxAndColor(builder.getCostBordersColor(), costBorderCheckBox, costBorderColorChooserButton);
        setCheckBoxAndColor(builder.getCostTypeFillColor(), costTypeFillCheckBox, costTypeFillColorChooserButton);
        costTypeColorChooserButton.setSelectedColor(builder.getCostTypeFontColor());
        costTypeFontSizeSpinner.setValue(builder.getCostTypeFontSize());
        costTypeFontTypeCombo.setSelectedItem(getFontName(builder.getCostTypeFontType()));
        setCheckBoxAndColor(builder.getCostValueFillColor(), costValueFillCheckBox, costValueFillColorChooserButton);
        costValueColorChooserButton.setSelectedColor(builder.getCostValueFontColor());
        costValueFontSizeSpinner.setValue(builder.getCostValueFontSize());
        costValueFontTypeCombo.setSelectedItem(getFontName(builder.getCostValueFontType()));
        setCheckBoxAndColor(builder.getForegroundGridColor(), foregroundGridCheckBox, gridColorChooserButton);
        legendColorChooserButton.setSelectedColor(builder.getLegendFontColor());
        legendfontSizeSpinner.setValue(builder.getLegendFontSize());
        legendFontTypeCombo.setSelectedItem(getFontName(builder.getLegendFontType()));
        marginXSpinner.setValue(builder.getMarginPercentX());
        marginYSpinner.setValue(builder.getMarginPercentY());
        nameColorChooserButton.setSelectedColor(builder.getNameFontColor());
        namefontSizeSpinner.setValue(builder.getNameFontSize());
        nameFontTypeCombo.setSelectedItem(getFontName(builder.getNameFontType()));
        pageSizeComboBox.setSelectedItem(getPageName(builder.getPageSize()));
        cardsPerXSpinner.setValue(builder.getPerX());
        cardsPerYSpinner.setValue(builder.getPerY());
        rulesColorChooserButton.setSelectedColor(builder.getRulesFontColor());
        rulesfontSizeSpinner.setValue(builder.getRulesFontSize());
        rulesFontTypeCombo.setSelectedItem(getFontName(builder.getRulesFontType()));
        setCheckBoxAndColor(builder.getTitleBarsColor(), titleBarsCheckBox, titleBarsColorChooserButton);
        titleColorChooserButton.setSelectedColor(builder.getTitleFontColor());
        titlefontSizeSpinner.setValue(builder.getTitleFontSize());
        titleFontTypeCombo.setSelectedItem(getFontName(builder.getTitleFontType()));

        backgroundPagesCheckBox.setSelected(builder.isBackgroundPages());
        fillUnusedCardSlotsCheckBox.setSelected(builder.isFillUnusedCardSlots());
        fillUnusedCardSlotsBordersCheckBox.setSelected(builder.isFillUnusedCardSlotsBorders());
        fillUnusedCardSlotsTitleBarsCheckBox.setSelected(builder.isFillUnusedCardSlotsTitles());

        backgroundGridCheckBox.setEnabled(backgroundPagesCheckBox.isSelected());
        fillUnusedCardSlotsBordersCheckBox.setEnabled(fillUnusedCardSlotsCheckBox.isSelected());
        fillUnusedCardSlotsTitleBarsCheckBox.setEnabled(fillUnusedCardSlotsCheckBox.isSelected());

    }

    private void setBuilderValuesFromDialog() {
        builder
                .setPageSize(getPageSize(pageSizeComboBox.getSelectedItem()))
                .setPerX(toInt(cardsPerXSpinner))
                .setPerY(toInt(cardsPerYSpinner))
                .setMarginPercentX(toFloat(marginXSpinner))
                .setMarginPercentY(toFloat(marginYSpinner))
                .setTitleFontSize(toFloat(titlefontSizeSpinner))
                .setNameFontSize(toFloat(namefontSizeSpinner))
                .setLegendFontSize(toFloat(legendfontSizeSpinner))
                .setRulesFontSize(toFloat(rulesfontSizeSpinner))
                .setCostValueFontSize(toFloat(costValueFontSizeSpinner))
                .setCostTypeFontSize(toFloat(costTypeFontSizeSpinner))
                .setTitleFontType(getFontType(titleFontTypeCombo.getSelectedItem()))
                .setNameFontType(getFontType(nameFontTypeCombo.getSelectedItem()))
                .setLegendFontType(getFontType(legendFontTypeCombo.getSelectedItem()))
                .setRulesFontType(getFontType(rulesFontTypeCombo.getSelectedItem()))
                .setCostValueFontType(getFontType(costValueFontTypeCombo.getSelectedItem()))
                .setCostTypeFontType(getFontType(costTypeFontTypeCombo.getSelectedItem()))
                .setTitleFontColor(titleColorChooserButton.getSelectedColor())
                .setNameFontColor(nameColorChooserButton.getSelectedColor())
                .setLegendFontColor(legendColorChooserButton.getSelectedColor())
                .setRulesFontColor(rulesColorChooserButton.getSelectedColor())
                .setCostValueFontColor(costValueColorChooserButton.getSelectedColor())
                .setCostTypeFontColor(costTypeColorChooserButton.getSelectedColor())
                .setCardBordersColor(getColorIfChecked(outerBorderCheckBox, outerBorderColorChooserButton))
                .setCostBordersColor(getColorIfChecked(costBorderCheckBox, costBorderColorChooserButton))
                .setCardBackgroundColor(getColorIfChecked(cardBackgroundCheckBox, cardBackgroundColorChooserButton))
                .setTitleBarsColor(getColorIfChecked(titleBarsCheckBox, titleBarsColorChooserButton))
                .setCardFillColor(getColorIfChecked(fillCheckBox, fillColorChooserButton))
                .setCostValueFillColor(getColorIfChecked(costValueFillCheckBox, costValueFillColorChooserButton))
                .setCostTypeFillColor(getColorIfChecked(costTypeFillCheckBox, costTypeFillColorChooserButton))
                .setForegroundGridColor(getColorIfChecked(foregroundGridCheckBox, gridColorChooserButton))
                .setBackgroundGridColor(getColorIfChecked(backgroundGridCheckBox, gridColorChooserButton))
                .setBackgroundPages(backgroundPagesCheckBox.isSelected())
                .setFillUnusedCardSlots(fillUnusedCardSlotsCheckBox.isSelected())
                .setFillUnusedCardSlotsBorders(fillUnusedCardSlotsBordersCheckBox.isSelected())
                .setFillUnusedCardSlotsTitles(fillUnusedCardSlotsTitleBarsCheckBox.isSelected());
    }
}
