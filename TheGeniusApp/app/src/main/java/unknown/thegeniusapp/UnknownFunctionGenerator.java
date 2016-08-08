package unknown.thegeniusapp;

public class UnknownFunctionGenerator {

    //Referenced http://stackoverflow.com/questions/4280727/java-creating-an-array-of-methods
    public static final int MAX_FUNCTIONS = 12;

    private UnknownFunction random_function;
    private interface UnknownFunction{ long calculate(int a, int b); }

    public UnknownFunctionGenerator(){ random_function = unknownFunctionGenerator(); }

    private UnknownFunction unknownFunctionGenerator(){
        int function_number = RandomNumberGenerators.randomNumber(MAX_FUNCTIONS);
        return functionArray[function_number];
    }

    public long getResult(int a, int b){
        return random_function.calculate(a, b);
    }

    private UnknownFunction[] functionArray = new UnknownFunction[]{
            /* ------------------ Easy ------------------ */
            new UnknownFunction() { public long calculate(int a, int b) { return addition(a, b); }},                // functionArray[0]
            new UnknownFunction() { public long calculate(int a, int b) { return subtraction(a, b); }},             // functionArray[1]
            new UnknownFunction() { public long calculate(int a, int b) { return multiplication(a, b); }},          // functionArray[2]
            new UnknownFunction() { public long calculate(int a, int b) { return numberOfClosedArea(a, b); }},      // functionArray[3]
            new UnknownFunction() { public long calculate(int a, int b) { return numberOfOne(a, b); }},             // functionArray[4]
            new UnknownFunction() { public long calculate(int a, int b) { return reverseLarger(a, b); }},            // functionArray[5]
            new UnknownFunction() { public long calculate(int a, int b) { return reverseSmaller(a, b); }},           // functionArray[6]
            new UnknownFunction() { public long calculate(int a, int b) { return integerCounter(a, b); }},          // functionArray[7]

            /* ------------------ Hard ------------------ */
            new UnknownFunction() { public long calculate(int a, int b) { return binarySumUnder10(a, b); }},        // functionArray[+ 1]
            new UnknownFunction() { public long calculate(int a, int b) { return binaryMinusUnder10(a, b); }},      // functionArray[+ 2]
            new UnknownFunction() { public long calculate(int a, int b) { return reverseSum(a, b); }},               // functionArray[+ 3]
            new UnknownFunction() { public long calculate(int a, int b) { return reverseSub(a, b); }},               // functionArray[+ 4]
    };

    /* ------------------------------------- Lookup Tables ------------------------------------- */
    int[] circleTable = {1, 0, 0, 0, 1, 0, 1, 0, 2, 1};

    /* ------------------------------------- Helper Functions ------------------------------------- */
    private long reverseNumber(int num){
        long result = 0;
        while(num > 0){
            result *= 10;
            result += num % 10;
            num /= 10;
        }
        return result;
    }

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
        int result = 0;
        do{
            result += circleTable[a % 10];
            a /= 10;
        } while(a > 0);

        do{
            result += circleTable[b % 10];
            b /= 10;
        } while(b > 0);
        return result;
    }

    private long numberOfOne(int a, int b){
        int result = 0;
        while(a > 0){
            result += (a & 1);
            a >>>= 1;
        }
        while(b > 0){
            result += (b & 1);
            b >>>= 1;
        }
        return result;
    }

    //TODO - check
    private long reverseLarger(int a, int b){
        return reverseNumber(Math.max(a, b));
    }

    //TODO - check
    private long reverseSmaller(int a, int b){
        return reverseNumber(Math.min(a, b));
    }

    //TODO - check
    private long integerCounter(int a, int b){
        int[] table = new int[10];
        do{
            table[a % 10] += 1;
            a /= 10;
        } while (a > 0);

        do{
            table[b % 10] += 1;
            b /= 10;
        } while (b > 0);

        long result = 0;
        for(int i = 9; i >= 0; i--){
            result *= 10;
            result += table[i];
        }
        return result;
    }

    /* ------------------------------------- Functions (Hard)------------------------------------- */
    private long binarySumUnder10(int a, int b){
        a %= 10;
        b %= 10;
        long result = Long.valueOf(Integer.toBinaryString(a + b));
        return result;
    }

    private long binaryMinusUnder10(int a, int b){
        a %= 10;
        b %= 10;
        long temp = Math.abs(a - b);
        long result = Long.valueOf(Long.toBinaryString(temp));
        return result;
    }

    //TODO - check
    private long reverseSum(int a, int b){
        return reverseNumber(a + b);
    }

    //TODO - check
    private long reverseSub(int a, int b){
        return reverseNumber(Math.abs(a - b));
    }
}
