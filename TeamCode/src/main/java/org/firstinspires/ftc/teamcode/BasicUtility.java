package org.firstinspires.ftc.teamcode;

public class BasicUtility {


    public BasicUtility(){}
    /**
     * Checks if a given value meets or surpasses a given threshold
     * Returns 0 if the value fails to surpass the threshold
     *
     * @param value   The given value
     * @param minimum The minimum requirement
     * @return the value if it surpasses the minimum, 0 otherwise
     */
    public Double thresholdCheck(Double value, Double minimum) {
        if (value > minimum) {
            return value;
        }
        return 0.0;


    }

    /**
     * Checks if a given value is below a maximum value
     * Returns the maximum if the value is above the maximum
     *
     * @param value   The given value
     * @param maximum The maximum value
     * @return the value if it is below the maximum, 0 otherwise
     */
    public Double maximumCheck(Double value, Double maximum) {
        if (value < maximum) {
            return value;
        }
        return 0.0;
    }

    /**
     * Checks if a given value is below a maximum value
     * Returns the maximum if the value is above the maximum
     * @param value The given value
     * @param maximum The maximum value
     * @return the value if it is below the maximum, the maximum otherwise
     */
    public Double maximumDefault(Double value, Double maximum) {
        if(value<maximum){
            return value;
        }
        return maximum;
    }

    /**
     * Checks if a given value is below a maximum value
     * Returns an error value if it is above the maximum
     * @param value The given value
     * @param maximum The maximum value
     * @param error The error value
     * @return the value if it is below the maximum, the error otherwise
     */
    public Double maximumDefault(Double value, Double maximum, Double error) {
        if(value<maximum){
            return value;
        }
        return error;
    }

    /**
     * Checks if a given value is within a maximum and minimum value
     * Returns 0 if it is not within the constraints
     * @param value The given value
     * @param maximum The maximum value
     * @param minimum The minimum value
     * @return The value if it is within the constraints, 0 otherwise
     */
    public Double constraintCheck(Double value, Double maximum, Double minimum) {
        if(value>=maximum){
            return 0.0;
        }else if(value <= minimum){
            return 0.0;
        }
        return value;
    }

    /**
     * Checks if a given value is within a maximum and minimum value
     * Returns the maximum if the value is too large, the minimum if it is too small
     * @param value The given value
     * @param maximum The maximum value
     * @param minimum The minimum value
     * @return The value if it is within the constraints, the respective extrema if not
     */
    public Double constraintDefault(Double value, Double maximum, Double minimum){
        if(value>=maximum){
            return maximum;
        }else if(value<= minimum){
            return minimum;
        }
        return value;

    }

    /**
     * Checks if a given value is within a maximum and minimum value
     * Returns an error value if it is not within the constraints
     * @param value The given value
     * @param maximum The maximum value
     * @param minimum The minimum value
     * @param error The error value
     * @return The value if it is within constraints, an error otherwise
     */
    public Double constraintDefault(Double value, Double maximum, Double minimum, Double error){
        if(value>=maximum || value<= minimum){
            return error;
        }
        return value;
    }

    /**
     * Checks if a given value is within a maximum and minimum value
     * Returns a minimum or maximum error if it fails to meet requirements
     * @param value The given value
     * @param maximum The maximum value
     * @param minimum The minimum value
     * @param maxError The maximum value error
     * @param minError The minimum value error
     * @return The value if it is within constraints, the max error if it is overly large, and the min error otherwise
     */
    public Double constraintDefault(Double value, Double maximum, Double minimum, Double maxError, Double minError){
        if(value>= maximum){
            return maxError;
        }else if(value <= minimum){
            return minError;
        }
        return value;
    }

}
