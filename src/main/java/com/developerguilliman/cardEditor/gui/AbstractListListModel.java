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

import java.util.List;
import javax.swing.AbstractListModel;

/**
 *
 * @author DeveloperGuilliman
 * @param <T>
 */
public abstract class AbstractListListModel<T> extends AbstractListModel<String> {

    @Override
    public int getSize() {
        List<T> list = getList();
        return (list != null) ? list.size() : 0;
    }

    @Override
    public String getElementAt(int index) {
        List<T> list = getList();
        return createString(index, (list != null) ? list.get(index) : null);
    }

    protected abstract List<T> getList();

    protected abstract String createString(int index, T element);

}
