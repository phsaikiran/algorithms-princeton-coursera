import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final Object[] EMPTY_ELEMENT_DATA = {};
    private Object[] elementData;
    private int size;

    private class RandomizedQueueIterator implements Iterator<Item> {

        private final Object[] data;
        private int s;

        public RandomizedQueueIterator() {
            this.data = elementData.clone();
            this.s = size;
        }

        @Override
        public boolean hasNext() {
            return s > 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("!hasNext()");
            }
            int random = StdRandom.uniform(0, s);
            Item returnData = (Item) data[random];
            data[random] = data[s - 1];
            data[s - 1] = null;
            s -= 1;
            return returnData;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove unsupported");
        }
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        elementData = EMPTY_ELEMENT_DATA;
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    private Object[] resize(int newCapacity) {
        if (elementData.length > 0 && elementData != EMPTY_ELEMENT_DATA) {
            Object[] newElementData = new Object[newCapacity];
            System.arraycopy(elementData, 0, newElementData, 0, Math.min(newCapacity, elementData.length));
            return newElementData;
        } else {
            return elementData = new Object[1];
        }
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item is null");
        }
        if (size == 0 || size == elementData.length) {
            elementData = resize(elementData.length * 2);
        }

        elementData[size] = item;
        size += 1;
    }

    private void print() {
        for (int i = 0; i < size; i++) {
            System.out.println(elementData[i]);
        }
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("size == 0");
        }
        if (size < elementData.length / 2) {
            elementData = resize(elementData.length / 2);
        }

        int random = StdRandom.uniform(0, size);
        Item returnData = (Item) elementData[random];
        elementData[random] = elementData[size - 1];
        elementData[size - 1] = null;
        size -= 1;
        return returnData;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException("size == 0");
        }
        int random = StdRandom.uniform(0, size);
        return (Item) elementData[random];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        System.out.println(randomizedQueue.isEmpty());

        randomizedQueue.enqueue("s");
        randomizedQueue.enqueue("s1");
        randomizedQueue.enqueue("2s");
        randomizedQueue.enqueue("s3");
        randomizedQueue.enqueue("s4");
        randomizedQueue.enqueue("s5");
        randomizedQueue.enqueue("s6");
        randomizedQueue.enqueue("s7");
        randomizedQueue.enqueue("s0");
        randomizedQueue.enqueue("s8");
        randomizedQueue.enqueue("s9");

        System.out.println(randomizedQueue.size());

        System.out.println("-------------------------------");

        System.out.println(randomizedQueue.sample());
        System.out.println(randomizedQueue.sample());
        System.out.println(randomizedQueue.sample());
        System.out.println(randomizedQueue.sample());

        System.out.println("-------------------------------");

        System.out.println(randomizedQueue.dequeue());
        System.out.println(randomizedQueue.dequeue());
        System.out.println(randomizedQueue.dequeue());
        System.out.println(randomizedQueue.dequeue());
        System.out.println(randomizedQueue.dequeue());
        System.out.println(randomizedQueue.dequeue());
        System.out.println(randomizedQueue.dequeue());
        System.out.println(randomizedQueue.dequeue());
        System.out.println(randomizedQueue.dequeue());
        System.out.println(randomizedQueue.dequeue());
        System.out.println(randomizedQueue.dequeue());

        System.out.println("-------------------------------");

        randomizedQueue.print();

        System.out.println("-------------------------------");

        randomizedQueue.enqueue("s");
        randomizedQueue.enqueue("s1");
        randomizedQueue.enqueue("2s");
        randomizedQueue.enqueue("s3");
        randomizedQueue.enqueue("s4");
        randomizedQueue.enqueue("s5");
        randomizedQueue.enqueue("s6");
        randomizedQueue.enqueue("s7");
        randomizedQueue.enqueue("s0");
        randomizedQueue.enqueue("s8");
        randomizedQueue.enqueue("s9");

        for (String s : randomizedQueue) {
            System.out.println(s);
        }

        System.out.println("-------------------------------");

        for (String s : randomizedQueue) {
            System.out.println(s);
        }

        System.out.println("-------------------------------");

        for (String s : randomizedQueue) {
            for (String ss : randomizedQueue) {
                System.out.println(ss + ":" + s);
            }
        }
    }

}