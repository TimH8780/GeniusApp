package unknown.thegeniusapp;

import android.util.Log;

import java.util.Random;

/**
 * Created by Unknown on 7/14/2016.
 */
public class UnknownFunctionGenerator {

    private int maxFunctions = 100;
    private Random generator = new Random();

    int RandomGenerator(){

        return  generator.nextInt(maxFunctions + 1);
    }
}
