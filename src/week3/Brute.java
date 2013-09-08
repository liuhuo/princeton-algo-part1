public class Brute {
    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);

        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
	Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
	    points[i] = p;
	    p.draw();
        }

	java.util.Arrays.sort(points);

	for (int i = 0 ; i < n; i++) {
	    for (int j = i + 1; j < n; j++) {
		for (int k = j + 1; k < n; k++) {
		    for (int m = k + 1; m < n; m++){
			Point p1 = points[i];
			Point p2 = points[j];
			Point p3 = points[k];
			Point p4 = points[m];

			double slope1 = p1.slopeTo(p2);
			double slope2 = p1.slopeTo(p3);
			double slope3 = p1.slopeTo(p4);
			if (slope1 == slope2 && slope1 == slope3 && slope2 == slope3) {
			    System.out.print(p1);
			    System.out.print(" -> ");
			    System.out.print(p2);
			    System.out.print(" -> ");
			    System.out.print(p3);
			    System.out.print(" -> ");
			    System.out.println(p4);
			    p1.drawTo(p4);
			}
		    }
		}
	    }
	}
	StdDraw.show(0);
    }
}
