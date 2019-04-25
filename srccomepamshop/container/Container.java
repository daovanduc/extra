package com.epam.dao.task1.task;

import com.epam.dao.task1.entity.Vehicle;

import java.util.*;
import java.util.function.Predicate;

public class Container implements List<Vehicle> {


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
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public Iterator<Vehicle> iterator() {
        return new IteratorImpl<Vehicle>();
    }

    public Iterator<Vehicle> iterator(Predicate<Vehicle> predicate) {
        return new IteratorImpl<>(predicate);
    }

    @Override
    public Object[] toArray() {
        Vehicle[] vehicles1 = new Vehicle[size];
        System.arraycopy(vehicles, 0, vehicles1, 0, size);
        return vehicles1;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size)
            return (T[]) Arrays.copyOf(vehicles, size, a.getClass());
        System.arraycopy(vehicles, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

    @Override
    public boolean add(Vehicle vehicle) {
        ensureCapacity(size + 1);
        if (vehicles[vehicles.length - 1] != null) {
            resize(vehicles.length * 2 + 1);
        }
        vehicles[size++] = vehicle;
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
    public boolean addAll(Collection<? extends Vehicle> c) {
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
    public boolean addAll(int index, Collection<? extends Vehicle> c) {
        rangeCheck(index);
        Vehicle[] a = (Vehicle[]) c.toArray();
        int numNew = a.length;
        ensureCapacity(size + numNew);

        int numMoved = size - index;
        if (numMoved > 0)
            System.arraycopy(vehicles, index, vehicles, index + numNew,
                    numMoved);

        System.arraycopy(a, 0, vehicles, index, numNew);
        size += numNew;
        return numNew != 0;
    }

    @Override
    public boolean removeAll(Collection<?> c) {

        Objects.requireNonNull(c);
        Vehicle[] toBeRemoved = (Vehicle[]) c.toArray();

        for (int i = 0; i < toBeRemoved.length; i++) {
            while (contains(toBeRemoved[i]))
                remove(indexOf(toBeRemoved[i]));
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c);
        Object[] toBeRemoved = c.toArray();
        for (int i = 0; i < size; i++) {
            if (Arrays.binarySearch(toBeRemoved, vehicles[i]) == -1) {
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
    public Vehicle get(int index) {
        return vehicles[index];
    }

    @Override
    public Vehicle set(int index, Vehicle element) {
        rangeCheck(index);
        Vehicle previousElement = vehicles[index];
        vehicles[index] = element;
        return previousElement;
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
    public Vehicle remove(int index) {

        Vehicle oldValue = vehicles[index];

        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(vehicles, index + 1, vehicles, index,
                    numMoved);
        vehicles[--size] = null;

        return oldValue;
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
    private void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size : " + size;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size; i >= 0; i--)
                if (vehicles[i] == null)
                    return i;
        } else {
            for (int i = size; i >= 0; i--)
                if (o.equals(vehicles[i]))
                    return i;
        }
        return -1;
    }

    @Override
    public ListIterator<Vehicle> listIterator() {
        return null;
    }

    @Override
    public ListIterator<Vehicle> listIterator(int index) {
        return null;
    }

    @Override
    public List<Vehicle> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < size; i++) {
            if ((vehicles[i + 1] == null && vehicles[i + 2] == null) || i == vehicles.length - 1) {
                s.append(vehicles[i]);
            } else {
                s.append(vehicles[i]).append(",\r\n");
            }
        }
        return s.toString();
    }


    public class IteratorImpl<Vehicle> implements Iterator<Vehicle> {

        private int cursor;
        private boolean doneNext;
        private int nextCondition;


        private Predicate<Vehicle> predicate;

        public IteratorImpl() {
        }

        public IteratorImpl(Predicate<Vehicle> predicate) {
            this.predicate = predicate;
        }

        public boolean hasNext() {
            if (predicate == null) {
                if (cursor < size) {
                    return true;
                }
            }
            return hasnextCondition();
        }

        public Vehicle next() {
            checkNext();

            if (noCondition())
                return (Vehicle) vehicles[cursor++];

            if (hasnextCondition()) {
                hasCondition();
            } else {
                throw new NoSuchElementException();
            }
            return (Vehicle) vehicles[cursor++];
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

        private boolean hasnextCondition() {
            for (int i = cursor; i < size; i++) {
                if (predicate.test((Vehicle) vehicles[i])) {
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

