-- ===================== USERS =====================

CREATE TABLE users (
  id UUID PRIMARY KEY,
  type VARCHAR(50) NOT NULL,
  name TEXT NOT NULL,
  surname TEXT NOT NULL,
  email TEXT UNIQUE NOT NULL,
  password_hash TEXT NOT NULL
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
  category VARCHAR(50),
  venue_id UUID NOT NULL,
  FOREIGN KEY (venue_id) REFERENCES venues(id)
);

-- ===================== TICKET TYPES =====================

CREATE TABLE ticket_types (
  id UUID PRIMARY KEY,
  event_id UUID NOT NULL,
  name TEXT NOT NULL,
  type VARCHAR(20) NOT NULL, -- STANDING / SEATED
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
  status VARCHAR(20) NOT NULL, -- AVAILABLE / RESERVED / SOLD
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
  status VARCHAR(50),
  created_at TIMESTAMP NOT NULL,
  country TEXT,
  region TEXT,
  FOREIGN KEY (user_id) REFERENCES users(id)
);

-- ===================== PAYMENTS =====================

CREATE TABLE payments (
  id UUID PRIMARY KEY,
  order_id UUID UNIQUE NOT NULL,
  amount DECIMAL NOT NULL,
  method VARCHAR(50),
  status VARCHAR(50),
  paid_at TIMESTAMP,
  FOREIGN KEY (order_id) REFERENCES orders(id)
);

-- ===================== BULK PURCHASE =====================

CREATE TABLE bulk_purchases (
  id UUID PRIMARY KEY,
  order_id UUID UNIQUE NOT NULL,
  type VARCHAR(20), -- GROUP / CORPORATE
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
  status VARCHAR(50),
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
  status VARCHAR(50),
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
  status VARCHAR(50),
  FOREIGN KEY (ticket_id) REFERENCES tickets(id)
);

-- ===================== TICKET ASSIGNMENT =====================

CREATE TABLE ticket_assignments (
  id UUID PRIMARY KEY,
  ticket_id UUID NOT NULL,
  email TEXT NOT NULL,
  user_id UUID,
  status VARCHAR(50),
  sent_at TIMESTAMP,
  FOREIGN KEY (ticket_id) REFERENCES tickets(id),
  FOREIGN KEY (user_id) REFERENCES users(id)
);

-- ===================== TAX =====================

CREATE TABLE tax_rules (
  id UUID PRIMARY KEY,
  name TEXT,
  rate DECIMAL,
  type VARCHAR(50),
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

-- ===================== EMAIL JOBS =====================

CREATE TABLE email_jobs (
  id UUID PRIMARY KEY,
  ticket_assignment_id UUID,
  notification_id UUID,
  email_type VARCHAR(50),
  recipient_email TEXT NOT NULL,
  payload JSONB,
  job_status VARCHAR(50),
  attempts INT DEFAULT 0,
  max_attempts INT DEFAULT 3,
  scheduled_at TIMESTAMP,
  sent_at TIMESTAMP,
  FOREIGN KEY (notification_id) REFERENCES notifications(id),
  FOREIGN KEY (ticket_assignment_id) REFERENCES ticket_assignments(id)
);

-- ===================== NOTIFICATIONS =====================

CREATE TABLE notifications (
  id UUID PRIMARY KEY,
  user_id UUID NOT NULL,
  type VARCHAR(50),
  title TEXT,
  message TEXT,
  scheduled_at TIMESTAMP,
  status VARCHAR(50),
  FOREIGN KEY (user_id) REFERENCES users(id)
);