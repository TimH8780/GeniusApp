package unknown.logica.Module;

import android.content.SharedPreferences;

import static unknown.logica.Module.HelperFunction.*;
import static unknown.logica.Settings.*;

public class UnknownFunctionGenerator {

    //Referenced http://stackoverflow.com/questions/4280727/java-creating-an-array-of-methods
    public static final int MAX_FUNCTIONS = 22;
    public static final int MIN_RANDOM_INDEX = 6;
    public static final int MAX_RANDOM_INDEX = 8;
    private static final int HARD_START_INDEX = 14;

    private UnknownFunction random_function;
    private int functionIndex;
    private interface UnknownFunction{ long calculate(int a, int b, int random); }

    public UnknownFunctionGenerator(SharedPreferences sp){ random_function = unknownFunctionGenerator(sp); }

    public UnknownFunctionGenerator(int index){
        random_function = functionArray[index];
    }

    private UnknownFunction unknownFunctionGenerator(SharedPreferences sp){
        int difficulty = sp.getInt(DIFFICULTY_VALUE, MIXED);

        do {
            functionIndex = RandomNumberGenerators.randomNumber(MAX_FUNCTIONS);
        } while(!isValid(difficulty, functionIndex));

        return functionArray[functionIndex];
    }

    private boolean isValid(int difficulty, int index){
        return difficulty == MIXED || (difficulty == EASY && index < HARD_START_INDEX) || (difficulty == HARD && index >= HARD_START_INDEX);
    }

    public long getResult(int a, int b, int random){
        return random_function.calculate(a, b, random);
    }

    public int getFunctionIndex(){
        return functionIndex + 1;
    }

    private UnknownFunction[] functionArray = new UnknownFunction[]{
            /* ------------------ Easy ------------------ */
            new UnknownFunction() { public long calculate(int a, int b, int random) { return addition(a, b); }},                      // functionArray[0]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return subtraction(a, b); }},                   // functionArray[1]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return multiplication(a, b); }},                // functionArray[2]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return numberOfClosedArea(a, b); }},            // functionArray[3]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return reverseLarger(a, b); }},                 // functionArray[4]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return reverseSmaller(a, b); }},                // functionArray[5]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return xCounter(a, b, random); }},              // functionArray[6]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return isContain(a, b, random); }},             // functionArray[7]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return isBothContain(a, b, random); }},         // functionArray[8]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return greatestCommonFactor(a, b); }},          // functionArray[9]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return leastCommonMultiple(a, b); }},           // functionArray[10]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return totalDigitCount(a, b); }},               // functionArray[11]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return digitDifference(a, b); }},               // functionArray[12]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return printSumOfDigit(a, b); }},               // functionArray[13]

            /* ------------------ Hard ------------------ */
            new UnknownFunction() { public long calculate(int a, int b, int random) { return numberOfBinaryOne(a, b); }},             // functionArray[+ 1]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return binarySumUnder10(a, b); }},              // functionArray[+ 2]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return binaryMinusUnder10(a, b); }},            // functionArray[+ 3]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return reverseSum(a, b); }},                    // functionArray[+ 4]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return reverseSub(a, b); }},                    // functionArray[+ 5]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return integerCounter(a, b); }},                // functionArray[+ 6]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return digitSum(a, b); }},                      // functionArray[+ 7]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return digitSub(a, b); }},                      // functionArray[+ 8]
    };

    /* ------------------------------------- Functions (Easy)------------------------------------- */
    private long addition(int a, int b){
        return a + b;
    }

    private long subtraction(int a, int b){
        return Math.abs(a - b);
    }

    private long multiplication(int a, int b){
        return a * b;
    }

    private long numberOfClosedArea(int a, int b){
        return closedArea(a) + closedArea(b);
    }

    private long reverseLarger(int a, int b){
        return reverseNumber(Math.max(a, b));
    }

    private long reverseSmaller(int a, int b){
        return reverseNumber(Math.min(a, b));
    }

    private long xCounter(int a, int b, int random){
        return xCountIn(a, random) + xCountIn(b, random);
    }

    private long isContain(int a, int b, int random){
        return contain(a, random) || contain(b, random)? 1: 0;
    }

    private long isBothContain(int a, int b, int random){
        return contain(a, random) && contain(b, random)? 1: 0;
    }

    private long greatestCommonFactor(int a, int b){
        if(a == 0 || b == 0){
            return a + b;
        }
        return greatestCommonFactor(b, a % b);
    }

    private long leastCommonMultiple(int a, int b){
        long GCF = greatestCommonFactor(a, b);
        return (a / GCF) * b;
    }

    private long totalDigitCount(int a, int b){
        return digitCount(a) + digitCount(b);
    }

    private long digitDifference(int a, int b){
        return Math.abs(digitCount(a) - digitCount(b));
    }

    private long printSumOfDigit(int a, int b){
        int[] tableA = new int[10];
        int[] tableB = new int[10];
        intCount(a, tableA);
        intCount(b, tableB);
        String temp = String.valueOf(sumOfDigit(tableA)) + String.valueOf(sumOfDigit(tableB));
        return Long.valueOf(temp);
    }

    /* ------------------------------------- Functions (Hard)------------------------------------- */
    private long numberOfBinaryOne(int a, int b){
        return Integer.bitCount(a) + Integer.bitCount(b);
    }

    private long binarySumUnder10(int a, int b){
        a %= 10;
        b %= 10;
        return Long.valueOf(Integer.toBinaryString(a + b));
    }

    private long binaryMinusUnder10(int a, int b){
        long temp = Math.abs(a % 10 - b % 10);
        return Long.valueOf(Long.toBinaryString(temp));
    }

    private long reverseSum(int a, int b){
        return reverseNumber(a + b);
    }

    private long reverseSub(int a, int b){
        return reverseNumber(Math.abs(a - b));
    }

    private long integerCounter(int a, int b){
        int[] table = new int[10];
        intCount(a, table);
        intCount(b, table);

        return intArrayToInteger(table);
    }

    private long digitSum(int a, int b){
        int[] tableA = new int[10];
        int[] tableB = new int[10];
        intCount(a, tableA);
        intCount(b, tableB);

        return sumOfDigit(tableA) + sumOfDigit(tableB);
    }

    private long digitSub(int a, int b){
        int[] tableA = new int[10];
        int[] tableB = new int[10];
        intCount(a, tableA);
        intCount(b, tableB);

        return Math.abs(sumOfDigit(tableA) - sumOfDigit(tableB));
    }
}
