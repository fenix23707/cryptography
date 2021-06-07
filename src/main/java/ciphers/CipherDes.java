package ciphers;

import numbers.BigNumber;
import numbers.BigNumberOperations;

import java.util.ArrayList;
import java.util.List;

public class CipherDes {
    public BigNumber initialPermutation(BigNumber dataBlock) {
        if (dataBlock.getSize() != 64 || dataBlock.getBaseNumSys() != 2) {
            throw new IllegalArgumentException("dataBlock is incorrect");
        }
        BigNumber permutation = new BigNumber(2);
        int index = 57;
        for (int i = 0; i < 64; i++) {
            if (i == 24) {
                System.out.println();
            }
            permutation.addDigit(dataBlock.getDigitAt(index));
            index = (index - 8) % 64;

            if (index < 0) {
                if (index == -1) {
                    index = 56;
                } else {
                    index = (index + 66) % 64;
                }
            }
        }
        return permutation;
    }

    public BigNumber inversePermutation(BigNumber num) {
        int[] table =
                {40, 8, 48, 16, 56, 24, 64, 32,
                        39, 7, 47, 15, 55, 23, 63, 31,
                        38, 6, 46, 14, 54, 22, 62, 30,
                        37, 5, 45, 13, 53, 21, 61, 29,
                        36, 4, 44, 12, 52, 20, 60, 28,
                        35, 3, 43, 11, 51, 19, 59, 27,
                        34, 2, 42, 10, 50, 18, 58, 26,
                        33, 1, 41, 9, 49, 17, 57, 25};

        BigNumber newNum = new BigNumber(num.getBaseNumSys(), 10, true);
        for (int i = 0; i < table.length; i++) {
            newNum.addDigit(num.getDigitAt(table[i] - 1));
        }
        return newNum;
    }

    public BigNumber functionDes(BigNumber halfBlock, BigNumber key) {
        halfBlock = BigNumberOperations.convertTo(halfBlock, 2);
        key = BigNumberOperations.convertTo(key, 2);
        if (halfBlock.getSize() != 32 || key.getSize() != 48) {
            throw new IllegalArgumentException("block or key is invalid");
        }
        //расширение
        halfBlock = expand(halfBlock);
        BigNumber h = BigNumberOperations.xor(halfBlock, key);
        //Начиная с этого момента не тестировал:
        //разбиение h на 8 частей
        List<BigNumber> partsH = h.divideInto(8);
        int i = 0;
        BigNumber newH = new BigNumber(h.getBaseNumSys());
        //для каждой части переход от 6 бит к 4 при помощи      convertWithS()
        for (BigNumber part : partsH) {
            //соединение вмести
            newH.addNum(convertWithS(part, i++));
        }

        // перестановка p
        return permutationP(newH);
    }

    public BigNumber convertWithS(BigNumber num, int indexTable) {
        int s[][][] = {
                {//1
                        {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
                        {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
                        {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
                        {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
                },
                {//2
                        {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
                        {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
                        {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
                        {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
                },
                {//3
                        {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
                        {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
                        {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
                        {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
                },
                {//4
                        {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
                        {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
                        {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
                        {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
                },
                {//5
                        {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
                        {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
                        {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
                        {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
                },
                {//6
                        {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
                        {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
                        {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
                        {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
                },
                {//7
                        {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
                        {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
                        {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
                        {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
                },
                {//8
                        {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
                        {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
                        {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
                        {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
                }
        };
        if (num.getSize() != 6) {
            throw new IllegalArgumentException("num is very big");
        }
        BigNumber collBigNum = new BigNumber(2);
        collBigNum.addDigit(num.getLastDigit());
        collBigNum.addDigit(num.getFirstDigit());
        int row = Integer.parseInt(BigNumberOperations.convertToDecimal(collBigNum).toString());

        BigNumber rowBigNum = new BigNumber(2);
        for (int i = 1; i < 5; i++) {
            rowBigNum.addDigit(num.getDigitAt(i));
        }

        int coll = Integer.parseInt(BigNumberOperations.convertToDecimal(rowBigNum).toString());
        int n = s[indexTable][row][coll];

        BigNumber t = new BigNumber(num.getBaseNumSys(), true, n);
        //добавление 0 до 4 бит
        while (t.getSize() != 4) {
            t.addDigit(0);
        }
        return t;
    }

    public BigNumber expand(BigNumber block) {
        block = BigNumberOperations.convertTo(block, 2);
        if (block.getSize() != 32) {
            throw new IllegalArgumentException("block size: "
                    + block.getSize() + " is very big");
        }
        BigNumber expandBlock = new BigNumber(2, 48, true);
        expandBlock.fillByDigit(0);
        for (int i = 0, j = 1; i < 32; i++) {
            expandBlock.setDigitAt(block.getDigitAt(i), j++);
            if (i % 4 == 3) {
                expandBlock.setDigitAt(block.getDigitAt((i + 1) % 32), j);
                expandBlock.setDigitAt(block.getDigitAt(i), (j + 1) % 48);
                j += 2;
            }
        }
        return expandBlock;
    }

    public BigNumber permutationP(BigNumber num) {
        int[] table =
                {
                        16, 7, 20, 21, 29, 12, 28, 17,
                        1, 15, 23, 26, 5, 18, 31, 10,
                        2, 8, 24, 14, 32, 27, 3, 9,
                        19, 13, 30, 6, 22, 11, 4, 25};

        BigNumber newNum = new BigNumber(num.getBaseNumSys(), 10, true);
        for (int i = 0; i < num.getSize(); i++) {
            newNum.addDigit(num.getDigitAt(table[i] - 1));
        }
        return newNum;
    }

    public BigNumber permutationPC1(BigNumber key) {
        if (key.getSize() != 64) {
            throw new IllegalArgumentException("key size: " + key.getSize()
                    + "is less than 64");
        }
        if (key.getBaseNumSys() != 2) {
            throw new IllegalArgumentException("base num system of key is not equals 2");
        }
        int[] table = {57, 49, 41, 33, 25, 17, 9,
                1, 58, 50, 42, 34, 26, 18,
                10, 2, 59, 51, 43, 35, 27,
                19, 11, 3, 60, 52, 44, 36,
                63, 55, 47, 39, 31, 23, 15,
                7, 62, 54, 46, 38, 30, 22,
                14, 6, 61, 53, 45, 37, 29,
                21, 13, 5, 28, 20, 12, 4};
        BigNumber newKey = new BigNumber(key.getBaseNumSys(), 56, true);
        newKey.fillByDigit(0);
        for (int i = 0; i < table.length; i++) {
            int temp = key.getDigitAt(table[i] - 1);
            newKey.setDigitAt(temp, i);
        }
        return newKey;
    }

    public BigNumber permutationPC2(BigNumber key) {
        int[] table = {14, 17, 11, 24, 1, 5,
                3, 28, 15, 6, 21, 10,
                23, 19, 12, 4, 26, 8,
                16, 7, 27, 20, 13, 2,
                41, 52, 31, 37, 47, 55,
                30, 40, 51, 45, 33, 48,
                44, 49, 39, 56, 34, 53,
                46, 42, 50, 36, 29, 32};
        BigNumber newKey = new BigNumber(key.getBaseNumSys());
        for (int i = 0; i < table.length; i++) {
            int temp = key.getDigitAt(table[i] - 1);
            newKey.addDigit(temp);
        }
        return newKey;
    }

    public BigNumber changeKey(BigNumber key) {
        BigNumber temp = permutationPC1(key);

        List<BigNumber> keys = temp.divideInto(2);
        BigNumber d = keys.get(0);
        BigNumber c = keys.get(1);
        for (int i = 0; i < 16; i++) {

            int shift;
            if (i < 2 || i == 8 || i == 15) {
                shift = 1;
            } else {
                shift = 2;
            }
            c = BigNumberOperations.cyclicShift(c, shift);
            d = BigNumberOperations.cyclicShift(d, shift);

        }
        List<BigNumber> list = new ArrayList<>();
        list.add(d);
        list.add(c);
        temp = BigNumber.merge(list);
        temp = permutationPC2(temp);
        return temp;
    }

    public BigNumber expandKey(BigNumber key) {
        key = BigNumberOperations.convertTo(key,2);

        if (key.getSize() != 56) {
            throw new IllegalArgumentException("key size: " + key.getSize() + "is incorrect");
        }

        BigNumber newNum = new BigNumber(2);
        for (int i = 0, k = 0; i < 8; i++) {
            int binSum = 1;
            for (int j = 0; j < 7; j++) {
                int temp = key.getDigitAt(k++);
                newNum.addDigit(temp);
                binSum = (binSum + temp) % 2;
            }
            newNum.addDigit(binSum);
        }
        return newNum;
    }

    public BigNumber encrypt(BigNumber msg, BigNumber key) {


        BigNumber binMsg = BigNumberOperations.convertTo(msg, 2);
        System.out.println("Сообщение в битовом виде: "+ binMsg);
        BigNumber binKey = BigNumberOperations.convertTo(key, 2);
        System.out.println("Ключ в битовом виде: "+ binKey);
        binKey = expandKey(binKey);
        System.out.println("Ключ с учетом битов контроля четности: " + binKey);

        //1
        BigNumber ip = initialPermutation(binMsg);
        System.out.println("ip: " + ip);
        //2
        List<BigNumber> parts = ip.divideInto(2);
        BigNumber l = parts.get(0);
        BigNumber h = parts.get(1);

        //key: 1) PC1:
        BigNumber keyPC1 = permutationPC1(binKey);
        //key: 2)
        List<BigNumber> keys = keyPC1.divideInto(2);
        BigNumber d = keys.get(0);
        BigNumber c = keys.get(1);
        System.out.println();
        for (int i = 0; i < 16; i++) {
            System.out.println(i + ":");
            System.out.println("h: " + h);
            System.out.println("l: " + l);

            //key: 3a)
            int shift;
            if (i < 2 || i == 8 || i == 15) {
                shift = 1;
            } else {
                shift = 2;
            }
            c = BigNumberOperations.cyclicShift(c, shift);
            d = BigNumberOperations.cyclicShift(d, shift);

            //key: 3b)
            List<BigNumber> list = new ArrayList<>();
            list.add(d);
            list.add(c);
            BigNumber roundKey = permutationPC2( BigNumber.merge(list));
            System.out.println("Ki: " + roundKey);


            BigNumber rezFunc = functionDes(l, roundKey);
            System.out.println("f(k,l): " + rezFunc);

            h = BigNumberOperations.xor(h,rezFunc);
            System.out.println();
        }
        System.out.println("h: " + h);
        System.out.println("l: " + l);
        List<BigNumber> list = new ArrayList<>();
        list.add(l);
        list.add(h);
        BigNumber t = BigNumber.merge(list);
        BigNumber cipher = initialPermutation(t);

        return cipher;
    }
}
