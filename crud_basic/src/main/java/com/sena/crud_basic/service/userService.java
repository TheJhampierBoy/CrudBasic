package com.sena.crud_basic.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sena.crud_basic.DTO.RequestLoginDTO;
import com.sena.crud_basic.DTO.RequestRegisterUserDTO;
import com.sena.crud_basic.DTO.ResponseLogin;
import com.sena.crud_basic.DTO.responseDTO;
import com.sena.crud_basic.DTO.rolesDTO;
import com.sena.crud_basic.DTO.userDTO;
import com.sena.crud_basic.jwt.JwtServices;
import com.sena.crud_basic.model.roles;
import com.sena.crud_basic.model.user;
import com.sena.crud_basic.repository.Iuser;
import com.sena.crud_basic.repository.Iroles;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class userService {

    private final Iuser userRepository;
    private final Iroles roleRepository;
    private final JwtServices jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public List<user> findAll() {
        return userRepository.findAll();
    }

    public Optional<user> findById(int id) {
        return userRepository.findById(id);
    }

    public Optional<user> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<user> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public responseDTO deleteUser(int id) {
        Optional<user> usuario = findById(id);
        if (!usuario.isPresent()) {
            return new responseDTO(HttpStatus.NOT_FOUND, "El usuario no existe");
        }

        userRepository.deleteById(id);
        return new responseDTO(HttpStatus.OK, "Usuario eliminado correctamente");
    }

public responseDTO save(RequestRegisterUserDTO userDTO) {
    try {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            return new responseDTO(HttpStatus.BAD_REQUEST, "El email ya está registrado");
        }

        user newUser = convertToModelRegister(userDTO);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(newUser);
        
        return new responseDTO(HttpStatus.CREATED, "Usuario registrado exitosamente");
    } catch (Exception e) {
        return new responseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "Error al registrar usuario: " + e.getMessage());
    }
}

private user convertToModelRegister(RequestRegisterUserDTO usuarioDTO) {
    // Obtener rol por defecto (ajusta según tu lógica)
    roles defaultRole = roleRepository.findById(2) // Ejemplo: 2 para rol de usuario normal
            .orElseThrow(() -> new RuntimeException("Rol por defecto no encontrado"));
    
    return new user(
            0, // ID se generará automáticamente
            usuarioDTO.getUsername(),
            usuarioDTO.getEmail(),
            usuarioDTO.getPassword(),
            usuarioDTO.getNumber(),
            LocalDateTime.now(),
            true, // Usuario activo por defecto
            defaultRole
    );
}

    public ResponseLogin login(RequestLoginDTO login) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getUsername(),
                        login.getPassword()));
        UserDetails userDetails = userRepository.findByUsername(login.getUsername()).orElseThrow();
        String token = jwtService.generateToken(userDetails);
        return new ResponseLogin(token);
    }

    public responseDTO updateUser(int id, userDTO userDTO) {
        Optional<user> usuario = findById(id);
        if (!usuario.isPresent()) {
            return new responseDTO(HttpStatus.NOT_FOUND, "El usuario no existe");
        }

        user updatedUser = usuario.get();
        updatedUser.setUsername(userDTO.getName());
        updatedUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        updatedUser.setEmail(userDTO.getEmail());
        updatedUser.setStatus(userDTO.getStatus());
        updatedUser.setRole(convertRolesDTOToEntity(userDTO.getRoles()));

        userRepository.save(updatedUser);
        return new responseDTO(HttpStatus.OK, "Usuario actualizado correctamente");
    }

// Métodos modificados en userService:

public responseDTO registerUser(userDTO userDTO) {
    try {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            return new responseDTO(HttpStatus.BAD_REQUEST, "El email ya está registrado");
        }

        // Obtener el rol con el método correcto
        roles role = roleRepository.findById(userDTO.getRoles().getId_roles())  // Cambiado a getId_roles()
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        user newUser = new user();
        newUser.setUsername(userDTO.getName());
        newUser.setEmail(userDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        newUser.setNumber(userDTO.getNumber());
        newUser.setRegistration_date(LocalDateTime.now());
        newUser.setStatus(true);
        newUser.setRole(role);

        userRepository.save(newUser);
        return new responseDTO(HttpStatus.CREATED, "Usuario registrado exitosamente");
    } catch (Exception e) {
        return new responseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "Error al registrar usuario: " + e.getMessage());
    }
}

private roles convertRolesDTOToEntity(rolesDTO rolesDTO) {
    if (rolesDTO == null) {
        return null;
    }
    
    roles role = new roles();
    role.setid_roles(rolesDTO.getId_roles());  // Cambiado a getId_roles()
    role.set_name_rol(rolesDTO.getName_rol());
    return role;
}
}