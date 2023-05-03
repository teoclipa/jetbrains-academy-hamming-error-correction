package correcter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public final class BinaryStringViewConverter {

    public static String toBinaryString(byte[] bytes) {
        StringBuilder view = new StringBuilder();
        for (byte b : bytes) {
            int unsignedInt = Byte.toUnsignedInt(b);
            String st = String.format("%8s", Integer.toBinaryString(unsignedInt)).replaceAll(" ", "0");
            view.append(st);
        }
        return view.toString();
    }

    public static byte[] toBytes(String binView) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            int i = 0;
            while (i < binView.length()) {
                int b = Integer.parseInt(binView.substring(i, i + 8), 2);
                byteArrayOutputStream.write(b);
                i += 8;
            }
            byteArrayOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        }
    }
}