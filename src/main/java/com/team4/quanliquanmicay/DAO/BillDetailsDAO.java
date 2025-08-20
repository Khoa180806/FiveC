package com.team4.quanliquanmicay.DAO;

import com.team4.quanliquanmicay.Entity.BillDetails;
import java.util.List;

public interface BillDetailsDAO extends CrudDAO<BillDetails, String> {
    List<BillDetails> findByBillId(Integer billId);
}
