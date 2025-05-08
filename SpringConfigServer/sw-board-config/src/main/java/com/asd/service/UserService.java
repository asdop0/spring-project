package com.asd.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.asd.model.User;
import com.asd.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	@Value("${springboot.jwt.secret}")
    private String secretKey;
	@PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
    }
	
	//토큰에서 유저 정보 추출
	public User findUser(HttpServletRequest request) {
		String token = request.getHeader("X-AUTH-TOKEN");
		String uid = Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload().getSubject();
		User user = userRepository.getByUid(uid).orElseThrow(() -> 
			new IllegalArgumentException("[findUser] 해당 유저를 찾을 수 없습니다.")
		);
		return user;
	}
	
	private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
	
	//닉네임으로 유저 추출
	public User findByNickname(String nickname) {
		User user = userRepository.findByNickname(nickname).orElseThrow(() -> 
    		new IllegalArgumentException("[findByNickname] 해당 유저를 찾을 수 없습니다.")
		);
		return user;
	}
}
