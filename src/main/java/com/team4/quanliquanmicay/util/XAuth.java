package com.team4.quanliquanmicay.util;

import com.team4.quanliquanmicay.Entity.UserAccount;
import java.util.Date;

/**
 *
 * @author Asus
 */
public class XAuth {
    public static UserAccount user = UserAccount.builder()
        .user_id("U001")
        .username("admin") 
        .pass("123") 
        .fullName("Nguyễn Văn Tèo") 
        .email("admin@example.com")
        .phone_number("0123456789")
        .image("manager.png") 
        .is_enabled(true)
        .created_date(new Date())
        .role_id("R001")
        .build(); // biến user này sẽ được thay thế sau khi đăng nhập
}
