package com.team4.quanliquanmicay.Entity;
import java.util.*;
import lombok.*;

/**
 * Entity BillDetails - Chi tiết hóa đơn
 * @author FiveC
 */
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
@Data 
public class BillDetails {
    private Integer bill_detail_id;
    private Integer bill_id;
    private String product_id;
    private int amount;
    private double price;
    private double discount;
    
    /**
     * Validation cho BillDetails entity
     */
    public boolean isValid() {
        return bill_id != null && bill_id > 0
            && product_id != null && !product_id.trim().isEmpty()
            && amount > 0 
            && price > 0
            && discount >= 0 && discount <= 1;
    }
    
    /**
     * Tính tổng tiền cho item này
     */
    public double getTotalPrice() {
        return price * amount * (1 - discount);
    }
}