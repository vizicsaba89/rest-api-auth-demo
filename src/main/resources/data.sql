INSERT INTO USER (
    id,
    active,
    address,
    created,
    created_at,
    deleted,
    deleted_at,
    deleted_flag,
    email,
    email_token,
    last_login,
    name,
    next_login_change_pwd,
    password,
    password_expired,
    phone,
    settlement_id,
    temp_password,
    temp_password_expired,
    updated,
    updated_at,
    user_type,
    username,
    settlements_by_settlement_id,
    user_by_created_id,
    user_by_deleted_id,
    user_by_updated_id
) VALUES
(1, true, 'address 1', true, '2020-05-02 08:00:00.00', false, NULL, NULL, 'johnsmith@gmail.com', 'asd-123', '2020-05-02 08:00:00.00', 'John Smith', NULL, 'ger454n', false, '+36 30 444 1111', 1, 'zrttzu3', true, false, NULL, 'BASIC', 'johnsmith', 'Settlement 1', 1, NULL, NULL),
(2, true, 'address 2', true, '2020-03-02 08:00:00.00', false, NULL, NULL, 'creativetoby@gmail.com', 'asd-124', '2020-05-02 08:00:00.00', 'Creative Toby', NULL, 'h6rgh54c', false, '+36 30 444 2222', 1, 'zrttzu4', true, false, NULL, 'BASIC', 'creativetoby', 'Settlement 1', 1, NULL, NULL),
(3, true, 'address 3', true, '2020-04-02 08:00:00.00', true, '2020-05-01 10:12:00.00', 'DELETED', 'jiffyages@gmail.com', 'asd-125', '2020-05-02 08:00:00.00', 'Jiffy Ages', NULL, 'xchzt765jh7', false, '+36 30 444 3333', 1, 'zrttzu5', true, false, NULL, 'BASIC', 'jiffyages', 'Settlement 2', 1, NULL, NULL);

INSERT INTO USER_ROLES (user_id, roles) VALUES
(1, 'ADMIN'),
(1, 'PUBLIC'),
(2, 'PUBLIC'),
(3, 'PUBLIC');
