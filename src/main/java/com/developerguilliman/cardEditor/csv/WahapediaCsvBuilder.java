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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.unbescape.html.HtmlEscape;

import com.developerguilliman.cardEditor.Utils;

/**
*
* @author Developer Guilliman <developerguilliman@gmail.com>
*/
public class WahapediaCsvBuilder {
	
	
	private static final Pattern SEPARATOR_PATTERN = Pattern.compile("\\|");
	private static final Pattern LI_TAG_START_PATTERN = Pattern.compile("\\<li.*?\\>");
	private static final Pattern ANY_TAG_PATTERN = Pattern.compile("\\<.*?\\>");

	private final ArrayList<String> header;
	private final ArrayList<ArrayList<String>> data;

	public WahapediaCsvBuilder() {
		this.header = new ArrayList<>();
		this.data = new ArrayList<>();
	}
	
	public WahapediaCsvBuilder build(InputStream is) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
		String readedString = br.readLine();
		if (readedString == null) {
			return this;
		}
		addSplitted(header, readedString);
		readedString = br.readLine();
		while (readedString != null) {
			data.add(addSplitted(new ArrayList<>(), readedString));
			readedString = br.readLine();
		}
		return this;
	}
	
	private static ArrayList<String> addSplitted(ArrayList<String> al, String readedString) {
		for (String s : SEPARATOR_PATTERN.split(readedString, -1)) {
			al.add(s.replaceAll("\uEFBBBF", "").trim());
		}
		return al;
	}
	
	public ArrayList<String> getHeader() {
		return header;
	}

	public ArrayList<ArrayList<String>> getData() {
		return data;
	}
	
	public static InputStream getInputStreamFromUrl(String url) throws IOException {
                System.out.println("Connecting to URL: " + url);
		HttpURLConnection huc = ((HttpURLConnection) new URL(url).openConnection());
		huc.addRequestProperty("User-Agent", Utils.getApplicationName());
		huc.addRequestProperty("Accept", "*/*");
		return huc.getInputStream();
	}

	public static String stripHtml(String str) {
		str = str.trim();
		str =  LI_TAG_START_PATTERN.matcher(str).replaceAll("\n\n• ");
		str =  ANY_TAG_PATTERN.matcher(str).replaceAll("");
		str = HtmlEscape.unescapeHtml(str);
		return str;
	}

}
