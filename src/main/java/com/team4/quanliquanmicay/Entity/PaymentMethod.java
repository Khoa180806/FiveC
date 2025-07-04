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
public class PaymentMethod { 
    private Boolean payment_method_id;
    private String method_name;
    private Boolean is_enable;
}
