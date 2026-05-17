import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
public class ResultWr{
    public void writeSummary(String file, Iterable<DeltaSummary> res) throws IOException {
        try (PrintWriter wr = new PrintWriter(new FileWriter(file))) {
            wr.println("delta,average_time_ms,average_swaps,average_hadamard,average_first_vector_norm");
            for (DeltaSummary r : res) {
                wr.printf("%.2f,%.6f,%.4f,%.10f,%.6f%n",  r.getDelta(), r.getAvgTime(), r.getAvgSwaps(), r.getAvgHadamard(), r.getAvgFirstVectorNorm()
                );
            }
        }
    }

    public void writeSizeSummary(String file, Iterable<SizeSum> res) throws IOException {
        try (PrintWriter wr = new PrintWriter(new FileWriter(file))) {
            wr.println("size,delta,average_time_ms,average_swaps");
            for (SizeSum r : res) {
                wr.printf("%d,%.2f,%.6f,%.4f%n", r.getSize(), r.getDelta(), r.getAvgTime(), r.getAvgSwaps()
                );
            }
        }
    }

    public void writeMatrixRes(String file,int matrixId,double delta,double[][] matrix, ReductionStats stats) throws IOException {
        try (PrintWriter wr = new PrintWriter(new FileWriter(file, true))) {
            wr.println("matrix_id="+ matrixId);
            wr.println("delta=" + delta);
            wr.println("time_ms=" + stats.getExecutionTimeMillis());
            wr.println("swaps=" + stats.getSwaps());
            wr.println("hadamard="+stats.getHadamardRatio());
            wr.println("first_vector_norm=" + stats.getFirstBasisVectorNorm());
            wr.println("matrix:");
            for (double[] row :matrix){
                for (int j=0; j < row.length; j++) {
                    wr.print((int) row[j]);
                    if (j + 1< row.length) {
                        wr.print(" ");
                    }
                }
                wr.println();
            }
            wr.println("-----");
        }
    }
}