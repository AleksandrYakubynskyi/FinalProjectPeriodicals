package com.project.util;

import com.project.constant.AttributeNameConstant;
import com.project.resolver.Resolver;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Map;

public class QueryBuilder {
    public static String addQuery(Map<String, String[]> requestParameterMap) {
        SqlFactory factory = new SqlFactory();

        for (Map.Entry<String, Resolver> entry : Resolver.resolverMap.entrySet()) {
            if (ObjectUtils.isNotEmpty(requestParameterMap.get(entry.getKey()))) {
                entry.getValue().resolve(requestParameterMap.get(entry.getKey()), factory);
            }
        }
        return factory.build();
    }

    public static void addWhereOrAnd(String field, SqlFactory factory) {
        if (factory.getLength() < NumberUtils.INTEGER_ONE) {
            factory.where(field);
        } else {
            factory.and(field);
        }
    }

    public static String addLimitsAndSort(Map<String, String[]> requestParameterMap) {
        SqlFactory factory = new SqlFactory();

        if (ObjectUtils.isNotEmpty(requestParameterMap.get(AttributeNameConstant.SORT_ATTRIBUTE))) {
            factory.orderBy(requestParameterMap.get(AttributeNameConstant.SORT_ATTRIBUTE)[NumberUtils.INTEGER_ZERO]);
        }

        if (ObjectUtils.isNotEmpty(requestParameterMap.get(AttributeNameConstant.PUBLICATION_ON_PAGE_ATTRIBUTE)) &&
                Integer.parseInt(requestParameterMap.get(AttributeNameConstant.PUBLICATION_ON_PAGE_ATTRIBUTE)[NumberUtils.INTEGER_ZERO]) > NumberUtils.INTEGER_ZERO) {
            factory.limit(requestParameterMap.get(AttributeNameConstant.PUBLICATION_ON_PAGE_ATTRIBUTE)[NumberUtils.INTEGER_ZERO]);
        } else {
            factory.limit(AttributeNameConstant.DEFAULT_NUMBER_OF_PUBLICATIONS_PER_PAGE);
        }

        if (Integer.parseInt(getStartPosition(requestParameterMap)) > NumberUtils.INTEGER_ZERO) {
            factory.offset(getStartPosition(requestParameterMap));
        }
        return factory.build();
    }

    private static String getStartPosition(Map<String, String[]> requestParameterMap) {
        String numberOfPublicationsForPage = AttributeNameConstant.DEFAULT_NUMBER_OF_PUBLICATIONS_PER_PAGE;

        if (ObjectUtils.isNotEmpty(requestParameterMap.get(AttributeNameConstant.PUBLICATION_ON_PAGE_ATTRIBUTE))) {
            numberOfPublicationsForPage = requestParameterMap.get(AttributeNameConstant.PUBLICATION_ON_PAGE_ATTRIBUTE)[NumberUtils.INTEGER_ZERO];
        }

        if (Integer.parseInt(numberOfPublicationsForPage) < NumberUtils.INTEGER_ONE || StringUtils.isEmpty(numberOfPublicationsForPage)) {
            numberOfPublicationsForPage = AttributeNameConstant.DEFAULT_NUMBER_OF_PUBLICATIONS_PER_PAGE;
        }

        if (ObjectUtils.isNotEmpty(requestParameterMap.get(AttributeNameConstant.PAGE_ATTRIBUTE))) {
            return String.valueOf(Integer.parseInt(requestParameterMap.get(AttributeNameConstant.PAGE_ATTRIBUTE)[NumberUtils.INTEGER_ZERO]) *
                    Integer.parseInt(numberOfPublicationsForPage) - Integer.parseInt(numberOfPublicationsForPage));
        }
        return String.valueOf(NumberUtils.INTEGER_ZERO);
    }
}
