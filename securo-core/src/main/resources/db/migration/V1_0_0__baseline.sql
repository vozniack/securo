/* Schema */

CREATE TABLE systems
(
    id          UUID         NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),

    scope       VARCHAR(255) NOT NULL             DEFAULT 'EXTERNAL',

    name        VARCHAR(255) NOT NULL UNIQUE,
    code        VARCHAR(8)   NOT NULL UNIQUE,

    description VARCHAR(1024),

    active      BOOLEAN                           DEFAULT TRUE,

    parent_id   UUID,

    created_at  TIMESTAMP    NOT NULL             DEFAULT now(),
    updated_at  TIMESTAMP    NOT NULL             DEFAULT now(),

    CONSTRAINT system_parent_fk FOREIGN KEY (parent_id) REFERENCES systems (id)
);


CREATE TABLE users
(
    id         UUID         NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),

    scope      VARCHAR(255) NOT NULL             DEFAULT 'EXTERNAL',

    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255),

    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,

    language   VARCHAR(255) NOT NULL             DEFAULT 'en_EN',

    active     BOOLEAN                           DEFAULT TRUE
);

CREATE TABLE user_systems
(
    user_id         UUID NOT NULL,
    system_id UUID NOT NULL,

    PRIMARY KEY (user_id, system_id),

    CONSTRAINT user_system_user_fk FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT user_system_system_fk FOREIGN KEY (system_id) REFERENCES systems (id)
);

/* Values */

INSERT INTO systems (id, scope, name, code)
VALUES ('3f9b1f2c-fa15-4cd0-94ab-e5a9588d42d5', 'INTERNAL', 'Securo', 'SEC');

INSERT INTO users (id, scope, email, password, first_name, last_name)
VALUES ('055cb1f2-162a-4f14-a445-883539a60002', 'INTERNAL', 'admin@securo.com',
        '$2y$10$8K1qTenpN7PtyCB4KMkCdejBfGxOczmYM1LP9nbJdRzSPyijoLtce', 'Admin', 'Admin');

INSERT INTO user_systems(user_id, system_id)
VALUES ('055cb1f2-162a-4f14-a445-883539a60002', '3f9b1f2c-fa15-4cd0-94ab-e5a9588d42d5');
