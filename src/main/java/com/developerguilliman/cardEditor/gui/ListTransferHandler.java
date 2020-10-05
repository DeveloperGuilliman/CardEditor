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

import java.awt.datatransfer.*;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.List;
import javax.swing.*;

/**
 *
 * @author DeveloperGuilliman
 * @param <T>
 */
public class ListTransferHandler<T> extends TransferHandler {

    private final DataFlavor dataFlavor;
    private final AbstractListListModel<T> listModel;

    public ListTransferHandler(AbstractListListModel<T> listModel) {
        this.dataFlavor = new DataFlavor(listModel.getClass(), null);
        this.listModel = listModel;
    }

    @Override
    public boolean canImport(TransferHandler.TransferSupport info) {
        return info.isDataFlavorSupported(dataFlavor);
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        return new Transferable() {
            @Override
            public DataFlavor[] getTransferDataFlavors() {
                return new DataFlavor[]{dataFlavor};
            }

            @Override
            public boolean isDataFlavorSupported(DataFlavor flavor) {
                return dataFlavor.equals(flavor);
            }

            @Override
            public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
                JList list = (JList) c;
                int index = list.getSelectedIndex();
                return new AbstractMap.SimpleImmutableEntry<>(index, listModel.getList().get(index));
            }
        };
    }

    @Override
    public int getSourceActions(JComponent c) {
        return TransferHandler.MOVE;
    }

    @Override
    public boolean importData(TransferHandler.TransferSupport info) {
        if (!info.isDrop()) {
            return false;
        }

        JList<?> jlist = (JList<?>) info.getComponent();
        JList.DropLocation dl = (JList.DropLocation) info.getDropLocation();
        int newIndex = dl.getIndex();

        Transferable t = info.getTransferable();
        AbstractMap.SimpleImmutableEntry<Integer, T> data;
        try {
            data = (AbstractMap.SimpleImmutableEntry<Integer, T>) t.getTransferData(dataFlavor);
        } catch (UnsupportedFlavorException | IOException e) {
            return false;
        }

        int oldIndex = data.getKey();
        int selectedIndex = newIndex;
        if (newIndex < oldIndex) {
            oldIndex++;
        } else {
            selectedIndex--;
        }
        if (oldIndex == newIndex) {
            return false;
        }
        T value = data.getValue();
        List<T> list = listModel.getList();

        list.add(newIndex, value);
        list.remove(oldIndex);
        jlist.setSelectedIndex(selectedIndex);
        jlist.updateUI();
        return true;
    }

    @Override
    protected void exportDone(JComponent c, Transferable data, int action) {
    }

}
