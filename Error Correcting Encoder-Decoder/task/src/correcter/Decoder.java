package correcter;

import java.io.IOException;

import static java.lang.Character.getNumericValue;

public final class Decoder {

    public Decoder() {
    }

    public byte[] decode(byte[] bytes) throws IOException {
        String encodedView = BinaryStringViewConverter.toBinaryString(bytes);
        StringBuilder decodedViewBuilder = new StringBuilder();
        int i = 0;
        while (i < encodedView.length()) {
            String encodedByte = encodedView.substring(i, i + 8);
            int fBit, sBit, thBit;
            if (encodedByte.charAt(0) != encodedByte.charAt(1)) {
                sBit = getNumericValue(encodedByte.charAt(2));
                thBit = getNumericValue(encodedByte.charAt(4));
                int parityBit = getNumericValue(encodedByte.charAt(6));
                fBit = parityBit ^ sBit ^ thBit;
            } else if (encodedByte.charAt(2) != encodedByte.charAt(3)) {
                fBit = getNumericValue(encodedByte.charAt(0));
                thBit = getNumericValue(encodedByte.charAt(4));
                int parityBit = getNumericValue(encodedByte.charAt(6));
                sBit = parityBit ^ fBit ^ thBit;
            } else if (encodedByte.charAt(4) != encodedByte.charAt(5)) {
                fBit = getNumericValue(encodedByte.charAt(0));
                sBit = getNumericValue(encodedByte.charAt(2));
                int parityBit = getNumericValue(encodedByte.charAt(6));
                thBit = parityBit ^ fBit ^ sBit;
            } else {
                fBit = getNumericValue(encodedByte.charAt(0));
                sBit = getNumericValue(encodedByte.charAt(2));
                thBit = getNumericValue(encodedByte.charAt(4));
            }
            decodedViewBuilder.append(fBit).append(sBit).append(thBit);
            i += 8;
        }
        String decodedView = decodedViewBuilder.substring(0, decodedViewBuilder.length() - decodedViewBuilder.length() % 8);
        return BinaryStringViewConverter.toBytes(decodedView);
    }

    private boolean isBitsPairIncorrect(String bitsPair) {
        return bitsPair.charAt(0) != bitsPair.charAt(1);
    }
}