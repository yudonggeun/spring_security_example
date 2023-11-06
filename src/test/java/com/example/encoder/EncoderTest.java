package com.example.encoder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.keygen.StringKeyGenerator;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.Arrays;
import java.util.HashMap;

public class EncoderTest {

    @DisplayName("DelegatingPasswordEncoder 동작 테스트")
    @Test
    void test() {
        // given
        PasswordEncoder noOpPasswordEncoder = new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return String.valueOf(rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return rawPassword.equals(encodedPassword);
            }
        };

        new BCryptPasswordEncoder();
        var map = new HashMap<String, PasswordEncoder>();
        map.put("bcrypt", new BCryptPasswordEncoder());
        map.put("no", noOpPasswordEncoder);

        var encoder = new DelegatingPasswordEncoder("bcrypt", map);
        encoder.setDefaultPasswordEncoderForMatches(map.get("no"));

        // test
        System.out.println(encoder.encode("hello"));
        System.out.println(map.get("b").encode("hello"));
        System.out.println(encoder.matches("hello", "{n}hello"));
        System.out.println(encoder.matches("hello", "hello"));
    }

    @Nested
    @DisplayName("salt를 위한 키 생성기")
    class Salt {

        @Test
        void salt1() {
            // given
            StringKeyGenerator generator = () -> "salt";
            StringKeyGenerator generator2 = KeyGenerators.string();
            // when
            var salt = generator.generateKey();
            // then
            System.out.println(salt);
        }

        @Test
        void salt2() {
            // given
            BytesKeyGenerator generator = new BytesKeyGenerator() {
                @Override
                public int getKeyLength() {
                    return 5;
                }

                @Override
                public byte[] generateKey() {
                    return new byte[]{1, 2, 3, 4, 5};
                }
            };
            // when
            var salt = generator.generateKey();
            // then
            System.out.println(Arrays.toString(salt));
        }

        @Test
        void salt3() {
            // given
            var generator = KeyGenerators.secureRandom();
            // when
            var len = generator.getKeyLength();
            var salt = generator.generateKey();
            // then
            System.out.println("len : " + len + ", " + Arrays.toString(salt));
        }

        @Test
        void salt4() {
            // given
            var generator = KeyGenerators.secureRandom(16);
            // when
            var len = generator.getKeyLength();
            var salt = generator.generateKey();
            // then
            System.out.println("len : " + len + ", " + Arrays.toString(salt));
        }
    }
}