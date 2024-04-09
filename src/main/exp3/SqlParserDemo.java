package main.exp3;

import main.antlr4.SqlLexer;
import main.antlr4.SqlParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class SqlParserDemo {
    public static void main(String[] args) throws Exception {
        // 替换ANTLRInputStream为CharStreams，适配最新的ANTLR版本
        String sql = "SELECT column1, column2 FROM table WHERE column1 = 'value1'";
        CharStream input = CharStreams.fromString(sql);
        SqlLexer lexer = new SqlLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SqlParser parser = new SqlParser(tokens);
        ParseTree tree = parser.query(); // 假设"query"是你的文法的顶级规则
        System.out.println(tree.toStringTree(parser)); // 打印解析树
    }
}
