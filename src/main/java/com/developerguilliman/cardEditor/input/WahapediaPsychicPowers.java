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
public class WahapediaPsychicPowers implements ICardInput {

	private static final String PSYCHIC_POWERS_URL = "https://wahapedia.ru/wh40k9ed/PsychicPowers.csv";

	private static final String PSYCHIC_POWER_FIND_FIRST = "has a warp charge ";

	private static final String PSYCHIC_POWER_FIND_SECOND = " of ";

	@SuppressWarnings("unused")
	private static final String ROLL_ROW = "roll";

	private static final String NAME_ROW = "name";

	private static final String FACTION_ROW = "faction_id";

	@SuppressWarnings("unused")
	private static final String FACTION_NAME_ROW = "faction_name";

	private static final String LEGEND_ROW = "legend";

	private static final String TYPE_ROW = "type";

	private static final String DESCRIPTION_ROW = "description";

	@SuppressWarnings("unused")
	private static final String ID_ROW = "id";

	private final LinkedHashMap<String, Faction> factions;

	private final int maxToGroup;

	private final boolean reorderByName;

	private final boolean deduplicate;

	public WahapediaPsychicPowers(LinkedHashMap<String, Faction> selectedFactions, int maxToGroup,
			boolean reorderByName, boolean deduplicate) {
		this.factions = selectedFactions;
		this.maxToGroup = maxToGroup;
		this.reorderByName = reorderByName;
		this.deduplicate = deduplicate;
	}

	@Override
	public CardCollectionData build(InputStream source) {
		try {

			WahapediaCsvBuilder wahapediaCsvBuilder = new WahapediaCsvBuilder().build(source);
			ArrayList<String> header = wahapediaCsvBuilder.getHeader();

			// int rollRow = header.indexOf(ROLL_ROW);
			int nameRow = header.indexOf(NAME_ROW);
			int factionRow = header.indexOf(FACTION_ROW);
			// int factionNameRow = header.indexOf(FACTION_NAME_ROW);
			int legendRow = header.indexOf(LEGEND_ROW);
			int typeRow = header.indexOf(TYPE_ROW);
			int descriptionRow = header.indexOf(DESCRIPTION_ROW);

			TreeMap<String, SectionData> sections = new TreeMap<>();

			for (ArrayList<String> row : wahapediaCsvBuilder.getData()) {
				String faction = safeGet(row, factionRow);
				if (!factions.containsKey(faction)) {
					continue;
				}
				String name = safeGet(row, nameRow).toUpperCase();
				String type = safeGet(row, typeRow).toUpperCase();
				String legend = safeGet(row, legendRow);
				String description = WahapediaCsvBuilder.stripHtml(safeGet(row, descriptionRow));
				String cost = extractPsychicPowerCost(description);

				SectionData sectionData = sections.get(type);
				if (sectionData == null) {
					sectionData = new SectionData();
					sections.put(type, sectionData);
				}
				sectionData.add(new CardData(type, name, legend, description, cost, "WARP CHARGE"));
			}
			CardCollectionData cardSections = new CardCollectionData();

			for (SectionData list : sections.values()) {
				Collections.sort(list, ICardInput.getComparator(reorderByName));
				list = ICardInput.createListDeduplicator(list);
				cardSections.add(list);
			}

			System.out.println("Found " + cardSections.countCards() + " psychic power cards");

			if (deduplicate) {
				System.out.println("Found " + cardSections.countCards() + " deduplicated psychic power cards");
			}

			ICardInput.regroupSections(cardSections, maxToGroup);
			System.out.println("Organized in " + cardSections.size() + " sections");
			return cardSections;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static String extractPsychicPowerCost(String desc) {
		int prefixIndexOf = desc.indexOf(PSYCHIC_POWER_FIND_FIRST);
		if (prefixIndexOf >= 0) {
			prefixIndexOf = desc.indexOf(PSYCHIC_POWER_FIND_SECOND, prefixIndexOf);
			if (prefixIndexOf >= 0) {
				prefixIndexOf += PSYCHIC_POWER_FIND_SECOND.length();
				int endIndexOf = desc.indexOf('.', prefixIndexOf);
				if (endIndexOf > prefixIndexOf) {
					return desc.substring(prefixIndexOf, endIndexOf);
				}
			}
		}
		return "";
	}

	public static InputStream getInputStreamFromUrl() throws IOException {
		return WahapediaCsvBuilder.getInputStreamFromUrl(PSYCHIC_POWERS_URL);
	}

	private static String safeGet(ArrayList<String> row, int index) {
		if (index < row.size()) {
			return row.get(index);
		}
		return "";
	}

}
