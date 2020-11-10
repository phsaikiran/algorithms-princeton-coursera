public class Heap {

    public static void sort(Comparable[] pq) {
        int N = pq.length;
        for (int k = N / 2; k >= 1; k--) {
            sink(pq, k, N);
        }
        while (N > 1) {
            exchange(pq, 1, N);
            sink(pq, 1, --N);
        }
    }

    public static void sink(Comparable[] pq, int k, int N) {

    }

    public static boolean less(Comparable[] pq, int i, int j) {
        return true;
    }

    public static void exchange(Comparable[] pq, int i, int j) {

    }

}
