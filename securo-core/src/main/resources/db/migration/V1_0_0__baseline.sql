/* Schema */

CREATE TABLE users
(
    id         UUID         NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),

    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255),

    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,

    language   VARCHAR(255) NOT NULL             DEFAULT 'en_EN',

    active     BOOLEAN                           DEFAULT TRUE
);

/* Values */

INSERT INTO users (id, email, password, first_name, last_name)
VALUES ('055cb1f2-162a-4f14-a445-883539a60002', 'john.doe@securo.com',
        'Admin123!', 'John', 'Doe');