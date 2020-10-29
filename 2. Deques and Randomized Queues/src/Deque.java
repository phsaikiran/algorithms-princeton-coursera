import java.util.Arrays;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private static final Object[] DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA = {};
    private Object[] elementData;
    private int size;

    // construct an empty deque
    public Deque() {
        elementData = DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    private Object[] grow(int minCapacity) {
        int oldCapacity = elementData.length;
        if (oldCapacity > 0 || elementData != DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA) {
            int newCapacity = oldCapacity * 2;
            return elementData = Arrays.copyOf(elementData, newCapacity);
        } else {
            return elementData = new Object[minCapacity];
        }
    }

    private Object[] grow() {
        return grow(size + 1);
    }

    // return the number of items on the deque
    public int size() {
        return elementData.length;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item is null");
        }
        if (size == 0 || size == elementData.length) {
            elementData = grow();
        }

        elementData[size] = item;
        size += 1;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item is null");
        }
    }

    // remove and return the item from the front
//    public Item removeFirst() {
//    }
//
//    // remove and return the item from the back
//    public Item removeLast() {
//    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        Iterator<Item> iterator = null;

        return iterator;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();

        System.out.println(deque.isEmpty());
        deque.addFirst("ff");
        deque.addFirst("ffdd");
        deque.addFirst("1ff");
        deque.addFirst("2ffdd");
        deque.addFirst("3ff");
        deque.addFirst("4ffdd");
        deque.addFirst("f5f");
        deque.addFirst("6ffdd");
        deque.addFirst("7ff");
        deque.addFirst("8ffdd");
        deque.addFirst("9ff");
        deque.addFirst("00ffdd");
        deque.addFirst("---ff");
        deque.addFirst("===ffdd");


    }

}