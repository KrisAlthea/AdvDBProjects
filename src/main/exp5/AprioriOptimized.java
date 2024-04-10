package main.exp5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AprioriOptimized {
    private static final String FILE_PATH = "src/main/exp5files/retail.txt"; // 文件路径
    private static final double SUPPORT_THRESHOLD_PERCENTAGE = 5.0; // 支持度阈值百分比
    private static final int TOTAL_BASKETS = 88162; // 总篮子数

    public static void main(String[] args) throws IOException {
        List<Set<String>> transactions = readTransactions(FILE_PATH);
        int supportThreshold = (int) (TOTAL_BASKETS * SUPPORT_THRESHOLD_PERCENTAGE / 100);
        System.out.println("Support threshold: " + supportThreshold);

        Map<String, Integer> itemFrequency = findSingletonFrequencies(transactions);
        pruneInfrequentItems(itemFrequency, supportThreshold);

        Map<Set<String>, Integer> frequentPairs = findFrequentPairs(transactions, itemFrequency, supportThreshold);
        frequentPairs.forEach((key, value) -> System.out.println(key + ": " + value));
    }

    // 读取事务数据
    private static List<Set<String>> readTransactions(String filePath) throws IOException {
        List<Set<String>> transactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Set<String> transaction = new HashSet<>(Arrays.asList(line.trim().split(" ")));
                transactions.add(transaction);
            }
        }
        return transactions;
    }

    // 找到单项频率
    private static Map<String, Integer> findSingletonFrequencies(List<Set<String>> transactions) {
        Map<String, Integer> frequency = new HashMap<>();
        for (Set<String> transaction : transactions) {
            for (String item : transaction) {
                frequency.put(item, frequency.getOrDefault(item, 0) + 1);
            }
        }
        return frequency;
    }

    // 剪枝非频繁项
    private static void pruneInfrequentItems(Map<String, Integer> frequency, int threshold) {
        frequency.entrySet().removeIf(entry -> entry.getValue() < threshold);
    }

    // 找到频繁对
    private static Map<Set<String>, Integer> findFrequentPairs
    (List<Set<String>> transactions, Map<String, Integer> frequentItems, int threshold) {
        Map<Set<String>, Integer> pairFrequency = new HashMap<>();

        for (Set<String> transaction : transactions) {
            List<String> items = new ArrayList<>(transaction);
            for (int i = 0; i < items.size(); i++) {
                for (int j = i + 1; j < items.size(); j++) {
                    String item1 = items.get(i);
                    String item2 = items.get(j);
                    if (frequentItems.containsKey(item1) && frequentItems.containsKey(item2)) {
                        Set<String> pair = new HashSet<>(Arrays.asList(item1, item2));
                        pairFrequency.put(pair, pairFrequency.getOrDefault(pair, 0) + 1);
                    }
                }
            }
        }

        // 筛选频繁对
        pairFrequency.entrySet().removeIf(entry -> entry.getValue() < threshold);
        return pairFrequency;
    }
}
