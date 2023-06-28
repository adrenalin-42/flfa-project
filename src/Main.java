import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        String code = """
                Description
                {
                    name="John"
                    type="NPC"
                    mbti="intj"
                    role="protagonist"
                    background="John is a witch hunter"
                }
                Setting
                {
                    type="game"
                    category="adventure, magic, open-world"
                    background="A world similar to Mars, where witches are living in forests and fight with humans"
                }
                Response
                {
                    length=300
                    prompt="I need the background story of the character. Add some info about family. Give 3 possible stories in which John is side character"
                }""";

        Tokenizer tokenizer = new Tokenizer(code);
        Token root = Tokenizer.buildAST();


        Tokenizer.printAST(root, 0);
    }
}