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
import com.developerguilliman.cardEditor.input.ICardInput;
import com.developerguilliman.cardEditor.input.WahapediaAllCardBuilder;
import com.developerguilliman.cardEditor.input.WahapediaMiscCardBuilder;
import com.developerguilliman.cardEditor.input.WahapediaStratagemCardBuilder;
import com.developerguilliman.cardEditor.input.XmlCardInput;
import com.developerguilliman.cardEditor.output.XmlCardOutput;
import java.awt.Component;
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
 * @author DeveloperGuilliman
 */
public class MainWindow extends javax.swing.JFrame {

    private final List<List<CardData>> cards;
    private final DefaultMutableTreeNode root;

    private List<CardData> actualSection;
    private CardData actualCard;

    private DefaultMutableTreeNode actualSectionNode;
    private DefaultMutableTreeNode actualCardNode;

    private File actualFile;

    /**
     * Creates new form Main
     */
    public MainWindow() {
        cards = new ArrayList<>();
        initComponents();

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
        sectionButtonsPanel = new javax.swing.JPanel();
        addSectionButton = new javax.swing.JButton();
        removeSectionButton = new javax.swing.JButton();
        addCardButton = new javax.swing.JButton();
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
        costLabel = new javax.swing.JLabel();
        costTextField = new javax.swing.JTextField();
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
        setMinimumSize(new java.awt.Dimension(600, 460));
        setPreferredSize(new java.awt.Dimension(780, 580));

        treePanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        treePanel.setLayout(new java.awt.BorderLayout());

        sectionButtonsPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 3, 0));
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

        addCardButton.setText("Add Card");
        addCardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCardButtonActionPerformed(evt);
            }
        });
        sectionButtonsPanel.add(addCardButton);

        removeCardButton.setText("Remove Card");
        removeCardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeCardButtonActionPerformed(evt);
            }
        });
        sectionButtonsPanel.add(removeCardButton);

        treePanel.add(sectionButtonsPanel, java.awt.BorderLayout.NORTH);

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
        ArrayList section = new ArrayList();
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
        int position = getExactIndex(cards, actualSection);
        cards.remove(position);
        root.remove(position);
        actualCard = null;
        actualCardNode = null;
        position = getBoundIndex(cards, position);
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
        int position = getExactIndex(actualSection, actualCard);
        actualSection.remove(position);
        actualSectionNode.remove(position);
        position = getBoundIndex(actualSection, position);
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
        Callable<Void> callable = () -> {
            WahapediaStratagemCardBuilder builder = new WahapediaStratagemCardBuilder(1, false, true);
            cards.addAll(builder.build(new URL(urlString).openStream()));
            updateTree();
            return null;
        };
        WaitingDialog.show(this, "Loading stratagem cards from Wahapedia...", callable);

    }//GEN-LAST:event_wahapediaStratagemImportMenuItemActionPerformed

    private void pdfExportMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pdfExportMenuItemActionPerformed

//        java.awt.EventQueue.invokeLater(() -> {
        new PdfCreateOptionsDialog(this, actualFile, cards).setVisible(true);
//        });

    }//GEN-LAST:event_pdfExportMenuItemActionPerformed

    private void wahapediaWarlordTraitImportMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wahapediaWarlordTraitImportMenuItemActionPerformed
        String urlString = JOptionPane.showInputDialog(this, "Type the Wahapedia faction URL to import Misc cards for", "Wahapedia Misc cards import", JOptionPane.QUESTION_MESSAGE);
        if (urlString == null) {
            return;
        }
        Callable<Void> callable = () -> {
            WahapediaMiscCardBuilder builder = new WahapediaMiscCardBuilder(1, false, true);
            cards.addAll(builder.build(new URL(urlString).openStream()));
            updateTree();
            return null;
        };
        WaitingDialog.show(this, "Loading misc cards from Wahapedia...", callable);
        }//GEN-LAST:event_wahapediaWarlordTraitImportMenuItemActionPerformed

    private void compactAllSectionsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_compactAllSectionsMenuItemActionPerformed
        ArrayList<CardData> singleSection = new ArrayList<>();
        for (List<CardData> section : cards) {
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
                } else if (pathObject instanceof List) {
                    List<CardData> sectionNode = (List<CardData>) pathObject;
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
        Callable<Void> callable = () -> {
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

        ArrayList<CardData> deduplicated = new ArrayList<>(ICardInput.createSectionsDeduplicator(cards));
        for (List<CardData> list : cards) {
            list.retainAll(deduplicated);
        }
        updateTree();
    }//GEN-LAST:event_deduplicateMenuItemActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        dispose();
    }//GEN-LAST:event_exitMenuItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addCardButton;
    private javax.swing.JButton addSectionButton;
    private javax.swing.JTree cardTree;
    private javax.swing.JScrollPane cardTreeScrollPane;
    private javax.swing.JMenuItem cardsXmlImportMenuItem;
    private javax.swing.JMenuItem compactAllSectionsMenuItem;
    private javax.swing.JLabel costLabel;
    private javax.swing.JTextField costTextField;
    private javax.swing.JPanel dataPanel;
    private javax.swing.JMenuItem deduplicateMenuItem;
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
    private javax.swing.JPanel sectionButtonsPanel;
    private javax.swing.JMenu sectionMenu;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JTextField titleTextField;
    private javax.swing.JPanel treePanel;
    private javax.swing.JMenuItem wahapediaAllImportMenuItem;
    private javax.swing.JMenuItem wahapediaStratagemImportMenuItem;
    private javax.swing.JMenuItem wahapediaWarlordTraitImportMenuItem;
    // End of variables declaration//GEN-END:variables

    public void loadCards(File file, String waitTitle, boolean clear) {
        Callable<Void> callable = () -> {
            FileInputStream fis = new FileInputStream(file);
            XmlCardInput input = new XmlCardInput();

            List<List<CardData>> newCards = input.build(fis);

            if (clear) {
                cardTree.clearSelection();
                cards.clear();
            }
            cards.addAll(newCards);
            updateTree();
            if (clear) {
                actualFile = file;
            }
            return null;
        };
        WaitingDialog.show(MainWindow.this, waitTitle, callable);
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
        titleTextField.setEnabled(hasActualCard);
        nameTextField.setEnabled(hasActualCard);
        legendTextArea.setEnabled(hasActualCard);
        rulesTextArea.setEnabled(hasActualCard);
        costTextField.setEnabled(hasActualCard);

        if (hasActualCard) {
            titleTextField.setText(actualCard.getTitle());
            nameTextField.setText(actualCard.getName());
            legendTextArea.setText(actualCard.getLegend());
            rulesTextArea.setText(actualCard.getRules());
            costTextField.setText(actualCard.getCost());
        } else {
            titleTextField.setText("");
            nameTextField.setText("");
            legendTextArea.setText("");
            rulesTextArea.setText("");
            costTextField.setText("");
        }
    }

    private void selectAndScroll(DefaultMutableTreeNode node) {
        TreePath path = createPath(node);
        cardTree.setSelectionPath(path);
        cardTree.scrollPathToVisible(path);
    }

    private void updateTree() {

        root.removeAllChildren();
        for (List<CardData> section : cards) {
            DefaultMutableTreeNode sectionNode = createSectionNode(section);
            root.add(sectionNode);
            updateNode(section, sectionNode);
        }
        expandNodes(cardTree, root);
        forceUpdateUI();
    }

    private void updateSectionSubTree(List<CardData> section, DefaultMutableTreeNode sectionNode) {

        sectionNode.removeAllChildren();
        updateNode(section, sectionNode);
        forceUpdateUI();
    }

    private void updateNode(List<CardData> section, DefaultMutableTreeNode sectionNode) {

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
        if (oldCardIndex == newCardIndex && oldSectionIndex == newSectionIndex) {
            return false;
        }
        if (oldSectionIndex == newSectionIndex && newSectionIndex > oldSectionIndex) {
            newSectionIndex--;
        }
        List<CardData> oldSection = cards.get(getBoundIndex(cards, oldSectionIndex));
        CardData card = oldSection.remove(getBoundIndex(oldSection, oldCardIndex));
        List<CardData> newSection = cards.get(getBoundIndex(cards, newSectionIndex));
        newSection.add(Math.max(0, Math.min(newSection.size(), newCardIndex)), card);
        updateTree();
        return true;
    }

    public boolean moveSection(int oldSectionIndex, int newSectionIndex) {
        if (oldSectionIndex == newSectionIndex) {
            return false;
        }
        if (newSectionIndex > oldSectionIndex) {
            newSectionIndex--;
        }
        List<CardData> section = cards.remove(getBoundIndex(cards, oldSectionIndex));
        cards.add(Math.max(0, Math.min(cards.size(), newSectionIndex)), section);
        updateTree();
        return true;
    }

    private static DefaultMutableTreeNode createCardNode(CardData card) {
        return new DefaultMutableTreeNode(card, false);
    }

    private static DefaultMutableTreeNode createSectionNode(List<CardData> section) {
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
        }
    }

    private static TreePath createPath(DefaultMutableTreeNode node) {
        return new TreePath(node.getPath());
    }

    private static <T> int getExactIndex(List<T> list, T element) {
        int i = 0;

        for (T listItem : list) {
            if (listItem == element) {
                return i;
            }
            i++;
        }
        return -1;
    }

    private int getBoundIndex(List<?> list, int index) {
        return Math.max(0, Math.min(list.size() - 1, index));
    }

    private class CardTreeCellRenderer extends DefaultTreeCellRenderer {

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            Object o = node.getUserObject();
            if (o instanceof CardData) {
                value = ((CardData) o).getName();
            } else if (o instanceof List) {
                List<CardData> section = (List<CardData>) o;
                int index = getExactIndex(cards, section) + 1;
                String sectionName = getSectionName(section);
                if (sectionName.isEmpty()) {
                    value = "Section " + index + " (" + section.size() + ")";
                } else {
                    value = "Section " + index + " - " + sectionName + " (" + section.size() + ")";
                }
            }
            return super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
        }

    }

    private static String getSectionName(List<CardData> section) {
        if (!section.isEmpty()) {
            String title = normalize(section.get(0).getTitle());
            if (title.isEmpty()) {
                return "";
            }
            for (CardData card : section) {
                if (!title.equals(normalize(card.getTitle()))) {
                    return "";
                }
            }
            return title;
        }
        return "";
    }

    private static String normalize(String s) {
        return (s == null) ? "" : s.trim();
    }

}
