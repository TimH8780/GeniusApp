package unknown.logica.Module;

import java.security.SecureRandom;

public class RandomNumberGenerators {

    public static int randomNumberTime(int maxValue) {
        //return (int)(Math.random() * maxValue);
        return (int)(((long)System.currentTimeMillis()) % maxValue);
    }

    public static int randomNumberMath(int maxValue){
        return (int)(Math.random() * maxValue);
    }

    public static int randomNumberSecure(int maxValue){
        int i = 0;
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);

        while (bytes[i] < 0){
            i++;
            if (i > 19) {
                random.nextBytes(bytes);
                i = 0;
            }
        }
        return bytes[i]%maxValue;
    }
}
