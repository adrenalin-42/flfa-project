import java.util.*;

import static java.lang.Character.isUpperCase;

public class Grammar
{
    // Some state variables as needed.
    // {V_n, V_t, P, S}
    final List<Character> V_n;
    final List<Character> V_t;
    final HashMap<Character, List<String>> productions;

    public Grammar(List<Character> V_n, List<Character> V_t, HashMap<Character, List<String>> productions) {
        this.V_n = V_n;
        this.V_t = V_t;
        this.productions = productions;
    }

    public String generate_string() {

        // Initiate random and other variables
        Random rand = new Random();
        String output_string = "";
        String prompt = "S";

        // Parse the prompt, until everything is not lowercase,
        // meaning all symbol are terminal.
        while (!prompt.equals(prompt.toLowerCase())) {
            int index = 0;
            output_string = "";

            // Parse the prompt length
            while (index < prompt.length()) {
                Character current_char = prompt.charAt(index);
                if (isUpperCase(current_char)) {
                    // Perform safety check
                    if (!V_n.contains(current_char)) {
                        return "ERROR: Production invalid.";
                    }
                    // Get the rules for the current character.
                    List<String> result = productions.get(current_char);
                    String next_rule = result.get(rand.nextInt(result.size()));
                    // TODO: Remove this workaround
                    // It is used to bypass generating the strings with
                    // a dot at the end. The dot is used to indicate the
                    // final state in a Finite Automata. :)
                    if (next_rule.contains(".")) {
                        next_rule = next_rule.replace(".", "");
                    }
                    output_string = output_string + next_rule;
//                    System.out.println(current_char + " --> " + next_rule);
                } else {
                    output_string = output_string + current_char;
                }
                index++;
            }
            prompt = output_string;
        }
        return output_string;
    }

    public FiniteAutomaton toFiniteAutomaton() {

        // Make the default FINAL state the dot (.),
        // as a workaround for now.
        FiniteAutomaton newAutomaton = new FiniteAutomaton(this.V_n, this.V_t, this.productions);

        return newAutomaton;
    }
}