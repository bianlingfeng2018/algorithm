package edu.fudan.algorithm.shortestpath.common;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 链表
 *
 * @param <T> 链表元素类型
 */
public class Bag<T> implements Iterable<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private int size;
    private T[] items;

    public Bag() {
        clear();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size;
    }

    public void add(T item) {
        if (items.length == size()) {
            ensureCapacity(2 * size() + 1);
        }
        items[size()] = item;
        size++;
    }

    @Override
    public Iterator<T> iterator() {
        return new BagIterator();
    }

    private class BagIterator implements Iterator<T> {
        private int current = 0;

        @Override
        public boolean hasNext() {
            return current < size();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return items[current++];
        }
    }

    private void clear() {
        size = 0;
        ensureCapacity(DEFAULT_CAPACITY);
    }

    /**
     * 给数组扩容
     *
     * @param newCapacity 新数组长度
     */
    private void ensureCapacity(int newCapacity) {
        if (newCapacity < size) {
            return;
        } else {
            T[] oldItems = items;
            items = (T[]) new Object[newCapacity];
            for (int i = 0; i < size(); i++) {
                items[i] = oldItems[i];
            }
        }
    }

}
