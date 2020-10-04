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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.developerguilliman.cardEditor.data.CardData;

/**
 *
 * @author DeveloperGuilliman
 */
public class WahapediaStratagemCardBuilder implements ICardInput {

	private final int maxToGroup;
	private final boolean reorderByName;
	private final boolean deduplicate;

	public WahapediaStratagemCardBuilder(int maxToGroup, boolean reorderByName, boolean deduplicate) {

		this.maxToGroup = maxToGroup;
		this.reorderByName = reorderByName;
		this.deduplicate = deduplicate;
	}

	@Override
	public List<List<CardData>> build(InputStream source) {
		try {
			Document doc = Jsoup.parse(source, null, "");
			Elements cardElements = doc.select("div.Columns2 div.BreakInsideAvoid.Corner7");

			List<CardData> list = new ArrayList<>();
			for (Element cardElement : cardElements) {
				CardData card = buildStratagem(cardElement);
				if (card != null) {
					list.add(card);
				}
			}
			System.out.println("Found " + list.size() + " cards");

			Collections.sort(list, getComparator());

			if (deduplicate) {

				TreeSet<CardData> deduplicator = new TreeSet<CardData>(
						Comparator.comparing(CardData::getTitle).thenComparing(CardData::getName)
								.thenComparing(CardData::getLegend).thenComparing(CardData::getRules)
								.thenComparing(CardData::getCost));

				deduplicator.addAll(list);
				list.retainAll(deduplicator);
				System.out.println("Found " + list.size() + " deduplicated cards");

			}
			List<List<CardData>> cardSections = divideSections(list);
			regroupSections(cardSections);
			System.out.println("Organized in " + cardSections.size() + " sections");
			return cardSections;
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	private Comparator<? super CardData> getComparator() {
		Comparator<CardData> comparatorBase = Comparator.comparing(CardData::getTitle);
		return reorderByName ? comparatorBase.thenComparing(CardData::getName) : comparatorBase;
	}

	private List<List<CardData>> divideSections(Collection<CardData> cards) {

		List<List<CardData>> cardSections = new ArrayList<>();
		String lastFaction = null;
		ArrayList<CardData> lastStratagemSection = null;

		for (CardData card : cards) {
			String currentFaction = card.getTitle();
			if (!currentFaction.equals(lastFaction)) {
				lastFaction = currentFaction;
				lastStratagemSection = new ArrayList<CardData>();
				cardSections.add(lastStratagemSection);
			}
			lastStratagemSection.add(card);
		}

		return cardSections;
	}

	private void regroupSections(List<List<CardData>> cardSections) {
		List<CardData> singletons = null;
		Iterator<List<CardData>> compactsingletons = cardSections.iterator();
		while (compactsingletons.hasNext()) {
			List<CardData> cardSection = compactsingletons.next();
			if (cardSection.size() <= maxToGroup) {
				if (singletons == null) {
					singletons = new ArrayList<>();
				}
				singletons.addAll(cardSection);
				compactsingletons.remove();
			}
		}
		if (singletons != null) {
			cardSections.add(singletons);
		}
	}

	private CardData buildStratagem(Element cardElement) {

		try {
			String lastText = "";

			Elements nameElement = cardElement.select("p.stratName");
			String name = nameElement.text();
			if (!name.isEmpty()) {
				lastText = name;
			}

			String faction = cardElement.select("p.stratFaction").text();
			if (!faction.isEmpty()) {
				lastText = faction;
			}

			String description = cardElement.select("p.stratLegend").text();
			if (!description.isEmpty()) {
				lastText = description;
			}

			Element rulesElement = nameElement.parents().first();
			String rules = rulesElement.text();
			int io = rules.indexOf(lastText) + lastText.length();
			rules = rules.substring(io);

			Elements siblings = cardElement.siblingElements();
			String cost = siblings.select("div.stratPts").text();

			return new CardData(faction, name, description, rules, cost);
		} catch (Exception e) {
			System.err.println("Error " + e + " at element " + cardElement.html());
			return null;
		}
	}

}
