package org.shiloh.dao;

import org.shiloh.entity.cache.Captcha;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 验证码缓存 Dao
 *
 * @author shiloh
 * @date 2023/6/10 14:29
 */
@Repository
public interface CaptchaDao extends CrudRepository<Captcha, String> {
}
