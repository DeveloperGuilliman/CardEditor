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
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author DeveloperGuilliman
 */
public class WahapediaStratagemCardBuilder implements IWahapediaCardInput {

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

            List<CardData> list = new ArrayList<>();
            buildFromHtml(doc, list);
            System.out.println("Found " + list.size() + " cards");

            Collections.sort(list, ICardInput.getComparator(reorderByName));

            if (deduplicate) {
                list.retainAll(ICardInput.createListDeduplicator(list));
                System.out.println("Found " + list.size() + " deduplicated cards");

            }
            List<List<CardData>> cardSections = ICardInput.divideSections(list);
            ICardInput.regroupSections(cardSections, maxToGroup);
            System.out.println("Organized in " + cardSections.size() + " sections");
            return cardSections;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void buildFromHtml(Document doc, List<CardData> list) {

        Elements cardElements = doc.select("div.Columns2 div.BreakInsideAvoid.Corner7");
        for (Element cardElement : cardElements) {
            CardData card = buildStratagem(cardElement);
            if (card != null) {
                list.add(card);
            }
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

            return IWahapediaCardInput.createCard(faction, name, description, rules, cost);
        } catch (Exception e) {
            System.err.println("Error " + e + " at element " + cardElement.html());
            return null;
        }
    }

}
