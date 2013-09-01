package exercises;



public class  MyQuickFind {
    private int[] id;

    public MyQuickFind(int n) {
	id = new int[n];
	for (int i = 0; i < n; i++) {
	    id[i] = i;
	}
    }

    public boolean connected(int p, int q) {
	return id[p] == id[q];
    }

    public void union(int p, int q) {
	if (connected(p,q)) return;
	int pid = id[p];
	int qid = id[q];
	for (int i = 0; i < id.length; i++) {
	    if (id[i] == pid) {
		id[i] = qid;
	    }
	}
    }

    private void print() {
	for (int i = 0; i < id.length; i++) {
	    System.out.print(id[i] + " ");
	}
	System.out.println();
    }

    public static void main(String[] args) {
	MyQuickFind uf = new MyQuickFind(10);
	//7-0 3-5 8-0 4-1 4-6 9-6

	uf.union(7,0);
	uf.union(3,5);
	uf.union(8,0);
	uf.union(4,1);
	uf.union(4,6);
	uf.union(9,6);
	uf.print();
    }
}
