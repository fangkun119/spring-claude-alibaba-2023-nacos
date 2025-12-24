# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Spring Cloud Alibaba (2023) e-commerce microservices demo project ("tlmall") demonstrating service mesh patterns with Nacos for service discovery and configuration management.

**Tech Stack:**
- Java 21
- Spring Boot 3.2.4
- Spring Cloud 2023.0.1
- Spring Cloud Alibaba 2023.0.1.0
- Nacos (service discovery + config center)
- Sentinel (rate limiting via gateway)
- MyBatis 3.0.3
- MySQL (order service database)

**Build System:** Maven multi-module project using `${revision}` property versioning (currently 1.0.8).

## Module Structure

```
microservices/
├── tlmall-common/                    # Shared code: Result DTOs, exceptions
├── tlmall-nacos-demo-gateway/        # API Gateway (port 18888)
├── tlmall-nacos-demo-user/           # User service (port 8050)
├── tlmall-nacos-demo-order/          # Order service (port 8060)
├── tlmall-user-config-demo/          # User service with Nacos config
├── tlmall-order-config-demo/         # Order service with Nacos config
└── tlmall-user-openfeign-demo/       # User service with OpenFeign client
```

**Service Communication:**
- User service calls Order service via OpenFeign (`@FeignClient(value = "tlmall-order")`)
- Gateway routes: `/user/**` → `tlmall-user-openfeign-demo`, `/order/**` → `tlmall-order`
- Custom load balancer: IP hash-based implementation in `MyCustomLoadBalancer.java`

## Common Commands

**Build entire project:**
```bash
mvn clean install
```

**Build specific module:**
```bash
mvn clean install -pl microservices/tlmall-nacos-demo-gateway
```

**Run tests (all or specific module):**
```bash
mvn test                                    # All tests
mvn test -pl microservices/tlmall-nacos-demo-user  # Specific module
```

**Package without tests:**
```bash
mvn clean package -DskipTests
```

## Architecture Notes

**Nacos Configuration:**
- Services use `spring.cloud.nacos.discovery.server-addr` for service registration
- Config demo modules use `spring.config.import` with `nacos:` prefix to pull configs from Nacos
- Default Nacos address: `tlmall-nacos-server:8848` (username/password: `nacos/nacos`)

**Gateway Features:**
- CORS enabled globally
- Sentinel integration for rate limiting (dashboard: `tlmall-sentinel-dashboard:8888`)
- Custom gateway filter factory: `CheckAuthGatewayFilterFactory` (commented out, not @Component)
- Custom block handler: `MyBlockRequestHandler` returns JSON on 429

**Database Access:**
- Order service uses MyBatis with camelCase mapping enabled
- Connection: MySQL at `tlmall-mysql:3306/tlmall_order`

**Feign Client Pattern:**
- Interface-based declarative HTTP clients in `*feign*` packages
- Uses `@RequestLine` and `@Param` annotations (Feign-native style, not Spring MVC annotations)