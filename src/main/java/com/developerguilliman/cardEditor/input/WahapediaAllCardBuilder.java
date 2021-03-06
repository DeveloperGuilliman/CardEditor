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
import java.util.Comparator;
import java.util.TreeSet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author Developer Guilliman <developerguilliman@gmail.com>
 */
public class WahapediaAllCardBuilder implements ICardInput {

    private final int maxToGroup;
    private final boolean reorderByName;
    private final boolean deduplicate;
    private final IWahapediaCardInput[] wahapediaBuilders;

    public WahapediaAllCardBuilder(int maxToGroup, boolean reorderByName, boolean deduplicate) {

        this.maxToGroup = maxToGroup;
        this.reorderByName = reorderByName;
        this.deduplicate = deduplicate;
        this.wahapediaBuilders = new IWahapediaCardInput[]{
            new WahapediaStratagemCardBuilder(maxToGroup, reorderByName, deduplicate),
            new WahapediaMiscCardBuilder(maxToGroup, reorderByName, deduplicate),
        };
    }

    @Override
    public CardCollectionData build(InputStream source) {
        try {
            Document doc = Jsoup.parse(source, null, "");

            SectionData list = new SectionData();
            for (IWahapediaCardInput builder : wahapediaBuilders) {
                builder.buildFromHtml(doc, list);
            }
            System.out.println("Found " + list.size() + " cards");

            Collections.sort(list, ICardInput.getComparator(reorderByName));

            if (deduplicate) {
                list = ICardInput.createListDeduplicator(list);
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

}
