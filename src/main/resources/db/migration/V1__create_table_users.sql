CREATE TABLE t_user (
    id BINARY(16) NOT NULL,
    username VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    is_active BOOLEAN,
    created_at DATETIME,
    updated_at DATETIME,
    deleted_at DATETIME,
    PRIMARY KEY (id)
);

CREATE TABLE user_roles (
    user_id BINARY(16) NOT NULL,
    role VARCHAR(255) NOT NULL,
    CONSTRAINT fk_user_role_user_id FOREIGN KEY (user_id) REFERENCES t_user(id) ON DELETE CASCADE
);