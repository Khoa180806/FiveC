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

public class TableForCustomer { 
    private int table_number;
    private int amount;
    private int status;
}


