package ciphers;

import numbers.BigNumber;
import numbers.BigNumberOperations;
import numbers.PrimeNumbers;

import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CipherRsaCrt{
    private int maxSize = 20;
    private String alphabet = " абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    private BigNumber p;
    private BigNumber q;
    private BigNumber e;
    private BigNumber n;
    private BigNumber d;


    public CipherRsaCrt(BigNumber p, BigNumber q, BigNumber e) {
        this.p = p;
        this.q = q;
        this.e = e;
        this.n = BigNumberOperations.mull(q,p);
    }

    public CipherRsaCrt() {
        generateKeys(maxSize);
        this.n = BigNumberOperations.mull(q,p);
    }

    public BigNumber encryption(String msg) {
        return encryption(charToBigNumber(msg));
    }


    public BigNumber findCrt(BigNumber num, BigNumber pOrQ, BigNumber s) {
        BigNumber temp = new BigNumber(num.getBaseNumSys());
        BigNumberOperations.div(num,pOrQ,temp);
        return BigNumberOperations.degreeRemainder2(temp,s,pOrQ);
    }

    public BigNumber encryption(BigNumber msgNum) {
        if( msgNum.getSize() > maxSize) {
            maxSize = msgNum.getSize();
            generateKeys(maxSize);
        }
        BigNumber a = findCrt(msgNum,p, e);
        BigNumber b = findCrt(msgNum, q, e);
        BigNumber inverseQ = BigNumberOperations.inverseBezu(q,p);
        BigNumber temp = BigNumberOperations.sub(a,b);
        temp = BigNumberOperations.mullRemainder(temp,inverseQ,p);
        temp = BigNumberOperations.mull(temp,q);
        BigNumber cipher = BigNumberOperations.sum(temp,b);
        cipher.removeExtraZeros();
        return cipher;
    }

    public String decryption(BigNumber cipher) {
        findD();
        BigNumber a = findCrt(cipher,p, d);
        BigNumber b = findCrt(cipher, q, d);
        BigNumber inverseQ = BigNumberOperations.inverseBezu(q,p);
        BigNumber temp = BigNumberOperations.sub(a,b);
       //TODO:
        temp = BigNumberOperations.mullRemainder(temp, inverseQ,p);
        temp = BigNumberOperations.mull(temp,q);
        BigNumber msgNum =  BigNumberOperations.sum(temp, b);
        return bigNUmberToString(msgNum);
    }

    private void findD() {
        BigNumber fyN = BigNumberOperations.functionEuler(p,q);
        fyN.removeExtraZeros();
        if (n.compareTo(e) <= 0 || !BigNumberOperations.nodEuclidean(fyN,e).isEqualDigit(1)) {
            throw new IllegalArgumentException("incorrect e");
        }
        this.d = BigNumberOperations.inverseBezu(e,fyN);
    }

    public BigNumber charToBigNumber(String msg) {
        StringBuilder builder = new StringBuilder(2*msg.length());
        for(int i = 0; i < msg.length(); i++) {
            String num = String.valueOf(alphabet.indexOf(msg.charAt(i)));
            if (num.length() == 1) {
                num = "0" + num;
            }
            builder.append(num);
        }
        return new BigNumber(e.getBaseNumSys(),builder.toString());
    }

    public String bigNUmberToString(BigNumber num) {
        String numStr = num.toString();
        if(numStr.length() % 2 != 0) {
            numStr = "0" + numStr;
        }
        StringBuilder msg = new StringBuilder(numStr.length());
        for(int i = 0; i < numStr.length(); i += 2) {
            String temp = numStr.substring(i,i + 2);
            int index = Integer.parseInt(temp);
            char letter = alphabet.charAt(index);
            msg.append(letter);
        }
        return msg.toString();
    }

    private void generateKeys(int maxSize) {
        p = PrimeNumbers.getPrimeNumber(10,maxSize*3/4);
        q = PrimeNumbers.getPrimeNumber(10,maxSize/4);
        BigNumber fy = BigNumberOperations.functionEuler(p,q);
        do{
            e = new BigNumber(10, true, ThreadLocalRandom.current().nextInt(5,80));
        }while (!PrimeNumbers.isPrimeNumber(e,1) && (e.compareTo(fy) != 0));
    }

}
