package com.gwg.shiro.web.util;

import org.springframework.data.redis.connection.RedisNode;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class RedisUtil {

    public static List<RedisNode> createSentinels(String sentinelNodes) {
        List<RedisNode> nodes = new ArrayList<RedisNode>();
        for (String node : StringUtils
                .commaDelimitedListToStringArray(sentinelNodes)) {
            try {
                String[] parts = StringUtils.split(node, ":");
                Assert.state(parts.length == 2, "Must be defined as 'host:port'");
                nodes.add(new RedisNode(parts[0], Integer.valueOf(parts[1])));
            }
            catch (RuntimeException ex) {
                throw new IllegalStateException(
                        "Invalid redis sentinel " + "property '" + node + "'", ex);
            }
        }
        return nodes;
    }
}
