INSERT INTO systems (id, name, code, description)
VALUES ('8fa5b6c9-6d45-462a-8d27-9213c0f2e1ab', 'Employers Management System', 'EMP_MGMT',
        'System to manage employer details and hierarchies');

INSERT INTO roles (id, name, code, description, system_id)
VALUES ('fe1db8f2-72cc-4d47-899c-1b2b7d11a5b4', 'HR Manager', 'HR_MGR', 'Manager responsible for human resources',
        '8fa5b6c9-6d45-462a-8d27-9213c0f2e1ab'),
       ('cf9e83e7-2d7b-4971-95d1-b7acb1eaf06d', 'Employee', 'EMPLOYEE', 'Regular employee with limited access',
        '8fa5b6c9-6d45-462a-8d27-9213c0f2e1ab');

INSERT INTO privileges (id, name, code, description, index, system_id, parent_id)
VALUES ('2e4f5a21-8496-4a32-995c-4f30f1a2387c', 'View Employers', 'VIEW_EMP', 'Privilege to view employer details', 1,
        '8fa5b6c9-6d45-462a-8d27-9213c0f2e1ab', NULL),
       ('1a3f7d59-3bce-4a71-9861-c2d69c7ec6d5', 'Manage Employers', 'MANAGE_EMP',
        'Parent privilege to manage employer data', 2, '8fa5b6c9-6d45-462a-8d27-9213c0f2e1ab', NULL),
       ('453c6a19-56de-4a83-9d7d-7f2756f3a041', 'Add Employer', 'ADD_EMP', 'Privilege to add new employers', 1,
        '8fa5b6c9-6d45-462a-8d27-9213c0f2e1ab', '1a3f7d59-3bce-4a71-9861-c2d69c7ec6d5'),
       ('dc87f924-6afc-4d47-86e7-8db30c72cfb1', 'Update Employer', 'UPDATE_EMP',
        'Privilege to update employer information', 2, '8fa5b6c9-6d45-462a-8d27-9213c0f2e1ab',
        '1a3f7d59-3bce-4a71-9861-c2d69c7ec6d5'),
       ('7199fb19-3f3f-4854-b199-f22e5a813b84', 'Delete Employer', 'DELETE_EMP', 'Privilege to delete employer records',
        3, '8fa5b6c9-6d45-462a-8d27-9213c0f2e1ab', '1a3f7d59-3bce-4a71-9861-c2d69c7ec6d5');

INSERT INTO systems (id, name, code, description, parent_id)
VALUES ('3b7e9d56-2f84-467e-9c3a-8e2d9b4a1c98', 'Performance Review System', 'PERF_REVIEW',
        'System to manage employee performance reviews and appraisals', '8fa5b6c9-6d45-462a-8d27-9213c0f2e1ab');

INSERT INTO roles (id, name, code, description, system_id)
VALUES ('6c7d9e21-5b14-482b-a8c3-7f8e2d9b6a4c', 'HR Manager', 'HR_MGR',
        'Manager responsible for overseeing performance reviews', '3b7e9d56-2f84-467e-9c3a-8e2d9b4a1c98'),
       ('2a5f9c83-4e26-41d7-b9a2-6f3b7e92a8f4', 'Reviewer', 'REVIEWER', 'Employee responsible for conducting reviews',
        '3b7e9d56-2f84-467e-9c3a-8e2d9b4a1c98'),
       ('9f4c7b82-8d62-402b-a7e9-6c2e9b8f3d12', 'Employee', 'EMPLOYEE', 'Employee whose performance is reviewed',
        '3b7e9d56-2f84-467e-9c3a-8e2d9b4a1c98');

INSERT INTO privileges (id, name, code, description, index, system_id, parent_id)
VALUES ('5a7f9c43-6e21-4c3a-b8e2-1f9e7d2a4b98', 'Submit Self-Assessment', 'SUBMIT_SELF',
        'Privilege to submit a self-assessment', 1, '3b7e9d56-2f84-467e-9c3a-8e2d9b4a1c98', NULL),
       ('6d8e4b52-9c24-4f8b-a7d6-3e8b2c4a6d12', 'Manage Reviews', 'MANAGE_REVIEWS',
        'Parent privilege to manage reviews', 2, '3b7e9d56-2f84-467e-9c3a-8e2d9b4a1c98', NULL),
       ('8a5e3f61-5d46-40b7-a9c8-2f7c9b6e3d18', 'Create Review', 'CREATE_REVIEW',
        'Privilege to create new performance reviews', 1, '3b7e9d56-2f84-467e-9c3a-8e2d9b4a1c98',
        '6d8e4b52-9c24-4f8b-a7d6-3e8b2c4a6d12'),
       ('2f9d7b34-8e14-46b2-a7c6-5f8e3c2a7d92', 'Update Review', 'UPDATE_REVIEW',
        'Privilege to update existing performance reviews', 2, '3b7e9d56-2f84-467e-9c3a-8e2d9b4a1c98',
        '6d8e4b52-9c24-4f8b-a7d6-3e8b2c4a6d12'),
       ('7e9a6f53-1c48-4a7e-a8d3-6f2b7e4c9d13', 'Delete Review', 'DELETE_REVIEW',
        'Privilege to delete performance reviews', 3, '3b7e9d56-2f84-467e-9c3a-8e2d9b4a1c98',
        '6d8e4b52-9c24-4f8b-a7d6-3e8b2c4a6d12');
