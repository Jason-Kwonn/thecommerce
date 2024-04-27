CREATE TABLE IF NOT EXISTS member (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      member_id VARCHAR(255) NOT NULL,
                                      password VARCHAR(255) NOT NULL,
                                      nick_name VARCHAR(255) NOT NULL,
                                      name VARCHAR(255) NOT NULL,
                                      phone_number VARCHAR(255) NOT NULL,
                                      mail_address VARCHAR(255) NOT NULL,
                                      reg_date TIMESTAMP NOT NULL
);
