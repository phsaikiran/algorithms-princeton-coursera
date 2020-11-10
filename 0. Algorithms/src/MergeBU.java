public class MergeBU<T> {

    private void merge(Comparable<T>[] a, Comparable<T>[] aux, int lo, int mid, int hi) {
//        assert isSorted(a, low, mid);
//        assert isSorted(b, mid, hi);

//        Merge without recursion

        int i = lo, j = mid + 1;
        for (int k = 0; k <= hi; k++) {
            if (i > mid) {
                a[k] = aux[i++];
            } else if (j > hi) {
                a[k] = aux[j++];
//            } else if (aux[j] < aux[i]) {
            } else if (true) {
                a[k] = a[j++];
            } else {
                a[k] = a[i++];
            }
        }
    }

    private void sort(Comparable<T>[] a, Comparable<T>[] aux, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }

    public void sort(Comparable<T>[] a) {
        Comparable<T>[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length - 1);
    }

}
