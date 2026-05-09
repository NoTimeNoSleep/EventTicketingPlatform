## Database setup

This project uses PostgreSQL with WildFly 38 datasource.

### What was done

A local database connection was set up and tested.  
A simple `Venue` flow was added to confirm that the backend can read from the database:

`venues` table → `Venue` entity → `VenueDAO` → `VenueBean` → `venues.xhtml`

If everything works, opening `/venues.xhtml` should show venues from the database.

---

## Local DB setup steps

1. Create PostgreSQL database

- Create a database named:
  `ticketplatform`

2. Run database schema script
- Open pgAdmin → select ticketplatform → Query Tool.
- Run:
`create_ER_with_enums.sql`
- This creates the required schema and tables.
- Tables are created inside schema:
event_ticketing

3. Add PostgreSQL driver to WildFly
- Copy PostgreSQL driver .jar into:
`wildfly/standalone/deployments`
- Example: 
`postgresql-42.7.10.jar`
- Start WildFly and make sure the driver is deployed.

4. Create WildFly datasource
- In WildFly admin console: 
http://localhost:9990
- Create datasource with:
  - Name: `TicketPlatformDS`
  - JNDI Name: `java:jboss/datasources/TicketPlatformDS`
  - Driver: `postgresql-42.7.10.jar`
  - Driver Class: `org.postgresql.Driver`
  - Connection URL: `jdbc:postgresql://localhost:5432/ticketplatform`
  - Username: your PostgreSQL username
  - Password: your PostgreSQL password

- After saving, test connection.
  - Expected result: Successfully tested connection for datasource TicketPlatformDS.

5. Build project
mvn clean package

6. Deploy to WildFly
- Copy: `target/event-ticket-platform.war`
to: `wildfly/standalone/deployments`
- Wait until:
`event-ticket-platform.war.deployed` appears.

7. Open test page
http://localhost:8080/event-ticket-platform/venues.xhtml

- If the venues table has data, it should appear in the table.

- To add exampple data you can try something like this in PgAdmin:
```
INSERT INTO event_ticketing.venues (id, name, location)
VALUES (gen_random_uuid(), 'Test Venue', 'Vilnius');
```

---

