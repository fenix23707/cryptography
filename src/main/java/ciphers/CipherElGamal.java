package ciphers;

import group.MultiplicativeGroup;
import numbers.BigNumber;
import numbers.BigNumberOperations;
import numbers.PrimeNumbers;

import java.util.concurrent.ThreadLocalRandom;

public class CipherElGamal {
    private BigNumber p;

    private BigNumber g;

    private BigNumber y;

    private BigNumber o;

    private BigNumber x;

    private String alphabet = " абвгдеёжзийклмнопрстуфхцчшщъыьэюя";

    private int maxNumberOfDigit = 3;

    public CipherElGamal() {
        generatePAndG(maxNumberOfDigit);
    }

    public CipherElGamal(BigNumber p, BigNumber g) {
        this.p = p;
        this.g = g;
    }

    public CipherElGamal(BigNumber p, BigNumber g, BigNumber y, BigNumber o) {
        this.p = p;
        this.g = g;
        this.y = y;
        this.o = o;
    }

    public String decryption(BigNumber cipher) {
        if (x == null) {
            x = findX(g, y, p);
        }
        BigNumber k = BigNumberOperations.degreeRemainder2(o, x, p);
        BigNumber inverseK = BigNumberOperations.inverseBezu(k, p);
        BigNumber msg = BigNumberOperations.mullRemainder(cipher, inverseK, p);
        return bigNUmberToString(msg);
    }

    public BigNumber encryption(String msg) {
        BigNumber numMsg = charToBigNumber(msg);
        if (numMsg.compareTo(p) >= 0) {
            maxNumberOfDigit = numMsg.getSize();
            generatePAndG(maxNumberOfDigit);
        }
        x = generateKorX(p);
        BigNumber k = generateKorX(p);
        return encryption(numMsg, x, k);
    }

    public BigNumber encryption(String msg, BigNumber x, BigNumber k) {
        return encryption(charToBigNumber(msg), x, k);
    }

    public BigNumber encryption(BigNumber msg, BigNumber x, BigNumber k) {
        if (msg.compareTo(p) >= 0) {
            maxNumberOfDigit = msg.getSize();
            generatePAndG(maxNumberOfDigit);
        }

        y = BigNumberOperations.degreeRemainder2(g, x, p);
        BigNumber mainK = BigNumberOperations.degreeRemainder2(y, k, p);
        BigNumber cipher = BigNumberOperations.mullRemainder(msg, mainK, p);
        o = BigNumberOperations.degreeRemainder2(g, k, p);
        return cipher;
    }

    private BigNumber findX(BigNumber g, BigNumber y, BigNumber p) {
        BigNumber temp = g;
        BigNumber x = new BigNumber(g.getBaseNumSys(), "1");
        while (temp.compareTo(y) != 0) {
            temp = BigNumberOperations.mullRemainder(temp, g, p);
            temp.removeExtraZeros();
            x = BigNumberOperations.inc(x);
        }
        return x;
    }

    public BigNumber charToBigNumber(String msg) {
        StringBuilder builder = new StringBuilder(2 * msg.length());
        for (int i = 0; i < msg.length(); i++) {
            String num = String.valueOf(alphabet.indexOf(msg.charAt(i)));
            if (num.length() == 1) {
                num = "0" + num;
            }
            builder.append(num);
        }
        return new BigNumber(p.getBaseNumSys(), builder.toString());
    }

    public String bigNUmberToString(BigNumber num) {
        String numStr = num.toString();
        if (numStr.length() % 2 != 0) {
            numStr = "0" + numStr;
        }
        StringBuilder msg = new StringBuilder(numStr.length());
        for (int i = 0; i < numStr.length(); i += 2) {
            String temp = numStr.substring(i, i + 2);
            int index = Integer.parseInt(temp);
            char letter = alphabet.charAt(index);
            msg.append(letter);
        }
        return msg.toString();
    }

    public BigNumber generateKorX(BigNumber p) {
        return BigNumber.getRandNumber(p);
    }

    private void generatePAndG(int maxNumberOfDigit) {
        p = PrimeNumbers.getPrimeNumber(10, maxNumberOfDigit);
        // g можно выбоать любое число большее 1. т.к. p простое число, а g должен быть взаимно прост с p
        int b = p.getBaseNumSys();
        int startValue = ThreadLocalRandom.current().nextInt(2, b);
        g = new BigNumber(10,true,startValue);
        while (!MultiplicativeGroup.isPrimitiveRoot(g, p)) {
            g = BigNumberOperations.inc(g);
        }
    }
}
