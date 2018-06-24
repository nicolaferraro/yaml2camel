package me.nicolaferraro.yaml2camel;


import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class YamlRouteBuilder extends RouteBuilder {

    private Map tree;

    public YamlRouteBuilder(InputStream inputStream) {
        Yaml parser = new Yaml();
        this.tree = parser.load(inputStream);
    }

    public void configure() throws Exception {
        ObjectUtils.get(tree, "routes", List.class)
                .ifPresent(this::configureRoutes);
    }

    protected void configureRoutes(List<?> routes) {
        routes.forEach(this::configureRoute);
    }

    protected void configureRoute(Object route) {
        ObjectUtils.get(route, "route", List.class)
                .ifPresent(this::configureRouteSteps);
    }

    protected void configureRouteSteps(List<?> steps) {

        if (steps.size() > 0) {

            // Consumer
            Object fromUri = steps.get(0);
            RouteDefinition route = from((String) fromUri);

            for (int i=1; i<steps.size(); i++) {
                Object to = steps.get(i);
                route = route.to((String) to);
            }

        }
    }

}
