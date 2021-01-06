INSERT INTO permission
set values
-- role permission
       (1, 'View All Roles', 'VIEW_ROLES'),
       (2, 'View A Specific Role', 'VIEW_ROLE'),
       (3, 'Create a  Role', 'CREATE_ROLE'),
       (4, 'Update a  Role', 'UPDATE_ROLE'),
       (5, 'Delete a  Role', 'DELETE_ROLE'),
--        Subscription Permission
       (6, 'View All Subscriber', 'VIEW_SUBSCRIBER'),
--        user permission
       (7, 'View All Users', 'VIEW_USERS'),
       (8, 'View A Specific User', 'VIEW_USER'),
       (9, 'Update User Status', 'UPDATE_USER_STATUS'),
       (10, 'Update A User', 'UPDATE_USER'),
       (11, 'Delete A User', 'DELETE_USER'),
       (12, 'Create A User', 'CREATE_USER'),
--        Post Permission
       (13, 'Create a Post', 'CREATE_POST'),
       (14, 'View All Post Belong To Currently Login User', 'VIEW_USER_POST'),
       (15, 'View A Specific Post Belong To Currently Login User', 'VIEW_POST'),