package group;

import numbers.BigNumber;
import numbers.BigNumberOperations;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class IntegerSet {
    public static Set<BigNumber> getSet(BigNumber g, BigNumber module) {
        Set<BigNumber> set = new LinkedHashSet<>();
        BigNumber element = new BigNumber(g);
        BigNumber i = new BigNumber(10,"0");
        while (i.compareTo(module) < 0){
            i = BigNumberOperations.inc(i);
            set.add(element);
            element = BigNumberOperations.mullRemainder(element,g,module);
        }
        set.add(element);
        return set;
    }
}
