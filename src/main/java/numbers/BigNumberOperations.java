package numbers;


import java.util.Vector;

public class BigNumberOperations {

    public static BigNumber sum(BigNumber first, BigNumber second) {
        if (first.getBaseNumSys() != second.getBaseNumSys())
            throw new IllegalArgumentException("number system of two number is different");
        int baseNumSys = first.getBaseNumSys();

        if ((!first.getSign() && second.getSign()))//если первое отрицательное, а второе положительное
        {
            BigNumber copy = new BigNumber(first);
            copy.setSign(!first.getSign());
            return sub(second, copy);
        }
        if ((first.getSign() && !second.getSign()))//если второе отрицательное, а первое положительное
        {
            BigNumber copy = new BigNumber(second);
            copy.setSign(!second.getSign());
            return sub(first, copy);
        }

        if (first.getSize() < second.getSize()) {
            BigNumber temp = first;
            first = second;
            second = temp;
        }
        BigNumber sum = new BigNumber(first);

        byte c = 0;
        for (int i = 0; i < second.getSize(); i++) {
            int sumDigit = first.getDigitAt(i) + second.getDigitAt(i) + c;
            if (sumDigit < baseNumSys)
                c = 0;
            else
                c = 1;
            sum.setDigitAt(sumDigit % baseNumSys, i);
        }

        //на случай если 999+1 и подобных
        for (int i = second.getSize(); i < first.getSize() && c != 0; i++) {
            int sumDigit = first.getDigitAt(i) + c;
            if (sumDigit < baseNumSys)
                c = 0;
            else
                c = 1;
            sum.setDigitAt(sumDigit % baseNumSys, i);
        }
        if (c != 0)
            sum.addDigit(c);


        return sum;
    }

    public static BigNumber sub(BigNumber first, BigNumber second) {
        if (first.getBaseNumSys() != second.getBaseNumSys())
            throw new IllegalArgumentException("number system of two number is different");
        int baseNumSys = first.getBaseNumSys();

        //если первое число положительное, а второе отрицательное, или наоборот, то результат будет сумма
        if ((first.getSign() && !second.getSign()) || (!first.getSign() && second.getSign())) {
            BigNumber copy = new BigNumber(second);
            copy.setSign(!second.getSign());
            return sum(first, copy);
        }

        if (first.compare(second) < 0)//если второе число больше, то меняем их местами
        {
            BigNumber temp = first;
            first = new BigNumber(second);
            first.setSign(!second.getSign());
            second = new BigNumber(temp);
        }
        for (int i = second.getSize() - 1; i < first.getSize() - 1; i++) {
            second.addDigit((byte) 0);
        }
        BigNumber sub = new BigNumber(first);

        byte c = 0;
        for (int i = 0; i < sub.getSize(); i++)//вычитание
        {
            byte subDigit = (byte) (first.getDigitAt(i) - second.getDigitAt(i) - c);
            if (subDigit >= 0)
                c = 0;
            else
                c = 1;
            sub.setDigitAt((byte) ((subDigit + baseNumSys) % baseNumSys), i);
        }

        if (c != 0)
            sub.addDigit(c);
        //second.removeExtraZeros();
        //sub.removeExtraZeros();
        return sub;
    }

    public static BigNumber mull(BigNumber first, BigNumber second) {
        if (first.getBaseNumSys() != second.getBaseNumSys())
            throw new IllegalArgumentException("number system of two number is different");
        int baseNumSys = first.getBaseNumSys();


        BigNumber mull = new BigNumber(baseNumSys, first.getSize() + second.getSize(), first.getSign() == second.getSign());

        mull.fillByDigit(0);

        int c = 0;
        for (int i = 0; i < second.getSize(); i++)//нахождение произведения
        {
            c = 0;
            for (int j = 0; j < first.getSize(); j++) {
                int xy = (int) (mull.getDigitAt(i + j) + first.getDigitAt(j) * second.getDigitAt(i) + c);
                mull.setDigitAt(xy % baseNumSys, i + j);
                c = xy / baseNumSys;
            }
            mull.setDigitAt(c, i + first.getSize());
        }

        return mull;
    }

    //доп методы:
    public static BigNumber div(BigNumber first, BigNumber second, BigNumber remainder) {
        if (first.getBaseNumSys() != second.getBaseNumSys())
            throw new IllegalArgumentException("number system of two number is different");
        if (remainder == null) {
            remainder = new BigNumber(first.getBaseNumSys());
        }
        remainder.clear();
        first = new BigNumber(first);
        second = new BigNumber(second);
        //удаление у second 0 в конце числа:
        while ((first.getFirstDigit() == 0) && (second.getFirstDigit() == 0)) {
            first.removeDigitAt(0);
            second.removeDigitAt(0);
            remainder.addDigit(0);
        }
        while (second.getFirstDigit() == 0 && first.getSize() != 0) {
            second.removeDigitAt(0);
            remainder.addDigit(first.getFirstDigit());
            first.removeDigitAt(0);
        }

        first.removeExtraZeros();
        second.removeExtraZeros();

        if (second.getSize() < 2) {
            BigNumber r = new BigNumber(first.getBaseNumSys());
            BigNumber q = div(first, second.getLastDigit(), r);
            for (int i = 0; i < r.getSize(); i++) {
                remainder.addDigit(r.getDigitAt(i));
            }
            return q;
        }

        int baseNumSys = first.getBaseNumSys();
        boolean sign = first.getSign() == second.getSign();

        //шаг первый:
        int d = baseNumSys / (second.getLastDigit() + 1);
        first = mull(first, new BigNumber(baseNumSys, first.getSign(), d));
        second = mull(second, new BigNumber(baseNumSys, second.getSign(), d));
        second.removeExtraZeros();

        int size;
        if (first.getSize() - second.getSize() < 0) {
            size = 0;
        } else {
            size = first.getSize() - second.getSize() + 1;
        }

        BigNumber quotient = new BigNumber(baseNumSys, size, sign);
        quotient.fillByDigit(0);

        first.setSign(true);
        second.setSign(true);

        //шаг второй:
        int secondSize = second.getSize();
        for (int i = first.getSize() - 1; i >= secondSize; i--) {
            //а)
            int temp = first.getDigitAt(i) * baseNumSys + first.getDigitAt(i - 1);
            temp /= second.getLastDigit();
            int q = Math.min(temp, baseNumSys - 1);

            //б)
            int leftNum, rightNUm;
            do {
                if (second.getSize() - 2 < 0) {
                    System.out.println("pause");
                }
                leftNum = second.getLastDigit() * baseNumSys + second.getDigitAt(second.getSize() - 2);
                rightNUm = first.getDigitAt(i) * baseNumSys * baseNumSys
                        + first.getDigitAt(i - 1) * baseNumSys
                        + first.getDigitAt(i - 2);
                if (q * leftNum <= rightNUm)
                    break;
                ;
                q--;
            } while (true);

            //в)
            BigNumber powNumSys = pow(new BigNumber(baseNumSys, true, baseNumSys),
                    new BigNumber(baseNumSys, true, (i - second.getSize())));// b^(i-k)
            BigNumber secondMullPowNumSys = mull(second, powNumSys);//v*b^(i-k)
            BigNumber element = mull(secondMullPowNumSys, new BigNumber(baseNumSys, true, q));//q*v*b^(i-k)
            element.removeExtraZeros();
            first = sub(first, element);

            //г)
            if (!first.getSign()) {
                q--;
                first = sum(first, secondMullPowNumSys);
            }

            //д)
            quotient.setDigitAt(q, i - second.getSize());
        }

        //третий шаг:
       /* for(int i = 0; i < first.getSize(); i++) {
            remainder.addDigit(first.getDigitAt(i)/d);
        }*/
        BigNumber partRemainder = div(first, d, null);
        for (int num : partRemainder.getNumber()) {
            remainder.addDigit(num);
        }
        quotient.removeExtraZeros();
        return quotient;
    }

    public static BigNumber inc(BigNumber number) {
        return sum(number, new BigNumber(number.getBaseNumSys(), "1"));
    }

    public static BigNumber dec(BigNumber number) {
        return sub(number, new BigNumber(number.getBaseNumSys(), "1"));
    }

    public static BigNumber div(BigNumber number, int digit, BigNumber remainder) {
        if (digit < 0 || digit >= number.getBaseNumSys()) {
            throw new IllegalArgumentException("digit: " + digit + " is incorrect");
        }


        BigNumber copyNum = new BigNumber(number);

        int c = 0;
        for (int i = number.getSize() - 1; i >= 0; i--) {
            int element = number.getDigitAt(i) + c * number.getBaseNumSys();
            copyNum.setDigitAt(element / digit, i);
            c = element % digit;
        }
        //TODO: не тестировал нижний иф
        if (!number.getSign()) {
            c = digit - c;
        }
        //могут быть проблемы с remainder, если в метод попадет заполненый remainder, но и обнулять его тоже нельзя т.к. не будет работать основной div
        if (remainder != null) {
            remainder.clear();
            remainder.addDigit(c % digit);
        }
        copyNum.removeExtraZeros();
        return copyNum;
    }

    public static BigNumber pow(BigNumber num, BigNumber degree) {
        if (!degree.getSign()) {
            throw new IllegalArgumentException("myPow can't raise to negative degree");
        }
        if (degree.isEqualDigit(0)) {
            return new BigNumber(num.getBaseNumSys(), "1");
        }
        BigNumber pow = new BigNumber(num);

        degree = dec(degree);//degree--
        BigNumber i = new BigNumber(degree);//i = degree -1
        while (!i.isEqualDigit(0)) {//пока i!=0
            pow = mull(pow, num);
            i.removeExtraZeros();
            i = dec(i);//degree--
            pow.removeExtraZeros();
        }
        return pow;
    }

    //лаба 3:
    public static Vector<BigNumber> factorization(BigNumber num) {

        num.removeExtraZeros();
        Vector<BigNumber> divisors = new Vector<>();
        BigNumber rem = new BigNumber(num.getBaseNumSys());
        BigNumber divisor = new BigNumber(num.getBaseNumSys(), num.getSign(), 2);
        while (!num.isEqualDigit(1)) {
            BigNumber temp = div(num, divisor, rem);
            if (rem.isEqualDigit(0)) {//если num делится на делитель:
                divisors.add(divisor);
                num = temp;
            } else {
                divisor = BigNumberOperations.inc(divisor);//i++
            }
        }
        return divisors;
    }

    public static BigNumber nodFactorization(BigNumber n1, BigNumber n2) {
        if (n1.getBaseNumSys() != n2.getBaseNumSys()) {
            throw new IllegalArgumentException("base num system is not equals");
        }

        Vector<BigNumber> factorization1 = factorization(n1);
        Vector<BigNumber> factorization2 = factorization(n2);
        BigNumber nod = new BigNumber(n1.getBaseNumSys(), "1");

        int i = 0, j = 0;
        while ((i < factorization1.size()) && (j < factorization2.size())) {
            while ((i < factorization1.size()) && (j < factorization2.size()) && (factorization1.get(i).compare(factorization2.get(j)) == 0)) {
                nod = mull(nod, factorization1.get(i));
                i++;
                j++;
            }
            while ((i < factorization1.size()) && (j < factorization2.size()) && (factorization1.get(i).compare(factorization2.get(j)) < 0)) {
                i++;
            }
            while ((i < factorization1.size()) && (j < factorization2.size()) && (factorization1.get(i).compare(factorization2.get(j)) > 0)) {
                j++;
            }
        }
        nod.removeExtraZeros();
        return nod;
    }

    public static BigNumber nodEuclidean(BigNumber n1, BigNumber n2) {
        if (n1.getBaseNumSys() != n2.getBaseNumSys()) {
            throw new IllegalArgumentException("base num system is not equals");
        }

        BigNumber remainder = new BigNumber(n1.getBaseNumSys());
        while (true) {
            div(n1, n2, remainder);
            if (remainder.isEqualDigit(0)) {
                return n2;
            }
            n1 = new BigNumber(n2);
            n2 = new BigNumber(remainder);
        }
    }

    public static void identityBezu(BigNumber a, BigNumber b, BigNumber u, BigNumber v) {

        if (a.isEqualDigit(0)) {
            u.setNumber("0");
            v.setNumber("1");
            return;
        }
        //проход вниз:
        BigNumber r = new BigNumber(a.getBaseNumSys());
        BigNumber q = div(b, a, r);
        identityBezu(r, a, u, v);

        //проход вверх:
        BigNumber saveU = new BigNumber(u);
        BigNumber temp = mull(q, u);//temp = q*u
        temp.removeExtraZeros();
        temp = sub(v, temp);//temp = v - temp
        u.setNumber(temp.toString()); // u = temp
        v.setNumber(saveU.toString());// v  = saveU
    }

    public static BigNumber degreeRemainder1(BigNumber a, BigNumber s, BigNumber n) {
        //перевод в двоичную систему счисления:
        BigNumber binary;
        if (s.getBaseNumSys() == 2) {
            binary = s;
        } else {
            binary = new BigNumber(2, s.getSize(), s.getSign());
            BigNumber r = new BigNumber(s.getBaseNumSys());
            do {
                s = div(s, 2, r);
                binary.addDigit(r.getFirstDigit());
            } while (!s.isEqualDigit(0));
        }

        //нахождения остатка:
        BigNumber degreeRem = new BigNumber(a.getBaseNumSys(), "1");
        BigNumber temp = new BigNumber(a);
        for (int i = 0; i < binary.getSize(); i++) {
            div(temp, n, temp);//temp = temp%n
            if (binary.getDigitAt(i) == 1) {
                div(mull(degreeRem, temp), n, degreeRem); //degreeRem = (degreeRem * temp) % n
            }
            temp = mull(temp, temp); // temp^2
        }
        return degreeRem;
    }

    public static BigNumber degreeRemainder2(BigNumber a, BigNumber s, BigNumber n) {
        if (s.isEqualDigit(0)) {
            return new BigNumber(a.getBaseNumSys(), "1");
        }
        BigNumber r = new BigNumber(a.getBaseNumSys());

        BigNumber temp = degreeRemainder2(a, div(s, 2, r), n);
        temp = mull(temp, temp);
        temp.removeExtraZeros();

        if (s.getSign() && (s.getFirstDigit() % 2 != 0)) {//положителе и является нечетным
            temp = mull(temp, a);
        }
        div(temp, n, r);
        return r;
    }

    //Лаба 4:
    public static BigNumber sumRemainder(BigNumber a, BigNumber b, BigNumber m) {
        BigNumber remainder = new BigNumber(a.getBaseNumSys());
        div(sum(a, b), m, remainder);
        return remainder;
    }

    public static BigNumber mullRemainder(BigNumber a, BigNumber b, BigNumber m) {
        BigNumber remainder = new BigNumber(a.getBaseNumSys());
        BigNumber temp = mull(a, b);
        temp.removeExtraZeros();
        div(temp, m, remainder);
        if (!remainder.getSign()) {
            remainder = sum(remainder, m);
        }
        return remainder;
    }

    public static void printTableSumRemainder(BigNumber module) {
        BigNumber i = new BigNumber(module.getBaseNumSys(), true, 0);
        while (i.compare(module) < 0) {
            BigNumber j = new BigNumber(module.getBaseNumSys(), true, 0);
            while (j.compare(module) < 0) {
                System.out.print(sumRemainder(i, j, module) + " ");
                j = inc(j);
            }
            System.out.println();
            i = inc(i);
        }
    }

    public static void printTableMullRemainder(BigNumber module) {
        BigNumber i = new BigNumber(module.getBaseNumSys(), true, 0);
        while (i.compare(module) < 0) {
            BigNumber j = new BigNumber(module.getBaseNumSys(), true, 0);
            while (j.compare(module) < 0) {
                System.out.print(mullRemainder(i, j, module) + " ");
                j = inc(j);
            }
            System.out.println();
            i = inc(i);
        }
    }

    public static BigNumber functionEuler(BigNumber num) {
        Vector<BigNumber> factorization = factorization(num);
        //1
        if (factorization.size() == 1) {
            return dec(num);
        }
        //2
        if (factorization.size() == 2) {
            BigNumber temp = nodEuclidean(factorization.get(0), factorization.get(1));
            if (temp.compare(new BigNumber(temp.getBaseNumSys(), true, 1)) == 0) {
                return mull(functionEuler(factorization.get(0)), functionEuler(factorization.get(1)));
            }
        }

        BigNumber remainder = new BigNumber(num.getBaseNumSys());
        //3
        BigNumber firstNum = factorization.get(0);
        int j = 1;
        //проверка 3-го условия (num == p^n)
        while ((j < factorization.size()) && (firstNum.compare(factorization.get(j)) == 0)) {
            j++;
        }
        if (j == factorization.size()) {
            return sub(num, div(num, firstNum, remainder));
        }

        //4
        for (int i = 0; i < factorization.size(); ) {
            BigNumber temp = factorization.get(i);
            //пропуск повторяющихся элементов
            while ((i < factorization.size()) && (temp.compare(factorization.get(i)) == 0)) {
                i++;
            }
            num = div(num, temp, remainder);
            num = mull(num, dec(temp));
        }
        return num;
    }

    public static BigNumber functionEuler(BigNumber p1, BigNumber p2) {
        if (p1.isEqualDigit(1)) {
            return BigNumberOperations.dec(p2);
        }
        if (p2.isEqualDigit(1)) {
            return BigNumberOperations.dec(p1);
        }
        //TODO: идеально сделать проверку что p1 и p2 простые
        return BigNumberOperations.mull(BigNumberOperations.dec(p1),
                BigNumberOperations.dec(p2));
    }

    public static boolean isInvertible(BigNumber num, BigNumber m) {
        return nodEuclidean(num, m).isEqualDigit(1);
    }

    public static BigNumber inverseClass(BigNumber num, BigNumber m) throws IllegalArgumentException {
        if (!isInvertible(num, m)) {
            throw new IllegalArgumentException("number is not invertible");
        }
        BigNumber inverseNum = new BigNumber(num.getBaseNumSys(), "-1");
        BigNumber temp;
        do {
            inverseNum = inc(inverseNum);
            temp = mullRemainder(inverseNum, num, m);
            temp.removeExtraZeros();
        } while (!temp.isEqualDigit(1));
        return inverseNum;
    }

    public static BigNumber inverseBezu(BigNumber num, BigNumber m) throws IllegalArgumentException {
        if (!isInvertible(num, m)) {
            throw new IllegalArgumentException("number is not invertible");
        }
        BigNumber u = new BigNumber(num.getBaseNumSys());
        BigNumber v = new BigNumber(num.getBaseNumSys());
        identityBezu(m, num, u, v);
        if (!v.getSign()) {
            v = sum(v, m);
            v.removeExtraZeros();
        }
        return v;
    }

    public static BigNumber convertTo(BigNumber num, int baseNumSystem) {
        if (num.getBaseNumSys() == baseNumSystem) {
            return num;
        }
        if (baseNumSystem == 10) {
            return convertToDecimal(num);
        }
        if (baseNumSystem < 2) {
            throw new IllegalArgumentException(baseNumSystem + "is incorrect base system");
        }

        BigNumber decimal = convertToDecimal(num);
        BigNumber convertNum = new BigNumber(baseNumSystem);
        BigNumber digit = new BigNumber(baseNumSystem);
        BigNumber b = new BigNumber(10,true,baseNumSystem);
        do {
            decimal = div(decimal, b, digit);
            decimal.removeExtraZeros();
            convertNum.addDigit(Integer.parseInt(digit.toString()));
        } while (!decimal.isEqualDigit(0));
        convertNum.setSign(num.getSign());
        return convertNum;
    }

    public static BigNumber convertToDecimal(BigNumber num) {
        if (num.getBaseNumSys() == 10) {
            return num;
        }
        BigNumber multiplier = new BigNumber(10, "1");
        BigNumber b = new BigNumber(10, true, num.getBaseNumSys());
        BigNumber decimal = new BigNumber(10, "0");
        for (int i = 0; i < num.getSize(); i++) {
            BigNumber temp = mull(multiplier, new BigNumber(10, true, num.getDigitAt(i)));
            temp.removeExtraZeros();
            decimal = sum(decimal, temp);
            decimal.removeExtraZeros();
            multiplier = mull(multiplier, b);
            multiplier.removeExtraZeros();
        }
        decimal.setSign(num.getSign());
        return decimal;
    }

    public static BigNumber xor(BigNumber n1, BigNumber n2) {
        int baseNumSys = n1.getBaseNumSys();
        n1 = convertTo(n1,2);
        n2 = convertTo(n2,2);
        if (n1.getSize() < n2.getSize())
        {
            BigNumber temp = n1;
            n1 = n2;
            n2 = temp;
        }
        int needAdd = n1.getSize() - n2.getSize();
        for (int i = 0; i < needAdd; i++) {
            n2.addDigit(0);
        }
        BigNumber rez = new BigNumber(2,n1.getSize(),n1.getSign());
        for (int i = 0; i < n1.getSize(); i++) {
            rez.addDigit(n1.getDigitAt(i) ^ n2.getDigitAt(i));
        }
        return convertTo(rez,baseNumSys);
    }
}




