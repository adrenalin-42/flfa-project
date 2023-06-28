import java.util.ArrayList;
import java.util.List;

public class Token {
    private final String type;
    private final String lexeme;
    private final String value;
    public List<Object> children;

    public Token(String type, String lexeme, String value) {
        this.type = type;
        this.lexeme = lexeme;
        this.value = value;
        this.children = new ArrayList<>();
    }

    public Token() {
        this.type = null;
        this.lexeme = null;
        this.value = null;
        this.children = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "(" + type + " " + lexeme + " " + value + ")";
    }

    public String getType() {
        return (type);
    }

    public String getLexeme() {
        return (lexeme);
    }

    public String getValue() {
        return (value);
    }

}
