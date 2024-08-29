package com.example.idolwiki.model.auth;

import com.example.idolwiki.model.auth.form.LoginForm;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @PostMapping("/common/login")
    public ResponseEntity<?> login(@RequestBody LoginForm loginForm, HttpServletRequest request) {
        String id = loginForm.getId().trim();
        String password = loginForm.getPassword();

        try{
            request.login(id, password);
            return ResponseEntity.ok(true);
        } catch (ServletException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/admin/index")
    public ResponseEntity<?> index(){
        return ResponseEntity.ok().body(true);
    }
}
