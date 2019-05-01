package com.epam.shop.container;

import com.epam.shop.model.Vehicle;

import java.util.*;

public class CopyOnWrite<T extends Vehicle> implements List<T> {

    private static final int NO_SUCH_ELEMENT = -1;
    private List<T> unmodifiable;
    private List<T> modifiable;

    public CopyOnWrite(List<T> unmodifiable, List<T> modifiable) {
        this.unmodifiable = Objects.requireNonNull(unmodifiable);
        this.modifiable = Objects.requireNonNull(modifiable);
    }


    @Override
    public int size() {
        return unmodifiable.size() + modifiable.size();
    }

    @Override
    public boolean isEmpty() {
        return unmodifiable.size() + modifiable.size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new IteratorListImpl<T>();
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean add(T t) {
        return modifiable.add(t);
    }

    @Override
    public boolean remove(Object o) {
        if (checkObjectIndex(o)) {
            return modifiable.remove(o);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    private boolean checkObjectIndex(Object o) {
        return modifiable.contains(o);

    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return modifiable.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        checkRange(index);
        return modifiable.addAll(index,c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return modifiable.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        modifiable.clear();
    }

    @Override
    public T get(int index) {
        if (unmodifiable.size()<index){
            return unmodifiable.get(index);
        }
        checkRange(index);
        return modifiable.get(index);
    }

    @Override
    public T set(int index, T element) {
        checkRange(index);
        return modifiable.set(index,element);
    }

    @Override
    public void add(int index, T element) {
        checkRange(index);
        modifiable.add(index, element);
    }

    private void checkRange(int index) {
        if (unmodifiable.size() > index) {
            throw new UnsupportedOperationException();
        } else if (index >= modifiable.size()) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public T remove(int index) {
        checkRange(index);
        return modifiable.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        if (unmodifiable.contains(o)) {
            return unmodifiable.indexOf(o);
        } else if (modifiable.contains(o)) {
            return unmodifiable.size() + modifiable.indexOf(o) - 1;
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = modifiable.lastIndexOf(o);
        if (index != NO_SUCH_ELEMENT) {
            return unmodifiable.size() + modifiable.lastIndexOf(o) - 1;
        } else if (unmodifiable.lastIndexOf(o) != NO_SUCH_ELEMENT) {
            return unmodifiable.lastIndexOf(o);
        } else {
            throw new NoSuchElementException();
        }

    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    private class IteratorListImpl<T extends Vehicle> implements Iterator<T> {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public T next() {
            return null;
        }
    }
}
