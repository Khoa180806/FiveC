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
    private Integer isAvailable;
    private String note;
    private Date createdDate;
    private String categoryId;

    public Integer getIsAvailable() {
        return isAvailable;
    }
    public void setIsAvailable(Integer isAvailable) {
        this.isAvailable = isAvailable;
    }
    public boolean isAvailable() {
        return isAvailable != null && isAvailable == 1;
    }
    public void setAvailable(boolean available) {
        this.isAvailable = available ? 1 : 0;
    }
}