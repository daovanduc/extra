package com.epam.shop.container;

import com.epam.shop.model.Vehicle;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.Iterator;
import java.util.Arrays;
import java.util.Collection;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.Predicate;

public class Container<T extends Vehicle> implements List<T> {

    private static final int NO_SUCH_ELEMENT = -1;
    private Vehicle[] vehicles = new Vehicle[10];
    private int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object object) {
        return indexOf(object) >= 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new IteratorImpl<T>();
    }

    public Iterator<T> iterator(Predicate<T> predicate) {
        return new IteratorImpl<T>(predicate);
    }

    @Override
    public Object[] toArray() {
        Vehicle[] vehicles1 = new Vehicle[size];
        System.arraycopy(vehicles, 0, vehicles1, 0, size);
        return vehicles1;
    }

    @Override
    public <T1> T1[] toArray(T1[] array) {
        if (array.length < size) {
            return (T1[]) Arrays.copyOf(vehicles, size, array.getClass());
        }
        System.arraycopy(vehicles, 0, array, 0, size);
        if (array.length > size) {
            array[size] = null;
        }
        return array;
    }

    @Override
    public boolean add(T t) {
        ensureCapacity(size + 1);
        if (vehicles[vehicles.length - 1] != null) {
            resize(vehicles.length * 2 + 1);
        }
        vehicles[size++] = t;
        return true;
    }

    private void resize(int newLength) {
        Vehicle[] newVehicles = new Vehicle[newLength];
        System.arraycopy(vehicles, 0, newVehicles, 0, newLength);
        vehicles = newVehicles;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (o != null && o.equals(vehicles[i])) {
                System.arraycopy(vehicles, i + 1, vehicles, i, size - i);
                vehicles[size--] = null;
                return true;

            } else if (o == (vehicles[i])) {
                System.arraycopy(vehicles, i + 1, vehicles, i, size - i);
                vehicles[size--] = null;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        Object[] objects = c.toArray();
        for (int i = 0; i < c.size(); i++)
            if (!contains(objects[i]))
                return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        Vehicle[] toBeAdded = (Vehicle[]) c.toArray();
        int sizeToBeAdded = toBeAdded.length;
        ensureCapacity(size + sizeToBeAdded);
        System.arraycopy(toBeAdded, 0, vehicles, size, sizeToBeAdded);
        size += sizeToBeAdded;
        return true;
    }

    private void ensureCapacity(int minCapacity) {
        int oldCapacity = vehicles.length;
        if (minCapacity > oldCapacity) {
            int newCapacity = oldCapacity + oldCapacity / 2;
            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }
            vehicles = Arrays.copyOf(vehicles, newCapacity);
        }
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        rangeCheck(index);
        Vehicle[] a = (Vehicle[]) c.toArray();
        int numNew = a.length;

        ensureCapacity(size + numNew);

        int numMoved = size - index;
        if (numMoved > 0) {
            System.arraycopy(vehicles, index, vehicles, index + numNew,
                    numMoved);
        }
        System.arraycopy(a, 0, vehicles, index, numNew);
        size += numNew;
        return numNew != 0;
    }

    private void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);
        Vehicle[] toBeRemoved = (Vehicle[]) c.toArray();

        for (int i = 0; i < toBeRemoved.length; i++) {
            while (contains(toBeRemoved[i])) {
                remove(indexOf(toBeRemoved[i]));
            }
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c);
        Object[] toBeRemoved = c.toArray();
        for (int i = 0; i < size; i++) {
            if (Arrays.binarySearch(toBeRemoved, vehicles[i]) == NO_SUCH_ELEMENT) {
                remove(indexOf(vehicles[i]));
            }
        }
        return true;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            vehicles[i] = null;
        }
        size = 0;
    }

    @Override
    public T get(int index) {
        rangeCheck(index);
        return (T) vehicles[index];
    }

    @Override
    public T set(int index, T element) {
        rangeCheck(index);
        Vehicle previousElement = vehicles[index];
        vehicles[index] = element;
        return (T) previousElement;
    }

    @Override
    public void add(int index, Vehicle element) {
        rangeCheck(index);
        ensureCapacity(size + 1);
        System.arraycopy(vehicles, index, vehicles, index + 1,
                size - index);
        vehicles[index] = element;
        size++;
    }

    @Override
    public T remove(int index) {

        Vehicle oldValue = vehicles[index];

        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(vehicles, index + 1, vehicles, index,
                    numMoved);
        vehicles[--size] = null;

        return (T) oldValue;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if ((o == null && vehicles[i] == null) || vehicles[i].equals(o)) {
                return i;
            }
        }
        return NO_SUCH_ELEMENT;
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size : " + size;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size; i >= 0; i--)
                if (vehicles[i] == null) {
                    return i;
                }
        } else {
            for (int i = size; i >= 0; i--)
                if (o.equals(vehicles[i])) {
                    return i;
                }
        }
        return NO_SUCH_ELEMENT;
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

    private class IteratorImpl<T> implements Iterator<T> {

        private int cursor;
        private boolean doneNext;
        private int nextCondition;
        private Predicate<T> predicate;

        public IteratorImpl() {
        }

        public IteratorImpl(Predicate<T> predicate) {
            this.predicate = predicate;
        }

        public boolean hasNext() {
            if (predicate == null) {
                if (cursor < size) {
                    return true;
                }
            }
            return hasNextCondition();
        }

        public T next() {
            checkNext();
            if (noCondition()) {
                return (T) vehicles[cursor++];
            }
            if (hasNextCondition()) {
                hasCondition();
            } else {
                throw new NoSuchElementException();
            }
            return (T) vehicles[cursor++];
        }

        public void remove() {
            if (!doneNext) {
                throw new IllegalStateException();
            }
            Container.this.remove(--cursor);
            doneNext = false;
        }

        private void checkNext() {
            if (cursor >= size) {
                throw new NoSuchElementException();
            }
        }

        private boolean hasNextCondition() {
            for (int i = cursor; i < size; i++) {
                if (predicate.test((T) vehicles[i])) {
                    nextCondition = i;
                    return true;
                }
            }
            return false;
        }

        private void hasCondition() {
            doneNext = true;
            cursor = nextCondition;
        }

        private boolean noCondition() {
            if (predicate == null) {
                if (cursor < size) {
                    doneNext = true;
                    return true;
                }
            }
            return false;
        }
    }

}

