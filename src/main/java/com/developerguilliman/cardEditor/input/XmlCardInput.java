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

import com.developerguilliman.cardEditor.Utils;
import com.developerguilliman.cardEditor.data.CardCollectionData;
import com.developerguilliman.cardEditor.data.CardData;
import com.developerguilliman.cardEditor.data.SectionData;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Developer Guilliman <developerguilliman@gmail.com>
 */
public class XmlCardInput implements ICardInput {

    @Override
    public CardCollectionData build(InputStream source) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(source);
            doc.getDocumentElement().normalize();

            NodeList sectionNodes = doc.getElementsByTagName("section");
            int len = sectionNodes.getLength();
            CardCollectionData cardDatas = new CardCollectionData(len);
            for (int i = 0; i < len; i++) {
                Node sectionNode = sectionNodes.item(i);
                if (sectionNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element sectionElement = (Element) sectionNode;
                    cardDatas.add(buildStratagemSection(sectionElement));
                }
            }
            return cardDatas;

        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }

    }

    private SectionData buildStratagemSection(Element sectionElement) {
        NodeList cardNodes = sectionElement.getElementsByTagName("card");
        int len = cardNodes.getLength();
        SectionData cardDatas = new SectionData(len);
        for (int i = 0; i < len; i++) {
            Node cardNode = cardNodes.item(i);
            if (cardNode.getNodeType() == Node.ELEMENT_NODE) {
                Element cardElement = (Element) cardNode;
                cardDatas.add(buildStratagem(cardElement));
            }
        }
        return cardDatas;
    }

    private CardData buildStratagem(Element cardElement) {

        String title = getText(cardElement, "title", "");
        String name = getText(cardElement, "name", "");
        String legend = getText(cardElement, "legend", "");
        String rules = getText(cardElement, "rules", "");
        String costValue = getText(cardElement, "costValue", null);
        String costType = getText(cardElement, "costType", null);
        if (costValue == null && costType == null) {
            String oldCost = getText(cardElement, "cost", null);
            int io = oldCost.indexOf(' ');
            if (io >= 0) {
                costValue = oldCost.substring(0, io);
                costType = oldCost.substring(io + 1, oldCost.length());
            } else {
                costValue = oldCost;
                costType = "";
            }
        } else {
            costValue = Utils.normalize(costValue);
            costType = Utils.normalize(costType);
        }
        return new CardData(title, name, legend, rules, costValue, costType);

    }

    private String getText(Element cardElement, String key, String defaultText) {

        NodeList cardNodes = cardElement.getElementsByTagName(key);
        int len = cardNodes.getLength();
        if (len == 0) {
            return defaultText;
        }
        if (len == 1) {
            return Utils.normalize(cardNodes.item(0).getTextContent());
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            String text = cardNodes.item(i).getTextContent();
            if (text != null) {
                sb.append(text);
            }
        }
        return sb.toString();

    }

}
