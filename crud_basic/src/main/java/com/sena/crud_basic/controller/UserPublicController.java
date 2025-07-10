package com.sena.crud_basic.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sena.crud_basic.DTO.RequestLoginDTO;
import com.sena.crud_basic.DTO.ResponseLogin;
import com.sena.crud_basic.DTO.responseDTO;
import com.sena.crud_basic.DTO.userDTO;
import com.sena.crud_basic.service.userService;


@RestController
@RequestMapping("/api/v1/public/users")
public class UserPublicController {
     @Autowired
    private userService userService;

@PostMapping("/register")
public ResponseEntity<responseDTO> saveUser(@RequestBody userDTO user) {
    responseDTO response = userService.registerUser(user);
    return new ResponseEntity<>(response, response.getStatus());
}

    @PostMapping("/login") //falta desarrollar
    public ResponseEntity<ResponseLogin> login(@RequestBody RequestLoginDTO userDTO) {
        ResponseLogin response = userService.login(userDTO);
        // ResponseLogin response = null;
        return new ResponseEntity<ResponseLogin>(response, HttpStatus.OK);
    }

    //  @PostMapping("/forgot") //falta desarrollar
    // public ResponseEntity<Object> forgot(@RequestBody UserDTO userDTO) {
    //     // ResponsesDTO response = userService.save(userDTO);
    //     return new ResponseEntity<>(response, HttpStatus.OK);
    // }
    
}
