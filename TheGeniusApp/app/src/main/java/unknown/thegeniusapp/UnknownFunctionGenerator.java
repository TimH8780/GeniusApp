package unknown.thegeniusapp;

import android.util.Log;

public class UnknownFunctionGenerator {

    //Referenced http://stackoverflow.com/questions/4280727/java-creating-an-array-of-methods
    public static final int MAX_FUNCTIONS = 7;

    private UnknownFunction random_function;
    private interface UnknownFunction{ int calculate(int a, int b); }

    public UnknownFunctionGenerator(){ random_function = unknownFunctionGenerator(); }

    private UnknownFunction unknownFunctionGenerator(){
        int function_number = RandomNumberGenerators.randomNumber(MAX_FUNCTIONS);
        return functionArray[function_number];
    }

    public int getResult(int a, int b){
        return random_function.calculate(a, b);
    }

    private UnknownFunction[] functionArray = new UnknownFunction[]{
            new UnknownFunction() { public int calculate(int a, int b) { return addition(a, b); }},         // functionArray[0]
            new UnknownFunction() { public int calculate(int a, int b) { return subtraction(a, b); }},      // functionArray[1]
            new UnknownFunction() { public int calculate(int a, int b) { return multiplication(a, b); }},   // functionArray[2]
            new UnknownFunction() { public int calculate(int a, int b) { return numberOfCircle(a, b); }},   // functionArray[3]
            new UnknownFunction() { public int calculate(int a, int b) { return numberOfOne(a, b); }},   // functionArray[4]
            new UnknownFunction() { public int calculate(int a, int b) { return binarySumUnder100(a, b); }},   // functionArray[5]
            new UnknownFunction() { public int calculate(int a, int b) { return binaryMinusUnder100(a, b); }},   // functionArray[6]
    };

    /* ------------------------------------- Lookup Table ------------------------------------- */
    int[] circleTable = {1, 0, 0, 0, 1, 0, 1, 0, 2, 1};

    /* ------------------------------------- Functions ------------------------------------- */
    private int addition(int a, int b){
        Log.d("Addition: ", Integer.toString(a + b));
        return a + b;
    }

    private int subtraction(int a, int b){
        Log.d("Subtraction: ", Integer.toString(Math.abs(a - b)));
        return Math.abs(a - b);
    }

    private int multiplication(int a, int b){
        Log.d("Multiplication: ", Integer.toString(a * b));
        return a * b;
    }

    private int numberOfCircle(int a, int b){
        int result = (a == 0)? 1: 0;
        result += (b == 0)? 1: 0;
        while(a != 0){
            result += circleTable[a % 10];
            a /= 10;
        }
        while(b != 0){
            result += circleTable[b % 10];
            b /= 10;
        }
        Log.d("numberOfCircle: ", String.valueOf(result));
        return result;
    }

    private int numberOfOne(int a, int b){
        int result = 0;
        while(a != 0){
            result += (a & 1);
            a >>>= 1;
        }
        while(b != 0){
            result += (a & 1);
            a >>>= 1;
        }
        Log.d("numberOfOne: ", String.valueOf(result));
        return result;
    }

    private int binarySumUnder100(int a, int b){
        a %= 1000;
        b %= 1000;
        int result = Integer.valueOf(Integer.toBinaryString(a + b));
        Log.d("binarySumUnder100: ", String.valueOf(result));
        return result;
    }

    private int binaryMinusUnder100(int a, int b){
        a %= 1000;
        b %= 1000;
        int large = Math.max(a, b);
        int small = large == a? b: a;
        int result = Integer.valueOf(Integer.toBinaryString(large - small));
        Log.d("binaryMinusUnder100: ", String.valueOf(result));
        return result;
    }

}
