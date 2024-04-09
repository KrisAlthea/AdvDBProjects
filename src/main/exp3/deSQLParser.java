package main.exp3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

// 基础的树节点类
class TreeNode {
    String value;
    List<TreeNode> children;

    public TreeNode(String value) {
        this.value = value;
        this.children = new ArrayList<>();
    }

    public void addChild(TreeNode child) {
        children.add(child);
    }
}

// SELECT节点类
class SelectNode extends TreeNode {
    public SelectNode() {
        super("SELECT");
    }
}

// FROM节点类
class FromNode extends TreeNode {
    public FromNode(String tableName) {
        super("FROM");
        this.addChild(new TreeNode(tableName));
    }
}

// WHERE节点类
class WhereNode extends TreeNode {
    public WhereNode() {
        super("WHERE");
    }
    public WhereNode(String condition) {
        super("WHERE");
        this.addChild(new TreeNode(condition));
    }
}

// GROUP BY节点类
class GroupByNode extends TreeNode {
    public GroupByNode() {
        super("GROUP BY");
    }
}

// ORDER BY节点类
class OrderByNode extends TreeNode {
    public OrderByNode() {
        super("ORDER BY");
    }
}

class LogicalOperatorNode extends TreeNode {
    public LogicalOperatorNode(String operator) {
        super(operator);
    }
}

class ExpressionNode extends TreeNode {
    public ExpressionNode(String expression) {
        super("EXPR");
        // 假设表达式以简单字符串形式存储，实际应用中可能需要更复杂的结构
        this.addChild(new TreeNode(expression));
    }
}

class LogicalOpNode extends TreeNode {
    public LogicalOpNode(String operator) {
        super(operator);
    }
}

// SQL解析器类
public class deSQLParser {
    public TreeNode parse(String sql) {
        // 预处理SQL语句
        sql = sql.toUpperCase(); // 转换为全大写
        sql = sql.replaceAll("\\s+GROUP BY\\s+", " GROUP BY ");
        sql = sql.replaceAll("\\s+ORDER BY\\s+", " ORDER BY ");

        TreeNode root = new TreeNode("QUERY");
        // 用正则表达式查找关键字的位置，并根据关键字分割SQL语句
        List<String> clauseKeywords = Arrays.asList("SELECT", "FROM", "WHERE", "GROUP BY", "ORDER BY");
        String regex = String.join("|", clauseKeywords.stream().map(kw -> "(?=" + kw + ")").collect(Collectors.toList()));
        String[] clauses = sql.split(regex);

        for (String clause : clauses) {
            if (clause.trim().startsWith("SELECT")) {
                processSelectClause(clause.trim(), root);
            } else if (clause.trim().startsWith("FROM")) {
                processFromClause(clause.trim(), root);
            } else if (clause.trim().startsWith("WHERE")) {
                processWhereClause(clause.trim(), root);
            } else if (clause.trim().startsWith("GROUP BY")) {
                processGroupByClause(clause.trim(), root);
            } else if (clause.trim().startsWith("ORDER BY")) {
                processOrderByClause(clause.trim(), root);
            }
        }

        return root;
    }

    private void processSelectClause(String clause, TreeNode root) {
        SelectNode selectNode = new SelectNode();
        String columns = clause.substring("SELECT".length()).trim();
        Arrays.stream(columns.split(",")).forEach(column -> selectNode.addChild(new TreeNode(column.trim())));
        root.addChild(selectNode);
    }

    private void processFromClause(String clause, TreeNode root) {
        String tableName = clause.substring("FROM".length()).trim();
        FromNode fromNode = new FromNode(tableName);
        root.addChild(fromNode);
    }

    private void processWhereClause(String clause, TreeNode root) {
        WhereNode whereNode = new WhereNode();
        // 这里简化处理，实际情况下你需要一个更复杂的解析策略来正确处理逻辑操作符和括号
        String[] tokens = clause.substring("WHERE".length()).trim().split("\\s+");

        Stack<TreeNode> stack = new Stack<>();
        for (String token : tokens) {
            if (token.equalsIgnoreCase("AND") || token.equalsIgnoreCase("OR")) {
                TreeNode right = stack.pop();
                TreeNode left = stack.pop();
                LogicalOpNode opNode = new LogicalOpNode(token);
                opNode.addChild(left);
                opNode.addChild(right);
                stack.push(opNode);
            } else {
                // 假设非操作符的token是表达式的一部分
                stack.push(new ExpressionNode(token));
            }
        }

        // 最终的栈顶元素应该是完整的表达式树
        if (!stack.isEmpty()) {
            whereNode.addChild(stack.pop());
        }

        root.addChild(whereNode);
    }

    private void processGroupByClause(String clause, TreeNode root) {
        GroupByNode groupByNode = new GroupByNode();
        String columns = clause.substring("GROUP BY".length()).trim();
        Arrays.stream(columns.split(",")).forEach(column -> groupByNode.addChild(new TreeNode(column.trim())));
        root.addChild(groupByNode);
    }

    private void processOrderByClause(String clause, TreeNode root) {
        OrderByNode orderByNode = new OrderByNode();
        String columns = clause.substring("ORDER BY".length()).trim();
        Arrays.stream(columns.split(",")).forEach(column -> orderByNode.addChild(new TreeNode(column.trim())));
        root.addChild(orderByNode);
    }
}
