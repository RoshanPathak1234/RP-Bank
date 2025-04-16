package com.RPBank.main.Controllers;

import com.RPBank.main.DTO.webDTO.LoginCredentials;
import com.RPBank.main.DTO.webDTO.RegistrationInfo;
import com.RPBank.main.Services.interfaces.WebServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/verify")
public class WebController {

    @Autowired
    private WebServices webServices;

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid registration data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("register")
    public ResponseEntity<String> registration(@RequestBody RegistrationInfo request) {
        return webServices.register(request);
    }

    @Operation(summary = "Login with username and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("login")
    public ResponseEntity<String> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User login credentials",
                    required = true,
                    content = @Content(schema = @Schema(implementation = LoginCredentials.class))
            )
            @RequestBody LoginCredentials loginCredentials) {
        return webServices.login(loginCredentials);
    }
}
