package unknown.logica.Module;

import android.util.Log;

import java.util.LinkedList;

/**
 *Created by Tim on 08/20/16.
 */
public class HelperFunction {
    public static final boolean REMOVE_EVEN_DIGIT = false;
    public static final boolean REMOVE_ODD_DIGIT = true;
    public static final boolean REMOVE_EVEN_POSITION = false;
    public static final boolean REMOVE_ODD_POSITION = true;
    public static final boolean REMOVE_LESS_5 = false;
    public static final boolean REMOVE_GREATER_5 = true;

    /* ------------------------------------- Lookup Tables ------------------------------------- */
    public static int[] circleTable = {1, 0, 0, 0, 1, 0, 1, 0, 2, 1};

    /* ------------------------------------- Helper Functions ------------------------------------- */
    public static long reverseNumber(long num){
        long result = 0;
        while(num > 0){
            result *= 10;
            result += num % 10;
            num /= 10;
        }
        return result;
    }

    public static long closedArea(int num){
        int result = 0;
        do{
            result += circleTable[num % 10];
            num /= 10;
        } while(num > 0);
        return result;
    }

    public static void intCount(int num, int[] table){
        do{
            table[num % 10] += 1;
            num /= 10;
        } while (num > 0);
    }

    public static long xCountIn(int num, int target){
        int result = 0;
        do{
            result += (num % 10) == target? 1: 0;
            num /= 10;
        } while(num > 0);
        return result;
    }

    public static boolean contain(int num, int target){
        do{
            if(num % 10 == target){
                return true;
            }
            num /= 10;
        } while(num > 0);
        return false;
    }

    public static int digitCount(long num){
        int result = 0;
        do{
            result++;
            num /= 10;
        } while(num > 0);
        return result;
    }

    public static long intArrayToInteger(int[] array){
        long result = 0;
        for(int i = 9; i >= 0; i--){
            result *= 10;
            result += array[i];
        }
        return result;
    }

    public static long sumOfDigit(int[] digits){
        long result = 0;
        for(int i = 9; i >= 0; i--){
            result += digits[i] * i;
        }
        return result;
    }

    public static long productOfDigit(int[] digits){
        long result = 1;
        for(int i = 9; i >= 0; i--){
            if(digits[i]!=0) {
                result *= Math.pow(i, digits[i]);
            }
        }
        return result;
    }

    public static long intArrayDescend(int[] array){
        long result = 0;
        for(int i = 9; i >= 0; i--){
            for(; array[i] > 0; array[i]--){
                result *= 10;
                result += i;
            }
        }
        return result;
    }

    public static long intArrayAscend(int[] array){
        long result = 0;
        for(int i = 1; i <= 9; i++){
            for(; array[i] > 0; array[i]--){
                result *= 10;
                result += i;
            }
        }
        return result;
    }

    public static long combinedTwoNumber(long a, long b){
        return Long.valueOf(String.valueOf(a) + String.valueOf(b));
    }

    public static long generateNines(int digitCount){
        long result = 0;
        for(int i = 0; i < digitCount; i++){
            result *= 10;
            result += 9;
        }
        return result;
    }

    public static int getHeadDigit(int num){
        while(num > 9){
            num /= 10;
        }
        return num;
    }

    public static int getTailDigit(int num){
        return num % 10;
    }

    public static int getLargestDigitOccurrence(int[] table){
        int max = 1;
        for(int i = 0; i < table.length; i++){
            max = Math.max(max, table[i]);
        }

        return max;
    }

    private static boolean isOddDigit(char c){
        return c == '1' || c == '3' || c == '5' || c == '7' || c == '9';
    }

    private static boolean isGreaterThan5(char c){
        return c == '5' || c == '6' || c == '7' || c == '8' || c == '9';
    }

    public static String removeGreaterLessThan5(String numStr, boolean which){
        StringBuilder builder = new StringBuilder();
        for(char c: numStr.toCharArray()){
            if(isGreaterThan5(c) ^ which){
                builder.append(c);
            }
        }
        return builder.toString();
    }

    public static String removeDigit(String numStr, boolean which){
        StringBuilder builder = new StringBuilder();
        for(char c: numStr.toCharArray()){
            if(isOddDigit(c) ^ which){
                builder.append(c);
            }
        }
        return builder.toString();
    }

    public static String removePositionDigit(String numStr, boolean which){
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < numStr.length(); i++){
            if(i % 2 == 0 ^ which){
                builder.append(numStr.charAt(i));
            }
        }
        return builder.toString();
    }

    public static char getLargestDigit(String numStr){
        char largest = '0';
        for(char c: numStr.toCharArray()){
            if(c > largest) largest = c;
        }

        return largest;
    }

    public static char getSmallerDigit(String numStr){
        char smallest = '9';
        for(char c: numStr.toCharArray()){
            if(c < smallest) smallest = c;
        }

        return smallest;
    }

    public static String removeDesiredDigit(String numStr, char digit){
        StringBuilder builder = new StringBuilder();
        for(char c: numStr.toCharArray()){
            if(c != digit){
                builder.append(c);
            }
        }
        return builder.toString();
    }

    public static int[] intToIntArray(long num){
        String numStr = String.valueOf(num);
        int[] array = new int[numStr.length()];
        for(int i = 0; i < array.length; i++){
            array[i] = (numStr.charAt(i) & 0x0F);
        }

        return array;
    }

    public static int[] doubleDigit(int num){
        int[] array = intToIntArray(num);
        for(int i = 0; i < array.length; i++){
            array[i] = (array[i] * 2) % 10;
        }

        return array;
    }

    public static int[] halfDigit(int num){
        int[] array = intToIntArray(num);
        for(int i = 0; i < array.length; i++){
            array[i] = array[i] / 2;
        }

        return array;
    }

    public static int combinedTwoIntArray(int[] a, int[] b){
        int result = 0;
        for(int i: a){
            result *= 10;
            result += i;
        }
        for(int i: b){
            result *= 10;
            result += i;
        }

        return result;
    }

    public static int intArrayToInt(int[] arr){
        int result = 0;
        for(int i: arr){
            result *= 10;
            result += i;
        }
        return result;
    }

    public static int insertX(int numb, int random){
        long result = 0;
        LinkedList<Integer> stack = new LinkedList<Integer>();
        while (numb > 0) {
            stack.push( numb % 10 );
            numb = numb / 10;
        }

        while (!stack.isEmpty()) {
            result = (int)combinedTwoNumber(result, stack.pop());
            result = (int)combinedTwoNumber(result, random);
        }

        return (int)(result /=10);
    }

    public static int factorial(int num){
        Log.d("factorial", String.valueOf(num));
        if (num==0) return 0;
        else if (num==1) return 1;
        else
            return num * factorial(num - 1);
    }

    public static int maxDigit(int n){
        return n==0 ? 0 : Math.max(n%10, maxDigit(n/10));
    }

    public static int minDigit(int n){
        return n==0 ? 0 : Math.min(n%10, minDigit(n/10));
    }
}
