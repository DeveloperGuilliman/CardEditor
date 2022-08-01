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
package com.developerguilliman.cardEditor.input;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.TreeMap;

import com.developerguilliman.cardEditor.csv.WahapediaCsvBuilder;
import com.developerguilliman.cardEditor.data.CardCollectionData;
import com.developerguilliman.cardEditor.data.CardData;
import com.developerguilliman.cardEditor.data.Faction;
import com.developerguilliman.cardEditor.data.SectionData;

/**
*
* @author Developer Guilliman <developerguilliman@gmail.com>
*/
public class WahapediaStratagems implements ICardInput {

	private static final String STRATAGEM_URL = "https://wahapedia.ru/wh40k9ed/Stratagems.csv";

	private static final int FACTION_ROW = 0;
	
	private static final int NAME_ROW = 1;
	
	private static final int TYPE_ROW = 2;
	
	private static final int COST_ROW = 3;

	private static final int LEGEND_ROW = 4;

	private static final int SOURCE_ROW = 5;

	private static final int DESCRIPTION_ROW = 6;

	private static final int ID_ROW = 7;

	private final LinkedHashMap<String, Faction> factions;

	public WahapediaStratagems(LinkedHashMap<String, Faction> selectedFactions) {
		this.factions = selectedFactions;
	}

	@Override
	public CardCollectionData build(InputStream source) {
		try {
			
			ArrayList<ArrayList<String>> csvData = new WahapediaCsvBuilder().build(source).getData();
			
			TreeMap<String, SectionData> sections = new TreeMap<>();
			
			for (ArrayList<String> row : csvData) {
				String faction = row.get(FACTION_ROW).trim();
				if (!factions.keySet().contains(faction)) {
					continue;
				}
				String name = row.get(NAME_ROW).trim();
				String type = row.get(TYPE_ROW).trim();
				String cost = row.get(COST_ROW).trim();
				String legend = row.get(LEGEND_ROW).trim();
				String description = WahapediaCsvBuilder.stripHtml(row.get(DESCRIPTION_ROW));

				SectionData sectionData = sections.get(type);
				if (sectionData == null) {
					sectionData = new SectionData();
					sections.put(type, sectionData);
				}
				sectionData.add(new CardData(type, name, legend, description, cost, "COMMAND POINTS"));
			}
			return new CardCollectionData(sections.values());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static InputStream getInputStreamFromUrl() throws IOException {
		return WahapediaCsvBuilder.getInputStreamFromUrl(STRATAGEM_URL);
	}

}
