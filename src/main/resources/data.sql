SELECT * FROM MESSAGES;

--DELETE FROM MESSAGES_USERS_WHO_DID_NOT_READ;

--DELETE FROM MESSAGES;

--DELETE FROM MESSAGES;
--DELETE FROM USER;
--DELETE FROM USER_ROLES;

--INSERT INTO ROLE(id, name) VALUES (0, 'ROLE_ADMIN'), (1, 'ROLE_USER');

--INSERT INTO USER(id, username, password) VALUES (1, 'admin', '$2a$12$STs9BW4ICmVK.a676bai5OwG7H4HA/XdmaKmoqXGh6YdMeU30bau.'), (2, 'cat', '$2a$12$YT/5vraodocUVG.mwlaAP.3iL7czqNIo547JM.5GLVT5bjSEF4dPq'), (3, 'third test user', '$2a$12$STs9BW4ICmVK.a676bai5OwG7H4HA/XdmaKmoqXGh6YdMeU30bau.'), (4, 'fourth test user', '$2a$12$STs9BW4ICmVK.a676bai5OwG7H4HA/XdmaKmoqXGh6YdMeU30bau.');

--INSERT INTO USER_ROLES(users_id, roles_id) VALUES (1, 0), (2, 1), (3, 1), (4, 1);