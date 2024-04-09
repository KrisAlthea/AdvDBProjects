package main.exp1;

import java.io.*;

public class MergeSortedBlocks {
    public static void main(String[] args) {
        String file1 = "src/main.exp1files/sortedBlock0.txt";
        String file2 = "src/main.exp1files/sortedBlock1.txt";
        String outputFile = "src/main.exp1files/finalSorted.txt";
        try (BufferedReader reader1 = new BufferedReader(new FileReader(file1));
             BufferedReader reader2 = new BufferedReader(new FileReader(file2));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            String line1 = reader1.readLine();
            String line2 = reader2.readLine();
            while (line1 != null && line2 != null) {
                int num1 = Integer.parseInt(line1);
                int num2 = Integer.parseInt(line2);
                if (num1 < num2) {
                    writer.write(line1 + "\n");
                    line1 = reader1.readLine();
                } else {
                    writer.write(line2 + "\n");
                    line2 = reader2.readLine();
                }
            }
            while (line1 != null) {
                writer.write(line1 + "\n");
                line1 = reader1.readLine();
            }
            while (line2 != null) {
                writer.write(line2 + "\n");
                line2 = reader2.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
