package group;

import numbers.BigNumber;
import numbers.BigNumberOperations;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class MultiplicativeGroup {
    public static Set<BigNumber> getGroupOfIntegersModulo(BigNumber modulo) {
        BigNumber elementGroup = new BigNumber(modulo.getBaseNumSys(),"1");
        Set<BigNumber> groupOfIntegers = new LinkedHashSet<>();
        do {
            if (BigNumberOperations.nodEuclidean(elementGroup,modulo).isEqualDigit(1)) {
                groupOfIntegers.add(elementGroup);
            }
            elementGroup = BigNumberOperations.inc(elementGroup);
        } while(elementGroup.compare(modulo) < 0);
        return groupOfIntegers;
    }

    public static boolean isMultiplicativeGroup(Set<BigNumber> set, BigNumber module) {
        Set<BigNumber> multiplicativeGroup = getGroupOfIntegersModulo(module);
        if (multiplicativeGroup.size() != set.size()) {
           return false;
        }

        for (BigNumber num: multiplicativeGroup) {
            if (!set.contains(num)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isCyclicGroup(Set<BigNumber> set, BigNumber module) {
        BigNumber sizeGroup = new BigNumber(module.getBaseNumSys(),true,set.size());
        //TODO: сделать номарльную проверку размера
//        return isMultiplicativeGroup(set, module) && ;
        return true;
    }

    public static List<BigNumber> getPrimitiveRootModulo(BigNumber module) {
        if (!checkExistPrimitiveRootModulo(module)) {
            throw new IllegalArgumentException("primitive roots don't exist");
        }
        int numberOfRoots = Integer.parseInt(BigNumberOperations.functionEuler(
                        BigNumberOperations.functionEuler(module)).toString());
        int count = 0;
        BigNumber root =  BigNumberOperations.dec(module);
        List<BigNumber> roots = new ArrayList<>();
        while(count != numberOfRoots) {
            if (isPrimitiveRoot(root,module)) {
                count++;
                roots.add(root);
            }
            root = BigNumberOperations.dec(root);
            root.removeExtraZeros();
        }
        return roots;
    }

    public static BigNumber getPrimitiveRoot(BigNumber m) {
        int b = m.getBaseNumSys();
        int startValue = ThreadLocalRandom.current().nextInt(2,b);
        BigNumber root = new BigNumber(b, String.valueOf(startValue));
        while (!isPrimitiveRoot(root,m)) {
            root = BigNumberOperations.inc(root);
        }
        return root;
    }

    public static boolean isPrimitiveRoot(BigNumber root, BigNumber m) {
        Vector<BigNumber> primeNumbers = BigNumberOperations.factorization(
                                    BigNumberOperations.functionEuler(m));
        for(int i = 0; i < primeNumbers.size(); i++) {
            BigNumber remainder= BigNumberOperations.degreeRemainder2(root,primeNumbers.get(i),m);
            if (remainder.isEqualDigit(1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkExistPrimitiveRootModulo(BigNumber module) {
        Vector<BigNumber> primeNumbers = BigNumberOperations.factorization(module);
        if (primeNumbers.size() == 1 && (primeNumbers.get(0).isEqualDigit(2) || primeNumbers.get(0).isEqualDigit(4))) {
            return true;
        }
        BigNumber p = primeNumbers.get(primeNumbers.size() - 1);
        for(int i = 1; i < primeNumbers.size(); i++) {
            if (!primeNumbers.get(i).equals(p)) {
                return false;
            }
        }
        return true;
    }
}
