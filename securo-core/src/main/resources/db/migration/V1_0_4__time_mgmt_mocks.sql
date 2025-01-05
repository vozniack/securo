INSERT INTO systems (id, name, code, description)
VALUES ('ba24fd1c-d8df-482d-92f8-1c17eb299eab', 'Working Time Management System', 'TIME_MGMT',
        'System to manage working hours and attendance');

INSERT INTO roles (id, name, code, description, system_id)
VALUES ('5d4b78e9-b8a6-486f-8b87-36a2be18700e', 'Time Administrator', 'TIME_ADMIN',
        'Administrator responsible for working time management', 'ba24fd1c-d8df-482d-92f8-1c17eb299eab'),
       ('4c21f9b7-d3f1-4723-9355-028aa6d981ab', 'Employee', 'EMPLOYEE', 'Regular employee with basic privileges',
        'ba24fd1c-d8df-482d-92f8-1c17eb299eab');

INSERT INTO privileges (id, name, code, description, index, system_id, parent_id)
VALUES ('2c64ef9d-95a6-44e8-80ae-dc43c0a6d4f5', 'View Working Time', 'VIEW_TIME',
        'Privilege to view working time entries', 1, 'ba24fd1c-d8df-482d-92f8-1c17eb299eab', NULL),
       ('cb98ed56-d4a9-4321-956d-63d34a6d8429', 'Manage Working Time', 'MANAGE_TIME',
        'Parent privilege to manage working time data', 2, 'ba24fd1c-d8df-482d-92f8-1c17eb299eab', NULL),
       ('3a42fb6e-7ba6-4ffb-871f-462d6eafbe34', 'Add Working Time Entry', 'ADD_TIME',
        'Privilege to add working time entries', 1, 'ba24fd1c-d8df-482d-92f8-1c17eb299eab',
        'cb98ed56-d4a9-4321-956d-63d34a6d8429'),
       ('e52d3fae-28f1-487d-a0c1-1f77e76e8237', 'Update Working Time Entry', 'UPDATE_TIME',
        'Privilege to update existing working time entries', 2, 'ba24fd1c-d8df-482d-92f8-1c17eb299eab',
        'cb98ed56-d4a9-4321-956d-63d34a6d8429'),
       ('81294d6c-a1d9-4d2b-9299-d3b41f238146', 'Delete Working Time Entry', 'DELETE_TIME',
        'Privilege to delete working time entries', 3, 'ba24fd1c-d8df-482d-92f8-1c17eb299eab',
        'cb98ed56-d4a9-4321-956d-63d34a6d8429');
