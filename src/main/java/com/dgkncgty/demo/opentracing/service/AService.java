package com.dgkncgty.demo.opentracing.service;

import com.dgkncgty.demo.opentracing.util.RandomUtil;
import com.google.common.collect.ImmutableMap;
import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AService {
    private static final int MAX_WAIT_TIME = 1000;

    private static final Logger log = LoggerFactory.getLogger(AService.class);

    @Autowired
    private Tracer tracer;

    private void doOp(String operationName) {
        int waitTime = RandomUtil.randomInt(MAX_WAIT_TIME);

        log.info("Will wait " + operationName + " for " + waitTime + "ms");

        Span span = tracer.buildSpan(operationName).start();
        try (Scope scope = tracer.scopeManager().activate(span)) {

            // Run random number of sub operations randomly
            if (RandomUtil.randomBool()) {
                doOp(operationName + "-" + RandomUtil.generateRandomString());
            }

            String logStr = String.format("Sleeping thread for %d seconds", waitTime);

            // Add logs to spans
            span.log("A string event log");
            span.log(ImmutableMap.of("event", "string-format", "value", logStr));
            span.log(ImmutableMap.of("event", "service-name", "handler", this.getClass().getCanonicalName()));

            // Add tags to spans
            span.setTag("op-name", operationName);
            span.setTag("wait-time", waitTime);

            // Add tags to spans (not used anymore, use logs)
            span.setBaggageItem("op-name-baggage", operationName);

            Thread.sleep(waitTime);
        } catch(InterruptedException e) {
            log.error("Interrupted sleep action", e);
        } finally {
            span.finish();
        }
    }

    public void doOp1() {
        doOp("op1");
    }

    public void doOp2() {
        doOp("op2");
    }

    public void doOp3() {
        doOp("op3");
    }

    public void doRandomOp() {
        doOp("randomOp" + "-" + RandomUtil.generateRandomString());
    }
}
