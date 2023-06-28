import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
    public static List<Token> actualTokens;
    private static final String[] TOKENS = {
            "DESCRIPTION", "SETTING", "RESPONSE", "NAME", "TYPE", "MBTI", "ROLE",
            "BACKGROUND", "CATEGORY", "LENGTH", "PROMPT", "STRING_LITERAL", "NUMBER_LITERAL",
            "LEFT_BRACE", "RIGHT_BRACE", "EQUALS", "LEFT_SQUARE_BRACKET", "RIGHT_SQUARE_BRACKET"
    };

    private static final String DESCRIPTION_REGEX = "Description\\s*";
    private static final String SETTING_REGEX = "Setting\\s*";
    private static final String RESPONSE_REGEX = "Response\\s*";
    private static final String NAME_REGEX = "name\\s*";
    private static final String TYPE_REGEX = "type\\s*";
    private static final String MBTI_REGEX = "mbti\\s*";
    private static final String ROLE_REGEX = "role\\s*";
    private static final String BACKGROUND_REGEX = "background\\s*";
    private static final String CATEGORY_REGEX = "category\\s*";
    private static final String LENGTH_REGEX = "length\\s*";
    private static final String PROMPT_REGEX = "prompt\\s*";
    private static final String NUMBER_LITERAL_REGEX = "\\d+";
    private static final String LEFT_BRACE_REGEX = "\\{";
    private static final String RIGHT_BRACE_REGEX = "\\}";
    private static final String EQUALS_REGEX = "=";
    private static final String LEFT_SQUARE_BRACKET_REGEX = "\\[";
    private static final String RIGHT_SQUARE_BRACKET_REGEX = "\\]";
    private static final String STRING_LITERAL_REGEX = "\"([^\"\\\\]|\\\\.)*\"";

    private static final String TOKEN_REGEX = String.join("|",
            DESCRIPTION_REGEX, SETTING_REGEX, RESPONSE_REGEX, NAME_REGEX, TYPE_REGEX,
            MBTI_REGEX, ROLE_REGEX, BACKGROUND_REGEX, CATEGORY_REGEX, LENGTH_REGEX, PROMPT_REGEX,
            STRING_LITERAL_REGEX, NUMBER_LITERAL_REGEX, LEFT_BRACE_REGEX, RIGHT_BRACE_REGEX,
            EQUALS_REGEX, LEFT_SQUARE_BRACKET_REGEX, RIGHT_SQUARE_BRACKET_REGEX
    );

    private static final Pattern PATTERN = Pattern.compile(TOKEN_REGEX);

    public Tokenizer(String code) {
        actualTokens = tokenize(code);
    }


    private static List<Token> tokenize(String code) {
        List<Token> tokens = new ArrayList<>();
        Matcher matcher = PATTERN.matcher(code);
        while (matcher.find()) {
            String value = matcher.group();
            if (Pattern.matches(DESCRIPTION_REGEX, value)) {
                tokens.add(new Token("DESCRIPTION", "keyword", value.trim()));
            } else if (Pattern.matches(SETTING_REGEX, value)) {
                tokens.add(new Token("SETTING", "keyword", value.trim()));
            } else if (Pattern.matches(RESPONSE_REGEX, value)) {
                tokens.add(new Token("RESPONSE", "keyword", value.trim()));
            } else if (Pattern.matches(NAME_REGEX, value)) {
                tokens.add(new Token("NAME", "identifier", value.trim()));
            } else if (Pattern.matches(TYPE_REGEX, value)) {
                tokens.add(new Token("TYPE", "identifier", value.trim()));
            } else if (Pattern.matches(MBTI_REGEX, value)) {
                tokens.add(new Token("MBTI", "identifier", value.trim()));
            } else if (Pattern.matches(ROLE_REGEX, value)) {
                tokens.add(new Token("ROLE", "identifier", value.trim()));
            } else if (Pattern.matches(BACKGROUND_REGEX, value)) {
                tokens.add(new Token("BACKGROUND", "identifier", value.trim()));
            } else if (Pattern.matches(CATEGORY_REGEX, value)) {
                tokens.add(new Token("CATEGORY", "identifier", value.trim()));
            } else if (Pattern.matches(LENGTH_REGEX, value)) {
                tokens.add(new Token("LENGTH", "identifier", value.trim()));
            } else if (Pattern.matches(PROMPT_REGEX, value)) {
                tokens.add(new Token("PROMPT", "identifier", value.trim()));
            } else if (Pattern.matches(NUMBER_LITERAL_REGEX, value)) {
                tokens.add(new Token("NUMBER_LITERAL", "literal", value.trim()));
            } else if (Pattern.matches(LEFT_BRACE_REGEX, value)) {
                tokens.add(new Token("LEFT_BRACE", "separator", value.trim()));
            } else if (Pattern.matches(RIGHT_BRACE_REGEX, value)) {
                tokens.add(new Token("RIGHT_BRACE", "separator", value.trim()));
            } else if (Pattern.matches(EQUALS_REGEX, value)) {
                tokens.add(new Token("EQUALS", "operator", value.trim()));
            } else if (Pattern.matches(LEFT_SQUARE_BRACKET_REGEX, value)) {
                tokens.add(new Token("LEFT_SQUARE_BRACKET", "separator", value.trim()));
            } else if (Pattern.matches(RIGHT_BRACE_REGEX, value)) {
                tokens.add(new Token("RIGHT_SQUARE_BRACKET", "separator", value.trim()));
            } else if (Pattern.matches(STRING_LITERAL_REGEX, value)) {
                tokens.add(new Token("STRING_LITERAL", "literal", value.trim()));
            }
        }
        return tokens;
    }

    public static Token buildAST() {

        Stack<Statement> statements = new Stack<>();
        Stack<Token> data = new Stack<>();
        Stack<Token> stack = new Stack<>();
        Token root = new Token();
        stack.push(root);

        for (Token token : actualTokens) {
            if (token.getType() == "LEFT_BRACE") {
                // Opening brace, create a new node
                Token node = new Token();
                Token parent = stack.peek();
                parent.children.add(node);
                stack.push(node);
            } else if (token.getType() == "RIGHT_BRACE") {
                // Closing brace, pop the stack
                stack.pop();
            }
            else if (token.getLexeme() == "identifier" || token.getLexeme() == "operator") {
                data.push(token);
            }
            else if (token.getLexeme() == "literal") {
                data.push(token);
                statements.push(new Statement(data.pop(), data.pop(), data.pop()));
                Token parent = stack.peek();
                parent.children.add(statements.pop());
            }
            else {
                // Regular line, add it as a child of the current node
                Token node = new Token(token.getType(), token.getLexeme(), token.getValue());
                Token parent = stack.peek();
                parent.children.add(node);
            }
        }

        return root; // Return the root of the AST
    }

    // Function to print the AST in a hierarchical format
    public static void printAST(Object node, int level) {
        if (node.getClass() == Token.class) {
            if (((Token) node).getValue() != null) {
                for (int i = 0; i < level; i++) {
                    System.out.print("\t");
                }
                System.out.println(((Token) node).getValue());

            }

            for (Object child : ((Token) node).children) {
                printAST(child, level + 1);
            }
        } else if (node.getClass() == Statement.class) {
            for (int i = 0; i < level; i++) {
                System.out.print("\t");
            }
            System.out.println(((Statement) node).getVarName() + " (" + ((Statement) node).getVarType() + ") " + ((Statement) node).getValue());
        }
    }

    public void printTokens() {
        for (Token token : actualTokens) {
            System.out.println(token.toString());
        }
    }
}

