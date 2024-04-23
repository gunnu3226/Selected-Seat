package io.nbc.selectedseat.domain.auth.service;

import io.nbc.selectedseat.mail.MailTemplateFactory;
import io.nbc.selectedseat.mail.MailUtil;
import io.nbc.selectedseat.redis.service.RedisService;
import java.time.Duration;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    public static final int EMAIL_AUTH_CODE_LENGTH = 8;
    private final MailUtil mailUtil;
    private final MailTemplateFactory mailTemplateFactory;
    private final RedisService redisService;

    public void sendEmailAuthentication(final String email) {
        String code = generateAuthCode();
        String redirectUrl = generateRedirectUrl(email, code);
        String template = mailTemplateFactory.generateEmailAuthenticationTemplate(
            email,
            code,
            redirectUrl
        );

        redisService.set(generateHashKey(email), code, Duration.ofMinutes(3));
        mailUtil.send(
            generateTitle(email),
            template,
            email
        );
    }

    public boolean checkAuthCode(
        final String email,
        final String code
    ) {
        return redisService.get(generateHashKey(email))
            .orElseThrow(() -> new IllegalStateException("만료된 코드입니다"))
            .equals(code);
    }

    private String generateRedirectUrl(final String email, final String code) {
        // TODO: will replace the shop url
        return "http://localhost:3000/signup?code=" + code + "&email=" + email;
    }

    private String generateTitle(final String email) {
        return "[이선좌] 이메일 계정을 인증해주세요 (" + email + ")";
    }

    private String generateHashKey(final String email) {
        return "email:auth:" + email;
    }

    private String generateAuthCode() {
        StringBuffer key = new StringBuffer();
        Random random = new Random();

        for (int i = 0; i < AuthService.EMAIL_AUTH_CODE_LENGTH; i++) {
            int index = random.nextInt(3);

            switch (index) {
                case 0 -> key.append((char) (random.nextInt(26) + 97));
                case 1 -> key.append((char) (random.nextInt(26) + 65));
                case 2 -> key.append(random.nextInt(10));
            }

        }

        return key.toString();
    }

}
