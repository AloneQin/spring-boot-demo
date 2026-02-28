package com.example.demo.utils;

import com.github.vertical_blank.sqlformatter.SqlFormatter;
import com.github.vertical_blank.sqlformatter.core.FormatConfig;
import com.github.vertical_blank.sqlformatter.languages.Dialect;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.util.TablesNamesFinder;

import java.util.*;
import java.util.regex.Pattern;

/**
 * SQL 工具类
 */
public class SqlUtils {

    /**
     * 格式化SQL
     * @param sql 原 SQL
     * @return 格式化后的 SQL
     */
    public static String format(String sql) {
        FormatConfig config = FormatConfig.builder()
                // 关键字大写
                .uppercase(true)
                // 制表符作为缩进样式
                .indent("\t")
                .build();
        String formatSql = SqlFormatter.of(Dialect.StandardSql).format(sql, config);
        // fix: 去除反引号标识符中的多余空格问题
        return formatSql.replaceAll("`\\s+([^`]+)\\s+`", "`$1`");

    }

    /**
     * 压缩SQL
     * @param sql 原 SQL
     * @return 压缩后的 SQL
     */
    public static String compress(String sql) {
        return sql.replaceAll("\\s+", " ").trim();
    }

    /**
     * 从SQL语句中提取表名
     * @param sql SQL语句
     * @return 表名列表
     */
    public static List<String> outputTableNames(String sql) {
        try {
            Statement statement = CCJSqlParserUtil.parse(sql);
            TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
//            Set<String> tableNames = tablesNamesFinder.getTables(statement);
//            return new ArrayList<>(tableNames);
            return tablesNamesFinder.getTableList(statement);
        } catch (Exception e) {
            throw new RuntimeException("解析SQL语句失败: " + sql, e);
        }
    }

    /**
     * 格式化SQL并设置默认库名
     * @param sql SQL语句
     * @param defaultDbName 默认库名
     * @return 格式化后的SQL
     */
    public static String formatAndSetDefaultDbName(String sql, String defaultDbName) {
        // 格式化
        sql = format(sql);
        // 提取表名
        List<String> tableNames = outputTableNames(sql);
        // 为表名添加库名
        LinkedHashMap<String, String> tableNamesMap = tableNames.stream()
                .filter(tableName -> !tableName.contains("."))
                .collect(LinkedHashMap::new,
                        (map, tableName) -> map.put(tableName, defaultDbName + "." + tableName),
                        LinkedHashMap::putAll
                );
        // 替换表名
        String resultSql = sql;
        for (Map.Entry<String, String> entry : tableNamesMap.entrySet()) {
            // \\b 表示单词边界，quote() 进行正则转义，避免正则匹配失败
            String regex = "\\b" + Pattern.quote(entry.getKey()) + "\\b";
            resultSql = resultSql.replaceAll(regex, entry.getValue());
        }
        return resultSql;
    }

    public static void main(String[] args) throws Exception {
        String sql = "/** sqlId=RealTimeWarningTrunkMapper.getTrunk **/ with trunk_tmp as(select t.project_id,t.project_code,t.project_type,t.over_untaken_ticket,t.over_untaken_ticket_un,t.empty_single_ticket,t.empty_single_ticket_un,t.wrong_carded_ticket,t.wrong_carded_ticket_un,t.emerg_undistribute_ticket,t.emerg_undistribute_ticket_un,t.over_undistribute_ticket,t.over_undistribute_ticket_un,t.check_unsign_ticket,t.check_unsign_ticket_un,t.staying_depot_ticket,t.staying_depot_ticket_un,pc.charge_emp_number,pc.charge_emp_id,pc.charge_name,if(pc.project_type!=1,pc.simple_name,null)as simple_name,if(pc.project_type=1,pc.simple_name,null)as project_simple_name,pc.id as rule_id from ads_coo.ads_coo_project_control_realtime_warn_mi t inner join ods_coo.project_control_config pc on t.project_id=pc.project_id and pc.enabled_flag=1 where 1=1),charge_department_tmp as(select distinct child_department_id,t.child_department_name,t.start_time,t.end_time,p.id as emp_id,p.employee_number as emp_number,p.duty_id,p.duty from dim_coo_department_bridge_his_cm t inner join ods_coo.employee p on t.child_department_id=p.department_id and p.enabled_flag=1 where t.parent_department_id in(-11)and t.dmonth='2025-11-01'),result_data as(select * from trunk_tmp t inner join charge_department_tmp cdt on cdt.emp_number=t.charge_emp_number)select /*+ SET_VAR(query_timeout=10)*/ * from result_data order by project_id desc limit 200";
//        sql = format(sql);
//        System.out.println(sql);
//        System.out.println(compress(sql));
//
//        List<String> tableNames = outputTableNames(sql);
//        tableNames.forEach(System.out::println);

        String s = "/** sqlId=NeedStraightenedMapper.needStraightenedSummary **/WITH depart_id_tmp AS (SELECT DISTINCT t.child_department_id AS department_id FROM dim_coo.dim_coo_department_bridge_his_cm t WHERE child_enabled_flag = 1 AND t.dmonth = '2025-11-01' AND t.parent_department_id IN (-11) AND t.level_diff = 0), dim_department_child AS (SELECT t.child_department_id AS department_id, t.parent_department_id AS g_department_id, t.start_time, t.end_time, t.child_organization_type, t.dmonth FROM dim_coo.dim_coo_department_bridge_his_cm t INNER JOIN depart_id_tmp p ON p.department_id = t.parent_department_id WHERE child_enabled_flag = 1 AND t.dmonth = '2025-11-01'), dim_department_child_data AS (SELECT t.department_id AS depart_id, t.department_name AS depart_name, t.hierarchy_code AS depart_level, t.charge_name AS charge_name, t.department_route, t.department_id_route, t.last_tier FROM dim_coo.dim_coo_department t INNER JOIN dim_department_child t1 ON t.department_id = t1.department_id WHERE t.end_time = '253402185600' AND t.enabled_flag = 1), coo_remark_follow_real_time_warning_tmp AS (SELECT * FROM (SELECT r.*, ROW_NUMBER() OVER (PARTITION BY index_code, code ORDER BY creation_time DESC) AS rn FROM (SELECT department_id, `code`, ifnull(follow_name, '未跟进') AS follow_status, follow_name, remark, created_by, creation_time, CASE WHEN problem_flag = 0 AND expired_time IS NOT NULL AND expired_time < 1764233700 THEN 1 ELSE problem_flag END AS problem_flag, index_code FROM coo_remark_follow WHERE creation_time >= 1764233400 AND creation_time <= 1764259200 AND index_type = 600 AND enabled_flag = 1 AND index_code IN (6010) UNION ALL SELECT department_id, `code`, ifnull(follow_name, '未跟进') AS follow_status, follow_name, remark, created_by, creation_time, CASE WHEN problem_flag = 0 AND expired_time IS NOT NULL AND expired_time < 1764233700 THEN 1 ELSE problem_flag END AS problem_flag, index_code FROM ods_coo.coo_remark_follow_doris WHERE dt >= '2025-11-20 00:00:00' AND dt < '2025-11-27 16:50:00' AND index_type = 600 AND enabled_flag = 1 AND index_code IN (6010)) r) a WHERE rn = 1), need_straightened_summary_tmp AS (SELECT DISTINCT t.project_code AS project_code, t.project_id AS project_id, t.picking_depot_id AS picking_depot_id, t.picking_depot_name AS picking_depot_name, t.allocation_original_id AS allocation_original_id, t.allocation_original AS allocation_original, t.allocation_target_id AS allocation_target_id, t.allocation_target AS allocation_target, t.line_type AS line_type, CEIL(t.distance) AS distance, CASE WHEN ROUND(t.total_weight / 1000.0, 1) = 0.0 THEN 0.1 ELSE ROUND(t.total_weight / 1000.0, 1) END AS total_weight, CASE WHEN ROUND(t.customer_weight / 1000.0, 1) = 0.0 THEN 0.1 ELSE ROUND(t.customer_weight / 1000.0, 1) END AS customer_weight, CASE WHEN ROUND(t.on_depot_weight / 1000.0, 1) = 0.0 THEN 0.1 ELSE ROUND(t.on_depot_weight / 1000.0, 1) END AS on_depot_weight, t.stat_warn_time AS stat_warn_time, IFNULL(rft.follow_status, '未跟进') AS follow_status, rft.remark, rft.created_by AS reviewer, rft.creation_time AS follow_time, concat(IFNULL(t.picking_depot_id, ''), IFNULL(t.allocation_original_id, ''), IFNULL(t.allocation_target_id, ''), t.line_type) AS join_code FROM dws_coo.dws_coo_project_control_realtime_need_straightened_mi t JOIN dim_department_child_data dcd ON t.picking_depot_id = dcd.depart_id LEFT JOIN coo_remark_follow_real_time_warning_tmp rft ON rft.code = concat(IFNULL(t.picking_depot_id, ''), IFNULL(t.allocation_original_id, ''), IFNULL(t.allocation_target_id, ''), t.line_type) WHERE t.project_id = 7303980156127946769 AND t.project_code = 'XM-202312-0104'), result_data AS (SELECT * FROM need_straightened_summary_tmp) SELECT /*+ SET_VAR(query_timeout=5) */ * FROM result_data order by stat_warn_time DESC LIMIT 200 ";
        sql = formatAndSetDefaultDbName(s, "prd_coofd");
        System.out.println(sql);
    }
}
