package com.pintu.rideshare.controller;

import com.pintu.rideshare.model.ERole;
import com.pintu.rideshare.model.Role;
import com.pintu.rideshare.model.User;
import com.pintu.rideshare.model.VerificationCode;
import com.pintu.rideshare.payload.request.*;
import com.pintu.rideshare.payload.response.*;
import com.pintu.rideshare.repository.RoleRepository;
import com.pintu.rideshare.repository.UserRepository;
import com.pintu.rideshare.repository.VerificationCodeRepository;
import com.pintu.rideshare.security.jwt.JwtUtils;
import com.pintu.rideshare.security.services.UserDetailsImpl;
import com.pintu.rideshare.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    VerificationCodeRepository verificationCodeRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    
    @Autowired
    EmailService emailService;
    
    @Value("${app.verification.expiration}")
    private int verificationExpirationSeconds;

    @PostMapping("/send-code")
    public ResponseEntity<?> sendVerificationCode(@Valid @RequestBody SendCodeRequest request) {
        String email = request.getEmail();
        
        // 生成6位随机验证码
        String code = String.format("%06d", new Random().nextInt(1000000));
        
        // 保存验证码
        VerificationCode verificationCode = new VerificationCode(email, code, verificationExpirationSeconds);
        verificationCodeRepository.save(verificationCode);
        
        // 发送验证码邮件
        emailService.sendVerificationCode(email, code);
        
        return ResponseEntity.ok(new MessageResponse("验证码已发送至您的邮箱！"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        // 验证邮箱是否已被注册
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: 邮箱已被注册!"));
        }

        // 验证验证码
        Optional<VerificationCode> verificationCodeOpt = verificationCodeRepository
                .findByEmailAndCodeAndUsedFalse(signUpRequest.getEmail(), signUpRequest.getVerificationCode());
        
        if (verificationCodeOpt.isEmpty() || verificationCodeOpt.get().isExpired()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: 验证码无效或已过期!"));
        }
        
        // 创建新用户账号
        User user = new User(
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        if (signUpRequest.getNickname() != null && !signUpRequest.getNickname().isEmpty()) {
            user.setNickname(signUpRequest.getNickname());
        }

        Set<Role> roles = new HashSet<>();

        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: 角色未找到"));
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);
        
        // 标记验证码为已使用
        VerificationCode verificationCode = verificationCodeOpt.get();
        verificationCode.setUsed(true);
        verificationCodeRepository.save(verificationCode);

        return ResponseEntity.ok(new MessageResponse("用户注册成功!"));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                                                 userDetails.getId(),
                                                 userDetails.getUsername(),
                                                 userDetails.getNickname(),
                                                 roles));
    }
} 