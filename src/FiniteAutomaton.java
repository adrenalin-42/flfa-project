import java.util.*;

public class FiniteAutomaton {
    // Some state variables as needed.
    // {Q, Sigma, delta, q0, F}
    final List<Character> possible_states; // Possible states -> Q
    final List<Character> sigma; // Σ
    final HashMap<Character, List<String>> rules; // rules
    Character current_state = 'S';
    final List<Character> final_state; // F



    public FiniteAutomaton(List<Character> possible_states, List<Character> sigma, HashMap<Character, List<String>> rules) {
        this.possible_states = possible_states;
        this.sigma = sigma;
        this.rules = rules;
        this.final_state = List.of('.');
    }

    public boolean string_belongs_to_language(final String input_string) {
        int index = 0;

        // Iterate through each character of the `input_string`
        while (index < input_string.length()) {
            // Store current character
            Character current_letter = input_string.charAt(index);

            // Apply the `current_letter` as an argument, to the current
            // state, to modify it.
            current_state = apply_rule(current_state, current_letter);

            index++;
        }
        // If we received a terminaSl symbol at the end, that means
        // the check was successful.
        if (final_state.contains(current_state)) { // terminal string
            return true;
        }
        // In other case, we got an error.
        return false;
    }

    private Character apply_rule(Character current_state, Character input_symbol) { // delta
        // If our `current_state` is not present in `possible_states`,
        // then we clearly have an illegal symbol, and we should return
        // an error, denoted by 'N'.
        if (!possible_states.contains(current_state)) {
            return 'N';
        }

        // If we don't have a key defined for the current state, then
        // we have another error, and we return error -> 'N'.
        if (!rules.containsKey(current_state)) {
            return 'N';
        }

        // Get the productions for the current state, a.k.a. non-terminal letter.
        List<String> current_state_rule = rules.get(current_state);

        // Iterate through all rules of the current production.
        for (String rule_string : current_state_rule) {
            // If the first symbol is equal to the `input_symbol`, then
            // we found the production rule we were searching.
            if (rule_string.charAt(0) == input_symbol) {
                // Final state rule if the next symbol in being
                // contained in the `final_state`, then we stop
                // execution, we can't return from the final state.
                if (final_state.contains(rule_string.charAt(1))) {
                    return (rule_string.charAt(1)); // termination
                }
                // If the first condition didn't work, then we return the
                // next symbol, which SHOULD BE a non-terminal letter.
                return rule_string.charAt(1);
            }
        }
        // Safety return, in case neither of the returns worked,
        // return error.
        return 'N';
    }
}