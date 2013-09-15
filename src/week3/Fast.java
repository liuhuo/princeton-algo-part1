import java.util.Comparator;
import java.util.Set;
import java.util.HashSet;


public class Fast {
    private static void detectDup(double[] array) {
	int i = 0;
	while (i < array.length) {
	    int j;
	    for (j = i+1; j < array.length; j++)
		if (array[j] != array[i]) break;
	    if (j - i >= 3) {
		System.out.println(i +  " to " + (j-1));
	    }
	    i = j;
	}
    }

    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
	//detectDup(new double[]{0.1,0.1,0.2,0.2,0.2,0.4,0.4,0.4,0.4,0.5,0.6,0.6,0.6});
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

	for (int i = 0; i < n; i++) {
	    java.util.Arrays.sort(points);
	    Point reference = points[i];
	    java.util.Arrays.sort(points,reference.SLOPE_ORDER);

	    // double currSlope = points[0].slopeTo(reference);
	    // int streak = 0;
	    // StringBuilder line = new StringBuilder(reference.toString());
	    // for (int j = 0; j <= n; j++) {
	    // 	if (j == n || currSlope != points[j].slopeTo(reference)) {
	    // 	    if (streak >= 3 && reference.compareTo(points[j-streak]) < 0) {
	    // 		System.out.println(line);
	    // 		reference.drawTo(points[j-1]);
	    // 	    }
	    // 	    if (j == n) break;
	    // 	    streak = 1;
	    // 	    currSlope = points[j].slopeTo(reference);
	    // 	    line = new StringBuilder(reference.toString());
	    // 	}
	    // 	else {
	    // 	    streak++;
	    // 	}
	    // 	line.append(" -> ");
	    // 	line.append(points[j].toString());
	    // }


	    int k = 0;
	    while (k < points.length) {
	    	int j;
	    	for (j = k + 1; j < points.length; j++) {
	    	    double slope1 = points[j].slopeTo(points[0]);
	    	    double slope2 = points[k].slopeTo(points[0]);
	    	    if (slope1 != slope2) break;
	    	}
	    	if ( j - k >= 3) {
	    	    if (points[0].compareTo(points[k]) < 0 ) {
			System.out.print(points[0]);
			for (int m = k; m < j; m++) {
			    System.out.print(" -> ");
			    System.out.print(points[m]);
			}
			System.out.println();
			points[0].drawTo(points[j-1]);
		    }
	    	}
	    	k = j;
	    }
	}
	StdDraw.show(0);
    }
}


// while (j < array.length) {
// 	if (array[j] == array[i]) {
// 	    j++;
// 	}
// 	else {
// 	    break;
// 	}
//}
