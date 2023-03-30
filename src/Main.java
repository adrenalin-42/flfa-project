import java.util.List;

public class Main {
    public static void main(String[] args) {
        String code = "Description {\n" +
                "    name=\"John\"\n" +
                "    type=\"NPC\"\n" +
                "    mbti=\"intj\"\n" +
                "    role=\"protagonist\"\n" +
                "    background=\"John is a witch hunter\"\n" +
                "}\n" +
                "Setting {\n" +
                "    type=\"game\"\n" +
                "    category=\"adventure, magic, open-world\"\n" +
                "    background=\"A world similar to Mars, where witches are living in forests and fight with humans\"\n" +
                "}\n" +
                "Response {\n" +
                "    length=300\n" +
                "    prompt=\"I need the background story of the character. Add some info about family. Give 3 possible stories in which John is side character\"\n" +
                "}";

        List<Token> tokens = Tokenizer.tokenize(code);
        System.out.println(tokens);
    }
}