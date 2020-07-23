REPLACE INTO role (id, description, name) VALUES (1, 'Super Admin role', 'ROLE_SUPER_ADMIN');
REPLACE INTO role (id, description, name) VALUES (2, 'Admin role', 'ROLE_ADMIN');
REPLACE INTO role (id, description, name) VALUES (3, 'Moderator role', 'ROLE_MODERATOR');
REPLACE INTO role (id, description, name) VALUES (4, 'User role', 'ROLE_USER');

REPLACE INTO `user` (id, username, password, email) VALUES (1, 'superadmin', '$2a$10$PIbFeyK49cFEbsaUFdJf6eCjbnfXKw2P6onZKnbV/JgVRQNlp94xa', 'superadmin@gmail.com');
REPLACE INTO `user` (id, username, password, email) VALUES (2, 'admin', '$2a$10$PIbFeyK49cFEbsaUFdJf6eCjbnfXKw2P6onZKnbV/JgVRQNlp94xa', 'admin@gmail.com');
REPLACE INTO `user` (id, username, password, email) VALUES (3, 'mod', '$2a$10$PIbFeyK49cFEbsaUFdJf6eCjbnfXKw2P6onZKnbV/JgVRQNlp94xa', 'mod@gmail.com');
REPLACE INTO `user` (id, username, password, email) VALUES (4, 'user', '$2a$10$PIbFeyK49cFEbsaUFdJf6eCjbnfXKw2P6onZKnbV/JgVRQNlp94xa', 'user@gmail.com');
REPLACE INTO `user` (id, username, password, email) VALUES (5, 'usertest', '$2a$10$PIbFeyK49cFEbsaUFdJf6eCjbnfXKw2P6onZKnbV/JgVRQNlp94xa', 'test@gmail.com');

REPLACE INTO  user_roles (user_id, role_id) VALUES(1, 1);
REPLACE INTO  user_roles (user_id, role_id) VALUES(2, 2);
REPLACE INTO  user_roles (user_id, role_id) VALUES(3, 3);
REPLACE INTO  user_roles (user_id, role_id) VALUES(4, 4);
REPLACE INTO  user_roles (user_id, role_id) VALUES(5, 2);