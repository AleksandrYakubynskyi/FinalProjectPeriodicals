package com.project.resolver;

import com.project.resolver.impl.TopicResolver;
import com.project.util.SqlFactory;

import java.util.LinkedHashMap;
import java.util.Map;

public interface Resolver {
    Map<String, Resolver> resolverMap = createMap();

    void resolve(String[] value, SqlFactory factory);

    static Map<String, Resolver> createMap() {
        Map<String, Resolver> map = new LinkedHashMap<>();

        map.put("topic", new TopicResolver());

        return map;
    }
}
