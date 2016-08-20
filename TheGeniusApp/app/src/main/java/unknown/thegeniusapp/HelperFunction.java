package unknown.thegeniusapp;

/**
 *Created by Tim on 08/20/16.
 */
public class HelperFunction {
    /* ------------------------------------- Lookup Tables ------------------------------------- */
    public static int[] circleTable = {1, 0, 0, 0, 1, 0, 1, 0, 2, 1};

    /* ------------------------------------- Helper Functions ------------------------------------- */
    public static long reverseNumber(int num){
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

    public static long binaryOne(int num){
        int result = 0;
        while(num > 0){
            result += (num & 1);
            num >>>= 1;
        }
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

    public static long digitCount(int num){
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
}
