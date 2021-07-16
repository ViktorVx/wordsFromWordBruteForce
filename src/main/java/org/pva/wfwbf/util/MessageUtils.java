package org.pva.wfwbf.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MessageUtils {

    private MessageUtils(){}

    public static List<String> prepareMessage(Map<Integer, List<String>> map, String word) {
        var msgs = new ArrayList<String>();
        for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
            var msg = String.format("(%s) %d-буквенные(%d):\n%s\n", word, entry.getKey(),
                    entry.getValue().size(), String.join(" ", entry.getValue()));
            msgs.add(msg);
        }
        return msgs;
    }
}
