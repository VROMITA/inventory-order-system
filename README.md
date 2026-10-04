# Inventory & Order Management System

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.x-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)
![Status](https://img.shields.io/badge/status-in%20development-yellow)

> ⚠️ **Actively under development.** Persistence layer, business logic, and the full REST API are complete. Test suite is in progress. See the [Roadmap](#roadmap) for detailed status.

## Overview

Backend for managing orders, multi-warehouse stock, and returns, inspired by real-world Order Management workflows (SAP domain: material master data, plant-level stock, order-to-delivery cycle). Second module of my personal portfolio, built as a natural technical progression after the [Incident Management System](https://github.com/VROMITA/incident-management-system-spring), applied to a domain closer to my day-to-day professional experience.

## Tech Stack

| Layer | Choice |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 4.x |
| Persistence | Spring Data JPA + Hibernate |
| Database | PostgreSQL 16 (via Docker Compose) |
| Mapping | MapStruct |
| Build | Maven |
| Validation | Jakarta Bean Validation |

## Domain Model

Six core entities, with many-to-one relationships and integrity constraints enforced both in Java (JPA) and at the database level:

- **Product** / **Warehouse**: independent master data
- **Stock**: quantity per product/warehouse pair, with optimistic locking (`@Version`) and atomic update queries for decreasing/increasing quantity
- **Order** → **OrderItem**: an order and its line items, using a price snapshot pattern, the price is frozen at order time and stays independent of later catalog price changes
- **Return**: returns linked to a specific order line, with returned quantity and reason

Composite constraints (e.g. a unique `product_id + warehouse_id` pair on `Stock`) and controlled state transitions (`OrderStatus`: `ORDERED → PACKAGING → IN_TRANSPORT → DELIVERED`, with `CANCELLED` as an alternative terminal state) model real business rules, not just generic CRUD.

Order creation automatically allocates stock from the first warehouse with sufficient quantity for each line. Each `OrderItem` keeps a reference to that warehouse, so cancelling an order restores the exact stock location it was taken from.

## API Overview

RESTful endpoints under `/api`, one resource per core entity:

- `/api/products`, `/api/warehouses`: full CRUD for master data
- `/api/stocks`: create and read, plus dedicated `receive` and `block` endpoints for inventory movements, keeping raw quantity changes behind named, intentional operations instead of a generic update
- `/api/orders`: create, read (optional `customerCode` filter), and `cancel` (restores stock across all order lines)
- `/api/returns`: create, read (optional `customerCode` filter)

Write endpoints validate input via Jakarta Bean Validation. Domain-specific errors (duplicate codes, insufficient stock, invalid status transitions) are translated into proper HTTP status codes (404, 409) by a centralized exception handler, instead of leaking generic 500 responses.

## Running Locally

```bash
# 1. Start PostgreSQL via Docker Compose
docker-compose up -d

# 2. Start the application ("local" profile)
./mvnw spring-boot:run
```

The app expects an `application-local.yaml` file (gitignored) with database credentials, not included in the repository.

## Roadmap

Versioning stays under `v0.x` until business logic, REST API, and tests are all solid together. Only then does `v1.0` ship.

- [x] **v0.1**: Domain model (6 entities), Docker + PostgreSQL infrastructure, complete Repository layer
- [x] **v0.2**: Service layer, business logic for orders, stock, returns
- [x] **v0.3**: REST API, DTOs, controllers, centralized error handling
- [ ] **v0.4**: Test suite (JUnit + Mockito), with a focus on optimistic locking under concurrency *(in progress)*
- [ ] **v1.0**: First stable release, Service + REST + Tests solid together
- [ ] **v1.1**: Hardening, structured logging, configuration polish
- [ ] **v1.2**: API documentation (Swagger/OpenAPI)
- [ ] **v1.3**: Integration with [IMS](https://github.com/VROMITA/incident-management-system-spring), automatic incident creation on detected anomalies (inconsistent stock, invalid return)

## Author

**Valerio Romita**
[LinkedIn](https://www.linkedin.com/in/valerio-romita/) · [GitHub](https://github.com/VROMITA)