package permutation;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


import java.util.LinkedHashSet;


public class PermutationTest {
    Permutation permutation;

    public Permutation createPermutation() {
        LinkedHashSet<Integer> integers = new LinkedHashSet<>();
        integers.add(5);
        integers.add(1);
        integers.add(4);
        integers.add(2);
        integers.add(3);
        return new Permutation(integers);
    }

    @Test
    public void iteratorTest() {
        Permutation permutation = createPermutation();
        int[] actualsInts = new int[5];
        int i = 0;
        for (Integer num: permutation) {
            actualsInts[i++] = num;
        }

        assertArrayEquals(new int[]{5,1,4,2,3},actualsInts);
    }

    @Test
    public void parseFromStringAndIteratorTest() {
        Permutation permutation = new Permutation("5 1 4 2 3");
        int[] actualsInts = new int[5];
        int i = 0;
        for (Integer num: permutation) {
            actualsInts[i++] = num;
        }
        assertArrayEquals(new int[]{5,1,4,2,3},actualsInts);
    }

    @Test
    public void parseFromStringAndIteratorTest2() {
        Permutation permutation = new Permutation("     5 1 4        2 3       ");
        int[] actualsInts = new int[5];
        int i = 0;
        for (Integer num: permutation) {
            actualsInts[i++] = num;
        }
        assertArrayEquals(new int[]{5,1,4,2,3},actualsInts);
    }

    @Test
    public void parseFromStringAndIteratorTest3() {
        Permutation permutation = new Permutation(" 2 2  ");
        int[] actualsInts = new int[1];
        int i = 0;
        for (Integer num: permutation) {
            actualsInts[i++] = num;
        }
        assertArrayEquals(new int[]{2},actualsInts);
    }

    @Test
    public void toStringTest() {
        Permutation permutation = createPermutation();
        assertEquals(true,"5 1 4 2 3".equals(permutation.toString()));
    }

    @Test
    public void testIsIdentityElement() {
        assertEquals(true, new Permutation("1 2 3 4 5 6").isIdentityElement());
    }
}
