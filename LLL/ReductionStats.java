public class ReductionStats {
    private final double[][] basis;
    private final long execTime;
    private final int swaps;
    private final double hadamar;
    private final double firstBVNorm;
    public ReductionStats(double[][] basis,long executionTime,int swaps, double hadamardRatio, double firstBasisVectorNorm){
        this.basis=basis;
        this.execTime =executionTime;
        this.swaps=swaps;
        this.hadamar=hadamardRatio;
        this.firstBVNorm =firstBasisVectorNorm;
    }

    public double[][] getBasis() {
        return basis;
    }

    public long getExecutionTime() {
        return execTime;
    }

    public double getExecutionTimeMillis() {
        return execTime/1000000.0;
    }

    public int getSwaps() {
        return swaps;
    }

    public double getHadamardRatio(){
        return hadamar;
    }

    public double getFirstBasisVectorNorm(){
        return firstBVNorm;
    }
}
