public class Token {
    private String type;
    private String lexeme;

    public Token(String type, String lexeme) {
        this.type = type;
        this.lexeme = lexeme;
    }

    @Override
    public String toString() {
        return "(" + type + " " + lexeme + ")";
    }
}
