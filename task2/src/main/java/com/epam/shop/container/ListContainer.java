package com.epam.shop.container;

import com.epam.shop.model.Vehicle;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

public class ListContainer<T extends Vehicle> extends Container  {

    @Override
    public Iterator<T> iterator() {
        int size = size();
        T[] newArray = (T[]) Arrays.copyOf(toArray(),size());
        return new IteratorList<T>(newArray,size);
    }


    private class IteratorList<T extends Vehicle> implements Iterator<T> {

        private int cursor;
        private int size;
        private T[] buffered;

        public IteratorList(T[] newArray, int size) {
            this.size= size;
            this.buffered= newArray;
        }

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public T next() {
            checkNext();
            if (noCondition()) {
                return buffered[cursor++];
            } else {
                throw new NoSuchElementException();
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void forEachRemaining(Consumer<? super T> action) {
            Objects.requireNonNull(action);
            while (hasNext())
                action.accept(next());
        }

        private void checkNext() {
            if (cursor >= size) {
                throw new NoSuchElementException();
            }
        }
        private boolean noCondition() {
            return cursor < size;
        }
    }
}
