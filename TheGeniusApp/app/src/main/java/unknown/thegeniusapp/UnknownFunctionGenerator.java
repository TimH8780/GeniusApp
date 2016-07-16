package unknown.thegeniusapp;

import android.util.Log;

import java.util.Random;

/**
 *Created by Unknown on 7/14/2016.
 */
public class UnknownFunctionGenerator {

    //public int random_num_a, random_num_b, final_answer;

    private int MAX_FUNCTIONS = 3;
    private Random generator = new Random();
    private UnknownFunction random_function;

    public UnknownFunctionGenerator(){
        random_function = unknownFunctionGenerator();
    }

    private int addition(int a, int b){
        //For debugging
//        final_answer = random_num_a + random_num_b;
//        Log.d("Addition: ", Integer.toString(final_answer));
//
//        return random_num_a + random_num_b;

        Log.d("Addition: ", Integer.toString(a + b));
        return a + b;
    }

    private int multiplication(int a, int b){
        //For debugging
//        final_answer = random_num_a * random_num_b;
//        Log.d("Multiplication: ", Integer.toString(final_answer));
//
//        return random_num_a * random_num_b;

        Log.d("Multiplication: ", Integer.toString(a * b));
        return a * b;
    }

    private int subtraction(int a, int b){
        //For debugging
//        final_answer = Math.max(random_num_a, random_num_b) - Math.min(random_num_a, random_num_b);
//        Log.d("Subtraction: ", Integer.toString(final_answer));
//
//        return Math.max(random_num_a, random_num_b) - Math.min(random_num_a, random_num_b);

        Log.d("Subtraction: ", Integer.toString(Math.abs(a - b)));
        return Math.abs(a - b);
    }

    private int randomGenerator(){
        // Seems to work
        //return  generator.nextInt(MAX_FUNCTIONS + 1);

        //Seems to only increment based on time, but might work in real time
        return (int)(((long)System.currentTimeMillis()) % MAX_FUNCTIONS);
    }

    private UnknownFunction unknownFunctionGenerator(){

        int function_number = randomGenerator();
        return functionArray[function_number];
    }

    public int getResult(int a, int b){
        return random_function.calculate(a, b);
    }

    //Referenced http://stackoverflow.com/questions/4280727/java-creating-an-array-of-methods
    interface UnknownFunction{ int calculate(int a, int b); }

    private UnknownFunction[] functionArray = new UnknownFunction[]{

        new UnknownFunction() { public int calculate(int a, int b) { return addition(a, b); }}, //functionArray[0]
        new UnknownFunction() { public int calculate(int a, int b) { return subtraction(a, b); }}, //functionArray[1]
        new UnknownFunction() { public int calculate(int a, int b) { return multiplication(a, b); }}, //functionArray[2]
    };

}
