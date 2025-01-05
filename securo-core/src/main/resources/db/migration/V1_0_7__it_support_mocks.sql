INSERT INTO systems (id, name, code, description)
VALUES ('6a4d9f26-95b4-42e8-86f6-48721dcae3db', 'IT Support Management System', 'IT_SUPPORT',
        'System to handle IT-related support tickets and incident management');

INSERT INTO roles (id, name, code, description, system_id)
VALUES ('8d2a4f67-9c84-4a26-b3d7-6c87a9e8f5a7', 'IT Support Specialist', 'IT_SPEC',
        'Specialist responsible for resolving IT-related issues', '6a4d9f26-95b4-42e8-86f6-48721dcae3db'),
       ('9e5b7c93-8d61-47e7-a9c4-6f92b7e14f2b', 'Employee', 'EMPLOYEE', 'Regular employee submitting support tickets',
        '6a4d9f26-95b4-42e8-86f6-48721dcae3db');

INSERT INTO privileges (id, name, code, description, index, system_id, parent_id)
VALUES ('5f2a8d73-6e91-4c4b-b8f5-1d6e7c49a3b4', 'Submit Support Ticket', 'SUBMIT_TICKET',
        'Privilege to submit IT support tickets', 1, '6a4d9f26-95b4-42e8-86f6-48721dcae3db', NULL),
       ('7e9a4b31-5f84-47a6-a2d9-6f2c7b94d6a8', 'Manage Support Tickets', 'MANAGE_TICKETS',
        'Parent privilege to manage support tickets', 2, '6a4d9f26-95b4-42e8-86f6-48721dcae3db', NULL),
       ('4c9b7e52-2d46-43f8-a9e7-1c7f8e5a2b36', 'Resolve Support Ticket', 'RESOLVE_TICKET',
        'Privilege to resolve submitted support tickets', 1, '6a4d9f26-95b4-42e8-86f6-48721dcae3db',
        '7e9a4b31-5f84-47a6-a2d9-6f2c7b94d6a8'),
       ('3a7e9c52-5b24-40a8-a2d7-6f2e9b4a2d87', 'Update Support Ticket', 'UPDATE_TICKET',
        'Privilege to update support ticket details', 2, '6a4d9f26-95b4-42e8-86f6-48721dcae3db',
        '7e9a4b31-5f84-47a6-a2d9-6f2c7b94d6a8'),
       ('6b8e2f79-1d34-4a57-a9b6-3e7f9b4c8d91', 'Delete Support Ticket', 'DELETE_TICKET',
        'Privilege to delete support tickets', 3, '6a4d9f26-95b4-42e8-86f6-48721dcae3db',
        '7e9a4b31-5f84-47a6-a2d9-6f2c7b94d6a8');
