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
 * @author DeveloperGuilliman
 */
public class CardCollectionData implements List<SectionData> {

    private final ArrayList<SectionData> sections;

    public CardCollectionData() {
        this.sections = new ArrayList<>();
    }

    public CardCollectionData(int len) {
        this.sections = new ArrayList<>(len);
    }

    public CardCollectionData(Collection<? extends SectionData> c) {
        this.sections = new ArrayList<>(c);
    }

    public int getExactIndex(SectionData section) {
        return Utils.getExactIndex(sections, section);
    }

    public int getBoundIndex(int position) {
        return Utils.getBoundIndex(sections, position);
    }

    public int getBoundIndexPlusOne(int position) {
        return Utils.getBoundIndexPlusOne(sections, position);
    }

    @Override
    public int size() {
        return sections.size();
    }

    @Override
    public boolean isEmpty() {
        return sections.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return sections.contains(o);
    }

    @Override
    public int indexOf(Object o) {
        return sections.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return sections.lastIndexOf(o);
    }

    @Override
    public Object[] toArray() {
        return sections.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return sections.toArray(a);
    }

    @Override
    public SectionData get(int index) {
        return sections.get(index);
    }

    @Override
    public SectionData set(int index, SectionData element) {
        return sections.set(index, element);
    }

    @Override
    public boolean add(SectionData e) {
        return sections.add(e);
    }

    @Override
    public void add(int index, SectionData element) {
        sections.add(index, element);
    }

    @Override
    public SectionData remove(int index) {
        return sections.remove(index);
    }

    @Override
    public boolean remove(Object o) {
        return sections.remove(o);
    }

    @Override
    public void clear() {
        sections.clear();
    }

    @Override
    public boolean addAll(Collection<? extends SectionData> c) {
        return sections.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends SectionData> c) {
        return sections.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return sections.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return sections.retainAll(c);
    }

    @Override
    public ListIterator<SectionData> listIterator(int index) {
        return sections.listIterator(index);
    }

    @Override
    public ListIterator<SectionData> listIterator() {
        return sections.listIterator();
    }

    @Override
    public Iterator<SectionData> iterator() {
        return sections.iterator();
    }

    @Override
    public List<SectionData> subList(int fromIndex, int toIndex) {
        return sections.subList(fromIndex, toIndex);
    }

    @Override
    public void forEach(Consumer<? super SectionData> action) {
        sections.forEach(action);
    }

    @Override
    public Spliterator<SectionData> spliterator() {
        return sections.spliterator();
    }

    @Override
    public boolean removeIf(Predicate<? super SectionData> filter) {
        return sections.removeIf(filter);
    }

    @Override
    public void replaceAll(UnaryOperator<SectionData> operator) {
        sections.replaceAll(operator);
    }

    @Override
    public void sort(Comparator<? super SectionData> c) {
        sections.sort(c);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.sections);
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
        final CardCollectionData other = (CardCollectionData) obj;
        return Objects.equals(this.sections, other.sections);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return sections.containsAll(c);
    }

    @Override
    public String toString() {
        return sections.toString();
    }

    @Override
    public Stream<SectionData> stream() {
        return sections.stream();
    }

    @Override
    public Stream<SectionData> parallelStream() {
        return sections.parallelStream();
    }

}
