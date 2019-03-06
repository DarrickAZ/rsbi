//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine;

public final class ExtConfigConstants {
    public static final String classNotFind = "配置的service: $0 不存在.";
    public static final String interNotFind = "配置的拦截器: $0 不存在.";
    public static final String mvNotFind = "id 为 $0 的mv不存在";
    public static final String mvIsExist = "id 为 $0 的mv已经存在.";
    public static final String xmlResuorceNotFind = "ext-config中未配置 $0";
    public static final String serviceNotFind = "id 为 $0 的service不存在.";
    public static final String serviceExist = "配置id 为 $0 的service已经存在.";
    public static final String sqlNotExist = "ref 为 $0 的sql不存在, mvId = $1 .";
    public static final String dsNotFind = "未在request对象中找到id为 $0 的数据集.";
    public static final String methodNotFind = "类 $0 指定的方法 $1 不存在.";
    public static final String inputIdExist = "id为 $0 的输入参数框存在重复.";
    public static final String typeNotExist = "type 为 $0 的类型不支持，现只支持int,double,string";
    public static final String paramNotFind = "id为 $0 的参数未绑定到MV中, mvid = $1";
    public static final String mvInServiceNotFind = "类 $0 中 method为 $1 的方法不存在, serviceid = $2";
    public static final String sqlKeyExist = "sqlMapping 配置中，id为 $0 的key存在重复.";
    public static final String fromMVNotFind = "请求方法为POST,但 $0 却为null";
    public static final String interIdExist = "id为 $0 的拦截器已经存在.";
    public static final String interIdNotExist = "id为 $0 的拦截器不存在.";
    public static final String inputIdNotExist = " $0 输入框未配置id.";
    public static final String inputPackageNotFind = "配置的包 $0 未找到.";
    public static final String cascadeNotExist = "配置的级联参数 $0 在文件 $1 (xml)中不存在.";
    public static final String labelExist = "配置的lebel $0 在文件 $1 (xml)中已经存在.";
    public static final String labelNotExist = "配置的target $0 在文件 $1 (xml)中未指向正确的组件.";
    public static final String cubeDataRefNotExist = "立方体中配置的 dataRef= $0 未指向正确的sql集. ";
    public static final String chartRefNotExist = "图形中配置的 dataRef= $0 未指向正确的sql集. ";
    public static final String crossColNotExist = "交叉报表中配置的指标 $0 在sql中未查询出数据.";
    public static final String jsFuncNotExist = "定义的 jsFunc 方法 $0 未找到.";
    public static final String confCallBackNotExist = "定义的 confCallBack 方法  $0 未找到.";
    public static final String testFuncNotExist = "定义的 testFunc 方法 $0 未找到.";
    public static final String fieldLoaderNotExist = " crossField 中获取维度信息的类 $0 加载失败. ";
    public static final String feildCodeNotExist = "sql 中查询出字段名为 $0 的值存在 null.";
    public static final String fieldNotExist = "需要加载类型为 $0 的纬度数据不存在.";
    public static final String chartSerNotExist = "图形中配置的ser $0  在sql中不存在.";
    public static final String chartXNotExist = "图形中配置的x $0 在sql中 不存在.";
    public static final String chartJSError = "图形中js函数 $0 调用出错, 参数 $1 .";
    public static final String aliasNotExist = "crossReport 中配置的  alias ($0) 无法从sql中获取数据.";
    public static final String aliasDescNotExist = "crossReport 中配置的  aliasDesc ($0) 无法从sql中获取数据.";
    public static final String aliasFmtNotExist = "crossReport 中配置的  aliasFmt ($0) 无法从sql中获取数据.";
    public static final String aliasAggNotExist = "crossReport 中配置的  aliasAggregation ($0) 无法从sql中获取数据.";
    public static final String kpiValueNotExist = "crossReport 中的数据集缺少 $0 字段.";
    public static final String refDataCenterNotExist = " crossReport 中引用的dataCenter ($0) 不存在. mv = $1";
    public static final String datacenterNotkpi = " dataCenter 中未配置指标. ";
    public static final String dcNotFindKpi = " 引用的dataCenter中未配置指标. id= $0 ";
    public static final String aggregationErr = "指标数据需要聚合，但未设置聚合方式. aggregation = null, kpi/kpiOther = $0";
    public static final String filterDimErr = "kpiFilter/filter 配置出错,引用的指标不存在，kpi=$0";
    public static final String builderInterceptorErr = "builder 拦截器类 $0 不存在.";
    public static final String dataSourceIdExist = "配置的dataSource id = $0 存在重复.";
    public static final String dataSourceIdNotExist = "配置的dataSource id = $0 不存在.";
    public static final String constantExist = "constant 配置存在重复， name = $0 . ";
    public static final String chartExist = "chart 配置存在重复， name = $0 . ";
    public static final String chartLinkNotExist = "图形链接的组件 $0 不存在。";

    public ExtConfigConstants() {
    }
}
