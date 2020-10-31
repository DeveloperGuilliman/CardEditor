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
package com.developerguilliman.cardEditor.output;

import com.developerguilliman.cardEditor.data.CardCollectionData;
import com.developerguilliman.cardEditor.data.CardData;
import com.developerguilliman.cardEditor.data.SectionData;
import java.io.IOException;
import java.io.OutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import com.developerguilliman.cardEditor.warning.IWarningHandler;

public class XmlCardOutput implements ICardOutput {

    @Override
    public void build(OutputStream out, CardCollectionData cards, IWarningHandler warnings) throws IOException {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            Element root = doc.createElement("cards");
            doc.appendChild(root);
            for (SectionData section : cards) {

                Element sectionElement = doc.createElement("section");
                root.appendChild(sectionElement);
                for (CardData card : section) {
                    Element cardElement = doc.createElement("card");
                    sectionElement.appendChild(cardElement);
                    create(doc, cardElement, "name", card.getName());
                    create(doc, cardElement, "title", card.getTitle());
                    create(doc, cardElement, "legend", card.getLegend());
                    create(doc, cardElement, "rules", card.getRules());
                    create(doc, cardElement, "cost", card.getCost());

                }
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource domSource = new DOMSource(doc);
            StreamResult streamResult = new StreamResult(out);
            transformer.transform(domSource, streamResult);

        } catch (ParserConfigurationException | TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    private void create(Document doc, Element element, String key, String value) {
        Element child = doc.createElement(key);
        Text text = doc.createTextNode(key);
        text.setData(value);
        child.appendChild(text);
        element.appendChild(child);
    }

}
