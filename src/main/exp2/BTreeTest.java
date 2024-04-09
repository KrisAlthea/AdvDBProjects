package main.exp2;

public class BTreeTest {
    public static void main(String[] args) {
        BTree t = new BTree(3); // 创建一个B树实例，假设最小度数t=3

        // 插入操作
        int[] keysToInsert = {10, 20, 5, 6, 12, 30, 7, 17, 3, 4, 22, 25, 28, 42, 47, 50, 55, 60, 65, 2, 1};
        for (int key : keysToInsert) {
            t.insert(key);
        }
        System.out.println("插入操作后的B树结构:");
        t.print();

        // 删除操作
        int[] keysToDelete = {6, 13, 7, 4, 2};
        for (int key : keysToDelete) {
            System.out.println("\n删除键 " + key + " 后的B树结构:");
            t.delete(key); // 注意: 如果delete方法或B树类中的其他相关方法尚未适配完全参数列表，可能需要调整
            t.print();
        }
    }
}
