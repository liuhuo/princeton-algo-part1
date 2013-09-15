public class Board {
    private int[][] blocks;
    private int dim;

    public Board(int[][] blocks) {
	this.dim = blocks.length;
	this.blocks = new int[dim][dim];
	for (int i = 0; i < dim; i++) {
	    for (int j = 0; j < dim; j++) {
		this.blocks[i][j] = blocks[i][j];
	    }
	}
    }

    public int dimension() {
	return this.dim;
    }

    public boolean isGoal() {
	boolean result = true;
	for (int i = 0; i < dim; i++) {
	    for (int j = 0; j < dim; j++) {
		if (i == dim - 1 && j == dim - 1) {
		    result = result && (blocks[i][j] == 0);
		}
		else {
		    result = result && (blocks[i][j] == (i * dim + j + 1));
		}
	    }
	}
	return result;
    }


    public int hamming() {
	int result = 0;
	for (int i = 0; i < dim; i++) {
	    for (int j = 0; j < dim; j++) {
		if (i == dim - 1 && j == dim - 1) {
		    if (blocks[i][j] != 0) {
			result++;
		    }
		}
		else {
		    if (blocks[i][j] != (i * dim + j + 1)) {
			result++;
		    }
		}
	    }
	}
	return result;
    }


    private void computeLinearIndex() {
	for (int i = 0; i < dim; i++) {
	    for (int j = 0; j < dim; j++) {
		System.out.print("(i,j) is " + i + " " + j +", linear idx is ");
		System.out.println((i * dim + j));
	    }
	}
    }

    public static void main(String[] args) {
	int[] i1 = new int[] {0,1,3};
	int[] i2 = new int[] {4,2,5};
	int[] i3 = new int[] {7,8,6};
	int[][] blocks = {i1,i2,i3};
	Board b = new Board(blocks);
	//b.computeLinearIndex();
	System.out.println(b.isGoal());
	blocks = new int[][] {new int[]{1,2,3}, new int[]{4,5,6}, new int[]{7,8,0}};
	//blocks = {{1,2,3}, {4,5,6}, {7,8,0}};
	b = new Board(blocks);
	System.out.println(b.isGoal());
    }
}
