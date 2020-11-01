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
package com.developerguilliman.cardEditor.data;

import com.developerguilliman.cardEditor.Utils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 *
 * @author Developer Guilliman <developerguilliman@gmail.com>
 */
public class SectionData implements List<CardData> {

    private final ArrayList<CardData> cards;

    public SectionData() {
        this.cards = new ArrayList<>();
    }

    public SectionData(int len) {
        this.cards = new ArrayList<>(len);
    }

    public SectionData(Collection<? extends CardData> c) {
        this.cards = new ArrayList<>(c);
    }

    public int getExactIndex(CardData card) {
        return Utils.getExactIndex(cards, card);
    }

    public int getBoundIndex(int position) {
        return Utils.getBoundIndex(cards, position);
    }

    public int getBoundIndexPlusOne(int position) {
        return Utils.getBoundIndexPlusOne(cards, position);
    }

    @Override
    public int size() {
        return cards.size();
    }

    @Override
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return cards.contains(o);
    }

    @Override
    public int indexOf(Object o) {
        return cards.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return cards.lastIndexOf(o);
    }

    @Override
    public Object[] toArray() {
        return cards.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return cards.toArray(a);
    }

    @Override
    public CardData get(int index) {
        return cards.get(index);
    }

    @Override
    public CardData set(int index, CardData element) {
        return cards.set(index, element);
    }

    @Override
    public boolean add(CardData e) {
        return cards.add(e);
    }

    @Override
    public void add(int index, CardData element) {
        cards.add(index, element);
    }

    @Override
    public CardData remove(int index) {
        return cards.remove(index);
    }

    @Override
    public boolean remove(Object o) {
        return cards.remove(o);
    }

    @Override
    public void clear() {
        cards.clear();
    }

    @Override
    public boolean addAll(Collection<? extends CardData> c) {
        return cards.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends CardData> c) {
        return cards.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return cards.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return cards.retainAll(c);
    }

    @Override
    public ListIterator<CardData> listIterator(int index) {
        return cards.listIterator(index);
    }

    @Override
    public ListIterator<CardData> listIterator() {
        return cards.listIterator();
    }

    @Override
    public Iterator<CardData> iterator() {
        return cards.iterator();
    }

    @Override
    public List<CardData> subList(int fromIndex, int toIndex) {
        return cards.subList(fromIndex, toIndex);
    }

    @Override
    public void forEach(Consumer<? super CardData> action) {
        cards.forEach(action);
    }

    @Override
    public Spliterator<CardData> spliterator() {
        return cards.spliterator();
    }

    @Override
    public boolean removeIf(Predicate<? super CardData> filter) {
        return cards.removeIf(filter);
    }

    @Override
    public void replaceAll(UnaryOperator<CardData> operator) {
        cards.replaceAll(operator);
    }

    @Override
    public void sort(Comparator<? super CardData> c) {
        cards.sort(c);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SectionData other = (SectionData) obj;
        return Objects.equals(this.cards, other.cards);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return cards.containsAll(c);
    }

    @Override
    public String toString() {
        return cards.toString();
    }

    @Override
    public Stream<CardData> stream() {
        return cards.stream();
    }

    @Override
    public Stream<CardData> parallelStream() {
        return cards.parallelStream();
    }

}
