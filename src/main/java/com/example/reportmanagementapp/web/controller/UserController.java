package com.example.reportmanagementapp.web.controller;

import com.example.reportmanagementapp.application.dto.UserDto;
import com.example.reportmanagementapp.application.user.commands.CreateUserCommandHandler;
import com.example.reportmanagementapp.application.user.commands.DeleteUserCommandHandler;
import com.example.reportmanagementapp.application.user.commands.UpdateUserCommandHandler;
import com.example.reportmanagementapp.application.user.queries.GetUserByIdQueryHandler;
import com.example.reportmanagementapp.application.user.queries.ListAllUsersQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final ListAllUsersQueryHandler listAllUsersQueryHandler;
    private final GetUserByIdQueryHandler getUserByIdQueryHandler;
    private final CreateUserCommandHandler createUserCommandHandler;
    private final UpdateUserCommandHandler updateUserCommandHandler;
    private final DeleteUserCommandHandler deleteUserCommandHandler;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> listRegisteredUsers() {
        return ResponseEntity.ok(listAllUsersQueryHandler.handle());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return getUserByIdQueryHandler.handle(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> createUser(@RequestBody UserDto userDto) {
        createUserCommandHandler.handle(userDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> editUser(@PathVariable Long id, @RequestBody UserDto updatedUser) {
        updateUserCommandHandler.handle(id, updatedUser);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        deleteUserCommandHandler.handle(id);
        return ResponseEntity.noContent().build();
    }
}
