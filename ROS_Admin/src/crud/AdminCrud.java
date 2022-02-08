/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crud;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mysql.jdbc.PreparedStatement;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Van AJ Vanguardia
 */
public class AdminCrud extends DBConnect {

    PreparedStatement pst;
    ResultSet rs;

    public int loginValidation(javax.swing.JTextField usernameField, javax.swing.JPasswordField passwordField) {
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());
        int q, id = -1;

        try {
            pst = (PreparedStatement) con.prepareStatement("SELECT id FROM employees WHERE employees.role = 'admin' AND employees.username = (?) AND employees.password = (?)");
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
            Logger.getLogger(AdminCrud.class.getName()).log(Level.SEVERE, null, ex);
        }

        return id;
    }

    public void fetchEmployee(javax.swing.JTable empTable) {
        int q;
        try {
            pst = (PreparedStatement) con.prepareStatement("SELECT * FROM employees WHERE employees.role = 'emp' ");
            rs = pst.executeQuery();
            ResultSetMetaData rss = rs.getMetaData();
            q = rss.getColumnCount();

            DefaultTableModel df = (DefaultTableModel) empTable.getModel();
            df.setRowCount(0);
            while (rs.next()) {
                Vector v2 = new Vector();
                for (int a = 1; a <= q; a++) {
                    v2.add(rs.getString("id"));
                    v2.add(rs.getString("fname"));
                    v2.add(rs.getString("lname"));
                    v2.add(rs.getString("username"));
                    v2.add(rs.getString("password"));
                }
                df.addRow(v2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int insertEmp(javax.swing.JTextField fnameInput, javax.swing.JTextField lnameInput, javax.swing.JTextField usernameInput, javax.swing.JTextField passwordInput) {
        String fname = fnameInput.getText();
        String lname = lnameInput.getText();
        String username = usernameInput.getText();
        String password = passwordInput.getText();

        try {
            pst = (PreparedStatement) con.prepareStatement("INSERT INTO employees (role, username, password, fname, lname) VALUES ('emp',?,?,?,?)");
            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, fname);
            pst.setString(4, lname);
            return pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AdminCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int updateEmp(javax.swing.JTextField idInput, javax.swing.JTextField fnameInput, javax.swing.JTextField lnameInput, javax.swing.JTextField usernameInput, javax.swing.JTextField passwordInput) {
        String tempID = idInput.getText();
        int id = Integer.parseInt(tempID);
        String fname = fnameInput.getText();
        String lname = lnameInput.getText();
        String username = usernameInput.getText();
        String password = passwordInput.getText();
        int res;

        try {
            pst = (PreparedStatement) con.prepareStatement("UPDATE employees SET username = (?), password = (?), fname = (?), lname = (?) WHERE id = (?)");
            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, fname);
            pst.setString(4, lname);
            pst.setInt(5, id);
            res = pst.executeUpdate();
            return res;
        } catch (SQLException ex) {
            Logger.getLogger(AdminCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int deleteEmp(javax.swing.JTextField idInput) {
        String tempID = idInput.getText();
        int id = Integer.parseInt(tempID);

        try {
            pst = (PreparedStatement) con.prepareStatement("DELETE FROM employees WHERE id = (?)");
            pst.setInt(1, id);
            return pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AdminCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public Boolean exportAsPDF_Emp(File filepath) {
        try {
            pst = (PreparedStatement) con.prepareStatement("SELECT * FROM employees WHERE role = 'emp'");
            rs = pst.executeQuery();

            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(filepath + ".pdf"));

            doc.open();
            PdfPTable table = new PdfPTable(6);
            PdfPCell cell;

            String IDTitle = "Employee ID";
            cell = new PdfPCell(new Phrase(IDTitle));
            table.addCell(cell);

            String roleTitle = "Role";
            cell = new PdfPCell(new Phrase(roleTitle));
            table.addCell(cell);

            String usernameTitle = "Username";
            cell = new PdfPCell(new Phrase(usernameTitle));
            table.addCell(cell);

            String passwordTitle = "Password";
            cell = new PdfPCell(new Phrase(passwordTitle));
            table.addCell(cell);

            String fnameTitle = "First Name";
            cell = new PdfPCell(new Phrase(fnameTitle));
            table.addCell(cell);

            String lnameTitle = "Last Name";
            cell = new PdfPCell(new Phrase(lnameTitle));
            table.addCell(cell);

            while (rs.next()) {
                String ID = rs.getString("id");
                cell = new PdfPCell(new Phrase(ID));
                table.addCell(cell);

                String role = rs.getString("role");
                cell = new PdfPCell(new Phrase(role));
                table.addCell(cell);

                String username = rs.getString("username");
                cell = new PdfPCell(new Phrase(username));
                table.addCell(cell);

                String password = rs.getString("password");
                cell = new PdfPCell(new Phrase(password));
                table.addCell(cell);

                String fname = rs.getString("fname");
                cell = new PdfPCell(new Phrase(fname));
                table.addCell(cell);

                String lname = rs.getString("lname");
                cell = new PdfPCell(new Phrase(lname));
                table.addCell(cell);
            }

            doc.add(table);
            doc.close();
            return true;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(AdminCrud.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(AdminCrud.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AdminCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Boolean exportAsCSV_Emp(File filepath) {
        try {
            FileWriter fw = new FileWriter(filepath + ".csv");

            pst = (PreparedStatement) con.prepareStatement("SELECT * FROM employees WHERE role = 'emp'");
            rs = pst.executeQuery();

            fw.append("Employee ID");
            fw.append(',');
            fw.append("Role");
            fw.append(',');
            fw.append("Username");
            fw.append(',');
            fw.append("Password");
            fw.append(',');
            fw.append("First Name");
            fw.append(',');
            fw.append("Last Name");
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
                fw.append('\n');
            }

            fw.flush();
            fw.close();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(AdminCrud.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AdminCrud.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public void fetchOrder(javax.swing.JTable orderTable) {
        int q;
        try {
            pst = (PreparedStatement) con.prepareStatement("SELECT * FROM orders");
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
                    v2.add(rs.getString("empID"));
                    v2.add(rs.getString("status"));
                    v2.add(rs.getString("date"));
                    v2.add(rs.getString("time"));
                }
                df.addRow(v2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int insertOrder(javax.swing.JTextField cusidField, javax.swing.JTextField menucodeField, javax.swing.JTextField empidField, javax.swing.JComboBox<String> statusField, javax.swing.JTextField dateField, javax.swing.JTextField timeField) {
        String tempCusID = cusidField.getText();
        int cusID = Integer.parseInt(tempCusID);
        String menuCode = menucodeField.getText();
        String tempEmpID = empidField.getText();
        int empID = Integer.parseInt(tempEmpID);
        String status = (String) statusField.getEditor().getItem();
        String date = dateField.getText();
        String time = timeField.getText();
        try {
            pst = (PreparedStatement) con.prepareStatement("INSERT INTO orders (customerID, menuCode, empID, status, date, time) VALUES (?,?,?,?,?,?) ");
            pst.setInt(1, cusID);
            pst.setString(2, menuCode);
            pst.setInt(3, empID);
            pst.setString(4, status);
            pst.setString(5, date);
            pst.setString(6, time);

            return pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.print("SQL ERROR");
            Logger.getLogger(AdminCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int updateOrder(javax.swing.JTextField orderidField, javax.swing.JTextField cusidField, javax.swing.JTextField menucodeField, javax.swing.JTextField empidField, javax.swing.JComboBox<String> statusField, javax.swing.JTextField dateField, javax.swing.JTextField timeField) {
        String tempOrderId = orderidField.getText();
        int orderID = Integer.parseInt(tempOrderId);
        String tempCusID = cusidField.getText();
        int cusID = Integer.parseInt(tempCusID);
        String menuCode = menucodeField.getText();
        String tempEmpID = empidField.getText();
        int empID = Integer.parseInt(tempEmpID);
        String status = (String) statusField.getEditor().getItem();
        String date = dateField.getText();
        String time = timeField.getText();
        int res, count = 0;

        try {
            pst = (PreparedStatement) con.prepareStatement("UPDATE orders SET customerID = (?), menuCode = (?), empID = (?), status = (?), date = (?), time = (?) WHERE id = (?)");
            pst.setInt(1, cusID);
            pst.setString(2, menuCode);
            pst.setInt(3, empID);
            pst.setString(4, status);
            pst.setString(5, date);
            pst.setString(6, time);
            pst.setInt(7, orderID);
            res = pst.executeUpdate();
            count += res;
            return count;
        } catch (SQLException ex) {
            Logger.getLogger(AdminCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }

    public int deleteOrder(javax.swing.JTextField idInput) {
        String tempID = idInput.getText();
        int id = Integer.parseInt(tempID);

        try {
            pst = (PreparedStatement) con.prepareStatement("DELETE FROM orders WHERE id = (?)");
            pst.setInt(1, id);
            return pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AdminCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public Boolean exportAsPDF_Order(File filepath) {
        try {
            pst = (PreparedStatement) con.prepareStatement("SELECT * FROM orders");
            rs = pst.executeQuery();

            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(filepath + ".pdf"));

            doc.open();
            PdfPTable table = new PdfPTable(7);
            PdfPCell cell;

            String title1 = "Order ID";
            cell = new PdfPCell(new Phrase(title1));
            table.addCell(cell);

            String title2 = "CustomerID";
            cell = new PdfPCell(new Phrase(title2));
            table.addCell(cell);

            String title3 = "Menu Code";
            cell = new PdfPCell(new Phrase(title3));
            table.addCell(cell);

            String title4 = "employee ID";
            cell = new PdfPCell(new Phrase(title4));
            table.addCell(cell);

            String title5 = "Status";
            cell = new PdfPCell(new Phrase(title5));
            table.addCell(cell);

            String title6 = "Date";
            cell = new PdfPCell(new Phrase(title6));
            table.addCell(cell);

            String title7 = "Time";
            cell = new PdfPCell(new Phrase(title7));
            table.addCell(cell);

            while (rs.next()) {
                String col1 = rs.getString("id");
                cell = new PdfPCell(new Phrase(col1));
                table.addCell(cell);

                String col2 = rs.getString("customerID");
                cell = new PdfPCell(new Phrase(col2));
                table.addCell(cell);

                String col3 = rs.getString("menuCode");
                cell = new PdfPCell(new Phrase(col3));
                table.addCell(cell);

                String col4 = rs.getString("empID");
                cell = new PdfPCell(new Phrase(col4));
                table.addCell(cell);

                String col5 = rs.getString("status");
                cell = new PdfPCell(new Phrase(col5));
                table.addCell(cell);

                String col6 = rs.getString("date");
                cell = new PdfPCell(new Phrase(col6));
                table.addCell(cell);

                String col7 = rs.getString("time");
                cell = new PdfPCell(new Phrase(col7));
                table.addCell(cell);
            }

            doc.add(table);
            doc.close();
            return true;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(AdminCrud.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(AdminCrud.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AdminCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Boolean exportAsCSV_Order(File filepath) {
        try {
            FileWriter fw = new FileWriter(filepath + ".csv");

            pst = (PreparedStatement) con.prepareStatement("SELECT * FROM orders");
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
            Logger.getLogger(AdminCrud.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AdminCrud.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public void fetchCustomer(javax.swing.JTable cusTable) {
        int q;
        try {
            pst = (PreparedStatement) con.prepareStatement("SELECT * FROM customers");
            rs = pst.executeQuery();
            ResultSetMetaData rss = rs.getMetaData();
            q = rss.getColumnCount();

            DefaultTableModel df = (DefaultTableModel) cusTable.getModel();
            df.setRowCount(0);
            while (rs.next()) {
                Vector v2 = new Vector();
                for (int a = 1; a <= q; a++) {
                    v2.add(rs.getString("id"));
                }
                df.addRow(v2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int insertCustomer(javax.swing.JTextField idInput) {
        String tempID = idInput.getText();
        int id = Integer.parseInt(tempID);

        try {
            pst = (PreparedStatement) con.prepareStatement("INSERT INTO customers (id) VALUES (?)");
            pst.setInt(1, id);
            return pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AdminCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int deleteCustomer(javax.swing.JTextField idInput) {
        String tempID = idInput.getText();
        int id = Integer.parseInt(tempID);

        try {
            pst = (PreparedStatement) con.prepareStatement("DELETE FROM customers WHERE id = (?)");
            pst.setInt(1, id);
            return pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AdminCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int deleteAll(String tablename, int mode) {
        try {
            if (mode == 1) {
                pst = (PreparedStatement) con.prepareStatement("DELETE FROM " + tablename + " WHERE role = 'emp'");
            } else {
                pst = (PreparedStatement) con.prepareStatement("DELETE FROM " + tablename);
            }
            return pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AdminCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public Boolean exportAsPDF_Cus(File filepath) {
        try {
            pst = (PreparedStatement) con.prepareStatement("SELECT * FROM customers");
            rs = pst.executeQuery();

            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(filepath + ".pdf"));

            doc.open();
            PdfPTable table = new PdfPTable(1);
            PdfPCell cell;

            String title1 = "Customer ID";
            cell = new PdfPCell(new Phrase(title1));
            table.addCell(cell);

            while (rs.next()) {
                String col1 = rs.getString("id");
                cell = new PdfPCell(new Phrase(col1));
                table.addCell(cell);
            }

            doc.add(table);
            doc.close();
            return true;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(AdminCrud.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(AdminCrud.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AdminCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Boolean exportAsCSV_Cus(File filepath) {
        try {
            FileWriter fw = new FileWriter(filepath + ".csv");

            pst = (PreparedStatement) con.prepareStatement("SELECT * FROM orders");
            rs = pst.executeQuery();

            fw.append("Customer ID");
            fw.append('\n');

            while (rs.next()) {
                fw.append(rs.getString(1));
                fw.append('\n');
            }

            fw.flush();
            fw.close();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(AdminCrud.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AdminCrud.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public void resetAI(String tablename) {
        try {
            pst = (PreparedStatement) con.prepareStatement("ALTER TABLE " + tablename + " AUTO_INCREMENT = 0");
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AdminCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void fetchMenu(javax.swing.JTable menuTable) {
        int q;
        try {
            pst = (PreparedStatement) con.prepareStatement("SELECT * FROM menu");
            rs = pst.executeQuery();
            ResultSetMetaData rss = rs.getMetaData();
            q = rss.getColumnCount();

            DefaultTableModel df = (DefaultTableModel) menuTable.getModel();
            df.setRowCount(0);
            while (rs.next()) {
                Vector v2 = new Vector();
                for (int a = 1; a <= q; a++) {
                    v2.add(rs.getString("id"));
                    v2.add(rs.getString("code"));
                    v2.add(rs.getString("name"));
                    v2.add(rs.getString("type"));
                    v2.add(rs.getString("price"));
                }
                df.addRow(v2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int insertMenu(javax.swing.JTextField codeField, javax.swing.JTextField nameField, javax.swing.JComboBox<String> typeField, javax.swing.JTextField priceField) {
        String code = codeField.getText();
        String name = nameField.getText();
        String type = (String) typeField.getEditor().getItem();
        String price = priceField.getText();

        try {
            pst = (PreparedStatement) con.prepareStatement("INSERT INTO menu (code, name, type, price) VALUES (?,?,?,?)");
            pst.setString(1, code);
            pst.setString(2, name);
            pst.setString(3, type);
            pst.setString(4, price);
            return pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AdminCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int updateMenu(javax.swing.JTextField idField, javax.swing.JTextField codeField, javax.swing.JTextField nameField, javax.swing.JComboBox<String> typeField, javax.swing.JTextField priceField) {
        String idTemp = idField.getText();
        int id = Integer.parseInt(idTemp);
        String code = codeField.getText();
        String name = nameField.getText();
        String type = (String) typeField.getEditor().getItem();
        String price = priceField.getText();
        int res;

        try {
            pst = (PreparedStatement) con.prepareStatement("UPDATE menu SET code = (?), name = (?), type = (?), price = (?) WHERE id = (?)");
            pst.setString(1, code);
            pst.setString(2, name);
            pst.setString(3, type);
            pst.setString(4, price);
            pst.setInt(5, id);
            res = pst.executeUpdate();
            return res;
        } catch (SQLException ex) {
            Logger.getLogger(AdminCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int deleteMenu(javax.swing.JTextField idInput) {
        String tempID = idInput.getText();
        int id = Integer.parseInt(tempID);

        try {
            pst = (PreparedStatement) con.prepareStatement("DELETE FROM menu WHERE id = (?)");
            pst.setInt(1, id);
            return pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AdminCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public Boolean exportAsPDF_Menu(File filepath) {
        try {
            pst = (PreparedStatement) con.prepareStatement("SELECT * FROM menu");
            rs = pst.executeQuery();

            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(filepath + ".pdf"));

            doc.open();
            PdfPTable table = new PdfPTable(5);
            PdfPCell cell;

            String title1 = "Menu ID";
            cell = new PdfPCell(new Phrase(title1));
            table.addCell(cell);

            String title2 = "Menu Code";
            cell = new PdfPCell(new Phrase(title2));
            table.addCell(cell);

            String title3 = "Name";
            cell = new PdfPCell(new Phrase(title3));
            table.addCell(cell);

            String title4 = "Type";
            cell = new PdfPCell(new Phrase(title4));
            table.addCell(cell);

            String title5 = "Price";
            cell = new PdfPCell(new Phrase(title5));
            table.addCell(cell);

            while (rs.next()) {
                String col1 = rs.getString("id");
                cell = new PdfPCell(new Phrase(col1));
                table.addCell(cell);

                String col2 = rs.getString("code");
                cell = new PdfPCell(new Phrase(col2));
                table.addCell(cell);

                String col3 = rs.getString("name");
                cell = new PdfPCell(new Phrase(col3));
                table.addCell(cell);

                String col4 = rs.getString("type");
                cell = new PdfPCell(new Phrase(col4));
                table.addCell(cell);

                String col5 = rs.getString("price");
                cell = new PdfPCell(new Phrase(col5));
                table.addCell(cell);
            }

            doc.add(table);
            doc.close();
            return true;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(AdminCrud.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(AdminCrud.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AdminCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Boolean exportAsCSV_Menu(File filepath) {
        try {
            FileWriter fw = new FileWriter(filepath + ".csv");

            pst = (PreparedStatement) con.prepareStatement("SELECT * FROM menu");
            rs = pst.executeQuery();

            fw.append("Menu ID");
            fw.append(',');
            fw.append("Menu Code");
            fw.append(',');
            fw.append("Name");
            fw.append(',');
            fw.append("Type");
            fw.append(',');
            fw.append("Price");
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
                fw.append('\n');
            }

            fw.flush();
            fw.close();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(AdminCrud.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AdminCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
