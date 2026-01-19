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
public class PublicLink {
    private String value;
    
    public static PublicLink generate() {
        String randomLink = java.util.UUID.randomUUID().toString().replace("-", "");
        return new PublicLink(randomLink);
    }
    
    public boolean isValid() {
        return value != null && !value.isBlank() && value.length() == AppConstants.PUBLIC_LINK_LENGTH;
    }
}
