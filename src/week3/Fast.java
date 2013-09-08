import java.util.Comparator;

public class Fast {
    private static void detectDup(double[] array) {
	int i = 0;
	while (i < array.length) {
	    int j = i + 1;
	    while (j < array.length) {
		if (array[j] == array[i]) {
		    j++;
		}
		else {
		    break;
		}
	    }
	    if (j - i >= 3) {
		System.out.println(i +  " to " + (j-1));
	    }
	    i = j;
	}
    }

    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        //StdDraw.show(0);
	detectDup(new double[]{0.1,0.1,0.2,0.2,0.2,0.4,0.4,0.4,0.4,0.5,0.6,0.6,0.6});
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

	// java.util.Arrays.sort(points);

	for (int i = 0; i < n; i++) {
	    java.util.Arrays.sort(points);

	    Comparator<Point> cmp = points[i].SLOPE_ORDER;
	    java.util.Arrays.sort(points,cmp);



	    if (i == 2) break;
	}

	//StdDraw.show(0);
    }
}
