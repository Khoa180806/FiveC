package com.team4.quanliquanmicay.Impl;
import com.team4.quanliquanmicay.DAO.CustomerDAO;
import com.team4.quanliquanmicay.Entity.Customer;
import com.team4.quanliquanmicay.Entity.TableForCustomer;
import com.team4.quanliquanmicay.util.XJdbc;
import com.team4.quanliquanmicay.util.XQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    String createSql = "INSERT INTO CUSTOMER(phone_number, customer_name, point_level, level_ranking, created_date) VALUES(?, ?, ?, ?, ?)";
    String updateSql = "UPDATE CUSTOMER SET customer_name=?, point_level=?, level_ranking=?, created_date=? WHERE phone_number=?";
    String deleteSql = "DELETE FROM CUSTOMER WHERE phone_number=?";
    String findAllSql = "SELECT phone_number, customer_name, point_level, level_ranking, created_date FROM CUSTOMER";
    String findByIdSql = "SELECT phone_number, customer_name, point_level, level_ranking, created_date FROM CUSTOMER WHERE phone_number=?";

    @Override
    public Customer create(Customer entity) {
        Object[] values ={
            entity.getPhone_number(),
            entity.getCustomer_name(),
            entity.getPoint_level(),
            entity.getLevel_ranking(),
            entity.getCreated_date()
        };
        XJdbc.executeQuery(createSql, values);
        return entity;
    }

    @Override   
    public void update(Customer entity) {
        Object[] values ={
            
            entity.getCustomer_name(),
            entity.getPoint_level(),
            entity.getLevel_ranking(),
            entity.getCreated_date(),
            entity.getPhone_number()
        };
        XJdbc.executeQuery(createSql, values);
        
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


}
