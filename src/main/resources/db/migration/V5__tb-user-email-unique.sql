ALTER TABLE tb_users
    ADD CONSTRAINT uc_tb_users_email UNIQUE (email);