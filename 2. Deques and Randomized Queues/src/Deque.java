import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        Item data;
        Node next, previous;

        public Node(Item data) {
            this.data = data;
            this.next = null;
            this.previous = null;
        }
    }

    private Node front, rear;
    private int size;

    private class DequeIterator implements Iterator<Item> {

        private Node front;

        public DequeIterator(Node front) {
            this.front = front;
        }

        @Override
        public boolean hasNext() {
            return front != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("!hasNext()");
            }
            Node ptr = front;
            front = front.next;
            return ptr.data;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove unsupported");
        }
    }

    // construct an empty deque
    public Deque() {
        front = null;
        rear = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item == null");
        }

        Node node = new Node(item);
        if (front == null || rear == null) {
            front = node;
            rear = node;
        } else {
            front.previous = node;
            node.next = front;
            front = node;
        }
        size += 1;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item == null");
        }

        Node node = new Node(item);
        if (front == null || rear == null) {
            front = node;
            rear = node;
        } else {
            node.previous = rear;
            rear.next = node;
            rear = node;
        }
        size += 1;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("size == 0");
        }
        Node ptr = front;
        front = front.next;
        if (front == null) {
            rear = null;
        } else {
            front.previous = null;
        }
        size -= 1;
        return ptr.data;
    }

    //
//    // remove and return the item from the back
    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("size == 0");
        }
        Node ptr = rear;
        rear = rear.previous;
        if (rear == null) {
            front = null;
        } else {
            rear.next = null;
        }
        size -= 1;
        return ptr.data;
    }

    private void print() {
        Node ptr = front;
        while (ptr != null) {
            System.out.println(ptr.data);
            ptr = ptr.next;
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator(front);
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();

        System.out.println(deque.isEmpty());
        deque.addFirst("0");
        deque.addFirst("1");
        deque.addFirst("2");
        deque.addFirst("3");
        deque.addFirst("4");
        deque.addFirst("5");
        deque.addFirst("6");
        deque.addFirst("7");
        deque.addFirst("8");
        deque.addFirst("9");

        System.out.println("-------------------------------");
        deque.print();
        System.out.println("-------------------------------");

        deque.addLast("a");
        deque.addLast("b");
        deque.addLast("c");
        deque.addLast("d");
        deque.addLast("e");
        deque.addLast("f");
        deque.addLast("g");
        deque.addLast("h");
        deque.addLast("i");
        deque.addLast("j");
        deque.addLast("k");
        deque.addLast("l");
        deque.addLast("m");
        deque.addLast("n");
//        System.out.println("-------------------------------");
//        System.out.println(deque.isEmpty());
//        System.out.println(deque.size());
        System.out.println("-------------------------------");
        deque.print();
        System.out.println("-------------------------------");

        System.out.println(deque.removeFirst());
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeFirst());

        System.out.println("-------------------------------");
        deque.print();
        System.out.println("-------------------------------");

        deque.addLast("o");
        deque.addLast("p");
        deque.addLast("q");
        deque.addLast("r");
        deque.addLast("s");
        deque.addLast("t");

        System.out.println("-------------------------------");
        deque.print();
        System.out.println("-------------------------------");

        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());


        System.out.println("-------------------------------");
        deque.print();
        System.out.println("-------------------------------");

        deque.addFirst("14");
        deque.addFirst("15");
        deque.addFirst("16");
        deque.addFirst("17");
        deque.addFirst("18");
        deque.addFirst("19");

//        System.out.println("-------------------------------");
//        System.out.println(deque.isEmpty());
//        System.out.println(deque.size());
        System.out.println("-------------------------------");
        deque.print();
        System.out.println("-------------------------------");

        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());

        System.out.println("-------------------------------");
        deque.print();
        System.out.println("-------------------------------");

        deque.addFirst("3");
        deque.addFirst("4");
        deque.addFirst("5");

        System.out.println("-------------------------------");
        deque.print();
        System.out.println("-------------------------------");

        System.out.println(deque.removeFirst());
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeFirst());

        System.out.println("-------------------------------");
        deque.print();
        System.out.println("-------------------------------");

        deque.addFirst("0");
        deque.addFirst("1");
        deque.addFirst("2");
        deque.addFirst("3");
        deque.addFirst("4");
        deque.addFirst("5");
        deque.addFirst("6");
        deque.addFirst("7");
        deque.addFirst("8");
        deque.addFirst("9");

        deque.addLast("a");
        deque.addLast("b");
        deque.addLast("c");
        deque.addLast("d");
        deque.addLast("e");
        deque.addLast("f");
        deque.addLast("g");
        deque.addLast("h");
        deque.addLast("i");
        deque.addLast("j");
        deque.addLast("k");
        deque.addLast("l");
        deque.addLast("m");
        deque.addLast("n");

        System.out.println("-------------------------------");
        deque.print();
        System.out.println("-------------------------------");

        for (String d : deque) {
            System.out.println(":" + d);
        }

        deque.addFirst("iii");
        deque.addLast("iii");

        for (String d : deque) {
            System.out.println(":" + d);
        }

    }

}