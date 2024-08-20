
package opd.system;

import org.mariadb.jdbc.Connection;
import org.mariadb.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils; // Import for ResultSet to TableModel conversion
//import javafx.scene.input.KeyCode;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;


public class Patients extends javax.swing.JFrame {

    
    private Connection con;
    private Statement st;
    private ResultSet rs;
    
    public Patients() {
        initComponents();
        this.setLocationRelativeTo(null);
        ShowPatients();
         try {
    con = (Connection) DriverManager.getConnection("jdbc:mariadb://localhost:3306/MedicalLabdb", "root", "1234");
       ShowPatients();
} catch (SQLException e) {
    JOptionPane.showMessageDialog(this, "Connection Error: " + e.getMessage());
    // Print the stack trace for more details
    e.printStackTrace();
}
    }
    
    private void  ShowPatients() {
    if (con == null) {
       // JOptionPane.showMessageDialog(this, "Connection is null. Make sure it's properly initialized.");
        return;
    }
    
    try {
        st = (Statement) con.createStatement();
        rs = st.executeQuery("SELECT * FROM PatientTbl");
        PatientList.setModel(DbUtils.resultSetToTableModel(rs));
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error fetching data: " + e.getMessage());
    } finally {
        try {
            if (rs != null) rs.close();
            if (st != null) st.close();
        } catch (SQLException ex) {
            // Handle exception if needed
        }
    }
}


   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        PNumTb = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        PNameTb = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        PPhoneTb = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        GenCb = new javax.swing.JComboBox<>();
        AddressTb = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        DOBTb = new com.toedter.calendar.JDateChooser();
        jLabel17 = new javax.swing.JLabel();
        DeleteBtn = new javax.swing.JButton();
        UpdateBtn = new javax.swing.JButton();
        AddBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        PatientList = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(3, 169, 244));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\Administrator\\Downloads\\icons8-patient-64.png")); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Patient");

        jPanel5.setBackground(new java.awt.Color(3, 169, 244));

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon("C:\\Users\\Administrator\\Downloads\\icons8-logout-32.png")); // NOI18N
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("Logout");
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(0, 22, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon("C:\\Users\\Administrator\\Downloads\\icons8-doctors-64.png")); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Doctor");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 49, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(131, 131, 131)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel3.setBackground(new java.awt.Color(0, 102, 255));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Laborotary Managment System");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setIcon(new javax.swing.ImageIcon("C:\\Users\\Administrator\\Downloads\\icons8-close-24 (1).png")); // NOI18N
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 546, Short.MAX_VALUE)
                .addComponent(jLabel4))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 803, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );

        PNumTb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PNumTbActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Patient Number");

        PNameTb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PNameTbActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("Patient Name");

        PPhoneTb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PPhoneTbActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("Patient PNumber");

        GenCb.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        GenCb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male ", "Female" }));
        GenCb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GenCbActionPerformed(evt);
            }
        });

        AddressTb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddressTbActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("Patient Address");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("Patient Birthday");

        DeleteBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        DeleteBtn.setText("DELETE");
        DeleteBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DeleteBtnMouseClicked(evt);
            }
        });

        UpdateBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        UpdateBtn.setText("UPDATE");
        UpdateBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                UpdateBtnMouseClicked(evt);
            }
        });

        AddBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        AddBtn.setText("ADD PATIENT");
        AddBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AddBtnMouseClicked(evt);
            }
        });

        PatientList.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        PatientList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Number", "Name", "Gender", "Adress", "Phone", "Date Of Birth"
            }
        ));
        PatientList.setRowHeight(30);
        PatientList.setRowMargin(1);
        PatientList.setSelectionBackground(new java.awt.Color(0, 102, 255));
        PatientList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PatientListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(PatientList);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(GenCb, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(PNumTb, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(PNameTb, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(PPhoneTb, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(AddressTb)
                            .addComponent(DOBTb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(AddBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(DeleteBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(UpdateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(PNumTb, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(PNameTb, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(PPhoneTb, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(GenCb, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AddressTb, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DOBTb, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(AddBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DeleteBtn)
                            .addComponent(UpdateBtn))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void PNumTbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PNumTbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PNumTbActionPerformed

    private void PNameTbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PNameTbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PNameTbActionPerformed

    private void PPhoneTbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PPhoneTbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PPhoneTbActionPerformed

    private void AddressTbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddressTbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AddressTbActionPerformed

    private void AddBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddBtnMouseClicked
       /*      if (PNumTb.getText().isEmpty() || PNameTb.getText().isEmpty() || PPhoneTb.getText().isEmpty() || AddressTb.getText().isEmpty() || GenCb.getSelectedIndex() == -1) {
        JOptionPane.showMessageDialog(this, "Missing Data");
    } else {
        try {
            con = (Connection) DriverManager.getConnection("jdbc:mariadb://localhost:3306/MedicalLabdb", "root", "1234");
            PreparedStatement Add = con.prepareStatement("INSERT INTO PatientTbl VALUES (?, ?, ?,?,?,?)");
            
            // Set the values for the parameters
            Add.setInt(1, Integer.parseInt(PNumTb.getText())); // Assuming TNumTb is for TestCode
            Add.setString(2, PNameTb.getText()); // Assuming TNameTb is for TestName
            Add.setString(3, GenCb.getSelectedItem().toString());// Assuming CostTb is for TestCost
             Add.setString(4, AddressTb.getText());
              Add.setString(5, PPhoneTb.getText());
               Add.setString(6, DOBTb.getDate().toString().substring(4,9)+DOBTb.getDate().toString().substring(22,26));
               
            
            // Execute the query
            int row = Add.executeUpdate();
            
            if (row > 0) {
                JOptionPane.showMessageDialog(this, "New Patient Added");
                ShowPatients();
               ;
                
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add new test");
            }
            
            Add.close(); // Close the PreparedStatement
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        } finally {
            // Close the connection
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error closing connection: " + ex.getMessage());
            }
        }
    }*/
       if (PNumTb.getText().isEmpty() || PNameTb.getText().isEmpty() || PPhoneTb.getText().isEmpty() || AddressTb.getText().isEmpty() || GenCb.getSelectedIndex() == -1) {
        JOptionPane.showMessageDialog(this, "Missing Data");
    } else {
        try {
            con = (Connection) DriverManager.getConnection("jdbc:mariadb://localhost:3306/MedicalLabdb", "root", "1234");
            PreparedStatement Add = con.prepareStatement("INSERT INTO PatientTbl VALUES (?, ?, ?,?,?,?)");
            
            // Set the values for the parameters
            Add.setInt(1, Integer.parseInt(PNumTb.getText())); // Assuming PNumTb is for Patient ID
            Add.setString(2, PNameTb.getText()); // Assuming PNameTb is for Patient Name
            Add.setString(3, GenCb.getSelectedItem().toString()); // Assuming GenCb is for Gender
            Add.setString(4, AddressTb.getText()); // Assuming AddressTb is for Address
            Add.setString(5, PPhoneTb.getText()); // Assuming PPhoneTb is for Phone Number
            
            // Convert JDateChooser date to SQL Date format
            java.util.Date utilDate = DOBTb.getDate();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            
            // Check if selected date is in the future
            java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
            if (sqlDate.after(currentDate)) {
                JOptionPane.showMessageDialog(this, "Date of Birth cannot be a future date.");
                return; // Stop execution if the date is in the future
            }
            
            Add.setDate(6, sqlDate); // Set the date
            
            // Execute the query
            int row = Add.executeUpdate();
            
            if (row > 0) {
                JOptionPane.showMessageDialog(this, "New Patient Added");
                ShowPatients();
                // Clear the text fields after successful addition
                PNumTb.setText("");
                PNameTb.setText("");
                PPhoneTb.setText("");
                AddressTb.setText("");
                GenCb.setSelectedIndex(-1); // Reset combobox selection
                DOBTb.setDate(null); // Reset date picker
                  PNumTb.setEditable(true);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add new patient");
            }
            
            Add.close(); // Close the PreparedStatement
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        } finally {
            // Close the connection
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error closing connection: " + ex.getMessage());
            }
        }
    }
        
    }//GEN-LAST:event_AddBtnMouseClicked

    private void PatientListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PatientListMouseClicked
     
                                           
    DefaultTableModel model = (DefaultTableModel) PatientList.getModel();
    int selectedRowIndex = PatientList.getSelectedRow();
    
    // Check if a row is selected
    if (selectedRowIndex >= 0) {
        PNumTb.setText(model.getValueAt(selectedRowIndex, 0).toString());
        PNameTb.setText(model.getValueAt(selectedRowIndex, 1).toString());
        PPhoneTb.setText(model.getValueAt(selectedRowIndex, 4).toString());
        AddressTb.setText(model.getValueAt(selectedRowIndex, 3).toString());
        GenCb.setSelectedItem(model.getValueAt(selectedRowIndex, 2).toString());
        
        // Convert String date to java.util.Date
        String dateString = model.getValueAt(selectedRowIndex, 5).toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date utilDate = sdf.parse(dateString);
            DOBTb.setDate(utilDate);
        } catch (ParseException ex) {
            // Handle parse exception
            ex.printStackTrace();
        }
        
        // Disable editing Patient ID after selection
        PNumTb.setEditable(false);
    }


    }//GEN-LAST:event_PatientListMouseClicked

    private void UpdateBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UpdateBtnMouseClicked
     /* if (PNumTb.getText().isEmpty() || PNameTb.getText().isEmpty() || PPhoneTb.getText().isEmpty() || AddressTb.getText().isEmpty() || GenCb.getSelectedIndex() == -1) {
        JOptionPane.showMessageDialog(this, "Missing Data");
    } else {
        try {
            con = (Connection) DriverManager.getConnection("jdbc:mariadb://localhost:3306/MedicalLabdb", "root", "1234");
          //  PreparedStatement Add = con.prepareStatement("update PatientTbl VALUES (?, ?, ?,?,?,?)");
          PreparedStatement Add = con.prepareStatement("UPDATE PatientTbl SET PatName=?, PatGen=?, PatAdd=?, PatPhone=?,PatDOB=? WHERE PatNum=?");
            
            // Set the values for the parameters
            Add.setInt(5, Integer.parseInt(PNumTb.getText())); // Assuming PNumTb is for Patient ID
            Add.setString(1, PNameTb.getText()); // Assuming PNameTb is for Patient Name
            Add.setString(2, GenCb.getSelectedItem().toString()); // Assuming GenCb is for Gender
            Add.setString(3, AddressTb.getText()); // Assuming AddressTb is for Address
            Add.setString(4, PPhoneTb.getText()); // Assuming PPhoneTb is for Phone Number
            
            // Convert JDateChooser date to SQL Date format
            java.util.Date utilDate = DOBTb.getDate();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            
            // Check if selected date is in the future
            java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
            if (sqlDate.after(currentDate)) {
                JOptionPane.showMessageDialog(this, "Date of Birth cannot be a future date.");
                return; // Stop execution if the date is in the future
            }
            
            Add.setDate(6, sqlDate); // Set the date
            
            // Execute the query
            int row = Add.executeUpdate();
            
            if (row > 0) {
                JOptionPane.showMessageDialog(this, " Patient Detail  Updated");
                ShowPatients();
                // Clear the text fields after successful addition
                PNumTb.setText("");
                PNameTb.setText("");
                PPhoneTb.setText("");
                AddressTb.setText("");
                GenCb.setSelectedIndex(-1); // Reset combobox selection
                DOBTb.setDate(null); // Reset date picker
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add new patient");
            }
            
            Add.close(); // Close the PreparedStatement
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        } finally {
            // Close the connection
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error closing connection: " + ex.getMessage());
            }
        }
    }*/
     if (PNumTb.getText().isEmpty() || PNameTb.getText().isEmpty() || PPhoneTb.getText().isEmpty() || AddressTb.getText().isEmpty() || GenCb.getSelectedIndex() == -1) {
        JOptionPane.showMessageDialog(this, "Missing Data");
    } else {
        try {
            con = (Connection) DriverManager.getConnection("jdbc:mariadb://localhost:3306/MedicalLabdb", "root", "1234");
            PreparedStatement Update = con.prepareStatement("UPDATE PatientTbl SET PatName=?, PatGen=?, PatAdd=?, PatPhone=?, PatDOB=? WHERE PatNum=?");
            
            // Set the values for the parameters
            Update.setString(1, PNameTb.getText()); // Assuming PNameTb is for Patient Name
            Update.setString(2, GenCb.getSelectedItem().toString()); // Assuming GenCb is for Gender
            Update.setString(3, AddressTb.getText()); // Assuming AddressTb is for Address
            Update.setString(4, PPhoneTb.getText()); // Assuming PPhoneTb is for Phone Number
            
            // Convert JDateChooser date to SQL Date format
            java.util.Date utilDate = DOBTb.getDate();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            
            // Check if selected date is in the future
            java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
            if (sqlDate.after(currentDate)) {
                JOptionPane.showMessageDialog(this, "Date of Birth cannot be a future date.");
                return; // Stop execution if the date is in the future
            }
            
            Update.setDate(5, sqlDate); // Set the date
            Update.setInt(6, Integer.parseInt(PNumTb.getText())); // Assuming PNumTb is for Patient ID
            
            // Execute the query
            int row = Update.executeUpdate();
            
            if (row > 0) {
                JOptionPane.showMessageDialog(this, "Patient Details Updated");
                ShowPatients();
                // Clear the text fields after successful update
                PNumTb.setText("");
                PNameTb.setText("");
                PPhoneTb.setText("");
                AddressTb.setText("");
                GenCb.setSelectedIndex(-1); // Reset combobox selection
                DOBTb.setDate(null); // Reset date picker
                  PNumTb.setEditable(true);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update patient");
            }
            
            Update.close(); // Close the PreparedStatement
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        } finally {
            // Close the connection
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error closing connection: " + ex.getMessage());
            }
        }
    }
    }//GEN-LAST:event_UpdateBtnMouseClicked

    private void DeleteBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DeleteBtnMouseClicked
        // TODO add your handling code here:
                                               
    // Check if Patient ID is provided
    if (PNumTb.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please select a patient to delete.");
        return;
    }
    
    // Confirm deletion with the user
    int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this patient?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
    
    if (dialogResult == JOptionPane.YES_OPTION) {
        try {
            con = (Connection) DriverManager.getConnection("jdbc:mariadb://localhost:3306/MedicalLabdb", "root", "1234");
            PreparedStatement Delete = con.prepareStatement("DELETE FROM PatientTbl WHERE PatNum=?");
            
            // Set the Patient ID for deletion
            Delete.setInt(1, Integer.parseInt(PNumTb.getText())); // Assuming PNumTb is for Patient ID
            
            // Execute the query
            int row = Delete.executeUpdate();
            
            if (row > 0) {
                JOptionPane.showMessageDialog(this, "Patient Deleted Successfully");
                ShowPatients();
                
                // Clear the text fields after successful deletion
                PNumTb.setText("");
                PNameTb.setText("");
                PPhoneTb.setText("");
                AddressTb.setText("");
                GenCb.setSelectedIndex(-1); // Reset combobox selection
                DOBTb.setDate(null); // Reset date picker
                 PNumTb.setEditable(true);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete patient");
            }
            
            Delete.close(); // Close the PreparedStatement
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        } finally {
            // Close the connection
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error closing connection: " + ex.getMessage());
            }
        
    }
}

    }//GEN-LAST:event_DeleteBtnMouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
       System.exit(0);
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
         new Login().setVisible(true); // Create a new instance of Patients frame
                this.dispose();
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
         new Login().setVisible(true); // Create a new instance of Patients frame
                this.dispose();
    }//GEN-LAST:event_jLabel11MouseClicked

    private void GenCbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GenCbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_GenCbActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Patients.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Patients.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Patients.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Patients.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Patients().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddBtn;
    private javax.swing.JTextField AddressTb;
    private com.toedter.calendar.JDateChooser DOBTb;
    private javax.swing.JButton DeleteBtn;
    private javax.swing.JComboBox<String> GenCb;
    private javax.swing.JTextField PNameTb;
    private javax.swing.JTextField PNumTb;
    private javax.swing.JTextField PPhoneTb;
    private javax.swing.JTable PatientList;
    private javax.swing.JButton UpdateBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
