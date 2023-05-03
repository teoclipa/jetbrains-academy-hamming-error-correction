package correcter;

import java.util.Random;

public class ErrorGenerator extends TextDecorator {

    private static final String VALID_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789 ";


    public ErrorGenerator(Text text) {
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
                char randomChar = VALID_CHARS.charAt(new Random().nextInt(VALID_CHARS.length()));
                char errorChar = group.charAt(new Random().nextInt(group.length()));
                while (errorChar == randomChar) {
                    randomChar = VALID_CHARS.charAt(new Random().nextInt(VALID_CHARS.length()));
                }
                output.append(group.replaceFirst(Character.toString(errorChar), Character.toString(randomChar)));
            }
        }
        return output.toString();
    }
}
