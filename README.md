# Spring Boot Opentracing Demo

Demonstration of using opentracing inside spring boot.


## Running

```sh
docker-compose up -d --build
```

## API

- http://localhost:8080/ - Links to operations
- http://localhost:8080/api/op1 - Runs operation 1
- http://localhost:8080/api/op2 - Runs operation 2
- http://localhost:8080/api/op3 - Runs operation 3
- http://localhost:8080/api/opr - Runs a random operation

## Enable/Disable Tracing

You can enable/disable by setting `opentracing.jaeger.enabled` property or `OPENTRACING_JAEGER_ENABLED` environment variable.

## Notes
- If `opentracing-spring-jaeger-starter` dependency is not included, NoopTracer is used, practically disabling tracing.
- Do not include `opentracing-spring-jaeger-starter` when developing a library.
