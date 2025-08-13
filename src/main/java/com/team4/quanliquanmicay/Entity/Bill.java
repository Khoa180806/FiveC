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
    private Integer status; // 0: Đang phục vụ, 1: Đã thanh toán, 2: Hủy
    
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
        return status != null && status == 1 && total_amount > 0;
    }
    
    /**
     * Kiểm tra hóa đơn đang phục vụ
     */
    public boolean isServing() {
        return status != null && status == 0;
    }
    
    /**
     * Kiểm tra hóa đơn đã thanh toán
     */
    public boolean isPaid() {
        return status != null && status == 1;
    }
    
    /**
     * Kiểm tra hóa đơn đã hủy
     */
    public boolean isCancelled() {
        return status != null && status == 2;
    }
    
    /**
     * Getter cho status
     */
    public Integer getStatus() {
        return status;
    }
    
    /**
     * Setter cho status  
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    /**
     * Lấy text trạng thái
     */
    public String getStatusText() {
        if (status == null) return "Không xác định";
        switch (status) {
            case 0: return "Đang phục vụ";
            case 1: return "Đã thanh toán";
            case 2: return "Hủy";
            default: return "Không xác định";
        }
    }

}
