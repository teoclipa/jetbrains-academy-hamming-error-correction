package correcter;

import java.util.Random;

public final class ErrorSimulator {

    public void applyErrors(byte[] bytes) {
        Random random = new Random();
        for (int i = 0; i < bytes.length; i++) {
            int bitPosition = random.nextInt(8);
            bytes[i] = (byte) changeBit(bitPosition, bytes[i]);
        }
    }

    private int changeBit(int bitPosition, int target) {
        int mask = 1 << bitPosition;
        return target ^ mask;
    }
}