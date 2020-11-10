public class MaxPQ<Key extends Comparable<Key>> {

    private final Key[] pq;
    private int N;

    public MaxPQ(int capacity) {
        this.N = 0;
        pq = (Key[]) new Comparable[capacity + 1];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public void insert(Key key) {
        N += 1;
        pq[N] = key;
        swim(N);
    }

    public Key delMax() {
        Key max = pq[1];
        exchange(1, N--);
        sink(1);
        pq[N + 1] = null;
        return max;
    }

    private void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            exchange(k, k / 2);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= N) {
            int j = 2 * k;
            if (j < N && less(j, j + 1)) {
                j += 1;
            }
            if (!less(k, j)) {
                break;
            }
            k = j;
        }
    }

    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    private void exchange(int i, int j) {
        Key t = pq[i];
        pq[i] = pq[j];
        pq[j] = t;
    }

}
