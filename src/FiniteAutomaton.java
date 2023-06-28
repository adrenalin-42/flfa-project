import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

import net.sourceforge.plantuml.SourceStringReader;

public class FiniteAutomaton {
    // Some state variables as needed.
    // {Q, Sigma, delta, q0, F}
    final List<List<Character>> possible_states; // Possible states -> Q
    final List<Character> sigma; // Σ
    final HashMap<List<Character>, List<String>> rules; // rules
    final List<Character> initial_state; // q0

    final List<List<Character>> final_states; // F

    Boolean was_transformed_from_nfa;

    // EMPTY STRING -> $

    public FiniteAutomaton(List<List<Character>> possible_states,
                           List<Character> sigma, HashMap<List<Character>,
                           List<String>> rules,
                           List<Character> initial_state,
                           List<List<Character>> final_states,
                           Boolean has_list) {
        this.possible_states = possible_states;
        this.sigma = sigma;
        this.rules = rules;
        this.initial_state = initial_state;

        this.final_states = final_states;

        // Variable that says if the FA was transformed from NFA to DFA.
        this.was_transformed_from_nfa = false;
    }

    public FiniteAutomaton(List<Character> possible_states,
                           List<Character> sigma, HashMap<Character,
                           List<String>> rules,
                           Character initial_state,
                           List<Character> final_states) {
        this.possible_states = new ArrayList<>();
        for (Character state : possible_states) {
            this.possible_states.add(List.of(state));
        }
        // TODO: Figure this out.
//        this.possible_states.add(List.of('$'));
        this.sigma = sigma;
        this.rules = new HashMap<>();
        this.initial_state = List.of(initial_state);

        for (Character key : rules.keySet()) {
            this.rules.put(List.of(key), rules.get(key));
        }

        if (final_states.isEmpty()) {
            this.final_states = List.of(List.of('$'));
        } else {
            this.final_states = List.of(final_states);
        }

        // Variable that says if the FA was transformed from NFA to DFA.
        this.was_transformed_from_nfa = false;
    }

    public Boolean string_belongs_to_language(String input_string) {

        if (was_transformed_from_nfa) {
            System.out.println("ERROR: You can't do that with a DFA. Use the NFA version.");
            return (false);
        }

        int index = 0;

        // Add EOS (End Of String) char.
        input_string = input_string;

        // Current state of the machine as the first possible state
        List<Character> current_state = this.initial_state;

        // Iterate through each character of the `input_string`
        while (index < input_string.length()) {
            // Store current character
            Character current_letter = input_string.charAt(index);

            // Final character
            if (final_states.contains(current_state)) {
                for (List<Character> final_states_list : this.final_states) {
                    if (final_states.contains(final_states_list)) {
                        // TODO: Investigate this.
                        break;
                    } else {
                        return (false);
                    }
                }
            }

            // Apply the `current_letter` as an argument, to the current
            // state, to modify it.
            current_state = apply_rule(current_state, current_letter);


//            System.out.println(index + "Current state: " + current_state);
//            System.out.println(index + "Current letter: " + current_letter);

            // Throw error
            if (current_state.contains('N')) {
                return (false);
            }

            index++;
        }


        // If we received a terminal symbol at the end, that means
        // the check was successful.
        for (List<Character> key : this.final_states) {
            for (Character key_char : key) {
                if (current_state.contains(key_char)) { // terminal string
                    return (true);
                }
            }
        }

        // In other case, we got an error.
        return (false);
    }

    public Grammar to_regular_grammar() {

        // Convert Finite Automata rules to Grammar productions.
        HashMap<List<Character>, List<String>> productions = new HashMap<List<Character>, List<String>>();

        for(List<Character> key: this.rules.keySet()) {
            productions.put(key, this.rules.get(key));
        }

        // Integrate final state
        for(List<Character> key: this.final_states) {
            productions.put(key, List.of(""));
        }

        // Convert Finite Automata possible states to Grammar V_n.
        List<Character> V_n = new ArrayList<>();

        for(List<Character> key: this.possible_states) {
            for(Character key_char: key) {
//                productions.put(key.toString(), this.rules.get(key));
                if (!V_n.contains(key_char)) {
                    V_n.add(key_char);
                }
            }
        }

        Grammar regular_grammar = new Grammar(V_n, this.sigma, productions);

        if (was_transformed_from_nfa) {
            regular_grammar.was_transformed_from_nfa = true;
        }

        return (regular_grammar);
    }

    public Boolean is_nfa() {

        for(List<Character> key: this.rules.keySet()) {
            for (Character argument : this.sigma) {

                List<Character> temp_list = apply_rule(key, argument);

                if (temp_list.size() > 1) {
                    return (true);
                }
            }
        }

        return (false);
    }

    private List<Character> apply_rule(List<Character> current_state, Character input_argument) {

        List<Character> output_list = new ArrayList<>();

        for(List<Character> rule_list: this.rules.keySet()) {
            for(Character key: rule_list) {
                for (String value : this.rules.get(rule_list)) {
                    // Iterate through every value of current_state array.
                    for (Character current_state_value : current_state) {
                        // 1. If it is the rule we are looking for
                        // 2. If the rule corresponds to our `input_argument`
                        // 3. No duplicate values in `output_list`
                        int index = 1;

                        while (index < value.length()) {
                            if (key == current_state_value && value.charAt(0) == input_argument && !output_list.contains(value.charAt(index))) {
                                output_list.add(value.charAt(index));
                            }
                            index++;
                        }
                    }
                }
            }
        }

        return (output_list);
    }

    private List<String> get_all_outputs(List<Character> input_list) {

        List<String> output_list = new ArrayList<>();
//        List<Character> output_list = new ArrayList<>();

        // Iterate through all possible inputs.
        for (Character argument : this.sigma) {
            String temporary_result = "";
            // Result is the output from `apply_rule`.
            for (Character result : apply_rule(input_list, argument)) {
                temporary_result += result.toString();
            }
            // Check if our temporary result is not empty.
            if (!temporary_result.isEmpty())
                output_list.add(argument.toString() + temporary_result);
        }

        return (output_list);
    }

     public FiniteAutomaton nfa_to_dfa() {
         List<List<Character>> new_possible_states = new ArrayList<>(List.of(this.initial_state)); // Possible states -> Q
         HashMap<List<Character>, List<String>> new_rules = new HashMap<>(); // Rules
         List<List<Character>> new_final_states = new ArrayList<>(this.final_states); // Final states


         // Rest of iterations
         List<List<Character>> temp_possible_states = new ArrayList<>(new_possible_states);

         // Iterate until the temporary list and the `possible_states` list don't become the
         // same. Meaning, there are no new lists variations left.
         do {
             temp_possible_states = new ArrayList<>(new_possible_states);

             for (List<Character> key : temp_possible_states) {
                 for (Character argument : this.sigma) {
                     List<Character> rule_output_list = apply_rule(key, argument);

                     // Add only if it is not empty, and it is not duplicate.
                     if (!rule_output_list.isEmpty() && !new_possible_states.contains(rule_output_list)) {

                         // Add final states
                         for (Character element : rule_output_list) {
                             for (List<Character> final_state_list : this.final_states) {
                                 if (final_state_list.contains(element) && !rule_output_list.equals(final_state_list)) {
                                     new_final_states.add(rule_output_list);
                                 }
                             }
                         }
                         new_possible_states.add(rule_output_list);
                         new_rules.put(key, get_all_outputs(key));
                     }
                 }
             }
         } while (!temp_possible_states.equals(new_possible_states));

         FiniteAutomaton dfa_automaton = new FiniteAutomaton(new_possible_states, this.sigma, new_rules, this.initial_state, new_final_states, true);

         dfa_automaton.was_transformed_from_nfa = true;

         return (dfa_automaton);
     }

     public void to_png() throws IOException {
         OutputStream png = new FileOutputStream("finite_automata.png");
         String source = "@startuml\n";
         source += "hide empty description\n";

         // Sort the possible states variable, so the output of the image
         // looks prettier. :)
         this.possible_states.sort(Comparator.comparing(List<Character>::hashCode));

         for (List<Character> key : this.possible_states) {
             source += "state \"" + key.toString() + "\" as " + key.hashCode() + "\n";
         }
         source += "[*] --> " + this.initial_state.hashCode() + "\n";

         for (List<Character> key : this.rules.keySet()) {
             for (String value : this.rules.get(key)) {
                 int index = 1;
                 List <Character> temp_list = new ArrayList<>();
                 while (index < value.length()) {
                     temp_list.add(value.charAt(index));
                     index++;
                 }
                 source += key.hashCode() + " --> " + temp_list.hashCode() + " : " + value.charAt(0) + "\n";
             }
         }

         for (List<Character> final_state : this.final_states) {
             source += final_state.hashCode() + " --> [*]\n";
         }
         source += "@enduml\n";

         SourceStringReader reader = new SourceStringReader(source);
         // Write the first image to "png"
         System.out.println("Image saved!");
//         System.out.println(source);
         // Return a null string if no generation
     }
}