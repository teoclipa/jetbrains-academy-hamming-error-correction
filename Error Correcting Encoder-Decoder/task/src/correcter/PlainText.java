package correcter;

public record PlainText(String text) implements Text {


    @Override
    public String getText() {
       return text;
    }
}
