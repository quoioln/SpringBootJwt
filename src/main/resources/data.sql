REPLACE INTO role (id, description, name) VALUES (1, 'Admin role', 'ROLE_ADMIN');
REPLACE INTO role (id, description, name) VALUES (2, 'Moderator role', 'ROLE_MODERATOR');
REPLACE INTO role (id, description, name) VALUES (3, 'User role', 'ROLE_USER');
REPLACE INTO `user` (id, username, password, email) VALUES (1, 'usertest', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 'test@gmail.com');
REPLACE INTO  user_roles (user_id, role_id) VALUES(1, 1);