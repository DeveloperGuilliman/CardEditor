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

import com.developerguilliman.cardEditor.data.CardCollectionData;
import com.developerguilliman.cardEditor.data.CardData;
import com.developerguilliman.cardEditor.data.SectionData;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;
import org.jsoup.select.QueryParser;

/**
 *
 * @author Developer Guilliman <developerguilliman@gmail.com>
 */
public class WahapediaStratagemCardBuilder implements IWahapediaCardInput {

    private static final Evaluator P_STRATNAME_EVALUATOR = QueryParser.parse("p.stratName");
    private static final Evaluator P_STRATFACTION_EVALUATOR = QueryParser.parse("p.stratFaction");
    private static final Evaluator P_STRATLEGEND_EVALUATOR = QueryParser.parse("p.stratLegend");

    
    private static final Evaluator STRATNAME9K_SPAN_EVALUATOR = QueryParser.parse(".stratName_9k span");
    private static final Evaluator STRATFACTIONCS_EVALUATOR = QueryParser.parse(".stratFaction_CS");
    private static final Evaluator SHOWFLUFF_EVALUATOR = QueryParser.parse(".ShowFluff");
    private static final Evaluator STRATTEXT_EVALUATOR = QueryParser.parse(".stratText_CS");

    private final int maxToGroup;
    private final boolean reorderByName;
    private final boolean deduplicate;

    public WahapediaStratagemCardBuilder(int maxToGroup, boolean reorderByName, boolean deduplicate) {

        this.maxToGroup = maxToGroup;
        this.reorderByName = reorderByName;
        this.deduplicate = deduplicate;
    }

    @Override
    public CardCollectionData build(InputStream source) {
        try {
            Document doc = Jsoup.parse(source, null, "");

            SectionData list = new SectionData();
            buildFromHtml(doc, list);
            System.out.println("Found " + list.size() + " cards");

            Collections.sort(list, ICardInput.getComparator(reorderByName));

            if (deduplicate) {
                list.retainAll(ICardInput.createListDeduplicator(list));
                System.out.println("Found " + list.size() + " deduplicated cards");

            }
            CardCollectionData cardSections = ICardInput.divideSections(list);
            ICardInput.regroupSections(cardSections, maxToGroup);
            System.out.println("Organized in " + cardSections.size() + " sections");
            return cardSections;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void buildFromHtml(Document doc, SectionData list) {
        buildFromHtml8thStyle(doc, list);
        buildFromHtml9thStyle(doc, list);
    }

    public void buildFromHtml8thStyle(Document doc, SectionData list) {

        Elements cardElements = doc.select("div.Columns2 div.BreakInsideAvoid.Corner7");
        for (Element cardElement : cardElements) {
            CardData card = buildStratagem8thStyle(cardElement);
            if (card != null) {
                list.add(card);
            }
        }
    }

    private CardData buildStratagem8thStyle(Element cardElement) {

        try {
            String lastText = "";

            Elements nameElement = cardElement.select(P_STRATNAME_EVALUATOR);
            String name = nameElement.text();
            if (!name.isEmpty()) {
                lastText = name;
            }

            String faction = cardElement.select(P_STRATFACTION_EVALUATOR).text();
            if (!faction.isEmpty()) {
                lastText = faction;
            }

            String description = cardElement.select(P_STRATLEGEND_EVALUATOR).text();
            if (!description.isEmpty()) {
                lastText = description;
            }

            Element rulesElement = nameElement.parents().first();
            String rules = IWahapediaCardInput.createRules(rulesElement);
            int io = rules.indexOf(lastText) + lastText.length();
            rules = rules.substring(io);

            Elements siblings = cardElement.siblingElements();
            String costValue = siblings.select("div.stratPts").text();

            String alternateCost = costValue.replace("CP", "");
            String costType = "";
            if (!alternateCost.equals(costValue)) {
                costValue = alternateCost.trim();
                costType = "COMMAND POINTS";
            }
            return IWahapediaCardInput.createCard(faction, name, description, rules, costValue, costType);
        } catch (Exception e) {
            System.err.println("Error " + e + " at element " + cardElement.html());
            return null;
        }
    }

    public void buildFromHtml9thStyle(Document doc, SectionData list) {

        Elements cardElements = doc.select("div.stratWrapper_CS");
        for (Element cardElement : cardElements) {
            CardData card = buildStratagem9thStyle(cardElement);
            if (card != null) {
                list.add(card);
            }
        }
    }

    private CardData buildStratagem9thStyle(Element cardElement) {

        try {

            Elements nameSpanElements = cardElement.select(STRATNAME9K_SPAN_EVALUATOR);

            if (nameSpanElements.isEmpty()) {
                return null;
            }
            String name = nameSpanElements.get(0).text();

            String costValue = nameSpanElements.get(1).text().replace("CP", "");

            String costType = "COMMAND POINTS";

            String faction = cardElement.select(STRATFACTIONCS_EVALUATOR).text().replace(" – ", " ").replace(" - ", " ");

            String description = cardElement.select(SHOWFLUFF_EVALUATOR).text();

            String rules = IWahapediaCardInput.createRules(cardElement.select(STRATTEXT_EVALUATOR));

            return IWahapediaCardInput.createCard(faction, name, description, rules, costValue, costType);
        } catch (Exception e) {
            System.err.println("Error " + e + " at element " + cardElement.html());
            return null;
        }
    }

}
