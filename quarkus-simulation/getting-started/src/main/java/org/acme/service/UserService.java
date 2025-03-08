package org.acme.service;
import jakarta.validation.constraints.NotBlank;

public class UserService {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String username;
    public UserService() {
    }
    public UserService(@NotBlank String email, @NotBlank String password,
            @NotBlank String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
