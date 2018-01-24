package org.faoxis.eurekaclient;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
public class MySuperEurekaClient {

    public static void main(String[] args) {
        SpringApplication.run(MySuperEurekaClient.class, args);
    }


    @Data
    @Accessors(chain = true)
    public static class User {
        private String firstName;
        private String lastName;
    }

    @FeignClient("my-eureka-client")
    public interface UserClient {

        @RequestMapping(method = RequestMethod.GET, value = "/users/")
        User get();
    }

    @RestController
    @RequestMapping("/users")
    public static class UserController {

        @GetMapping
        public User get() {
            System.out.println("get from server 1");
            return new User()
                    .setFirstName("Sergei")
                    .setLastName("Samoilov");
        }

        @Autowired
        private UserClient userClient;

        @GetMapping("test")
        public User testGet() {
            return userClient.get();
        }
    }



}
