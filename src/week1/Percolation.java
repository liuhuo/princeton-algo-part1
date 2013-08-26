public class Percolation {
    private WeightedQuickUnionUF uf1;
    private WeightedQuickUnionUF uf2;
    private boolean[][] sites;
    private int size;

    public Percolation(int n) {
	this.size = n;
	this.sites = new boolean[n+1][n+1];
	this.uf1 = new WeightedQuickUnionUF(n*n+2);
	this.uf2 = new WeightedQuickUnionUF(n*n+1);
    }

    public void open(int i , int j) {
	checkRange(i,true);
	checkRange(j,true);
	sites[i][j] = true;


	if (i == 1) {
	    uf1.union(0,convertCoordinate(i,j));
	    uf2.union(0,convertCoordinate(i,j));
	}
	if (i == size) {
	    uf1.union(size*size+1, convertCoordinate(i,j));
	}

	Coordinate[] neighbors = new Coordinate[] {
	    new Coordinate(i-1, j),
	    new Coordinate(i+1, j),
	    new Coordinate(i, j-1),
	    new Coordinate(i, j+1)
	};
	for (Coordinate tmp : neighbors) {
	    if (checkRange(tmp.x, false) &&
		checkRange(tmp.y, false) &&
		sites[tmp.x][tmp.y]) {
		uf1.union(convertCoordinate(i,j), convertCoordinate(tmp.x,tmp.y));
		uf2.union(convertCoordinate(i,j), convertCoordinate(tmp.x,tmp.y));
	    }
	}
    }

    public boolean isOpen(int i, int j) {
	checkRange(i, true);
	checkRange(j, true);
	return sites[i][j];
    }

    public boolean isFull(int i, int j) {
	checkRange(i, true);
	checkRange(j, true);
	return uf2.connected(0, convertCoordinate(i,j));
    }

    public boolean percolates() {
	return uf1.connected(0, size * size + 1);
    }

    private int convertCoordinate(int i, int j) {
	return (i - 1) * size + j;
    }

    private boolean checkRange(int i, boolean throwIt) {
	if (throwIt && (i <= 0 || i > size))
	    throw new IndexOutOfBoundsException("index out of range");
	else if (i > 0 && i <= size)
	    return true;
	else
	    return false;
    }
}

class Coordinate {
    public int x;
    public int y;

    public Coordinate(int x, int y) {
	this.x = x;
	this.y = y;
    }
}
