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
package com.developerguilliman.cardEditor.warning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author DeveloperGuilliman
 */
public class WarningArrayList implements IWarningHandler {

    private final ArrayList<String> warnings;

    public WarningArrayList() {
        this.warnings = new ArrayList<>();
    }

    @Override
    public void warn(String warningText) {
        warnings.add(warningText);
    }

    public List<String> getWarnings() {
        return Collections.unmodifiableList(warnings);
    }
    
    public boolean isEmpty() {
        return warnings.isEmpty();
    }

}
