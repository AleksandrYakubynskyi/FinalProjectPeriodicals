package com.project.resolver.impl;

import com.project.resolver.Resolver;
import com.project.util.QueryBuilder;
import com.project.util.SqlFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class TopicResolver implements Resolver {
    @Override
    public void resolve(String[] value, SqlFactory factory) {
        if (StringUtils.isNotEmpty(value[NumberUtils.INTEGER_ZERO])) {
            QueryBuilder.addWhereOrAnd("topic", factory);
            factory.equals(value[NumberUtils.INTEGER_ZERO]);
        }
    }
}
