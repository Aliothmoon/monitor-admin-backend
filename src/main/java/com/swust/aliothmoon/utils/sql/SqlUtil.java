package com.swust.aliothmoon.utils.sql;

import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.util.StrUtil;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.regex.Pattern;

/**
 * sql操作工具类
 *
 * @author Aliothmoon
 */
@UtilityClass
public class SqlUtil {

    /**
     * 定义常用的 sql关键字
     */
    public static final String SQL_REGEX = "select |insert |delete |update |drop |count |exec |chr |mid |master |truncate |char |and |declare ";

    /**
     * 仅支持字母、数字、下划线、空格、逗号、小数点（支持多个字段排序）
     */
    public static final Pattern SQL_PATTERN = Pattern.compile("[a-zA-Z0-9_ ,.]+");

    /**
     * 限制orderBy最大长度
     */
    private static final int ORDER_BY_MAX_LENGTH = 500;

    /**
     * 检查字符，防止注入绕过
     */
    public static String escapeOrderBySql(String value) {
        if (StrUtil.isNotEmpty(value) && !isValidOrderBySql(value)) {
            throw new UtilException("参数不符合规范，不能进行查询");
        }
        if (StrUtil.length(value) > ORDER_BY_MAX_LENGTH) {
            throw new UtilException("参数已超过最大限制，不能进行查询");
        }
        return value;
    }

    /**
     * 验证 order by 语法是否符合规范
     */
    public static boolean isValidOrderBySql(String value) {
        return SQL_PATTERN.matcher(value).matches();
    }

    /**
     * SQL关键字检查
     */
    public static void filterKeyword(String value) {
        if (StrUtil.isEmpty(value)) {
            return;
        }
        List<String> sqlKeywords = StrUtil.split(SQL_REGEX, "\\|");
        for (String sqlKeyword : sqlKeywords) {
            if (StrUtil.indexOfIgnoreCase(value, sqlKeyword) > -1) {
                throw new UtilException("参数存在SQL注入风险");
            }
        }
    }
}
