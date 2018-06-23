package me.nicolaferraro.yaml2camel;


import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        Optional<String> id = ObjectUtils.get(route, "id", String.class);
        ObjectUtils.get(route, "steps", List.class)
                .ifPresent(steps -> this.configureRouteSteps(steps, id));
    }

    protected void configureRouteSteps(List<?> steps, Optional<String> id) {

        if (steps.size() > 0) {

            // Consumer
            Object fromUri = steps.get(0);
            RouteDefinition route = from((String) fromUri);

            if (id.isPresent()) {
                route = route.id(id.get());
            }

            for (int i=1; i<steps.size(); i++) {
                Object to = steps.get(i);
                route = route.to((String) to);
            }

        }
    }

}
