/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Timer;

/**
 *
 * @author FisayoCaleb
 */
public class GUIMulticast extends javax.swing.JPanel {

    /**
     * Creates new form GUIMulticast
     */
     private final SimpleDateFormat date = new SimpleDateFormat("HH.mm.ss");
    private long startTime;
    private final ClockListener clock = new ClockListener();
    public final Timer timer = new Timer(53, (ActionListener) clock);
    public GUIMulticast() {
        initComponents();
        startTime = System.currentTimeMillis();
        timer.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        lblDuration = new javax.swing.JLabel();
        cmdMute2 = new javax.swing.JToggleButton();

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/icons/room1.png"))); // NOI18N

        lblStatus.setText("Calling ...");

        jLabel1.setText("Duration:");

        lblUser.setText("User");

        lblDuration.setText("time");

        cmdMute2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/icons/mute-51.png"))); // NOI18N
        cmdMute2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdMute2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(lblStatus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblUser))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblDuration)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addComponent(cmdMute2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStatus)
                    .addComponent(lblUser))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblDuration)))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmdMute2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmdMute2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdMute2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdMute2ActionPerformed

private class ClockListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            updateClock();
        }
    }

    private void updateClock() {
        Date elapsed = new Date(System.currentTimeMillis() - startTime - 3600000);
        lblDuration.setText(date.format(elapsed));
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JToggleButton cmdMute2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    public static javax.swing.JLabel lblDuration;
    public static javax.swing.JLabel lblStatus;
    public static javax.swing.JLabel lblUser;
    // End of variables declaration//GEN-END:variables
}
