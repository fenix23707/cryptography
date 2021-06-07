package signature;

import group.MultiplicativeGroup;
import numbers.BigNumber;
import numbers.BigNumberOperations;
import numbers.PrimeNumbers;

import java.util.concurrent.ThreadLocalRandom;

public class SignatureElGamal {
    private BigNumber x = null;

    private BigNumber p = null;

    private BigNumber g = null;

    private BigNumber y = null;

    private BigNumber r = null;

    private BigNumber s = null;

    private BigNumber k = null;

    private int maxNumberOfDigit = 5;

    public SignatureElGamal(BigNumber p, BigNumber g, BigNumber x, BigNumber k) {
        this.x = x;
        this.p = p;
        this.g = g;
        this.k = k;
    }

    public BigNumber generateDigitalSignature(BigNumber msg) {
        if (p == null || g == null) {
            p = PrimeNumbers.getPrimeNumber(msg.getBaseNumSys(), maxNumberOfDigit);
            g = MultiplicativeGroup.getPrimitiveRoot(p);
        }

        if (x == null) {
            x = BigNumber.getRandNumber(p);
        }
        y = BigNumberOperations.degreeRemainder2(g, x, p);

        if (k == null) {
            k = generateK();
        }

        r = BigNumberOperations.degreeRemainder2(g, k, p);

        BigNumber decP = BigNumberOperations.dec(p);
        BigNumber temp = BigNumberOperations.mull(r, x);
        temp = BigNumberOperations.sub(msg, temp);
        while (!temp.getSign()) {
            temp = BigNumberOperations.sum(temp, decP);
        }
        BigNumber inverseK = BigNumberOperations.inverseBezu(k, decP);
        s = BigNumberOperations.mullRemainder(inverseK, temp, decP);
        return s;
    }

    public boolean isValidSignature(BigNumber msg, BigNumber signature) {
        BigNumber expected = BigNumberOperations.degreeRemainder2(g, msg, p);
        BigNumber temp1 = BigNumberOperations.degreeRemainder2(y, r, p);
        BigNumber temp2 = BigNumberOperations.degreeRemainder2(r, signature, p);
        BigNumber actual = BigNumberOperations.mullRemainder(temp1, temp2, p);
        return expected.compareTo(actual) == 0;
    }

    private BigNumber generateK() {
        BigNumber temp = BigNumberOperations.dec(p);
        do {
            BigNumber k = BigNumber.getRandNumber(p);
            while (!BigNumberOperations.nodEuclidean(k, temp).isEqualDigit(1)) {
                k = BigNumberOperations.inc(k);
            }
        } while (p.compareTo(k) <= 0);
        return k;
    }

}
