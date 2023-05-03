package correcter;

public class TripleEncoder extends TextDecorator {
    public TripleEncoder(Text text) {
        super(text);
    }

    @Override
    public String getText() {
        //triple each character in the text
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.text.getText().length(); i++) {
            sb.append(this.text.getText().charAt(i));
            sb.append(this.text.getText().charAt(i));
            sb.append(this.text.getText().charAt(i));
        }
        return sb.toString();
    }

}
