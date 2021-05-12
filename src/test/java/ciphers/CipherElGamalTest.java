package ciphers;

import junit.framework.TestCase;
import numbers.BigNumber;
import org.junit.Test;

public class CipherElGamalTest extends TestCase {

    @Test
    public void testDecryption() {
        BigNumber p = new BigNumber(10,"127");
        BigNumber g = new BigNumber(10,"3");
        BigNumber y = new BigNumber(10,"48");
        BigNumber o = new BigNumber(10,"22");
        BigNumber cipher = new BigNumber(10,"122");
        CipherElGamal cipherElGamal = new CipherElGamal(p,g,y,o);
        String msg = cipherElGamal.decryption(cipher);
        assertEquals("ау",msg);
    }

    @Test
    public void testEncryptionDec() {
        BigNumber p = new BigNumber(10,"23");
        BigNumber g = new BigNumber(10,"5");
        BigNumber msg = new BigNumber(10,"20");
        BigNumber k = new BigNumber(10,"3");
        BigNumber x = new BigNumber(10,"7");
        CipherElGamal cipherElGamal = new CipherElGamal(p,g);
        BigNumber cipher = cipherElGamal.encryption(msg,x,k);
        assertEquals("4",cipher.toString());
    }

    @Test
    public void testEncryptionEncAndDec() {
        BigNumber p = new BigNumber(10,"127");
        BigNumber g = new BigNumber(10,"3");
        String msg = "ау";
        BigNumber k = new BigNumber(10,"14");
        BigNumber x = new BigNumber(10,"37");
        CipherElGamal cipherElGamal = new CipherElGamal(p,g);
        BigNumber cipher = cipherElGamal.encryption(msg,x,k);

        assertEquals("ау",cipherElGamal.decryption(cipher));
    }

    @Test
    public void testEncryptionEncAndDecGenerateRandPAndQ() {
        String msg = "ау";
        CipherElGamal cipherElGamal = new CipherElGamal();
        BigNumber cipher = cipherElGamal.encryption(msg);

        assertEquals("ау",cipherElGamal.decryption(cipher));
    }
}