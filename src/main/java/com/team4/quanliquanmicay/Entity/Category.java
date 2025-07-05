package com.team4.quanliquanmicay.Entity;

import lombok.*;

/**
 *
 * @author FiveC
 */
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
@Data 
public class Category {
    private String categoryId;
    private String categoryName;
    private boolean isAvailable;
}