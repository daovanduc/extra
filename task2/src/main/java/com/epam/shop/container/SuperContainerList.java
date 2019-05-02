package com.epam.shop.container;

import com.epam.shop.exception.*;
import com.epam.shop.exception.IllegalStateException;
import com.epam.shop.exception.IndexOutOfBoundsException;
import com.epam.shop.exception.UnsupportedOperationException;
import com.epam.shop.model.Vehicle;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.ListIterator;


public class SuperContainerList<T extends Vehicle> implements List<T> {

    private static final int NO_SUCH_ELEMENT = -1;
    private List<T> unmodifiable;
    private List<T> modifiable;

    public SuperContainerList(List<T> unmodifiable, List<T> modifiable) {
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
        if (unmodifiable.contains(o)) {
            return true;
        }
        return modifiable.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>(){

            private int lastReturn = -1;
            private int cursor;

            @Override
            public boolean hasNext() {
                return cursor < size();
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("iterator next Exception");
                }
                return get(cursor++);
            }

            @Override
            public void remove() {
                if (cursor == 0){
                    throw new IllegalStateException("iterator remove Exception");
                }
                if (lastReturn == cursor){
                    throw new ConcurrentModificationException("iterator remove exception");
                }
                SuperContainerList.this.remove(cursor);
            }

        };
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("to Array Exception");
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException("to Array Exception");
    }

    @Override
    public boolean add(T t) {
        return modifiable.add(t);
    }

    @Override
    public boolean remove(Object o) {
        if (!checkObjectIndex(o)) {
            return modifiable.remove(o);
        } else {
            throw new UnsupportedOperationException("remove Exception");
        }
    }

    private boolean checkObjectIndex(Object o) {
        return unmodifiable.contains(o);

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
        index = getModifiableIndex(index);
        return modifiable.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);
        for (Object o : c){
            if (unmodifiable.contains(o)){
                throw new UnsupportedOperationException("removeAll Exception");
            }
        }
        return modifiable.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c);
        if (unmodifiable.containsAll(c)){
            throw new UnsupportedOperationException("retainAll Exception");
        }else {
            return modifiable.containsAll(c);
        }
    }

    @Override
    public void clear() {
        if (unmodifiable.size()==0){
            throw new UnsupportedOperationException("clear Exception");
        }
        modifiable.clear();
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >=size()){
            throw new IndexOutOfBoundsException("Get Exception");
        }else if (unmodifiable.size() > index) {
            return unmodifiable.get(index);
        }else {
            index = getModifiableIndex(index);
            return modifiable.get(index);
        }
    }

    @Override
    public T set(int index, T element) {
        checkRange(index);
        index = getModifiableIndex(index);
        return modifiable.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        checkRange(index);
        index = getModifiableIndex(index);
        modifiable.add(index, element);
    }

    private int getModifiableIndex(int index){
        index -= unmodifiable.size();
        return index;
    }

    private void checkRange(int index) {
        if (unmodifiable.size() > index) {
            throw new UnsupportedOperationException("Check Range Exception");
        } else if (index >= size()) {
            throw new IndexOutOfBoundsException("Check Range Exception");
        }
    }

    @Override
    public T remove(int index) {
        checkRange(index);
        index = getModifiableIndex(index);
        return modifiable.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        if (unmodifiable.contains(o)) {
            return unmodifiable.indexOf(o);
        } else if (modifiable.contains(o)) {
            return unmodifiable.size() + modifiable.indexOf(o) - 1;
        } else {
            throw new NoSuchElementException("IndexOf Exception");
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
            throw new NoSuchElementException("Last Index Of Exception");
        }
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException("listIterator Exception");
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException("listIterator Exception");
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("subList Exception");
    }

}
