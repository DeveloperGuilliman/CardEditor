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
    private String name;

    public SectionData() {
        this.cards = new ArrayList<>();
        this.name = "";
    }

    public SectionData(int len) {
        this.cards = new ArrayList<>(len);
        this.name = "";

    }

    public SectionData(Collection<? extends CardData> c) {
        this.cards = new ArrayList<>(c);
        this.name = getCommonCardsName();
    }

    public void updateName() {
        name = getCommonCardsName();
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

    public String getName() {
        return name;
    }

    private String getCommonCardsName() {
        if (cards.isEmpty()) {
            return "";
        }

        ArrayList<String> list = new ArrayList<>(cards.size());
        for (CardData card : cards) {
            list.add(Utils.normalizeTrim(card.getTitle()));
        }
        return Utils.longestCommonWords(list);
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
        CardData ret = cards.set(index, element);
        updateName();
        return ret;
    }

    @Override
    public boolean add(CardData e) {
        boolean ret = cards.add(e);
        if (ret) {
            updateName();
        }
        return ret;
    }

    @Override
    public void add(int index, CardData element) {
        cards.add(index, element);
        updateName();
    }

    @Override
    public CardData remove(int index) {
        CardData ret = cards.remove(index);
        updateName();
        return ret;
    }

    @Override
    public boolean remove(Object o) {
        boolean ret = cards.remove(o);
        if (ret) {
            updateName();
        }
        return ret;
    }

    @Override
    public void clear() {
        cards.clear();
        name = "";
    }

    @Override
    public boolean addAll(Collection<? extends CardData> c) {
        boolean ret = cards.addAll(c);
        if (ret) {
            updateName();
        }
        return ret;
    }

    @Override
    public boolean addAll(int index, Collection<? extends CardData> c) {
        boolean ret = cards.addAll(index, c);
        if (ret) {
            updateName();
        }
        return ret;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean ret = cards.removeAll(c);
        if (ret) {
            updateName();
        }
        return ret;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean ret = cards.retainAll(c);
        if (ret) {
            updateName();
        }
        return ret;
    }

    @Override
    public ListIterator<CardData> listIterator(int index) {
        return cards.listIterator(index);
    }

    @Override
    public ListIterator<CardData> listIterator() {

        ListIterator<CardData> listIterator = cards.listIterator();
        return new ListIterator<CardData>() {
            @Override
            public boolean hasNext() {
                return listIterator.hasNext();
            }

            @Override
            public CardData next() {
                return listIterator.next();
            }

            @Override
            public boolean hasPrevious() {
                return listIterator.hasPrevious();
            }

            @Override
            public CardData previous() {
                return listIterator.previous();
            }

            @Override
            public int nextIndex() {
                return listIterator.nextIndex();
            }

            @Override
            public int previousIndex() {
                return listIterator.previousIndex();
            }

            @Override
            public void remove() {
                listIterator.remove();
                updateName();
            }

            @Override
            public void set(CardData e) {
                listIterator.set(e);
                updateName();
            }

            @Override
            public void add(CardData e) {
                listIterator.add(e);
                updateName();
            }
        };
    }

    @Override
    public Iterator<CardData> iterator() {
        Iterator<CardData> iterator = cards.iterator();
        return new Iterator<CardData>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public CardData next() {
                return iterator.next();
            }

            @Override
            public void remove() {
                iterator.remove();
                updateName();
            }

        };
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
        boolean ret = cards.removeIf(filter);
        if (ret) {
            updateName();
        }
        return ret;
    }

    @Override
    public void replaceAll(UnaryOperator<CardData> operator) {
        cards.replaceAll(operator);
        updateName();
    }

    @Override
    public void sort(Comparator<? super CardData> c) {
        cards.sort(c);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + cards.hashCode();
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
