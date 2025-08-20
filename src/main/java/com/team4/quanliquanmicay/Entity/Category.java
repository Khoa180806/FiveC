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
    private String category_id;
    private String category_name;
    private int is_available;
}