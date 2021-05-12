package numbers;

import junit.framework.TestCase;
import org.junit.Test;

public class BigNumberTest extends TestCase {

    @Test
    public void testGetHighPart() {
        BigNumber num = new BigNumber(2, "1100");
        BigNumber highPart = num.getHighPart();
        assertEquals("11", highPart.toString());
        assertEquals(true,highPart.getSign());
        assertEquals(2,highPart.getBaseNumSys());
    }

    public void testGetHighPartSizeIsOdd() {
        BigNumber num = new BigNumber(2, "11000");
        BigNumber highPart = num.getHighPart();
        assertEquals("110", highPart.toString());
        assertEquals(true,highPart.getSign());
        assertEquals(2,highPart.getBaseNumSys());
    }

    public void testGetHighPartSizeIsSmall() {
        BigNumber num = new BigNumber(2, "11");
        BigNumber highPart = num.getHighPart();
        assertEquals("1", highPart.toString());
        assertEquals(true,highPart.getSign());
        assertEquals(2,highPart.getBaseNumSys());
    }

    public void testGetHighPartSizeEqualsOne() {
        BigNumber num = new BigNumber(2, "1");
        BigNumber highPart = num.getHighPart();
        assertEquals("1", highPart.toString());
        assertEquals(true,highPart.getSign());
        assertEquals(2,highPart.getBaseNumSys());
    }

    public void testGetLowPart() {
        BigNumber num = new BigNumber(2, "1100");
        BigNumber lowPart = num.getLowPart();
        assertEquals("00", lowPart.toString());
        assertEquals(true,lowPart.getSign());
        assertEquals(2,lowPart.getBaseNumSys());
    }

    public void testGetLowPartSizeIsOdd() {
        BigNumber num = new BigNumber(2, "11000");
        BigNumber lowPart = num.getLowPart();
        assertEquals("00", lowPart.toString());
        assertEquals(true,lowPart.getSign());
        assertEquals(2,lowPart.getBaseNumSys());
    }

    public void testGetLowPartSizeIsSmall() {
        BigNumber num = new BigNumber(2, "11");
        BigNumber lowPart = num.getLowPart();
        assertEquals("1", lowPart.toString());
        assertEquals(true,lowPart.getSign());
        assertEquals(2,lowPart.getBaseNumSys());
    }

    public void testGetLowPartSizeIsEqualsOne() {
        BigNumber num = new BigNumber(2, "1");
        BigNumber lowPart = num.getLowPart();
        assertEquals("1", lowPart.toString());
        assertEquals(true,lowPart.getSign());
        assertEquals(2,lowPart.getBaseNumSys());
    }
}