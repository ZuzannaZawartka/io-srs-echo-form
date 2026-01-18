package com.echoform.controller;

import com.echoform.model.Form;
import com.echoform.service.FormService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/forms")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FormController {
    
    private final FormService formService;
    
    @PostMapping
    public ResponseEntity<Form> createForm(@RequestBody Map<String, String> request) {
        String title = request.get("title");
        String content = request.get("content");
        
        if (title == null || title.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        
        Form form = formService.createForm(title, content);
        return ResponseEntity.status(HttpStatus.CREATED).body(form);
    }
    
    @GetMapping
    public ResponseEntity<List<Form>> getAllForms() {
        List<Form> forms = formService.getAllForms();
        return ResponseEntity.ok(forms);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Form> getFormById(@PathVariable Long id) {
        return formService.getFormById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/{id}/public-link")
    public ResponseEntity<Form> generatePublicLink(@PathVariable Long id) {
        try {
            Form form = formService.generatePublicLink(id);
            return ResponseEntity.ok(form);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/link/{publicLink}")
    public ResponseEntity<Form> getFormByPublicLink(@PathVariable String publicLink) {
        return formService.getFormByPublicLink(publicLink)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
