---Insert test data---

---event_seats

SELECT id, name FROM event_ticketing.events;
SELECT id, section, row, number FROM event_ticketing.seats;
SELECT id, name FROM event_ticketing.ticket_types;

INSERT INTO event_ticketing.event_seats (
    id,
    event_id,
    seat_id,
    ticket_type_id,
    status
)
VALUES (
           gen_random_uuid(),
           'PASTE_EVENT_ID_HERE',
           'PASTE_SEAT_ID_HERE',
           'PASTE_TICKET_TYPE_ID_HERE',
           'AVAILABLE'
       );

--- tickets

SELECT id, name FROM event_ticketing.events;
SELECT id, name FROM event_ticketing.ticket_types;
SELECT id, section, row, number FROM event_ticketing.seats;
SELECT id, email FROM event_ticketing.users;

---

INSERT INTO event_ticketing.orders (
    id, user_id, subtotal, tax_total, total_amount, status, created_at
)
VALUES (
           gen_random_uuid(),
           'b8d43fc4-35f9-4c3e-aa90-798883d4e3b8',
           50.00,
           10.50,
           60.50,
           'CREATED',
           NOW()
       );

---

SELECT id, total_amount, status FROM event_ticketing.orders;

---

INSERT INTO event_ticketing.tickets (
    id, event_id, ticket_type_id, seat_id, order_id, status
)
VALUES (
           gen_random_uuid(),
           'b17d1a25-8bd7-4d45-a532-607e195bfaf6',
           '3bfe42d8-68dc-4c12-b0db-8c631676c7c2',
           'dceef315-4e88-435c-9f44-5b0d2309a71d',
           '5b15ed79-3709-40ce-9ff5-0509d4b22fe8',
           'RESERVED'
       );

---








