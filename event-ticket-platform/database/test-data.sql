---Insert test data---

-- ===================== USERS =====================

INSERT INTO event_ticketing.users (id, name, surname, email, password_hash)
VALUES 
  ('00000000-0000-0000-0000-000000000001', 'John', 'Doe', 'john.doe@example.com', 'hashed_password_1'),
  ('00000000-0000-0000-0000-000000000002', 'Jane', 'Smith', 'jane.smith@example.com', 'hashed_password_2');

-- ===================== ROLES =====================

INSERT INTO event_ticketing.roles (id, type)
VALUES 
  ('00000000-0000-0000-0000-000000000101', 'CUSTOMER'),
  ('00000000-0000-0000-0000-000000000102', 'ADMIN');

-- ===================== USER ROLES =====================

INSERT INTO event_ticketing.user_roles (user_id, role_id)
VALUES 
  ('00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000101'),
  ('00000000-0000-0000-0000-000000000002', '00000000-0000-0000-0000-000000000102');

-- ===================== VENUES =====================

INSERT INTO event_ticketing.venues (id, name, location)
VALUES 
  ('00000000-0000-0000-0000-000000000201', 'Grand Arena', 'New York, NY'),
  ('00000000-0000-0000-0000-000000000202', 'Music Hall', 'Los Angeles, CA');

-- ===================== SEATS =====================

INSERT INTO event_ticketing.seats (id, venue_id, section, row, number)
VALUES 
  ('00000000-0000-0000-0000-000000000301', '00000000-0000-0000-0000-000000000201', 'A', '1', '1'),
  ('00000000-0000-0000-0000-000000000302', '00000000-0000-0000-0000-000000000201', 'A', '1', '2'),
  ('00000000-0000-0000-0000-000000000303', '00000000-0000-0000-0000-000000000202', 'B', '2', '5');

-- ===================== BUSINESSES =====================

INSERT INTO event_ticketing.businesses (id, name, email, phone, website, tax_id, address, city, postal_code, country, registration_date, legal_form, industry, description, logo_url)
VALUES 
  ('00000000-0000-0000-0000-000000000f10', 'EventTicketing', 'info@eventticketing.com', '+1-555-0123', 'www.eventticketing.com', 'TAX-PE-2024-001', 'MIF', 'Vilnius', 'VU 10001', 'Lithuania', NOW(), 'LLC', 'Entertainment & Events', 'Leading event management company specializing in concert productions and live entertainment', 'https://assets.eventticketing.com/logo.png');

-- ===================== EVENTS =====================

INSERT INTO event_ticketing.events (id, name, description, datetime, category, venue_id)
VALUES 
  ('00000000-0000-0000-0000-000000000401', 'Summer Concert 2026', 'Best rock bands', NOW() + INTERVAL '30 days', 'CONCERT', '00000000-0000-0000-0000-000000000201'),
  ('00000000-0000-0000-0000-000000000402', 'Classical Symphony', 'Orchestra performance', NOW() + INTERVAL '45 days', 'THEATER', '00000000-0000-0000-0000-000000000202');

-- ===================== TICKET TYPES =====================

INSERT INTO event_ticketing.ticket_types (id, event_id, name, type, price, capacity, reserved_count, sold_count)
VALUES 
  ('00000000-0000-0000-0000-000000000501', '00000000-0000-0000-0000-000000000401', 'VIP Seat', 'SEATED', 150.00, 100, 0, 0),
  ('00000000-0000-0000-0000-000000000502', '00000000-0000-0000-0000-000000000401', 'General Admission', 'STANDING', 50.00, 500, 0, 0),
  ('00000000-0000-0000-0000-000000000503', '00000000-0000-0000-0000-000000000402', 'Premium Seat', 'SEATED', 120.00, 80, 0, 0);

-- ===================== EVENT SEATS =====================

INSERT INTO event_ticketing.event_seats (id, event_id, seat_id, ticket_type_id, status, reserved_until)
VALUES 
  ('00000000-0000-0000-0000-000000000601', '00000000-0000-0000-0000-000000000401', '00000000-0000-0000-0000-000000000301', '00000000-0000-0000-0000-000000000501', 'AVAILABLE', NULL),
  ('00000000-0000-0000-0000-000000000602', '00000000-0000-0000-0000-000000000401', '00000000-0000-0000-0000-000000000302', '00000000-0000-0000-0000-000000000501', 'AVAILABLE', NULL);

-- ===================== ORDERS =====================

INSERT INTO event_ticketing.orders (id, user_id, subtotal, tax_total, total_amount, status, created_at, updated_at, country, region)
VALUES 
  ('00000000-0000-0000-0000-000000000701', '00000000-0000-0000-0000-000000000001', 150.00, 31.50, 181.50, 'PENDING_PAYMENT', NOW(), NOW(), 'USA', 'NY'),
  ('00000000-0000-0000-0000-000000000702', '00000000-0000-0000-0000-000000000002', 100.00, 21.00, 121.00, 'PAID', NOW(), NOW(), 'USA', 'CA');

-- ===================== PAYMENTS =====================

INSERT INTO event_ticketing.payments (id, order_id, amount, method, status, created_at, paid_at)
VALUES 
  ('00000000-0000-0000-0000-000000000801', '00000000-0000-0000-0000-000000000701', 181.50, 'CARD', 'PENDING', NOW(), NULL),
  ('00000000-0000-0000-0000-000000000802', '00000000-0000-0000-0000-000000000702', 121.00, 'BANK_TRANSFER', 'PAID', NOW(), NOW());

-- ===================== TAX RULES =====================

INSERT INTO event_ticketing.tax_rules (id, name, rate, type, country, region, included_in_price, valid_from, valid_to)
VALUES 
  ('00000000-0000-0000-0000-000000000901', 'US Sales Tax NY', 8.50, 'SALES_TAX', 'USA', 'NY', false, NOW(), NOW() + INTERVAL '1 year'),
  ('00000000-0000-0000-0000-000000000902', 'US Sales Tax CA', 7.50, 'SALES_TAX', 'USA', 'CA', false, NOW(), NOW() + INTERVAL '1 year');

-- ===================== TAX LINES =====================

INSERT INTO event_ticketing.tax_lines (id, order_id, ticket_id, name, rate, amount)
VALUES 
  ('00000000-0000-0000-0000-000000000a01', '00000000-0000-0000-0000-000000000701', NULL, 'Sales Tax', 8.50, 31.50),
  ('00000000-0000-0000-0000-000000000a02', '00000000-0000-0000-0000-000000000702', NULL, 'Sales Tax', 7.50, 21.00);

-- ===================== BULK PURCHASES =====================

INSERT INTO event_ticketing.bulk_purchases (id, order_id, type, company_name, company_email, tax_id, created_at)
VALUES 
  ('00000000-0000-0000-0000-000000000b01', '00000000-0000-0000-0000-000000000702', 'CORPORATE', 'Tech Corp Inc', 'events@techcorp.com', 'TAX-123456', NOW());

-- ===================== INVOICES =====================

INSERT INTO event_ticketing.invoices (id, order_id, company_name, tax_id, billing_address, subtotal, tax_total, total_amount, status, issued_at)
VALUES 
  ('00000000-0000-0000-0000-000000000c01', '00000000-0000-0000-0000-000000000702', 'Tech Corp Inc', 'TAX-123456', '123 Business St, CA 90001', 100.00, 21.00, 121.00, 'ISSUED', NOW());

-- ===================== INVOICE TAX LINES =====================

INSERT INTO event_ticketing.invoice_tax_lines (id, invoice_id, name, rate, amount)
VALUES 
  ('00000000-0000-0000-0000-000000000d01', '00000000-0000-0000-0000-000000000c01', 'Sales Tax CA', 7.50, 21.00);

-- ===================== TICKETS =====================

INSERT INTO event_ticketing.tickets (id, event_id, ticket_type_id, seat_id, order_id, status)
VALUES 
  ('00000000-0000-0000-0000-000000000e01', '00000000-0000-0000-0000-000000000401', '00000000-0000-0000-0000-000000000501', '00000000-0000-0000-0000-000000000301', '00000000-0000-0000-0000-000000000701', 'RESERVED'),
  ('00000000-0000-0000-0000-000000000e02', '00000000-0000-0000-0000-000000000402', '00000000-0000-0000-0000-000000000503', NULL, '00000000-0000-0000-0000-000000000702', 'PURCHASED');

-- ===================== QR CODES =====================

INSERT INTO event_ticketing.qr_codes (id, ticket_id, code_value, generated_at, status)
VALUES 
  ('00000000-0000-0000-0000-000000000f01', '00000000-0000-0000-0000-000000000e01', 'QR_CODE_12345_SEAT_A1', NOW(), 'ACTIVE'),
  ('00000000-0000-0000-0000-000000000f02', '00000000-0000-0000-0000-000000000e02', 'QR_CODE_12346_GENERAL', NOW(), 'ACTIVE');

-- ===================== TICKET DELIVERIES =====================

INSERT INTO event_ticketing.ticket_deliveries (id, ticket_id, email, user_id, status, created_at, updated_at, sent_at)
VALUES 
  ('00000000-0000-0000-0000-000000001001', '00000000-0000-0000-0000-000000000e01', 'john.doe@example.com', '00000000-0000-0000-0000-000000000001', 'SENT', NOW(), NOW(), NOW()),
  ('00000000-0000-0000-0000-000000001002', '00000000-0000-0000-0000-000000000e02', 'jane.smith@example.com', '00000000-0000-0000-0000-000000000002', 'SENT', NOW(), NOW(), NOW());

-- ===================== NOTIFICATIONS =====================

INSERT INTO event_ticketing.notifications (id, user_id, ticket_delivery_id, type, title, message, created_at, scheduled_at, sent_at, status)
VALUES 
  ('00000000-0000-0000-0000-000000001101', NULL, '00000000-0000-0000-0000-000000001001', 'TICKET_DELIVERY', 'Ticket Ready', 'Your ticket for Summer Concert 2026 is ready', NOW(), NOW(), NOW(), 'SENT'),
  ('00000000-0000-0000-0000-000000001102', '00000000-0000-0000-0000-000000000002', NULL, 'ORDER_CONFIRMATION', 'Order Confirmed', 'Your order #00000000-0000-0000-0000-000000000702 has been confirmed', NOW(), NOW(), NOW(), 'SENT');

-- ===================== EMAIL JOBS =====================

INSERT INTO event_ticketing.email_jobs (id, notification_id, recipient_email, payload, job_status, attempts, max_attempts, created_at, scheduled_at, sent_at)
VALUES 
  ('00000000-0000-0000-0000-000000001201', '00000000-0000-0000-0000-000000001101', 'john.doe@example.com', '{"ticket_id":"00000000-0000-0000-0000-000000000e01","event_name":"Summer Concert 2026"}', 'SENT', 1, 3, NOW(), NOW(), NOW()),
  ('00000000-0000-0000-0000-000000001202', '00000000-0000-0000-0000-000000001102', 'jane.smith@example.com', '{"order_id":"00000000-0000-0000-0000-000000000702","total":"121.00"}', 'SENT', 1, 3, NOW(), NOW(), NOW());

-- ===================== VERIFICATION QUERIES =====================

SELECT 'Users' as table_name, COUNT(*) as row_count FROM event_ticketing.users
UNION ALL
SELECT 'Roles', COUNT(*) FROM event_ticketing.roles
UNION ALL
SELECT 'User Roles', COUNT(*) FROM event_ticketing.user_roles
UNION ALL
SELECT 'Venues', COUNT(*) FROM event_ticketing.venues
UNION ALL
SELECT 'Seats', COUNT(*) FROM event_ticketing.seats
UNION ALL
SELECT 'Businesses', COUNT(*) FROM event_ticketing.businesses
UNION ALL
SELECT 'Events', COUNT(*) FROM event_ticketing.events
UNION ALL
SELECT 'Ticket Types', COUNT(*) FROM event_ticketing.ticket_types
UNION ALL
SELECT 'Event Seats', COUNT(*) FROM event_ticketing.event_seats
UNION ALL
SELECT 'Orders', COUNT(*) FROM event_ticketing.orders
UNION ALL
SELECT 'Payments', COUNT(*) FROM event_ticketing.payments
UNION ALL
SELECT 'Tax Rules', COUNT(*) FROM event_ticketing.tax_rules
UNION ALL
SELECT 'Tax Lines', COUNT(*) FROM event_ticketing.tax_lines
UNION ALL
SELECT 'Bulk Purchases', COUNT(*) FROM event_ticketing.bulk_purchases
UNION ALL
SELECT 'Invoices', COUNT(*) FROM event_ticketing.invoices
UNION ALL
SELECT 'Invoice Tax Lines', COUNT(*) FROM event_ticketing.invoice_tax_lines
UNION ALL
SELECT 'Tickets', COUNT(*) FROM event_ticketing.tickets
UNION ALL
SELECT 'QR Codes', COUNT(*) FROM event_ticketing.qr_codes
UNION ALL
SELECT 'Ticket Deliveries', COUNT(*) FROM event_ticketing.ticket_deliveries
UNION ALL
SELECT 'Notifications', COUNT(*) FROM event_ticketing.notifications
UNION ALL
SELECT 'Email Jobs', COUNT(*) FROM event_ticketing.email_jobs;

-- ===================== ADDITIONAL QUERIES =====================

-- Link one tax line to a ticket to test optional TaxLine -> Ticket relationship
UPDATE event_ticketing.tax_lines
SET ticket_id = (
    SELECT id
    FROM event_ticketing.tickets
             LIMIT 1
    )
WHERE id = (
    SELECT id
    FROM event_ticketing.tax_lines
    LIMIT 1
    );


