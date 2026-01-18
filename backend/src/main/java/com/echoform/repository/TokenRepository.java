package com.echoform.repository;

import com.echoform.model.OneTimeToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<OneTimeToken, Long> {
    
    Optional<OneTimeToken> findByTokenValue(String tokenValue);
    
    List<OneTimeToken> findByFormId(Long formId);
}
