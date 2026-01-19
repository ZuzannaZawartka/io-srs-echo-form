package com.echoform.valueobject;

import com.echoform.config.AppConstants;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenValue {
    private String value;
    
    public static TokenValue generate() {
        String randomToken = AppConstants.TOKEN_PREFIX + 
            java.util.UUID.randomUUID().toString().replace("-", "").substring(0, 20);
        return new TokenValue(randomToken);
    }
    
    public boolean isValid() {
        return value != null && value.startsWith(AppConstants.TOKEN_PREFIX) && 
            value.length() == AppConstants.TOKEN_VALUE_LENGTH;
    }
}
