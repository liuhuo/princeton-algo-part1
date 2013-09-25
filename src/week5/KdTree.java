import java.util.Comparator;

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

	public Node(Point2D p, RectHV rect) {
	    this.p = p;
	    this.rect = rect;
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
	root = put(root, p, VERTICAL, null, 0);
    }

    public void draw() {
	RectHV frame = root.rect;
	frame.draw();
	traverseAndDraw(root,VERTICAL);
    }

    public Iterable<Point2D> range(RectHV query) {
	Queue<Point2D> result = new Queue<Point2D>();
	rangeHelper(root,query,result);
	return result;
    }

    public boolean contains(Point2D p) {
	Node x = root;
	int line = VERTICAL;
	while (x != null) {
	    int cmp = p.compareTo(x.p);
	    if (cmp == 0) return true;
	    Comparator<Point2D> c = (line == VERTICAL) ? Point2D.X_ORDER : Point2D.Y_ORDER;
	    cmp = c.compare(p,x.p);
	    if (cmp < 0) x = x.lb;
	    else  x=x.rt;
	    line = 1 - line;
	}
	return false;
    }

    public Point2D nearest(Point2D query) {
	if (root == null) return null;
	Point2D[] result = new Point2D[]{root.p};
	neighborHelper(root,query,Double.POSITIVE_INFINITY,result,VERTICAL);
	return result[0];
    }

    // private boolean searchHelper(Node curr, Node query, int orientation) {

    // }
    private double neighborHelper(Node curr, Point2D query, double best, Point2D[] result, int orientation) {
	if (curr == null) return best;

	double currDist = query.distanceSquaredTo(curr.p);
	if (currDist < best) {
	    best = currDist;
	    result[0] = curr.p;
	}

	RectHV currRect = curr.rect;
	double toRectDist = curr.rect.distanceSquaredTo(query);

	if (best < toRectDist) {

	}
	else {
	    Comparator<Point2D> c = (orientation == VERTICAL) ? Point2D.X_ORDER : Point2D.Y_ORDER;
	    Node firstSearchTree, secondSearchTree;
	    int cmp = c.compare(query,curr.p);
	    firstSearchTree = (cmp < 0) ? curr.lb : curr.rt;
	    secondSearchTree = (firstSearchTree == curr.lb) ? curr.rt : curr.lb;
	    best = neighborHelper(firstSearchTree,query,best,result, 1-orientation);
	    best = neighborHelper(secondSearchTree,query,best,result, 1-orientation);
	}
	return best;
    }

    private void rangeHelper(Node curr, RectHV query, Queue<Point2D> result) {
	if (curr == null) return;
	if (query.contains(curr.p)) {
	    result.enqueue(curr.p);
	}
	if (curr.lb != null && curr.lb.rect.intersects(query)) {
	    rangeHelper(curr.lb, query, result);
	}
	if (curr.rt != null && curr.rt.rect.intersects(query)) {
	    rangeHelper(curr.rt, query, result);
	}
    }

    private Node put(Node x, Point2D p, int orientation, Node parent, int pos) {
	if (x == null) {
	    if (parent == null) {
		size++;
		return new Node(p, new RectHV(0.0,0.0,1.0,1.0));
	    }
	    RectHV parentRect = parent.rect;
	    double xmin = parentRect.xmin();
	    double ymin = parentRect.ymin();
	    double xmax = parentRect.xmax();
	    double ymax = parentRect.ymax();
	    double parentX = parent.p.x();
	    double parentY = parent.p.y();
	    if (orientation == VERTICAL) {
		if (pos < 0) {
		    ymax = parentY;
		}
		else {
		    ymin = parentY;
		}
	    }
	    else {
		if (pos < 0) {
		    xmax = parentX;
		}
		else {
		    xmin = parentX;
		}
	    }
	    size++;
	    return new Node(p, new RectHV(xmin,ymin,xmax,ymax));
	}

	Point2D curr = x.p;
	if (curr.equals(p)) return x;
	Comparator<Point2D> c = (orientation == VERTICAL) ? Point2D.X_ORDER : Point2D.Y_ORDER;
	int cmp = c.compare(p,curr);
	if (cmp < 0)
	    x.lb = put(x.lb, p, 1 - orientation, x, cmp);
	else
	    x.rt = put(x.rt, p, 1 - orientation, x, cmp);
	return x;
    }

    private void traverseAndDraw(Node curr, int orientation) {
	if (curr == null) return;
	StdDraw.setPenRadius(.02);
	StdDraw.setPenColor(StdDraw.BLACK);
	curr.p.draw();
	StdDraw.setPenRadius();
	if (orientation == VERTICAL) {
	    StdDraw.setPenColor(StdDraw.RED);
	    RectHV myRect = curr.rect;
	    Point2D start = new Point2D(curr.p.x(), myRect.ymax());
	    Point2D end = new Point2D(curr.p.x(), myRect.ymin());
	    start.drawTo(end);
	}
	else {
	    StdDraw.setPenColor(StdDraw.BLUE);
	    RectHV myRect = curr.rect;
	    Point2D start = new Point2D(myRect.xmin() ,curr.p.y());
	    Point2D end = new Point2D(myRect.xmax() ,curr.p.y());
	    start.drawTo(end);
	}
	traverseAndDraw(curr.lb, 1 - orientation);
	traverseAndDraw(curr.rt, 1 - orientation);
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
	t.draw();
	System.out.println();
	System.out.println(t.contains(new Point2D(0.7,0.2)));
	System.out.println(t.contains(new Point2D(0.5,0.4)));
	System.out.println(t.contains(new Point2D(0.2,0.3)));
	System.out.println(t.contains(new Point2D(0.4,0.7)));
	System.out.println(t.contains(new Point2D(0.9,0.6)));
	System.out.println("--------------------------------");
	System.out.println(t.contains(new Point2D(0.7,0.3)));
    }
}
