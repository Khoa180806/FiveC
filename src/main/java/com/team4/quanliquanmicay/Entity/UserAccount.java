package com.team4.quanliquanmicay.Entity;

import java.util.*;
import lombok.*;

/**
 *
 * @author FiveC
 */
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
@Data 
public class UserAccount { 
    private String user_id;
    private String username;
    private String pass;
    private String fullName;
    private Integer gender;        // Sử dụng Integer thay vì Boolean để nhận NUMBER(1)
    private String email;
    private String phone_number;
    private String image;
    private Integer is_enabled;    // Sử dụng Integer thay vì Boolean để nhận NUMBER(1)
    private Date created_date;
    private String role_id;
}

