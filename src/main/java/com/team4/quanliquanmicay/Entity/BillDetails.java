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
    private String bill_details_id;
    private String bill_id;
    private String product_id;
    private int amount;
    private double price;
    private double discount;
}