//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.cube;

public final class DCConstants {
    public static final String SCRIPTERR = "查询执行出错.";
    public static final String SYSFUNCNOTFIND = "读取资源文件出错.";
    public static final String CURVALUE = "CV";
    public static final String CURVALUEAMO = "CVA";
    public static final String KPICOMPUTEERR = "_kpiCompute 函数调用出错.";
    public static final String EXTKPICOMPUTEERR = "_extKpiCompute 函数调用出错.";
    public static final String EXTKPICOMPUTEPARAMERR = "_extKpiCompute 函数传入参数不对应.";
    public static final String TYPEERR = "字段不存在. ($0)";
    public static final String WHEREERR = "_filter 表达式错误. ($0)";
    public static final String COLUMNTYPEERR = "_filterDim 过滤字段错误，($0)";
    public static final String COLUMNTYPEERRBYKPI = "_filterKpi 过滤字段错误，($0)";
    public static final String AGGREGATIONTYPEERR = "_aggregation 聚合类型错误, ($0)";
    public static final String AGGREGATIONKPIERR = "_aggregation 聚合指标/衍生指标未找到, ($0)";
    public static final String AGGREGATIONTYPENOTFOND = "_aggregation 未设置聚合类型.";
    public static final String AGGREGATIONDIMERR = "_aggregation 聚合维度不存在, ($0)";
    public static final String SORTTYPEERR = "_sort 函数类型错误.($0)";
    public static final String DIMISNOTSAME = " _join 函数出错, 数据集维度不同, 不能JOIN.";
    public static final String JOINDSIDERR = " _join 函数出错， 引用的数据集不存在. (id=$0)";
    public static final String JOINKPIERR = " _join 函数出错，两数据集指标不一致，不能链接.";
    public static final String GETDATASETBYID = " _getDataSetById 出错，引用的数据集不存在。(id=$0)";
    public static final String KPICOMPUTEPARAMERR = " _kpiCompute 函数传入参数不对应.";
    public static final String SYSEXTKPIFUNCERR = "衍生指标计算出错. $0";
    public static final String horizontalKpiERR = "需要计算的 horizontalKpi不存在， horizontalKpi=$0";
    public static final String JOINNODATE = "需要进行 _ref 操作，但数据集中无账期维度. ";
    public static final String TOPTYPEERR = "_top函数输入类型错误，需要一个KPI, 但传入却不是, input=$0";
    public static final String GRIDAGGREGATIONERR = "aggregation函数出错，column不存在. column=$0";
    public static final String GRIDAGGREGATIONTYPEERR = "aggregation函数出错，column不是数字. column=$0";
    public static final String GRIDDATEFILLCOLERR = "dateFill 函数出错， column $0  不存在 或不是账期类型.";
    public static final String GRIDDATEFILLTYPEERR = "dateFill 函数出错，type $0 只能为 month/day ";

    public DCConstants() {
    }
}
