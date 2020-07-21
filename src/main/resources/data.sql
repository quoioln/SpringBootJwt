REPLACE INTO role (id, description, name) VALUES (1, 'Admin role', 'ROLE_ADMIN');
REPLACE INTO `user` (id, username, password, email) VALUES (1, 'usertest', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 'test@gmail.com');
REPLACE INTO  user_roles (user_id, role_id) VALUES(1, 1);