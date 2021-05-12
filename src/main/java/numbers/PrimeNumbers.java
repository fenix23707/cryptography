package numbers;

import java.util.Random;

public class PrimeNumbers {
    public static boolean isPrimeNumber(BigNumber number,int numberOfIter){
        if (number.getSize()==1&&number.getFirstDigit() < 4) {
            return true;
        }
        if (number.getFirstDigit() % 2 == 0) {
            return false;
        }

        //1.:
        BigNumber r = new BigNumber(number.getBaseNumSys());
        BigNumber s = findDegreeOfTwo(number,r);

        //2.:
        for(int t = 1; t <= numberOfIter; t++){
            boolean contin = false;
            //1):
            BigNumber max = BigNumberOperations.sub(number, new BigNumber(number.getBaseNumSys(),"4"));
            BigNumber rand = BigNumber.getRandNumber(max);
            BigNumber u = BigNumberOperations.sum( rand, new BigNumber(number.getBaseNumSys(),"2"));
            //2)
            BigNumber v = BigNumberOperations.degreeRemainder2(u,r,number);
            //3)
            if (v.isEqualDigit(1) || v.compare(BigNumberOperations.dec(number)) == 0) {
                continue;
            }
                //4)
                BigNumber i = new BigNumber(number.getBaseNumSys(),"1");
                while (i.compare(s) < 0){
                    //a)
                    BigNumber temp = BigNumberOperations.mull(v,v);
                    BigNumberOperations.div(temp,number,v);//v = v^2 mod n
                    //b
                    if (v.isEqualDigit(1)) {
                        return false;
                    }
                    //c
                    if( v.compareTo(BigNumberOperations.dec(number)) == 0) {
                        contin = true;
                        break;
                    }
                    i = BigNumberOperations.inc(i);
                }
                //
                if (!contin) {
                    return false;
                }
        }
        return true;
    }
    /*
    * findDegreeOfTwo() - после своей работы возвращает два числа:
    *   1) s - степень двойки. Возвращается при помощи return
    *   2) r - остаток при деление основного числа на 2^s. Изменяется второй аргумент метода
    * number = (2^s)*r
    * */
    private static BigNumber findDegreeOfTwo(BigNumber number,BigNumber r){
        number = BigNumberOperations.dec(number);//уменьшение на 1
        BigNumber s = new BigNumber(number.getBaseNumSys(),"0");

        BigNumber constZero = new BigNumber(number.getBaseNumSys(),"0");
        BigNumber remainder = new BigNumber(number.getBaseNumSys());

        while(number.compare(constZero)!=0){
            BigNumber temp = BigNumberOperations.div(number,2,remainder);//number /=2
            if(remainder.compare(constZero)!=0){//если число не делится на 2, то продолжение цикла бессмыслено
                break;
            }
            number = temp;
            s = BigNumberOperations.inc(s);//s++
        }
        r.setNumber(number.toString());
        return s;
    }

    public static BigNumber getPrimeNumber(int baseNumSys, int size) {
        BigNumber randPrimeNumber = BigNumber.getRandNumber(size,baseNumSys);
        while (!isPrimeNumber(randPrimeNumber,1)){
            randPrimeNumber = BigNumberOperations.inc(randPrimeNumber);
        };
        return randPrimeNumber;
    }
}
