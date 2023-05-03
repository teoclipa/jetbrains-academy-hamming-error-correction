package correcter;

public abstract class TextDecorator implements Text{
    protected Text text;

    public TextDecorator(Text text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text.getText();
    }
}
