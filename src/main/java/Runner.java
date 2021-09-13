import ciphers.*;
import numbers.BigNumber;
import numbers.BigNumberOperations;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;


public class Runner {
    public static void main(String[] args) throws UnsupportedEncodingException {

        // test git 
        /*BigNumber msg = new BigNumber(16, "CACEC2C7CEC2C2CB");//ковзоввл
        BigNumber key = new BigNumber(16, "CACEC2C7CEC2C2");

        System.out.println("Сообщение: ковзоввл");
        System.out.println("Ключ: ковзовв");

        CipherDes cipherDes = new CipherDes();
        System.out.println("Шифр: " + cipherDes.encrypt(msg, key));*/

        BigNumber msg1 = new BigNumber(16, "CACEC2C7CEC2C2");

        SingleVariableFunction function1 = x -> {
            int sum = 0;
            sum += x.getDigitAt(4);
            sum += x.getDigitAt(1);
            return sum%2;
        };

        SingleVariableFunction function2 = x -> {
            int sum = 0;
            sum += x.getDigitAt(4);
            sum += x.getDigitAt(2);
            sum += x.getDigitAt(1);
            sum += x.getDigitAt(0);
            return sum%2;
        };

        SingleVariableFunction function3 = x -> {
            int sum = 0;
            sum += x.getDigitAt(4);
            sum += x.getDigitAt(2);
            sum += x.getDigitAt(1);
            return sum%2;
        };


        BigNumber start = new BigNumber(2,"01010");
        CipherXor cipherXor = new CipherXor();
        System.out.println( cipherXor.encrypt(msg1, start, function1));
        System.out.println( cipherXor.encrypt(msg1, start, function2));
        System.out.println( cipherXor.encrypt(msg1, start, function2));


    }
}
