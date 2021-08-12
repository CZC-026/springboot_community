package com.czc.test.test.provider;

import com.alibaba.fastjson.JSON;
import com.czc.test.test.DTO.AccessTokenDTO;
import com.czc.test.test.DTO.GithubUser;
import okhttp3.*;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component  //这里犯过很致命的错误 不加这个的话 authorizecontroller中加入注解provider会报错的
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
         MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1]; //token的样式access_token=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&scope=&token_type=bearer
            System.out.println(string);
            return string;
        }catch (IOException e){
        }
        return null;
    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization","token" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string,GithubUser.class);
            return githubUser;
        }catch (IOException e){
        }
        return null;
    }
}
