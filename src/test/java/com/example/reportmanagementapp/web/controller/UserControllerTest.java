package com.example.reportmanagementapp.web.controller;

import com.example.reportmanagementapp.application.user.commands.CreateUserCommandHandler;
import com.example.reportmanagementapp.application.user.commands.DeleteUserCommandHandler;
import com.example.reportmanagementapp.application.user.commands.UpdateUserCommandHandler;
import com.example.reportmanagementapp.application.user.queries.GetUserByIdQueryHandler;
import com.example.reportmanagementapp.application.user.queries.ListAllUsersQueryHandler;
import com.example.reportmanagementapp.infrastructure.security.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ListAllUsersQueryHandler listAllUsersQueryHandler;

    @MockBean
    private GetUserByIdQueryHandler getUserByIdQueryHandler;

    @MockBean
    private CreateUserCommandHandler createUserCommandHandler;

    @MockBean
    private UpdateUserCommandHandler updateUserCommandHandler;

    @MockBean
    private DeleteUserCommandHandler deleteUserCommandHandler;
    
    @MockBean
    private JwtService jwtService;

    @Test
    @WithMockUser
    void listRegisteredUsers_ShouldReturnUsers() throws Exception {
        when(listAllUsersQueryHandler.handle()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk());
    }
}
