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
import java.util.LinkedHashMap;

import com.developerguilliman.cardEditor.data.Faction;

/**
*
* @author Developer Guilliman <developerguilliman@gmail.com>
*/
public class WahapediaCsvFactions {

	private static final String FACTIONS_URL = "https://wahapedia.ru/wh40k9ed/Factions.csv";

	private static final int ID_ROW = 0;

	private static final int NAME_ROW = 1;

	private static final int LINK_ROW = 2;

	private final LinkedHashMap<String, Faction> data;

	public WahapediaCsvFactions() {
		this.data = new LinkedHashMap<>();
	}
	
	public WahapediaCsvFactions build(InputStream source) throws IOException {
		ArrayList<ArrayList<String>> csvData = new WahapediaCsvBuilder().build(source).getData();
		for (ArrayList<String> row : csvData) {
			Faction faction = new Faction();
			faction.setId(row.get(ID_ROW));
			faction.setName(row.get(NAME_ROW));
			faction.setLink(row.get(LINK_ROW));
			data.put(faction.getId(), faction);
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
