package unknown.thegeniusapp;

import android.util.Log;

import java.util.Random;

/**
 * Created by Unknown on 7/14/2016.
 */
public class UnknownFunctionGenerator {

    public int random_num_a = 2, random_num_b = 3, final_answer;

    private int MAX_FUNCTIONS = 3;
    private Random generator = new Random();

    int addition(){
        //For debugging
        final_answer = random_num_a + random_num_b;
        Log.d("Addition: ", Integer.toString(final_answer));

        return random_num_a + random_num_b;
    }

    int multiplication(){
        //For debugging
        final_answer = random_num_a * random_num_b;
        Log.d("Multiplication: ", Integer.toString(final_answer));

        return random_num_a * random_num_b;
    }

    int subtraction(){
        //For debugging
        final_answer = Math.max(random_num_a, random_num_b) - Math.min(random_num_a, random_num_b);
        Log.d("Subtraction: ", Integer.toString(final_answer));

        return Math.max(random_num_a, random_num_b) - Math.min(random_num_a, random_num_b);
    }

    int randomGenerator(){
        // Seems to work
        //return  generator.nextInt(MAX_FUNCTIONS + 1);

        //Seems to only increment based on time, but might work in real time
        return (int)(((long)System.currentTimeMillis()) % MAX_FUNCTIONS);
    }

    long unknownFunctionGenerator(){

        int function_number = randomGenerator();
        return functionArray[function_number].calculate();
    }

    //Referenced http://stackoverflow.com/questions/4280727/java-creating-an-array-of-methods
    interface UnknownFunction{ int calculate(); }

    public UnknownFunction[] functionArray = new UnknownFunction[]{

        new UnknownFunction() { public int calculate() { return addition(); }}, //functionArray[0]
        new UnknownFunction() { public int calculate() { return subtraction(); }}, //functionArray[1]
        new UnknownFunction() { public int calculate() { return multiplication(); }}, //functionArray[2]
    };

}
