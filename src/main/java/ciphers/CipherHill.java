package ciphers;

import matrix.MatrixOperations;
import numbers.BigNumber;
import numbers.BigNumberOperations;

import java.util.Locale;

public class CipherHill {
    private static String symbols = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя., ?";

    public static String decrypt(String key, String msg) {
        return decrypt(stringToCharArr(key),msg);
    }

    public static String decrypt(char[][] key, String msg) {
        if (key.length != key[0].length) {
           throw new IllegalArgumentException("key must be square matrix");
        }
        return decrypt(parseInt(key),msg);
    }

    public static String decrypt(int[][] key, String msg) {
        if (!isValidKey(key)) {
            throw new IllegalArgumentException("key is not valid");
        }

        msg = msg.toLowerCase(Locale.ROOT);
        int[] intMsg = charToInt(msg);

        //нахождение обратной матрицы
        int[][] inverseKey = MatrixOperations.getAlgebraicAddition(key);
        int det = MatrixOperations.determinant(key);

        BigNumber xBigNum = new BigNumber(10);
        BigNumber y = new BigNumber(10);
        BigNumber detBigNum = new BigNumber(10, String.valueOf(det));
        BigNumber modDibNum = new BigNumber(10, true, symbols.length());
        BigNumberOperations.identityBezu(detBigNum,modDibNum,xBigNum,y);
        int x = Integer.parseInt(xBigNum.toString());
        if (det < 0) {
           x = Math.abs(x);
        } else {
            x = (x + symbols.length()) % symbols.length();
        }
        MatrixOperations.mullOnNumber(inverseKey,x);
        MatrixOperations.divModule(inverseKey, symbols.length());
        MatrixOperations.addModuleToNegativeElements(inverseKey,symbols.length());

        /*int[][] m = MatrixOperations.mull(inverseKey,key);
        MatrixOperations.divModule(m,symbols.length());
        MatrixOperations.printMatrix(m);*/
        StringBuilder decryptionMsg = new StringBuilder(msg.length());

        for(int i = 0; i < intMsg.length; i += inverseKey.length) {
            int[] temp = MatrixOperations.mull(intMsg,i,inverseKey, symbols.length());
            for(int j = 0; j < temp.length; j++) {
                decryptionMsg.append(symbols.charAt(temp[j]));
            }
        }
        return decryptionMsg.toString().trim();
    }

    private static int[][] parseInt(char[][] key) {
        int[][] intKey = new int[key.length][key.length];
        for( int i = 0; i < key.length; i++) {
            for( int j = 0; j < key.length; j++) {
                int temp = symbols.indexOf(key[i][j]);
                if (temp < 0) {
                   throw new IllegalArgumentException("i don't know this symbol: " + key[i][j]);
                }
                intKey[i][j] = temp;
            }
        }
        return intKey;
    }

    private static boolean isValidKey(int[][] key) {
        return MatrixOperations.determinant(key) != 0;
    }

    private static int[] charToInt(String msg) {
        int[] intMsg =  new int[msg.length()];

        for(int i = 0; i < msg.length(); i++) {
            int temp = symbols.indexOf(msg.charAt(i));
            if (temp < 0) {
                throw new IllegalArgumentException("i don't know this symbol: " + msg.charAt(i));
            }
            intMsg[i] = temp;
        }
        return intMsg;
    }

    private static char[][] stringToCharArr(String str) {
        double size = Math.sqrt(str.length());
        if((size - (int)size) != 0) {
            throw new IllegalArgumentException("incorrect key");
        }

        char[][] key = new char[(int)size][(int)size];
        int k = 0;
        for(int i  = 0; i < key.length; i++) {
            for(int j = 0; j < key.length; j++) {
                key[i][j] = str.charAt(k++);
            }
        }
        return key;
    }
}
