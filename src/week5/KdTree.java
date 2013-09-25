public class KdTree {
    private static int HORIZONTAL = 0;
    private static int VERTICAL = 1;
    private int size;
    private Node root;

    private static class Node {
	private Point2D p;
	private RectHV rect;
	private Node lb;
	private Node rt;

	public Node(Point2D p) {
	    this.p = p;
	    this.lb = null;
	    this.rt = null;
	}

	@Override
	public String toString() {
	    return p.toString();
	}
    }

    public KdTree() {
	this.root = null;
	this.size = 0;
    }

    public boolean isEmpty() {
	return this.size == 0;
    }

    public int size() {
	return this.size;
    }

    public void insert(Point2D p) {
	root = put(root, p, VERTICAL);
    }

    private Node put(Node x, Point2D p, int orientation) {

	if (x == null) {
	    return new Node(p);
	}
	Point2D curr = x.p;
	double cmp = (orientation == VERTICAL) ?  p.x() - curr.x()  : p.y() - curr.y();
	if (cmp < 0) {
	    x.lb = put(x.lb, p, 1 - orientation);
	}
	else {
	    x.rt = put(x.rt, p , 1 -orientation);
	}
	return x;
    }

    private void levelOrderTrav() {
	Queue<Node> q = new Queue<Node>();
	q.enqueue(root);
	while (!q.isEmpty()) {
	    Node curr = q.dequeue();
	    if (curr == null) continue;
	    System.out.print(curr.p);
	    q.enqueue(curr.lb);
	    q.enqueue(curr.rt);
	}
    }

    public static void main(String[] args) {
	KdTree t = new KdTree();
	t.insert(new Point2D(0.7, 0.2));
	t.insert(new Point2D(0.5, 0.4));
	t.insert(new Point2D(0.2, 0.3));
	t.insert(new Point2D(0.4, 0.7));
	t.insert(new Point2D(0.9, 0.6));
	t.levelOrderTrav();
    }
}
