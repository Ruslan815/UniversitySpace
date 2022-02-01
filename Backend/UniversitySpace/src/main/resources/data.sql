INSERT INTO ROLE(id, name) VALUES (0, 'ROLE_ADMIN'), (1, 'ROLE_USER');

INSERT INTO USER(id, username, password) VALUES (1, 'admin', '$2a$12$STs9BW4ICmVK.a676bai5OwG7H4HA/XdmaKmoqXGh6YdMeU30bau.'), (2, 'cat', '$2a$12$YT/5vraodocUVG.mwlaAP.3iL7czqNIo547JM.5GLVT5bjSEF4dPq');

INSERT INTO USER_ROLES(users_id, roles_id) VALUES (1, 0), (2, 1);