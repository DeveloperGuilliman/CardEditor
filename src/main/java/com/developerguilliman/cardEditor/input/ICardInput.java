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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

/**
 *
 * @author DeveloperGuilliman
 */
public interface ICardInput {

    List<List<CardData>> build(InputStream source);

    public static List<List<CardData>> divideSections(Collection<CardData> cards) {

        List<List<CardData>> cardSections = new ArrayList<>();
        String lastFaction = null;
        ArrayList<CardData> lastCardSection = null;

        for (CardData card : cards) {
            String currentFaction = card.getTitle();
            if (!currentFaction.equals(lastFaction)) {
                lastFaction = currentFaction;
                lastCardSection = new ArrayList<>();
                cardSections.add(lastCardSection);
            }
            lastCardSection.add(card);
        }

        return cardSections;
    }

    public static void regroupSections(List<List<CardData>> cardSections, int maxToGroup) {
        List<CardData> singletons = null;
        Iterator<List<CardData>> compactsingletons = cardSections.iterator();
        while (compactsingletons.hasNext()) {
            List<CardData> cardSection = compactsingletons.next();
            if (cardSection.size() <= maxToGroup) {
                if (singletons == null) {
                    singletons = new ArrayList<>();
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

    public static TreeSet<CardData> createListDeduplicator(List<CardData> list) {
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

    public static TreeSet<CardData> createSectionsDeduplicator(List<List<CardData>> list) {
        TreeSet<CardData> dedup = new TreeSet<>(
                Comparator.comparing(CardData::getTitle)
                        .thenComparing(CardData::getName)
                        .thenComparing(CardData::getLegend)
                        .thenComparing(CardData::getRules)
                        .thenComparing(CardData::getCost)
        );
        for (List<CardData> l : list) {
            dedup.addAll(l);
        }
        return dedup;
    }

}
