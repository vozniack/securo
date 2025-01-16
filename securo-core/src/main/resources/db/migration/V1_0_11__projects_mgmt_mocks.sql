INSERT INTO systems (id, name, code, description)
VALUES ('5b4a6f71-8d61-4b7c-a7e2-6f3c9e8b4d92', 'Project Management System', 'PROJ_MGMT',
        'System to track and manage projects across teams');

INSERT INTO privileges (id, name, code, description, index, system_id, parent_id)
VALUES ('7e6f9a43-2c51-4b7a-a8d6-3e7f4b9a8d52', 'Manage Projects', 'MANAGE_PROJECTS',
        'Parent privilege to manage projects', 1, '5b4a6f71-8d61-4b7c-a7e2-6f3c9e8b4d92', NULL),
       ('9d8f4b62-3c72-4a8c-a7d3-4f6b8e2c9a31', 'Create Project', 'CREATE_PROJECT', 'Privilege to create new projects',
        1, '5b4a6f71-8d61-4b7c-a7e2-6f3c9e8b4d92', '7e6f9a43-2c51-4b7a-a8d6-3e7f4b9a8d52'),
       ('2f9b7a83-4b61-4c8f-a9d2-5e4b7f9c8a24', 'Update Project', 'UPDATE_PROJECT',
        'Privilege to update existing projects', 2, '5b4a6f71-8d61-4b7c-a7e2-6f3c9e8b4d92',
        '7e6f9a43-2c51-4b7a-a8d6-3e7f4b9a8d52'),
       ('4b7f6e32-5a91-4d8b-a7d9-6e8c7f2b9a63', 'Delete Project', 'DELETE_PROJECT', 'Privilege to delete projects', 3,
        '5b4a6f71-8d61-4b7c-a7e2-6f3c9e8b4d92', '7e6f9a43-2c51-4b7a-a8d6-3e7f4b9a8d52'),
       ('7c8e9f41-3a82-4c9b-a6d7-4e8b3f6a7c51', 'View Projects', 'VIEW_PROJECTS', 'Privilege to view projects', 4,
        '5b4a6f71-8d61-4b7c-a7e2-6f3c9e8b4d92', NULL);
