package com.team4.quanliquanmicay.Entity;

import lombok.*;
import java.util.*;

/**
 *
 * @author FiveC
 */
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
@Data 
public class Product {
    private String productId;
    private String productName;
    private double price;
    private double discount;
    private String unit;
    private String image;
    private boolean isAvailable;
    private String note;
    private Date createdDate;
    private String categoryId;
}