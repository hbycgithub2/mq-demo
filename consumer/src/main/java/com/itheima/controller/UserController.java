package com.itheima.controller;

import com.itheima.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/me")
    public Result me(){
        // TODO 获取当前登录的用户并返回
        return Result.ok("-----consumer-----");
    }
}
