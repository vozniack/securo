INSERT INTO systems (id, name, code, description)
VALUES ('7c8e12b9-d456-4423-8a99-276c60e8f92f', 'Onboarding System', 'ONBOARD',
        'System to manage employee training programs and development activities');

INSERT INTO roles (id, name, code, description, system_id)
VALUES ('5a6f9c48-8e87-432f-8c31-3d7867e7123f', 'Onboarding Supervisor', 'SUPERVISOR',
        'Supervisor responsible for overseeing training programs', '7c8e12b9-d456-4423-8a99-276c60e8f92f'),
       ('7f8d9b24-44a3-4c2a-b5f1-6e3479e8a4c7', 'Employee', 'EMPLOYEE',
        'Regular employee participating in training programs', '7c8e12b9-d456-4423-8a99-276c60e8f92f');

INSERT INTO privileges (id, name, code, description, index, system_id, parent_id)
VALUES ('1a5e8f29-9d75-4d27-b0c3-d6a26c4b79e3', 'View Training Programs', 'VIEW_TRAINING',
        'Privilege to view training programs and schedules', 1, '7c8e12b9-d456-4423-8a99-276c60e8f92f', NULL),
       ('b2f6e193-c8a4-4778-98c7-41389e6f8d2a', 'Manage Training Programs', 'MANAGE_TRAINING',
        'Parent privilege to manage training programs', 2, '7c8e12b9-d456-4423-8a99-276c60e8f92f', NULL),
       ('6d8c4e93-1e67-48b7-a5f7-3a81b7f4e8c2', 'Create Training Program', 'CREATE_TRAINING',
        'Privilege to create new training programs', 1, '7c8e12b9-d456-4423-8a99-276c60e8f92f',
        'b2f6e193-c8a4-4778-98c7-41389e6f8d2a'),
       ('9a5b3f61-7d39-49f8-a7c3-5e2687f47b12', 'Update Training Program', 'UPDATE_TRAINING',
        'Privilege to update existing training programs', 2, '7c8e12b9-d456-4423-8a99-276c60e8f92f',
        'b2f6e193-c8a4-4778-98c7-41389e6f8d2a'),
       ('2d7a6f82-5b14-452b-a8d9-9d3b6f1c8a12', 'Delete Training Program', 'DELETE_TRAINING',
        'Privilege to delete training programs', 3, '7c8e12b9-d456-4423-8a99-276c60e8f92f',
        'b2f6e193-c8a4-4778-98c7-41389e6f8d2a');
