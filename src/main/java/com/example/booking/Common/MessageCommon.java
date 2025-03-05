package com.example.booking.Common;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Locale;

@Service
@Scope(
        scopeName = "singleton"
)
public class MessageCommon {
    private final MessageSource messageSource;

    public MessageCommon(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getValueByMessageCode(String messageCode) {
        return this.messageSource.getMessage(messageCode, (Object[])null, new Locale("vi"));
    }

    public String getValueByMessageCode(String messageCode, Locale locale) {
        return this.messageSource.getMessage(messageCode, (Object[])null, locale);
    }

    public String getMessage(String messageCode, Object... params) {
        try {
            if (params == null || params.length == 0) {
                return this.getValueByMessageCode(messageCode);
            }
            return this.messageSource.getMessage(messageCode, params, new Locale("vi"));
        } catch (Exception e) {
            return messageCode;
        }
    }

}

