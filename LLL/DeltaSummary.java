public class DeltaSummary {
    private final double delta;
    private final double avgTime;
    private final double avgSwaps;
    private final double avgHadamar;
    private final double avgFVNorm;

    public DeltaSummary(double delta,double avgTime,double avgSwaps, double avgHadamar,double avgFVNorm) {
        this.delta = delta;
        this.avgTime =avgTime;
        this.avgSwaps=avgSwaps;
        this.avgHadamar= avgHadamar;
        this.avgFVNorm = avgFVNorm;
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

    public double getAvgHadamard() {
        return avgHadamar;
    }

    public double getAvgFirstVectorNorm() {
        return avgFVNorm;
    }
}
