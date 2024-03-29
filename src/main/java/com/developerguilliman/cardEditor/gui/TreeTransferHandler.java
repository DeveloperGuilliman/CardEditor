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

import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.developerguilliman.cardEditor.data.CardData;
import com.developerguilliman.cardEditor.data.SectionData;

/**
 *
 * @author Developer Guilliman <developerguilliman@gmail.com>
 */
public class TreeTransferHandler extends TransferHandler {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2927124919317940240L;
	
	private final JTree cardTree;
    private final DataFlavor dataFlavor;

    public TreeTransferHandler(JTree cardTree) {
        this.cardTree = cardTree;
        this.dataFlavor = new DataFlavor(CardData.class, null);
    }

    @Override
    public int getSourceActions(JComponent c) {
        return TransferHandler.MOVE;
    }

    @Override
    public boolean canImport(TransferSupport support) {
        return (support.getComponent() == cardTree);
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        TreePath path = cardTree.getSelectionPath();

        if (path == null) {
            return null;
        }

        Object[] pathNodes = path.getPath();
        int len = pathNodes.length - 1;
        int[] route = new int[len];

        for (int i = 0; i < route.length; i++) {
            DefaultMutableTreeNode pathNode = (DefaultMutableTreeNode) pathNodes[i];
            route[i] = pathNode.getIndex((DefaultMutableTreeNode) pathNodes[i + 1]);
        }

        DataMove transferData;
        switch (len) {
            case 2:
                transferData = new CardMove(route[1], route[0]);
                break;
            case 1:
                transferData = new SectionMove(route[0]);
                break;
            default:
                return null;
        }
        return new Transferable() {
            @Override
            public DataFlavor[] getTransferDataFlavors() {
                return new DataFlavor[]{dataFlavor};
            }

            @Override
            public boolean isDataFlavorSupported(DataFlavor flavor) {
                return flavor == dataFlavor;
            }

            @Override
            public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
                return transferData;
            }
        };
    }

    @Override
    public boolean importData(TransferHandler.TransferSupport info) {
        if (!info.isDrop()) {
            return false;
        }

        try {
            Component component = info.getComponent();
            while (!(component instanceof MainWindow)) {
                component = component.getParent();
            }
            MainWindow mw = (MainWindow) component;
            DataMove dataMove = (DataMove) info.getTransferable().getTransferData(dataFlavor);
            return dataMove.importData(mw, (JTree.DropLocation) info.getDropLocation());
        } catch (UnsupportedFlavorException | IOException | RuntimeException ex) {
            System.err.println(ex);
            return false;
        }
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
        try {
            DataMove dataMove = (DataMove) data.getTransferData(dataFlavor);
            dataMove.exportDone();
        } catch (UnsupportedFlavorException | IOException | RuntimeException ex) {
            System.err.println(ex);
        }
    }

    private interface DataMove extends Serializable {

        public boolean importData(MainWindow mw, JTree.DropLocation dropLocation);

        public void exportDone();

    }

    private static class CardMove implements DataMove {

        /**
		 * 
		 */
		private static final long serialVersionUID = 2998071903857465469L;
		
		private final int cardIndex;
        private final int sectionIndex;

        public CardMove(int cardIndex, int sectionIndex) {
            this.cardIndex = cardIndex;
            this.sectionIndex = sectionIndex;
        }

        @Override
        public boolean importData(MainWindow mw, JTree.DropLocation dropLocation) {

            Object[] newPath = dropLocation.getPath().getPath();
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) newPath[0];
            int newCardIndex;
            int newSectionIndex;
            if (newPath.length > 1) {
                newCardIndex = dropLocation.getChildIndex();
                newSectionIndex = node.getIndex((DefaultMutableTreeNode) newPath[1]);
            } else {
                newSectionIndex = dropLocation.getChildIndex() - 1;
                newCardIndex = (newSectionIndex < 0) ? 0 : Integer.MAX_VALUE;
            }
            return mw.moveCard(cardIndex, sectionIndex, newCardIndex, newSectionIndex);

        }

        @Override
        public void exportDone() {
        }

    }

    private static class SectionMove implements DataMove {

        /**
		 * 
		 */
		private static final long serialVersionUID = 4161203204519347563L;
		
		private final int sectionIndex;

        public SectionMove(int sectionIndex) {
            this.sectionIndex = sectionIndex;
        }

        @Override
        public boolean importData(MainWindow mw, JTree.DropLocation dropLocation) {
            Object[] newPath = dropLocation.getPath().getPath();
            int newSectionIndex;
            if (newPath.length > 1) {
                return false;
            } else {
                newSectionIndex = dropLocation.getChildIndex();
            }
            return mw.moveSection(sectionIndex, newSectionIndex);
        }

        @Override
        public void exportDone() {
        }

    }

    private static CardData getPathCard(TreePath path) {
        Object[] pathNodes = path.getPath();

        for (int i = pathNodes.length - 1; i >= 0; i--) {
            DefaultMutableTreeNode pathNode = (DefaultMutableTreeNode) pathNodes[i];
            Object pathObject = pathNode.getUserObject();
            if (pathObject instanceof CardData) {
                return (CardData) pathObject;
            }
        }
        return null;
    }

    private static SectionData getPathSection(TreePath path) {
        Object[] pathNodes = path.getPath();

        for (int i = pathNodes.length - 1; i >= 0; i--) {
            DefaultMutableTreeNode pathNode = (DefaultMutableTreeNode) pathNodes[i];
            Object pathObject = pathNode.getUserObject();
            if (pathObject instanceof SectionData) {
                return (SectionData) pathObject;
            }
        }
        return null;
    }

}
