package exercise;

// BEGIN
public class ReversedSequence implements CharSequence {

    private String text;

    public ReversedSequence(String text){
        this.text = text;
    }

    @Override
    public int length() {
        return this.text.length();
    }

    @Override
    public char charAt(int index) {
        return this.text.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return this.text.substring(start, end);
    }

    @Override
    public String toString() {
        String reversText = "";
        for (int i = text.length() - 1; i >= 0; i--) {
            reversText += text.charAt(i);
        }
        return this.text = reversText;
    }
}
// END
