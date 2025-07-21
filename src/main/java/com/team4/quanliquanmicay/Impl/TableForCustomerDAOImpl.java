package com.team4.quanliquanmicay.Impl;
import com.team4.quanliquanmicay.DAO.TableForCustomerDAO;
import com.team4.quanliquanmicay.DAO.UserDAO;
import com.team4.quanliquanmicay.Entity.PaymentMethod;
import com.team4.quanliquanmicay.Entity.TableForCustomer;
import com.team4.quanliquanmicay.Entity.UserAccount;
import com.team4.quanliquanmicay.util.XJdbc;
import com.team4.quanliquanmicay.util.XQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class TableForCustomerDAOImpl implements TableForCustomerDAO {
    String createSql = "INSERT INTO TABLE_FOR_CUSTOMER(table_number, amount, status) VALUES(?, ?, ?)";
    String updateSql = "UPDATE TABLE_FOR_CUSTOMER SET amount=?, status=? WHERE table_number=?";
    String deleteSql = "DELETE FROM TABLE_FOR_CUSTOMER WHERE table_number=?";
    String findAllSql = "SELECT table_number, amount, status FROM TABLE_FOR_CUSTOMER";
    String findByIdSql = "SELECT table_number, amount, status FROM TABLE_FOR_CUSTOMER WHERE table_number=?";

    @Override
    public TableForCustomer create(TableForCustomer entity) {
        Object[] values ={
            entity.getTable_number(),
            entity.getAmount(),
            entity.getStatus()
        };
        XJdbc.executeQuery(createSql, values);
        return entity;
    }

    @Override
    public void update(TableForCustomer entity) {
        Object[] values ={
            entity.getAmount(),
            entity.getStatus(),
            entity.getTable_number()
        };
        XJdbc.executeQuery(updateSql, values);
    }

    @Override
    public void deleteById(Integer table_number) {
        XJdbc.executeUpdate(deleteSql, table_number);
    }

    @Override
    public List<TableForCustomer> findAll() {
        List<TableForCustomer> list = new ArrayList<>();
        String sql = "SELECT table_number, amount, status FROM TABLE_FOR_CUSTOMER";
        
        try {
            ResultSet rs = XJdbc.executeQuery(sql);
            while (rs.next()) {
                TableForCustomer table = new TableForCustomer();
                table.setTable_number(rs.getInt("table_number"));
                table.setAmount(rs.getInt("amount"));
                table.setStatus(rs.getInt("status"));
                list.add(table);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return list;
    }


    public List<TableForCustomer> selectAll() {
        return XQuery.getBeanList(TableForCustomer.class, findAllSql);
    }

 
    @Override
    public TableForCustomer findById(Integer table_number) {
        return XQuery.getSingleBean(TableForCustomer.class, findByIdSql, table_number);
    }

   
}
