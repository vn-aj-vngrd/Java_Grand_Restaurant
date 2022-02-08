/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crud;

import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

import java.io.FileOutputStream;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Van AJ Vanguardia
 */
public class OrderCrud extends DBConnect {

    PreparedStatement pst;
    ResultSet rs;

    public int loginValidation(javax.swing.JTextField usernameField, javax.swing.JPasswordField passwordField) {
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());
        int q, id = -1;

        try {
            pst = (PreparedStatement) con.prepareStatement("SELECT id FROM employees WHERE employees.role = 'emp' AND employees.username = (?) AND employees.password = (?)");
            pst.setString(1, username);
            pst.setString(2, password);
            rs = pst.executeQuery();
            ResultSetMetaData rss = rs.getMetaData();
            q = rss.getColumnCount();
            while (rs.next()) {
                id = rs.getInt("id");
            }
            return id;

        } catch (SQLException ex) {
            Logger.getLogger(OrderCrud.class.getName()).log(Level.SEVERE, null, ex);
        }

        return id;
    }

    public void fetchOrder(String status, javax.swing.JTable orderTable) {
        int q;
        try {
            pst = (PreparedStatement) con.prepareStatement("SELECT * FROM orders WHERE orders.status = (?)");
            pst.setString(1, status);
            rs = pst.executeQuery();
            ResultSetMetaData rss = rs.getMetaData();
            q = rss.getColumnCount();

            DefaultTableModel df = (DefaultTableModel) orderTable.getModel();
            df.setRowCount(0);
            while (rs.next()) {
                Vector v2 = new Vector();
                for (int a = 1; a <= q; a++) {
                    v2.add(rs.getString("id"));
                    v2.add(rs.getString("customerID"));
                    v2.add(rs.getString("menuCode"));
                    v2.add(rs.getString("date"));
                    v2.add(rs.getString("time"));
                }
                df.addRow(v2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int changeStatus(int empID, String status, javax.swing.JTextField field) {
        String temp = field.getText();
        int orderID = Integer.parseInt(temp);
        try {
            pst = (PreparedStatement) con.prepareStatement("UPDATE orders SET orders.status = (?), orders.empID = (?) WHERE orders.id = (?) ");
            pst.setString(1, status);
            pst.setInt(2, empID);
            pst.setInt(3, orderID);
            return pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrderCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public Boolean exportAsPDF(String status, File filepath) {
        try {
            pst = (PreparedStatement) con.prepareStatement("SELECT * FROM orders WHERE orders.status = (?)");
            pst.setString(1, status);
            rs = pst.executeQuery();

            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(filepath + ".pdf"));

            doc.open();
            PdfPTable table = new PdfPTable(7);
            PdfPCell cell;

            String IDTitle = "Order ID";
            cell = new PdfPCell(new Phrase(IDTitle));
            table.addCell(cell);

            String customerIDTitle = "Customer ID";
            cell = new PdfPCell(new Phrase(customerIDTitle));
            table.addCell(cell);

            String menuCodeTitle = "Menu Code";
            cell = new PdfPCell(new Phrase(menuCodeTitle));
            table.addCell(cell);

            String empIDTitle = "Employee ID";
            cell = new PdfPCell(new Phrase(empIDTitle));
            table.addCell(cell);

            String tstatusTitle = "Status";
            cell = new PdfPCell(new Phrase(tstatusTitle));
            table.addCell(cell);

            String dateTitle = "Date";
            cell = new PdfPCell(new Phrase(dateTitle));
            table.addCell(cell);

            String timeTitle = "Title";
            cell = new PdfPCell(new Phrase(timeTitle));
            table.addCell(cell);

            while (rs.next()) {
                String ID = rs.getString("id");
                cell = new PdfPCell(new Phrase(ID));
                table.addCell(cell);

                String customerID = rs.getString("customerID");
                cell = new PdfPCell(new Phrase(customerID));
                table.addCell(cell);

                String menuCode = rs.getString("menuCode");
                cell = new PdfPCell(new Phrase(menuCode));
                table.addCell(cell);

                String empID = rs.getString("empID");
                cell = new PdfPCell(new Phrase(empID));
                table.addCell(cell);

                String tstatus = rs.getString("status");
                cell = new PdfPCell(new Phrase(tstatus));
                table.addCell(cell);

                String date = rs.getString("date");
                cell = new PdfPCell(new Phrase(date));
                table.addCell(cell);

                String time = rs.getString("time");
                cell = new PdfPCell(new Phrase(time));
                table.addCell(cell);
            }

            doc.add(table);
            doc.close();
            return true;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(OrderCrud.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(OrderCrud.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(OrderCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Boolean exportAsCSV(String status, File filepath) {
        try {
            FileWriter fw = new FileWriter(filepath + ".csv");

            pst = (PreparedStatement) con.prepareStatement("SELECT * FROM orders where orders.status = (?)");
            pst.setString(1, status);

            rs = pst.executeQuery();
            fw.append("Order ID");
            fw.append(',');
            fw.append("Customer ID");
            fw.append(',');
            fw.append("Menu Code");
            fw.append(',');
            fw.append("Employee ID");
            fw.append(',');
            fw.append("Status");
            fw.append(',');
            fw.append("Date");
            fw.append(',');
            fw.append("Time");
            fw.append('\n');

            while (rs.next()) {
                fw.append(rs.getString(1));
                fw.append(',');
                fw.append(rs.getString(2));
                fw.append(',');
                fw.append(rs.getString(3));
                fw.append(',');
                fw.append(rs.getString(4));
                fw.append(',');
                fw.append(rs.getString(5));
                fw.append(',');
                fw.append(rs.getString(6));
                fw.append(',');
                fw.append(rs.getString(7));
                fw.append('\n');
            }

            fw.flush();
            fw.close();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(OrderCrud.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(OrderCrud.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }
}
