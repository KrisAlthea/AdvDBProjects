package main.exp1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GenerateDiskData {
    public static void main(String[] args) {
        try (FileWriter writer = new FileWriter("src/main.exp1files/diskdata.txt")) {
            Random rand = new Random();
            for (int i = 0; i < 1000000; i++) { // 生成100万个随机整数
                int number = rand.nextInt(100000); // 假设整数范围在0到99999之间
                writer.write(number + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
