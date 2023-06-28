import java.util.*;

import static java.lang.Character.isUpperCase;

public class Grammar {
    // Some state variables as needed.
    // {V_n, V_t, P, S}
    List<Character> V_n;
    final List<Character> V_t;
    HashMap<List<Character>, List<String>> productions;
    Character start_symbol;

    Boolean was_transformed_from_nfa;

    public Grammar(List<Character> V_n, List<Character> V_t, HashMap<List<Character>, List<String>> productions) {
        this.V_n = V_n;
        this.V_t = V_t;
        this.productions = productions;
        this.was_transformed_from_nfa = false;
        try {
            start_symbol = V_n.get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("V_n passed in grammar, has a size of 0.");
        }
    }

    public Grammar(List<Character> V_n, List<Character> V_t, HashMap<List<Character>, List<String>> productions, Character start_symbol) {
        this.V_n = V_n;
        this.V_t = V_t;
        this.productions = productions;
        this.was_transformed_from_nfa = false;
        this.start_symbol = start_symbol;
    }

    public String generate_string() {

        if (was_transformed_from_nfa) {
            System.out.println("ERROR: You can't do that with a DFA. Use the NFA version.");
            return (null);
        }


        // Initiate random and other variables
        Random rand = new Random();
//        String prompt = "S";
        String prompt = start_symbol.toString();

        // Convert V_n to a sequence of chars
        StringBuilder V_n_string = new StringBuilder();

        for (Character value : V_n) {
            V_n_string.append(value);
        }

        // Parse the prompt, until there are no non-terminals left.
        while (prompt.matches(".*[" + V_n_string + "].*")) {
            int index = 0;

            // Parse the prompt length
            while (index < prompt.length()) {
                Character current_char = prompt.charAt(index);

                if (V_t.contains(current_char)) {
                    index++;
                    continue;
                }

                // Perform safety check
                if (V_n.contains(current_char)) {
                    // Get the rules for the current character.
                    List<String> result = this.productions.get(List.of(current_char));

                    String next_rule = result.get(rand.nextInt(result.size()));

                    // Empty string case
                    next_rule = next_rule.replace("$", "");

                    prompt = prompt.replace(current_char.toString(), next_rule);
                } else {
                    prompt = prompt + current_char;
                }
                index++;
            }
        }
        return prompt;
    }

    public FiniteAutomaton to_finite_automaton() {

        // Only convert if we have a type 3 grammar.
        if (classify_grammar() != 3) {
            System.out.println("Error: You can only convert Regular Grammar to Finite Automata");
            return (null);
        }

        // Convert productions to Finite Automata rules.
        HashMap<List<Character> , List<String>> fa_rules = new HashMap<List<Character> , List<String>>();

        for(List<Character> key: this.productions.keySet()) {
            fa_rules.put(key, this.productions.get(key));
        }

        List<List<Character>> possible_states = new ArrayList<>();

        for (Character key : this.V_n) {
            possible_states.add(List.of(key));
        }

        FiniteAutomaton new_automaton = new FiniteAutomaton(possible_states, this.V_t, fa_rules, List.of('S'), List.of(), true);

        return (new_automaton);
    }

    public void printGrammar() {
        System.out.println("Variables: " + V_n);
        System.out.println("Terminals: " + V_t);
        System.out.println("Productions: ");
        for (List<Character> rule : productions.keySet()) {
            System.out.println(rule.toString() + " -> " + productions.get(rule));
        }
    }

    public Integer classify_grammar() {

        int type_3_pass = 0;
        int type_2_pass = 0;
        int type_1_pass = 0;
        int type_0_pass = 0;

        for(List<Character> key: productions.keySet()) {
            for (String value : productions.get(key)) {

                StringBuilder lowercase_key_letters = new StringBuilder();
                StringBuilder uppercase_key_letters = new StringBuilder();

                for (Character temp_char : key) {

                    if (Character.isLowerCase(temp_char)) {
                        lowercase_key_letters.append(temp_char);
                    } else if (Character.isUpperCase(temp_char)) {
                        uppercase_key_letters.append(temp_char);
                    }
                }

                StringBuilder lowercase_value_letters = new StringBuilder();
                StringBuilder uppercase_value_letters = new StringBuilder();

                for (int index = 0; index < value.length(); index++) {
                    Character temp_char = value.charAt(index);

                    if (Character.isLowerCase(temp_char)) {
                        lowercase_value_letters.append(temp_char);
                    } else if (Character.isUpperCase(temp_char)) {
                        uppercase_value_letters.append(temp_char);
                    }
                }
                // Check for type 3 (Regular Grammar)
                if (key.size() == 1 && uppercase_value_letters.length() <= 1) {
                    type_3_pass += 1;
                }

                // Check for type 2 (Context - Free)
                else if (key.size() == 1) {
                    type_2_pass += 1;
                }

                // Check for type 1 (Context sensitive)
                else if (key.size() > 1 && lowercase_key_letters.length() == 0) {
                    type_1_pass += 1;

                // Check for type 0 (Unrestricted grammar)
                } else {
                    type_0_pass += 1;
                }
            }
        }

        if (type_0_pass > 0) {
            return (0);
        } else if (type_1_pass > 0) {
            return (1);
        } else if (type_2_pass > 0) {
            return (2);
        }
        return (3);
    }
}