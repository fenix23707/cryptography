package ciphers;

import numbers.BigNumber;
import numbers.BigNumberOperations;

public class CipherXor {
    private BigNumber gamma = null;

    private String alphabet = " абвгдеёжзийклмнопрстуфхцчшщъыьэюя";

    public BigNumber charToBigNumber(String msg) {
        StringBuilder builder = new StringBuilder(2*msg.length());
        for(int i = 0; i < msg.length(); i++) {
            String num = String.valueOf(alphabet.indexOf(msg.charAt(i)));
            if (num.length() == 1) {
                num = "0" + num;
            }
            builder.append(num);
        }
        return new BigNumber(10,builder.toString());
    }

    public String bigNUmberToString(BigNumber num) {
        String numStr = num.toString();
        if(numStr.length() % 2 != 0) {
            numStr = "0" + numStr;
        }
        StringBuilder msg = new StringBuilder(numStr.length());
        for(int i = 0; i < numStr.length(); i += 2) {
            String temp = numStr.substring(i,i + 2);
            int index = Integer.parseInt(temp);
            char letter = alphabet.charAt(index);
            msg.append(letter);
        }
        return msg.toString();
    }

    public BigNumber encrypt(String msg, BigNumber start, SingleVariableFunction func) {
        return encrypt(charToBigNumber(msg), start, func);
    }

    public BigNumber encrypt(BigNumber msg, BigNumber start, SingleVariableFunction func) {
        msg = BigNumberOperations.convertTo(msg, 2);
        gamma = BigNumber.linearFeedbackShiftRegister(start,func, msg.getSize());
        return BigNumberOperations.xor(gamma, msg);
    }

}
