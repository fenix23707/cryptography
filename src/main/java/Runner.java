import ciphers.*;
import numbers.BigNumber;
import numbers.BigNumberOperations;


public class Runner {
    public static void main(String[] args) {

        BigNumber msg = new BigNumber(16, "CACEC2C7CEC2C2CB");//ковзоввл
        BigNumber key = new BigNumber(16, "CACEC2C7CEC2C2");

        System.out.println("Сообщение: ковзоввл");
        System.out.println("Ключ: ковзовв");

        CipherDes cipherDes = new CipherDes();
        System.out.println("Шифр: " + cipherDes.encrypt(msg, key));
    }
}
