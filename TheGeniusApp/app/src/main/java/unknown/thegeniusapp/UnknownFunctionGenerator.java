package unknown.thegeniusapp;

import android.util.Log;

import java.util.Random;

public class UnknownFunctionGenerator {

    private static int MAX_FUNCTIONS = 3;
    private Random generator = new Random();
    private UnknownFunction random_function;
    private RandomNumberGenerators randomNumberGenerator = new RandomNumberGenerators();

    public UnknownFunctionGenerator(){ random_function = unknownFunctionGenerator(); }

    private int addition(int a, int b){

        Log.d("Addition: ", Integer.toString(a + b));
        return a + b;
    }

    private int multiplication(int a, int b){

        Log.d("Multiplication: ", Integer.toString(a * b));
        return a * b;
    }

    private int subtraction(int a, int b){

        Log.d("Subtraction: ", Integer.toString(Math.abs(a - b)));
        return Math.abs(a - b);
    }

//    private static int randomGenerator(int arg1){ return (int)(Math.random() * 1000.0) % MAX_FUNCTIONS; }

    private UnknownFunction unknownFunctionGenerator(){

        //int function_number = randomGenerator(MAX_FUNCTIONS);
        int function_number = randomNumberGenerator.randomNumber(MAX_FUNCTIONS);
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
