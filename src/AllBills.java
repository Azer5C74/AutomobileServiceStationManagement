 //traduction faite

 import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
/**
 *
 * @author Tanmey Tsci6
 */
public class AllBills extends javax.swing.JPanel {

    String d = null;
    int j;
    String data[][] = {};
    String col[] = {"Num Facture", "Num Client", "Nom Client", "Email du client", "Date écheance", "Date", "Tax", "Tax par unité", "Total"};
    DefaultTableModel model = new DefaultTableModel(data, col);
    TableColumn column = null;
    Connection con = null;
    Date d1 = null;
    Date d2 = null;
    String sixmonth = "";

    Calendar cal = Calendar.getInstance();

    /**
     * Creates new form VehicleReport
     */
    public AllBills() {
        initComponents();
        java.util.Date dnow = new java.util.Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
        d = ft.format(dnow.getTime());
        //jButton1.setText(d);

        if ((dnow.getMonth() + 1) > 6)
            sixmonth = (dnow.getYear() + 1900) + "." + (dnow.getMonth() + 1 - 6) + "." + dnow.getDate();
        else
            sixmonth = (dnow.getYear() + 1900 + 1) + "." + (dnow.getMonth() + 13 - 6) + "." + dnow.getDate();


    }

    public void showdata(String cmd) {
        model.getRowCount();
        try {

//           if(jTable1.getRowCount()>0){
//                    while(model.getRowCount()>0){
//                        model.removeRow(0);
//                    }}
//            javax.swing.JOptionPane.showMessageDialog(null, cmd);
            con = ConnectionProvider.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(cmd);

            while (rs.next()) {

                model.insertRow(jTable1.getRowCount(), new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(6), rs.getString(5), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10)});
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jInternalFrame1 = new javax.swing.JInternalFrame();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jButton3 = new JButton();

        jInternalFrame1.setVisible(true);

        jTable1.setModel(model);
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Tous Les Rapports Des Factures ");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 255, 255)));

        jButton2.setText("Tout rapport");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setText("Trouver facture par num");

        jButton4.setText("Selectionner");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton3.setText("Imprimer");
        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 215, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addGap(10, 10, 10)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton4)
                                .addGap(332, 332, 332)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(350, 350, 350))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(52, 52, 52)
                                                .addComponent(jButton2))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(51, 51, 51)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel3)
                                                        .addComponent(jButton4).addComponent(jButton3)
                                                )))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
                jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1)
                        .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                .addGap(357, 357, 357)
                                .addComponent(jLabel1)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
                jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jInternalFrame1)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jInternalFrame1)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed


        showdata("SELECT * FROM billinfo ORDER BY Bill_no DESC");
        //  showdata("select * from addnewvehicle where Date " );
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        String aa = jTextField1.getText();
        model.getRowCount();

        try {

            if (jTable1.getRowCount() > 0) {
                while (model.getRowCount() > 0) {
                    model.removeRow(0);
                }
            }
//            javax.swing.JOptionPane.showMessageDialog(null, cmd);
            con = ConnectionProvider.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM billinfo  where Bill_no='" + aa + "' ORDER BY flddate DESC");

            while (rs.next()) {
                model.insertRow(jTable1.getRowCount(), new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(6), rs.getString(5), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10)});
            }

            if (!rs.first())
                model.insertRow(jTable1.getRowCount(), new Object[]{"aucun record trouvé", "kp", "aucun record trouvé", "kp", "aucun record trouvé", "kp", "aucun record trouvé", "kp", "aucun record trouvé", "kp", "aucun record trouvé",});
            else System.out.println(" theek ");


        } catch (Exception e) {
            System.out.println("pas trouvée");
            model.insertRow(jTable1.getRowCount(), new Object[]{"aucun record trouvé", "aucun record trouvé", "aucun record trouvé", "aucun record trouvé", "aucun record trouvé", "aucun record trouvé", "aucun record trouvé", "aucun record trouvé", "aucun record trouvé", "aucun record trouvé", "aucun record trouvé",});
            System.out.println(e.getMessage());
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(ActionEvent evt) {
        String LookFor = (jTextField1.getText());
        model.getRowCount();
        String data1[][] = new String[0][];
    /*    try{


//            javax.swing.JOptionPane.showMessageDialog(null, cmd);
            con=ConnectionProvider.getConnection();
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("SELECT * FROM billinfo  where Bill_no='" + LookFor + "' ORDER BY flddate DESC");
            while(rs.next()){
                try {
                    model.insertRow(jTable1.getRowCount(),  data1[0][0] = new String[]{rs.getString(1)}, data1[0][1] = new String[]{rs.getString(2)}, data1[2] = new String[]{rs.getString(3)}, data1[2] = new String[]{rs.getString(6)}, data1[3] = new String[]{rs.getString(5)}, data1[4] = new String[]{rs.getString(7)}, data1[2] = new String[]{rs.getString(8)}, data1[2] = new String[]{rs.getString(9)}, data1[2] = new String[]{rs.getString(10)});

                }
                catch (SQLException ex) {
                    ex.printStackTrace();
                };
            }

        if(!rs.first())
                model.insertRow(jTable1.getRowCount(), new Object[]{"aucun record trouvé","kp","aucun record trouvé","kp","aucun record trouvé","kp","aucun record trouvé","kp","aucun record trouvé","kp","aucun record trouvé",});
            else System.out.println(" theek ");


        }catch(Exception e){
            System.out.println("pas trouvée");
            model.insertRow(jTable1.getRowCount(), new Object[]{"aucun record trouvé","aucun record trouvé","aucun record trouvé","aucun record trouvé","aucun record trouvé","aucun record trouvé","aucun record trouvé","aucun record trouvé","aucun record trouvé","aucun record trouvé","aucun record trouvé",});
            System.out.println(e.getMessage());
        }
        JTable table =new JTable(data1,col);



        Document document = new Document(PageSize.A4.rotate());
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("7.pdf"));

            document.open();
            PdfContentByte cb = writer.getDirectContent();

            cb.saveState();
            Graphics2D g2 = cb.createGraphicsShapes(500, 500);

            Shape oldClip = g2.getClip();
            g2.clipRect(0, 0, 500, 500);

            jTable1.print(g2);
            g2.setClip(oldClip);

            g2.dispose();
            cb.restoreState();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        document.close();
    }*/
    }
        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JButton jButton2;
        private javax.swing.JButton jButton4;
        private javax.swing.JButton jButton3;
        private javax.swing.JInternalFrame jInternalFrame1;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JTable jTable1;
        private javax.swing.JTextField jTextField1;
        // End of variables declaration//GEN-END:variables
    }
