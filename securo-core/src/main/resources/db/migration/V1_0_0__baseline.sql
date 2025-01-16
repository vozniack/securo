/* Schema */

CREATE TABLE users
(
    id            UUID         NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),

    scope         VARCHAR(255) NOT NULL             DEFAULT 'EXTERNAL',

    email         VARCHAR(255) NOT NULL UNIQUE,
    password      VARCHAR(255),

    phone_prefix  VARCHAR(4),
    phone_number  VARCHAR(16),

    first_name    VARCHAR(255) NOT NULL,
    last_name     VARCHAR(255) NOT NULL,

    date_of_birth DATE         NOT NULL,

    language      VARCHAR(255) NOT NULL             DEFAULT 'en_EN',

    country       VARCHAR(255),
    city          VARCHAR(255),
    zip           VARCHAR(16),
    street        VARCHAR(255),
    house         VARCHAR(255),

    active        BOOLEAN      NOT NULL             DEFAULT TRUE,

    created_at    TIMESTAMP    NOT NULL             DEFAULT now(),
    updated_at    TIMESTAMP
);

CREATE TABLE teams
(
    id          UUID         NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),

    scope       VARCHAR(255) NOT NULL             DEFAULT 'EXTERNAL',

    name        VARCHAR(255) NOT NULL UNIQUE,
    code        VARCHAR(16)  NOT NULL UNIQUE,

    description VARCHAR(1024),

    created_at  TIMESTAMP    NOT NULL             DEFAULT now(),
    updated_at  TIMESTAMP
);

CREATE TABLE systems
(
    id          UUID         NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),

    scope       VARCHAR(255) NOT NULL             DEFAULT 'EXTERNAL',

    name        VARCHAR(255) NOT NULL UNIQUE,
    code        VARCHAR(16)  NOT NULL UNIQUE,

    description VARCHAR(1024),
    icon        VARCHAR(64),

    active      BOOLEAN      NOT NULL             DEFAULT TRUE,

    parent_id   UUID,

    created_at  TIMESTAMP    NOT NULL             DEFAULT now(),
    updated_at  TIMESTAMP,

    CONSTRAINT system_parent_fk FOREIGN KEY (parent_id) REFERENCES systems (id)
);

CREATE TABLE roles
(
    id          UUID         NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),

    scope       VARCHAR(255) NOT NULL             DEFAULT 'EXTERNAL',

    name        VARCHAR(255) NOT NULL,
    code        VARCHAR(16)  NOT NULL,
    description VARCHAR(1024),

    team_id     UUID         NOT NULL,

    active      BOOLEAN      NOT NULL             DEFAULT TRUE,

    created_at  TIMESTAMP    NOT NULL             DEFAULT now(),
    updated_at  TIMESTAMP,

    CONSTRAINT role_name_team_unique UNIQUE (name, team_id),
    CONSTRAINT role_code_team_unique UNIQUE (code, team_id),
    CONSTRAINT role_team_fk FOREIGN KEY (team_id) REFERENCES teams (id)
);

CREATE TABLE privileges
(
    id          UUID         NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),

    scope       VARCHAR(255) NOT NULL             DEFAULT 'EXTERNAL',

    name        VARCHAR(255) NOT NULL,
    code        VARCHAR(255) NOT NULL,
    description VARCHAR(1024),

    index       INT          NOT NULL,

    system_id   UUID         NOT NULL,
    parent_id   UUID,

    created_at  TIMESTAMP    NOT NULL             DEFAULT now(),
    updated_at  TIMESTAMP,

    CONSTRAINT privilege_name_system_unique UNIQUE (name, system_id),
    CONSTRAINT privilege_code_system_unique UNIQUE (code, system_id),
    CONSTRAINT privilege_index_parent_system_unique UNIQUE (index, parent_id, system_id),
    CONSTRAINT privilege_system_fk FOREIGN KEY (system_id) REFERENCES systems (id),
    CONSTRAINT privilege_parent_fk FOREIGN KEY (parent_id) REFERENCES privileges (id)
);

CREATE TABLE user_systems
(
    user_id   UUID NOT NULL,
    system_id UUID NOT NULL,

    PRIMARY KEY (user_id, system_id),

    CONSTRAINT user_system_user_fk FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT user_system_system_fk FOREIGN KEY (system_id) REFERENCES systems (id)
);

CREATE TABLE user_roles
(
    user_id UUID NOT NULL,
    role_id UUID NOT NULL,

    PRIMARY KEY (user_id, role_id),

    CONSTRAINT user_role_user_fk FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT user_role_role_fk FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE user_privileges
(
    id           UUID      NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),

    user_id      UUID      NOT NULL,
    privilege_id UUID      NOT NULL,

    excluded     BOOLEAN                        DEFAULT FALSE,

    created_at   TIMESTAMP NOT NULL             DEFAULT now(),
    updated_at   TIMESTAMP NOT NULL             DEFAULT now(),

    CONSTRAINT user_privilege_unique UNIQUE (user_id, privilege_id),
    CONSTRAINT user_privilege_user_fk FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT user_privilege_privilege_fk FOREIGN KEY (privilege_id) REFERENCES privileges (id)
);

CREATE TABLE role_privileges
(
    id           UUID      NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),

    role_id      UUID      NOT NULL,
    privilege_id UUID      NOT NULL,

    excluded     BOOLEAN                        DEFAULT FALSE,

    created_at   TIMESTAMP NOT NULL             DEFAULT now(),
    updated_at   TIMESTAMP NOT NULL             DEFAULT now(),

    CONSTRAINT role_privilege_unique UNIQUE (role_id, privilege_id),
    CONSTRAINT role_privilege_role_fk FOREIGN KEY (role_id) REFERENCES roles (id),
    CONSTRAINT role_privilege_privilege_fk FOREIGN KEY (privilege_id) REFERENCES privileges (id)
);

/* Values */

INSERT INTO users (id, scope, email, password, first_name, last_name, date_of_birth, language)
VALUES ('055cb1f2-162a-4f14-a445-883539a60002', 'INTERNAL', 'admin@securo.com',
        '$2y$10$8K1qTenpN7PtyCB4KMkCdejBfGxOczmYM1LP9nbJdRzSPyijoLtce', 'Admin', 'Admin', '1995-08-15', 'en_US');

INSERT INTO systems (id, scope, name, code, description)
VALUES ('3f9b1f2c-fa15-4cd0-94ab-e5a9588d42d5', 'INTERNAL', 'Securo', 'SEC', 'Internal Securo System');

INSERT INTO teams (id, scope, name, code, description)
VALUES ('6bdd108b-10b5-442c-8d51-abf6391dc8d5', 'INTERNAL', 'Securo Team', 'SEC', 'Internal Securo Team');

INSERT INTO roles (id, scope, name, code, description, team_id)
VALUES ('98fa7b2c-6caa-4852-b632-e5c05b507021', 'INTERNAL', 'Admin', 'ADMIN',
        'Securo administrator',
        '6bdd108b-10b5-442c-8d51-abf6391dc8d5'),
       ('451adc34-f819-46d5-9e35-719ee343fb73', 'INTERNAL', 'User', 'USER', 'Securo user',
        '6bdd108b-10b5-442c-8d51-abf6391dc8d5');

INSERT INTO privileges (id, scope, name, code, description, index, system_id, parent_id)
VALUES ('39798f2b-df6f-4239-9736-138b245b151c', 'INTERNAL', 'Login', 'LOGIN',
        'Right to access the application', 0, '3f9b1f2c-fa15-4cd0-94ab-e5a9588d42d5', null),


       ('9d31e72b-2d3a-4984-8e19-fec0b67857ef', 'INTERNAL', 'Manage account', 'MANAGE_ACCOUNT',
        'User account management privileges set', 1, '3f9b1f2c-fa15-4cd0-94ab-e5a9588d42d5', null),

       ('150e6506-8cc1-4fb3-b700-af236b23d5e9', 'INTERNAL', 'Update email', 'UPDATE_EMAIL',
        'Right to update own email', 0, '3f9b1f2c-fa15-4cd0-94ab-e5a9588d42d5',
        '9d31e72b-2d3a-4984-8e19-fec0b67857ef'),

       ('fbf61743-c54b-446c-b5ef-addf083cd3c6', 'INTERNAL', 'Update password', 'UPDATE_PASSWORD',
        'Right to update own password', 1, '3f9b1f2c-fa15-4cd0-94ab-e5a9588d42d5',
        '9d31e72b-2d3a-4984-8e19-fec0b67857ef'),


       ('9b62cadc-bf60-4ea0-b239-7aae1ad7b9d7', 'INTERNAL', 'Manage systems', 'MANAGE_SYSTEMS',
        'Systems management privilege set', 2, '3f9b1f2c-fa15-4cd0-94ab-e5a9588d42d5', null),

       ('6393f416-38a7-4f62-94cc-b3f884d69358', 'INTERNAL', 'Read system', 'READ_SYSTEM',
        'Right to display system', 0, '3f9b1f2c-fa15-4cd0-94ab-e5a9588d42d5',
        '9b62cadc-bf60-4ea0-b239-7aae1ad7b9d7'),

       ('8c6e5b1a-b901-4046-a224-e60de272a7bd', 'INTERNAL', 'Create system', 'CREATE_SYSTEM',
        'Right to create system', 1, '3f9b1f2c-fa15-4cd0-94ab-e5a9588d42d5',
        '9b62cadc-bf60-4ea0-b239-7aae1ad7b9d7'),

       ('2acf3d11-656b-4cf1-9f38-1b067c744c98', 'INTERNAL', 'Update system', 'UPDATE_SYSTEM',
        'Right to update system', 2, '3f9b1f2c-fa15-4cd0-94ab-e5a9588d42d5',
        '9b62cadc-bf60-4ea0-b239-7aae1ad7b9d7'),

       ('b5b29a88-73ff-456b-a98d-9ae5dd48f2e9', 'INTERNAL', 'Delete system', 'DELETE_SYSTEM',
        'Right to delete system', 3, '3f9b1f2c-fa15-4cd0-94ab-e5a9588d42d5',
        '9b62cadc-bf60-4ea0-b239-7aae1ad7b9d7'),


       ('80fbf8a7-efb6-44f1-b8fb-2db9cdbdd80c', 'INTERNAL', 'Manage users', 'MANAGE_USERS',
        'User management privilege set', 3, '3f9b1f2c-fa15-4cd0-94ab-e5a9588d42d5', null),

       ('b52cebd3-7a86-4f08-a87a-8e13ba29eec0', 'INTERNAL', 'Read user', 'READ_USER',
        'Right to display users', 0, '3f9b1f2c-fa15-4cd0-94ab-e5a9588d42d5', '80fbf8a7-efb6-44f1-b8fb-2db9cdbdd80c'),

       ('b5ff26bc-7ff6-4505-800d-10743b839d5e', 'INTERNAL', 'Create user', 'CREATE_USER',
        'Right to create users', 1, '3f9b1f2c-fa15-4cd0-94ab-e5a9588d42d5', '80fbf8a7-efb6-44f1-b8fb-2db9cdbdd80c'),

       ('1e7f623e-1dc3-4a43-974f-3888bfc6c371', 'INTERNAL', 'Update user', 'UPDATE_USER',
        'Right to update users', 2, '3f9b1f2c-fa15-4cd0-94ab-e5a9588d42d5', '80fbf8a7-efb6-44f1-b8fb-2db9cdbdd80c'),

       ('81a06d5c-8e79-436c-80a7-aa4605c1eb2e', 'INTERNAL', 'Delete user', 'DELETE_USER',
        'Right to delete users', 3, '3f9b1f2c-fa15-4cd0-94ab-e5a9588d42d5', '80fbf8a7-efb6-44f1-b8fb-2db9cdbdd80c'),


       ('4b2e19fa-79db-44b9-8bbf-6561a47b3cb9', 'INTERNAL', 'Manage roles', 'MANAGE_ROLES',
        'Roles management privilege set', 4, '3f9b1f2c-fa15-4cd0-94ab-e5a9588d42d5', null),

       ('7eb13577-06b2-4b88-ad78-f1b99f951c6e', 'INTERNAL', 'Read roles', 'READ_ROLE',
        'Right to display roles', 0, '3f9b1f2c-fa15-4cd0-94ab-e5a9588d42d5', '4b2e19fa-79db-44b9-8bbf-6561a47b3cb9'),

       ('0d4e60b3-48f1-470c-94b7-167edd97bb1b', 'INTERNAL', 'Create role', 'CREATE_ROLE',
        'Right to create roles', 1, '3f9b1f2c-fa15-4cd0-94ab-e5a9588d42d5', '4b2e19fa-79db-44b9-8bbf-6561a47b3cb9'),

       ('fe50c422-526b-4f7c-9482-8401cc704d25', 'INTERNAL', 'Update role', 'UPDATE_ROLE',
        'Right to update roles', 2, '3f9b1f2c-fa15-4cd0-94ab-e5a9588d42d5', '4b2e19fa-79db-44b9-8bbf-6561a47b3cb9'),

       ('59ce3239-abe4-4722-b408-6b01f4aca184', 'INTERNAL', 'Delete role', 'DELETE_ROLE',
        'Right to delete roles', 3, '3f9b1f2c-fa15-4cd0-94ab-e5a9588d42d5', '4b2e19fa-79db-44b9-8bbf-6561a47b3cb9'),


       ('32cb0e0a-4368-447c-ad00-2affe47e7d1d', 'INTERNAL', 'Manage privileges', 'MANAGE_PRIVILEGES',
        'Privilege management privilege set', 5, '3f9b1f2c-fa15-4cd0-94ab-e5a9588d42d5', null),

       ('082493be-3d0c-4f12-8554-2e7cb87d76f9', 'INTERNAL', 'Read privilege', 'READ_PRIVILEGE',
        'Right to display privileges', 0, '3f9b1f2c-fa15-4cd0-94ab-e5a9588d42d5',
        '32cb0e0a-4368-447c-ad00-2affe47e7d1d'),

       ('e12be999-5734-4b8f-80d0-3a83a54d6bc3', 'INTERNAL', 'Create privilege', 'CREATE_PRIVILEGE',
        'Right to create privileges', 1, '3f9b1f2c-fa15-4cd0-94ab-e5a9588d42d5',
        '32cb0e0a-4368-447c-ad00-2affe47e7d1d'),

       ('33989dd4-708a-4717-a294-476c486ffdb8', 'INTERNAL', 'Update privilege', 'UPDATE_PRIVILEGE',
        'Right to update privileges', 2, '3f9b1f2c-fa15-4cd0-94ab-e5a9588d42d5',
        '32cb0e0a-4368-447c-ad00-2affe47e7d1d'),

       ('2884d2ef-9b95-4206-a0c0-5a9cece202cb', 'INTERNAL', 'Delete privilege', 'DELETE_PRIVILEGE',
        'Right to delete privileges', 3, '3f9b1f2c-fa15-4cd0-94ab-e5a9588d42d5',
        '32cb0e0a-4368-447c-ad00-2affe47e7d1d');

INSERT INTO user_systems(user_id, system_id)
VALUES ('055cb1f2-162a-4f14-a445-883539a60002', '3f9b1f2c-fa15-4cd0-94ab-e5a9588d42d5');

INSERT INTO user_roles (user_id, role_id)
VALUES ('055cb1f2-162a-4f14-a445-883539a60002', '98fa7b2c-6caa-4852-b632-e5c05b507021');

INSERT INTO user_privileges (user_id, privilege_id)
VALUES ('055cb1f2-162a-4f14-a445-883539a60002', '39798f2b-df6f-4239-9736-138b245b151c');

INSERT INTO role_privileges (role_id, privilege_id)
VALUES ('98fa7b2c-6caa-4852-b632-e5c05b507021', '9d31e72b-2d3a-4984-8e19-fec0b67857ef'),
       ('98fa7b2c-6caa-4852-b632-e5c05b507021', '9b62cadc-bf60-4ea0-b239-7aae1ad7b9d7'),
       ('98fa7b2c-6caa-4852-b632-e5c05b507021', '80fbf8a7-efb6-44f1-b8fb-2db9cdbdd80c'),
       ('98fa7b2c-6caa-4852-b632-e5c05b507021', '4b2e19fa-79db-44b9-8bbf-6561a47b3cb9'),
       ('98fa7b2c-6caa-4852-b632-e5c05b507021', '32cb0e0a-4368-447c-ad00-2affe47e7d1d'),
       ('451adc34-f819-46d5-9e35-719ee343fb73', '9d31e72b-2d3a-4984-8e19-fec0b67857ef'),
       ('451adc34-f819-46d5-9e35-719ee343fb73', '9b62cadc-bf60-4ea0-b239-7aae1ad7b9d7'),
       ('451adc34-f819-46d5-9e35-719ee343fb73', '80fbf8a7-efb6-44f1-b8fb-2db9cdbdd80c'),
       ('451adc34-f819-46d5-9e35-719ee343fb73', '4b2e19fa-79db-44b9-8bbf-6561a47b3cb9'),
       ('451adc34-f819-46d5-9e35-719ee343fb73', '32cb0e0a-4368-447c-ad00-2affe47e7d1d');
