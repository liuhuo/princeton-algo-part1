public class PercolationStats {
    private double[] results;
    private int n, t;

    public PercolationStats(int n, int t) {
	if (n <= 0 || t <= 0)
	    throw new IllegalArgumentException();
	this.n = n;
	this.t = t;
	results = new double[t];
	for (int i = 0; i < t; i++) {
	    results[i] = trial(n);
	}
    }

    public double mean() {
	return StdStats.mean(results);
    }

    public double stddev() {
	return StdStats.stddev(results);
    }

    public double confidenceLo() {
	return mean() - 1.96 * stddev() / Math.sqrt(t);
    }

    public double confidenceHi() {
	return mean() + 1.96 * stddev() / Math.sqrt(t);
    }

    private double trial(int n) {
	Percolation perc = new Percolation(n);
	int openCount = 0;
	while (!perc.percolates()) {
	    int x = StdRandom.uniform(1,n+1);
	    int y = StdRandom.uniform(1,n+1);
	    if (!perc.isOpen(x,y)) {
		perc.open(x,y);
		openCount++;
	    }
	}

	return openCount * 1.0 / (n*n);
    }

    public static void main(String[] args) {
	int n = Integer.parseInt(args[0]);
	int t = Integer.parseInt(args[1]);

	PercolationStats stats = new PercolationStats(n,t);
	System.out.println("mean                    = " + stats.mean());
	System.out.println("stddev                  = " + stats.stddev());
	double lo = stats.confidenceLo();
	double hi = stats.confidenceHi();
	System.out.println("95% confidence interval = " + lo + ", " + hi);
    }
}
