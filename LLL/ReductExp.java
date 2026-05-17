public class ReductExp {
    private final Matrix latticeFact = new Matrix();
    private final BasisShortener shortener = new BasisShortener();
    public DeltaSummary runForDelta(double delta, int matrixSize,int matrixCount, int minVal, int maxVal) {
        double totalT = 0.0;
        double totalS = 0.0;
        double totalH = 0.0;
        double totalFN = 0.0;
        for (int i = 0; i < matrixCount; i++) {
            double[][] matrix = latticeFact.createBasis(matrixSize, minVal, maxVal);
            ReductionStats stats = shortener.reduce(matrix, delta);
            totalT += stats.getExecutionTimeMillis();
            totalS += stats.getSwaps();
            totalH += stats.getHadamardRatio();
            totalFN += stats.getFirstBasisVectorNorm();
            System.out.println("delta = " + delta + ", size = " + matrixSize + ", matrix " + (i + 1) + "/" + matrixCount);
        }
        return new DeltaSummary(delta, totalT / matrixCount, totalS / matrixCount, totalH / matrixCount, totalFN / matrixCount);
    }

    public SizeSum runForSize(int matrixSize,double delta, int matrixCount, int minVal,int maxVal) {
        double totalTime = 0.0;
        double totalSwaps = 0.0;
        for (int i = 0;i < matrixCount; i++) {
            double[][] matrix=latticeFact.createBasis(matrixSize, minVal, maxVal);
            ReductionStats stats = shortener.reduce(matrix, delta);
            totalTime += stats.getExecutionTimeMillis();
            totalSwaps += stats.getSwaps();
            System.out.println("size = " + matrixSize + ", delta = " + delta + ", matrix " + (i + 1) + "/" + matrixCount);
        }
        return new SizeSum(matrixSize, delta, totalTime / matrixCount, totalSwaps / matrixCount);
    }
}