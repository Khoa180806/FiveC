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
public class PaymentHistory {
    private Integer payment_history_id;
    private Integer payment_method_id;
    private Date payment_date;
    private double total_amount;
    private String status; // Sửa thành String để khớp với database
    private String note; 
}