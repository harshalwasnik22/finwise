package com.finwise.api.controllers;

import com.finwise.api.dto.UserRequestDTO;
import com.finwise.api.dto.UserResponseDTO;
import com.finwise.api.models.User;
import com.finwise.api.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User Management", description = "APIs for managing user accounts")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieves a list of all registered users")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved user list")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userRepository.findAll().stream()
                .map(user -> new UserResponseDTO(user.getId(), user.getFirstName() + " " + user.getLastName(), user.getEmail()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieves a specific user by their unique ID")
    @ApiResponse(responseCode = "200", description = "User found")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id));
        UserResponseDTO response = new UserResponseDTO(user.getId(), user.getFirstName() + " " + user.getLastName(), user.getEmail());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Create new user", description = "Registers a new user in the system")
    @ApiResponse(responseCode = "201", description = "User successfully created")
    @ApiResponse(responseCode = "400", description = "Invalid user data provided")
    @ApiResponse(responseCode = "409", description = "User with email already exists")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with email already exists");
        }
        User user = new User();
        user.setFirstName(userRequestDTO.getName().split(" ")[0]);
        user.setLastName(userRequestDTO.getName().split(" ").length > 1 ? userRequestDTO.getName().split(" ")[1] : "");
        user.setEmail(userRequestDTO.getEmail());
        User savedUser = userRepository.save(user);
        UserResponseDTO response = new UserResponseDTO(savedUser.getId(), savedUser.getFirstName() + " " + savedUser.getLastName(), savedUser.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user", description = "Updates an existing user's information")
    @ApiResponse(responseCode = "200", description = "User successfully updated")
    @ApiResponse(responseCode = "404", description = "User not found")
    @ApiResponse(responseCode = "400", description = "Invalid user data provided")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDTO userRequestDTO) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id);
        }
        User user = userRepository.findById(id).orElseThrow();
        user.setFirstName(userRequestDTO.getName().split(" ")[0]);
        user.setLastName(userRequestDTO.getName().split(" ").length > 1 ? userRequestDTO.getName().split(" ")[1] : "");
        user.setEmail(userRequestDTO.getEmail());
        User updatedUser = userRepository.save(user);
        UserResponseDTO response = new UserResponseDTO(updatedUser.getId(), updatedUser.getFirstName() + " " + updatedUser.getLastName(), updatedUser.getEmail());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Removes a user from the system")
    @ApiResponse(responseCode = "204", description = "User successfully deleted")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id);
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Find user by email", description = "Retrieves a user by their email address")
    @ApiResponse(responseCode = "200", description = "User found")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with email: " + email));
        UserResponseDTO response = new UserResponseDTO(user.getId(), user.getFirstName() + " " + user.getLastName(), user.getEmail());
        return ResponseEntity.ok(response);
    }
}