package MathOperations;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.linear.RealVector;

public class VectorOperations {

    public static double dotProduct(RealVector vec1, RealVector vec2)
    {
        if(vec1.getDimension() == 0 || vec2.getDimension() == 0)
            return 0.0;
        if(vec1.getDimension() != vec2.getDimension())
            throw new IllegalArgumentException("Dimensions should be the same");
        else
        {
            double result = 0.0;
            for(int i = 0; i < vec1.getDimension(); i++)
            {
                result += vec1.getEntry(i) * vec2.getEntry(i);
            }
            return result;
        }
    }
}
