package com.thecommerce.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private static final String DATA_SQL_PATH = "./src/main/resources/data.sql";

    public DatabaseLoader(JdbcTemplate jdbcTemplate) {
    }

    @Override
    public void run(String... args) throws Exception {
        File file = new File(DATA_SQL_PATH);
        // 파일 존재 및 비어 있지 않은지 확인
        if (file.exists() && file.length() > 0) {
            System.out.println("data.sql already exists and is not empty.");
            return;
        }

        // data.sql 파일이 비어 있거나 존재하지 않는 경우, 파일 생성
        try (FileWriter writer = new FileWriter(file, false)) {  // false to overwrite.
            LocalDate startDate = LocalDate.of(2024, 1, 1); // 시작 날짜 설정
            writer.write("INSERT INTO member (member_id, password, nick_name, name, phone_number, mail_address, reg_date) VALUES\n");
            for (int i = 1; i <= 100; i++) {
                LocalDate regDate = startDate.plusDays(i - 1); // 가입일자 하루씩 증가
                writer.write(String.format("('user%d', 'password%d', 'nick%d', 'Name%d', '0100000%04d', 'user%d@example.com', '%s')%s\n",
                        i, i, i, i, i, i, regDate, (i < 100 ? "," : ";")));
            }
        } catch (IOException e) {
            System.err.println("Error writing to data.sql: " + e.getMessage());
        }
    }
}
