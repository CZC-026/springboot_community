package com.czc.test.test.controller;


import com.czc.test.test.DTO.AccessTokenDTO;
import com.czc.test.test.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by codedrinker on 2019/4/24.
 */
@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("e6e80215901bd5c33256");
        accessTokenDTO.setClient_secret("c50994d962dd97992c8e972abdf3f400dfb8fea1");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_url("http://localhost:8887/callback");
        accessTokenDTO.setState(state);
        githubProvider.getAccessToken(accessTokenDTO);
        return  "index";
    }

}
