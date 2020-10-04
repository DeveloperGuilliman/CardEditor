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
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.developerguilliman.cardEditor.data.CardData;

/**
 *
 * @author DeveloperGuilliman
 */
public class XmlCardInput implements ICardInput {

	@Override
	public List<List<CardData>> build(InputStream source) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(source);
			doc.getDocumentElement().normalize();

			NodeList sectionNodes = doc.getElementsByTagName("section");
			int len = sectionNodes.getLength();
			List<List<CardData>> cardDatas = new ArrayList<>(len);
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

	private List<CardData> buildStratagemSection(Element sectionElement) {
		NodeList cardNodes = sectionElement.getElementsByTagName("card");
		int len = cardNodes.getLength();
		ArrayList<CardData> cardDatas = new ArrayList<>(len);
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

		String title = normalize(getText(cardElement, "title"));
		String name = normalize(getText(cardElement, "name"));
		String legend = normalize(getText(cardElement, "legend"));
		String rules = normalize(getText(cardElement, "rules"));
		String cost = normalize(getText(cardElement, "cost"));
		return new CardData(title, name, legend, rules, cost);

	}

	private String getText(Element cardElement, String key) {

		NodeList cardNodes = cardElement.getElementsByTagName(key);
		int len = cardNodes.getLength();
		if (len == 0) {
			return "";
		}
		if (len == 1) {
			return cardNodes.item(0).getTextContent();
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			sb.append(cardNodes.item(i).getTextContent());
		}
		return sb.toString();

	}

	private String normalize(String str) {
		return (str != null) ? str : "";
	}

}
