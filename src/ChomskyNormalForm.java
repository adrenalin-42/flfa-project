import java.util.*;
import java.util.stream.Collectors;

public class ChomskyNormalForm extends Grammar {

    public ChomskyNormalForm(Grammar grammar) {
        super(grammar.V_n, grammar.V_t, grammar.productions, grammar.start_symbol);

        productions = eliminateEpsilonProductions();
        productions = removeUnitProductions();
        productions = removeInaccessibleSymbols();
        productions = removeNonProductiveSymbols();
        productions = toCNF();
    }

    public ChomskyNormalForm(List<Character> V_n, List<Character> V_t, HashMap<List<Character>, List<String>> productions, Character start_symbol) {
        super(V_n, V_t, productions, start_symbol);
    }

    public HashMap<List<Character>, List<String>> toCNF() {
        HashMap<List<Character>, List<String>> newProductions = new HashMap<>();
        int nextNewVar = 1;
        HashMap<Character, String> terminalVarMap = new HashMap<>();
        HashMap<String, List<Character>> prodVarMap = new HashMap<>();


        for (List<Character> rule : productions.keySet()) {
            for (String production : productions.get(rule)) {
                if (production.length() >= 3) {
                    if (prodVarMap.get(production) == null) {
                        List<Character> prodVars = new ArrayList<>();
                        for (int i = 0; i < production.length() - 1; i++) {
                            Character temp_value = (char) (48 + nextNewVar + i);
                            prodVars.add(temp_value);
                        }
                        nextNewVar += production.length() - 1;
                        V_n.addAll(prodVars);
                        prodVarMap.put(production, prodVars);
                    }
                    List<Character> prodVars = new ArrayList<>(prodVarMap.get(production));

                    QualityOfLife.hashMapAppend(newProductions, rule, List.of(production.charAt(0), prodVars.get(0)), true);
//                    System.out.println(production + " -> " + prodVars);
//                    System.out.println(rule + " -> " + prodVars);
//                    System.out.println("PRODVARS: " + prodVars);
                    for (int i = 0; i < production.length() - 2; i++) {
                        if (newProductions.get(List.of(prodVars.get(i))) == null) {
                            QualityOfLife.hashMapAppend(newProductions,
                                    List.of(prodVars.get(i)),
                                    List.of(production.charAt(i + 1),
                                            prodVars.get(i + 1)),
                                    true);
                        }
                    }
                    QualityOfLife.hashMapAppend(newProductions,
                            List.of(prodVars.get(prodVars.size() - 1)),
                            List.of(production.charAt(production.length() - 1)),
                            true);
                }
                else if (production.length() == 2 && QualityOfLife.list_ch_contains_all_string(V_n, production)) {
                    QualityOfLife.hashMapAppend(newProductions, rule, production);
                }
                else {
                    String newProd = production;
                    for (int i = 0; i < newProd.length(); i++) {
                        Character sym = newProd.charAt(i);

                        if (V_t.contains(sym)) {
                            if (!terminalVarMap.containsKey(sym)) {
                                Character temp_value = (char) (48 + nextNewVar + i);
                                nextNewVar += 1;
                                V_n.add(temp_value);
                                QualityOfLife.hashMapAppend(newProductions, List.of(temp_value), sym);

                                terminalVarMap.put(sym, temp_value.toString());
                            }
//                            System.out.println("BEFORE REPLACEMENT: " + newProd);
                            newProd = newProd.replaceFirst(sym.toString(), terminalVarMap.get(sym));
//                            newProd = newProd.replaceFirst()
//                            System.out.println("AFTER REPLACEMENT: " + newProd);

                        }
                    }
                    QualityOfLife.hashMapAppend(newProductions, rule, newProd);
                }
            }
        }


        return (newProductions);
    }

    public HashMap<List<Character>, List<String>> removeNonProductiveSymbols() {

        List<Character> productiveSymbols = new ArrayList<>();

        boolean changed = true;
        while (changed) {
            changed = false;

            for (List<Character> rule : productions.keySet()) {
                for (String production : productions.get(rule)) {
                    if (!QualityOfLife.list_ch_contains_list_ch(rule, productiveSymbols) &&
                            (QualityOfLife.list_ch_contains_all_string(productiveSymbols, production)
                            || QualityOfLife.list_ch_contains_all_string(V_t, production)))
                    {
                        productiveSymbols.add(rule.get(0));
                        changed = true;
                    }
                }
            }
        }

//        System.out.println("PRODUCTIVE: " + productiveSymbols);

        List<Character> nonProductiveSymbols = new ArrayList<>();
        for (Character elem : V_n) {
            if (!productiveSymbols.contains(elem)) {
                nonProductiveSymbols.add(elem);
            }
        }

//        System.out.println("NONPRODUCTIVE: " + nonProductiveSymbols);
//        System.out.println(nonProductiveSymbols);


        HashMap<List<Character>, List<String>> newProductions = new HashMap<>();

        for (List<Character> rule : productions.keySet()) {
            for (String production : productions.get(rule)) {
                if (QualityOfLife.list_ch_contains_list_ch(productiveSymbols, rule)) {
                    boolean allSymbolsAreProductive = true;
                    for (int i = 0; i < production.length(); i++) {
                        Character sym = production.charAt(i);
                        if (!productiveSymbols.contains(sym) && !V_t.contains(sym)) {
                            allSymbolsAreProductive = false;
                            break;
                        }
                    }
                    if (allSymbolsAreProductive) {
                        if (newProductions.get(rule) == null) {
                            newProductions.put(rule, List.of(production));
                        } else {
                            List<String> tmp_list = new ArrayList<>(newProductions.get(rule));

                            tmp_list.add(production);

                            newProductions.replace(rule, tmp_list);
                        }
                    }
                }
            }
        }

        List<Character> newVariables = new ArrayList<>();

        for (Character elem : V_n) {
            if (productiveSymbols.contains(elem)) {
                newVariables.add(elem);
            }
        }

        V_n = newVariables;
//        System.out.println("NEW VARS: " + newVariables);


        return newProductions;
    }



    public HashMap<List<Character>, List<String>> removeInaccessibleSymbols() {
        HashMap<List<Character>, List<String>> newProductions = new HashMap<>();;
        List<Character> accessibleSymbols = new ArrayList<>(List.of('S'));

        boolean changed = true;
        while (changed) {
            changed = false;

            for (List<Character> rule : productions.keySet()) {
                for (String production : productions.get(rule)) {
//                    for (Character rule_char : rule) {
//                        if (accessibleSymbols.contains(rule_char)) {
                        if (QualityOfLife.list_ch_contains_list_ch(accessibleSymbols, rule)) {
//                            System.out.println("I got executed");
                            for (int i = 0; i < production.length(); i++) {
                                Character tmp_char = production.charAt(i);

                                if (V_n.contains(tmp_char) && !accessibleSymbols.contains(tmp_char)) {
                                    accessibleSymbols.add(tmp_char);
                                    changed = true;
                                }
                            }
                        }
//                    }
                }
            }
        }

        for (List<Character> rule : productions.keySet()) {
            if (QualityOfLife.list_ch_contains_list_ch(accessibleSymbols, rule)) {
                newProductions.put(rule, productions.get(rule));
            }
        }

        // Update V_n
        this.V_n = accessibleSymbols;


        return newProductions;
    }

    public HashMap<List<Character>, List<String>> removeUnitProductions() {
        HashMap<List<Character>, List<String>> unitProduction = new HashMap<>();
        HashMap<List<Character>, List<String>> nonUnitProduction = new HashMap<>();
        HashMap<List<Character>, List<String>> newProductions;

        for (List<Character> rule : productions.keySet()) {
            for (String production: productions.get(rule)) {
                if (production.length() == 1 && QualityOfLife.list_ch_contains_any_string(V_n, production)) {
                    if (unitProduction.get(rule) == null) {
                        unitProduction.put(rule, List.of(production));
                    } else {
                        List<String> tmp_list = new ArrayList<>(unitProduction.get(rule));

                        tmp_list.add(production);

                        unitProduction.replace(rule, tmp_list);
                    }
//                    break;
                }
                else {
                    if (nonUnitProduction.get(rule) == null) {
                        nonUnitProduction.put(rule, List.of(production));
                    } else {
                        List<String> tmp_list = new ArrayList<>(nonUnitProduction.get(rule));

                        tmp_list.add(production);

                        nonUnitProduction.replace(rule, tmp_list);
                    }
                }
            }
        }

//        System.out.println("Unit prods: " + unitProduction);
//        System.out.println("Non Unit prods: " + nonUnitProduction);

        newProductions = new HashMap<>(nonUnitProduction);

//        System.out.println("newProductions copy: " + newProductions);

        for (List<Character> uRule : unitProduction.keySet()) {
            for (String uProduction: unitProduction.get(uRule)) {
                for (List<Character> nuRule: nonUnitProduction.keySet()) {
                    for (String nuProduction: nonUnitProduction.get(nuRule)) {
//                        System.out.println("production: " + production + " nuRule: " + nuRule.get(0).toString());
                        if (uProduction.equals(nuRule.get(0).toString())) {
//                            newProductions.put(rule, List.of(nuProduction));
                            if (newProductions.get(uRule) == null) {
                                newProductions.put(uRule, List.of(nuProduction));
                            } else {
                                List<String> tmp_list = new ArrayList<>(newProductions.get(uRule));
                                tmp_list.add(nuProduction);
                                newProductions.replace(uRule, tmp_list);
                            }
                        }
                    }
                }
            }
        }

        return newProductions;
    }

    public HashMap<List<Character>, List<String>> eliminateEpsilonProductions() {
        ArrayList<List<Character>> haveNullProductions = new ArrayList<>();
        HashMap<List<Character>, List<String>> new_productions = new HashMap<>();


        // Find null productions
        for (List<Character> rule : productions.keySet()) {
            for (String production: productions.get(rule)) {
                if (production.equals("$") && !haveNullProductions.contains(rule)) {
                    haveNullProductions.add(rule);
                }
            }
        }

        for (List<Character> rule : productions.keySet()) {
            for (String production: productions.get(rule)) {
                if (!production.equals("$")) {
                    if (new_productions.get(rule) == null) {
                        new_productions.put(rule, List.of(production));
                    } else {
                        List<String> tmp_list = new ArrayList<>(new_productions.get(rule));
                        tmp_list.add(production);
                        new_productions.replace(rule, tmp_list);
                    }
                }
                for (int i = 0; i < production.length(); i++) {
                    if (haveNullProductions.contains(List.of(production.charAt(i)))) {
                        List<String> tmp_list = new ArrayList<>(new_productions.get(rule));
//                        production = production.replace("$", "");
                        tmp_list.add(production.substring(0, i));
                        if (new_productions.get(rule) == null) {
                            new_productions.put(rule, tmp_list);
                        } else {
                            new_productions.replace(rule, tmp_list);
                        }
                    }
                }
            }
        }

        return new_productions;
    }
}
