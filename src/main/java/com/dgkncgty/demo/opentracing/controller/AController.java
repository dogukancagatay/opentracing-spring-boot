package com.dgkncgty.demo.opentracing.controller;

import com.dgkncgty.demo.opentracing.service.AService;
import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AController {
    private static final Logger log = LoggerFactory.getLogger(AController.class);

    @Autowired
    private AService service;

    @Autowired
    private Tracer tracer;

    @RequestMapping("/")
    public String index() {

        String result;

        Span span = tracer.buildSpan("prepare-index-page").start();
        try (Scope scope = tracer.scopeManager().activate(span)) {
            result = "<html><body>"
                    + "<h2>Routes</h2>"
                    + "<ul>"
                    + "<li><a href=\"/api/op1\">Run Operation 1</a></li>"
                    + "<li><a href=\"/api/op2\">Run Operation 2</a></li>"
                    + "<li><a href=\"/api/op3\">Run Operation 3</a></li>"
                    + "<li><a href=\"/api/opr\">Run Multiple Random Operations</a></li>"
                    + "<li><a href=\"/sequential-run\">Run Sequential Operations</a></li>"
                    + "</ul>"
                    + "</body></html>";
        } finally {
            span.finish();
        }

        return result;
    }

    @RequestMapping("/api/{op}")
    public String runOperation(@PathVariable String op) {

        switch (op) {
            case "op1":
                service.doOp1();
                break;
            case "op2":
                service.doOp2();
                break;
            case "op3":
                service.doOp3();
                break;
            default:
                service.doRandomOp();
                service.doRandomOp();
                service.doRandomOp();
                service.doRandomOp();
                service.doRandomOp();
                service.doRandomOp();
                break;
        }

        return "<html><body><p>Operation (" + (op.isEmpty() ? "random operation" : op) + ") completed. "
                + "<a href=\"/\">Go to home</a>.</p></body></html>"
                ;
    }

    @RequestMapping("/sequential-run")
    public String sequential() {

        Span span = tracer.buildSpan("seq-cont-op1").start();
        try (Scope scope = tracer.scopeManager().activate(span)) {
            service.doOp1();
        } finally {
            span.finish();
        }

        span = tracer.buildSpan("secondary-seq-op").start();
        try (Scope scope = tracer.scopeManager().activate(span)) {
            service.doOp1();
        } finally {
            span.finish();
        }
        return "Hello world";
    }
}
