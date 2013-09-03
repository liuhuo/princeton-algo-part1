package exercises;

public class MyWeightedQuickUnion {
    private int[] id;
    private int[] sz;

    public MyWeightedQuickUnion(int n) {
	id = new int[n];
	sz = new int[n];
	for (int i = 0; i < n; i++) {
	    id[i] = i;
	    sz[i] = 1;
	}
    }

    public int find(int p) {
	while (p != id[p]) {
	    p = id[p];
	}
	return p;
    }

    public boolean connected(int p, int q) {
	return find(p) == find(q);
    }

    public void union(int p, int q) {
	int i = find(p);
	int j = find(q);
	if (i == j) return;

	if (sz[i] < sz[j]) {
	    id[i] = j;
	    sz[j] += sz[i];
	}
	else {
	    id[j] = id[i];
	    sz[i] += sz[j];
	}
    }

    private void print() {
	for (int i = 0 ; i < id.length; i++) {
	    System.out.print(id[i] + " ");
	}
	System.out.println();
    }
    public static void main(String[] args ) {
	MyWeightedQuickUnion uf = new MyWeightedQuickUnion(10);
	//1-4 3-2 8-0 4-7 8-9 1-6 7-5 0-3 7-0

	uf.union(1,4);
	uf.union(3,2);
	uf.union(8,0);
	uf.union(4,7);
	uf.union(8,9);
	uf.union(1,6);
	uf.union(7,5);
	uf.union(0,3);
	uf.union(7,0);
	uf.print();
    }
}
