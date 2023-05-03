package correcter;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final File FILE = new File("send.txt");
    private static final File ENCODED_FILE = new File("encoded.txt");
    private static final File RECEIVED_FILE = new File("received.txt");
    private static final File DECODED_FILE = new File("decoded.txt");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Write a mode: ");
        String mode = scanner.next();

        switch (mode) {
            case "encode":
                encode();
                break;
            case "send":
                send();
                break;
            case "decode":
                decode();
                break;
            default:
                break;
        }
    }

    private static void encode() {
        try (InputStream inputStream = new FileInputStream(FILE);
             OutputStream outputStream = new FileOutputStream(ENCODED_FILE)) {
            byte[] bytes = inputStream.readAllBytes();
            byte[] encodedBytes = new byte[bytes.length * 2];
            for (int i = 0; i < bytes.length; i++) {
                for (int j = 0; j < 2; j++) {
                    byte expandedByte = 0;
                    for (int k = 0; k < 4; k++) {
                        int mask = 0b10000000 >>> (j * 4 + k);
                        if ((bytes[i] & mask) != 0) {
                            expandedByte = (byte) (expandedByte ^ (0b00000001 << (int) Math.pow(2, 2 - k) + 1));
                        }
                    }
                    encodedBytes[i * 2 + j] = addParityBits(expandedByte);
                }
            }
            outputStream.write(encodedBytes);

        } catch (IOException e) {
            System.out.println("Error has occurred: " + e.getMessage());
        }
    }

    private static void send() {
        try (InputStream inputStream = new FileInputStream(ENCODED_FILE);
             OutputStream outputStream = new FileOutputStream(RECEIVED_FILE)) {
            byte[] bytes = inputStream.readAllBytes();
            byte[] corruptedBytes = new byte[bytes.length];
            for (int i = 0; i < bytes.length; i++) {
                Random random = new Random();
                byte mask = (byte) (0b00000001 << random.nextInt(8));
                mask = (byte) ~mask;
                corruptedBytes[i] = (byte) ~(bytes[i] ^ mask);
            }
            outputStream.write(corruptedBytes);

        } catch (IOException e) {
            System.out.println("Error has occurred: " + e.getMessage());
        }
    }

    private static void decode() {
        try (InputStream inputStream = new FileInputStream(RECEIVED_FILE);
             OutputStream outputStream = new FileOutputStream(DECODED_FILE)) {
            byte[] bytes = inputStream.readAllBytes();
            byte[] decodedBytes = new byte[bytes.length / 2];
            for (int i = 0; i < bytes.length; i += 2) {
                byte decodedByte = 0;
                for (int j = 0; j < 8; j++) {
                    if (j < 4) {
                        decodedByte = (byte) (decodedByte ^ ((correctByte(bytes[i]) << (j + 8) / 3) & (128 >> j))); //shifts: 2-3-3-3
                    } else {
                        decodedByte = (byte) (decodedByte ^ ((correctByte(bytes[i + 1]) >> (12 - j) / 4) & (128 >> j))); //shifts: 2-1-1-1
                    }
                }
                decodedBytes[i / 2] = decodedByte;
            }
            outputStream.write(decodedBytes);
        } catch (IOException e) {
            System.out.println("Error has occurred: " + e.getMessage());
        }
    }

    private static byte addParityBits(byte expandedByte) {
        byte encodedByte = expandedByte;
        if (((expandedByte >> 5) + (expandedByte >> 3) + (expandedByte >> 1)) % 2 != 0) {
            encodedByte = (byte) (encodedByte ^ 0b10000000);
        }
        if (((expandedByte >> 5) + (expandedByte >> 2) + (expandedByte >> 1)) % 2 != 0) {
            encodedByte = (byte) (encodedByte ^ 0b01000000);
        }
        if (((expandedByte >> 3) + (expandedByte >> 2) + (expandedByte >> 1)) % 2 != 0) {
            encodedByte = (byte) (encodedByte ^ 0b00010000);
        }
        return encodedByte;
    }

    private static byte correctByte(byte corruptedByte) {
        if (corruptedByte % 2 != 0) {
            return (byte) (corruptedByte & 0b11111110);
        }
        int corruptedBit = 0;
        if (((corruptedByte >> 5) + (corruptedByte >> 3) + (corruptedByte >> 1)) % 2 != (corruptedByte >> 7) % 2) {
            corruptedBit += 1;
        }
        if (((corruptedByte >> 5) + (corruptedByte >> 2) + (corruptedByte >> 1)) % 2 != (corruptedByte >> 6) % 2) {
            corruptedBit += 2;
        }
        if (((corruptedByte >> 3) + (corruptedByte >> 2) + (corruptedByte >> 1)) % 2 != (corruptedByte >> 4) % 2) {
            corruptedBit += 4;
        }
        return (byte) (corruptedByte ^ (1 << 8 - corruptedBit));
    }
}