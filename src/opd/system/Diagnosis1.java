package opd.system;


import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import org.mariadb.jdbc.Connection;
import org.mariadb.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
//import javax.swing.JTable;
//import net.proteanit.sql.DbUtils; // Import for ResultSet to TableModel conversion
import java.sql.PreparedStatement;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;


public class Diagnosis1 extends javax.swing.JFrame {

    private Connection con;
    private Statement st;
    private ResultSet rs;
   private int currentDiagnosisID = -1; // Initialize to -1 or any default value

    public Diagnosis1() {
  initComponents();
        this.setLocationRelativeTo(null);

        GetPatient();
        GetDoctor();
        generateNewDiagnosisID();

        DocSp.setEditable(false);

        try {
            con = (Connection) DriverManager.getConnection("jdbc:mariadb://localhost:3306/MedicalLabdb", "root", "1234");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Connection Error: " + e.getMessage());
            e.printStackTrace();
        }

        DocIdCb.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedDoctorID = (String) DocIdCb.getSelectedItem();
                    updateDoctorSpecialization(selectedDoctorID);
                }
            }
        });
    }

    private void GetPatient() {
        try {
            con = (Connection) DriverManager.getConnection("jdbc:mariadb://localhost:3306/MedicalLabdb", "root", "1234");
            st = (Statement) con.createStatement();
            rs = st.executeQuery("SELECT * FROM PatientTbl");
            while (rs.next()) {
                String MyPatient = rs.getString("PatNum");
                PatIdCb.addItem(MyPatient);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching patient data: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
            } catch (SQLException ex) {
                // Handle exception if needed
            }
        }
    }

    private void GetDoctor() {
        try {
            con = (Connection) DriverManager.getConnection("jdbc:mariadb://localhost:3306/MedicalLabdb", "root", "1234");
            st = (Statement) con.createStatement();
            rs = st.executeQuery("SELECT * FROM DoctorTbl");
            while (rs.next()) {
                int doctorID = rs.getInt("DocNum");
                DocIdCb.addItem(String.valueOf(doctorID)); // Convert int to String
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching doctor data: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
            } catch (SQLException ex) {
                // Handle exception if needed
            }
        }
    }

    private void generateNewDiagnosisID() {
        try {
            con = (Connection) DriverManager.getConnection("jdbc:mariadb://localhost:3306/MedicalLabdb", "root", "1234");
            st = (Statement) con.createStatement();
            rs = st.executeQuery("SELECT MAX(DiagnosisID) FROM DiagnosisTbl");
            if (rs.next()) {
                currentDiagnosisID = rs.getInt(1);
                currentDiagnosisID++; // Increment the current ID for the next entry
                DId.setText(String.valueOf(currentDiagnosisID));
                DId.setEditable(false);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error generating DiagnosisID: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (st != null) {
                    st.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error closing connection: " + ex.getMessage());
            }
        }
    }

    private void addDiagnosisInfo() {
        if (PatIdCb.getSelectedIndex() == -1 || DocIdCb.getSelectedIndex() == -1 || DocSp.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a Patient and Doctor.");
            return;
        }

        String patientID = (String) PatIdCb.getSelectedItem();
        String doctorID = (String) DocIdCb.getSelectedItem();
        String doctorSpecialization = DocSp.getText();

        try {
            con = (Connection) DriverManager.getConnection("jdbc:mariadb://localhost:3306/MedicalLabdb", "root", "1234");
            PreparedStatement stmt = con.prepareStatement("INSERT INTO DiagnosisTbl (DiagnosisID, PatNum, DocNum, DocSp) VALUES (?, ?, ?, ?)");
            stmt.setInt(1, currentDiagnosisID);
            stmt.setString(2, patientID);
            stmt.setString(3, doctorID);
            stmt.setString(4, doctorSpecialization);

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Diagnosis information added successfully. DiagnosisID: " + currentDiagnosisID);
                // Lock fields after adding info
                PatIdCb.setEnabled(false);
                DocIdCb.setEnabled(false);
                DocSp.setEnabled(false);
                AddInfo.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add diagnosis information");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error adding diagnosis information: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error closing connection: " + ex.getMessage());
            }
        }
    }

    private void updateDoctorSpecialization(String doctorID) {
        try {
            con = (Connection) DriverManager.getConnection("jdbc:mariadb://localhost:3306/MedicalLabdb", "root", "1234");
            PreparedStatement stmt = con.prepareStatement("SELECT DocSp FROM DoctorTbl WHERE DocNum = ?");
            stmt.setString(1, doctorID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String doctorSpecialization = rs.getString("DocSp");
                DocSp.setText(doctorSpecialization); // Set the text field with doctor specialization
                System.out.println("Doctor Specialization: " + doctorSpecialization); // Debug message
            } else {
                DocSp.setText(""); // Handle if no doctor is found
                System.out.println("Doctor Specialization not found for ID: " + doctorID); // Debug message
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error getting doctor specialization: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error closing connection: " + ex.getMessage());
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
        PatIdCb = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        DocIdCb = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        DId = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        DescTb = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        DocSp = new javax.swing.JTextField();
        AddInfo = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        MedicineNameTb = new javax.swing.JTextField();
        DosageTb = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        FrequencyTb = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        DurationTb = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        AddDrugBtn = new javax.swing.JButton();
        UpdatePrescription = new javax.swing.JButton();
        Delete = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        DrugPrescriptionTb = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        PrescriptionInfoTbl = new javax.swing.JTable();
        jLabel19 = new javax.swing.JLabel();
        SaveandPrint = new javax.swing.JButton();

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
                        .addGap(0, 0, 0)
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

        PatIdCb.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        PatIdCb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PatIdCbActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("Doc ID");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("Patient Id");

        DocIdCb.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        DocIdCb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DocIdCbActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Description");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Dignoisis ID");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Doctor Specilization");

        AddInfo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        AddInfo.setText("ADD Info");
        AddInfo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AddInfoMouseClicked(evt);
            }
        });
        AddInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddInfoActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Name");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("Dosage");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setText("Frequncy");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setText("Duration");

        AddDrugBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        AddDrugBtn.setText("Add ");
        AddDrugBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AddDrugBtnMouseClicked(evt);
            }
        });

        UpdatePrescription.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        UpdatePrescription.setText("Update");
        UpdatePrescription.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                UpdatePrescriptionMouseClicked(evt);
            }
        });

        Delete.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Delete.setText("Delete");
        Delete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DeleteMouseClicked(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setText("Drug Description");

        PrescriptionInfoTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        PrescriptionInfoTbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PrescriptionInfoTblMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(PrescriptionInfoTbl);

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setText("Doctor Specilization");

        SaveandPrint.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        SaveandPrint.setText("Save and print");
        SaveandPrint.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SaveandPrintMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 77, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(SaveandPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(DId))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(AddDrugBtn)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(UpdatePrescription)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Delete, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(PatIdCb, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(DocIdCb, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(DescTb, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(FrequencyTb, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(DrugPrescriptionTb, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(DurationTb, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addComponent(MedicineNameTb, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel13)
                                                        .addComponent(DosageTb, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                            .addGap(6, 6, 6)
                                                            .addComponent(jLabel17)))))
                                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel7)
                                                    .addComponent(jLabel10)
                                                    .addComponent(jLabel16)
                                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addComponent(DocSp, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(AddInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(908, 908, 908)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(16, 16, 16)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(PatIdCb, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DocIdCb, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DocSp)
                            .addComponent(AddInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(DescTb, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(MedicineNameTb, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(DosageTb, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(FrequencyTb, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(DurationTb, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DrugPrescriptionTb, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(AddDrugBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(UpdatePrescription, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Delete, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addComponent(SaveandPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(190, 190, 190)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(525, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void PatIdCbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PatIdCbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PatIdCbActionPerformed

    private void DocIdCbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DocIdCbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DocIdCbActionPerformed

    private void AddInfoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddInfoMouseClicked
         if (PatIdCb.getSelectedIndex() == -1 || DocIdCb.getSelectedIndex() == -1 || DocSp.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please select a Patient and Doctor.");
        return;
    }
    
    addDiagnosisInfo();
    }//GEN-LAST:event_AddInfoMouseClicked

    private void AddDrugBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddDrugBtnMouseClicked
        addPrescriptionInfo();
    }//GEN-LAST:event_AddDrugBtnMouseClicked

    private void AddInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddInfoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AddInfoActionPerformed

    private void PrescriptionInfoTblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PrescriptionInfoTblMouseClicked
     DefaultTableModel model = (DefaultTableModel) PrescriptionInfoTbl.getModel();
    int selectedRowIndex = PrescriptionInfoTbl.getSelectedRow();
    
    // Check if a row is selected
    if (selectedRowIndex >= 0) {
        MedicineNameTb.setText(model.getValueAt(selectedRowIndex, 0).toString());
        DosageTb.setText(model.getValueAt(selectedRowIndex, 1).toString());
        FrequencyTb.setText(model.getValueAt(selectedRowIndex, 2).toString());
        DurationTb.setText(model.getValueAt(selectedRowIndex, 3).toString());
        DrugPrescriptionTb.setText(model.getValueAt(selectedRowIndex, 4).toString());
        
        // enable editing of fields if needed
        MedicineNameTb.setEditable(true);
        DosageTb.setEditable(true);
        FrequencyTb.setEditable(true);
        DurationTb.setEditable(true);
        DrugPrescriptionTb.setEditable(true);
        
        
    }
    }//GEN-LAST:event_PrescriptionInfoTblMouseClicked

    private void UpdatePrescriptionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UpdatePrescriptionMouseClicked
        if (MedicineNameTb.getText().isEmpty() || DosageTb.getText().isEmpty() || FrequencyTb.getText().isEmpty() || DurationTb.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill in all prescription details");
        return;
    }

    String medicineName = MedicineNameTb.getText();
    String dosage = DosageTb.getText();
    String frequency = FrequencyTb.getText();
    String duration = DurationTb.getText();
    String drugPrescription = DrugPrescriptionTb.getText(); // Assuming DrugPrescriptionTb is the text field for drug prescription

    try {
        con = (Connection) DriverManager.getConnection("jdbc:mariadb://localhost:3306/MedicalLabdb", "root", "1234");
        PreparedStatement stmt = con.prepareStatement("UPDATE PrescriptionTbl SET MedicineName = ?, Dosage = ?, Frequency = ?, Duration = ?, DrugPrescription = ? WHERE DiagnosisID = ?");
        stmt.setString(1, medicineName);
        stmt.setString(2, dosage);
        stmt.setString(3, frequency);
        stmt.setString(4, duration);
        stmt.setString(5, drugPrescription);
        stmt.setInt(6, currentDiagnosisID); // You'll need to have a way to get the current diagnosis ID

        int rowsUpdated = stmt.executeUpdate();

        if (rowsUpdated > 0) {
            JOptionPane.showMessageDialog(this, "Prescription updated successfully");
            // Clear the text fields after updating prescription
            MedicineNameTb.setText("");
            DosageTb.setText("");
            FrequencyTb.setText("");
            DurationTb.setText("");
            DrugPrescriptionTb.setText("");
            
            // Update the PrescriptionInfoTbl with the new data
            updatePrescriptionInfoTable();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update prescription");
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error updating prescription: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error closing connection: " + ex.getMessage());
        }
    }
    }//GEN-LAST:event_UpdatePrescriptionMouseClicked

    private void DeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DeleteMouseClicked
           int selectedRowIndex = PrescriptionInfoTbl.getSelectedRow();

    // Check if a row is selected
    if (selectedRowIndex >= 0) {
        DefaultTableModel model = (DefaultTableModel) PrescriptionInfoTbl.getModel();
        
        // Get the medicine name to be deleted
        String medicineNameToDelete = model.getValueAt(selectedRowIndex, 0).toString();

        int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the medicine: " + medicineNameToDelete + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (dialogResult == JOptionPane.YES_OPTION) {
            try {
                con = (Connection) DriverManager.getConnection("jdbc:mariadb://localhost:3306/MedicalLabdb", "root", "1234");
                PreparedStatement stmt = con.prepareStatement("DELETE FROM PrescriptionTbl WHERE MedicineName = ? AND DiagnosisID = ?");
                stmt.setString(1, medicineNameToDelete);
                stmt.setInt(2, currentDiagnosisID); // Assuming you have currentDiagnosisID available
                
                int rowsDeleted = stmt.executeUpdate();
                
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(this, "Medicine deleted successfully");
                    updatePrescriptionInfoTable();
                    
                    // Clear the fields after deletion
                    MedicineNameTb.setText("");
                    DosageTb.setText("");
                    FrequencyTb.setText("");
                    DurationTb.setText("");
                    DrugPrescriptionTb.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete medicine");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error deleting medicine: " + ex.getMessage());
                ex.printStackTrace();
            } finally {
                try {
                    if (con != null) {
                        con.close();
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error closing connection: " + ex.getMessage());
                }
            }
        }
    } else {
        JOptionPane.showMessageDialog(this, "Please select a row to delete");
    }
    }//GEN-LAST:event_DeleteMouseClicked

    private void SaveandPrintMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SaveandPrintMouseClicked
        String description = DescTb.getText();

    if (description.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter a description");
        return;
    }

    // Call the addDescription method
    addDescription(description);
     // Save the diagnosis information
    addDiagnosisInfo();

    // Save the prescription information
    addPrescriptionInfo();
    }//GEN-LAST:event_SaveandPrintMouseClicked

    private void addDescription(String description) {
    try {
        con = (Connection) DriverManager.getConnection("jdbc:mariadb://localhost:3306/MedicalLabdb", "root", "1234");
        PreparedStatement stmt = con.prepareStatement("UPDATE DiagnosisTbl SET Description = ? WHERE DiagnosisID = ?");
        stmt.setString(1, description);
        stmt.setInt(2, currentDiagnosisID); // Assuming you have currentDiagnosisID available

        int rowsUpdated = stmt.executeUpdate();

        if (rowsUpdated > 0) {
            JOptionPane.showMessageDialog(this, "Description added successfully");
            // Clear the DescTb field after adding description
            DescTb.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add description");
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error adding description: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error closing connection: " + ex.getMessage());
        }
    }
}
    
    //private int currentDiagnosisID = -1; // Initialize to -1 or any default value
    
  private void addPrescriptionInfo() {
    if (MedicineNameTb.getText().isEmpty() || DosageTb.getText().isEmpty() || FrequencyTb.getText().isEmpty() || DurationTb.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill in all prescription details");
        return;
    }

    String medicineName = MedicineNameTb.getText();
    String dosage = DosageTb.getText();
    String frequency = FrequencyTb.getText();
    String duration = DurationTb.getText();
    String drugPrescription = DrugPrescriptionTb.getText(); // Assuming DrugPrescriptionTb is the text field for drug prescription

    try {
        con = (Connection) DriverManager.getConnection("jdbc:mariadb://localhost:3306/MedicalLabdb", "root", "1234");
        PreparedStatement stmt = con.prepareStatement("INSERT INTO PrescriptionTbl (MedicineName, Dosage, Frequency, Duration, DrugPrescription, DiagnosisID) VALUES (?, ?, ?, ?, ?, ?)");
        stmt.setString(1, medicineName);
        stmt.setString(2, dosage);
        stmt.setString(3, frequency);
        stmt.setString(4, duration);
        stmt.setString(5, drugPrescription);
        stmt.setInt(6, currentDiagnosisID); // You'll need to have a way to get the current diagnosis ID

        int rowsInserted = stmt.executeUpdate();

        if (rowsInserted > 0) {
            JOptionPane.showMessageDialog(this, "Prescription added successfully");
            // Clear the text fields after adding prescription
            MedicineNameTb.setText("");
            DosageTb.setText("");
            FrequencyTb.setText("");
            DurationTb.setText("");
            DrugPrescriptionTb.setText("");
            
            // Update the PrescriptionInfoTbl with the new data
            updatePrescriptionInfoTable();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add prescription");
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error adding prescription: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error closing connection: " + ex.getMessage());
        }
    }
}

   private void updatePrescriptionInfoTable() {
    try {
        con = (Connection) DriverManager.getConnection("jdbc:mariadb://localhost:3306/MedicalLabdb", "root", "1234");
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM PrescriptionTbl WHERE DiagnosisID = ?");
        stmt.setInt(1, currentDiagnosisID);

        ResultSet rs = stmt.executeQuery();

        // Convert ResultSet to DefaultTableModel
        DefaultTableModel model = (DefaultTableModel) PrescriptionInfoTbl.getModel();
        model.setRowCount(0); // Clear previous data

        // Set column headers
        String[] columnHeaders = {"Medicine Name", "Dosage", "Frequency", "Duration", "Drug Prescription"};
        model.setColumnIdentifiers(columnHeaders);

        while (rs.next()) {
            String medName = rs.getString("MedicineName");
            String medDosage = rs.getString("Dosage");
            String medFreq = rs.getString("Frequency");
            String medDur = rs.getString("Duration");
            String medPresc = rs.getString("DrugPrescription");

            model.addRow(new Object[]{medName, medDosage, medFreq, medDur, medPresc});
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error updating prescription table: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error closing connection: " + ex.getMessage());
        }
    }
}
 /*   private void addDiagnosisInfo() {
    if (PatIdCb.getSelectedIndex() == -1 || DocIdCb.getSelectedIndex() == -1) {
        JOptionPane.showMessageDialog(this, "Please select Patient ID and Doctor ID");
        return;
    }

    String patientID = (String) PatIdCb.getSelectedItem();
    String doctorID = (String) DocIdCb.getSelectedItem();
    String doctorSpecialization = DocSp.getText();

    try {
        con = (Connection) DriverManager.getConnection("jdbc:mariadb://localhost:3306/MedicalLabdb", "root", "1234");
        PreparedStatement stmt = con.prepareStatement("INSERT INTO DiagnosisTbl (PatNum, DocNum, DocSp) VALUES (?, ?, ?)");
        stmt.setString(1, patientID);
        stmt.setString(2, doctorID);
        stmt.setString(3, doctorSpecialization);
        
        int rowsInserted = stmt.executeUpdate();
        
        if (rowsInserted > 0) {
            JOptionPane.showMessageDialog(this, "Diagnosis information added successfully");
            // Lock fields after adding info
            PatIdCb.setEnabled(false);
            DocIdCb.setEnabled(false);
            DocSp.setEnabled(false);
            AddInfo.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add diagnosis information");
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error adding diagnosis information: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error closing connection: " + ex.getMessage());
        }
    }
}
*/
 

/*private void addDiagnosisInfo() {
    if (PatIdCb.getSelectedIndex() == -1 || DocIdCb.getSelectedIndex() == -1 || DocSp.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please select a Patient and Doctor.");
        return;
    }

    String patientID = (String) PatIdCb.getSelectedItem();
    String doctorID = (String) DocIdCb.getSelectedItem();
    String doctorSpecialization = DocSp.getText();

    try {
        con = (Connection) DriverManager.getConnection("jdbc:mariadb://localhost:3306/MedicalLabdb", "root", "1234");
        PreparedStatement stmt = con.prepareStatement("INSERT INTO DiagnosisTbl (PatNum, DocNum, DocSp) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, patientID);
        stmt.setString(2, doctorID);
        stmt.setString(3, doctorSpecialization);

        int rowsInserted = stmt.executeUpdate();

        if (rowsInserted > 0) {
            // Get the generated keys to retrieve the DiagnosisID
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int currentDiagnosisID = generatedKeys.getInt(1);
                
                // Print the currentDiagnosisID for debugging
                System.out.println("Current Diagnosis ID: " + currentDiagnosisID);
                
                JOptionPane.showMessageDialog(this, "Diagnosis information added successfully. DiagnosisID: " + currentDiagnosisID);
                // Lock fields after adding info
                PatIdCb.setEnabled(false);
                DocIdCb.setEnabled(false);
                DocSp.setEnabled(false);
                AddInfo.setEnabled(false);
                
                // Set the DiagnosisID to the DiagnosisID field in your form and make it non-editable
                DId.setText(String.valueOf(currentDiagnosisID));
                DId.setEditable(false);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to get DiagnosisID");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add diagnosis information");
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error adding diagnosis information: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error closing connection: " + ex.getMessage());
        }
    }
}
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
            java.util.logging.Logger.getLogger(Diagnosis1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Diagnosis1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Diagnosis1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Diagnosis1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Diagnosis1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddDrugBtn;
    private javax.swing.JButton AddInfo;
    private javax.swing.JTextField DId;
    private javax.swing.JButton Delete;
    private javax.swing.JTextField DescTb;
    private javax.swing.JComboBox<String> DocIdCb;
    private javax.swing.JTextField DocSp;
    private javax.swing.JTextField DosageTb;
    private javax.swing.JTextField DrugPrescriptionTb;
    private javax.swing.JTextField DurationTb;
    private javax.swing.JTextField FrequencyTb;
    private javax.swing.JTextField MedicineNameTb;
    private javax.swing.JComboBox<String> PatIdCb;
    private javax.swing.JTable PrescriptionInfoTbl;
    private javax.swing.JButton SaveandPrint;
    private javax.swing.JButton UpdatePrescription;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
