package com.example.reportmanagementapp.web.controller;

import com.example.reportmanagementapp.application.dto.AuthResponse;
import com.example.reportmanagementapp.application.dto.LoginRequest;
import com.example.reportmanagementapp.application.dto.UserDto;
import com.example.reportmanagementapp.application.user.commands.RegisterUserCommandHandler;
import com.example.reportmanagementapp.application.user.queries.FindUserByEmailQueryHandler;
import com.example.reportmanagementapp.domain.entity.User;
import com.example.reportmanagementapp.infrastructure.security.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FindUserByEmailQueryHandler findUserByEmailQueryHandler;

    @MockBean
    private RegisterUserCommandHandler registerUserCommandHandler;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    void login_ShouldReturnToken() throws Exception {
        LoginRequest request = new LoginRequest("test@test.com", "pass");
        
        when(userDetailsService.loadUserByUsername(any())).thenReturn(org.springframework.security.core.userdetails.User.withUsername("test@test.com").password("pass").roles("USER").build());
        when(jwtService.generateToken(any())).thenReturn("mocked-jwt-token");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mocked-jwt-token"));
    }

    @Test
    void register_ShouldReturnSuccess() throws Exception {
        UserDto dto = new UserDto();
        dto.setName("Test");
        dto.setEmail("new@test.com");
        dto.setPassword("pass");

        when(findUserByEmailQueryHandler.handle(any())).thenReturn(null);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }
}
