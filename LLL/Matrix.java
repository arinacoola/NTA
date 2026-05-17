import java.util.Random;
public class Matrix{
    private final Random rnd= new Random();

    public double[][] createBasis(int size,int minVal,int maxVal) {
        while (true) {
            double[][] matrix= new double[size][size];
            for (int i = 0;i < size;i++) {
                for (int j = 0;j < size;j++) {
                    matrix[i][j] = rnd.nextInt(maxVal-minVal+1)+minVal;
                }
            }
            if (isFullRank(matrix)) {
                return matrix;
            }
        }
    }

    private boolean isFullRank(double[][] matrix) {
        return Math.abs(determinant(matrix))>1e-9;
    }

    private double determinant(double[][] matrix) {
        int n=matrix.length;
        double[][] a = copy(matrix);
        double det = 1.0;
        for (int i = 0;i < n; i++) {
            int pivot =i;
            for (int row = i + 1;row < n;row++) {
                if (Math.abs(a[row][i])>Math.abs(a[pivot][i])) {
                    pivot = row;
                }
            }
            if (Math.abs(a[pivot][i])< 1e-12) {
                return 0.0;
            }
            if (pivot!= i) {
                double[] tmp = a[i];
                a[i] =a[pivot];
                a[pivot] = tmp;
                det *= -1;
            }
            det *=a[i][i];
            for(int row = i + 1;row < n;row++) {
                double factor = a[row][i]/a[i][i];
                for (int col= i;col< n; col++) {
                    a[row][col]-= factor * a[i][col];
                }
            }
        }
        return det;
    }

    private double[][] copy(double[][] matrix){
        double[][] res= new double[matrix.length][matrix[0].length];
        for (int i = 0;i < matrix.length;i++) {
            res[i] = matrix[i].clone();
        }
        return res;
    }
}
