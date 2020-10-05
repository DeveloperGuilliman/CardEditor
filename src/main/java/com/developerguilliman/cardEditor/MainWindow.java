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

import com.developerguilliman.cardEditor.gui.WaitingDialog;
import com.developerguilliman.cardEditor.gui.ListTransferHandler;
import com.developerguilliman.cardEditor.gui.AbstractListListModel;
import com.developerguilliman.cardEditor.data.CardData;
import com.developerguilliman.cardEditor.input.WahapediaPsychicPowerCardBuilder;
import com.developerguilliman.cardEditor.input.WahapediaStratagemCardBuilder;
import com.developerguilliman.cardEditor.input.WahapediaWarlordTraitCardBuilder;
import com.developerguilliman.cardEditor.input.XmlCardInput;
import com.developerguilliman.cardEditor.output.PdfOutput;
import com.developerguilliman.cardEditor.output.XmlCardOutput;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
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

    private final List<List<CardData>> cards;
    private List<CardData> actualSection;
    private CardData actualCard;

    private File actualFile;

    /**
     * Creates new form Main
     */
    public MainWindow() {
        cards = new ArrayList<>();
        initComponents();
        AbstractListListModel<List<CardData>> sectionListModel = new AbstractListListModel<List<CardData>>() {
            @Override
            protected List<List<CardData>> getList() {
                return cards;
            }

            @Override
            protected String createString(int index, List<CardData> element) {
                return "Section " + (index + 1) + " (" + element.size() + ")";
            }

        };
        sectionList.setModel(sectionListModel);
        sectionList.setTransferHandler(new ListTransferHandler(sectionListModel));

        AbstractListListModel<CardData> cardListModel = new AbstractListListModel<CardData>() {
            @Override
            protected List<CardData> getList() {
                return actualSection;
            }

            @Override
            protected String createString(int index, CardData element) {
                return (element != null) ? element.getName() : null;
            }

        };

        cardList.setModel(cardListModel);
        cardList.setTransferHandler(new ListTransferHandler(cardListModel));

        titleTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent index) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent index) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent index) {
                update();
            }

            private void update() {
                if (actualCard == null) {
                    return;
                }
                actualCard.setTitle(titleTextField.getText());
                cardList.updateUI();
            }
        });

        nameTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent index) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent index) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent index) {
                update();
            }

            private void update() {
                if (actualCard == null) {
                    return;
                }
                actualCard.setName(nameTextField.getText());
                cardList.updateUI();
            }
        });

        legendTextArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent index) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent index) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent index) {
                update();
            }

            private void update() {
                if (actualCard == null) {
                    return;
                }
                actualCard.setLegend(legendTextArea.getText());
                cardList.updateUI();
            }
        });

        rulesTextArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent index) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent index) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent index) {
                update();
            }

            private void update() {
                if (actualCard == null) {
                    return;
                }
                actualCard.setRules(rulesTextArea.getText());
                cardList.updateUI();
            }
        });

        costTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent index) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent index) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent index) {
                update();
            }

            private void update() {
                if (actualCard == null) {
                    return;
                }
                actualCard.setCost(costTextField.getText());
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
        wahapediaWarlordTraitImportMenuItem = new javax.swing.JMenuItem();
        wahapediaPsychicPowerImportMenuItem = new javax.swing.JMenuItem();
        exportMenu = new javax.swing.JMenu();
        pdfExportMenuItem = new javax.swing.JMenuItem();
        sectionMenu = new javax.swing.JMenu();
        compactAllSectionsMenuItem = new javax.swing.JMenuItem();
        reorderTitleSectionMenuItem = new javax.swing.JMenuItem();
        reorderNameSectionMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(620, 460));
        setPreferredSize(new java.awt.Dimension(1000, 750));

        listsPanel.setLayout(new java.awt.GridLayout(1, 0));

        sectionPanel.setLayout(new java.awt.BorderLayout());

        sectionList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        sectionList.setDragEnabled(true);
        sectionList.setDropMode(javax.swing.DropMode.INSERT);
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
        cardList.setDragEnabled(true);
        cardList.setDropMode(javax.swing.DropMode.INSERT);
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

        wahapediaWarlordTraitImportMenuItem.setText("Wahapedia Warlord traits...");
        wahapediaWarlordTraitImportMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wahapediaWarlordTraitImportMenuItemActionPerformed(evt);
            }
        });
        importMenu.add(wahapediaWarlordTraitImportMenuItem);

        wahapediaPsychicPowerImportMenuItem.setText("Wahapedia Pyschic powers...");
        wahapediaPsychicPowerImportMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wahapediaPsychicPowerImportMenuItemActionPerformed(evt);
            }
        });
        importMenu.add(wahapediaPsychicPowerImportMenuItem);

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

        sectionMenu.setText("Section");

        compactAllSectionsMenuItem.setText("Compact all sections");
        compactAllSectionsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compactAllSectionsMenuItemActionPerformed(evt);
            }
        });
        sectionMenu.add(compactAllSectionsMenuItem);

        reorderTitleSectionMenuItem.setText("Reorder cards by title");
        reorderTitleSectionMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reorderTitleSectionMenuItemActionPerformed(evt);
            }
        });
        sectionMenu.add(reorderTitleSectionMenuItem);

        reorderNameSectionMenuItem.setText("Reorder cards by name");
        reorderNameSectionMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reorderNameSectionMenuItemActionPerformed(evt);
            }
        });
        sectionMenu.add(reorderNameSectionMenuItem);

        jMenuBar1.add(sectionMenu);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addSectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSectionButtonActionPerformed
        cards.add(new ArrayList());
        updateListsUI();
    }//GEN-LAST:event_addSectionButtonActionPerformed

    private void removeSectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeSectionButtonActionPerformed
        int section = sectionList.getSelectedIndex();
        if (section < 0 || section > cards.size()) {
            return;
        }
        cards.remove(section);
        sectionList.clearSelection();
        updateSelectedSection(-1);
    }//GEN-LAST:event_removeSectionButtonActionPerformed

    private void sectionListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_sectionListValueChanged
        updateSelectedSection(sectionList.getSelectedIndex());
    }//GEN-LAST:event_sectionListValueChanged

    private void cardListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_cardListValueChanged
        int selectedCard = cardList.getSelectedIndex();
        if (selectedCard < 0 || selectedCard >= actualSection.size()) {
            actualCard = null;
        } else {
            actualCard = actualSection.get(selectedCard);
        }
        updateCardFields();
    }//GEN-LAST:event_cardListValueChanged

    private void addCardButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCardButton1ActionPerformed
        if (actualSection == null) {
            return;
        }
        CardData stratagemData = new CardData();
        stratagemData.setName("NEW");
        actualSection.add(stratagemData);
        updateListsUI();
    }//GEN-LAST:event_addCardButton1ActionPerformed

    private void removeCardButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeCardButton1ActionPerformed
        if (actualSection == null) {
            return;
        }
        int card = cardList.getSelectedIndex();
        if (card < 0 || card > actualSection.size()) {
            return;
        }
        actualSection.remove(card);
        updateListsUI();
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
            actualFile = getChooserSelectedFile(chooser, "xml");
        }
        saveCards(actualFile);

    }//GEN-LAST:event_saveMenuItemActionPerformed

    private void saveAsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsMenuItemActionPerformed

        JFileChooser chooser = createXmlFileChooser();
        int returnVal = chooser.showSaveDialog(this);
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }
        actualFile = getChooserSelectedFile(chooser, "xml");
        saveCards(actualFile);

    }//GEN-LAST:event_saveAsMenuItemActionPerformed

    private void newMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newMenuItemActionPerformed
        actualFile = null;
        cards.clear();
        updateSelectedSection(-1);
        updateListsUI();
    }//GEN-LAST:event_newMenuItemActionPerformed

    private void wahapediaStratagemImportMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wahapediaStratagemImportMenuItemActionPerformed
        String urlString = JOptionPane.showInputDialog(this, "Type the Wahapedia faction URL to import stratagems for", "Wahapedia Stratagem import", JOptionPane.QUESTION_MESSAGE);
        if (urlString == null) {
            return;
        }
        Callable<Void> callable = () -> {
            WahapediaStratagemCardBuilder builder = new WahapediaStratagemCardBuilder(1, false, true);
            cards.addAll(builder.build(new URL(urlString).openStream()));
            updateListsUI();
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
        File file = getChooserSelectedFile(chooser, "pdf");
        Callable<Void> callable = () -> {
            PdfOutput builder = new PdfOutput(3, 3);
            builder.build(new FileOutputStream(file), cards);
            return null;
        };
        WaitingDialog.show(this, "Creating pdf...", callable);


    }//GEN-LAST:event_pdfExportMenuItemActionPerformed

    private void wahapediaWarlordTraitImportMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wahapediaWarlordTraitImportMenuItemActionPerformed
        String urlString = JOptionPane.showInputDialog(this, "Type the Wahapedia faction URL to import stratagems for", "Wahapedia Stratagem import", JOptionPane.QUESTION_MESSAGE);
        if (urlString == null) {
            return;
        }
        Callable<Void> callable = () -> {
            WahapediaWarlordTraitCardBuilder builder = new WahapediaWarlordTraitCardBuilder(1, false, true);
            cards.addAll(builder.build(new URL(urlString).openStream()));
            updateListsUI();
            return null;
        };
        WaitingDialog.show(this, "Loading warlord traits from Wahapedia...", callable);    }//GEN-LAST:event_wahapediaWarlordTraitImportMenuItemActionPerformed

    private void wahapediaPsychicPowerImportMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wahapediaPsychicPowerImportMenuItemActionPerformed
        String urlString = JOptionPane.showInputDialog(this, "Type the Wahapedia faction URL to import stratagems for", "Wahapedia Stratagem import", JOptionPane.QUESTION_MESSAGE);
        if (urlString == null) {
            return;
        }
        Callable<Void> callable = () -> {
            WahapediaPsychicPowerCardBuilder builder = new WahapediaPsychicPowerCardBuilder(1, false, true);
            cards.addAll(builder.build(new URL(urlString).openStream()));
            updateListsUI();
            return null;
        };
        WaitingDialog.show(this, "Loading psychic powers from Wahapedia...", callable);
    }//GEN-LAST:event_wahapediaPsychicPowerImportMenuItemActionPerformed

    private void compactAllSectionsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_compactAllSectionsMenuItemActionPerformed
        ArrayList<CardData> singleSection = new ArrayList<>();
        for (List<CardData> section : cards) {
            singleSection.addAll(section);
        }
        cards.clear();
        cards.add(singleSection);
        sectionList.setSelectedIndex(0);
        updateSelectedSection(0);
    }//GEN-LAST:event_compactAllSectionsMenuItemActionPerformed

    private void reorderNameSectionMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reorderNameSectionMenuItemActionPerformed
        if (actualSection == null) {
            return;
        }
        Collections.sort(actualSection, Comparator.comparing(CardData::getName));
        actualCard = null;
        cardList.clearSelection();
        updateListsUI();
    }//GEN-LAST:event_reorderNameSectionMenuItemActionPerformed

    private void reorderTitleSectionMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reorderTitleSectionMenuItemActionPerformed
        if (actualSection == null) {
            return;
        }
        Collections.sort(actualSection, Comparator.comparing(CardData::getTitle));
        actualCard = null;
        cardList.clearSelection();
        updateListsUI();
    }//GEN-LAST:event_reorderTitleSectionMenuItemActionPerformed

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
    private javax.swing.JMenuItem compactAllSectionsMenuItem;
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
    private javax.swing.JMenuItem reorderNameSectionMenuItem;
    private javax.swing.JMenuItem reorderTitleSectionMenuItem;
    private javax.swing.JLabel rulesLabel;
    private javax.swing.JScrollPane rulesScrollPane;
    private javax.swing.JTextArea rulesTextArea;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JPanel sectionButtonsPanel;
    private javax.swing.JList<String> sectionList;
    private javax.swing.JMenu sectionMenu;
    private javax.swing.JPanel sectionPanel;
    private javax.swing.JScrollPane sectionScrollPane;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JTextField titleTextField;
    private javax.swing.JMenuItem wahapediaPsychicPowerImportMenuItem;
    private javax.swing.JMenuItem wahapediaStratagemImportMenuItem;
    private javax.swing.JMenuItem wahapediaWarlordTraitImportMenuItem;
    // End of variables declaration//GEN-END:variables

    private void loadCards(File file) {
        Callable<Void> callable = () -> {
            FileInputStream fis = new FileInputStream(file);
            XmlCardInput input = new XmlCardInput();

            List<List<CardData>> newCards = input.build(fis);

            cards.clear();
            cards.addAll(newCards);
            updateSelectedSection(0);
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
        JFileChooser chooser = new JFileChooser();
        if (actualFile != null) {
            String filename = actualFile.getName();
            int lio = filename.lastIndexOf('.');
            filename = ((lio < 0) ? filename : filename.substring(0, lio)).concat(".xml");
            File f = new File(actualFile.getParent(), filename);
            chooser.setSelectedFile(f);
        }
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Card XML file", "xml");
        chooser.setFileFilter(filter);
        return chooser;
    }

    private JFileChooser createPdfFileChooser() {

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

    private File getChooserSelectedFile(JFileChooser chooser, String extension) {
        File file = chooser.getSelectedFile();
        if (file.getName().indexOf('.') < 0) {
            file = new File(file.getAbsolutePath() + "." + extension);
        }
        return file;
    }

    private void updateCardFields() {
        if (actualCard == null) {
            titleTextField.setText("");
            nameTextField.setText("");
            legendTextArea.setText("");
            rulesTextArea.setText("");
            costTextField.setText("");
        } else {
            titleTextField.setText(actualCard.getTitle());
            nameTextField.setText(actualCard.getName());
            legendTextArea.setText(actualCard.getLegend());
            rulesTextArea.setText(actualCard.getRules());
            costTextField.setText(actualCard.getCost());
        }
    }

    private void updateListsUI() {
        java.awt.EventQueue.invokeLater(() -> {
            sectionList.updateUI();
            cardList.updateUI();
            updateCardFields();
        });
    }

    private void updateSelectedSection(int selectedSection) {
        if (selectedSection < 0 || selectedSection >= cards.size()) {
            actualSection = null;
            actualCard = null;
        } else {
            actualSection = cards.get(selectedSection);
            actualCard = null;
        }
        cardList.clearSelection();

        updateListsUI();
    }
}
