package signature;

import junit.framework.TestCase;
import numbers.BigNumber;

public class SignatureElGamalTest extends TestCase {

    public void testGenerateDigitalSignature() {
        BigNumber p = new BigNumber(10, "1553");
        BigNumber g = new BigNumber(10, "17");
        BigNumber x = new BigNumber(10, "14");
        BigNumber k = new BigNumber(10, "117");
        BigNumber msg = new BigNumber(10, "112");
        SignatureElGamal elGamal = new SignatureElGamal(p,g,x,k);
        assertEquals("1268", elGamal.generateDigitalSignature(msg).toString());

    }

    public void testIsValidSignature() {
        BigNumber p = new BigNumber(10, "1553");
        BigNumber g = new BigNumber(10, "17");
        BigNumber x = new BigNumber(10, "14");
        BigNumber k = new BigNumber(10, "117");
        BigNumber msg = new BigNumber(10, "112");
        SignatureElGamal elGamal = new SignatureElGamal(p,g,x,k);
        BigNumber signature = elGamal.generateDigitalSignature(msg);
        assertEquals(true,elGamal.isValidSignature(msg, signature) );
    }
}