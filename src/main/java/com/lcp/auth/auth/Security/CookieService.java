package com.lcp.auth.auth.Security;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;

@Service
@Getter
public class CookieService {

    private final String refreshTokenName;
    private final boolean cookieHTTPOnly;
    private final boolean cookieSecure;
    private final String cookieDomainName;
    private final String cookieSameSite;


    public CookieService(
        @Value("${security.jwt.refresh-token-cookie-name}")String refreshTokenName, 
        @Value("${security.jwt.cookie-http-only}")boolean cookieHTTPOnly, 
        @Value("${security.jwt.cookie-secure}")boolean cookieSecure,
        @Value("${security.jwt.cookie-Domain}")String cookieDomainName, 
        @Value("${security.jwt.cookie-same-site}")String cookieSameSite) {

        this.refreshTokenName = refreshTokenName;
        this.cookieHTTPOnly = cookieHTTPOnly;
        this.cookieSecure = cookieSecure;
        this.cookieDomainName = cookieDomainName;
        this.cookieSameSite = cookieSameSite;
    }

    public void addRefreshCookie(HttpServletResponse response, String value, int maxAge){

        var responseCookieBuilder= ResponseCookie.from(refreshTokenName, value)    //ResponseCookie.from(name, value) starts building a cookie
                                   .httpOnly(cookieHTTPOnly)                    //Browser automatically sends the cookie.JavaScript cannot read it 
                                   .secure(cookieSecure)                        //the cookie is sent only over HTTPS.prevents the token from being exposed over an unencrypted connection.
                                   .path("/")                                   //cookie is available for the entire application.
                                   .maxAge(maxAge)                              //how long the browser will keep the cookie.
                                   .sameSite(cookieSameSite);                   //the browser sends this cookie only when the request originates from your own site  
                                   /*"Strict" → Most secure.
                                        "Lax" → Good default for many apps.
                                        "None" → Allows cross-site usage (requires Secure=true).*/


            if(cookieDomainName!=null && !cookieDomainName.isBlank()){
                 responseCookieBuilder.domain(cookieDomainName);
            }

            ResponseCookie responseCookie= responseCookieBuilder.build();
             response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
    }
    

    //method to clear the refreshToken from cookie
     
     public void clearRefreshCookie(HttpServletResponse response){
         
         var responseCookie= ResponseCookie.from(refreshTokenName," ")
                             .httpOnly(cookieHTTPOnly)
                                   .secure(cookieSecure)
                                   .path("/")
                                   .maxAge(0)
                                   .sameSite(cookieSameSite);


            if(cookieDomainName!=null && !cookieDomainName.isBlank()){
                 responseCookie.domain(cookieDomainName);
            }

            ResponseCookie responseCookies= responseCookie.build();
             response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
     }


     public void addNoStroreHeaders(HttpServletResponse response){
        response.addHeader(HttpHeaders.CACHE_CONTROL, "no-store");
        response.addHeader("pragma", "no-cache");

     }

     public String getRefreshTokenName(){
          return refreshTokenName;
     }

}
