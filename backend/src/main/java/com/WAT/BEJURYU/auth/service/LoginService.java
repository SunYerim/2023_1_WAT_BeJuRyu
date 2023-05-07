package com.WAT.BEJURYU.auth.service;

import com.WAT.BEJURYU.auth.dto.KakaoUserInfo;
import com.WAT.BEJURYU.auth.dto.Token;
import com.WAT.BEJURYU.entity.User;
import com.WAT.BEJURYU.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {

    private static final String KAKAO_API_PROFILE = "https://kapi.kakao.com/v2/user/me";

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public KakaoUserInfo parse(final String token) throws MalformedURLException, URISyntaxException {
        final RestTemplate client = new RestTemplate();
        final URL url = new URL(KAKAO_API_PROFILE);

        final HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(token);
        final HttpEntity<Object> httpEntity = new HttpEntity<>(header);

        return client.exchange(url.toURI(), HttpMethod.GET, httpEntity, KakaoUserInfo.class).getBody();
    }

    public boolean isRegistered(final Long kakaoId) {
        return userRepository.existsById(kakaoId);
    }

    @Transactional
    public void register(final KakaoUserInfo userInfo) {
        final User user = new User(userInfo.getId(), userInfo.getProperties().getNickname());

        userRepository.save(user);
    }

    public Token createToken(final KakaoUserInfo userInfo) {
        final String access = jwtTokenProvider.createToken(String.valueOf(userInfo.getId()));
        final String refresh = jwtTokenProvider.createRefreshToken();

        return new Token(access, refresh);
    }
}
