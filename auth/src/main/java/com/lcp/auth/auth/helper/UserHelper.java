package com.lcp.auth.auth.helper;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class UserHelper {

     public UUID parseUserId(String Id){
         return UUID.fromString(Id);
     }
}


