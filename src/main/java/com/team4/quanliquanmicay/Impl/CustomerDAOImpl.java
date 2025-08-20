package com.team4.quanliquanmicay.Impl;
import com.team4.quanliquanmicay.DAO.CustomerDAO;
import com.team4.quanliquanmicay.Entity.Customer;
import com.team4.quanliquanmicay.Entity.TableForCustomer;
import com.team4.quanliquanmicay.util.XJdbc;
import com.team4.quanliquanmicay.util.XQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    String createSql = "INSERT INTO CUSTOMER(phone_number, customer_name, point_level, level_ranking, created_date) VALUES(?, ?, ?, ?, ?)";
    String updateSql = "UPDATE CUSTOMER SET customer_name=?, point_level=?, level_ranking=?, created_date=? WHERE phone_number=?";
    String deleteSql = "DELETE FROM CUSTOMER WHERE phone_number=?";
    String findAllSql = "SELECT phone_number, customer_name, point_level, level_ranking, created_date FROM CUSTOMER";
    String findByIdSql = "SELECT phone_number, customer_name, point_level, level_ranking, created_date FROM CUSTOMER WHERE phone_number=?";
    String existsByPhoneSql = "SELECT 1 FROM CUSTOMER WHERE phone_number = ?";
    String updateBillPhoneSql = "UPDATE BILL SET phone_number = ? WHERE phone_number = ?";
    String searchByPhoneSql = "SELECT phone_number, customer_name, point_level, level_ranking, created_date FROM CUSTOMER WHERE phone_number LIKE ?";
    String sortByPointAscSql = "SELECT phone_number, customer_name, point_level, level_ranking, created_date FROM CUSTOMER ORDER BY point_level ASC";
    String sortByPointDescSql = "SELECT phone_number, customer_name, point_level, level_ranking, created_date FROM CUSTOMER ORDER BY point_level DESC";

    @Override
    public Customer create(Customer entity) {
        Object[] values ={
            entity.getPhone_number(),
            entity.getCustomer_name(),
            entity.getPoint_level(),
            entity.getLevel_ranking(),
            entity.getCreated_date() != null ? new Date(entity.getCreated_date().getTime()) : null
        };
        XJdbc.executeUpdate(createSql, values);
        return entity;
    }

    @Override   
    public void update(Customer entity) {
        Object[] values ={
            entity.getCustomer_name(),
            entity.getPoint_level(),
            entity.getLevel_ranking(),
            entity.getCreated_date() != null ? new Date(entity.getCreated_date().getTime()) : null,
            entity.getPhone_number()
        };
        XJdbc.executeUpdate(updateSql, values);
    }

    @Override
    public void deleteById(String phone_number) {
        XJdbc.executeUpdate(deleteSql, phone_number);
    }                   

    @Override
    public List<Customer> findAll() {
        return XQuery.getBeanList(Customer.class, findAllSql);
    }

    @Override
    public Customer findById(String phone_number) {
        return XQuery.getSingleBean(Customer.class, findByIdSql, phone_number);
    }

    /**
     * Search customers by phone number (partial match)
     */
    public List<Customer> searchByPhone(String phoneNumber) {
        String searchPattern = "%" + phoneNumber + "%";
        return XQuery.getBeanList(Customer.class, searchByPhoneSql, searchPattern);
    }

    /**
     * Sort customers by point level in ascending order
     */
    public List<Customer> sortByPointAsc() {
        return XQuery.getBeanList(Customer.class, sortByPointAscSql);
    }

    /**
     * Sort customers by point level in descending order
     */
    public List<Customer> sortByPointDesc() {
        return XQuery.getBeanList(Customer.class, sortByPointDescSql);
    }

    @Override
    public boolean existsByPhone(String phoneNumber) {
        Integer v = XJdbc.getValue(existsByPhoneSql, Integer.class, phoneNumber);
        return v != null;
    }

    @Override
    public void updateWithPhoneChange(String oldPhoneNumber, Customer entity) {
        // Nếu số điện thoại đổi, phải tạo parent trước rồi mới cập nhật con để tránh FK lỗi
        if (oldPhoneNumber != null && entity != null && entity.getPhone_number() != null
            && !oldPhoneNumber.equals(entity.getPhone_number())) {
            // Nếu số mới đã tồn tại thì không cho đổi
            if (existsByPhone(entity.getPhone_number())) {
                throw new RuntimeException("Số điện thoại mới đã tồn tại!");
            }
            // 1) Tạo CUSTOMER với số mới (parent)
            create(entity);
            // 2) Cập nhật BILL tham chiếu từ số cũ sang số mới (child)
            XJdbc.executeUpdate(updateBillPhoneSql, entity.getPhone_number(), oldPhoneNumber);
            // 3) Xóa CUSTOMER số cũ
            deleteById(oldPhoneNumber);
            return;
        }
        // Không đổi số điện thoại -> update thông tin còn lại
        update(entity);
    }
}
