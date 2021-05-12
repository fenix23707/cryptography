import ciphers.*;
import group.Group;
import group.IntegerSet;
import group.MultiplicativeGroup;
import numbers.BigNumber;
import numbers.BigNumberOperations;
import numbers.PrimeNumbers;
import permutation.Permutation;
import permutation.PermutationOperations;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;


public class Runner {
    public static void main(String[] args) {

        //Лаб 7:
        CipherRsa cipherRsa = new CipherRsa();
        BigNumber msg = new BigNumber(10,"123");
        BigNumber chipher = cipherRsa.encryption(msg);
        System.out.println(chipher);
        System.out.println(cipherRsa.decryption(chipher));

        CipherRsaCrt cipherRsaCrt = new CipherRsaCrt();
        String msgStr = "привет";
        BigNumber chipher1 = cipherRsaCrt.encryption(msgStr);
        System.out.println(chipher1);
        System.out.println(cipherRsaCrt.decryption(chipher1));


        //Лаб 8:

       /* BigNumber p81 = new BigNumber(10,"11239");
        BigNumber g81 = new BigNumber(10,"3");
        BigNumber y81 = new BigNumber(10,"27");
        BigNumber o81 = new BigNumber(10,"729");
        BigNumber cipher = new BigNumber(10,"3158");
        CipherElGamal cipherElGamal = new CipherElGamal(p81,g81,y81,o81);
        String msg81 = cipherElGamal.decryption(cipher);
        System.out.println(msg81);

        BigNumber p82 = new BigNumber(10,"401141729");
        BigNumber g82 = new BigNumber(10,"6");
        BigNumber y82 = new BigNumber(10,"102149179");
        BigNumber o82 = new BigNumber(10,"166506866");
        BigNumber cipher82 = new BigNumber(10,"166161904");
        CipherElGamal cipherElGamal82 = new CipherElGamal(p82,g82,y82,o82);
        String msg82 = cipherElGamal82.decryption(cipher82);
        System.out.println(msg82);*/

        //Лаб 9
        System.out.println("Лаб 9:");
        CipherElGamal cipherElGamal9 = new CipherElGamal();
        String msg9 = "привет";
        BigNumber cipher = cipherElGamal9.encryption(msg9);
        System.out.println(cipher);
        System.out.println(cipherElGamal9.decryption(cipher));

        //ЛАб 10:


    }
}
