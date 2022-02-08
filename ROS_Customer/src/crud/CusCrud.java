/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crud;

import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import java.sql.Date;
import java.sql.Time;
import customer.MainMenu;

/**
 *
 * @author Van AJ Vanguardia
 */
public class CusCrud extends DBConnect {

    PreparedStatement pst;
    ResultSet rs;

    public int createUser() {
        try {
            pst = (PreparedStatement) con.prepareStatement("INSERT INTO customers VALUES ()", Statement.RETURN_GENERATED_KEYS);
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            return rs.next() ? rs.getInt(1) : -1;

        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public void fetchMenu(javax.swing.JTable menuTable, String type) {
        int q;
        try {
            pst = (PreparedStatement) con.prepareStatement("SELECT * FROM menu WHERE type = (?) ");
            pst.setString(1, type);
            rs = pst.executeQuery();
            ResultSetMetaData rss = rs.getMetaData();
            q = rss.getColumnCount();

            DefaultTableModel df = (DefaultTableModel) menuTable.getModel();
            df.setRowCount(0);
            while (rs.next()) {
                Vector v2 = new Vector();
                for (int a = 1; a <= q; a++) {
                    v2.add(rs.getString("code"));
                    v2.add(rs.getString("name"));
                    v2.add("Php " + rs.getString("price"));
                }
                df.addRow(v2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public double fetchOrder(int customerID, javax.swing.JTable ordersTable) {
        int q, total = 0;
        try {
            pst = (PreparedStatement) con.prepareStatement("SELECT *, COUNT(orders.menuCode) AS qty, SUM(menu.price) as total FROM menu INNER JOIN orders ON orders.menuCode = menu.code WHERE orders.customerID = (?) GROUP BY menu.code;");
            pst.setInt(1, customerID);
            rs = pst.executeQuery();
            ResultSetMetaData rss = rs.getMetaData();
            q = rss.getColumnCount();

            DefaultTableModel df = (DefaultTableModel) ordersTable.getModel();
            df.setRowCount(0);
            while (rs.next()) {
                Vector v2 = new Vector();
                for (int a = 1; a <= q; a++) {
                    v2.add(rs.getString("code"));
                    v2.add(rs.getString("name"));
                    v2.add(rs.getString("price"));
                    v2.add(rs.getString("qty"));
                }
                total += rs.getDouble("total");
                df.addRow(v2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    public Boolean searchMenu(String code) {
        try {
            pst = (PreparedStatement) con.prepareStatement("SELECT menu.code FROM menu WHERE code = (?)");
            pst.setString(1, code);
            rs = pst.executeQuery();
            return rs.next();

        } catch (SQLException ex) {
            Logger.getLogger(CusCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public int insertOrder(int customerID, javax.swing.JTextField codeInput, javax.swing.JTextField qtyInput) {
        String code = codeInput.getText().toUpperCase();
        String temp = qtyInput.getText();
        int qty = Integer.parseInt(temp), count = 0, res;
        Time time = new Time(0L);
        time.setTime(new java.util.Date().getTime());
        Date date = new Date(time.getTime());

        try {
            if (searchMenu(code)) {
                for (int i = 0; i < qty; i++) {
                    pst = (PreparedStatement) con.prepareStatement("INSERT INTO orders (customerID, menuCode, date, time, status) VALUES (?,?,?,?, 'pending')"); 
                    pst.setInt(1, customerID);
                    pst.setString(2, code);
                    pst.setDate(3, date);
                    pst.setTime(4, time);
                    res = pst.executeUpdate();
                    count += res;
                }
                return count;

            } else {
                System.out.println("Menu Code is not found");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CusCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }

    public int removeOrder(int customerID, javax.swing.JTextField codeInput, javax.swing.JTextField qtyInput) {
        String code = codeInput.getText();
        String temp = qtyInput.getText();
        int qty = Integer.parseInt(temp);
        
        try {
            if (searchMenu(code)) {
                pst = (PreparedStatement) con.prepareStatement("DELETE FROM orders WHERE orders.customerID = (?) AND orders.menuCode = (?) LIMIT " + qty);
                pst.setInt(1, customerID);
                pst.setString(2, code);
                return pst.executeUpdate();
            } else {
                System.out.println("Menu Code is not found");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CusCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
   
}
