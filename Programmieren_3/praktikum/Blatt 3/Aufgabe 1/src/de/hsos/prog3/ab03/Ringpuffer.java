package de.hsos.prog3.ab03;

import java.io.Serializable;
import java.util.*;

public class Ringpuffer<T> implements Deque<T>, RandomAccess, Serializable, Cloneable {
    private static final int CAPACITY_FACTOR = 2;

    private ArrayList<T> elements;
    private T[] test;
    private Cursor head;
    private Cursor tail;
    private int size;
    private int capacity;
    private boolean fixedCapacity;
    private boolean discarding;

    private boolean empty;

    public Ringpuffer(int capacity, boolean fixedCapacity, boolean discarding) throws IllegalArgumentException {
        if (capacity <= 0) {
            throw new IllegalArgumentException();
        }
        this.elements = new ArrayList<>(Collections.nCopies(capacity, null));
        this.head = new Cursor(0);
        this.tail = new Cursor(0);
        this.size = 0;
        this.capacity = capacity;
        this.fixedCapacity = fixedCapacity;
        this.discarding = discarding;
        this.empty = true;
    }

    public Ringpuffer(Ringpuffer<T> other) {
        this(other.capacity, other.fixedCapacity, other.discarding);
        this.elements = new ArrayList<T>(other.elements);
    }

    private class Cursor {
        public int index;

        public Cursor(int index) {
            this.index = index;
        }

        public Cursor(Cursor other) {
            this.index = other.index;
        }

        void increment() {
            index = (index + 1) % capacity;
        }

        void decrement() {
            index = (index - 1 + capacity) % capacity;
        }

        T get() {
            return elements.get(index);
        }

        void set(T t) {
            elements.set(index, t);
        }

        public boolean equals(Cursor other) {
            if (other == null) {
                return false;
            }
            return other.index == this.index;
        }
    }

    private void increaseCapacity() {
        ArrayList<T> newElements = new ArrayList<>(Collections.nCopies(this.capacity * CAPACITY_FACTOR, null));
        int i = 0;
        for (T t : this) {
            newElements.set(i, t);
            i++;
        }
        this.elements = newElements;
        this.tail = new Cursor(0);
        this.head = new Cursor(capacity);
        this.capacity *= CAPACITY_FACTOR;
    }

    @Override
    public void addFirst(T t) {
        if (!head.equals(tail) || empty) {
            tail.decrement();
            tail.set(t);
            empty = false;
        } else {
            if (discarding) {
                tail.decrement();
                tail.set(t);
                head.decrement();
            } else if (!fixedCapacity) {
                increaseCapacity();
                addFirst(t);
            } else {
                throw new IllegalStateException();
            }
        }
    }

    @Override
    public void addLast(T t) {
        if (!head.equals(tail) || empty) {
            head.set(t);
            head.increment();
            empty = false;
        } else if (!fixedCapacity) {
            increaseCapacity();
            addLast(t);
        } else {
            if (discarding) {
                head.set(t);
                head.increment();
                tail.increment();
            } else {
                throw new IllegalStateException();
            }
        }
    }

    @Override
    public boolean offerFirst(T t) {
        try {
            addFirst(t);
        } catch (IllegalStateException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean offerLast(T t) {
        try {
            addLast(t);
        } catch (IllegalStateException e) {
            return false;
        }
        return true;
    }

    @Override
    public T removeFirst() {
        if (empty) {
            throw new NoSuchElementException();
        }
        T element = tail.get();
        tail.increment();
        if (tail.equals(head)) {
            empty = true;
        }
        return element;
    }

    @Override
    public T removeLast() {
        if (empty) {
            throw new NoSuchElementException();
        }
        head.decrement();
        T element = head.get();
        if (head.equals(tail)) {
            empty = true;
        }
        return element;
    }

    @Override
    public T pollFirst() {
        try {
            return removeFirst();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public T pollLast() {
        try {
            return removeLast();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public T getFirst() {
        if (empty) {
            throw new NoSuchElementException();
        }
        return head.get();
    }

    @Override
    public T getLast() {
        if (empty) {
            throw new NoSuchElementException();
        }
        return tail.get();
    }

    @Override
    public T peekFirst() {
        try {
            return getFirst();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public T peekLast() {
        try {
            return getLast();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        //TODO: Implement
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        //TODO: Implement
        return false;
    }

    @Override
    public boolean add(T t) {
        return this.offerLast(t);
    }

    @Override
    public boolean offer(T t) {
        return this.offerFirst(t);
    }

    @Override
    public T remove() {
        return removeFirst();
    }

    @Override
    public T poll() {
        return pollFirst();
    }

    @Override
    public T element() {
        return getFirst();
    }

    @Override
    public T peek() {
        return peekFirst();
    }

    @Override
    public void push(T t) {
        addLast(t);
    }

    @Override
    public T pop() {
        return removeFirst();
    }

    @Override
    public boolean remove(Object o) {
        return removeFirstOccurrence(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException();
        }
        if (c.isEmpty()) {
            return true;
        }
        if (this.isEmpty()) {
            return false;
        }
        for (Object o : c) {
            if (!this.contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (T t : c) {
            this.add(t);
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        head = new Cursor(0);
        tail = new Cursor(0);
        empty = true;
    }

    @Override
    public boolean contains(Object o) {
        Cursor cursor = new Cursor(head);
        boolean found = false;
        for (T t : this) {
            if (o.equals(t)) {
                found = true;
                break;
            }
        }
        return found;
    }

    @Override
    public int size() {
        if (empty) {
            return 0;
        }
        return (head.index - tail.index + capacity - 1) % capacity + 1;
    }

    @Override
    public boolean isEmpty() {
        return empty;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private Cursor cursor = new Cursor(tail);

            @Override
            public boolean hasNext() {
                return !(cursor.equals(head));
            }

            @Override
            public T next() {
                T next = cursor.get();
                cursor.increment();
                return next;
            }
        };
    }

    @Override
    public Object[] toArray() {
        return elements.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return elements.toArray(a);
    }

    @Override
    public Iterator<T> descendingIterator() {
        return new Iterator<T>() {

            private Cursor cursor = new Cursor(head);

            @Override
            public boolean hasNext() {
                return !(cursor.equals(tail));
            }

            @Override
            public T next() {
                cursor.decrement();
                return cursor.get();
            }
        };
    }

    @Override
    public Object clone(){
        return new Ringpuffer<T>(this);
    }


}
