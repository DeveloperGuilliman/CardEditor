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
package com.developerguilliman.cardEditor;

import com.developerguilliman.cardEditor.data.CardData;
import com.developerguilliman.cardEditor.input.WahapediaStratagemCardBuilder;
import com.developerguilliman.cardEditor.input.XmlCardInput;
import com.developerguilliman.cardEditor.output.PdfOutput;
import com.developerguilliman.cardEditor.output.XmlCardOutput;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import javax.swing.AbstractListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author DeveloperGuilliman
 */
public class MainWindow extends javax.swing.JFrame {

    private List<List<CardData>> cards;

    private File actualFile;

    /**
     * Creates new form Main
     */
    public MainWindow() {
        cards = new ArrayList<>();
        initComponents();
        sectionList.setModel(new AbstractListModel<String>() {
            @Override
            public int getSize() {
                return cards.size();
            }

            @Override
            public String getElementAt(int arg0) {
                return "Section " + (arg0 + 1);
            }
        });

        cardList.setModel(new AbstractListModel<String>() {
            @Override
            public int getSize() {
                List<CardData> section = getCurrentSection();
                return (section != null) ? section.size() : 0;
            }

            @Override
            public String getElementAt(int arg0) {
                List<CardData> section = getCurrentSection();
                return (section != null) ? section.get(arg0).getName() : null;
            }
        });

        titleTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent arg0) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent arg0) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent arg0) {
                update();
            }

            private void update() {
                CardData card = getCurrentCard();
                if (card == null) {
                    return;
                }
                card.setTitle(titleTextField.getText());
                cardList.updateUI();
            }
        });

        nameTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent arg0) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent arg0) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent arg0) {
                update();
            }

            private void update() {
                CardData card = getCurrentCard();
                if (card == null) {
                    return;
                }
                card.setName(nameTextField.getText());
                cardList.updateUI();
            }
        });

        legendTextArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent arg0) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent arg0) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent arg0) {
                update();
            }

            private void update() {
                CardData card = getCurrentCard();
                if (card == null) {
                    return;
                }
                card.setLegend(legendTextArea.getText());
                cardList.updateUI();
            }
        });

        rulesTextArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent arg0) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent arg0) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent arg0) {
                update();
            }

            private void update() {
                CardData card = getCurrentCard();
                if (card == null) {
                    return;
                }
                card.setRules(rulesTextArea.getText());
                cardList.updateUI();
            }
        });

        costTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent arg0) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent arg0) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent arg0) {
                update();
            }

            private void update() {
                CardData card = getCurrentCard();
                if (card == null) {
                    return;
                }
                card.setCost(costTextField.getText());
                cardList.updateUI();
            }
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

        listsPanel = new javax.swing.JPanel();
        sectionPanel = new javax.swing.JPanel();
        sectionScrollPane = new javax.swing.JScrollPane();
        sectionList = new javax.swing.JList<>();
        sectionButtonsPanel = new javax.swing.JPanel();
        addSectionButton = new javax.swing.JButton();
        removeSectionButton = new javax.swing.JButton();
        cardPanel = new javax.swing.JPanel();
        cardlPane = new javax.swing.JScrollPane();
        cardList = new javax.swing.JList<>();
        cardsButtonsPanel = new javax.swing.JPanel();
        addCardButton1 = new javax.swing.JButton();
        removeCardButton1 = new javax.swing.JButton();
        dataPanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        titleTextField = new javax.swing.JTextField();
        nameLabel = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        legendLabel = new javax.swing.JLabel();
        legendScrollPane = new javax.swing.JScrollPane();
        legendTextArea = new javax.swing.JTextArea();
        rulesLabel = new javax.swing.JLabel();
        rulesScrollPane = new javax.swing.JScrollPane();
        rulesTextArea = new javax.swing.JTextArea();
        costLabel = new javax.swing.JLabel();
        costTextField = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        newMenuItem = new javax.swing.JMenuItem();
        loadMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        importMenu = new javax.swing.JMenu();
        wahapediaStratagemImportMenuItem = new javax.swing.JMenuItem();
        exportMenu = new javax.swing.JMenu();
        pdfExportMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(620, 460));
        setPreferredSize(new java.awt.Dimension(1000, 750));

        listsPanel.setLayout(new java.awt.GridLayout(1, 0));

        sectionPanel.setLayout(new java.awt.BorderLayout());

        sectionList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        sectionList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                sectionListValueChanged(evt);
            }
        });
        sectionScrollPane.setViewportView(sectionList);

        sectionPanel.add(sectionScrollPane, java.awt.BorderLayout.CENTER);

        sectionButtonsPanel.setLayout(new javax.swing.BoxLayout(sectionButtonsPanel, javax.swing.BoxLayout.LINE_AXIS));

        addSectionButton.setText("Add Section");
        addSectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSectionButtonActionPerformed(evt);
            }
        });
        sectionButtonsPanel.add(addSectionButton);

        removeSectionButton.setText("Remove Section");
        removeSectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeSectionButtonActionPerformed(evt);
            }
        });
        sectionButtonsPanel.add(removeSectionButton);

        sectionPanel.add(sectionButtonsPanel, java.awt.BorderLayout.NORTH);

        listsPanel.add(sectionPanel);

        cardPanel.setLayout(new java.awt.BorderLayout());

        cardList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        cardList.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        cardList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                cardListValueChanged(evt);
            }
        });
        cardlPane.setViewportView(cardList);

        cardPanel.add(cardlPane, java.awt.BorderLayout.CENTER);

        cardsButtonsPanel.setLayout(new javax.swing.BoxLayout(cardsButtonsPanel, javax.swing.BoxLayout.LINE_AXIS));

        addCardButton1.setText("Add Card");
        addCardButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCardButton1ActionPerformed(evt);
            }
        });
        cardsButtonsPanel.add(addCardButton1);

        removeCardButton1.setText("Remove Card");
        removeCardButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeCardButton1ActionPerformed(evt);
            }
        });
        cardsButtonsPanel.add(removeCardButton1);

        cardPanel.add(cardsButtonsPanel, java.awt.BorderLayout.NORTH);

        listsPanel.add(cardPanel);

        getContentPane().add(listsPanel, java.awt.BorderLayout.WEST);

        dataPanel.setLayout(new javax.swing.BoxLayout(dataPanel, javax.swing.BoxLayout.PAGE_AXIS));

        titleLabel.setLabelFor(titleTextField);
        titleLabel.setText("Title");
        dataPanel.add(titleLabel);

        titleTextField.setMaximumSize(new java.awt.Dimension(2147483647, 20));
        titleTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                titleTextFieldActionPerformed(evt);
            }
        });
        dataPanel.add(titleTextField);

        nameLabel.setLabelFor(nameTextField);
        nameLabel.setText("Name");
        dataPanel.add(nameLabel);

        nameTextField.setMaximumSize(new java.awt.Dimension(2147483647, 20));
        dataPanel.add(nameTextField);

        legendLabel.setLabelFor(legendTextArea);
        legendLabel.setText("Legend");
        dataPanel.add(legendLabel);

        legendScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        legendTextArea.setColumns(20);
        legendTextArea.setLineWrap(true);
        legendTextArea.setRows(5);
        legendTextArea.setWrapStyleWord(true);
        legendScrollPane.setViewportView(legendTextArea);

        dataPanel.add(legendScrollPane);

        rulesLabel.setLabelFor(rulesTextArea);
        rulesLabel.setText("Rules");
        dataPanel.add(rulesLabel);

        rulesScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        rulesTextArea.setColumns(20);
        rulesTextArea.setLineWrap(true);
        rulesTextArea.setRows(5);
        rulesTextArea.setWrapStyleWord(true);
        rulesScrollPane.setViewportView(rulesTextArea);

        dataPanel.add(rulesScrollPane);

        costLabel.setLabelFor(costTextField);
        costLabel.setText("Cost");
        dataPanel.add(costLabel);

        costTextField.setMaximumSize(new java.awt.Dimension(2147483647, 20));
        dataPanel.add(costTextField);

        getContentPane().add(dataPanel, java.awt.BorderLayout.CENTER);

        fileMenu.setText("File");

        newMenuItem.setText("New");
        newMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(newMenuItem);

        loadMenuItem.setText("Load...");
        loadMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(loadMenuItem);

        saveMenuItem.setText("Save");
        saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveMenuItem);

        saveAsMenuItem.setText("Save as...");
        saveAsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveAsMenuItem);

        jMenuBar1.add(fileMenu);

        importMenu.setText("Import");

        wahapediaStratagemImportMenuItem.setText("Wahapedia Stratagems...");
        wahapediaStratagemImportMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wahapediaStratagemImportMenuItemActionPerformed(evt);
            }
        });
        importMenu.add(wahapediaStratagemImportMenuItem);

        jMenuBar1.add(importMenu);

        exportMenu.setText("Export");

        pdfExportMenuItem.setText("Export PDF...");
        pdfExportMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pdfExportMenuItemActionPerformed(evt);
            }
        });
        exportMenu.add(pdfExportMenuItem);

        jMenuBar1.add(exportMenu);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void titleTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_titleTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_titleTextFieldActionPerformed

    private void addSectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSectionButtonActionPerformed
        cards.add(new ArrayList());
        sectionList.updateUI();
    }//GEN-LAST:event_addSectionButtonActionPerformed

    private void removeSectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeSectionButtonActionPerformed
        int section = sectionList.getSelectedIndex();
        if (section < 0 || section > cards.size()) {
            return;
        }
        cards.remove(section);
        sectionList.updateUI();
    }//GEN-LAST:event_removeSectionButtonActionPerformed

    private void sectionListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_sectionListValueChanged
        cardList.setSelectedIndex(0);
        cardList.updateUI();
        updateCardFields();
    }//GEN-LAST:event_sectionListValueChanged

    private void cardListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_cardListValueChanged
        updateCardFields();
    }//GEN-LAST:event_cardListValueChanged

    private void addCardButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCardButton1ActionPerformed
        List<CardData> section = getCurrentSection();
        if (section == null) {
            return;
        }
        CardData stratagemData = new CardData();
        stratagemData.setName("NEW");
        section.add(stratagemData);
        cardList.updateUI();

    }//GEN-LAST:event_addCardButton1ActionPerformed

    private void removeCardButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeCardButton1ActionPerformed
        List<CardData> section = getCurrentSection();
        if (section == null) {
            return;
        }
        int card = cardList.getSelectedIndex();
        if (card < 0 || card > section.size()) {
            return;
        }
        section.remove(card);
        cardList.updateUI();
        updateCardFields();
    }//GEN-LAST:event_removeCardButton1ActionPerformed

    private void loadMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadMenuItemActionPerformed
        JFileChooser chooser = createXmlFileChooser();
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            loadCards(chooser.getSelectedFile());
        }
    }//GEN-LAST:event_loadMenuItemActionPerformed

    private void saveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuItemActionPerformed
        if (actualFile == null) {
            JFileChooser chooser = createXmlFileChooser();
            int returnVal = chooser.showSaveDialog(this);
            if (returnVal != JFileChooser.APPROVE_OPTION) {
                return;
            }
            actualFile = getChooserSelectedFile(chooser);
        }
        saveCards(actualFile);

    }//GEN-LAST:event_saveMenuItemActionPerformed

    private void saveAsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsMenuItemActionPerformed

        JFileChooser chooser = createXmlFileChooser();
        int returnVal = chooser.showSaveDialog(this);
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }
        actualFile = getChooserSelectedFile(chooser);
        saveCards(actualFile);

    }//GEN-LAST:event_saveAsMenuItemActionPerformed

    private void newMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newMenuItemActionPerformed
        actualFile = null;
        cards.clear();
        sectionList.updateUI();
        cardList.updateUI();
    }//GEN-LAST:event_newMenuItemActionPerformed

    private void wahapediaStratagemImportMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wahapediaStratagemImportMenuItemActionPerformed
        String urlString = JOptionPane.showInputDialog(this, "Type the Wahapedia faction URL to import stratagems for", "Wahapedia Stratagem import", JOptionPane.QUESTION_MESSAGE);
        if (urlString == null) {
            return;
        }
        Callable<Void> callable = () -> {
            WahapediaStratagemCardBuilder builder = new WahapediaStratagemCardBuilder(1, false, true);
            cards = builder.build(new URL(urlString).openStream());
            sectionList.updateUI();
            cardList.updateUI();
            return null;
        };
        WaitingDialog.show(this, "Loading stratagems from Wahapedia...", callable);

    }//GEN-LAST:event_wahapediaStratagemImportMenuItemActionPerformed

    private void pdfExportMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pdfExportMenuItemActionPerformed

        JFileChooser chooser = createPdfFileChooser();
        int returnVal = chooser.showSaveDialog(this);
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File file = getChooserSelectedFile(chooser);
        Callable<Void> callable = () -> {
            PdfOutput builder = new PdfOutput(3, 3);
            builder.build(new FileOutputStream(file), cards);
            return null;
        };
        WaitingDialog.show(this, "Creating pdf...", callable);


    }//GEN-LAST:event_pdfExportMenuItemActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new MainWindow().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addCardButton1;
    private javax.swing.JButton addSectionButton;
    private javax.swing.JList<String> cardList;
    private javax.swing.JPanel cardPanel;
    private javax.swing.JScrollPane cardlPane;
    private javax.swing.JPanel cardsButtonsPanel;
    private javax.swing.JLabel costLabel;
    private javax.swing.JTextField costTextField;
    private javax.swing.JPanel dataPanel;
    private javax.swing.JMenu exportMenu;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu importMenu;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JLabel legendLabel;
    private javax.swing.JScrollPane legendScrollPane;
    private javax.swing.JTextArea legendTextArea;
    private javax.swing.JPanel listsPanel;
    private javax.swing.JMenuItem loadMenuItem;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JMenuItem newMenuItem;
    private javax.swing.JMenuItem pdfExportMenuItem;
    private javax.swing.JButton removeCardButton1;
    private javax.swing.JButton removeSectionButton;
    private javax.swing.JLabel rulesLabel;
    private javax.swing.JScrollPane rulesScrollPane;
    private javax.swing.JTextArea rulesTextArea;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JPanel sectionButtonsPanel;
    private javax.swing.JList<String> sectionList;
    private javax.swing.JPanel sectionPanel;
    private javax.swing.JScrollPane sectionScrollPane;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JTextField titleTextField;
    private javax.swing.JMenuItem wahapediaStratagemImportMenuItem;
    // End of variables declaration//GEN-END:variables

    private List<CardData> getCurrentSection() {
        int section = sectionList.getSelectedIndex();
        if (section < 0 || section >= cards.size()) {
            return null;
        }
        return cards.get(section);
    }

    private CardData getCurrentCard() {
        List<CardData> section = getCurrentSection();
        if (section == null) {
            return null;
        }
        int card = cardList.getSelectedIndex();
        if (card < 0 || card >= section.size()) {
            return null;
        }
        return section.get(card);
    }

    private void updateCardFields() {
        CardData card = getCurrentCard();
        if (card == null) {
            titleTextField.setText("");
            nameTextField.setText("");
            legendTextArea.setText("");
            rulesTextArea.setText("");
            costTextField.setText("");
        } else {
            titleTextField.setText(card.getTitle());
            nameTextField.setText(card.getName());
            legendTextArea.setText(card.getLegend());
            rulesTextArea.setText(card.getRules());
            costTextField.setText(card.getCost());
        }
    }

    private void loadCards(File file) {
        Callable<Void> callable = () -> {
            FileInputStream fis = new FileInputStream(file);
            XmlCardInput input = new XmlCardInput();
            // XmlStratagemBuilder input = new XmlStratagemBuilder();
            cards = input.build(fis);
            sectionList.updateUI();
            cardList.updateUI();
            actualFile = file;
            return null;
        };
        WaitingDialog.show(MainWindow.this, "Loading...", callable);
    }

    private void saveCards(File file) {
        Callable<Void> callable = () -> {
            FileOutputStream fos = new FileOutputStream(file);
            XmlCardOutput output = new XmlCardOutput();
            output.build(fos, cards);
            actualFile = file;
            return null;
        };
        WaitingDialog.show(MainWindow.this, "Saving...", callable);

    }

    private JFileChooser createXmlFileChooser() {
        File dir = (actualFile != null) ? actualFile.getParentFile() : null;
        JFileChooser chooser = new JFileChooser(dir);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Card XML file", "xml");
        chooser.setFileFilter(filter);
        return chooser;
    }

    private JFileChooser createPdfFileChooser() {
        File dir = (actualFile != null) ? actualFile.getParentFile() : null;
        JFileChooser chooser = new JFileChooser(dir);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "PDF file", "pdf");
        chooser.setFileFilter(filter);
        return chooser;
    }

    private File getChooserSelectedFile(JFileChooser chooser) {
        File file = chooser.getSelectedFile();
        if (file.getName().indexOf('.') < 0) {
            file = new File(file.getAbsolutePath() + ".pdf");
        }
        return file;
    }

}
