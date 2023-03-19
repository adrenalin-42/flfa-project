import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
//        HashMap<List<Character>, List<String>> productions = new HashMap<>();
//
//        productions.put(List.of('S'), List.of("dA"));
//        productions.put(List.of('A'), List.of("aB", "bA"));
//        productions.put(List.of('B'), List.of("bC", "aB", "d"));
//        productions.put(List.of('C'), List.of("cB"));
//
//        List<Character> V_n = new ArrayList<>(Arrays.asList('S', 'A', 'B', 'C'));
//        List<Character> V_t = List.of('a', 'b', 'c', 'd');
////
//        Grammar grammar = new Grammar(V_n, V_t, productions);
//        String word = grammar.generate_string();
//        System.out.println("Final word is: " + word);
//        System.out.println("Final word is: " + grammar.generate_string());
//        System.out.println("Final word is: " + grammar.generate_string());
//        System.out.println("Final word is: " + grammar.generate_string());
//        System.out.println("Final word is: " + grammar.generate_string());
//
//
//        List<Character> possible_states = new ArrayList<>(); // Possible states -> Q
//
//        possible_states.add('S');
//        possible_states.add('A');
//        possible_states.add('B');
//        possible_states.add('C');
//
//        List<Character> sigma = new ArrayList<>(); // Possible states -> Q
//
//        sigma.add('a');
//        sigma.add('b');
//        sigma.add('c');
//        sigma.add('d');
//
//        FiniteAutomaton automaton = grammar.to_finite_automaton();
//        System.out.println("Word is from grammar: " + automaton.string_belongs_to_language(word));
//        System.out.println("Word is from grammar: " + automaton.string_belongs_to_language(word + 'd'));
//
//        System.out.println(grammar.to_finite_automaton().string_belongs_to_language("dads"));

        HashMap<List<Character>, List<String>> productions2 = new HashMap<>();

        productions2.put(List.of('S'), List.of("XaX"));
        productions2.put(List.of('X'), List.of("a", "aX", "abc", "λ"));

        List<Character> V_n2 = new ArrayList<>(Arrays.asList('S', 'X'));
        List<Character> V_t2 = List.of('a', 'b', 'c', 'λ');

        Grammar grammar2 = new Grammar(V_n2, V_t2, productions2);

        System.out.println("Grammar type is " + grammar2.classify_grammar());
        System.out.println("Final word is: " + grammar2.generate_string());

        // NDFA to DFA example
//        List<Character> possible_states = new ArrayList<>(); // Possible states -> Q
//
//        possible_states.add('0');
//        possible_states.add('1');
//        possible_states.add('2');
//        possible_states.add('3');
//
//        List<Character> sigma = new ArrayList<>(); // Sigma
//
//        sigma.add('a');
//        sigma.add('b');
//        sigma.add('c');
//
//        HashMap<Character, List<String>> rules = new HashMap<>();
//
//        rules.put('0', List.of("a0", "a1"));
//        rules.put('1', List.of("b1", "a2"));
//        rules.put('2', List.of("b3", "a0"));
//
//        FiniteAutomaton my_automaton = new FiniteAutomaton(possible_states, sigma, rules, '0', List.of('3'));
//
//        System.out.println("Before: " + my_automaton.rules);
//
//        FiniteAutomaton my_dfa_automaton = my_automaton.nfa_to_dfa();
//
//        System.out.println("After: " + my_dfa_automaton.rules);

//        System.out.println("Word is from FA: " + my_dfa_automaton.string_belongs_to_language("aaaaaaaaabbbbbbbbbbbbbbbbbbbb"));


//        System.out.println("Is NFA: " + my_automaton.is_nfa());

//        System.out.println("Is NFA: " + my_dfa_automaton.is_nfa());

//        my_dfa_automaton.is_nfa();
//        System.out.println(my_dfa_automaton.to_regular_grammar().generate_string());
//        my_dfa_automaton.to_png();

//        Grammar newGrammar = my_dfa_automaton.to_regular_grammar();
//
//        System.out.println(newGrammar.productions);
//        System.out.println(newGrammar.V_n);
//        System.out.println(newGrammar.V_t);
//
//        System.out.println("Generated string is: " + newGrammar.generate_string());
    }
}