package homework.day05.gateway.config;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RouteConfig {

    public static final String ROUTE_PREFIX = "http://127.0.0.1:";

    public static final Map<String, List<String>> ROUTE_MAP = new ConcurrentHashMap<>();

    public static synchronized void putRouteServer(String serverName, String route) {
        if (ROUTE_MAP.containsKey(serverName)) {
            boolean match = ROUTE_MAP.get(serverName).stream().anyMatch(url -> url.equals(route));
            if (!match) {
                ROUTE_MAP.get(serverName).add(route);
            }
        } else {
            ROUTE_MAP.put(serverName, new ArrayList<>(Collections.singletonList(route)));
        }
    }

}
