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
public class BillDetails {
    private Integer bill_detail_id;
    private Integer bill_id;
    private String product_id;
    private int amount;
    private double price;
    private double discount;
}