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
import java.util.Collections;
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
public class WahapediaAbilities implements ICardInput {

	private static final String STRATAGEM_URL = "https://wahapedia.ru/wh40k9ed/Abilities.csv";

	@SuppressWarnings("unused")
	private static final int ID_ROW = 0;
	
	private static final int TYPE_ROW = 1;
	
	private static final int NAME_ROW = 2;
	
	private static final int LEGEND_ROW = 3;
	
	@SuppressWarnings("unused")
	private static final int WARGEAR_ROW = 4;

	private static final int FACTION_ROW = 5;
	
	private static final int DESCRIPTION_ROW = 6;

	private final LinkedHashMap<String, Faction> factions;
	
	private final int maxToGroup;

	private final boolean reorderByName;

	private final boolean deduplicate;

	public WahapediaAbilities(LinkedHashMap<String, Faction> selectedFactions, int maxToGroup, boolean reorderByName, boolean deduplicate) {
		this.factions = selectedFactions;
        this.maxToGroup = maxToGroup;
        this.reorderByName = reorderByName;
        this.deduplicate = deduplicate;
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
				String name = row.get(NAME_ROW).trim().toUpperCase();
				String type = row.get(TYPE_ROW).trim().toUpperCase();
				String legend = row.get(LEGEND_ROW).trim();
				
				if (type.isEmpty() && legend.isEmpty()) {
					continue;
				}
				
				String description = WahapediaCsvBuilder.stripHtml(row.get(DESCRIPTION_ROW));
				SectionData sectionData = sections.get(type);
				if (sectionData == null) {
					sectionData = new SectionData();
					sections.put(type, sectionData);
				}
				sectionData.add(new CardData(type, name, legend, description, "", ""));
			}
            
            CardCollectionData cardSections = new CardCollectionData();            
            
            for (SectionData list: sections.values()) {
            	Collections.sort(list, ICardInput.getComparator(reorderByName));
                list = ICardInput.createListDeduplicator(list);
                cardSections.add(list);
            }
            
            System.out.println("Found " + cardSections.countCards() + " ability cards");


            if (deduplicate) {
                System.out.println("Found " + cardSections.countCards() + " deduplicated ability cards");
            }
            
			ICardInput.regroupSections(cardSections, maxToGroup);
            System.out.println("Organized in " + cardSections.size() + " sections");
            return cardSections;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static InputStream getInputStreamFromUrl() throws IOException {
		return WahapediaCsvBuilder.getInputStreamFromUrl(STRATAGEM_URL);
	}

}
