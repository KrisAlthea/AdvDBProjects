package main.exp1;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortBlocks {
    public static void main(String[] args) {
        String inputFile = "src/main.exp1files/diskdata.txt";
        int blockSize = 10000; // 每个块10000行
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            boolean done = false;
            int fileCount = 0;
            while (!done) {
                List<Integer> block = new ArrayList<>();
                for (int i = 0; i < blockSize; i++) {
                    String line = reader.readLine();
                    if (line == null) {
                        done = true;
                        break;
                    }
                    block.add(Integer.parseInt(line));
                }
                Collections.sort(block);

                try (BufferedWriter writer = new BufferedWriter(
                        new FileWriter("src/main.exp1files/sortedBlock" + fileCount + ".txt"))) {
                    for (int num : block) {
                        writer.write(num + "\n");
                    }
                }
                fileCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
