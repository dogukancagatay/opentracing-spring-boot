package com.dgkncgty.demo.opentracing.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(value = "opentracing.jaeger.enabled", havingValue = "false", matchIfMissing = false)
@Configuration
public class TracingDisabler {
    private static final Logger log = LoggerFactory.getLogger(TracingDisabler.class);

    @Bean
    public io.opentracing.Tracer jaegerTracer() {
        log.info("Distributed tracing disabled");
        return io.opentracing.noop.NoopTracerFactory.create();
    }
}
