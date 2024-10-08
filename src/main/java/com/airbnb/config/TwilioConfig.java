package com.airbnb.config;

//import lombok.Value;
import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfig {

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Bean
    public void init(){
        Twilio.init(accountSid, authToken);

    }

//    @Value("${twilio.phone-number}")
//    private String phoneNumber;
//
//    public String getAccountSid() {
//        return accountSid;
//    }
//
//    public String getAuthToken() {
//        return authToken;
//    }
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
}
