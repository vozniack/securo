INSERT INTO systems (id, name, code, description)
VALUES ('4a7f9b34-2d36-40a8-a7c3-5e9b8c2a3f64', 'Customer Relationship Management', 'CRM_SYS',
        'System to manage customer relationships and interactions');

INSERT INTO privileges (id, name, code, description, index, system_id, parent_id)
VALUES ('4e7c9b64-2f61-4a8f-a7d3-5f9b2c8a3d94', 'Manage Customers', 'MANAGE_CUSTOMERS',
        'Parent privilege to manage customer records', 1, '4a7f9b34-2d36-40a8-a7c3-5e9b8c2a3f64', NULL),
       ('9c6e4b83-1d72-4c9f-a7d6-3e7b8f2c6d31', 'Add Customer', 'ADD_CUSTOMER', 'Privilege to add new customer records',
        1, '4a7f9b34-2d36-40a8-a7c3-5e9b8c2a3f64', '4e7c9b64-2f61-4a8f-a7d3-5f9b2c8a3d94'),
       ('2e8b6f51-4b38-48c9-a7d9-6f3b4e7c9d24', 'Update Customer', 'UPDATE_CUSTOMER',
        'Privilege to update customer records', 2, '4a7f9b34-2d36-40a8-a7c3-5e9b8c2a3f64',
        '4e7c9b64-2f61-4a8f-a7d3-5f9b2c8a3d94'),
       ('5a9f8d72-3b26-4f9c-a8d3-7e6c4b8f2a51', 'Delete Customer', 'DELETE_CUSTOMER',
        'Privilege to delete customer records', 3, '4a7f9b34-2d36-40a8-a7c3-5e9b8c2a3f64',
        '4e7c9b64-2f61-4a8f-a7d3-5f9b2c8a3d94'),
       ('8b6c7e41-5a39-40c7-a8d9-3e9c7b4f8a62', 'View Customer Data', 'VIEW_CUSTOMERS',
        'Privilege to view customer data', 4, '4a7f9b34-2d36-40a8-a7c3-5e9b8c2a3f64', NULL);
