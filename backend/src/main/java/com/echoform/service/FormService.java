package com.echoform.service;

import com.echoform.model.Form;
import com.echoform.repository.FormRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FormService {
    
    private final FormRepository formRepository;
    
    @Transactional
    public Form createForm(String title, String content) {
        Form form = new Form();
        form.setTitle(title);
        form.setContent(content != null ? content : getDefaultFormContent());
        return formRepository.save(form);
    }
    
    public List<Form> getAllForms() {
        return formRepository.findAll();
    }
    
    public Optional<Form> getFormById(Long id) {
        return formRepository.findById(id);
    }
    
    public Optional<Form> getFormByPublicLink(String publicLink) {
        return formRepository.findByPublicLink(publicLink);
    }
    
    @Transactional
    public Form generatePublicLink(Long formId) {
        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new RuntimeException("Form not found"));
        
        String publicLink = generateUniqueLink();
        form.setPublicLink(publicLink);
        return formRepository.save(form);
    }
    
    private String generateUniqueLink() {
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }
    
    private String getDefaultFormContent() {
        return """
            {
              "title": "Default Feedback Form",
              "description": "Standard feedback questionnaire",
              "questions": [
                {
                  "id": 1,
                  "text": "Rate your experience",
                  "type": "single_choice",
                  "required": true,
                  "options": ["5 - Excellent", "4 - Good", "3 - Average", "2 - Below Average", "1 - Poor"]
                },
                {
                  "id": 2,
                  "text": "What did you like most?",
                  "type": "text",
                  "required": false
                },
                {
                  "id": 3,
                  "text": "Would you participate again?",
                  "type": "boolean",
                  "required": true
                }
              ]
            }
            """;
    }
}
