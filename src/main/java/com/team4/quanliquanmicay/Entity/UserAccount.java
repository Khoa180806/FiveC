package com.team4.quanliquanmicay.Entity;

import java.util.Date; // ĐỔI THÀNH java.util.Date
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
    private Integer gender;        
    private String email;
    private String phone_number;
    private String image;
    private Integer is_enabled;    
    private Date created_date;     // ĐỔI THÀNH java.util.Date
    private String role_id;
}