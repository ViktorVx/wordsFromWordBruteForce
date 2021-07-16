package org.pva.wfwbf.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageUtils {

    private MessageUtils(){}

    private static final Map<Integer, String> letterNumberMap = new HashMap<>();

    static {
        letterNumberMap.put(2, "Двухбуквенные");
        letterNumberMap.put(3, "Трехбуквенные");
        letterNumberMap.put(4, "Четырехбуквенные");
        letterNumberMap.put(5, "Пятибуквенные");
        letterNumberMap.put(6, "Шестибуквенные");
        letterNumberMap.put(7, "Семибуквенные");
        letterNumberMap.put(8, "Восьмибуквенные");
        letterNumberMap.put(9, "Девятибуквенные");
        letterNumberMap.put(10, "Десятибуквенные");
        letterNumberMap.put(11, "Одиннадцатибуквенные");
        letterNumberMap.put(12, "Двеннадцатибуквенные");
    }

    public static List<String> prepareMessage(Map<Integer, List<String>> map, String word) {
        var msgs = new ArrayList<String>();
        for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
            var msg = String.format("(%s) %s(%d):\n%s\n", word, letterNumberMap.get(entry.getKey()),
                    entry.getValue().size(), String.join(" ", entry.getValue()));
            msgs.add(msg);
        }
        return msgs;
    }
}
