## Running the Critter project (Docker + JDK notes)

Prerequisites:
- JDK 17 or newer installed (recommended: JDK 17+). Spring Boot 3.x requires Java 17+.

Optional (Docker):
- If you prefer to run MySQL in Docker, ensure Docker & Docker Compose are installed and run the project's `docker-compose.yml` (if present).
- Example (optional):

```bash
cd starter/critter
docker compose up -d
```

This starts a MySQL 8 instance with:
- database: `critter`
- user: `critter_user`
- password: `critter_pass`

Update credentials in `src/main/resources/application.properties` if needed.

To run tests or start the app:

```bash
# Ensure JDK 17+ is active
mvn -f starter/critter/pom.xml clean test
# or
mvn -f starter/critter/pom.xml spring-boot:run
```

If your local JDK is older than 17, tests and build will fail. You can:
- Install and use JDK 17+ locally, or
- Build/run inside a container that has JDK 17+.