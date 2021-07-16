package org.pva.wfwbf.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Service
@AllArgsConstructor
@Slf4j
public class BruteForceService {

    private static final Map<String, Map<String, Long>> dictStats = new HashMap<>();

    static {
        loadDictionary("russian_nouns.txt");
        log.info(String.valueOf(dictStats.size()));
        loadDictionary("answers.txt");
        log.info(String.valueOf(dictStats.size()));
    }

    public Map<Integer, List<String>> bruteForce(String word) {
        log.info(word);
        if (isBlank(word) || word.length() == 1)
            return Collections.emptyMap();

        var combinations = new HashSet<String>();
        //***
        var wordLength = word.length();
        var chars = Arrays.stream(word.toLowerCase().split("")).collect(Collectors.toList());
        var wordStat = chars.stream().collect(Collectors.groupingBy(c -> c, Collectors.counting()));
        dictStats.keySet().stream()
                .filter(w -> w.length() <= wordLength)
                .filter(w -> {
                    var stat = dictStats.get(w);
                    for (var entry : stat.entrySet()) {
                        if (!wordStat.containsKey(entry.getKey()))
                            return false;
                        if (wordStat.get(entry.getKey()) < entry.getValue())
                            return false;
                    }
                    return true;
                })
                .forEach(combinations::add);

        //***
        fillAnswerFromForeignApi(combinations, word);
        //***
        var combinationList = new ArrayList<>(combinations);
        var sortedList = combinationList
                .stream()
                .map(String::toLowerCase)
                .sorted(Comparator.comparing(String::length).thenComparing(Comparator.naturalOrder()))
                .collect(Collectors.toList());
        return sortedList.stream().collect(Collectors.groupingBy(String::length, Collectors.toList()));
    }

    private void fillAnswerFromForeignApi(Set<String> combinations, String word) {
        var client = WebClient.create();
        var responseSpec = client.get()
                .uri(String.format("https://wordparts.ru/anagramma/?word=%s", word.toUpperCase()))
                .retrieve();
        var html = responseSpec.bodyToMono(String.class).block();

        var str = String.format("<%s>(.*?)</%s>", "strong", "span");
        var pattern = Pattern.compile(str);
        if (html != null) {
            var matcher = pattern.matcher(html);
            while (matcher.find()) {
                combinations.add(matcher.group(1));
            }
        }
    }

    private static void loadDictionary(String fileName) {
        String[] lines = null;
        try {
            var res = BruteForceService.class.getResourceAsStream("/" + fileName);
            String result = IOUtils.toString(res, StandardCharsets.UTF_8);
            lines = result.split("\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (lines != null) {
            for (String w : lines) {
                dictStats.put(w, Arrays.stream(w.toLowerCase().split(""))
                        .collect(Collectors.groupingBy(c -> c, Collectors.counting())));
            }
        }
    }
}
