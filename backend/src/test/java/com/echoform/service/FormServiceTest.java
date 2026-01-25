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
        assertTrue(result.getContent().contains(title));
        verify(formRepository, times(1)).save(any(Form.class));
    }
    @Test
    void getFormByPublicLinkOrThrow_ShouldReturnForm_WhenFound() {
        String link = "abc-123";
        Form form = new Form();
        form.setPublicLink(link);
        
        when(formRepository.findByPublicLink(link)).thenReturn(Optional.of(form));
        
        Form result = formService.getFormByPublicLinkOrThrow(link);
        
        assertNotNull(result);
        assertEquals(link, result.getPublicLink());
    }

    @Test
    void getFormByPublicLinkOrThrow_ShouldThrowException_WhenNotFound() {
        String link = "invalid-link";
        when(formRepository.findByPublicLink(link)).thenReturn(Optional.empty());
        
        assertThrows(com.echoform.exception.FormNotFoundException.class, () -> {
            formService.getFormByPublicLinkOrThrow(link);
        });
    }

    @Test
    void getFormByIdOrThrow_ShouldReturnForm_WhenFound() {
        Form form = new Form();
        form.setId(1L);

        when(formRepository.findById(1L)).thenReturn(Optional.of(form));

        Form result = formService.getFormByIdOrThrow(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void getFormByIdOrThrow_ShouldThrowException_WhenNotFound() {
        when(formRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(com.echoform.exception.FormNotFoundException.class, () -> {
            formService.getFormByIdOrThrow(999L);
        });
    }

}
