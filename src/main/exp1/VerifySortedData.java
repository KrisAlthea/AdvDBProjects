package main.exp1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class VerifySortedData {
    public static void main(String[] args) {
        String inputFilePath = "src/main.exp1files/diskdata.txt"; // 根据实际路径调整
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            String previousLine = reader.readLine();
            String currentLine;
            boolean isSorted = true;
            
            while ((currentLine = reader.readLine()) != null && isSorted) {
                if (Integer.parseInt(previousLine) > Integer.parseInt(currentLine)) {
                    isSorted = false;
                    break;
                }
                previousLine = currentLine;
            }
            
            if (isSorted) {
                System.out.println("The file is correctly sorted.");
            } else {
                System.out.println("The file is not sorted correctly.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
