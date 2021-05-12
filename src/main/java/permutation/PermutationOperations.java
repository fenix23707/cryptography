package permutation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;

public class PermutationOperations {

    public static Permutation mull(Permutation p1, Permutation p2) {
        if (p1.getSize() != p2.getSize()) {
           throw new IllegalArgumentException("permutation sizes are not equals");
        }
        Permutation mull = new Permutation();
        for (int num2 : p2) {
            int n = p1.get(num2);
            mull.add(n);
        }
        return mull;
    }

    public  static ArrayList<Permutation> getCyclicGroup(Permutation f) {
        Permutation elementOfGroup = f;
        ArrayList<Permutation> permutations = new ArrayList<>();
        permutations.add(elementOfGroup);
        while (!elementOfGroup.isIdentityElement()) {
            elementOfGroup = mull(elementOfGroup, f);
            permutations.add(elementOfGroup);
        }
        return permutations;
    }
}
