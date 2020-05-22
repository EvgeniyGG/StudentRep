import java.util.concurrent.CountDownLatch;

public class SLAESolution extends Thread {

    private double [][]matrixA;
    private double []colB;
    private double []colX;
    private static final double EPS = 10e-5;
    private int changingRowNumber, baseRowNumber, threadNumber, numRowsForThisThread;
    private static CountDownLatch cdl;



    public SLAESolution(double[][] A, double[] B, double []X, int changingRowNumber, int baseRowNumber, int numRowsForThread)
    {
        this.matrixA = A;
        this.colB = B;
        this.colX = X;
        this.changingRowNumber = changingRowNumber;
        this.baseRowNumber = baseRowNumber;
        this.numRowsForThisThread = numRowsForThread;
    }


    public void run()
    {
        if(lastChangedRow != matrixA.length - 1)
        {
            double multCoef;
            cdl.countDown();
            for(int i = 0; i < numRowsForThisThread; i++)
            {
                multCoef = matrixA[changingRowNumber + i][baseRowNumber] / matrixA[baseRowNumber][baseRowNumber];
                for (int k = 0; k < matrixA[0].length; k++)
                    matrixA[changingRowNumber + i][k] -= multCoef * matrixA[baseRowNumber][k];
                colB[changingRowNumber + i] -= multCoef * colB[baseRowNumber];
            }

        }
    }

    public static double[] solveSLAE(double [][]A, double []B, double []X, int threadNumber) throws InterruptedException {
        if(checkCorrect(A, B, X))
        {
            ////FORWARD ELIMINATION
            if(threadNumber > A.length - 1)
                threadNumber = A.length - 1;

            cdl = new CountDownLatch(threadNumber);

            Thread []threads = new Thread[threadNumber];
            int numRowsForThread;
            int lastChangedRow;

            for(int i = 0; i < A.length - 1; i++)
            {
                lastChangedRow = i;
                for(int j = 0; j < threadNumber; j++)
                {
                    numRowsForThread = getNumRowsForThread(A.length, i, j, threadNumber, lastChangedRow);
                    threads[j] = new SLAESolution(A, B, X, lastChangedRow + 1, i, numRowsForThread);
                    threads[j].start();
                    lastChangedRow = i + j * numRowsForThread + 1;
                }
                cdl.await();
            }
            backSubstitution(A, B, X);
            return null;
        }

        System.out.println("Incorrect input");
        return null;
    }

    private static void backSubstitution(double [][]A, double []B, double []X)
    {
        double res = 0;
        for(int i = A.length - 1; i >= 0; i--)
        {
            for(int j = A.length - 1; j > i; j--)
                res += A[i][j] * X[j];
            res = (B[i] - res) / A[i][i];
            X[i] = res;
            res = 0;
        }
    }

    private static int getNumRowsForThread(int size, int baseRowNumber, int numOfStartedThreads, int threadNumber, int lastChangedRow)
    {
        if(size - baseRowNumber - 1 <= threadNumber)
            return 1;
        else
        {
            if(threadNumber - numOfStartedThreads == 1)
                return size - lastChangedRow - 1;
            else
            {
                return (size - baseRowNumber - 1) / (threadNumber);
            }
        }
    }
    private static boolean checkCorrect(double [][]A, double []B, double []X)
    {
        if(A.length != A[0].length || A.length != B.length || A.length != X.length)
            return false;
        else
            return true;
    }



}
