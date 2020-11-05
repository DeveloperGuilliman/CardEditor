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
import com.developerguilliman.cardEditor.data.CardData;
import com.developerguilliman.cardEditor.data.SectionData;
import com.developerguilliman.cardEditor.input.ICardInput;
import com.developerguilliman.cardEditor.input.WahapediaAllCardBuilder;
import com.developerguilliman.cardEditor.input.WahapediaMiscCardBuilder;
import com.developerguilliman.cardEditor.input.WahapediaStratagemCardBuilder;
import com.developerguilliman.cardEditor.input.XmlCardInput;
import com.developerguilliman.cardEditor.output.PdfOutput;
import com.developerguilliman.cardEditor.output.XmlCardOutput;
import com.developerguilliman.cardEditor.warning.WarningArrayList;
import java.awt.Component;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author Developer Guilliman <developerguilliman@gmail.com>
 */
public class MainWindow extends javax.swing.JFrame {

    private final PdfOutput.Builder pdfSettings;
    private final CardCollectionData cards;
    private final DefaultMutableTreeNode root;

    private SectionData actualSection;
    private CardData actualCard;

    private DefaultMutableTreeNode actualSectionNode;
    private DefaultMutableTreeNode actualCardNode;

    private File actualFile;

    /**
     * Creates new form Main
     */
    public MainWindow() {
        cards = new CardCollectionData();
        initComponents();

        pdfSettings = new PdfOutput.Builder();
        root = new DefaultMutableTreeNode("Cards", true);
        cardTree.setModel(new DefaultTreeModel(root, true));
        cardTree.setTransferHandler(new TreeTransferHandler(cardTree));
        cardTree.setCellRenderer(new CardTreeCellRenderer());
        cardTree.getSelectionModel().setSelectionMode(DefaultTreeSelectionModel.SINGLE_TREE_SELECTION);
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
                actualSection.updateName();
                cardTree.updateUI();
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
                cardTree.updateUI();
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
            }
        });

        costValueTextField.getDocument().addDocumentListener(new DocumentListener() {
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
                actualCard.setCostValue(costValueTextField.getText());
            }
        });

        costTypeTextField.getDocument().addDocumentListener(new DocumentListener() {
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
                actualCard.setCostType(costTypeTextField.getText());
            }
        });
        updateButtonsFields();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        treePanel = new javax.swing.JPanel();
        treeButtonsPanel = new javax.swing.JPanel();
        sectionsButtonsPanel = new javax.swing.JPanel();
        addSectionButton = new javax.swing.JButton();
        removeSectionButton = new javax.swing.JButton();
        cardsButtonsPanel = new javax.swing.JPanel();
        addCardButton = new javax.swing.JButton();
        duplicateCardButton = new javax.swing.JButton();
        removeCardButton = new javax.swing.JButton();
        cardTreeScrollPane = new javax.swing.JScrollPane();
        cardTree = new javax.swing.JTree();
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
        costPanel = new javax.swing.JPanel();
        costValuePanel = new javax.swing.JPanel();
        costValueLabel = new javax.swing.JLabel();
        costValueTextField = new javax.swing.JTextField();
        costTypePanel = new javax.swing.JPanel();
        costTypeLabel = new javax.swing.JLabel();
        costTypeTextField = new javax.swing.JTextField();
        mainMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        newMenuItem = new javax.swing.JMenuItem();
        loadMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        importMenu = new javax.swing.JMenu();
        cardsXmlImportMenuItem = new javax.swing.JMenuItem();
        wahapediaStratagemImportMenuItem = new javax.swing.JMenuItem();
        wahapediaWarlordTraitImportMenuItem = new javax.swing.JMenuItem();
        wahapediaAllImportMenuItem = new javax.swing.JMenuItem();
        exportMenu = new javax.swing.JMenu();
        pdfExportMenuItem = new javax.swing.JMenuItem();
        sectionMenu = new javax.swing.JMenu();
        compactAllSectionsMenuItem = new javax.swing.JMenuItem();
        reorderTitleSectionMenuItem = new javax.swing.JMenuItem();
        reorderNameSectionMenuItem = new javax.swing.JMenuItem();
        deduplicateMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Card editor");
        setIconImage(createIcon("images/cards.png"));
        setMinimumSize(new java.awt.Dimension(600, 460));

        treePanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        treePanel.setLayout(new java.awt.BorderLayout());

        treeButtonsPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 3, 0));
        treeButtonsPanel.setLayout(new javax.swing.BoxLayout(treeButtonsPanel, javax.swing.BoxLayout.LINE_AXIS));

        sectionsButtonsPanel.setLayout(new java.awt.GridLayout(2, 0));

        addSectionButton.setText("Add Section");
        addSectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSectionButtonActionPerformed(evt);
            }
        });
        sectionsButtonsPanel.add(addSectionButton);

        removeSectionButton.setText("Remove Section");
        removeSectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeSectionButtonActionPerformed(evt);
            }
        });
        sectionsButtonsPanel.add(removeSectionButton);

        treeButtonsPanel.add(sectionsButtonsPanel);

        cardsButtonsPanel.setLayout(new java.awt.GridLayout(2, 2));

        addCardButton.setText("Add Card");
        addCardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCardButtonActionPerformed(evt);
            }
        });
        cardsButtonsPanel.add(addCardButton);

        duplicateCardButton.setText("Duplicate Card");
        duplicateCardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                duplicateCardButtonActionPerformed(evt);
            }
        });
        cardsButtonsPanel.add(duplicateCardButton);

        removeCardButton.setText("Remove Card");
        removeCardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeCardButtonActionPerformed(evt);
            }
        });
        cardsButtonsPanel.add(removeCardButton);

        treeButtonsPanel.add(cardsButtonsPanel);

        treePanel.add(treeButtonsPanel, java.awt.BorderLayout.NORTH);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        cardTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        cardTree.setAutoscrolls(true);
        cardTree.setDragEnabled(true);
        cardTree.setDropMode(javax.swing.DropMode.INSERT);
        cardTree.setEditable(true);
        cardTree.setRootVisible(false);
        cardTree.setShowsRootHandles(true);
        cardTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                cardTreeValueChanged(evt);
            }
        });
        cardTreeScrollPane.setViewportView(cardTree);

        treePanel.add(cardTreeScrollPane, java.awt.BorderLayout.CENTER);

        getContentPane().add(treePanel, java.awt.BorderLayout.WEST);

        dataPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
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

        costPanel.setLayout(new javax.swing.BoxLayout(costPanel, javax.swing.BoxLayout.LINE_AXIS));

        costValuePanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 1));
        costValuePanel.setMaximumSize(new java.awt.Dimension(300, 34));
        costValuePanel.setLayout(new javax.swing.BoxLayout(costValuePanel, javax.swing.BoxLayout.Y_AXIS));

        costValueLabel.setLabelFor(costValueTextField);
        costValueLabel.setText("Cost Value");
        costValueLabel.setMinimumSize(new java.awt.Dimension(300, 14));
        costValuePanel.add(costValueLabel);

        costValueTextField.setMaximumSize(new java.awt.Dimension(300, 20));
        costValuePanel.add(costValueTextField);

        costPanel.add(costValuePanel);

        costTypePanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 1, 0, 0));
        costTypePanel.setLayout(new javax.swing.BoxLayout(costTypePanel, javax.swing.BoxLayout.Y_AXIS));

        costTypeLabel.setLabelFor(costValueTextField);
        costTypeLabel.setText("Cost Type");
        costTypePanel.add(costTypeLabel);

        costTypeTextField.setMaximumSize(new java.awt.Dimension(2147483647, 20));
        costTypePanel.add(costTypeTextField);

        costPanel.add(costTypePanel);

        dataPanel.add(costPanel);

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

        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        mainMenuBar1.add(fileMenu);

        importMenu.setText("Import");

        cardsXmlImportMenuItem.setText("Cards XML...");
        cardsXmlImportMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardsXmlImportMenuItemActionPerformed(evt);
            }
        });
        importMenu.add(cardsXmlImportMenuItem);

        wahapediaStratagemImportMenuItem.setText("Wahapedia Stratagem Cards...");
        wahapediaStratagemImportMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wahapediaStratagemImportMenuItemActionPerformed(evt);
            }
        });
        importMenu.add(wahapediaStratagemImportMenuItem);

        wahapediaWarlordTraitImportMenuItem.setText("Wahapedia Misc Cards...");
        wahapediaWarlordTraitImportMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wahapediaWarlordTraitImportMenuItemActionPerformed(evt);
            }
        });
        importMenu.add(wahapediaWarlordTraitImportMenuItem);

        wahapediaAllImportMenuItem.setText("Wahapedia All Cards...");
        wahapediaAllImportMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wahapediaAllImportMenuItemActionPerformed(evt);
            }
        });
        importMenu.add(wahapediaAllImportMenuItem);

        mainMenuBar1.add(importMenu);

        exportMenu.setText("Export");

        pdfExportMenuItem.setText("Export PDF...");
        pdfExportMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pdfExportMenuItemActionPerformed(evt);
            }
        });
        exportMenu.add(pdfExportMenuItem);

        mainMenuBar1.add(exportMenu);

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

        deduplicateMenuItem.setText("Find and remove duplicates");
        deduplicateMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deduplicateMenuItemActionPerformed(evt);
            }
        });
        sectionMenu.add(deduplicateMenuItem);

        mainMenuBar1.add(sectionMenu);

        setJMenuBar(mainMenuBar1);

        setBounds(0, 0, 796, 618);
    }// </editor-fold>//GEN-END:initComponents

    private void addSectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSectionButtonActionPerformed
        SectionData section = new SectionData();
        cards.add(section);
        DefaultMutableTreeNode sectionNode = createSectionNode(section);
        root.add(sectionNode);
        expandNodes(cardTree, sectionNode);
        selectAndScroll(sectionNode);
        forceUpdateUI();
    }//GEN-LAST:event_addSectionButtonActionPerformed

    private void removeSectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeSectionButtonActionPerformed
        if (actualSection == null) {
            return;
        }
        int position = cards.getExactIndex(actualSection);
        cards.remove(position);
        root.remove(position);
        actualCard = null;
        actualCardNode = null;
        position = cards.getBoundIndex(position);
        if (cards.size() > 0) {
            actualSection = cards.get(position);
            actualSectionNode = (DefaultMutableTreeNode) root.getChildAt(position);
            selectAndScroll(actualSectionNode);
        } else {
            actualSection = null;
            actualSectionNode = null;
            cardTree.clearSelection();
        }

        forceUpdateUI();
    }//GEN-LAST:event_removeSectionButtonActionPerformed

    private void addCardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCardButtonActionPerformed
        if (actualSection == null) {
            return;
        }
        CardData card = new CardData();
        card.setName("NEW");
        card.setTitle(actualSection.getName());
        actualSection.add(card);
        DefaultMutableTreeNode cardNode = createCardNode(card);
        actualSectionNode.add(cardNode);
        actualCardNode = cardNode;
        selectAndScroll(cardNode);
        forceUpdateUI();
    }//GEN-LAST:event_addCardButtonActionPerformed

    private void removeCardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeCardButtonActionPerformed
        if (actualSection == null && actualCard != null) {
            return;
        }
        int position = actualSection.getExactIndex(actualCard);
        actualSection.remove(position);
        actualSectionNode.remove(position);
        position = actualSection.getBoundIndex(position);
        if (actualSection.size() > 0) {
            actualCard = actualSection.get(position);
            actualCardNode = (DefaultMutableTreeNode) actualSectionNode.getChildAt(position);
            selectAndScroll(actualCardNode);
        } else {
            actualCard = null;
            actualCardNode = null;
            selectAndScroll(actualSectionNode);
        }
        forceUpdateUI();
    }//GEN-LAST:event_removeCardButtonActionPerformed

    private void loadMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadMenuItemActionPerformed
        JFileChooser chooser = createXmlFileChooser();
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            loadCards(chooser.getSelectedFile(), "Loading...", true);
        }
    }//GEN-LAST:event_loadMenuItemActionPerformed

    private void saveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuItemActionPerformed
        if (actualFile == null) {
            JFileChooser chooser = createXmlFileChooser();
            int returnVal = chooser.showSaveDialog(this);
            if (returnVal != JFileChooser.APPROVE_OPTION) {
                return;
            }
            setActualFile(getChooserSelectedFile(chooser, "xml"));
        }
        saveCards(actualFile);

    }//GEN-LAST:event_saveMenuItemActionPerformed

    private void saveAsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsMenuItemActionPerformed

        JFileChooser chooser = createXmlFileChooser();
        int returnVal = chooser.showSaveDialog(this);
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }
        setActualFile(getChooserSelectedFile(chooser, "xml"));
        saveCards(actualFile);

    }//GEN-LAST:event_saveAsMenuItemActionPerformed

    private void newMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newMenuItemActionPerformed
        setActualFile(null);
        cards.clear();
        actualCard = null;
        actualSection = null;
        actualCardNode = null;
        actualSectionNode = null;
        updateTree();
    }//GEN-LAST:event_newMenuItemActionPerformed

    private void wahapediaStratagemImportMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wahapediaStratagemImportMenuItemActionPerformed
        String urlString = JOptionPane.showInputDialog(this, "Type the Wahapedia faction URL to import Stratagems cards for", "Wahapedia Stratagems cards import", JOptionPane.QUESTION_MESSAGE);
        if (urlString == null) {
            return;
        }
        Callable<List<String>> callable = () -> {
            WahapediaStratagemCardBuilder builder = new WahapediaStratagemCardBuilder(1, false, true);
            cards.addAll(builder.build(new URL(urlString).openStream()));
            updateTree();
            return null;
        };
        WaitingDialog.show(this, "Loading stratagem cards from Wahapedia...", callable);

    }//GEN-LAST:event_wahapediaStratagemImportMenuItemActionPerformed

    private void pdfExportMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pdfExportMenuItemActionPerformed

//        java.awt.EventQueue.invokeLater(() -> {
        new PdfCreateOptionsDialog(this, actualFile, cards, pdfSettings).setVisible(true);
//        });

    }//GEN-LAST:event_pdfExportMenuItemActionPerformed

    private void wahapediaWarlordTraitImportMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wahapediaWarlordTraitImportMenuItemActionPerformed
        String urlString = JOptionPane.showInputDialog(this, "Type the Wahapedia faction URL to import Misc cards for", "Wahapedia Misc cards import", JOptionPane.QUESTION_MESSAGE);
        if (urlString == null) {
            return;
        }
        Callable<List<String>> callable = () -> {
            WahapediaMiscCardBuilder builder = new WahapediaMiscCardBuilder(1, false, true);
            cards.addAll(builder.build(new URL(urlString).openStream()));
            updateTree();
            return null;
        };
        WaitingDialog.show(this, "Loading misc cards from Wahapedia...", callable);
        }//GEN-LAST:event_wahapediaWarlordTraitImportMenuItemActionPerformed

    private void compactAllSectionsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_compactAllSectionsMenuItemActionPerformed
        SectionData singleSection = new SectionData();
        for (SectionData section : cards) {
            singleSection.addAll(section);
        }
        cards.clear();
        cards.add(singleSection);
        updateTree();
    }//GEN-LAST:event_compactAllSectionsMenuItemActionPerformed

    private void reorderNameSectionMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reorderNameSectionMenuItemActionPerformed
        if (actualSection == null) {
            return;
        }
        Collections.sort(actualSection, Comparator.comparing(CardData::getName));
        actualCard = null;
        updateSectionSubTree(actualSection, actualSectionNode);
    }//GEN-LAST:event_reorderNameSectionMenuItemActionPerformed

    private void reorderTitleSectionMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reorderTitleSectionMenuItemActionPerformed
        if (actualSection == null) {
            return;
        }
        Collections.sort(actualSection, Comparator.comparing(CardData::getTitle));
        actualCard = null;
        updateSectionSubTree(actualSection, actualSectionNode);
    }//GEN-LAST:event_reorderTitleSectionMenuItemActionPerformed

    private void cardTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_cardTreeValueChanged
        DefaultMutableTreeNode path = (DefaultMutableTreeNode) cardTree.getLastSelectedPathComponent();

        actualCard = null;
        actualCardNode = null;

        actualSection = null;
        actualSectionNode = null;

        if (path != null) {
            TreeNode[] pathNodes = path.getPath();

            for (int i = pathNodes.length - 1; i >= 0; i--) {
                DefaultMutableTreeNode pathNode = (DefaultMutableTreeNode) pathNodes[i];
                Object pathObject = pathNode.getUserObject();
                if (pathObject instanceof CardData) {
                    CardData cardNode = (CardData) pathObject;
                    actualCard = cardNode;
                    actualCardNode = pathNode;
                } else if (pathObject instanceof SectionData) {
                    SectionData sectionNode = (SectionData) pathObject;
                    actualSection = sectionNode;
                    actualSectionNode = pathNode;
                }
            }
        }
        forceUpdateUI();
    }//GEN-LAST:event_cardTreeValueChanged

    private void wahapediaAllImportMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wahapediaAllImportMenuItemActionPerformed
        String urlString = JOptionPane.showInputDialog(this, "Type the Wahapedia faction URL to import all cards for", "Wahapedia all cards import", JOptionPane.QUESTION_MESSAGE);
        if (urlString == null) {
            return;
        }
        Callable<List<String>> callable = () -> {
            WahapediaAllCardBuilder builder = new WahapediaAllCardBuilder(1, false, true);
            cards.addAll(builder.build(new URL(urlString).openStream()));
            updateTree();
            return null;
        };
        WaitingDialog.show(this, "Loading all cards from Wahapedia...", callable);
    }//GEN-LAST:event_wahapediaAllImportMenuItemActionPerformed

    private void cardsXmlImportMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cardsXmlImportMenuItemActionPerformed
        JFileChooser chooser = createXmlFileChooser();
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            loadCards(chooser.getSelectedFile(), "Importing...", false);
        }
    }//GEN-LAST:event_cardsXmlImportMenuItemActionPerformed

    private void deduplicateMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deduplicateMenuItemActionPerformed

        SectionData deduplicated = new SectionData(ICardInput.createSectionsDeduplicator(cards));
        for (SectionData list : cards) {
            list.retainAll(deduplicated);
        }
        updateTree();
    }//GEN-LAST:event_deduplicateMenuItemActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        dispose();
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void duplicateCardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_duplicateCardButtonActionPerformed
        if (actualSection == null && actualCard != null) {
            return;
        }
        CardData card = new CardData(actualCard.getTitle(), actualCard.getName(), actualCard.getLegend(), actualCard.getRules(), actualCard.getCostValue(), actualCard.getCostType());
        actualSection.add(card);
        DefaultMutableTreeNode cardNode = createCardNode(card);
        actualSectionNode.add(cardNode);
        forceUpdateUI();
    }//GEN-LAST:event_duplicateCardButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addCardButton;
    private javax.swing.JButton addSectionButton;
    private javax.swing.JTree cardTree;
    private javax.swing.JScrollPane cardTreeScrollPane;
    private javax.swing.JPanel cardsButtonsPanel;
    private javax.swing.JMenuItem cardsXmlImportMenuItem;
    private javax.swing.JMenuItem compactAllSectionsMenuItem;
    private javax.swing.JPanel costPanel;
    private javax.swing.JLabel costTypeLabel;
    private javax.swing.JPanel costTypePanel;
    private javax.swing.JTextField costTypeTextField;
    private javax.swing.JLabel costValueLabel;
    private javax.swing.JPanel costValuePanel;
    private javax.swing.JTextField costValueTextField;
    private javax.swing.JPanel dataPanel;
    private javax.swing.JMenuItem deduplicateMenuItem;
    private javax.swing.JButton duplicateCardButton;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu exportMenu;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu importMenu;
    private javax.swing.JLabel legendLabel;
    private javax.swing.JScrollPane legendScrollPane;
    private javax.swing.JTextArea legendTextArea;
    private javax.swing.JMenuItem loadMenuItem;
    private javax.swing.JMenuBar mainMenuBar1;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JMenuItem newMenuItem;
    private javax.swing.JMenuItem pdfExportMenuItem;
    private javax.swing.JButton removeCardButton;
    private javax.swing.JButton removeSectionButton;
    private javax.swing.JMenuItem reorderNameSectionMenuItem;
    private javax.swing.JMenuItem reorderTitleSectionMenuItem;
    private javax.swing.JLabel rulesLabel;
    private javax.swing.JScrollPane rulesScrollPane;
    private javax.swing.JTextArea rulesTextArea;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JMenu sectionMenu;
    private javax.swing.JPanel sectionsButtonsPanel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JTextField titleTextField;
    private javax.swing.JPanel treeButtonsPanel;
    private javax.swing.JPanel treePanel;
    private javax.swing.JMenuItem wahapediaAllImportMenuItem;
    private javax.swing.JMenuItem wahapediaStratagemImportMenuItem;
    private javax.swing.JMenuItem wahapediaWarlordTraitImportMenuItem;
    // End of variables declaration//GEN-END:variables

    public void loadCards(File file, String waitTitle, boolean clear) {
        Callable<List<String>> callable = () -> {
            FileInputStream fis = new FileInputStream(file);
            XmlCardInput input = new XmlCardInput();

            CardCollectionData newCards = input.build(fis);

            if (clear) {
                cardTree.clearSelection();
                cards.clear();
            }
            cards.addAll(newCards);
            updateTree();
            if (clear) {
                setActualFile(file);
            }
            return null;
        };
        WaitingDialog.show(MainWindow.this, waitTitle, callable);
    }

    private void saveCards(File file) {
        Callable<List<String>> callable = () -> {
            FileOutputStream fos = new FileOutputStream(file);
            XmlCardOutput output = new XmlCardOutput();
            output.build(fos, cards, new WarningArrayList());
            setActualFile(file);
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

    public static File getChooserSelectedFile(JFileChooser chooser, String extension) {
        File file = chooser.getSelectedFile();
        if (file.getName().indexOf('.') < 0) {
            file = new File(file.getAbsolutePath() + "." + extension);
        }
        return file;
    }

    private void updateButtonsFields() {

        boolean hasActualSection = (actualSection != null);
        addCardButton.setEnabled(hasActualSection);
        removeSectionButton.setEnabled(hasActualSection);
        reorderTitleSectionMenuItem.setEnabled(hasActualSection);
        reorderNameSectionMenuItem.setEnabled(hasActualSection);

        boolean hasActualCard = (actualCard != null);
        removeCardButton.setEnabled(hasActualCard);
        duplicateCardButton.setEnabled(hasActualCard);
        titleTextField.setEnabled(hasActualCard);
        nameTextField.setEnabled(hasActualCard);
        legendTextArea.setEnabled(hasActualCard);
        rulesTextArea.setEnabled(hasActualCard);
        costValueTextField.setEnabled(hasActualCard);
        costTypeTextField.setEnabled(hasActualCard);

        if (hasActualCard) {
            titleTextField.setText(actualCard.getTitle());
            nameTextField.setText(actualCard.getName());
            legendTextArea.setText(actualCard.getLegend());
            rulesTextArea.setText(actualCard.getRules());
            costValueTextField.setText(actualCard.getCostValue());
            costTypeTextField.setText(actualCard.getCostType());
        } else {
            titleTextField.setText("");
            nameTextField.setText("");
            legendTextArea.setText("");
            rulesTextArea.setText("");
            costValueTextField.setText("");
            costTypeTextField.setText("");
        }
    }

    private void selectAndScroll(DefaultMutableTreeNode node) {
        TreePath path = createPath(node);
        cardTree.setSelectionPath(path);
        cardTree.scrollPathToVisible(path);
    }

    private void updateTree() {

        root.removeAllChildren();
        for (SectionData section : cards) {
            DefaultMutableTreeNode sectionNode = createSectionNode(section);
            root.add(sectionNode);
            updateNode(section, sectionNode);
        }
        expandNodes(cardTree, root);
        forceUpdateUI();
    }

    private void updateSectionSubTree(SectionData section, DefaultMutableTreeNode sectionNode) {

        sectionNode.removeAllChildren();
        updateNode(section, sectionNode);
        forceUpdateUI();
    }

    private void updateNode(SectionData section, DefaultMutableTreeNode sectionNode) {

        for (CardData c : section) {
            sectionNode.add(createCardNode(c));
        }
    }

    private void forceUpdateUI() {
        java.awt.EventQueue.invokeLater(() -> {
            cardTree.updateUI();
            updateButtonsFields();
        });
    }

    public boolean moveCard(int oldCardIndex, int oldSectionIndex, int newCardIndex, int newSectionIndex) {
        if (newSectionIndex == oldSectionIndex) {
            if (newCardIndex == oldCardIndex) {
                return false;
            } else if (newCardIndex > oldCardIndex) {
                // Need to decrease the new index because upon removing the
                // card will move the intented position one position down
                newCardIndex--;
            }
        }
        SectionData oldSection = cards.get(cards.getBoundIndex(oldSectionIndex));
        CardData card = oldSection.remove(oldSection.getBoundIndex(oldCardIndex));
        SectionData newSection = cards.get(cards.getBoundIndex(newSectionIndex));
        newSection.add(newSection.getBoundIndexPlusOne(newCardIndex), card);
        updateTree();
        return true;
    }

    public boolean moveSection(int oldSectionIndex, int newSectionIndex) {
        if (newSectionIndex == oldSectionIndex) {
            return false;
        } else if (newSectionIndex > oldSectionIndex) {
            // Need to decrease the new index because upon removing the
            // section will move the intented position one position down
            newSectionIndex--;
        }
        SectionData section = cards.remove(cards.getBoundIndex(oldSectionIndex));
        cards.add(cards.getBoundIndexPlusOne(newSectionIndex), section);
        updateTree();
        return true;
    }

    private Image createIcon(String resourceName) {
        try {
            ClassLoader ccl = Thread.currentThread().getContextClassLoader();
            return ImageIO.read(ccl.getResourceAsStream(resourceName));
        } catch (Exception _ignored) {
        }
        return null;

    }

    private static DefaultMutableTreeNode createCardNode(CardData card) {
        return new DefaultMutableTreeNode(card, false);
    }

    private static DefaultMutableTreeNode createSectionNode(SectionData section) {
        return new DefaultMutableTreeNode(section, true);
    }

    private static void expandNodes(JTree tree, DefaultMutableTreeNode node) {
        ArrayList<DefaultMutableTreeNode> list = Collections.list(node.children());
        for (DefaultMutableTreeNode treeNode : list) {
            expandNodes(tree, treeNode);
        }
        if (!node.isRoot()) {
            TreePath path = createPath(node);
            tree.expandPath(path);
            try {
                tree.expandPath(path);
            } catch (NullPointerException _tryAgain) {
                try {
                    tree.expandPath(path);
                } catch (NullPointerException _whatTheFrackAreYouDoingSwing) {
                }
            }
        }
    }

    private static TreePath createPath(DefaultMutableTreeNode node) {
        return new TreePath(node.getPath());
    }

    private void setActualFile(File file) {
        actualFile = file;
        setTitle(file != null ? file.getName().concat(" - Card editor") : "Card editor");
    }

    private class CardTreeCellRenderer extends DefaultTreeCellRenderer {

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            Object o = node.getUserObject();
            if (o instanceof CardData) {
                value = ((CardData) o).getName();
            } else if (o instanceof SectionData) {
                SectionData section = (SectionData) o;
                int index = cards.getExactIndex(section) + 1;
                String sectionName = section.getName();
                if (sectionName.isEmpty()) {
                    value = "Section " + index + " (" + section.size() + ")";
                } else {
                    value = "Section " + index + " - " + sectionName + " (" + section.size() + ")";
                }
            }
            return super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
        }

    }

}
