public class SizeSum {
    private final int size;
    private final double delta;
    private final double avgTime;
    private final double avgSwaps;

    public SizeSum(int size,double delta,double avgTime,double avgSwaps) {
        this.size = size;
        this.delta=delta;
        this.avgTime = avgTime;
        this.avgSwaps =avgSwaps;
    }

    public int getSize() {
        return size;
    }

    public double getDelta() {
        return delta;
    }

    public double getAvgTime() {
        return avgTime;
    }

    public double getAvgSwaps() {
        return avgSwaps;
    }
}
