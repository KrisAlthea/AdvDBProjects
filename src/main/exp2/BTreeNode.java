package main.exp2;

public class BTreeNode {
    int[] keys; // 节点中的键值
    int t; // 最小度数（定义了树的分支数目范围）
    BTreeNode[] children; // 子节点引用
    int n; // 当前键值数量
    boolean leaf; // 是否为叶节点

    public BTreeNode(int t, boolean leaf) {
        this.t = t;
        this.leaf = leaf;
        this.keys = new int[2*t - 1]; // B树节点中最多可以有2t-1个键
        this.children = new BTreeNode[2*t]; // B树节点中最多可以有2t个子节点
        this.n = 0; // 初始时，节点中没有键
    }
    // 插入新的键到非满节点
    public void insertNonFull(int key) {
        int i = n - 1; // 初始化i为最后一个键的索引
        if (leaf) {
            // 如果这是一个叶节点，找到新键的位置，并移动所有大于键的值到一个位置向后
            while (i >= 0 && keys[i] > key) {
                keys[i + 1] = keys[i];
                i--;
            }
            // 插入新键到找到的位置
            keys[i + 1] = key;
            n = n + 1;
        } else {
            // 如果这不是一个叶节点，找到新键将要插入的子节点的位置
            while (i >= 0 && keys[i] > key)
                i--;
            // 查看找到的子节点是否满了
            if (children[i + 1].n == 2*t - 1) {
                // 如果子节点满了，则分裂它
                splitChild(i + 1, children[i + 1]);
                // 分裂后，中间键上升，并且分裂节点被分成两个。判断新键应该插入哪个部分
                if (keys[i + 1] < key)
                    i++;
            }
            children[i + 1].insertNonFull(key);
        }
    }
    // 分裂子节点
    public void splitChild(int i, BTreeNode y) {
        BTreeNode z = new BTreeNode(y.t, y.leaf);
        z.n = t - 1; // 新节点将有t-1个键

        // 将y的后半部分键移动到z
        for (int j = 0; j < t - 1; j++) {
            z.keys[j] = y.keys[j + t];
        }

        // 如果y不是叶子，将y的后半部分子节点移动到z
        if (!y.leaf) {
            for (int j = 0; j < t; j++) {
                z.children[j] = y.children[j + t];
            }
        }

        y.n = t - 1; // 更新y的键数量

        // 将子节点移动使得父节点可以链接新的子节点z
        for (int j = n; j >= i + 1; j--) {
            children[j + 1] = children[j];
        }
        children[i + 1] = z;

        // 将y的中间键移动到此节点
        for (int j = n - 1; j >= i; j--) {
            keys[j + 1] = keys[j];
        }
        keys[i] = y.keys[t - 1];

        n = n + 1;
    }
    // 用于打印树和它的内容；方便调试
    public void print(String indent, boolean last) {
        System.out.print(indent);
        if (last) {
            System.out.print("└─");
            indent += "  ";
        } else {
            System.out.print("├─");
            indent += "| ";
        }

        // 打印当前节点的所有键
        for (int i = 0; i < n; i++) {
            System.out.print(keys[i]);
            if (i < n - 1) System.out.print(", ");
        }
        System.out.println();

        // 递归打印子节点
        for (int i = 0; i < n + 1; i++) {
            if (children[i] != null) {
                children[i].print(indent, i == n);
            }
        }
    }
    // BTreeNode类内部
// 删除当前子树中的键
    public void deleteKey(int key, int t) {
        int idx = findKey(key);

        // 如果键在当前节点中
        if (idx < n && keys[idx] == key) {
            if (leaf) {
                // 如果是叶节点，则直接从节点中删除
                removeFromLeaf(idx);
            } else {
                // 如果是内部节点，则需要更复杂的处理
                removeFromNonLeaf(idx, t);
            }
        } else {
            // 如果当前节点是叶节点，则树中不存在该键
            if (leaf) {
                System.out.println("The key " + key + " does not exist in the tree.");
                return;
            }

            // 标记待删除的键是否在子树的最右边界
            boolean isAtLastChild = (idx == n);

            // 如果子节点有少于t个键，则需要先填充该子节点
            if (children[idx].n < t) {
                fill(idx, t);
            }

            // 如果最后一个键在前一个步骤中被合并，则需要在idx-1的子节点中递归删除
            if (isAtLastChild && idx > n) {
                children[idx - 1].deleteKey(key, t);
            } else {
                children[idx].deleteKey(key, t);
            }
        }
    }
    // 在当前节点中找到给定键的第一个大于或等于key的键的索引
    private int findKey(int key) {
        int idx = 0;
        while (idx < n && keys[idx] < key) {
            ++idx;
        }
        return idx;
    }
    // 从叶节点中删除键
    private void removeFromLeaf(int idx) {
        // 向左移动此节点中从idx+1开始的所有键，覆盖要删除的键
        for (int i = idx + 1; i < n; ++i) {
            keys[i - 1] = keys[i];
        }
        n--;
    }
    private void removeFromNonLeaf(int idx, int t) {
        int key = keys[idx];

        // 如果前驱节点至少有t个键，则在前驱节点中找到key的前驱（最大键），将其复制到当前节点，并在前驱节点中递归删除那个键。
        if (children[idx].n >= t) {
            BTreeNode pred = getPred(idx);
            keys[idx] = pred.keys[pred.n - 1];
            children[idx].deleteKey(pred.keys[pred.n - 1], t);
        }
        // 否则，如果后继节点至少有t个键，则找到key的后继（最小键），将其复制到当前节点，并在后继节点中递归删除那个键。
        else if (children[idx + 1].n >= t) {
            BTreeNode succ = getSucc(idx);
            keys[idx] = succ.keys[0];
            children[idx + 1].deleteKey(succ.keys[0], t);
        }
        // 否则，如果key的前驱和后继节点都只有t-1个键，则合并这两个节点和key，然后在合并后的节点中递归删除key。
        else {
            merge(idx, t);
            children[idx].deleteKey(key, t);
        }
    }

    private BTreeNode getPred(int idx) {
        // 一直向右走直到找到叶子
        BTreeNode cur = children[idx];
        while (!cur.leaf) {
            cur = cur.children[cur.n];
        }
        // 返回叶子节点的最后一个键
        return cur;
    }

    private BTreeNode getSucc(int idx) {
        // 一直向左走直到找到叶子
        BTreeNode cur = children[idx + 1];
        while (!cur.leaf) {
            cur = cur.children[0];
        }
        // 返回叶子节点的第一个键
        return cur;
    }

    private void merge(int idx, int t) {
        BTreeNode child = children[idx];
        BTreeNode sibling = children[idx + 1];

        // 把idx的key下移至最后一个键后面
        child.keys[t - 1] = keys[idx];

        // 把后一个兄弟的所有键复制到child
        for (int i = 0; i < sibling.n; ++i) {
            child.keys[i + t] = sibling.keys[i];
        }

        // 复制后一个兄弟的子指针到child
        if (!child.leaf) {
            for (int i = 0; i <= sibling.n; ++i) {
                child.children[i + t] = sibling.children[i];
            }
        }

        // 把keys和children向前移动填补空缺
        for (int i = idx + 1; i < n; ++i) {
            keys[i - 1] = keys[i];
        }
        for (int i = idx + 2; i <= n; ++i) {
            children[i - 1] = children[i];
        }

        child.n += sibling.n + 1;
        n--;
    }
    private void fill(int idx, int t) {
        // 如果前一个兄弟节点有多于t-1个键，则从该节点借一个键
        if (idx != 0 && children[idx - 1].n >= t) {
            borrowFromPrev(idx);
        }
        // 如果下一个兄弟节点有多于t-1个键，则从该节点借一个键
        else if (idx != n && children[idx + 1].n >= t) {
            borrowFromNext(idx);
        }
        // 否则，如果有下一个兄弟节点，则与之合并，否则与前一个兄弟节点合并
        else {
            if (idx != n) {
                merge(idx, t);
            } else {
                merge(idx - 1, t);
            }
        }
    }

    private void borrowFromPrev(int idx) {
        BTreeNode child = children[idx];
        BTreeNode sibling = children[idx - 1];

        // child的所有键和子节点向右移动一位
        for (int i = child.n - 1; i >= 0; --i) {
            child.keys[i + 1] = child.keys[i];
        }
        if (!child.leaf) {
            for (int i = child.n; i >= 0; --i) {
                child.children[i + 1] = child.children[i];
            }
        }

        // 将当前节点的键[idx-1]下移到child的第一个键位置
        child.keys[0] = keys[idx - 1];
        if (!child.leaf) {
            child.children[0] = sibling.children[sibling.n];
        }

        // 将兄弟节点的最后一个键上移到当前节点的键
        keys[idx - 1] = sibling.keys[sibling.n - 1];

        child.n += 1;
        sibling.n -= 1;
    }

    private void borrowFromNext(int idx) {
        BTreeNode child = children[idx];
        BTreeNode sibling = children[idx + 1];

        // child的最后一个键直接从当前节点的键[idx]获取
        child.keys[child.n] = keys[idx];
        if (!child.leaf) {
            child.children[child.n + 1] = sibling.children[0];
        }

        // 第一个兄弟的第一个键上移到当前节点的键
        keys[idx] = sibling.keys[0];

        // 兄弟节点的键和子节点向前移动一位
        for (int i = 1; i < sibling.n; ++i) {
            sibling.keys[i - 1] = sibling.keys[i];
        }
        if (!sibling.leaf) {
            for (int i = 1; i <= sibling.n; ++i) {
                sibling.children[i - 1] = sibling.children[i];
            }
        }

        child.n += 1;
        sibling.n -= 1;
    }

}
