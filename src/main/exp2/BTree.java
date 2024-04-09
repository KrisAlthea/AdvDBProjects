package main.exp2;

public class BTree {
	private BTreeNode root;
	private int t; // 最小度数

	public BTree (int t) {
		this.root = null;
		this.t = t;
	}

	// 插入键
	public void insert (int key) {
		if (root == null) {
			root = new BTreeNode(t, true);
			root.keys[0] = key;
			root.n = 1;
		} else {
			if (root.n == 2 * t - 1) {
				BTreeNode s = new BTreeNode(t, false);
				s.children[0] = root;
				s.splitChild(0, root);
				int i = 0;
				if (s.keys[0] < key)
					i++;
				s.children[i].insertNonFull(key);
				root = s;
			} else
				root.insertNonFull(key);
		}
	}
	// 实现删除方法
	public void delete(int key) {
		if (root == null) {
			System.out.println("The tree is empty");
			return;
		}

		// 从根节点开始删除键
		root.deleteKey(key, t);

		// 如果根节点键数为0，那么它的第一个子节点成为新的根节点
		// 如果树有一个键，那么树会减少一个层级
		if (root.n == 0) {
			if (root.leaf)
				root = null; // 树变为空
			else
				root = root.children[0]; // 第一个子节点成为新的根节点
		}
	}
	// 打印树的内容
	public void print () {
		if (root != null) root.print("", true);
	}
}
