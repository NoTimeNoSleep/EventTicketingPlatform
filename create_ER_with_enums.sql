-- Specifically designed for PostgreSQL

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
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (role_id) REFERENCES roles(id)
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
  FOREIGN KEY (venue_id) REFERENCES venues(id)
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
  price DECIMAL NOT NULL,
  capacity INT,
  FOREIGN KEY (event_id) REFERENCES events(id)
);

-- ===================== EVENT SEATS (SEATED INVENTORY) =====================

CREATE TABLE event_seats (
  id UUID PRIMARY KEY,
  event_id UUID NOT NULL,
  seat_id UUID NOT NULL,
  ticket_type_id UUID NOT NULL,
  status seat_status_enum NOT NULL,
  reserved_until TIMESTAMP,
  FOREIGN KEY (event_id) REFERENCES events(id),
  FOREIGN KEY (seat_id) REFERENCES seats(id),
  FOREIGN KEY (ticket_type_id) REFERENCES ticket_types(id),
  UNIQUE (event_id, seat_id)
);

-- ===================== ORDERS =====================

CREATE TABLE orders (
  id UUID PRIMARY KEY,
  user_id UUID NOT NULL,
  subtotal DECIMAL NOT NULL,
  tax_total DECIMAL NOT NULL,
  total_amount DECIMAL NOT NULL,
  status order_status_enum,
  created_at TIMESTAMP NOT NULL,
  country TEXT,
  region TEXT,
  FOREIGN KEY (user_id) REFERENCES users(id)
);

-- ===================== PAYMENTS =====================

CREATE TABLE payments (
  id UUID PRIMARY KEY,
  order_id UUID NOT NULL,
  amount DECIMAL NOT NULL,
  method payment_method_enum,
  status payment_status_enum,
  paid_at TIMESTAMP,
  FOREIGN KEY (order_id) REFERENCES orders(id)
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
  FOREIGN KEY (order_id) REFERENCES orders(id)
);

-- ===================== INVOICES =====================

CREATE TABLE invoices (
  id UUID PRIMARY KEY,
  order_id UUID UNIQUE NOT NULL,
  company_name TEXT,
  tax_id TEXT,
  billing_address TEXT,
  subtotal DECIMAL,
  tax_total DECIMAL,
  total_amount DECIMAL,
  status invoice_status_enum,
  issued_at TIMESTAMP,
  FOREIGN KEY (order_id) REFERENCES orders(id)
);

CREATE TABLE invoice_tax_lines (
  id UUID PRIMARY KEY,
  invoice_id UUID NOT NULL,
  name TEXT,
  rate DECIMAL,
  amount DECIMAL,
  FOREIGN KEY (invoice_id) REFERENCES invoices(id)
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
  FOREIGN KEY (order_id) REFERENCES orders(id)
);

-- ===================== QR CODES =====================

CREATE TABLE qr_codes (
  id UUID PRIMARY KEY,
  ticket_id UUID UNIQUE NOT NULL,
  code_value TEXT NOT NULL,
  generated_at TIMESTAMP,
  status qr_code_status_enum,
  FOREIGN KEY (ticket_id) REFERENCES tickets(id)
);

-- ===================== TICKET DELIVERY =====================

CREATE TABLE ticket_deliveries (
  id UUID PRIMARY KEY,
  ticket_id UUID UNIQUE NOT NULL,
  email TEXT NOT NULL,
  user_id UUID,
  status assignment_status_enum,
  sent_at TIMESTAMP,
  FOREIGN KEY (ticket_id) REFERENCES tickets(id),
  FOREIGN KEY (user_id) REFERENCES users(id)
);

-- ===================== TAX =====================

CREATE TABLE tax_rules (
  id UUID PRIMARY KEY,
  name TEXT,
  rate DECIMAL,
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
  rate DECIMAL,
  amount DECIMAL,
  FOREIGN KEY (order_id) REFERENCES orders(id),
  FOREIGN KEY (ticket_id) REFERENCES tickets(id)
);

-- ===================== NOTIFICATIONS =====================

CREATE TABLE notifications (
  id UUID PRIMARY KEY,
  user_id UUID,
  ticket_delivery_id UUID,
  type notification_type_enum,
  title TEXT,
  message TEXT,
  scheduled_at TIMESTAMP,
  status notification_status_enum,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (ticket_delivery_id) REFERENCES ticket_deliveries(id),
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
  scheduled_at TIMESTAMP,
  sent_at TIMESTAMP,
  FOREIGN KEY (notification_id) REFERENCES notifications(id),
  CHECK (attempts >= 0),
  CHECK (max_attempts >= 1),
  CHECK (attempts <= max_attempts)
);