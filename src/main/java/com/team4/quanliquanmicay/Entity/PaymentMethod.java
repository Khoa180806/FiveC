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
    private int payment_method_id;
    private String method_name;
    private int is_enable;
}
