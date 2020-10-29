public class QuickFindUF {

    private final int[] arr;

    public QuickFindUF(int n) {
        arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i;
        }
    }

    public boolean connected(int p, int q) {
        return arr[p] == arr[q];
    }

    public void union(int p, int q) {
        int pid = arr[p];
        int qid = arr[q];

        int n = arr.length;
        for (int i = 0; i < n; i++) {
            if (arr[i] == pid) {
                arr[i] = qid;
            }
        }
    }

}
