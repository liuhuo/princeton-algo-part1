import java.util.Comparator;


public class Solver {
    private boolean solvable;
    private Stack<Board> solutions;
    private Node dest;

    public Solver(Board initial) {
	MinPQ<Node> frontier = new MinPQ<Node>(new NodeComparator());
	MinPQ<Node> alter = new MinPQ<Node>(new NodeComparator());
	frontier.insert(new Node(initial, 0 , null));
	alter.insert(new Node(initial.twin(), 0 , null));

	while (true) {
	    Node curr = frontier.delMin();
	    Node curr_alter = alter.delMin();
	    if (curr.b.isGoal()) {
		solvable = true;
		dest = curr;
		break;
	    }
	    if (curr_alter.b.isGoal()) {
		solvable = false;
		break;
	    }

	    for (Board nb : curr.b.neighbors()) {
		if (curr.prev == null) {
		    frontier.insert(new Node(nb, curr.moves + 1, curr));
		}
		else if (!nb.equals(curr.prev.b)) {
		    frontier.insert(new Node(nb, curr.moves + 1, curr));
		}
	    }
	    for (Board nb : curr_alter.b.neighbors()) {
		if (curr_alter.prev == null) {
		    alter.insert(new Node(nb, curr_alter.moves + 1, curr_alter));
		}
		else if (!nb.equals(curr_alter.prev.b)) {
		    alter.insert(new Node(nb, curr_alter.moves + 1, curr_alter));
		}
	    }
	}
    }

    public boolean isSolvable() {
	return solvable;
    }

    public int moves() {
	if (isSolvable()) return dest.moves;
	else return -1;
    }

    public Iterable<Board> solution() {
	if (!isSolvable()) return null;
	solutions = new Stack<Board>();
	Node curr = dest;
	while (curr != null) {
	    solutions.push(curr.b);
	    curr = curr.prev;
	}
	return solutions;
    }

    public static void main(String[] args) {
	In in = new In(args[0]);
	int N = in.readInt();
	int[][] blocks = new int[N][N];
	for (int i = 0; i < N; i++)
	    for (int j = 0; j < N; j++)
		blocks[i][j] = in.readInt();
	Board initial = new Board(blocks);

	// solve the puzzle
	Solver solver = new Solver(initial);

	// print solution to standard output
	if (!solver.isSolvable())
	    StdOut.println("No solution possible");
	else {
	    StdOut.println("Minimum number of moves = " + solver.moves());
	    for (Board board : solver.solution())
		StdOut.println(board);
	}
    }
}




class Node {
    Board b;
    int moves;
    Node prev;

    public Node(Board b, int moves, Node prev) {
	this.b = b;
	this.moves = moves;
	this.prev = prev;
    }
}

class NodeComparator implements Comparator<Node> {
    public int compare(Node x, Node y) {
	return (x.b.manhattan() + x.moves) - (y.b.manhattan() + y.moves);
    }
}
