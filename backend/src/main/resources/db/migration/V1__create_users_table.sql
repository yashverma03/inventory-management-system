-- Create user_role enum type
START TRANSACTION;

CREATE TYPE user_role_enum AS ENUM ('admin', 'manager');

CREATE TABLE IF NOT EXISTS users (
  id BIGSERIAL PRIMARY KEY,
  email VARCHAR(255) NOT NULL UNIQUE,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255),
  password VARCHAR(255) NOT NULL,
  role user_role_enum NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
  deleted_at TIMESTAMP
);

COMMIT;

/*
Down migration:

DROP TABLE IF EXISTS users;
DROP TYPE IF EXISTS user_role_enum;
*/
