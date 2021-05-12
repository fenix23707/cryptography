package permutation;

import java.util.*;

public class Permutation implements Iterable<Integer>{
    private Map<Integer, Integer> permutation = null;

    public Permutation(String permutation) {
        this.permutation = parsePermutation(permutation);
    }

    public Permutation(LinkedHashSet<Integer> permutation) {
        this.permutation = parsePermutation(permutation);
    }

    public Permutation() {
        permutation = new TreeMap<>();
    }

    private Map<Integer, Integer> parsePermutation(String str) {
        String[] numbersInStr = str.trim().split("\s+");
        LinkedHashSet<Integer> numbers = new LinkedHashSet<>();
        for (String numInStr: numbersInStr) {
            numbers.add(Integer.parseInt(numInStr));
        }
        return parsePermutation(numbers);
    }

    private Map<Integer, Integer> parsePermutation(LinkedHashSet<Integer> numbers) {
        Map<Integer, Integer> permutation = new HashMap<>();
        int i = 1;
        for (int num: numbers) {
            if (num < 0) {
                throw new IllegalArgumentException("in permutation can't be negative numbers");
            }
            permutation.put(i++,num);
        }
        return permutation;
    }

    public int getSize() {
        return permutation.size();
    }

    public void add(int num) {
        if (num < 0) {
            throw new IllegalArgumentException("in permutation can't be negative numbers");
        }
        permutation.put(getSize() + 1,num);
    }

    public int get(int key) {
        if (!permutation.containsKey(key)) {
           throw new IllegalArgumentException("there is not this key: " + key);
        }
        return permutation.get(key);
    }

    public Permutation getInverse() {
        Map<Integer, Integer> inverseMap = new TreeMap<>();
        for (Map.Entry<Integer,Integer> element: permutation.entrySet()) {
            inverseMap.put(element.getValue(),element.getKey());
        }
        LinkedHashSet inverseSet = new LinkedHashSet();
        for (int num: inverseMap.values()) {
            inverseSet.add(num);
        }
        return new Permutation(inverseSet);
    }

    public boolean isIdentityElement() {
        for (Map.Entry<Integer, Integer> element: this.permutation.entrySet()) {
            if (element.getValue() != element.getKey()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            private int key = 1;
            @Override
            public boolean hasNext() {
                return permutation.containsKey(key);
            }

            @Override
            public Integer next() {
                return permutation.get(key++);
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder permutationStr = new StringBuilder();
        for (int num: this) {
            permutationStr.append(String.valueOf(num)).append(" ");
        }
        return permutationStr.toString().trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permutation that = (Permutation) o;
        return Objects.equals(permutation, that.permutation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(permutation);
    }
}
