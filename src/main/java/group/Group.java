package group;

import numbers.BigNumber;
import numbers.BigNumberOperations;

import java.util.List;
import java.util.Set;

public class Group {
    public static boolean isGroup(Set<BigNumber> set, BigNumber module) {
        boolean isGroup = false;
        for (BigNumber num: set) {
            if (!BigNumberOperations.isInvertible(num,module)) {
                return false;
            }
            if (num.isEqualDigit(1)) {
               isGroup = true;
            }
        }
        return isGroup;
    }
}
