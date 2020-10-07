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
public class WahapediaPsychicPowerCardBuilder implements IWahapediaCardInput {

    private final int maxToGroup;
    private final boolean reorderByName;
    private final boolean deduplicate;

    public WahapediaPsychicPowerCardBuilder(int maxToGroup, boolean reorderByName, boolean deduplicate) {

        this.maxToGroup = maxToGroup;
        this.reorderByName = reorderByName;
        this.deduplicate = deduplicate;
    }

    @Override
    public List<List<CardData>> build(InputStream source) {
        try {
            Document doc = Jsoup.parse(source, null, "");
            //Elements cardElements = doc.select("div.Columns2 div.BreakInsideAvoid p.impact18, div.Columns2 div.BreakInsideAvoid>h3");
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
        Elements cardElements = doc.select("div.Columns2 div.BreakInsideAvoid p.impact18");

        for (Element cardElement : cardElements) {
            CardData card = buildTrait(cardElement);
            if (card != null) {
                list.add(card);
            }
        }
    }

    private CardData buildTrait(Element cardElement) {

        try {
            Elements warlordElements = warlordElements(cardElement);
            if (warlordElements.size() < 2) {
                return null;
            }
            String type = warlordElements.get(1).html();
            if (!"PSYCHIC POWER".equalsIgnoreCase(type)) {
                return null;
            }

            Elements nextElements = nextElements(cardElement);
            if (nextElements.isEmpty()) {
                return null;
            }

            Element titleElement = titleElement(cardElement);
            String title = titleElement.ownText();

            String name = cardElement.text();

            int ioName = name.indexOf(':');
            if (ioName > 0) {
                String preName = name.substring(0, ioName).trim();
                name = name.substring(ioName + 1).trim();
                title = preName + " " + title;
            }

            String description = nextElements.first().text();

            String rules;
            if (nextElements.size() == 2) {
                rules = nextElements.last().text();
            } else {
                rules = cardElement.parent().text();
                int ioRules = rules.indexOf(description) + description.length();
                rules = rules.substring(ioRules);
            }
            String cost = "";

            return IWahapediaCardInput.createCard(title, name, description, rules, cost);
        } catch (Exception e) {
            System.err.println("Error at element " + cardElement.html());
            e.printStackTrace();
            return null;
        }
    }

    private Elements warlordElements(Element cardElement) {
        return cardElement.parents().select("div.Columns2").select("td.impact18");
    }

    private Elements nextElements(Element cardElement) {
        return cardElement.nextElementSiblings().select("p");
    }

    private Element titleElement(Element cardElement) {
        return cardElement.parents().select("div.Columns2").first().previousElementSiblings().select("h2.outline_header").first();
    }

}
