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

import com.developerguilliman.cardEditor.data.CardData;
import com.developerguilliman.cardEditor.data.SectionData;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;
import org.jsoup.select.QueryParser;

/**
 *
 * @author Developer Guilliman <developerguilliman@gmail.com>
 */
interface IWahapediaCardInput extends ICardInput {
    
    public static final Evaluator UL_EVALUATOR = QueryParser.parse("ul");
    public static final Evaluator LI_EVALUATOR = QueryParser.parse("li");

    public static CardData createCard(String title, String name, String legend, String rules, String costValue, String costType) {
        return new CardData(title.trim().toUpperCase(), name.trim().toUpperCase(), legend.trim(), rules.trim(), costValue.trim(), costType.trim());
    }

    public void buildFromHtml(Document doc, SectionData list);

    public static String createRules(Elements els) {
        StringBuilder sb = new StringBuilder();
        for (Element el : els) {
            sb.append(createRules(el));
        }
        return sb.toString();
    }

    public static String createRules(Element el) {
        Elements ulElements = el.select(UL_EVALUATOR);
        if (ulElements.isEmpty()) {
            return el.wholeText();
        }
        String text = el.wholeText();
        StringBuilder sb = new StringBuilder();
        for (Element ulElement : ulElements) {
            sb.setLength(0);
            Elements liElements = ulElement.select(LI_EVALUATOR);
            for (Element liElement : liElements) {
                sb.append("\n\n• ");
                sb.append(liElement.wholeText());
            }
            text = text.replace(ulElement.wholeText(), sb);
        }
        return text.trim();
    }

}
