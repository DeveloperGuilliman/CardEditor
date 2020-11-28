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
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.Callable;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author Developer Guilliman <developerguilliman@gmail.com>
 */
public class CardImportDialog extends javax.swing.JDialog {

    private final CardCollectionData cards;
    private final DefaultMutableTreeNode root;

    /**
     * Creates new form CardImportDialog
     *
     * @param parent
     * @param cards
     */
    public CardImportDialog(MainWindow parent, CardCollectionData cards) {
        super(parent, true);
        this.cards = cards;
        initComponents();
        root = new DefaultMutableTreeNode("Cards", true);
        cardTree.setModel(new DefaultTreeModel(root, true));
        cardTree.getSelectionModel().setSelectionMode(DefaultTreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        updateTree(true);
        importButton.requestFocus();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cardTreeScrollPane = new javax.swing.JScrollPane();
        cardTree = new javax.swing.JTree();
        buttonsPanel = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jPanel1 = new javax.swing.JPanel();
        selectedCardsLabel = new javax.swing.JLabel();
        importButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(500, 480));
        setModal(true);
        getContentPane().setLayout(new java.awt.BorderLayout());

        cardTreeScrollPane.setMinimumSize(new java.awt.Dimension(200, 23));
        cardTreeScrollPane.setPreferredSize(new java.awt.Dimension(300, 352));

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        cardTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        cardTree.setCellRenderer(new CardTreeCellRenderer(cards));
        cardTree.setRootVisible(false);
        cardTree.setShowsRootHandles(true);
        cardTree.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                cardTreeMouseMoved(evt);
            }
        });
        cardTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                cardTreeValueChanged(evt);
            }
        });
        cardTreeScrollPane.setViewportView(cardTree);

        getContentPane().add(cardTreeScrollPane, java.awt.BorderLayout.WEST);

        buttonsPanel.setLayout(new javax.swing.BoxLayout(buttonsPanel, javax.swing.BoxLayout.LINE_AXIS));
        buttonsPanel.add(filler1);

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 2, 0, 2));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));
        jPanel1.add(selectedCardsLabel);

        buttonsPanel.add(jPanel1);

        importButton.setText("Import selected");
        importButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importButtonActionPerformed(evt);
            }
        });
        buttonsPanel.add(importButton);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        buttonsPanel.add(cancelButton);

        getContentPane().add(buttonsPanel, java.awt.BorderLayout.SOUTH);

        dataPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        dataPanel.setLayout(new javax.swing.BoxLayout(dataPanel, javax.swing.BoxLayout.PAGE_AXIS));

        titleLabel.setText("Title");
        dataPanel.add(titleLabel);

        titleTextField.setEnabled(false);
        titleTextField.setMaximumSize(new java.awt.Dimension(2147483647, 20));
        dataPanel.add(titleTextField);

        nameLabel.setText("Name");
        dataPanel.add(nameLabel);

        nameTextField.setEnabled(false);
        nameTextField.setMaximumSize(new java.awt.Dimension(2147483647, 20));
        dataPanel.add(nameTextField);

        legendLabel.setText("Legend");
        dataPanel.add(legendLabel);

        legendScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        legendTextArea.setBackground(javax.swing.UIManager.getDefaults().getColor("TextField.disabledBackground"));
        legendTextArea.setColumns(20);
        legendTextArea.setLineWrap(true);
        legendTextArea.setRows(5);
        legendTextArea.setWrapStyleWord(true);
        legendTextArea.setEnabled(false);
        legendScrollPane.setViewportView(legendTextArea);

        dataPanel.add(legendScrollPane);

        rulesLabel.setText("Rules");
        dataPanel.add(rulesLabel);

        rulesScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        rulesTextArea.setBackground(javax.swing.UIManager.getDefaults().getColor("TextField.disabledBackground"));
        rulesTextArea.setColumns(20);
        rulesTextArea.setLineWrap(true);
        rulesTextArea.setRows(5);
        rulesTextArea.setWrapStyleWord(true);
        rulesTextArea.setEnabled(false);
        rulesScrollPane.setViewportView(rulesTextArea);

        dataPanel.add(rulesScrollPane);

        costPanel.setLayout(new javax.swing.BoxLayout(costPanel, javax.swing.BoxLayout.LINE_AXIS));

        costValuePanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 1));
        costValuePanel.setMaximumSize(new java.awt.Dimension(300, 34));
        costValuePanel.setLayout(new javax.swing.BoxLayout(costValuePanel, javax.swing.BoxLayout.Y_AXIS));

        costValueLabel.setText("Cost Value");
        costValueLabel.setMinimumSize(new java.awt.Dimension(300, 14));
        costValuePanel.add(costValueLabel);

        costValueTextField.setEnabled(false);
        costValueTextField.setMaximumSize(new java.awt.Dimension(300, 20));
        costValuePanel.add(costValueTextField);

        costPanel.add(costValuePanel);

        costTypePanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 1, 0, 0));
        costTypePanel.setLayout(new javax.swing.BoxLayout(costTypePanel, javax.swing.BoxLayout.Y_AXIS));

        costTypeLabel.setText("Cost Type");
        costTypePanel.add(costTypeLabel);

        costTypeTextField.setEnabled(false);
        costTypeTextField.setMaximumSize(new java.awt.Dimension(2147483647, 20));
        costTypePanel.add(costTypeTextField);

        costPanel.add(costTypePanel);

        dataPanel.add(costPanel);

        getContentPane().add(dataPanel, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 566, 438);
    }// </editor-fold>//GEN-END:initComponents

    private void importButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importButtonActionPerformed
        MainWindow mainWindow = (MainWindow) getParent();
        Callable<List<String>> callable = () -> {
            LinkedHashMap<SectionData, SectionData> newCards = new LinkedHashMap<>();
            for (TreePath selectedPath : cardTree.getSelectionPaths()) {
                DefaultMutableTreeNode pathNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();

                Object pathObject = pathNode.getUserObject();
                if (pathObject instanceof CardData) {
                    CardData cardNode = (CardData) pathObject;
                    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) pathNode.getParent();
                    SectionData listedSection = (SectionData) parentNode.getUserObject();
                    SectionData importSection = newCards.get(listedSection);
                    if (importSection == null) {
                        importSection = new SectionData();
                        newCards.put(listedSection, importSection);
                    }
                    importSection.add(cardNode);
                }
            }
            mainWindow.addCards(newCards.values());
            dispose();
            return null;
        };
        WaitingDialog.show(mainWindow, "Importing...", callable);

    }//GEN-LAST:event_importButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void cardTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_cardTreeValueChanged
        DefaultMutableTreeNode pathNode = (DefaultMutableTreeNode) evt.getPath().getLastPathComponent();
        Object pathObject = pathNode.getUserObject();
        if (pathObject instanceof SectionData) {
            selectNodes(cardTree, pathNode, evt.isAddedPath());
        }
        int selectedCards = getSelectedCards();
        int totalCards = getTotalCards();
        selectedCardsLabel.setText(selectedCards + "/" + totalCards);
    }//GEN-LAST:event_cardTreeValueChanged

    private void cardTreeMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cardTreeMouseMoved
        updateActualCard(evt.getX(), evt.getY());
    }//GEN-LAST:event_cardTreeMouseMoved

    private void updateActualCard(int x, int y) {
        CardData actualCard = getPathCardData(cardTree.getPathForLocation(x, y));
        if (actualCard != null) {
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

    private CardData getPathCardData(TreePath tp) {
        if (tp != null) {
            DefaultMutableTreeNode pathNode = (DefaultMutableTreeNode) tp.getLastPathComponent();
            if (pathNode != null) {
                Object pathObject = pathNode.getUserObject();
                if (pathObject instanceof CardData) {
                    return (CardData) pathObject;
                }
            }
        }
        return null;
    }

    private void updateTree(boolean select) {

        root.removeAllChildren();
        for (SectionData section : cards) {
            DefaultMutableTreeNode sectionNode = createSectionNode(section);
            root.add(sectionNode);
            updateNode(section, sectionNode);
        }
        selectNodes(cardTree, root, select);
        forceUpdateUI();
    }

    private void updateNode(SectionData section, DefaultMutableTreeNode sectionNode) {

        for (CardData c : section) {
            sectionNode.add(createCardNode(c));
        }
    }

    private static DefaultMutableTreeNode createCardNode(CardData card) {
        return new DefaultMutableTreeNode(card, false);
    }

    private static DefaultMutableTreeNode createSectionNode(SectionData section) {
        return new DefaultMutableTreeNode(section, true);
    }

    private int getSelectedCards() {
        int selected = 0;
        TreePath[] selectionPaths = cardTree.getSelectionPaths();
        if (selectionPaths != null) {
            for (TreePath selection : selectionPaths) {
                DefaultMutableTreeNode pathNode = (DefaultMutableTreeNode) selection.getLastPathComponent();
                selected += (pathNode.getUserObject() instanceof CardData) ? 1 : 0;
            }
        }
        return selected;
    }

    private int getTotalCards() {
        int total = 0;
        for (SectionData section : cards) {
            total += section.size();
        }
        return total;
    }

    private static void selectNodes(JTree tree, DefaultMutableTreeNode node, boolean select) {
        ArrayList<DefaultMutableTreeNode> list = Collections.list(node.children());
        for (DefaultMutableTreeNode treeNode : list) {
            selectNodes(tree, treeNode, select);
        }
        if (!node.isRoot()) {
            TreePath path = createPath(node);

            try {
                if (select) {
                    tree.addSelectionPath(path);
                } else {
                    tree.removeSelectionPath(path);
                }

            } catch (NullPointerException _tryAgain) {
                try {
                    if (select) {
                        tree.addSelectionPath(path);
                    } else {
                        tree.removeSelectionPath(path);
                    }
                } catch (NullPointerException _whatTheFrackAreYouDoingSwing) {
                }
            }
        }
    }

    private static TreePath createPath(DefaultMutableTreeNode node) {
        return new TreePath(node.getPath());
    }

    private void forceUpdateUI() {
        java.awt.EventQueue.invokeLater(() -> {
            cardTree.updateUI();
            selectedCardsLabel.setText(getSelectedCards() + "/" + getTotalCards());
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTree cardTree;
    private javax.swing.JScrollPane cardTreeScrollPane;
    private javax.swing.JPanel costPanel;
    private javax.swing.JLabel costTypeLabel;
    private javax.swing.JPanel costTypePanel;
    private javax.swing.JTextField costTypeTextField;
    private javax.swing.JLabel costValueLabel;
    private javax.swing.JPanel costValuePanel;
    private javax.swing.JTextField costValueTextField;
    private javax.swing.JPanel dataPanel;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JButton importButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel legendLabel;
    private javax.swing.JScrollPane legendScrollPane;
    private javax.swing.JTextArea legendTextArea;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JLabel rulesLabel;
    private javax.swing.JScrollPane rulesScrollPane;
    private javax.swing.JTextArea rulesTextArea;
    private javax.swing.JLabel selectedCardsLabel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JTextField titleTextField;
    // End of variables declaration//GEN-END:variables

}
