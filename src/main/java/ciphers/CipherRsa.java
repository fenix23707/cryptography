package ciphers;

import numbers.BigNumber;
import numbers.BigNumberOperations;
import numbers.PrimeNumbers;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CipherRsa {
    private int maxMsgSize = 20;
    protected BigNumber n;
    protected BigNumber e;
    protected BigNumber d;
    BigNumber p;
    BigNumber q;

    public CipherRsa(BigNumber module, BigNumber enc) {
        this.e = enc;
        this.n = module;
    }

    public CipherRsa(){
        generateKeys(maxMsgSize);
    }

    private void findD() {
        BigNumber fyN;
        if(p != null && q != null) {
            fyN = BigNumberOperations.functionEuler(p,q);
        } else {
            fyN = BigNumberOperations.functionEuler(n);
        }
        fyN.removeExtraZeros();
        if (n.compareTo(e) <= 0 || !BigNumberOperations.nodEuclidean(fyN,e).isEqualDigit(1)) {
            System.out.println(fyN + " " + BigNumberOperations.nodEuclidean(fyN,e));
            throw new IllegalArgumentException("incorrect e");
        }
        this.d = BigNumberOperations.inverseBezu(e,fyN);
    }

    public BigNumber encryption(BigNumber msg) {
        if (msg.compareTo(n) >= 0) {
            maxMsgSize = msg.getSize();
            generateKeys(maxMsgSize);
        }
        return BigNumberOperations.degreeRemainder2(msg,e,n);
    }

    public BigNumber decryption(BigNumber cipher) {
        findD();
        return BigNumberOperations.degreeRemainder2(cipher,d,n);
    }

    private void generateKeys(int maxSize) {
        p = PrimeNumbers.getPrimeNumber(10,maxSize*3/4);
        q = PrimeNumbers.getPrimeNumber(10,maxSize/4);
        n = BigNumberOperations.mull(p,q);
        BigNumber fy = BigNumberOperations.functionEuler(p,q);
        do{
            e = new BigNumber(10, true, ThreadLocalRandom.current().nextInt(5,80));
        }while (!PrimeNumbers.isPrimeNumber(e,1) && (e.compareTo(fy) != 0));
    }
}
