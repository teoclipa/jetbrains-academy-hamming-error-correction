package correcter;

import java.io.IOException;

import static java.lang.Character.getNumericValue;

public final class Encoder {

    public byte[] encode(byte[] bytes) throws IOException {
        String binView = BinaryStringViewConverter.toBinaryString(bytes);
        String encodedBinView = encode(binView);
        return BinaryStringViewConverter.toBytes(encodedBinView);
    }

    private String encode(String binView) {
        int bitsToAddNum = (int) (Math.ceil((double) binView.length() / 3) - binView.length() / 3);
        binView += "0".repeat(bitsToAddNum);
        StringBuilder encoded = new StringBuilder();
        for (int i = 0; i < binView.length() - 2; i = i + 3) {
            int fBit = getNumericValue(binView.charAt(i));
            int sBit = getNumericValue(binView.charAt(i + 1));
            int thBit = getNumericValue(binView.charAt(i + 2));
            int parityBit = fBit ^ sBit ^ thBit;
            encoded.append(fBit).append(fBit)
                    .append(sBit).append(sBit)
                    .append(thBit).append(thBit)
                    .append(parityBit).append(parityBit);
        }
        return encoded.toString();
    }

}