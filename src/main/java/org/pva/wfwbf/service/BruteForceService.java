package org.pva.wfwbf.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.paukov.combinatorics3.Generator;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Service
@AllArgsConstructor
@Slf4j
public class BruteForceService {

    private static final Set<String> dict = new HashSet<>();

    static {
        loadDictionary(dict, "russian_nouns.txt");
        log.info(String.valueOf(dict.size()));
        loadDictionary(dict, "answers.txt");
        log.info(String.valueOf(dict.size()));
    }

    public List<String> bruteForce(String word) {
        log.info(word);
        if (isBlank(word) || word.length() == 1)
            return Collections.emptyList();

        var chars = Arrays.stream(word.toLowerCase().split("")).collect(Collectors.toList());

        var combinations = new HashSet<String>();
        for (var i = 2; i <= chars.size(); i++) {
            Generator.combination(chars)
                    .simple(i)
                    .stream()
                    .forEach(cmb -> Generator.permutation(cmb)
                            .simple()
                            .stream()
                            .map(elems -> String.join("", elems))
                            .filter(dict::contains)
                            .forEach(combinations::add)
                    );
        }
        //***
        fillAnswerFromForeignApi(combinations, word);
        //***
        var combinationList = new ArrayList<>(combinations);
        combinationList
                .sort(Comparator.comparing(String::length).thenComparing(Comparator.naturalOrder()));
        return combinationList;
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

    private static void loadDictionary(final Set<String> dict, String fileName) {
        Stream<String> lines = null;
        try {
            var path = Paths.get(Objects.requireNonNull(BruteForceService.class.getClassLoader()
                    .getResource(fileName)).toURI());
            lines = Files.lines(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (lines != null)
            lines.forEach(dict::add);
    }
}
