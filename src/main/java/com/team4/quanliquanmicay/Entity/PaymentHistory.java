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
    private String payment_history_id;
    private Boolean payment_method_id;
    private Date payment_date;
    private double total_amount;
    private Boolean status;
    private String note; 
}