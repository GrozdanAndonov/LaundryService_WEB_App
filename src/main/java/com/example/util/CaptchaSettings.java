package com.example.util;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class CaptchaSettings {
 
    private String url = "https://www.google.com/recaptcha/api/siteverify?";
    private String secretKey = "6Lc0YlIUAAAAAPHegH1k7DydbMETXMK9aQHIOnl1";
    
    
    public boolean isCaptchaValid(String response) {
        try {
            String urlWhole = url + "secret=" + this.secretKey + "&response=" + response;
            InputStream res = new URL(urlWhole).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(res, Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }      
            String jsonText = sb.toString();
            res.close();
            JSONObject json = new JSONObject(jsonText);
            return json.getBoolean("success");       		
        } catch (Exception e) {
            return false;
        }
    }
   
}



