package main.exp3;

import main.antlr4.SqlLexer;
import main.antlr4.SqlParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class SqlParserTest {
    public static void main(String[] args) throws Exception {
        String sql = "SELECT column1, column2 FROM table WHERE column1 = 'value1' AND column2 = 'value2'";
        ANTLRInputStream input = new ANTLRInputStream(sql);
        SqlLexer lexer = new SqlLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SqlParser parser = new SqlParser(tokens);
        ParseTree tree = parser.query(); // 'query' is the root rule of your grammar
        System.out.println(tree.toStringTree(parser)); // Print LISP-style tree
    }
}
