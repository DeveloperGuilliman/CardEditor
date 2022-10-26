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
package com.developerguilliman.cardEditor.csv;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.developerguilliman.cardEditor.data.Faction;

/**
 *
 * @author Developer Guilliman <developerguilliman@gmail.com>
 */
public class WahapediaCsvFactions {

	private static final String FACTIONS_URL = "https://wahapedia.ru/wh40k9ed/Factions.csv";

	private static final String ID_ROW = "﻿id";

	private static final String NAME_ROW = "name";

	private static final String LINK_ROW = "link";

	private static final String IS_SUBFACTION_ROW = "is_subfaction";

	private static final String PARENT_FACTION_ID_ROW = "parent_id";

	private final LinkedHashMap<String, Faction> data;

	public WahapediaCsvFactions() {
		this.data = new LinkedHashMap<>();
	}

	public WahapediaCsvFactions build(InputStream source) throws IOException {
		WahapediaCsvBuilder wahapediaCsvBuilder = new WahapediaCsvBuilder().build(source);
		ArrayList<String> headers = wahapediaCsvBuilder.getHeader();
		
		HashMap<String, Faction> factions = new HashMap<>();

		
		int idRow = headers.indexOf(ID_ROW);
		int nameRow = headers.indexOf(NAME_ROW);
		int linkRow = headers.indexOf(LINK_ROW);
		int isSubfactionRow = headers.indexOf(IS_SUBFACTION_ROW);
		int parentFactionId = headers.indexOf(PARENT_FACTION_ID_ROW);
		for (ArrayList<String> row : wahapediaCsvBuilder.getData()) {
			Faction faction = new Faction();
			faction.setId(row.get(idRow));
			faction.setName(row.get(nameRow));
			faction.setLink(row.get(linkRow));
			if (Boolean.parseBoolean(row.get(isSubfactionRow))) {
				faction.setParentId(row.get(parentFactionId));
			}
			factions.put(faction.getId(), faction);
		}
		ArrayList<Faction> orderedFactions = new ArrayList<>();
		for (Faction f : factions.values()) {
			String parentId = f.getParentId();
			if (parentId != null) {
				f.setFullName(factions.get(parentId).getName() + " - " + f.getName());
			} else {
				f.setFullName(f.getName());
			}
			orderedFactions.add(f);
		}
		Collections.sort(orderedFactions, new Comparator<Faction>() {

			@Override
			public int compare(Faction o1, Faction o2) {
				return o1.getFullName().compareTo(o2.getFullName());
			}
		});
		for (Faction f : orderedFactions) {
			data.put(f.getId(), f);
		}
		return this;
	}

	public LinkedHashMap<String, Faction> getData() {
		return data;
	}

	public static InputStream getInputStreamFromUrl() throws IOException {
		return WahapediaCsvBuilder.getInputStreamFromUrl(FACTIONS_URL);
	}

}
