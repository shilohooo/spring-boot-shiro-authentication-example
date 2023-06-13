package org.shiloh.security.codec;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 密码加密器
 *
 * @author shiloh
 * @date 2023/6/10 16:52
 */
public final class PasswordEncryptUtils {
    /**
     * 随机数生成器
     */
    private static final RandomNumberGenerator RANDOM_NUMBER_GENERATOR = new SecureRandomNumberGenerator();

    /**
     * 密码加密使用的算法名称
     */
    public static final String ALGORITHM_NAME = "md5";

    /**
     * Hash 散列次数
     */
    public static final int HASH_ITERATIONS = 2;

    /**
     * 明文密码加密
     *
     * @param plaintext 明文密码
     * @param salt      密码盐
     * @return 加密后的密文
     * @author shiloh
     * @date 2023/6/10 17:02
     */
    public static String encrypt(String plaintext, String salt) {
        return new SimpleHash(
                ALGORITHM_NAME,
                plaintext,
                ByteSource.Util.bytes(salt),
                HASH_ITERATIONS
        )
                .toHex();
    }

    /**
     * 生成随机密码盐
     *
     * @return 随机密码盐
     * @author shiloh
     * @date 2023/6/11 12:28
     */
    public static String generateSalt() {
        return RANDOM_NUMBER_GENERATOR.nextBytes().toHex();
    }
}
