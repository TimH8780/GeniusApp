package unknown.logica.Module;

import android.content.SharedPreferences;
import android.util.Log;

import java.util.Arrays;
import java.util.LinkedList;

import static unknown.logica.Module.HelperFunction.*;
import static unknown.logica.Settings.*;

public class UnknownFunctionGenerator {

    //Referenced http://stackoverflow.com/questions/4280727/java-creating-an-array-of-methods
    public static final int MAX_FUNCTIONS = 35;
    public static final int MIN_RANDOM_INDEX = 6;
    public static final int MAX_RANDOM_INDEX = 8;
    private static final int HARD_START_INDEX = 24;

    private UnknownFunction random_function;
    private int functionIndex;
    private interface UnknownFunction{ long calculate(int a, int b, int random); }

    private static int start = 72;

    public UnknownFunctionGenerator(SharedPreferences sp){ random_function = unknownFunctionGenerator(sp); }

    public UnknownFunctionGenerator(int index){
        random_function = functionArray[index];
    }

    private UnknownFunction unknownFunctionGenerator(SharedPreferences sp){
        int difficulty = sp.getInt(DIFFICULTY_VALUE, MIXED);

        //int i = 0;
        int array[] = new int[40];
        //while(i < 20) {
            do {
                functionIndex = RandomNumberGenerators.randomNumberMath(MAX_FUNCTIONS);
                array[functionIndex]++;
                functionIndex = start++;
            } while (!isValid(difficulty, functionIndex));
            //i++;
        //}
        //for(int j = 0; j < 40; j++){
        //    Log.d("Function Index - " + Integer.toString(j), String.valueOf(array[j]));
        //}

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
            new UnknownFunction() { public long calculate(int a, int b, int random) { return printSmaller(a, b); }},                  // functionArray[14]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return printLarger(a, b); }},                   // functionArray[15]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return totalDigitCountSmaller(a, b); }},        // functionArray[16]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return totalDigitCountLarger(a, b); }},         // functionArray[17]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return reorderAscend(a, b); }},                 // functionArray[18]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return reorderDescend(a, b); }},                // functionArray[19]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return reorderAscendSmall(a, b); }},            // functionArray[20]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return reorderAscendLarge(a, b); }},            // functionArray[21]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return reorderDescendSmall(a, b); }},           // functionArray[22]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return reorderDescendLarge(a, b); }},           // functionArray[23]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return digitCountMultiplication(a, b); }},      // functionArray[24]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return digitCountSubtraction(a, b); }},         // functionArray[25]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return average(a, b); }},                       // functionArray[26]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return reverseAll(a, b); }},                    // functionArray[27]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return oddDigitOnly(a, b); }},                  // functionArray[28]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return evenDigitOnly(a, b); }},                 // functionArray[29]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return oddPositionOnly(a, b); }},               // functionArray[30]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return evenPositionOnly(a, b); }},              // functionArray[31]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return division(a, b); }},                      // functionArray[32]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return removeLargestDigit(a, b); }},            // functionArray[33]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return removeSmallerDigit(a, b); }},            // functionArray[34]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return removeRepeatedDigit(a, b); }},           // functionArray[35]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return doubleDigits(a, b); }},                  // functionArray[36]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return printAll(a, b); }},                      // functionArray[37]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return mostOccurrence(a, b); }},                // functionArray[38]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return headAndHead(a, b); }},                   // functionArray[39]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return headAndTail(a, b); }},                   // functionArray[40]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return tailAndHead(a, b); }},                   // functionArray[41]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return tailAndTail(a, b); }},                   // functionArray[42]

            /* ------------------ Hard ------------------ */
            new UnknownFunction() { public long calculate(int a, int b, int random) { return numberOfBinaryOne(a, b); }},             // functionArray[+ 1]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return binarySumUnder10(a, b); }},              // functionArray[+ 2]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return binaryMinusUnder10(a, b); }},            // functionArray[+ 3]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return reverseSum(a, b); }},                    // functionArray[+ 4]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return reverseSub(a, b); }},                    // functionArray[+ 5]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return integerCounter(a, b); }},                // functionArray[+ 6]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return digitSum(a, b); }},                      // functionArray[+ 7]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return digitSub(a, b); }},                      // functionArray[+ 8]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return sumSmaller(a, b); }},                    // functionArray[+ 9]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return sumLarger(a, b); }},                     // functionArray[+ 10]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return addDigitUntilOne(a, b); }},             // functionArray[+ 11]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return sumOfReverseLarger(a, b); }},            // functionArray[+ 12]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return sumOfReverseSmaller(a, b); }},           // functionArray[+ 13]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return subOfReverseLarger(a, b); }},            // functionArray[+ 14]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return subOfReverseSmall(a, b); }},             // functionArray[+ 15]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return nineComplementAll(a, b); }},             // functionArray[+ 16]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return nineComplementSmaller(a, b); }},         // functionArray[+ 17]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return nineComplementLarger(a, b); }},          // functionArray[+ 18]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return digitMedian(a, b); }},                   // functionArray[+ 19]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return divAndMod(a, b); }},                     // functionArray[+ 20]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return sumAndSub(a, b); }},                     // functionArray[+ 21]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return halfDigits(a, b); }},                    // functionArray[+ 22]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return sumOfDoubledDigits(a, b); }},            // functionArray[+ 23]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return subOfDoubledDigits(a, b); }},            // functionArray[+ 24]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return sumOfHalvedDigits(a, b); }},             // functionArray[+ 25]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return subOfHalvedDigits(a, b); }},             // functionArray[+ 26]
            new UnknownFunction() { public long calculate(int a, int b, int random) { return indicatedDigit(a, b); }},                // functionArray[+ 27]

            /* ------------------ Undecided ------------------ */
//            new UnknownFunction() { public long calculate(int a, int b, int random) { return bothEqual(a, b); }},
//            new UnknownFunction() { public long calculate(int a, int b, int random) { return bothNotEqual(a, b); }},
//            new UnknownFunction() { public long calculate(int a, int b, int random) { return moduloAB(a, b); }},
//            new UnknownFunction() { public long calculate(int a, int b, int random) { return mostOccurrence(a, b); }},
//            new UnknownFunction() { public long calculate(int a, int b, int random) { return additionOfModX(a, b, random); }},
//            new UnknownFunction() { public long calculate(int a, int b, int random) { return insertXInbetween(a, b, random); }},
//            new UnknownFunction() { public long calculate(int a, int b, int random) { return moduloAB(a, b); }},
//            new UnknownFunction() { public long calculate(int a, int b, int random) { return moduloBA(a, b); }},
//            new UnknownFunction() { public long calculate(int a, int b, int random) { return moduloBigSmall(a, b); }},
            new UnknownFunction() { public long calculate(int a, int b, int random) { return amountDigitGreaterThan5(a, b); }},
            new UnknownFunction() { public long calculate(int a, int b, int random) { return amountDigitLessThan5(a, b); }},
            new UnknownFunction() { public long calculate(int a, int b, int random) { return removeDigitGreaterThan5(a, b); }},
            new UnknownFunction() { public long calculate(int a, int b, int random) { return removeDigitLessThan5(a, b); }},
            new UnknownFunction() { public long calculate(int a, int b, int random) { return amountDigitEven(a, b); }},
            new UnknownFunction() { public long calculate(int a, int b, int random) { return amountDigitOdd(a, b); }},
            new UnknownFunction() { public long calculate(int a, int b, int random) { return sumOfAllEvenDigit(a, b); }},
            new UnknownFunction() { public long calculate(int a, int b, int random) { return sumOfAllOddDigit(a, b); }},
            new UnknownFunction() { public long calculate(int a, int b, int random) { return productOfAllEvenDigit(a, b); }},
            new UnknownFunction() { public long calculate(int a, int b, int random) { return productofAllOddDigit(a, b); }},
//            new UnknownFunction() { public long calculate(int a, int b, int random) { return differenceEvenOddDigit(a, b); }},
//            new UnknownFunction() { public long calculate(int a, int b, int random) { return differenceOddEvenDigit(a, b); }},
//            new UnknownFunction() { public long calculate(int a, int b, int random) { return multiplyEvenOddDigit(a, b); }},
            new UnknownFunction() { public long calculate(int a, int b, int random) { return multiplyAllDigit(a, b); }},
            new UnknownFunction() { public long calculate(int a, int b, int random) { return factorialFirstDigit(a, b); }},
            new UnknownFunction() { public long calculate(int a, int b, int random) { return factorialLastDigit(a, b); }},
            new UnknownFunction() { public long calculate(int a, int b, int random) { return factorialSmallestDigit(a, b); }},
            new UnknownFunction() { public long calculate(int a, int b, int random) { return factorialLargestDigit(a, b); }},
            new UnknownFunction() { public long calculate(int a, int b, int random) { return pythagoreanTheoream(a, b); }},
            new UnknownFunction() { public long calculate(int a, int b, int random) { return rotationByHourHand(a, b); }},
            new UnknownFunction() { public long calculate(int a, int b, int random) { return rotationByMinuteHand(a, b); }},

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

        return combinedTwoNumber(sumOfDigit(tableA), sumOfDigit(tableB));
    }

    private long digitCountMultiplication(int a, int b){
        int digA = digitCount(a);
        int digB = digitCount(b);
        Log.d("digitCountMult", Integer.toString(digA));
        return (digA * digB);
    }

    private long digitCountSubtraction(int a, int b){
        int digA = digitCount(a);
        int digB = digitCount(b);
        return Math.abs(digA-digB);
    }

    private long average(int a, int b){
        return (a + b) / 2;
    }

    private long reverseAll(int a, int b){
        long combined = combinedTwoNumber(a, b);
        return reverseNumber(combined);
    }

    private long oddDigitOnly(int a, int b){
        String combinedString = String.valueOf(a) + String.valueOf(b);
        String processed = removeDigit(combinedString, REMOVE_EVEN_DIGIT);
        if(processed.equals("")) return 0;
        return Long.valueOf(processed);
    }

    private long evenDigitOnly(int a, int b){
        String combinedString = String.valueOf(a) + String.valueOf(b);
        String processed = removeDigit(combinedString, REMOVE_ODD_DIGIT);
        if(processed.equals("")) return 0;
        return Long.valueOf(processed);
    }

    private long oddPositionOnly(int a, int b){
        String combinedString = String.valueOf(a) + String.valueOf(b);
        String processed = removePositionDigit(combinedString, REMOVE_EVEN_POSITION);
        return Long.valueOf(processed);
    }

    private long evenPositionOnly(int a, int b){
        String combinedString = String.valueOf(a) + String.valueOf(b);
        String processed = removePositionDigit(combinedString, REMOVE_ODD_POSITION);
        return Long.valueOf(processed);
    }

    private long division(int a, int b){
        if (b==0) return 0;
        return a / b;
    }

    private long removeLargestDigit(int a, int b){
        String combinedString = String.valueOf(a) + String.valueOf(b);
        char largestDigit = getLargestDigit(combinedString);
        String remainingStr = removeDesiredDigit(combinedString, largestDigit);
        if(remainingStr.equals("")) return 0;
        return Long.valueOf(remainingStr);
    }

    private long removeSmallerDigit(int a, int b){
        String combinedString = String.valueOf(a) + String.valueOf(b);
        char smallestDigit = getSmallerDigit(combinedString);
        String remainingStr = removeDesiredDigit(combinedString, smallestDigit);
        if(remainingStr.equals("")) return 0;
        return Long.valueOf(remainingStr);
    }

    private long removeRepeatedDigit(int a, int b){
        int result = 0, set = 0, rev = 0, combined = (int)combinedTwoNumber(a, b);  // result
        int copy = combined;

        while (combined > 0) {
            rev = (rev * 10) + (combined % 10);
            combined /= 10;
        }

        while (rev > 0) {
            final int mod = rev % 10;
            final int mask = 1 << mod;
            if ((set & mask) == 0) {
                result = (result * 10) + mod;
                set |= mask;
            }
            rev /= 10;
        }

        if(copy % 10 == 0 && (set & 1) == 0){
            result *= 10;
        }
        return result;
    }

    private long doubleDigits(int a, int b){
        int[] doubledA = doubleDigit(a);
        int[] doubledB = doubleDigit(b);
        return combinedTwoIntArray(doubledA, doubledB);
    }

    private long printAll(int a, int b){
        return combinedTwoNumber(a, b);
    }

    private long mostOccurrence(int a, int b){
        int[] table = new int[10];
        intCount(a, table);
        intCount(b, table);
        return getLargestDigitOccurrence(table);
    }

    private long headAndHead(int a, int b){
        return combinedTwoNumber(getHeadDigit(a), getHeadDigit(b));
    }

    private long headAndTail(int a, int b){
        return combinedTwoNumber(getHeadDigit(a), getTailDigit(b));
    }

    private long tailAndHead(int a, int b){
        return combinedTwoNumber(getTailDigit(a), getHeadDigit(b));
    }

    private long tailAndTail(int a, int b){
        return combinedTwoNumber(getTailDigit(a), getTailDigit(b));
    }


    /* ------------------------------------- Functions (Hard)------------------------------------- */
    private long numberOfBinaryOne(int a, int b){
        return Integer.bitCount(a) + Integer.bitCount(b);
    }

    private long binarySumUnder10(int a, int b){
        return Long.valueOf(Integer.toBinaryString(a % 10 + b % 10));
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
        int[] table = new int[10];
        intCount(a, table);
        intCount(b, table);

        return sumOfDigit(table);
    }

    private long digitSub(int a, int b){
        int[] tableA = new int[10];
        int[] tableB = new int[10];
        intCount(a, tableA);
        intCount(b, tableB);

        return Math.abs(sumOfDigit(tableA) - sumOfDigit(tableB));
    }

    private long printSmaller(int a, int b){
        return Math.min(a, b);
    }

    private long printLarger(int a, int b){
        return Math.max(a, b);
    }

    private long sumSmaller(int a, int b){
        int smaller = Math.min(a, b);
        int[] smallerTable = new int[10];
        intCount(smaller, smallerTable);
        return sumOfDigit(smallerTable);
    }

    private long sumLarger(int a, int b){
        int larger = Math.max(a, b);
        int[] largerTable = new int[10];
        intCount(larger, largerTable);
        return sumOfDigit(largerTable);
    }

    private long totalDigitCountSmaller(int a, int b){
        int smaller = Math.min(a, b);
        return digitCount(smaller);
    }

    private long totalDigitCountLarger(int a, int b){
        int larger = Math.max(a, b);
        return digitCount(larger);
    }

    private long reorderDescend(int a, int b){
        int[] table = new int[10];
        intCount(a, table);
        intCount(b, table);

        return intArrayDescend(table);
    }

    private long reorderAscend(int a, int b){
        int[] table = new int[10];
        intCount(a, table);
        intCount(b, table);

        return intArrayAscend(table);
    }

    private long reorderDescendSmall(int a, int b){
        int smaller = Math.min(a, b);
        int[] table = new int[10];
        intCount(smaller, table);

        return intArrayDescend(table);
    }

    private long reorderDescendLarge(int a, int b){
        int larger = Math.max(a, b);
        int[] table = new int[10];
        intCount(larger, table);

        return intArrayDescend(table);
    }

    private long reorderAscendSmall(int a, int b){
        int smaller = Math.min(a, b);
        int[] table = new int[10];
        intCount(smaller, table);

        return intArrayAscend(table);
    }

    private long reorderAscendLarge(int a, int b){
        int larger = Math.max(a, b);
        int[] table = new int[10];
        intCount(larger, table);

        return intArrayAscend(table);
    }

    private long addDigitUntilOne(int a, int b){
        if(a == 0 && b == 0) return 0;

        long result = (a % 9) + (b % 9);
        while(result > 9){
            result %= 9;
        }
        if(result == 0) return 9;

        return result;
    }

    private long sumOfReverseLarger(int a, int b){
        return reverseLarger(a, b) + Math.min(a, b);
    }

    private long sumOfReverseSmaller(int a, int b){
        return reverseSmaller(a, b) + Math.max(a, b);
    }

    private long subOfReverseLarger(int a, int b){
        return Math.abs(reverseLarger(a, b) - Math.min(a, b));
    }

    private long subOfReverseSmall(int a, int b){
        return Math.abs(reverseSmaller(a, b) - Math.max(a, b));
    }

    private long nineComplementAll(int a, int b){
        long combined = combinedTwoNumber(a, b);
        long nines = generateNines(digitCount(combined));

        return nines - combined;
    }

    private long nineComplementSmaller(int a, int b){
        int smaller = Math.min(a, b);
        long nines = generateNines(digitCount(smaller));

        return nines - smaller;
    }

    private long nineComplementLarger(int a, int b){
        long larger = Math.max(a, b);
        long nines = generateNines(digitCount(larger));

        return nines - larger;
    }

    private long digitMedian(int a, int b){
        int median = 0;
        String combined = String.valueOf(a) + String.valueOf(b);

        char[] combinedDigits = combined.toCharArray();
        Arrays.sort(combinedDigits);

        if (combinedDigits.length % 2 == 0)
            median = (Character.getNumericValue(combinedDigits[combinedDigits.length/2]) + Character.getNumericValue(combinedDigits[combinedDigits.length/2 - 1]))/2;
        else
            median = Character.getNumericValue(combinedDigits[combinedDigits.length/2]);
        return median;
    }


    private long divAndMod(int a, int b){
        return combinedTwoNumber(division(a, b), moduloAB(a, b));
    }

    private long sumAndSub(int a, int b){
        return combinedTwoNumber(addition(a, b), subtraction(a, b));
    }

    private long halfDigits(int a, int b){
        int[] halvedA = halfDigit(a);
        int[] halvedB = halfDigit(b);
        return combinedTwoIntArray(halvedA, halvedB);
    }

    private long sumOfDoubledDigits(int a, int b){
        return addition(intArrayToInt(doubleDigit(a)), intArrayToInt(doubleDigit(b)));
    }

    private long subOfDoubledDigits(int a, int b){
        return subtraction(intArrayToInt(doubleDigit(a)), intArrayToInt(doubleDigit(b)));
    }

    private long sumOfHalvedDigits(int a, int b){
        return addition(intArrayToInt(halfDigit(a)), intArrayToInt(halfDigit(b)));
    }

    private long subOfHalvedDigits(int a, int b){
        return subtraction(intArrayToInt(halfDigit(a)), intArrayToInt(halfDigit(b)));
    }

    private long indicatedDigit(int a, int b){
        int[] intArray = intToIntArray(combinedTwoNumber(a, b));
        int firstDigit = intArray[0];
        int targetPosition = (firstDigit % intArray.length) - 1;
        if(targetPosition < 0) targetPosition += intArray.length;

        return intArray[targetPosition];
    }

    /* ------------------------------------- Functions (Undecided)------------------------------------- */
    private long bothEqual(int a, int b){
        return a == b? 1 : 0;
    }

    private long bothNotEqual(int a, int b){
        return a != b? 1 : 0;
    }

    private long additionOfModX(int a, int b, int random){
        if(a == 0 && b == 0) return 0;

        long result = (a % random) + (b % random);
        while(result > random){
            result %= random;
        }
        if(result == 0) return random; //(result == random) return 0?

        return result;
    }

    private long insertXInbetween(int a, int b, int random){
        int combined = (int)combinedTwoNumber(a, b);
        return insertX(combined, random);
    }

    private long moduloAB(int a, int b){
        if(b==0) return 0;
        return (a % b);
    }

    private long moduloBA(int a, int b){
        if(a==0) return 0;
        return (b % a);
    }

    private long moduloBigSmall(int a, int b){
        int larger = Math.max(a, b);
        int smaller = Math.min(a, b);
        if(smaller==0) return 0;
        return (larger % smaller);
    }

    private long amountDigitGreaterThan5(int a, int b){
        int table[] = new int[10];
        int medium = 5, result = 0;
        intCount(a, table);
        intCount(b, table);
        do {
            result += table[medium];
            medium++;
        }while (medium <= 9);

        return result;
    }

    private long amountDigitLessThan5(int a, int b){
        int table[] = new int[10];
        int medium = 5, result = 0;
        intCount(a, table);
        intCount(b, table);
        do {
            result += table[medium-1];
            medium--;
        }while (medium > 0);

        return result;
    }

    private long removeDigitGreaterThan5(int a, int b){
        String combinedString = String.valueOf(a) + String.valueOf(b);
        String result = removeGreaterLessThan5(combinedString, REMOVE_GREATER_5);
        if(result.equals("")) return 0;
        return Long.valueOf(result);
    }

    private long removeDigitLessThan5(int a, int b){
        String combinedString = String.valueOf(a) + String.valueOf(b);
        String result = removeGreaterLessThan5(combinedString, REMOVE_LESS_5);
        if(result.equals("")) return 0;
        return Long.valueOf(result);
    }

    private long amountDigitEven(int a, int b){
        String combinedString = String.valueOf(a) + String.valueOf(b);
        String processed = removeDigit(combinedString, REMOVE_ODD_DIGIT);
        if(processed.equals("")) return 0;
        return digitCount(Long.valueOf(processed));
    }

    private long amountDigitOdd(int a, int b){
        String combinedString = String.valueOf(a) + String.valueOf(b);
        String processed = removeDigit(combinedString, REMOVE_EVEN_DIGIT);
        if(processed.equals("")) return 0;
        return digitCount(Long.valueOf(processed));
    }

    private long sumOfAllEvenDigit(int a, int b){
        String combinedString = String.valueOf(a) + String.valueOf(b);
        String processed = removeDigit(combinedString, REMOVE_ODD_DIGIT);
        if(processed.equals("")) return 0;
        int[] table = new int[10];
        intCount(Integer.valueOf(processed), table);
        return sumOfDigit(table);
    }

    private long sumOfAllOddDigit(int a, int b){
        String combinedString = String.valueOf(a) + String.valueOf(b);
        String processed = removeDigit(combinedString, REMOVE_EVEN_DIGIT);
        if(processed.equals("")) return 0;
        int[] table = new int[10];
        intCount(Integer.valueOf(processed), table);
        return sumOfDigit(table);
    }

    private long productOfAllEvenDigit(int a, int b){
        String combinedString = String.valueOf(a) + String.valueOf(b);
        String processed = removeDigit(combinedString, REMOVE_ODD_DIGIT);
        if(processed.equals("")) return 0;
        int[] table = new int[10];
        intCount(Integer.valueOf(processed), table);
        return productOfDigit(table);
    }

    private long productofAllOddDigit(int a, int b){
        String combinedString = String.valueOf(a) + String.valueOf(b);
        String processed = removeDigit(combinedString, REMOVE_EVEN_DIGIT);
        if(processed.equals("")) return 0;
        int[] table = new int[10];
        intCount(Integer.valueOf(processed), table);
        return productOfDigit(table);
    }

    private long differenceEvenOddDigit(int a, int b){
        //REDO: VERY BUGGY - Crashes with error about Long: ""
//        String combinedString = String.valueOf(a) + String.valueOf(b);
//        if(combinedString.equals("")) return 0;
//        String sumOfEven = removeDigit(combinedString, REMOVE_ODD_DIGIT);
//        String sumOfOdd = removeDigit(combinedString, REMOVE_EVEN_DIGIT);
//        return Math.abs(digitCount(Long.valueOf(sumOfEven))-digitCount(Long.valueOf(sumOfOdd)));
        return 0;
    }

    private long differenceOddEvenDigit(int a, int b){
        //REDO: VERY BUGGY - Crashes with error about Long: ""
//        String combinedString = String.valueOf(a) + String.valueOf(b);
//        String sumOfEven = removeDigit(combinedString, REMOVE_ODD_DIGIT);
//        String sumOfOdd = removeDigit(combinedString, REMOVE_EVEN_DIGIT);
//        if(combinedString.equals("")) return 0;
//        return Math.abs(digitCount(Long.valueOf(sumOfOdd))-digitCount(Long.valueOf(sumOfEven)));
        return 0;
    }

    private long multiplyEvenOddDigit(int a, int b){
        //REDO: VERY BUGGY - Crashes with error about Long: ""
//        String combinedString = String.valueOf(a) + String.valueOf(b);
//        //if(combinedString.equals("")) return 0;
//        String sumOfEven = removeDigit(combinedString, REMOVE_ODD_DIGIT);
//        String sumOfOdd = removeDigit(combinedString, REMOVE_EVEN_DIGIT);
//        return (digitCount(Long.valueOf(sumOfEven))*digitCount(Long.valueOf(sumOfOdd)));
        return 0;
    }

    private long multiplyAllDigit(int a, int b){
        String combined = String.valueOf(a) + String.valueOf(b);
        int[] table = new int[10];
        intCount(Integer.valueOf(combined), table);
        return productOfDigit(table);
    }

    private long factorialFirstDigit(int a, int b){
        int firstDigit = getHeadDigit(a);
        return factorial(firstDigit);
    }

    private long factorialLastDigit(int a, int b){
        int lastDigit = getTailDigit(b);
        return factorial(lastDigit);
    }

    private long factorialSmallestDigit(int a, int b) {
        String combinedString = String.valueOf(a) + String.valueOf(b);
        char smallestDigit = getSmallerDigit(combinedString);
        Log.d("smallest", String.valueOf(smallestDigit));
        return factorial(Character.getNumericValue(smallestDigit));
    }

    private long factorialLargestDigit(int a, int b){
        String combinedString = String.valueOf(a) + String.valueOf(b);
        char largestDigit = getLargestDigit(combinedString);
        return factorial(Character.getNumericValue(largestDigit));
    }

    private long pythagoreanTheoream(int a, int b){
        return (int)(Math.sqrt((a*a)+(b*b)));
    }

    private long rotationByHourHand(int a, int b){
        if(a == 0 && b == 0) return 0;

        long result = (a % 12) + (b % 12);
        while(result > 12){
            result %= 12;
        }
        if(result == 0) return 12;

        return result;
    }

    private long rotationByMinuteHand(int a, int b){
        if(a == 0 && b == 0) return 0;

        long result = (a % 60) + (b % 60);
        while(result > 60){
            result %= 60;
        }
        if(result == 0) return 60;

        return result;
    }
}

