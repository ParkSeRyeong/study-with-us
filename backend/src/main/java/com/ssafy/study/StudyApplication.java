package com.ssafy.study;

import com.ssafy.study.api.request.UserReq;
import com.ssafy.study.db.entity.User;
import com.ssafy.study.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyApplication.class, args);

        System.out.println("Spring Start");
    }

}
