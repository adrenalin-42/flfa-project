import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QualityOfLife {
    public static List<Character> generate_vn_from_productions(HashMap<List<Character>, List<String>> productions) {
        List<Character> output_vn = new ArrayList<>();

        for (List<Character> item : productions.keySet()) {
            for (Character value : item) {
                if (!output_vn.contains(value)) {
                    output_vn.add(value);
                }
            }
            for (String value : productions.get(item)) {
                for (int i = 0; i < value.length(); i++) {
                    Character tmp_char = value.charAt(i);

                    if (!output_vn.contains(tmp_char) && Character.isUpperCase(tmp_char)) {
                        output_vn.add(tmp_char);
                    }
                }
            }
        }
        return (output_vn);
    }

    public static void hashMapAppend(HashMap<List<Character>, List<String>> hashMap, List<Character> key, String value) {
        if (hashMap.get(key) == null) {
            hashMap.put(key, List.of(value));
        } else {
            List<String> tmp_list = new ArrayList<>(hashMap.get(key));

            tmp_list.add(value);

            hashMap.replace(key, tmp_list);
        }
    }

    public static void hashMapAppend(HashMap<List<Character>, List<String>> hashMap, List<Character> key, Character value) {
        if (hashMap.get(key) == null) {
            hashMap.put(key, List.of(value.toString()));
        } else {
            List<String> tmp_list = new ArrayList<>(hashMap.get(key));

            tmp_list.add(value.toString());

            hashMap.replace(key, tmp_list);
        }
    }

    public static void hashMapAppend(HashMap<List<Character>, List<String>> hashMap, List<Character> key, List<Character> value, boolean like_string) {
        if (hashMap.get(key) == null) {
            if (like_string) {
                String tmp_string = "";

                for (Character sym : value) {
                    tmp_string += sym.toString();
                }
                hashMap.put(key, List.of(tmp_string));

            } else {
                List<String> tmp_string_list = new ArrayList<>();
                for (Character sym : value) {
                    tmp_string_list.add(sym.toString());
                }
                hashMap.put(key, tmp_string_list);

            }

        } else {
            List<String> tmp_list = new ArrayList<>(hashMap.get(key));
            if (like_string) {
                String tmp_string = "";

                for (Character sym : value) {
                    tmp_string += sym.toString();
                }
                tmp_list.add(tmp_string);
            } else {
                for (Character sym : value) {
                    tmp_list.add(sym.toString());
                }
            }

            hashMap.replace(key, tmp_list);

//            tmp_list.add(value);

        }
    }

    public static <K, V> Map<V, K> invert(Map<K, V> map)
    {
        Map<V, K> result = new HashMap<V, K>();
        for (Map.Entry<K, V> entry : map.entrySet())
        {
            result.put(entry.getValue(), entry.getKey());
        }
        return result;
    }

    public static void hashMapAppend(HashMap<List<Character>, List<Character>> hashMap, List<Character> key, List<Character> value) {
        if (hashMap.get(key) == null) {
            hashMap.put(key, value);
        } else {
            List<Character> tmp_list = new ArrayList<>(hashMap.get(key));

            tmp_list.addAll(value);

            hashMap.replace(key, tmp_list);
        }
    }

    public static void hashMapAppend(HashMap<String, List<Character>> hashMap, String key, List<Character> value) {
        if (hashMap.get(key) == null) {
            hashMap.put(key, value);
        } else {
            List<Character> tmp_list = new ArrayList<>(hashMap.get(key));

            tmp_list.addAll(value);

            hashMap.replace(key, tmp_list);
        }
    }

    public static List<Character> generate_vt_from_productions(HashMap<List<Character>, List<String>> productions) {
        List<Character> vn = generate_vn_from_productions(productions);
        List<Character> output_vt = new ArrayList<>();

        for (List<Character> key : productions.keySet()) {
            for (String value : productions.get(key)) {
                for (int i = 0; i < value.length(); i++) {
                    if (!output_vt.contains(value.charAt(i)) && !vn.contains(value.charAt(i)) && value.charAt(i) != '$') {
                        output_vt.add(value.charAt(i));
                    }
                }
            }
        }

        return (output_vt);
    }

    public static HashMap<List<Character>, List<String>> initialize_productions() {
        return (new HashMap<>());
    }

    public static Boolean list_ch_contains_any_string(List<Character> list, String str) {
        for (int i = 0; i < str.length(); i++) {

            Character tmp_char = str.charAt(i);

            if (list.contains(tmp_char)) {
                return true;
            }
        }

        return false;
    }

    public static Boolean list_ch_contains_all_string(List<Character> list, String str) {
        for (int i = 0; i < str.length(); i++) {

            Character tmp_char = str.charAt(i);

            if (!list.contains(tmp_char)) {
                return false;
            }
        }

        return true;
    }

    public static Boolean list_ch_contains_list_ch(List<Character> list1, List<Character> list2) {
//        System.out.println("List1: " + list1);
//        System.out.println("List2: " + list2);
        for (Character list1_item : list1) {
            if (list2.contains(list1_item)) {
                return true;
            }
        }

        return false;
    }


//    public static HashMap<List<Character>, List<String>> production_add_rule(HashMap<List<Character>, List<String>> productions, Character key, String value) {
//        List<String> old_list = productions.get(List.of(key));
//        List<String> new_list = new ArrayList<>(productions.get(List.of(key)));
//
//        new_list.add(value);
//        productions.replace(List.of(key), old_list, new_list);
//        return (productions);
//    }
}
