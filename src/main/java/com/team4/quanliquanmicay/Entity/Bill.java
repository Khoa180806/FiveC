package com.team4.quanliquanmicay.Entity;
import java.util.*;
import lombok.*;

/**
 * Entity Bill - Quản lý hóa đơn
 * @author FiveC
 */
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
@Data 
public class Bill {
    private Integer bill_id;
    private String user_id;
    private String phone_number;
    private Integer payment_history_id;
    private int table_number;
    private double total_amount;
    private Date checkin;
    private Date checkout;
    private Boolean status;
    
    /**
     * Validation cho Bill entity
     */
    public boolean isValid() {
        return user_id != null && !user_id.trim().isEmpty() 
            && table_number > 0 
            && total_amount >= 0;
    }
    
    /**
     * Kiểm tra hóa đơn có thể thanh toán
     */
    public boolean canBePaid() {
        return status != null && status && total_amount > 0;
    }
}
