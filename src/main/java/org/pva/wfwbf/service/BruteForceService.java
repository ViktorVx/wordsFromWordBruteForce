package org.pva.wfwbf.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.paukov.combinatorics3.Generator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Service
@AllArgsConstructor
@Slf4j
public class BruteForceService {

    public List<String> bruteForce(String word) {
        if (isBlank(word) || word.length() == 1)
            return Collections.emptyList();

        log.info("*******************");
        var chars = Arrays.stream(word.split("")).collect(Collectors.toList());
        var combinations = new ArrayList<String>();
        for (int i = 2; i <= chars.size(); i++) {
            Generator.combination(chars)
                    .simple(i)
                    .stream()
                    .forEach(cmb -> Generator.permutation(cmb)
                            .simple()
                            .stream()
                            .map(elems -> String.join("", elems))
                            .forEach(combinations::add)
                    );
        }

//        for (String combination : combinations) {
//            log.info(combination);
//        }
        log.info("*******************");
        log.info(String.valueOf(combinations.size()));
        log.info(combinations.get(0));
        //******
        return chars;
    }
}
