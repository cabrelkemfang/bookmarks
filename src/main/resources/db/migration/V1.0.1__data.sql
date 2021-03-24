INSERT INTO permission
VALUES
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
(11, 'Get The Summary Report on Users Activity', 'SUMMARY_REPORTS'),
(12, 'Create A User', 'CREATE_USER'),
--        Post Permission
(13, 'Create a Post', 'CREATE_POST'),
(14, 'View All Post Belong To Currently Login User', 'VIEW_USER_POST'),
(15, 'View A Specific Post Belong To Currently Login User', 'VIEW_POST'),
(16, 'Delete Post ', 'DELETE_POST'),
(17, 'Update Post ', 'UPDATE_POST'),
(18, 'Get Summary Post Reports ', 'SUMMARY_POST_REPORTS'),
(19, 'View All Post By Amin Either Private Or Public ', 'ADMIN_VIEW_POST'),
(20, 'View All Post By Amin Either Private Or Public ', 'SEARCH_POST'),
-- Category Permission
(21, 'View All Categories', 'VIEW_CATEGORIES'),
(22, 'View a Specific Category', 'VIEW_CATEGORY'),
(23, 'Create A Category', 'CREATE_CATEGORY'),
(24, 'Delete A Category', 'DELETE_CATEGORY'),
(25, 'Update A Category', 'UPDATE_CATEGORY'),
(26, 'Search A Category', 'SEARCH_CATEGORY'),
(27, 'Create Admin User', 'CREATE_ADMIN');


INSERT INTO role
VALUES (1, current_timestamp, current_timestamp, FALSE, "role_admin", 'SYSTEM_USER', 'SYSTEM_USER'),
       (2, current_timestamp, current_timestamp, FALSE, "role_user", 'SYSTEM_USER', 'SYSTEM_USER');

INSERT INTO role_permission
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (1, 7),
       (1, 8),
       (1, 9),
       (1, 10),
       (1, 11),
       (1, 12),
       (1, 13),
       (1, 14),
       (1, 15),
       (1, 16),
       (1, 17),
       (1, 18),
       (1, 19),
       (1, 20),
       (1, 21),
       (1, 22),
       (1, 23),
       (1, 24),
       (1, 25),
       (1, 26),
       (1, 27),
       (2, 13),
       (2, 14),
       (2, 15),
       (2, 16),
       (2, 17),
       (2, 18),
       (2, 19),
       (2, 20);

INSERT INTO hibernate_sequence
    VALUE (10);