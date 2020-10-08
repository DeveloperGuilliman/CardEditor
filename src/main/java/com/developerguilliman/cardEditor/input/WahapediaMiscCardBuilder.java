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
public class WahapediaMiscCardBuilder implements IWahapediaCardInput {

    private final int maxToGroup;
    private final boolean reorderByName;
    private final boolean deduplicate;

    public WahapediaMiscCardBuilder(int maxToGroup, boolean reorderByName, boolean deduplicate) {

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

        for (Element cardElement : doc.select("div.Columns2 div.BreakInsideAvoid p.impact18,div.Columns2 div.BreakInsideAvoid h3")) {
            try {
                CardData card = buildRandomCard(cardElement);
                if (card != null) {
                    list.add(card);
                }
            } catch (Exception e) {
                System.err.println("Error at element " + cardElement.html());
                e.printStackTrace();
            }
        }
        for (Element tableElement : doc.select("div.Columns2 tr.tableColumns3")) {
            String firstCellType = tableElement.select("td").first().text();
            String lastCellType = tableElement.select("td").last().text();

            for (Element cardElement : tableElement.nextElementSiblings()) {
                try {
                    list.add(buildFactionCard(cardElement, firstCellType, lastCellType));
                } catch (Exception e) {
                    System.err.println("Error at element " + cardElement.html());
                    e.printStackTrace();
                }
            }
        }

    }

    private CardData buildRandomCard(Element cardElement) {

        Elements warlordElements = warlordElements(cardElement);
        if (warlordElements.size() < 2) {
            return null;
        }
        String type = warlordElements.get(0).html();
        if (!"D6".equalsIgnoreCase(type) && !"D3".equalsIgnoreCase(type)) {
            return null;
        }

        Elements nextElements = nextElements(cardElement);
        if (nextElements.isEmpty()) {
            return null;
        }

        String title = inferTitle(cardElement.parents().select("div.Columns2").first());
//        String tlc = title.toLowerCase();
//        if (tlc.equals("abilities") || tlc.endsWith("detachment rules") || tlc.endsWith("chapter tactics") || tlc.endsWith("doctrines")) {
//            return null;
//        }
//        if (!title.endsWith("Traits") && !title.endsWith("Powers") && titleElement.tagName().equalsIgnoreCase("h3")) {
//            Element parent = titleElement.parent();
//            Element parentParent = parent.parent();
//            if (parentParent.is(".BreakInsideAvoid")) {
//                parent = parentParent;
//            }
//            Element postElementTitle = parent.previousElementSibling();
//            while (postElementTitle != null && !postElementTitle.tag().equals("h3") && !postElementTitle.tag().equals("h2")) {
//                postElementTitle = postElementTitle.previousElementSibling();
//            }
//            if (postElementTitle != null) {
//                title = title + " " + postElementTitle.ownText();
//            }
//        }

        String name = cardElement.text();
        int ioName = name.indexOf(':');
        if (ioName > 0) {
            String preName = name.substring(0, ioName).trim();
            name = name.substring(ioName + 1).trim();
            title = titleCase(preName + " " + title);
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

    }

    private Elements warlordElements(Element cardElement) {
        return cardElement.parents().select("div.Columns2").select("td.impact18");
    }

    private Elements nextElements(Element cardElement) {
        return cardElement.nextElementSiblings().select("p");
    }

    private CardData buildFactionCard(Element cardElement, String firstCellType, String lastCellType) {

        String title = inferTitle(cardElement.parents().select("table").first());

        Elements cellElements = cardElement.select("td");
        Element lastElement = cellElements.last();
        String first = cellElements.first().text();
        String last = lastElement.text();
        String bold = lastElement.select("b").text();
        String italic = lastElement.select("i").text();

        String name = bold.endsWith(":") ? bold.substring(0, bold.length() - 1) : bold;

//
//        if (lastCellType.toUpperCase().endsWith("TRAIT")) {
//            title = first.concat(" Warlord Trait");
//        } else
//        if (firstCellType.equals("D6") || firstCellType.equals("D3")) {
//            title = lastCellType;
//        } else
        if (name.isEmpty()) {
            name = first;
        } else if (!firstCellType.equals("D6") && !firstCellType.equals("D3")) {
            title = first + " " + title;
        }
        title = titleCase(title);

        String legend = italic;
        String rules = last.substring(last.indexOf(italic) + italic.length());
        String cost = "";
        return IWahapediaCardInput.createCard(title, name, legend, rules, cost);
    }

    private String titleCase(String title) {
        int len = title.length();
        char[] charBuffer = new char[len];
        boolean upperNextChar = true;
        for (int i = 0; i < len; i++) {
            char c = title.charAt(i);
            if (c == ' ') {
                upperNextChar = true;
            } else if (upperNextChar) {
                c = Character.toUpperCase(c);
                upperNextChar = false;
            } else {
                c = Character.toLowerCase(c);
            }
            charBuffer[i] = c;
        }
        return new String(charBuffer);
    }

    private static String inferTitle(Element element) {
        Element h2 = null;
        Element h3 = null;
        int max = 1;
        while (element != null) {

            Element postElementTitle = element.previousElementSibling();

            while (postElementTitle != null) {
                if (h3 == null && postElementTitle.tagName().equals("h3")) {
                    h3 = postElementTitle;
                    if (h2 != null) {
                        return joinTitle(getTrimText(h2), getTrimText(h3));
                    }
                }
                if (h2 == null && postElementTitle.tagName().equals("h2")) {
                    h2 = postElementTitle;
                    if (h3 != null) {
                        return joinTitle(getTrimText(h2), getTrimText(h3));
                    }
                }
                postElementTitle = postElementTitle.previousElementSibling();
            }
            element = element.parent();
            if (max == 0) {
                break;
            } else {
                max--;
            }
        }
        return joinTitle(getTrimText(h2), getTrimText(h3));
    }

    private static String joinTitle(String first, String last) {

        String title;
        if (first.endsWith(last)) {
            title = first;
        } else if (last.endsWith(first)) {
            title = last;
        } else {
            title = (first + " " + last).trim();
        }

        if (title.endsWith("Psychic Powers")) {
            title = title.substring(0, title.length() - "Psychic Powers".length()).concat("Discipline");
        } else if (title.endsWith("Traits")) {
            title = title.substring(0, title.length() - 1);
        }
        return title;
    }

    private static String getTrimText(Element element) {
        if (element != null) {
            return element.ownText().trim();
        }
        return "";
    }

}
