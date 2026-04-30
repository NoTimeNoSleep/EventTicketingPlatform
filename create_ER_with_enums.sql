-- Specifically designed for PostgreSQL

-- ===================== SCHEMA =====================

CREATE SCHEMA IF NOT EXISTS event_ticketing;
SET search_path TO event_ticketing;

-- ===================== ENUM TYPES =====================

CREATE TYPE role_type_enum AS ENUM ('ADMIN', 'MANAGER', 'EVENTORGANIZER', 'STAFF', 'CUSTOMER');
CREATE TYPE category_enum AS ENUM ('CONCERT', 'SPORTS', 'FESTIVAL', 'THEATER', 'MUSEUM', 'MOVIE');
CREATE TYPE ticket_kind_enum AS ENUM ('STANDING', 'SEATED');
CREATE TYPE seat_status_enum AS ENUM ('AVAILABLE', 'RESERVED', 'SOLD');
CREATE TYPE ticket_status_enum AS ENUM ('RESERVED', 'PURCHASED', 'CANCELLED', 'USED');
CREATE TYPE payment_method_enum AS ENUM ('CARD', 'BANK_TRANSFER');
CREATE TYPE payment_status_enum AS ENUM ('PAID', 'PENDING', 'FAILED', 'REFUNDED');
CREATE TYPE invoice_status_enum AS ENUM ('DRAFT', 'ISSUED', 'PAID', 'CANCELLED');
CREATE TYPE bulk_type_enum AS ENUM ('GROUP', 'CORPORATE');
CREATE TYPE assignment_status_enum AS ENUM ('PENDING', 'SENT', 'CLAIMED');
CREATE TYPE job_status_enum AS ENUM ('PENDING', 'PROCESSING', 'SENT', 'FAILED', 'DEAD');
CREATE TYPE tax_type_enum AS ENUM ('VAT', 'SALES_TAX', 'SERVICE_TAX');
CREATE TYPE notification_type_enum AS ENUM ('TICKET_DELIVERY', 'ORDER_CONFIRMATION', 'EVENT_REMINDER', 'SYSTEM', 'INVOICE', 'PASSWORD_RESET');
CREATE TYPE notification_status_enum AS ENUM ('SCHEDULED', 'SENT', 'FAILED', 'CANCELLED');
CREATE TYPE order_status_enum AS ENUM ('CREATED', 'PENDING_PAYMENT', 'PAID', 'COMPLETED');
CREATE TYPE qr_code_status_enum AS ENUM ('ACTIVE', 'INVALIDATED', 'EXPIRED');

-- ===================== USERS =====================

CREATE TABLE users (
  id UUID PRIMARY KEY,
  name TEXT NOT NULL,
  surname TEXT NOT NULL,
  email TEXT UNIQUE NOT NULL,
  password_hash TEXT NOT NULL
);

CREATE TABLE roles (
  id UUID PRIMARY KEY,
  type role_type_enum NOT NULL,
  UNIQUE (type)
);

CREATE TABLE user_roles (
  user_id UUID NOT NULL,
  role_id UUID NOT NULL,
  PRIMARY KEY (user_id, role_id),
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- ===================== VENUE & SEATS =====================

CREATE TABLE venues (
  id UUID PRIMARY KEY,
  name TEXT NOT NULL,
  location TEXT NOT NULL
);

CREATE TABLE seats (
  id UUID PRIMARY KEY,
  venue_id UUID NOT NULL,
  section TEXT,
  row TEXT,
  number TEXT,
  FOREIGN KEY (venue_id) REFERENCES venues(id) ON DELETE CASCADE
);

-- ===================== EVENTS =====================

CREATE TABLE events (
  id UUID PRIMARY KEY,
  name TEXT NOT NULL,
  description TEXT,
  datetime TIMESTAMP NOT NULL,
  category category_enum,
  venue_id UUID NOT NULL,
  FOREIGN KEY (venue_id) REFERENCES venues(id)
);

-- ===================== TICKET TYPES =====================

CREATE TABLE ticket_types (
  id UUID PRIMARY KEY,
  event_id UUID NOT NULL,
  name TEXT NOT NULL,
  type ticket_kind_enum NOT NULL,
  price NUMERIC(12,2) NOT NULL,
  capacity INT,
  reserved_count INT NOT NULL DEFAULT 0,
  sold_count INT NOT NULL DEFAULT 0,
  FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE,
  CHECK (capacity IS NULL OR capacity >= 0),
  CHECK (reserved_count >= 0 AND sold_count >= 0),
  CHECK (capacity IS NULL OR reserved_count + sold_count <= capacity)
);

-- ===================== EVENT SEATS (SEATED INVENTORY) =====================

CREATE TABLE event_seats (
  id UUID PRIMARY KEY,
  event_id UUID NOT NULL,
  seat_id UUID NOT NULL,
  ticket_type_id UUID NOT NULL,
  status seat_status_enum NOT NULL,
  reserved_until TIMESTAMP,
  FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE,
  FOREIGN KEY (seat_id) REFERENCES seats(id) ON DELETE CASCADE,
  FOREIGN KEY (ticket_type_id) REFERENCES ticket_types(id) ON DELETE CASCADE,
  UNIQUE (event_id, seat_id)
);

-- ===================== ORDERS =====================

CREATE TABLE orders (
  id UUID PRIMARY KEY,
  user_id UUID NOT NULL,
  subtotal NUMERIC(12,2) NOT NULL,
  tax_total NUMERIC(12,2) NOT NULL,
  total_amount NUMERIC(12,2) NOT NULL,
  status order_status_enum,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP,
  country TEXT,
  region TEXT,
  FOREIGN KEY (user_id) REFERENCES users(id),
  CHECK (subtotal >= 0 AND tax_total >= 0 AND total_amount >= 0)
);

-- ===================== PAYMENTS =====================

CREATE TABLE payments (
  id UUID PRIMARY KEY,
  order_id UUID NOT NULL,
  amount NUMERIC(12,2) NOT NULL,
  method payment_method_enum,
  status payment_status_enum,
  created_at TIMESTAMP NOT NULL,
  paid_at TIMESTAMP,
  FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);

-- ===================== BULK PURCHASE =====================

CREATE TABLE bulk_purchases (
  id UUID PRIMARY KEY,
  order_id UUID UNIQUE NOT NULL,
  type bulk_type_enum,
  company_name TEXT,
  company_email TEXT,
  tax_id TEXT,
  created_at TIMESTAMP,
  FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);

-- ===================== INVOICES =====================

CREATE TABLE invoices (
  id UUID PRIMARY KEY,
  order_id UUID UNIQUE NOT NULL,
  company_name TEXT,
  tax_id TEXT,
  billing_address TEXT,
  subtotal NUMERIC(12,2),
  tax_total NUMERIC(12,2),
  total_amount NUMERIC(12,2),
  status invoice_status_enum,
  issued_at TIMESTAMP,
  FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);

CREATE TABLE invoice_tax_lines (
  id UUID PRIMARY KEY,
  invoice_id UUID NOT NULL,
  name TEXT,
  rate NUMERIC(5,2),
  amount NUMERIC(12,2),
  FOREIGN KEY (invoice_id) REFERENCES invoices(id) ON DELETE CASCADE
);

-- ===================== TICKETS =====================

CREATE TABLE tickets (
  id UUID PRIMARY KEY,
  event_id UUID NOT NULL,
  ticket_type_id UUID NOT NULL,
  seat_id UUID,
  order_id UUID NOT NULL,
  status ticket_status_enum,
  FOREIGN KEY (event_id) REFERENCES events(id),
  FOREIGN KEY (ticket_type_id) REFERENCES ticket_types(id),
  FOREIGN KEY (seat_id) REFERENCES seats(id),
  FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);

-- ===================== QR CODES =====================

CREATE TABLE qr_codes (
  id UUID PRIMARY KEY,
  ticket_id UUID UNIQUE NOT NULL,
  code_value TEXT NOT NULL,
  generated_at TIMESTAMP,
  status qr_code_status_enum,
  FOREIGN KEY (ticket_id) REFERENCES tickets(id) ON DELETE CASCADE
);

-- ===================== TICKET DELIVERY =====================

CREATE TABLE ticket_deliveries (
  id UUID PRIMARY KEY,
  ticket_id UUID UNIQUE NOT NULL,
  email TEXT NOT NULL,
  user_id UUID,
  status assignment_status_enum,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP,
  sent_at TIMESTAMP,
  FOREIGN KEY (ticket_id) REFERENCES tickets(id) ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
);

-- ===================== TAX =====================

CREATE TABLE tax_rules (
  id UUID PRIMARY KEY,
  name TEXT,
  rate NUMERIC(5,2),
  type tax_type_enum,
  country TEXT,
  region TEXT,
  included_in_price BOOLEAN,
  valid_from TIMESTAMP,
  valid_to TIMESTAMP
);

CREATE TABLE tax_lines (
  id UUID PRIMARY KEY,
  order_id UUID NOT NULL,
  ticket_id UUID,
  name TEXT,
  rate NUMERIC(5,2),
  amount NUMERIC(12,2),
  FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
  FOREIGN KEY (ticket_id) REFERENCES tickets(id) ON DELETE SET NULL
);

-- ===================== NOTIFICATIONS =====================

CREATE TABLE notifications (
  id UUID PRIMARY KEY,
  user_id UUID,
  ticket_delivery_id UUID,
  type notification_type_enum,
  title TEXT,
  message TEXT,
  created_at TIMESTAMP NOT NULL,
  scheduled_at TIMESTAMP,
  sent_at TIMESTAMP,
  status notification_status_enum,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (ticket_delivery_id) REFERENCES ticket_deliveries(id) ON DELETE CASCADE,
  CHECK (
    (type = 'TICKET_DELIVERY' AND ticket_delivery_id IS NOT NULL AND user_id IS NULL) OR
    (type IS DISTINCT FROM 'TICKET_DELIVERY' AND ticket_delivery_id IS NULL AND user_id IS NOT NULL)
  )
);

-- ===================== EMAIL JOBS =====================

CREATE TABLE email_jobs (
  id UUID PRIMARY KEY,
  notification_id UUID NOT NULL,
  recipient_email TEXT NOT NULL,
  payload JSONB,
  job_status job_status_enum,
  attempts INT DEFAULT 0,
  max_attempts INT DEFAULT 3,
  created_at TIMESTAMP NOT NULL,
  scheduled_at TIMESTAMP,
  sent_at TIMESTAMP,
  FOREIGN KEY (notification_id) REFERENCES notifications(id) ON DELETE CASCADE,
  CHECK (attempts >= 0),
  CHECK (max_attempts >= 1),
  CHECK (attempts <= max_attempts)
);

-- ===================== INDEXES =====================

CREATE INDEX idx_user_roles_user_id ON user_roles(user_id);
CREATE INDEX idx_user_roles_role_id ON user_roles(role_id);
CREATE INDEX idx_seats_venue_id ON seats(venue_id);
CREATE INDEX idx_events_venue_id ON events(venue_id);
CREATE INDEX idx_ticket_types_event_id ON ticket_types(event_id);
CREATE INDEX idx_event_seats_event_id ON event_seats(event_id);
CREATE INDEX idx_event_seats_seat_id ON event_seats(seat_id);
CREATE INDEX idx_event_seats_ticket_type_id ON event_seats(ticket_type_id);
CREATE INDEX idx_orders_user_id ON orders(user_id);
CREATE INDEX idx_payments_order_id ON payments(order_id);
CREATE UNIQUE INDEX ux_payments_one_paid_per_order ON payments(order_id) WHERE status = 'PAID';
CREATE INDEX idx_bulk_purchases_order_id ON bulk_purchases(order_id);
CREATE INDEX idx_invoices_order_id ON invoices(order_id);
CREATE INDEX idx_invoice_tax_lines_invoice_id ON invoice_tax_lines(invoice_id);
CREATE INDEX idx_tickets_event_id ON tickets(event_id);
CREATE INDEX idx_tickets_ticket_type_id ON tickets(ticket_type_id);
CREATE INDEX idx_tickets_seat_id ON tickets(seat_id);
CREATE INDEX idx_tickets_order_id ON tickets(order_id);
CREATE INDEX idx_qr_codes_ticket_id ON qr_codes(ticket_id);
CREATE INDEX idx_ticket_deliveries_ticket_id ON ticket_deliveries(ticket_id);
CREATE INDEX idx_ticket_deliveries_user_id ON ticket_deliveries(user_id);
CREATE INDEX idx_tax_lines_order_id ON tax_lines(order_id);
CREATE INDEX idx_tax_lines_ticket_id ON tax_lines(ticket_id);
CREATE INDEX idx_notifications_user_id ON notifications(user_id);
CREATE INDEX idx_notifications_ticket_delivery_id ON notifications(ticket_delivery_id);
CREATE INDEX idx_email_jobs_notification_id ON email_jobs(notification_id);