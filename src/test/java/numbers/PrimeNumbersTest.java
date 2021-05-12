package numbers;

import junit.framework.TestCase;
import org.junit.Test;

public class PrimeNumbersTest extends TestCase {
    @Test
    public void testIsPrimeNumberSmallNumber() {
        BigNumber primeNumber = new BigNumber(10,"5");
        assertEquals(true,PrimeNumbers.isPrimeNumber(primeNumber,5));
    }

    @Test
    public void testIsPrimeNumberMidlNumber() {
        BigNumber primeNumber = new BigNumber(10,"8708670011");
        assertEquals(true,PrimeNumbers.isPrimeNumber(primeNumber,5));
    }
}