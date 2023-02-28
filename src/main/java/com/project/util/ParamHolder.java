package com.project.util;

import com.project.constant.AttributeNameConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.http.HttpServletRequest;

public class ParamHolder {
    private String numberOfPublicationsForPage;

    public void setUpParametersFromRequest(HttpServletRequest request) {

        String topic = request.getParameter("topic");
        String sort = request.getParameter(AttributeNameConstant.SORT_ATTRIBUTE);

        String page = StringUtils.isEmpty(request.getParameter(AttributeNameConstant.PAGE_ATTRIBUTE)) ?
                String.valueOf(NumberUtils.INTEGER_ONE) : request.getParameter(AttributeNameConstant.PAGE_ATTRIBUTE);

        numberOfPublicationsForPage = StringUtils.isEmpty(request.getParameter(AttributeNameConstant.PUBLICATION_ON_PAGE_ATTRIBUTE)) ?
                AttributeNameConstant.DEFAULT_NUMBER_OF_PUBLICATIONS_PER_PAGE : request.getParameter(AttributeNameConstant.PUBLICATION_ON_PAGE_ATTRIBUTE);
        request.setAttribute(AttributeNameConstant.CURRENT_PAGE_ATTRIBUTE, page);
        request.setAttribute("topic", topic);
        request.setAttribute(AttributeNameConstant.SORT_ATTRIBUTE, sort);
        request.setAttribute(AttributeNameConstant.PUBLICATION_ON_PAGE_ATTRIBUTE, numberOfPublicationsForPage);
    }

    public int getNumberOfPages(int numberOfPublication) {
        return (int) Math.ceil(numberOfPublication * NumberUtils.DOUBLE_ONE / Integer.parseInt(numberOfPublicationsForPage));
    }
}
