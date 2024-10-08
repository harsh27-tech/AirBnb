package com.airbnb.service;

//import com.twilio.rest.chat.v1.service.channel.Message;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class SMSService {

    private static final Logger logger = LoggerFactory.getLogger(SMSService.class);

    @Value("${twilio.phone-number}")
    private String twilioPhoneNumber;

    public  void sendSms(String to, String messageBody){
        try {
            logger.info("Starting to send SMS"+new Date());
            Message.creator(
                    new PhoneNumber(to),
                    new PhoneNumber(twilioPhoneNumber),
                    messageBody
            ).create();
            logger.info("SMS sent - "+new Date());

        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }
}
