package numbers;

import ciphers.SingleVariableFunction;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class BigNumber implements Comparable<BigNumber> {

    private int baseNumSys;

    private Vector<Integer> number;

    private boolean sign = true;

    public BigNumber(int baseNumSys, int size, boolean isPositive) {
        this.baseNumSys = baseNumSys;
        number = new Vector<Integer>(size);
        this.sign = isPositive;
    }

    public BigNumber(int baseNumSys, String strNumber) {
        this.baseNumSys = baseNumSys;
        this.setNumber(strNumber);
    }

    public BigNumber(BigNumber bigNumber) {
        this.baseNumSys = bigNumber.getBaseNumSys();
        this.sign = bigNumber.getSign();
        this.number = new Vector<>(bigNumber.getNumber());
    }

    public BigNumber(int baseNumSys, boolean isPositive, Collection<Integer> number) {
        this.baseNumSys = baseNumSys;
        this.sign = isPositive;
        this.number = new Vector<>();
        this.number.addAll(number);
    }

    public BigNumber(int baseNumSys, boolean isPositive, long num) {
        this.baseNumSys = baseNumSys;
        this.sign = isPositive;
        setNumber(num);
    }

    public BigNumber(int baseNumSys) {
        number = new Vector<>();
        this.baseNumSys = baseNumSys;
    }

    public BigNumber(int baseNumSys, boolean isPositive) {
        number = new Vector<>();
        this.sign = isPositive;
        this.baseNumSys = baseNumSys;
    }

    public int getBaseNumSys() {
        return this.baseNumSys;
    }

    public int getDigitAt(int index) {
        return number.get(index);
    }

    public int getLastDigit() {
        return number.lastElement();
    }

    public int getSize() {
        return number.size();
    }

    public Vector<Integer> getNumber() {
        return this.number;
    }

    public boolean getSign() {
        return this.sign;
    }

    public int getFirstDigit() {
        return this.number.firstElement();
    }

    public void setSign(boolean sign) {
        this.sign = sign;
    }

    public void setDigitAt(int digit, int index) {
        number.setElementAt(digit, index);
    }

    //??????????, ?????????????? ???? ???????????? ???????????????? ???????????? ????????, ?????????????? ?? ??????????
    public void setNumber(String strToNumber) {
        if (strToNumber == null)
            throw new NullPointerException("strToNumber is null");
        if (strToNumber.isEmpty())
            throw new IllegalArgumentException("strToNumber is empty");
        number = new Vector<>(strToNumber.length());
        int end = 0;
        if (strToNumber.charAt(0) == '-')//???????? ?????????? ???????????????????? ?? ?????????? ???? ???????????????????? ?????? ?? ???????????? ???????????????? ???? ?????????????????? ??????????????
        {
            end++;
            this.sign = false;
        } else
            this.sign = true;
        for (int i = strToNumber.length() - 1; i >= end; i--) {
            number.add(Character.digit(strToNumber.charAt(i), baseNumSys));
        }

    }

    private void setNumber(long num) {
        number = new Vector<>();

        do {
            number.add((int) (num % baseNumSys));
            num /= baseNumSys;
        } while (num != 0);
    }

    public int compare(BigNumber o) {
        return compareTo(o);
    }

    public boolean isEqualDigit(int digit) {
        return number.size() == 1 && getFirstDigit() == digit;
    }

    public void addDigit(int digit) {
        if (digit < 0) {
            throw new IllegalArgumentException("digit is negative");
        }
        number.add(digit);
    }

    public void addDigit(int index, int digit) {
        number.add(index, digit);
    }

    public void addNum(BigNumber num) {
        if ((num.getBaseNumSys() != baseNumSys)) {
            throw new IllegalArgumentException("base num system is incorrect");
        }
        for(int item : num.getNumber()) {
            number.add(item);
        }
    }

    public void removeDigitAt(int index) {
        number.remove(index);
    }

    public String toString() {
        //TODO: ???? ????????????????????
        if (number.size() == 0) {
            return "0";
        }
        StringBuilder stringBuilder = new StringBuilder(number.size());
        if (!sign)
            stringBuilder.append("-");
        //removeExtraZeros();
        for (int i = number.size() - 1; i >= 0; i--) {
            stringBuilder.append(Character.forDigit(number.get(i), baseNumSys));
        }

        return stringBuilder.toString();
    }

    //??????????, ?????????????? ?????????????????? ???????????? ???????? ????????????
    public void fillByDigit(int digit) {
        for (int i = 0; i < number.capacity(); i++)
            this.number.add(digit);
    }

    //?????????? ?????????????? ?????????????? ???????????? ???????? ?? ??????????
    public void removeExtraZeros() {
        //???????????????? ?????????? ?? ??????????
        while ((number.size() >= 2) && (number.lastElement() == 0)) {
            number.remove(number.size() - 1);
        }
    }

    public void clear() {
        number.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BigNumber bigNumber = (BigNumber) o;
        return baseNumSys == bigNumber.baseNumSys && sign == bigNumber.sign && Objects.equals(number, bigNumber.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseNumSys, number, sign);
    }

    @Override
    public int compareTo(BigNumber o) {
        int indexNonZeroFirst = this.getSize() - 1, indexNonZeroSecond = o.getSize() - 1;

        //???????? ?? ???????????? ???????? ????????, ???????????????????? ????:
        while (indexNonZeroFirst >= 0 && this.getDigitAt(indexNonZeroFirst) == 0) {
            indexNonZeroFirst--;
        }
        while (indexNonZeroSecond >= 0 && o.getDigitAt(indexNonZeroSecond) == 0) {
            indexNonZeroSecond--;
        }
        if (indexNonZeroFirst != indexNonZeroSecond)
            return indexNonZeroFirst - indexNonZeroSecond;

        //??.??. ?????????? ???????????????????? ?????????????????????? ?????? ???????? ?????????? ???????????????????? ?????????? ???? ?????? ????????????(???? ??????.) ?????????? ???????????????? ???? ???????????? ??????????
        for (int i = indexNonZeroFirst; i >= 0; i--) {
            int difference = this.getDigitAt(i) - o.getDigitAt(i);
            if (difference != 0)//???????? ?????????????? ???? ???????????????????? ??????????
                return difference;
        }
        return 0;
    }

    public static BigNumber getRandNumber(BigNumber max) {
        BigNumber randNum = new BigNumber(max.getBaseNumSys(), max.getSign());
        max.removeExtraZeros();
        if (max.getSize() == 1) {
            randNum.addDigit(max.getFirstDigit());
        } else {
            for (int i = 0; i < max.getSize() - 1; i++) {
                randNum.addDigit(ThreadLocalRandom.current().nextInt(0, max.getBaseNumSys()));
            }
        }
        return randNum;

    }

    public static BigNumber getRandNumber(int size, int baseNumSys) {
        BigNumber randNum = new BigNumber(baseNumSys, size, true);
        for (int i = 0; i < size; i++) {
            randNum.addDigit(ThreadLocalRandom.current().nextInt(0, baseNumSys));
        }
        return randNum;
    }

    public BigNumber getHighPart() {
        return new BigNumber(baseNumSys, sign, number.subList(number.size() / 2, number.size()));
    }

    public BigNumber getLowPart() {
        if (number.size() == 1) {
            return new BigNumber(baseNumSys, sign, number);
        }
        return new BigNumber(baseNumSys, sign, number.subList(0, number.size() / 2));
    }

    public List<BigNumber> divideInto(int parts) {
        if (parts < 0) {
            throw new IllegalArgumentException("parts is negative");
        }
        if (parts > number.size()) {
            throw new IllegalArgumentException("size less than parts");
        }

        ArrayList<BigNumber> nums = new ArrayList<>(parts);

        int partSize = number.size() / parts;
        if ((number.size() % parts) != 0) {
            partSize++;
        }
        //TODO: ???????? ????????????????
        for (int i = 0, k = 0; i < parts; i++) {
            BigNumber num = new BigNumber(baseNumSys);
            for (int j = 0; j < partSize && k < number.size(); j++) {
                num.addDigit(number.get(k++));
            }
            nums.add(num);
        }
        return nums;
    }

    public static BigNumber merge(List<BigNumber> numbers) {
        BigNumber mergeNum = new BigNumber(numbers.get(0).getBaseNumSys());
        for (BigNumber num : numbers) {
            mergeNum.addNum(num);
        }
        return mergeNum;
    }

    public static BigNumber linearFeedbackShiftRegister(BigNumber start,
                                                 SingleVariableFunction function, int size) {
        start = BigNumberOperations.convertTo(start, 2);
        BigNumber copyStart = new BigNumber(start);
        BigNumber rez = new BigNumber(2, size, true);
        int period = -1;

        boolean finded = false;
        for (int i = 0; i < size; i ++) {

            int temp = function.calculate(copyStart);
            copyStart = BigNumberOperations.cyclicShift(copyStart, 1);
            rez.addDigit(copyStart.getFirstDigit());
            copyStart.setDigitAt(temp, 0);

            if (copyStart.compareTo(start) == 0 && !finded) {
                period = i + 1;
                finded = true;
            }
        }
        System.out.println("???????????? ????????????????: " + period);
        return rez;
    }
}
