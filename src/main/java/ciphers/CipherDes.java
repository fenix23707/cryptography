package ciphers;

import numbers.BigNumber;
import numbers.BigNumberOperations;

public class CipherDes {

    public BigNumber initialPermutation(BigNumber dataBlock) {
        if (dataBlock.getSize() != 64 || dataBlock.getBaseNumSys() != 2) {
            throw new IllegalArgumentException("dataBlock us incorrect");
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

    public BigNumber functionDes(BigNumber halfBlock, BigNumber key) {
        halfBlock = BigNumberOperations.convertTo(halfBlock, 2);
        key = BigNumberOperations.convertTo(key, 2);
        if (halfBlock.getSize() != 32 || key.getSize() != 48) {
            throw new IllegalArgumentException("block or key is invalid");
        }
        return null;
    }

    public BigNumber expand(BigNumber block) {
        block = BigNumberOperations.convertTo(block,2);
        if (block.getSize() != 32) {
            throw new IllegalArgumentException("block size: "
                    + block.getSize()+ " is very big");
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
}
