---
name: data-schema-consistency
description: "Use when modifying database entities or schema. Ensures schema.sql and data.sql remain consistent with Entity changes. Keywords: Entity, schema.sql, data.sql, database change, synchronization."
---

# Backend Schema Consistency Skill

## Purpose
Ensures that any changes to database entities, data models (DTO/VO), or business logic table structures are immediately synchronized across `schema.sql` and `data.sql` to maintain a consistent environment.

## Trigger Scenario
Whenever you modify Java Entities, alter table structures, or introduce new relationships (like many-to-many).

## Non-Negotiable Rules
1. **Synchronize Schema**: You must check and update `src/main/resources/schema.sql` to reflect the latest field changes, new table definitions, or constraint conditions (e.g., `CREATE TABLE` rules).
2. **Synchronize Data**: You must check and update `src/main/resources/data.sql` to ensure `INSERT` statements remain compatible with the modified table structure.
   - Removed fields MUST be removed from `INSERT` operations.
   - Newly forced (non-null) fields MUST have initial data populated.
3. **Idempotent Operations**: Use `ON DUPLICATE KEY UPDATE` or similar idempotent SQL strategies where possible, ensuring `data.sql` can run multiple times without causing duplicate ID conflicts.
4. **Validation Check**:
   - Manually read both SQL files again after generating them.
   - Ensure the database column naming convention (snake_case) maps correctly to the Java Entity mapping (camelCase).

## Examples
- **Bad Practice**: Modifying the Entity and `schema.sql`, but forgetting to update `data.sql`, causing the Spring Boot application to crash on startup due to missing column assignments.
- **Best Practice**: After editing an Entity, update the corresponding DDL in `schema.sql`, update initial insert data in `data.sql`, and execute a compilation check (`mvnw clean compile`).

