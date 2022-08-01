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

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.developerguilliman.cardEditor.data.CardCollectionData;
import com.developerguilliman.cardEditor.data.CardData;
import com.developerguilliman.cardEditor.data.SectionData;

/**
 *
 * @author Developer Guilliman <developerguilliman@gmail.com>
 */
public class CardTreeCellRenderer extends DefaultTreeCellRenderer {

    private final CardCollectionData cards;

    public CardTreeCellRenderer(CardCollectionData cards) {
        this.cards = cards;
    }

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
