package com.echoform.service;

import com.echoform.model.Form;
import com.echoform.repository.FormRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FormServiceTest {

    @Mock
    private FormRepository formRepository;
    
    @Spy
    private ObjectMapper objectMapper;

    @InjectMocks
    private FormService formService;

    @Test
    void createForm_ShouldCreateFormWithGivenContent() {
        // Arrange
        String title = "Test Form";
        String content = "{\"key\": \"value\"}";
        Form savedForm = new Form();
        savedForm.setId(1L);
        savedForm.setTitle(title);
        savedForm.setContent(content);

        when(formRepository.save(any(Form.class))).thenReturn(savedForm);

        // Act
        Form result = formService.createForm(title, content);

        // Assert
        assertNotNull(result);
        assertEquals(title, result.getTitle());
        assertEquals(content, result.getContent());
        verify(formRepository, times(1)).save(any(Form.class));
    }
    
    @Test
    void createForm_ShouldUseDefaultContent_WhenContentIsNull() {
        // Arrange
        String title = "Test Form";
        Form savedForm = new Form();
        savedForm.setId(1L);
        savedForm.setTitle(title);
        // We expect default content, not null
        
        when(formRepository.save(any(Form.class))).thenAnswer(invocation -> {
            Form form = invocation.getArgument(0);
            form.setId(1L);
            return form;
        });

        // Act
        Form result = formService.createForm(title, null);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getContent());
        assertTrue(result.getContent().contains("Default Feedback Form"));
        verify(formRepository, times(1)).save(any(Form.class));
    }
}
