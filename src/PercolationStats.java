/**
 * Created by Максим on 01.04.2015.
 */
public class PercolationStats {

    private int _n;

    private int _t;

    private double[] _result;

    public static final double DeviationMultiplayer = 1.96;

    /**
     * Perform T independent experiments on an N-by-N grid
     * @param N grid size
     * @param T runs count
     */
    public PercolationStats(int N, int T)  {
        _n = N;
        _t = T;
        _result = new double[N];

        for(int i =0; i< T; i++){
            doPercolationTest(i);
        }
    }

    /**
     * Runs single Percolation test
     * @param i test number
     */
    private void doPercolationTest(int i) {
        Percolation per = new Percolation(_n);

        int count = 0;

        while (!per.percolates()){
                openRandomSite(per);
                count ++;
        }

        _result[i] = count;
    }

    /**
     * Open one site
     * @param per Percolation object
     */
    private void openRandomSite(Percolation per){
        int i = StdRandom.uniform(1, _n);
        int j = StdRandom.uniform(1, _n);

        while(per.isOpen(i,j)){
            i = StdRandom.uniform(1, _n);
            j = StdRandom.uniform(1, _n);
        }
    }

    /**
     * Sample mean of percolation threshold
     * @return mean
     */
    public double mean(){
        return StdStats.mean(_result);
    }

    /**
     * Sample standard deviation of percolation threshold
     * @return deviation
     */
    public double stddev(){
        return StdStats.stddev(_result);
    }

    /**
     * Low  endpoint of 95% confidence interval
     * @return confidenceLo
     */
    public double confidenceLo(){
        return mean() - (DeviationMultiplayer * stddev()/Math.sqrt(_t));
    }

    /**
     * high endpoint of 95% confidence interval
     * @return confidenceHi
     */
    public double confidenceHi() {
        return mean() + (DeviationMultiplayer * stddev()/Math.sqrt(_t));
    }

    public static void main(String[] args){
//        if(args.length < 2) throw new IllegalArgumentException("Wrong input parametr count");
//
//        int n = Integer.parseInt(args[0]);
//        int t = Integer.parseInt(args[1]);

        int n = 20;
        int t = 20;

        if(n<=0 || t<=0) throw new IllegalArgumentException ("Wrong input arguments");

        StdOut.print("Simulation for N=" + n + " T=" + t+"\n");
        PercolationStats stat = new PercolationStats(n,t);
        StdOut.print("mean = " + stat.mean());
        StdOut.print("stddev = " + stat.stddev());
        StdOut.print("95% confidence interval = " + stat.confidenceLo() + ", " + stat.confidenceHi());
    }
}
