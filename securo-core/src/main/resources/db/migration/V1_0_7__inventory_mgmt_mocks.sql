INSERT INTO systems (id, name, code, description)
VALUES ('7e4d6a93-8d6f-47c2-a7b4-6f8d7e92a4c3', 'Inventory Management System', 'INV_MGMT',
        'System to track and manage company inventory and stock');

INSERT INTO privileges (id, name, code, description, index, system_id, parent_id)
VALUES ('5d7b9f64-3e41-4b8c-a9e2-6f7c2d4a9b32', 'View Inventory', 'VIEW_INV', 'Privilege to view inventory details', 1,
        '7e4d6a93-8d6f-47c2-a7b4-6f8d7e92a4c3', NULL),
       ('7a4d3b92-8e61-41b9-a7f6-5f2c9b6e4d13', 'Manage Inventory', 'MANAGE_INV',
        'Parent privilege to manage inventory', 2, '7e4d6a93-8d6f-47c2-a7b4-6f8d7e92a4c3', NULL),
       ('9b3a8f24-7e56-42d9-a5c7-3e9c8b2d7a94', 'Add Inventory Item', 'ADD_INV_ITEM',
        'Privilege to add new inventory items', 1, '7e4d6a93-8d6f-47c2-a7b4-6f8d7e92a4c3',
        '7a4d3b92-8e61-41b9-a7f6-5f2c9b6e4d13'),
       ('6c8e3f71-5b49-40c8-a9d2-7f9b4e3c6d81', 'Update Inventory Item', 'UPDATE_INV_ITEM',
        'Privilege to update existing inventory items', 2, '7e4d6a93-8d6f-47c2-a7b4-6f8d7e92a4c3',
        '7a4d3b92-8e61-41b9-a7f6-5f2c9b6e4d13'),
       ('3f6d8b42-1e28-4c9a-a7f9-5e8b9c2a3d64', 'Delete Inventory Item', 'DELETE_INV_ITEM',
        'Privilege to delete inventory items', 3, '7e4d6a93-8d6f-47c2-a7b4-6f8d7e92a4c3',
        '7a4d3b92-8e61-41b9-a7f6-5f2c9b6e4d13');
