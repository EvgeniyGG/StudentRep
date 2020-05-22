import java.util.Random;

public class PerformanceResearch {
    public static void main(String []args) throws InterruptedException {
        int maxThreadNum = 256;

        for(int i = 1; i < maxThreadNum + 1; i *= 2)
        {
            System.out.println("Result for" + i + " threads " + execute(i) + " microseconds");
        }

    }

    private static double execute(int threadNum) throws InterruptedException {
        int size = 750;
        double [][]matrixA = new double[size][size];
        double[] colB = new double[size];
        double []X = new double[size];

        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                matrixA[i][j] = (double)(new Random().nextInt(100));
            }
            colB[i] = (double)(new Random().nextInt(100));
        }
        double res = 0;

        long nanos = System.nanoTime();
        SLAESolution.solveSLAE(matrixA, colB, X, threadNum);
        for(int i = 0; i < size; i++)
        {
            res += X[i] * matrixA[0][i];
        }
        return ((double)System.nanoTime() - nanos) / 1000;
    }

}
