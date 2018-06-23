package me.nicolaferraro.yaml2camel;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.Test;

public class YamlRouteBuilderTest {

    @Test
    public void testParser() throws Exception {
        YamlRouteBuilder builder = new YamlRouteBuilder(getClass().getResourceAsStream("/example-1.yaml"));

        CamelContext context = new DefaultCamelContext();
        builder.addRoutesToCamelContext(context);
        context.start();

        Thread.sleep(5000);
        context.stop();

    }

}
