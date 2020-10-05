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
import java.util.List;
import javax.swing.*;

/**
 *
 * @author DeveloperGuilliman
 * @param <T>
 */
public class ListTransferHandler<T> extends TransferHandler {

    private final DataFlavor[] dataFlavors;

    public ListTransferHandler() {
        this.dataFlavors = new DataFlavor[]{new DataFlavor(List.class, null)};

    }

    public ListTransferHandler(Class<T> clazz) {
        this.dataFlavors = new DataFlavor[]{new DataFlavor(clazz, null), new DataFlavor(List.class, null)};
    }

    @Override
    public boolean canImport(TransferHandler.TransferSupport info) {
        for (DataFlavor df : dataFlavors) {
            if (info.isDataFlavorSupported(df)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        return new Transferable() {
            @Override
            public DataFlavor[] getTransferDataFlavors() {
                return dataFlavors;
            }

            @Override
            public boolean isDataFlavorSupported(DataFlavor flavor) {
                for (DataFlavor df : dataFlavors) {
                    if (flavor.match(df)) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
                JList list = (JList) c;
                return list.getSelectedValue();
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
        T data;
        try {
            data = (T) t.getTransferData(dataFlavors[0]);
        } catch (UnsupportedFlavorException | IOException e) {
            return false;
        }

        int selectedIndex = newIndex;
        AbstractListListModel<T> list = (AbstractListListModel<T>) jlist.getModel();
        list.add(newIndex, data);
        jlist.setSelectedIndex(selectedIndex);
        jlist.updateUI();
        return true;
    }

    @Override
    protected void exportDone(JComponent c, Transferable data, int action) {
    }

}
