package com.echoform.controller;

import com.echoform.dto.mapper.DtoMapper;
import com.echoform.dto.request.FormCreateRequest;
import com.echoform.dto.response.FormResponse;
import com.echoform.model.Form;
import com.echoform.service.FormService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forms")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FormController {
    
    private final FormService formService;
    
    @PostMapping
    public ResponseEntity<FormResponse> createForm(@Valid @RequestBody FormCreateRequest request) {
        Form form = formService.createForm(request.title(), request.content());
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoMapper.toFormResponse(form));
    }
    
    @GetMapping
    public ResponseEntity<List<FormResponse>> getAllForms() {
        List<FormResponse> forms = formService.getAllForms().stream()
                .map(DtoMapper::toFormResponse)
                .toList();
        return ResponseEntity.ok(forms);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<FormResponse> getFormById(@PathVariable Long id) {
        return formService.getFormById(id)
                .map(DtoMapper::toFormResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/{id}/public-link")
    public ResponseEntity<FormResponse> generatePublicLink(@PathVariable Long id) {
        Form form = formService.generatePublicLink(id);
        return ResponseEntity.ok(DtoMapper.toFormResponse(form));
    }
    
    @GetMapping("/link/{publicLink}")
    public ResponseEntity<FormResponse> getFormByPublicLink(@PathVariable String publicLink) {
        return formService.getFormByPublicLink(publicLink)
                .map(DtoMapper::toFormResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
