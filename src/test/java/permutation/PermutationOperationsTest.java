package permutation;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


import java.util.LinkedHashSet;


public class PermutationOperationsTest {
    @Test
    public void testMullNormal() {
        Permutation p1 = new Permutation("5 1 4 2 3");
        Permutation p2 = new Permutation("2 1 3 5 4");
        assertEquals(true,"1 5 4 3 2".equals(PermutationOperations.mull(p1,p2).toString()));

    }
    @Test
    public void testGetCyclicGroupOrder() {
        Permutation p1 = new Permutation("5 1 4 2 3");
        assertEquals(5,PermutationOperations.getCyclicGroup(p1).size());
    }
    @Test
    public void testGetCyclicGroupNormal() {
        Permutation p1 = new Permutation("5 1 4 2 3");
        Permutation[] permutations = new Permutation[]{
            new Permutation("5 1 4 2 3"),
            new Permutation("3 5 2 1 4"),
            new Permutation("4 3 1 5 2"),
            new Permutation("2 4 5 3 1"),
            new Permutation("1 2 3 4 5")};
        assertArrayEquals(permutations,PermutationOperations.getCyclicGroup(p1).toArray());
    }
}