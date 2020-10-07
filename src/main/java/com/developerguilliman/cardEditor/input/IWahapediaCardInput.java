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
package com.developerguilliman.cardEditor.input;

import com.developerguilliman.cardEditor.data.CardData;
import java.util.List;
import org.jsoup.nodes.Document;

/**
 *
 * @author DeveloperGuilliman
 */
interface IWahapediaCardInput extends ICardInput {

    public static CardData createCard(String title, String name, String description, String rules, String cost) {
        return new CardData(title.trim(), name.trim().toUpperCase(), description.trim(), rules.trim(), cost.trim());
    }

    public void buildFromHtml(Document doc, List<CardData> list);

}
