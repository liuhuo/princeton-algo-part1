import java.util.List;
import java.util.ArrayList;

public class Board {
    private char[][] blocks;
    private int dim;
    private Coordinate empty;
    private int man = 0;
    private boolean first_time = true;


    public Board(int[][] blocks) {
	this.dim = blocks.length;
	this.blocks = new char[dim][dim];
	for (int i = 0; i < dim; i++) {
	    for (int j = 0; j < dim; j++) {
		if (blocks[i][j] == 0) empty = new Coordinate(i,j);
		this.blocks[i][j] = (char)blocks[i][j];
	    }
	}
    }

    private Board(char[][] blocks) {
	this.dim = blocks.length;
	this.blocks = new char[dim][dim];
	for (int i = 0; i < dim; i++) {
	    for (int j = 0; j < dim; j++) {
		if (blocks[i][j] == 0) empty = new Coordinate(i,j);
		this.blocks[i][j] = (char)blocks[i][j];
	    }
	}
    }



    public int dimension() {
	return this.dim;
    }

    public boolean isGoal() {
	return hamming() == 0;
    }

    public int hamming() {
	int result = 0;
	for (int i = 0; i < dim; i++) {
	    for (int j = 0; j < dim; j++) {
		if (blocks[i][j] == 0) continue;
		if (blocks[i][j] != i *dim + j + 1) result++;
	    }
	}
	return result;
    }

    public int manhattan() {
	if (!first_time) return man;
	int result = 0;
	for (int i = 0; i < dim; i++) {
	    for (int j = 0; j < dim; j++) {
		if (blocks[i][j] == 0) continue;
		Coordinate c = toCoordinate(blocks[i][j]);
		result += Math.abs(c.x - i) + Math.abs(c.y - j);
	    }
	}
	first_time = false;
	man = result;
	return result;
    }

    public Board twin() {
	Board result = new Board(this.blocks);
	if (result.empty.x == 0) {
	    result.swap(1,0,1,1);
	}
	else {
	    result.swap(0,0,0,1);
	}

	return result;
    }

    public boolean equals(Object y) {
	if (y == this) return true;
	if (y == null) return false;
	if (this.getClass() != y.getClass()) return false;
	Board that = (Board) y;
	boolean result = true;
	result = result && (this.dim == that.dim);
	for (int i = 0; i < dim; i++) {
	    for (int j = 0; j < dim; j++) {
		result = result && this.blocks[i][j] == that.blocks[i][j];
	    }
	}
	return result;
    }

    public Iterable<Board> neighbors() {
	List<Board> result = new ArrayList<Board>();

	Coordinate[] tmp = new Coordinate[] {
	    new Coordinate(empty.x - 1, empty.y),
	    new Coordinate(empty.x + 1, empty.y),
	    new Coordinate(empty.x, empty.y - 1),
	    new Coordinate(empty.x, empty.y + 1)
	};

	for (Coordinate c : tmp) {
	    if (c.x < dim && c.x >= 0 && c.y < dim && c.y >= 0) {
		Board b = new Board(this.blocks);
		b.swap(b.empty.x, b.empty.y, c.x,c.y);
		b.empty = new Coordinate(c.x,c.y);
		result.add(b);
	    }
	}
	return result;
    }

    public String toString() {
	StringBuilder s = new StringBuilder();
	s.append(dim + "\n");
	for (int i = 0; i < dim; i++) {
	    for (int j = 0; j < dim; j++) {
		s.append(String.format("%2d ", (int)blocks[i][j]));
	    }
	    s.append("\n");
	}
	return s.toString();
    }

    private void swap(int x1, int y1, int x2, int y2) {
	char tmp = blocks[x1][y1];
	blocks[x1][y1] = blocks[x2][y2];
	blocks[x2][y2] = tmp;
    }
    private Coordinate toCoordinate(int n) {
	int y = (n-1) % dim;
	int x = (n - y) / dim;
	return new Coordinate(x,y);
    }

    private void computeLinearIndex() {
	for (int i = 0; i < dim; i++) {
	    for (int j = 0; j < dim; j++) {
		System.out.print("(i,j) is " + i + " " + j +", linear idx is ");
		System.out.println((i * dim + j));
	    }
	}
    }

    // public static void main(String[] args) {
    // 	int[] i1 = new int[] {0,1,3};
    // 	int[] i2 = new int[] {4,2,5};
    // 	int[] i3 = new int[] {7,8,6};
    // 	int[][] blocks = {i1,i2,i3};
    // 	Board b = new Board(blocks);
    // 	//b.computeLinearIndex();
    // 	System.out.println(b.isGoal());
    // 	System.out.println(b.hamming());
    // 	System.out.println(b.manhattan());
    // 	blocks = new int[][] {new int[]{1,2,3}, new int[]{4,5,6}, new int[]{7,8,0}};
    // 	b = new Board(blocks);
    // 	System.out.println(b.isGoal());
    // 	System.out.println(b.hamming());
    // 	System.out.println(b.manhattan());
    // 	b = new Board(new int[][] {new int[]{8,1,3}, new int[]{4,0,2}, new int[]{7,6,5}});
    // 	System.out.println(b.isGoal());
    // 	System.out.println(b.hamming());
    // 	System.out.println(b.manhattan());
    // 	System.out.println(b.toCoordinate(8));
    // 	System.out.println(b.toCoordinate(7));
    // 	System.out.println(b.toCoordinate(6));
    // 	System.out.println(b.toCoordinate(5));
    // 	System.out.println(b.toCoordinate(4));
    // 	System.out.println(b.toCoordinate(3));
    // 	System.out.println(b.toCoordinate(2));
    // 	System.out.println(b.toCoordinate(1));
    // 	System.out.println(b);
    // 	System.out.println(b.twin());
    // 	System.out.println("=---------------");
    // 	b = new Board(new int[][] {new int[]{1,0,3}, new int[]{4,2,5}, new int[]{7,8,6}});
    // 	for (Board tmp : b.neighbors()) {
    // 	    System.out.println(tmp);
    // 	}
    // }
}

class Coordinate {
    public int x;
    public int y;

    public Coordinate(int x, int y) {
	this.x = x;
	this.y = y;
    }

    @Override
    public String toString() {
	return "(" + x + " " + y +")";
    }
}
