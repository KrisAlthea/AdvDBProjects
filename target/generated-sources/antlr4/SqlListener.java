// Generated from Sql.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SqlParser}.
 */
public interface SqlListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SqlParser#query}.
	 * @param ctx the parse tree
	 */
	void enterQuery(SqlParser.QueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link SqlParser#query}.
	 * @param ctx the parse tree
	 */
	void exitQuery(SqlParser.QueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link SqlParser#select_clause}.
	 * @param ctx the parse tree
	 */
	void enterSelect_clause(SqlParser.Select_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link SqlParser#select_clause}.
	 * @param ctx the parse tree
	 */
	void exitSelect_clause(SqlParser.Select_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link SqlParser#from_clause}.
	 * @param ctx the parse tree
	 */
	void enterFrom_clause(SqlParser.From_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link SqlParser#from_clause}.
	 * @param ctx the parse tree
	 */
	void exitFrom_clause(SqlParser.From_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link SqlParser#where_clause}.
	 * @param ctx the parse tree
	 */
	void enterWhere_clause(SqlParser.Where_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link SqlParser#where_clause}.
	 * @param ctx the parse tree
	 */
	void exitWhere_clause(SqlParser.Where_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link SqlParser#column_list}.
	 * @param ctx the parse tree
	 */
	void enterColumn_list(SqlParser.Column_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link SqlParser#column_list}.
	 * @param ctx the parse tree
	 */
	void exitColumn_list(SqlParser.Column_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link SqlParser#table_name}.
	 * @param ctx the parse tree
	 */
	void enterTable_name(SqlParser.Table_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link SqlParser#table_name}.
	 * @param ctx the parse tree
	 */
	void exitTable_name(SqlParser.Table_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link SqlParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(SqlParser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SqlParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(SqlParser.ConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SqlParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(SqlParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SqlParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(SqlParser.ExpressionContext ctx);
}