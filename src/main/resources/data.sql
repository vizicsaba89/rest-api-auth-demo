INSERT INTO USER (id, username, password) VALUES
    (1, 'johnsmith', 'ger454n'),
    (2, 'creativetoby', 'h6rgh54c');

INSERT INTO USER_ROLES (user_id, roles) VALUES
    (1, 'ADMIN'),
    (1, 'PUBLIC'),
    (2, 'PUBLIC');

INSERT INTO TODO (id, title, body) VALUES
    (1, 'shopping', 'go to the shop and get some milk'),
    (2, 'homework', 'do the homework');
