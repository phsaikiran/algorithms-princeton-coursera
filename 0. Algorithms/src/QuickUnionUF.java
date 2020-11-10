public class QuickUnionUF {

    private final int[] arr;
    private final int[] size;

    public QuickUnionUF(int n) {
        arr = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i;
            size[i] = 1;
        }
    }

    private int root(int i) {
        while (i != arr[i]) {
            arr[i] = arr[arr[i]];
            i = arr[i];
        }
        return i;
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    public void union(int p, int q) {
        int pRoot = root(p);
        int qRoot = root(q);
        if (pRoot == qRoot) {
            return;
        }
        if (size[pRoot] < size[qRoot]) {
            arr[pRoot] = qRoot;
            size[qRoot] += size[pRoot];
        } else {
            arr[qRoot] = pRoot;
            size[pRoot] += size[qRoot];
        }
    }

}
