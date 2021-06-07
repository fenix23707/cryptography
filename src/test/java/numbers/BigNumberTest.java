package numbers;

import ciphers.SingleVariableFunction;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void testDivideInto() {
        BigNumber number = new BigNumber(10,"11111111");
        List<BigNumber> nums = number.divideInto(2);

        assertEquals("1111",nums.get(0).toString());
        assertEquals("1111",nums.get(1).toString());
    }

    @Test
    public void testDivideIntoOddSize() {
        BigNumber number = new BigNumber(10,"1111111");
        List<BigNumber> nums = number.divideInto(2);

        assertEquals("1111",nums.get(0).toString());
        assertEquals("111",nums.get(1).toString());
    }

    @Test
    public void testDivideIntoSizeEqualsParts() {
        BigNumber number = new BigNumber(10,"10");
        List<BigNumber> nums = number.divideInto(2);

        assertEquals("0",nums.get(0).toString());
        assertEquals("1",nums.get(1).toString());
    }

    @Test
    public void testAddNum() {
        BigNumber num1 = new BigNumber(10,"12");
        BigNumber num2 = new BigNumber(10,"34");
        num1.addNum(num2);
        assertEquals("3412",num1.toString());
    }

    @Test
    public void testMerge() {
        BigNumber number = new BigNumber(10,"11110000");
        List<BigNumber> nums = number.divideInto(2);

        assertEquals("11110000",BigNumber.merge(nums).toString());
    }

    @Test
    public void testMergeOddSize() {
        BigNumber number = new BigNumber(10,"1234567");
        List<BigNumber> nums = number.divideInto(2);
        assertEquals("1234567",BigNumber.merge(nums).toString());
    }

    @Test
    public void testMergeSizeEqualsOne() {
        BigNumber number = new BigNumber(10,"1");
        List<BigNumber> nums = number.divideInto(1);

        assertEquals("1",BigNumber.merge(nums).toString());
    }

    @Test
    public void testLinearFeedbackShiftRegister() {
        SingleVariableFunction function = x -> {
            int sum = 0;
            sum += x.getDigitAt(9);
            sum += x.getDigitAt(6);
            sum += x.getDigitAt(3);
            sum += x.getDigitAt(2);
            sum += x.getDigitAt(0);
            return sum%2;
        };

        BigNumber start = new BigNumber(2, "1001001001");
        BigNumber rez = BigNumber.linearFeedbackShiftRegister(start, function, 13);
        assertEquals("1001001001001", rez.toString());
    }

}