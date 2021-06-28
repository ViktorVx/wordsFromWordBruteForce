package org.pva.wfwbf.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Service
@AllArgsConstructor
@Slf4j
public class BruteForceService {

    public List<String> bruteForce(String word) {
        if (isBlank(word))
            return Collections.emptyList();

        //******
        var arr = List.of("1", "2", "3", "4", "5");
        var r = 3;
        int n = arr.size();
        printCombination(arr, n, r);
        //******

        var chars = List.of(word.split(""));
        return chars;
    }

    static void combinationUtil(List<String> arr, String[] data, int start, int end, int index, int r) {
        if (index == r) {
            for (var j = 0; j < r; j++)
                log.info(data[j] + " ");
            log.info("");
            return;
        }

        for (int i = start; i <= end && end - i + 1 >= r - index; i++) {
            data[index] = arr.get(i);
            combinationUtil(arr, data, i + 1, end, index + 1, r);
        }
    }

    static void printCombination(List<String> arr, int n, int r) {
        var data = new String[r];
        combinationUtil(arr, data, 0, n - 1, 0, r);
    }
}
