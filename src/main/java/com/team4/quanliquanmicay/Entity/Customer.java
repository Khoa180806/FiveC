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
public class Customer { 
    private String phone_number;
    private String customer_name;
    private int point_level;
    private String level_ranking;
    private Date created_date;
}
