package com.quan.repository;

import com.quan.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/11/14
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 用户名查找
     * @param name
     * @return
     */
    User findByName(String name);
}
