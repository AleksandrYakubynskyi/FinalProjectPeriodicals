package com.project.util;

import com.project.constant.MysqlKeys;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class SqlFactory {
    private final StringBuilder builder = new StringBuilder();

    public static SqlFactory getFactory() {
        return new SqlFactory();
    }

    public SqlFactory selectFrom(String from) {
        builder.append(MysqlKeys.SELECT_FROM).append(from);
        return this;
    }

    public SqlFactory selectFields(String fields) {
        builder.append(MysqlKeys.SELECT).append(fields);
        return this;
    }

    public SqlFactory from(String from) {
        builder.append(MysqlKeys.FROM).append(from);
        return this;
    }

    public SqlFactory like(String like) {
        builder.append(MysqlKeys.LIKE).append(like).append(MysqlKeys.PERCENT);
        return this;
    }

    public SqlFactory join(String table) {
        builder.append(MysqlKeys.JOIN).append(table);
        return this;
    }

    public SqlFactory on(String on) {
        builder.append(MysqlKeys.ON).append(on);
        return this;
    }

    public SqlFactory selectCountFrom(String from) {
        builder.append(MysqlKeys.SELECT_COUNT).append(from);
        return this;
    }

    public SqlFactory where(String parameter) {
        builder.append(MysqlKeys.WHERE).append(parameter);
        return this;
    }

    public SqlFactory equals(String value) {
        builder.append(MysqlKeys.EQUALS).append(MysqlKeys.QUOTE).append(value).append(MysqlKeys.QUOTE);
        return this;
    }

    public SqlFactory and(String value) {
        builder.append(MysqlKeys.AND).append(value);
        return this;
    }

    public SqlFactory orderBy(String value) {
        builder.append(MysqlKeys.ORDER_BY).append(value);
        return this;
    }

    public SqlFactory limit(String value) {
        builder.append(MysqlKeys.LIMIT).append(value);
        return this;
    }

    public SqlFactory offset(String value) {
        builder.append(MysqlKeys.OFFSET).append(value);
        return this;
    }

    public SqlFactory insertInto(String into) {
        builder.append(MysqlKeys.INSERT).append(into);
        return this;
    }

    public SqlFactory parameters(String... parameters) {
        builder.append(MysqlKeys.OPEN);
        for (String parameter : parameters) {
            builder.append(parameter).append(MysqlKeys.COMMA);
        }
        deleteLastChar();
        builder.append(MysqlKeys.CLOSE);

        builder.append(MysqlKeys.VALUES);
        for (String ignored : parameters) {
            builder.append(MysqlKeys.QUESTION).append(MysqlKeys.COMMA);
        }
        deleteLastChar();
        builder.append(MysqlKeys.CLOSE);
        return this;
    }

    public SqlFactory deleteFrom(String value) {
        if (StringUtils.isEmpty(value)) {
            return this;
        }
        builder.append(MysqlKeys.DELETE).append(value);
        return this;
    }

    public SqlFactory update(String value) {
        if (StringUtils.isEmpty(value)) {
            return this;
        }
        builder.append(MysqlKeys.UPDATE).append(value);
        return this;
    }

    public SqlFactory set(String... parameters) {
        builder.append(MysqlKeys.SET);
        for (String parameter : parameters) {
            builder.append(parameter).append(MysqlKeys.EQUALS)
                    .append(MysqlKeys.QUESTION).append(MysqlKeys.COMMA);
        }
        deleteLastChar();
        return this;
    }

    public int getLength() {
        return builder.length();
    }

    public String build() {
        return builder.toString();
    }

    private void deleteLastChar() {
        builder.deleteCharAt(builder.length() - NumberUtils.INTEGER_TWO);
    }
}
