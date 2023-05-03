package correcter;

public class TripleDecoder extends TextDecorator {
    public TripleDecoder(Text text) {
        super(text);
    }

    @Override
    public String getText() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < this.text.getText().length(); i += 3) {
            String group = this.text.getText().substring(i, Math.min(i + 3, this.text.getText().length()));
            if (group.length() == 1) {
                output.append(group);
            } else {
                // Simulate error correction by selecting the most common character in the group
                char correctedChar = mostCommonChar(group);
                output.append(correctedChar);
            }
        }
        return output.toString();
    }

    private char mostCommonChar(String str) {
        int[] charCounts = new int[256];
        for (char c : str.toCharArray()) {
            charCounts[c]++;
        }
        char mostCommon = 0;
        int highestCount = 0;
        for (int i = 0; i < charCounts.length; i++) {
            if (charCounts[i] > highestCount) {
                mostCommon = (char) i;
                highestCount = charCounts[i];
            }
        }
        return mostCommon;
    }
}

