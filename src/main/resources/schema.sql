CREATE TABLE IF NOT EXISTS task (
  id SERIAL PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  description TEXT,
  status VARCHAR(20) NOT NULL,
  due_date_time TIMESTAMP
  );

