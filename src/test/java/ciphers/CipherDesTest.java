package ciphers;

import junit.framework.TestCase;
import numbers.BigNumber;
import org.junit.Test;

public class CipherDesTest extends TestCase {

    @Test
    public void testInitialPermutation() {
        CipherDes cipherDes = new CipherDes();
        BigNumber block = new BigNumber(2,
                "0000000000000010000000000000000000000000000000000000000000000001");
        String expected = "0000000000000000000000001000000000000000000000000000000000000010";
//        BigNumber extended = cipherDes.expand(block.getLowPart());
//        System.out.println(extended);
        assertEquals(expected,cipherDes.initialPermutation(block).toString());
    }
}