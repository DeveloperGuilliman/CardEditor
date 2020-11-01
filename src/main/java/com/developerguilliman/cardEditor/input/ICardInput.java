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
import java.io.InputStream;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

/**
 *
 * @author Developer Guilliman <developerguilliman@gmail.com>
 */
public interface ICardInput {

    CardCollectionData build(InputStream source);

    public static CardCollectionData divideSections(Collection<CardData> cards) {

        CardCollectionData cardSections = new CardCollectionData();
        String lastFaction = null;
        SectionData lastCardSection = null;

        for (CardData card : cards) {
            String currentFaction = card.getTitle();
            if (!currentFaction.equals(lastFaction)) {
                lastFaction = currentFaction;
                lastCardSection = new SectionData();
                cardSections.add(lastCardSection);
            }
            lastCardSection.add(card);
        }

        return cardSections;
    }

    public static void regroupSections(CardCollectionData cardSections, int maxToGroup) {
        SectionData singletons = null;
        Iterator<SectionData> compactsingletons = cardSections.iterator();
        while (compactsingletons.hasNext()) {
            SectionData cardSection = compactsingletons.next();
            if (cardSection.size() <= maxToGroup) {
                if (singletons == null) {
                    singletons = new SectionData();
                }
                singletons.addAll(cardSection);
                compactsingletons.remove();
            }
        }
        if (singletons != null) {
            cardSections.add(singletons);
        }
    }

    public static Comparator<? super CardData> getComparator(boolean reorderByName) {
        Comparator<CardData> comparatorBase = Comparator.comparing(CardData::getTitle);
        return reorderByName ? comparatorBase.thenComparing(CardData::getName) : comparatorBase;
    }

    public static TreeSet<CardData> createListDeduplicator(SectionData list) {
        TreeSet<CardData> dedup = new TreeSet<>(
                Comparator.comparing(CardData::getTitle)
                        .thenComparing(CardData::getName)
                        .thenComparing(CardData::getLegend)
                        .thenComparing(CardData::getRules)
                        .thenComparing(CardData::getCost)
        );
        dedup.addAll(list);
        return dedup;
    }

    public static TreeSet<CardData> createSectionsDeduplicator(CardCollectionData list) {
        TreeSet<CardData> dedup = new TreeSet<>(
                Comparator.comparing(CardData::getTitle)
                        .thenComparing(CardData::getName)
                        .thenComparing(CardData::getLegend)
                        .thenComparing(CardData::getRules)
                        .thenComparing(CardData::getCost)
        );
        for (SectionData l : list) {
            dedup.addAll(l);
        }
        return dedup;
    }

}
