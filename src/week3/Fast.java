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
	detectDup(new double[]{0.1,0.1,0.2,0.2,0.2,0.4,0.4,0.4,0.4,0.5,0.6,0.6,0.6});
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
	Point[] points = new Point[n];
	Set<Double> visited = new HashSet<Double>();

        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
	    points[i] = p;
	    p.draw();
        }

	for (int i = 0; i < n; i++) {
	    java.util.Arrays.sort(points);
	    java.util.Arrays.sort(points,points[i].SLOPE_ORDER);

	    System.out.println("===============");
	    int k = 0;
	    while (k < points.length) {
	    	int j;
	    	for (j = k + 1; j < points.length; j++) {
	    	    double slope1 = points[j].slopeTo(points[0]);
	    	    double slope2 = points[k].slopeTo(points[0]);
	    	    if (slope1 != slope2) break;
	    	}
	    	if ( j - k >= 3) {

	    	    System.out.println(k + " to " + (j-1));
	    	    System.out.print(points[0]);

	    	    for (int m = k; m < j; m++) {
	    		System.out.print(" -> ");
	    		System.out.print(points[m]);
	    		//visited.add(points[m]);
	    	    }
		    points[0].drawTo(points[j-1]);
	    	    System.out.println(" " + visited.size());

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
