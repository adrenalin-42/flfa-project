import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        HashMap<List<Character>, List<String>> productions = QualityOfLife.initialize_productions();

        productions.put(List.of('S'), List.of("aA", "AC"));
        productions.put(List.of('A'), List.of("a", "ASC", "BC", "aD"));
        productions.put(List.of('B'), List.of("b", "bA"));
        productions.put(List.of('C'), List.of("$", "BA"));
        productions.put(List.of('D'), List.of("abC"));
        productions.put(List.of('E'), List.of("aB"));

        List<Character> Vt = QualityOfLife.generate_vt_from_productions(productions);
        List<Character> Vn = QualityOfLife.generate_vn_from_productions(productions);

        Grammar grammar = new Grammar(Vn, Vt, productions, 'S');

        ChomskyNormalForm newGrammar = new ChomskyNormalForm(grammar);

        newGrammar.printGrammar();
    }
}