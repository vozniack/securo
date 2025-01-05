INSERT INTO systems (id, name, code, description)
VALUES ('1d6f78a9-3c20-40b3-917a-b41efacb7c34', 'Document Management System', 'DOC_MGMT',
        'System to manage company documents');

INSERT INTO roles (id, name, code, description, system_id)
VALUES ('a3f2b50d-9817-4dbf-950e-78d96c4d3128', 'User', 'USER', 'Regular user of the document management system',
        '1d6f78a9-3c20-40b3-917a-b41efacb7c34'),
       ('d1f8476b-4745-4b2c-991e-c5ea24af6c96', 'Supervisor', 'SUPERVISOR',
        'Supervisor with extended privileges in the document management system',
        '1d6f78a9-3c20-40b3-917a-b41efacb7c34');

INSERT INTO privileges (id, name, code, description, index, system_id, parent_id)
VALUES ('c3a1fd64-8f26-420a-a3c1-98a4faef5c72', 'View Document', 'VIEW_DOC', 'Privilege to view documents', 1,
        '1d6f78a9-3c20-40b3-917a-b41efacb7c34', NULL),
       ('f12b4c78-a09e-4d1f-bac7-8e9c3074e2cb', 'Manage Documents', 'MANAGE_DOCS',
        'Parent privilege to manage documents', 2, '1d6f78a9-3c20-40b3-917a-b41efacb7c34', NULL),
       ('c12e5a8d-374f-4e80-b987-fda1679e5b28', 'Add Document', 'ADD_DOC', 'Privilege to add documents', 1,
        '1d6f78a9-3c20-40b3-917a-b41efacb7c34', 'f12b4c78-a09e-4d1f-bac7-8e9c3074e2cb'),
       ('ba96a423-445e-437a-8f23-57b9c9a23fb5', 'Update Document', 'UPDATE_DOC', 'Privilege to update documents', 2,
        '1d6f78a9-3c20-40b3-917a-b41efacb7c34', 'f12b4c78-a09e-4d1f-bac7-8e9c3074e2cb'),
       ('e15d6b48-7b24-4d09-809b-f761bc329a29', 'Delete Document', 'DELETE_DOC', 'Privilege to delete documents', 3,
        '1d6f78a9-3c20-40b3-917a-b41efacb7c34', 'f12b4c78-a09e-4d1f-bac7-8e9c3074e2cb'),
       ('d9af76b4-d5e4-4c8f-8991-c14f7b39aeba', 'Generate Document', 'GEN_DOC',
        'Parent privilege to generate documents', 3, '1d6f78a9-3c20-40b3-917a-b41efacb7c34', NULL),
       ('a24d7a1f-65e6-4b22-9f5b-d2f4e59c8cbb', 'Generate Draft Document', 'GEN_DRAFT_DOC',
        'Privilege to generate draft documents', 1, '1d6f78a9-3c20-40b3-917a-b41efacb7c34',
        'd9af76b4-d5e4-4c8f-8991-c14f7b39aeba'),
       ('b28e29cd-493f-4e5c-9d13-5e34f826aec1', 'Generate Formal Document', 'GEN_FORM_DOC',
        'Privilege to generate formal documents', 2, '1d6f78a9-3c20-40b3-917a-b41efacb7c34',
        'd9af76b4-d5e4-4c8f-8991-c14f7b39aeba');
