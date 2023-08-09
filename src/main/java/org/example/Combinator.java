package org.example;

import java.util.*;

public class Combinator {

    private static void iteration(int n, int k, int index, int i, Set<List<Integer>> all, List<Integer> tmp) {
        if (index == k) {
            all.add(tmp.stream().toList());
            return;
        }

        // out of bounds
        if (i >= n) {
            return;
        }

        tmp.set(index, i + 1);
        iteration(n, k, index + 1, i + 1, all, tmp);
        iteration(n, k, index, i + 1, all, tmp);
    }

    public static Set<List<Integer>> combineKOutOfN(int k, int n) {
        var combinations = new HashSet<List<Integer>>();
        var tmp = new ArrayList<Integer>(k);

        for (int i = 0; i < k; i++) {
            tmp.add(0);
        }

        iteration(n, k, 0, 0, combinations, tmp);
        return combinations;
    }
}
