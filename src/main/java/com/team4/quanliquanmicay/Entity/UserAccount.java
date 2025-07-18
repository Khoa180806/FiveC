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
    private String email;
    private String phone_number;
    private String image;
    private Boolean is_enabled;
    private Date created_date;
    private String role_id;
    private Boolean gender;
}

