package com.careerpilot.controller;
import com.careerpilot.entity.User; import com.careerpilot.repository.UserRepository; import org.springframework.http.*; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/auth")
public class AuthController {
 private final UserRepository users; private final PasswordEncoder encoder;
 public AuthController(UserRepository u,PasswordEncoder e){users=u;encoder=e;}
 @PostMapping("/register") public ResponseEntity<?> register(@RequestBody Map<String,String> body){String name=body.getOrDefault("name","").trim(),email=body.getOrDefault("email","").trim().toLowerCase(),password=body.getOrDefault("password",""); if(name.isBlank()||email.isBlank()||password.length()<6)return ResponseEntity.badRequest().body(Map.of("message","Enter name, email and a password of at least 6 characters.")); if(users.existsByEmail(email))return ResponseEntity.status(409).body(Map.of("message","Email already registered.")); User u=new User();u.setName(name);u.setEmail(email);u.setPassword(encoder.encode(password));users.save(u);return ResponseEntity.ok(profile(u));}
 @PostMapping("/login") public ResponseEntity<?> login(@RequestBody Map<String,String> body){String email=body.getOrDefault("email","").toLowerCase(); User u=users.findByEmail(email).orElse(null);if(u==null||!encoder.matches(body.getOrDefault("password",""),u.getPassword()))return ResponseEntity.status(401).body(Map.of("message","Invalid email or password."));return ResponseEntity.ok(profile(u));}
 private Map<String,Object> profile(User u){return Map.of("id",u.getId(),"name",u.getName(),"email",u.getEmail());}
}
