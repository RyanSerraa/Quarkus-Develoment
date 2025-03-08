package org.acme.response;

import org.acme.entity.Usuario;

public class UsuarioResponse {
    private String email;
    private String password;
    private String username;
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
    public static UsuarioResponse fromEntity(Usuario user){
        UsuarioResponse response= new UsuarioResponse();
        response.setEmail(user.getEmail());
        response.setPassword(user.getPassword());
        response.setUsername(user.getUsername());
        return response;
    }
}
