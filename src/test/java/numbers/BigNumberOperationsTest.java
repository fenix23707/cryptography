package numbers;

import junit.framework.TestCase;
import org.junit.Test;

public class BigNumberOperationsTest extends TestCase {
    @Test
    public void testDivZeroInEnd() {
        BigNumber n1 = new BigNumber(10,"110");
        BigNumber n2 = new BigNumber(10,"40");
        BigNumber r = new BigNumber(10);
        assertEquals("2",BigNumberOperations.div(n1,n2,r).toString());
        assertEquals("30",r.toString());
    }

    @Test
    public void testDivZeroInEnd2() {
        BigNumber n1 = new BigNumber(10,"110");
        BigNumber n2 = new BigNumber(10,"400");
        BigNumber r = new BigNumber(10);
        BigNumber q = BigNumberOperations.div(n1,n2,r);
        q.removeExtraZeros();
        r.removeExtraZeros();
        assertEquals("0",q.toString());
        assertEquals("110",r.toString());
    }

    @Test
    public void testDivZeroInEnd3() {
        BigNumber n1 = new BigNumber(10,"110");
        BigNumber n2 = new BigNumber(10,"4000");
        BigNumber r = new BigNumber(10);
        BigNumber q = BigNumberOperations.div(n1,n2,r);
        q.removeExtraZeros();
        r.removeExtraZeros();
        assertEquals("0",q.toString());
        assertEquals("110",r.toString());
    }

    @Test
    public void testDivNormal() {
        BigNumber n1 = new BigNumber(10,"11");
        BigNumber n2 = new BigNumber(10,"4");
        BigNumber r = new BigNumber(10);
        BigNumber q = BigNumberOperations.div(n1,n2,r);
        q.removeExtraZeros();
        r.removeExtraZeros();
        assertEquals("2",q.toString());
        assertEquals("3",r.toString());
    }

    @Test
    public void testDivSecondMoreFirst() {
        BigNumber n1 = new BigNumber(10,"7");
        BigNumber n2 = new BigNumber(10,"10");
        BigNumber r = new BigNumber(10);
        BigNumber q = BigNumberOperations.div(n1,n2,r);
        q.removeExtraZeros();
        r.removeExtraZeros();
        assertEquals("0",q.toString());
        assertEquals("7",r.toString());
    }

    @Test
    public void testDivSmallNumberByDigit() {
        BigNumber n1 = new BigNumber(10,"01");

        BigNumber r = new BigNumber(10);
        BigNumber q = BigNumberOperations.div(n1,2,r);
        q.removeExtraZeros();
        r.removeExtraZeros();
        assertEquals("0",q.toString());
        assertEquals("1",r.toString());
    }

    @Test
    public void testDivSmallNumberByDigit2() {
        BigNumber n1 = new BigNumber(10,"03");

        BigNumber r = new BigNumber(10);
        BigNumber q = BigNumberOperations.div(n1,5,r);
        q.removeExtraZeros();
        r.removeExtraZeros();
        assertEquals("0",q.toString());
        assertEquals("3",r.toString());
    }

    @Test
    public void testDegreeRemainder2() {
        BigNumber a = new BigNumber(10,"354082605");
        BigNumber s = new BigNumber(10,"4354335005");
        BigNumber n = new BigNumber(10,"8708670011");
        String expected = "8708670010";
        assertEquals(expected, BigNumberOperations.degreeRemainder2(a,s,n).toString());
    }

    @Test
    public void testMullBigOnSmall() {
        BigNumber n1 = new BigNumber(10,"9256831");
        BigNumber n2 = new BigNumber(10,"01");
        BigNumber mull = BigNumberOperations.mull(n1,n2);
        mull.removeExtraZeros();
        assertEquals("9256831", mull.toString());
    }

    @Test
    public void testNodEuclideanBigNum() {
        BigNumber n1 = new BigNumber(10,"15512039975758453884");
        BigNumber n2 = new BigNumber(10,"47");
        BigNumber nod = BigNumberOperations.nodEuclidean(n1,n2);
        assertEquals("47",nod.toString());
    }

    @Test
    public void testDivBigNumber() {
        BigNumber n1 = new BigNumber(10,"15512039975758453884");
        BigNumber n2 = new BigNumber(10,"47");
        BigNumber div = BigNumberOperations.div(n1,n2,null);
        assertEquals("330043403739541572",div.toString());
    }

    @Test
    public void testConvertToDecimalPositiveNumBase3() {
        BigNumber baseTreeNum = new BigNumber(3,"22");
        BigNumber decimal = BigNumberOperations.convertToDecimal(baseTreeNum);
        assertEquals(10, decimal.getBaseNumSys());
        assertEquals(true,decimal.getSign());
        assertEquals("8",decimal.toString());
    }

    @Test
    public void testConvertToDecimalNegativeNumBase3() {
        BigNumber baseTreeNum = new BigNumber(3,"-22");
        BigNumber decimal = BigNumberOperations.convertToDecimal(baseTreeNum);
        assertEquals(10, decimal.getBaseNumSys());
        assertEquals(false,decimal.getSign());
        assertEquals("-8",decimal.toString());
    }

    @Test
    public void testConvertToDecimalPositiveNumBase16() {
        BigNumber baseTreeNum = new BigNumber(16,"42149820412");
        BigNumber decimal = BigNumberOperations.convertToDecimal(baseTreeNum);
        assertEquals(10, decimal.getBaseNumSys());
        assertEquals(true,decimal.getSign());
        assertEquals("4541013689362",decimal.toString());
    }

    @Test
    public void testConvertToDecimalPositiveNumBase16Case2() {
        BigNumber baseTreeNum = new BigNumber(16,"FABCD");
        BigNumber decimal = BigNumberOperations.convertToDecimal(baseTreeNum);
        assertEquals(10, decimal.getBaseNumSys());
        assertEquals(true,decimal.getSign());
        assertEquals("1027021",decimal.toString());
    }

    @Test
    public void testConvertToDecimalNegativeNumBase16() {
        BigNumber baseTreeNum = new BigNumber(16,"-42149820412");
        BigNumber decimal = BigNumberOperations.convertToDecimal(baseTreeNum);
        assertEquals(10, decimal.getBaseNumSys());
        assertEquals(false,decimal.getSign());
        assertEquals("-4541013689362",decimal.toString());
    }

    @Test
    public void testConvertToDecimalNegativeNumBase9() {
        BigNumber baseTreeNum = new BigNumber(9,"-15514357321");
        BigNumber decimal = BigNumberOperations.convertToDecimal(baseTreeNum);
        assertEquals(10, decimal.getBaseNumSys());
        assertEquals(false,decimal.getSign());
        assertEquals("-5646244501",decimal.toString());
    }

    @Test
    public void testConvertToDecToOct() {
        BigNumber num = new BigNumber(10,"15");
        BigNumber rez = BigNumberOperations.convertTo(num,8);
        assertEquals(8, rez.getBaseNumSys());
        assertEquals(true,rez.getSign());
        assertEquals("17",rez.toString());
    }

    @Test
    public void testConvertToHexToOct() {
        BigNumber num = new BigNumber(16,"FDFDBCDD");
        BigNumber rez = BigNumberOperations.convertTo(num,8);
        assertEquals(8, rez.getBaseNumSys());
        assertEquals(true,rez.getSign());
        assertEquals("37577336335",rez.toString());
    }

    @Test
    public void testConvertToHexToBinNegative() {
        BigNumber num = new BigNumber(16,"-FDFDBCDD");
        BigNumber rez = BigNumberOperations.convertTo(num,2);
        assertEquals(2, rez.getBaseNumSys());
        assertEquals(false,rez.getSign());
        assertEquals("-11111101111111011011110011011101",rez.toString());
    }
    @Test
    public void testXorBinary() {
        BigNumber n1 = new BigNumber(2, "11");
        BigNumber n2 = new BigNumber(2,"10");
        BigNumber rez = BigNumberOperations.xor(n1,n2);
        assertEquals("01",rez.toString());
        assertEquals(2,rez.getBaseNumSys());
        assertEquals(true,rez.getSign());
    }

    @Test
    public void testXorHex() {
        BigNumber n1 = new BigNumber(16, "FFF");
        BigNumber n2 = new BigNumber(16,"ABC");
        BigNumber rez = BigNumberOperations.xor(n1,n2);
        assertEquals("543",rez.toString());
        assertEquals(16,rez.getBaseNumSys());
        assertEquals(true,rez.getSign());
    }

    @Test
    public void testXorHexDifferentSize() {
        BigNumber n1 = new BigNumber(16, "FFF");
        BigNumber n2 = new BigNumber(16,"AB");
        BigNumber rez = BigNumberOperations.xor(n1,n2);
        assertEquals("f54",rez.toString());
        assertEquals(16,rez.getBaseNumSys());
        assertEquals(true,rez.getSign());
    }

}