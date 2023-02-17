import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        HashMap<Character, List<String>> productions = new HashMap<>();

        productions.put('S', List.of("dA"));
        productions.put('A', List.of("aB", "bA"));
        productions.put('B', List.of("bC", "aB", "d."));
        productions.put('C', List.of("cB"));

        List<Character> V_n = List.of('S', 'A', 'B', 'C');
        List<Character> V_t = List.of('a', 'b', 'c', 'd');

        Grammar grammar = new Grammar(V_n, V_t, productions);
        String word = grammar.generate_string();
        System.out.println("Final word is: " + word);
        System.out.println("Final word is: " + grammar.generate_string());
        System.out.println("Final word is: " + grammar.generate_string());
        System.out.println("Final word is: " + grammar.generate_string());
        System.out.println("Final word is: " + grammar.generate_string());


        List<Character> possible_states = new ArrayList<>(); // Possible states -> Q

        possible_states.add('S');
        possible_states.add('A');
        possible_states.add('B');
        possible_states.add('C');

        List<Character> sigma = new ArrayList<>(); // Possible states -> Q

        sigma.add('a');
        sigma.add('b');
        sigma.add('c');
        sigma.add('d');

        FiniteAutomaton automaton = new FiniteAutomaton(possible_states, sigma, productions);
        System.out.println("Word is from grammar: " + automaton.string_belongs_to_language(word));
        System.out.println("Word is from grammar: " + automaton.string_belongs_to_language(word + 'd'));

        System.out.println(grammar.toFiniteAutomaton().string_belongs_to_language("dad"));
    }
}