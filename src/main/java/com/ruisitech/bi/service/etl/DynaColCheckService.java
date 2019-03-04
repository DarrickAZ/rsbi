//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.etl;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLAggregateExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLCaseExpr;
import com.alibaba.druid.sql.ast.expr.SQLCharExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLIntegerExpr;
import com.alibaba.druid.sql.ast.expr.SQLMethodInvokeExpr;
import com.alibaba.druid.sql.ast.expr.SQLNumericLiteralExpr;
import com.alibaba.druid.sql.ast.expr.SQLQueryExpr;
import com.alibaba.druid.sql.ast.expr.SQLCaseExpr.Item;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.alibaba.druid.sql.parser.ParserException;
import com.alibaba.druid.sql.parser.SQLExprParser;
import com.alibaba.druid.sql.parser.Token;
import com.ruisitech.bi.entity.etl.EtlTableMetaCol;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.nlpcn.es4sql.domain.ResultScript;
import org.nlpcn.es4sql.parse.ElasticSqlExprParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DynaColCheckService {
    private final List<String> aggsFunctions = Arrays.asList("SUM", "MAX", "MIN", "AVG", "COUNT");
    private Boolean existAgg;
    @Autowired
    private EtlTableMetaColService colService;

    public DynaColCheckService() {
    }

    public String checkExpression(String expression, Integer tid, Boolean mustAgg) throws Exception {
        this.existAgg = false;
        expression = RSBIUtils.unescape(expression);
        String sql = "select " + expression + " as es from dual";
        SQLExprParser parser = new ElasticSqlExprParser(sql);
        SQLExpr expr = parser.expr();
        if (parser.getLexer().token() != Token.EOF) {
            throw new ParserException("表达式有语法错误 。");
        } else {
            SQLQueryExpr sqlExpr = (SQLQueryExpr)expr;
            MySqlSelectQueryBlock query = (MySqlSelectQueryBlock)sqlExpr.getSubQuery().getQuery();
            List<EtlTableMetaCol> cols = this.colService.queryTableColumnsNotExpress(tid);
            String ret = null;
            List<SQLSelectItem> selectList = query.getSelectList();

            SQLSelectItem sqlSelectItem;
            for(Iterator var12 = selectList.iterator(); var12.hasNext(); ret = this.parse(sqlSelectItem, cols, mustAgg)) {
                sqlSelectItem = (SQLSelectItem)var12.next();
            }

            return ret;
        }
    }

    private String parseBinary(SQLBinaryOpExpr expr, List<ResultScript> res, List<EtlTableMetaCol> cols, Boolean mustAgg) {
        String ret = "";
        SQLExpr left = expr.getLeft();
        SQLExpr right = expr.getRight();
        String r;
        ResultScript rs;
        if (right instanceof SQLBinaryOpExpr) {
            this.parseBinary((SQLBinaryOpExpr)right, res, cols, mustAgg);
        } else if (right instanceof SQLAggregateExpr) {
            r = this.parseAggregate((SQLAggregateExpr)right, cols, mustAgg);
            if (r != null) {
                ret = ret + r;
            }
        } else if (right instanceof SQLNumericLiteralExpr || right instanceof SQLIdentifierExpr || right instanceof SQLIntegerExpr) {
            rs = new ResultScript((String)null, right.toString());
            rs.oper = expr.getOperator().getName();
            if (right instanceof SQLIdentifierExpr) {
                rs.constant = false;
            } else {
                rs.constant = true;
            }
            res.add(rs);
        }

        if (left instanceof SQLBinaryOpExpr) {
            this.parseBinary((SQLBinaryOpExpr)left, res, cols, mustAgg);
        } else if (left instanceof SQLAggregateExpr) {
            r = this.parseAggregate((SQLAggregateExpr)left, cols, mustAgg);
            if (r != null) {
                ret = ret + r;
            }
        } else if (left instanceof SQLNumericLiteralExpr || left instanceof SQLIdentifierExpr || left instanceof SQLIntegerExpr) {
            rs = new ResultScript((String)null, left.toString());
            rs.oper = expr.getOperator().getName();
            if (left instanceof SQLIdentifierExpr) {
                rs.constant = false;
            } else {
                rs.constant = true;
            }

            res.add(rs);
        }

        return ret;
    }

    private String parse(SQLSelectItem item, List<EtlTableMetaCol> cols, Boolean mustAgg) {
        SQLExpr fieldExpr = item.getExpr();
        return this.parseField(fieldExpr, cols, mustAgg, false);
    }

    private String parseField(SQLExpr fieldExpr, List<EtlTableMetaCol> cols, Boolean mustAgg, boolean mustNumber) {
        if (fieldExpr instanceof SQLIdentifierExpr) {
            SQLIdentifierExpr expr = (SQLIdentifierExpr)fieldExpr;
            if (!this.filedExist(expr.getName(), cols)) {
                return "字段 [" + expr.getName() + "] 不存在。";
            }

            if (mustNumber && !this.filedIsNumber(expr.getName(), cols)) {
                return "在此表达式中，字段 [" + expr.getName() + "] 必须数字类型。";
            }
        } else if (fieldExpr instanceof SQLBinaryOpExpr) {
            SQLBinaryOpExpr expr = (SQLBinaryOpExpr)fieldExpr;
            List<ResultScript> res = new ArrayList();
            String ret = this.parseBinary(expr, res, cols, mustAgg);
            if (ret != null && ret.trim().length() > 0) {
                return ret;
            }

            Iterator var8 = res.iterator();

            while(var8.hasNext()) {
                ResultScript r = (ResultScript)var8.next();
                if (!r.constant) {
                    String c = r.val.toString();
                    if (!this.filedExist(c, cols)) {
                        return "字段 [" + c + "] 不存在。";
                    }

                    if (mustNumber && !this.filedIsNumber(c, cols)) {
                        return "在此表达式中，字段 [" + c + "] 必须数字类型。";
                    }
                }
            }
        } else {
            if (fieldExpr instanceof SQLCaseExpr) {
                SQLCaseExpr expr = (SQLCaseExpr)fieldExpr;
                return this.parseCaseWhen(expr, cols, mustAgg);
            }

            if (fieldExpr instanceof SQLMethodInvokeExpr) {
                SQLMethodInvokeExpr expr = (SQLMethodInvokeExpr)fieldExpr;
                return this.parseMethod(expr, cols);
            }

            if (fieldExpr instanceof SQLAggregateExpr) {
                SQLAggregateExpr expr = (SQLAggregateExpr)fieldExpr;
                return this.parseAggregate(expr, cols, mustAgg);
            }

            if (fieldExpr instanceof SQLCharExpr) {
                return null;
            }
        }

        return mustAgg && !this.existAgg ? "表达式必须用sum/avg/count/max/min等聚合函数包装。" : null;
    }

    private String parseAggregate(SQLAggregateExpr expr, List<EtlTableMetaCol> cols, Boolean mustAgg) {
        this.existAgg = true;
        String aggName = expr.getMethodName();
        if (!this.aggsFunctions.contains(aggName)) {
            return "聚合函数 [" + aggName + "] 不存在。";
        } else if (!mustAgg) {
            return "表达式不能包含sum/avg/count/max/min等聚合函数。";
        } else {
            List<SQLExpr> args = expr.getArguments();
            if (args != null && args.size() != 0) {
                Iterator var6 = args.iterator();

                while(var6.hasNext()) {
                    SQLExpr arg = (SQLExpr)var6.next();
                    if (!aggName.equals("COUNT")) {
                        String ret = this.parseField(arg, cols, mustAgg, true);
                        if (ret != null) {
                            return ret;
                        }
                    }
                }

                return null;
            } else {
                return "聚合函数 [" + aggName + "] 不能为空";
            }
        }
    }

    private String parseMethod(SQLMethodInvokeExpr method, List<EtlTableMetaCol> cols) {
        List<SQLExpr> pms = method.getParameters();
        Iterator var4 = pms.iterator();

        while(var4.hasNext()) {
            SQLExpr pm = (SQLExpr)var4.next();
            if (pm instanceof SQLIdentifierExpr) {
                SQLIdentifierExpr tpm = (SQLIdentifierExpr)pm;
                String name = tpm.getName();
                if (!this.filedExist(name, cols)) {
                    return "字段 [" + name + "] 不存在。";
                }
            }
        }

        return null;
    }

    private String parseCaseWhen(SQLCaseExpr caseExpr, List<EtlTableMetaCol> cols, Boolean mustAgg) {
        Iterator var4 = caseExpr.getItems().iterator();

        while(true) {
            SQLExpr conditionExpr;
            do {
                if (!var4.hasNext()) {
                    return null;
                }

                Item item = (Item)var4.next();
                conditionExpr = item.getConditionExpr();
            } while(!(conditionExpr instanceof SQLBinaryOpExpr));

            SQLBinaryOpExpr expr = (SQLBinaryOpExpr)conditionExpr;
            List<ResultScript> res = new ArrayList();
            this.parseBinary(expr, res, cols, mustAgg);
            Iterator var9 = res.iterator();

            while(var9.hasNext()) {
                ResultScript r = (ResultScript)var9.next();
                if (!r.constant) {
                    String c = r.val.toString();
                    if (!this.filedExist(c, cols)) {
                        return "字段 [" + c + "] 不存在。";
                    }
                }
            }
        }
    }

    private boolean filedExist(String field, List<EtlTableMetaCol> cols) {
        boolean ret = false;
        Iterator var4 = cols.iterator();

        while(var4.hasNext()) {
            EtlTableMetaCol col = (EtlTableMetaCol)var4.next();
            if (col.getColName().equals(field)) {
                ret = true;
                break;
            }
        }

        return ret;
    }

    private boolean filedIsNumber(String field, List<EtlTableMetaCol> cols) {
        boolean ret = false;
        Iterator var4 = cols.iterator();

        while(var4.hasNext()) {
            EtlTableMetaCol col = (EtlTableMetaCol)var4.next();
            if (col.getColName().equals(field) && (col.getColType().equals("Int") || col.getColType().equals("Double"))) {
                ret = true;
                break;
            }
        }

        return ret;
    }
}
