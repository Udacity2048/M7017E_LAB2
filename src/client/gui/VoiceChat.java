/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.gui;

import config.Constants;
import client.core.EventListener;
import config.Network;
import client.core.VCClient;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.*;
import org.gstreamer.Gst;
import client.pipelines.ReceivingPipeline;
import client.pipelines.SendingPipeline;

/**
 *
 * @author Fisayo
 */
public class VoiceChat extends javax.swing.JFrame implements EventListener {

    /**
     * Creates new form VoiceChat
     */
    protected VCClient client = null;
    String clientName = null;
    String ServerAddress = null;
    Network.Room[] rooms = null;
    int lastSelected = -1;
    ArrayList<Network.Client> list;
    GUICaller reciever = null;
    GUICaller caller = null;
    GUICalling calling = null;
    GUICalling recieving = null;
    GUIMulticast multicast = null;
    long ssrc;
    SendingPipeline transmitterpipe;
    ReceivingPipeline receiverpipe;
    Object refreshMsg = null;

    public VoiceChat() {
        initComponents();
        myInitComponents();
        transmitterpipe = new SendingPipeline();
        receiverpipe = new ReceivingPipeline();
    }

    public void fireEvent(int message, Object o) {

        switch (message) {

            //new clients liste received
            case Constants.EVT_CLIENT_LIST_RECEIVED:
                System.out.println("CLIENTS:");
                list = (ArrayList<Network.Client>) o;

                DefaultListModel clientModel = new DefaultListModel();
                int i = 0;
                for (Network.Client c : list) {
                    System.out.println(c.id + " - " + c.ip + " - " + c.name);
                    if (c.id != client.getID()) {
                        clientModel.add(i, c.name);
                        i++;
                    }

                }
                lsClient.setModel(clientModel);
                break;

            //new rooms array received   
            case Constants.EVT_ROOM_ARRAY_RECEIVED:
                rooms = (Network.Room[]) o;
                refreshList(rooms);
                if (lastSelected != -1) {
                    updateRoomClient(lastSelected);
                }
                break;

            //new message from another client received 
            case Constants.EVT_MESSAGE_FROM_ANOTHER_CLIENT:
                try {
                    Network.Message m = (Network.Message) o;
                    System.out.println(m.text + "from " + m.sender.name);
                    if (m.text.equals("calling")) {
                        reciever(m);
                    } else if (m.text.equals("dropped")) {
                        callDropped();
                        Window w = SwingUtilities.getWindowAncestor(calling);
                        closeDialog(w);
                    } else if (m.text.equals("ended")) {
                        callDropped();
                        Window w = SwingUtilities.getWindowAncestor(recieving);
                        closeDialog(w);
                    } else if (m.text.equals("recieved")) {
                        if (!receiverpipe.isReceivingFromUnicast()) {
                            receiverpipe.startReceivingFromUnicast();
                            receiverpipe.printPipeline();
                        }
                    } else if (m.text.equals("rejected")) {
                        if (transmitterpipe.isStreamingToUnicast()) {
                            transmitterpipe.stopStreamingToUnicast();
                            transmitterpipe.printPipeline();
                            Window w = SwingUtilities.getWindowAncestor(calling);
                            closeDialog(w);
                        }
                    }

                } catch (Exception e) {
                    System.out.println(e);
                }

                break;

        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        args = Gst.init("AudioCapture", args);

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VoiceChat().setVisible(true);
            }
        });

        // TODO code application logic here
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane5 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jScrollPane3 = new javax.swing.JScrollPane();
        lsClient = new javax.swing.JList();
        cmdCall = new javax.swing.JButton();
        cmdJoinRoom = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        lsRoom = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        lsRoomClient = new javax.swing.JList();
        cmdRefresh = new javax.swing.JButton();

        jTree1.setBorder(javax.swing.BorderFactory.createTitledBorder("Chat rooms"));
        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Room1");
        javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Jean");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Charles");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Camille");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Room2");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Cindy");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Romain");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Benoit");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane5.setViewportView(jTree1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        lsClient.setBorder(javax.swing.BorderFactory.createTitledBorder("Connected People"));
        lsClient.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane3.setViewportView(lsClient);

        cmdCall.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/icons/call-user1.png"))); // NOI18N
        cmdCall.setText("Call");
        cmdCall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCallActionPerformed(evt);
            }
        });

        cmdJoinRoom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/icons/room1.png"))); // NOI18N
        cmdJoinRoom.setText("Join");
        cmdJoinRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdJoinRoomActionPerformed(evt);
            }
        });

        lsRoom.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        lsRoom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lsRoomMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(lsRoom);

        jScrollPane2.setViewportView(lsRoomClient);

        cmdRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/icons/reload1.png"))); // NOI18N
        cmdRefresh.setText("Refresh List");
        cmdRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmdRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmdCall, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cmdJoinRoom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmdCall, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmdJoinRoom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdRefresh)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmdJoinRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdJoinRoomActionPerformed
        // TODO add your handling code here:
        if (lsRoom.isSelectionEmpty()) {
            String infoMessage = "Please Select Room";
            String titleBar = "Select Room";
            JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
        } else {
            int i = lsRoom.getSelectedIndex();
            lastSelected = i;
            client.sendJoinRoom(i);
            System.out.println(lastSelected);
            String multicast = rooms[lastSelected].multicastIP;
            System.out.println(multicast);
            ssrc = transmitterpipe.startStreamingToMulticast(multicast);
            receiverpipe.startReceivingFromMulticast(multicast, ssrc);
            //client.sendRefreshLists();
            joinRoom();
        }
    }//GEN-LAST:event_cmdJoinRoomActionPerformed

    private void lsRoomMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lsRoomMouseClicked
        // TODO add your handling code here:
        int i = lsRoom.getSelectedIndex();
        updateRoomClient(i);
    }//GEN-LAST:event_lsRoomMouseClicked

    private void cmdCallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCallActionPerformed
        // TODO add your handling code here:

        if (lsClient.isSelectionEmpty()) {
            String infoMessage = "Please Select User";
            String titleBar = "Select User";
            JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
        } else {
            makeCall();
        }


    }//GEN-LAST:event_cmdCallActionPerformed

    private void cmdRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRefreshActionPerformed
        client.sendRefreshLists();
    }//GEN-LAST:event_cmdRefreshActionPerformed

    private void myInitComponents() {
        initialInput();
        ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/client/icons/users.png")));
        this.setIconImage(icon.getImage());
    }

    private void initialInput() {
        final GUIStartup initia = new GUIStartup();

        int result = JOptionPane.showConfirmDialog(null, initia,
                "Please enter Server Address and Username", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            ServerAddress = initia.txtSAddress.getText();
            clientName = initia.txtUsername.getText();
            this.setTitle(clientName);
            connect();
        } else {
            System.exit(0);
        }
    }

    private void connect() {
        client = new VCClient(ServerAddress, clientName);
        client.setListener(this);
        client.connect();
        try {
            Thread.sleep(100);
            //client.sendMessage(c, "TEST MESSAGE");
        } catch (InterruptedException ex) {
            Logger.getLogger(VoiceChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateRoomClient(int i) {
        lsRoom.setSelectedIndex(i);
        DefaultListModel clientRModel = new DefaultListModel();
        int j = 0;
        for (Network.Client c : rooms[i].clients) {
            clientRModel.add(j, c.name);
            System.out.println("-- " + c.name);
            j++;
        }
        lsRoomClient.setModel(clientRModel);

    }

    private void calling(final Network.Client b) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                client.sendMessage(b, "calling");
                transmitterpipe.startStreamingToUnicast(b.ip);
                //receiverpipe.startReceivingFromUnicast();
                calling = new GUICalling();
                calling.lblUser.setText(b.name);
                calling.cmdMute2.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        if (calling.cmdMute2.isSelected()) {
                            //receiverpipe.stopReceivingFromUnicast();
                            receiverpipe.muteSound();
                        } else {
                            //receiverpipe.startReceivingFromUnicast();
                            receiverpipe.unmuteSound();
                        }
                    }
                });
                Object[] options = {"End Call"};
                int result = JOptionPane.showOptionDialog(null, calling, "Calling " + b.name, JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                try {
                    if (result == JOptionPane.YES_OPTION) {
                        calling.timer.stop();
                        if (transmitterpipe.isStreamingToUnicast() && transmitterpipe != null) {
                            client.sendMessage(b, "ended");
                            transmitterpipe.stopStreamingToUnicast();
                        }
                        if (receiverpipe.isReceivingFromUnicast() && receiverpipe != null) {
                            receiverpipe.stopReceivingFromUnicast();
                        }
                        cmdCall.setEnabled(true);
                    } else {
                        calling.timer.stop();
                        if (transmitterpipe.isStreamingToUnicast() && transmitterpipe != null) {
                            client.sendMessage(b, "ended");
                            transmitterpipe.stopStreamingToUnicast();
                        }
                        if (receiverpipe.isReceivingFromUnicast() && receiverpipe != null) {
                            receiverpipe.stopReceivingFromUnicast();
                        }
                        cmdCall.setEnabled(true);
                        Window w = SwingUtilities.getWindowAncestor(calling);
                        closeDialog(w);

                    }
                } catch (NullPointerException e) {
                    System.out.println(e.getMessage());
                }

            }

        });
    }

    private void caller(final Network.Client b) {
        cmdCall.setEnabled(false);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                caller = new GUICaller();
                caller.lblCallerID.setText(Integer.toString(b.id));
                caller.lblIP.setText(b.ip);
                caller.lblUsername.setText(b.name);
                final Object[] options = {"Place Call"};
                int result = JOptionPane.showOptionDialog(null, caller, "Call " + b.name, JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                if (result == JOptionPane.YES_OPTION) {
                    calling(b);
                } else {
                    cmdCall.setEnabled(true);
                    Window w = SwingUtilities.getWindowAncestor(caller);
                    closeDialog(w);
                }
            }
        });

    }

    private void reciever(final Network.Message m) {
        cmdCall.setEnabled(false);
        reciever = new GUICaller();
        reciever.lblCallerID.setText(Integer.toString(m.sender.id));
        reciever.lblIP.setText(m.sender.ip);
        reciever.lblUsername.setText(m.sender.name);
        final Object[] options = {"Recieve Call"};
        final JOptionPane pane = new JOptionPane(reciever, JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        final JDialog dialog = new JDialog(this, "Call from " + m.sender.name);
        dialog.setContentPane(pane);
        dialog.setModal(false);
        dialog.setResizable(false);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                cmdCall.setEnabled(true);
                client.sendMessage(m.sender, "rejected");
                dialog.dispose();
            }
        });
        pane.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                String prop = pce.getPropertyName();
                if (dialog.isVisible()
                        && (pce.getSource() == pane)
                        && (prop.equals(JOptionPane.VALUE_PROPERTY))) {
                    String value = pane.getValue().toString();
                    System.out.println(pane.getValue());
                    if (value.equals("Recieve Call")) {
                        recieving(m);
                    } else {
                        cmdCall.setEnabled(true);
                        client.sendMessage(m.sender, "rejected");
                        dialog.dispose();
                    }
                    dialog.setVisible(false);
                }

            }
        });
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

    }

    private void recieving(final Network.Message m) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                client.sendMessage(m.sender, "recieved");
                transmitterpipe.startStreamingToUnicast(m.sender.ip);
                receiverpipe.startReceivingFromUnicast();
                receiverpipe.printPipeline();
                recieving = new GUICalling();
                recieving.lblUser.setText(m.sender.name);
                recieving.lblStatus.setText("Call From ");
                recieving.cmdMute2.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        if (recieving.cmdMute2.isSelected()) {
                            receiverpipe.muteSound();
                            //receiverpipe.stopReceivingFromUnicast();
                        } else {
                            //receiverpipe.startReceivingFromUnicast();
                            receiverpipe.unmuteSound();
                        }
                    }
                });
                Object[] options = {"Drop Call"};
                int result = JOptionPane.showOptionDialog(null, recieving, "recieving from " + m.sender.name, JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                try {
                    if (result == JOptionPane.YES_OPTION) {
                        recieving.timer.stop();

                        if (receiverpipe.isReceivingFromUnicast()) {
                            client.sendMessage(m.sender, "dropped");
                            receiverpipe.stopReceivingFromUnicast();
                        }
                        if (transmitterpipe.isStreamingToUnicast() && transmitterpipe != null) {
                            transmitterpipe.stopStreamingToUnicast();
                        }
                        cmdCall.setEnabled(true);
                    } else {
                        recieving.timer.stop();
                        if (receiverpipe.isReceivingFromUnicast()) {
                            client.sendMessage(m.sender, "dropped");
                            receiverpipe.stopReceivingFromUnicast();
                        }
                        if (transmitterpipe.isStreamingToUnicast() && transmitterpipe != null) {
                            transmitterpipe.stopStreamingToUnicast();
                        }
                        cmdCall.setEnabled(true);
                        Window w = SwingUtilities.getWindowAncestor(recieving);
                        closeDialog(w);
                    }

                } catch (NullPointerException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
    }

    private void joinRoom() {
        cmdJoinRoom.setEnabled(false);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                multicast = new GUIMulticast();
                multicast.lblUser.setText(rooms[lastSelected].name);
                multicast.cmdMute2.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        if (multicast.cmdMute2.isSelected()) {
                            receiverpipe.muteSound();
                            //receiverpipe.stopReceivingFromUnicast();
                        } else {
                            //receiverpipe.startReceivingFromUnicast();
                            receiverpipe.unmuteSound();
                        }
                    }
                });
                Object[] options = {"Leave Room"};
                int result = JOptionPane.showOptionDialog(null, multicast, "calling " + rooms[lastSelected].name, JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                if (result == JOptionPane.YES_OPTION) {
                    multicast.timer.stop();
                    if (receiverpipe.isReceivingFromMulticast()) {
                        client.sendLeaveRoom();
                        transmitterpipe.stopStreamingToMulticast();
                        receiverpipe.stopReceivingFromMulticast();
                        ssrc = 0;

                    }
                    cmdJoinRoom.setEnabled(true);
                } else {
                    multicast.timer.stop();
                    if (receiverpipe.isReceivingFromMulticast()) {
                        client.sendLeaveRoom();
                        transmitterpipe.stopStreamingToMulticast();
                        receiverpipe.stopReceivingFromMulticast();
                        ssrc = 0;

                    }
                    cmdJoinRoom.setEnabled(true);
                }

            }
        });

    }

    private void callDropped() {
        try {
            if (transmitterpipe.isStreamingToUnicast()) {
                transmitterpipe.stopStreamingToUnicast();

            }
            if (receiverpipe.isReceivingFromUnicast()) {
                receiverpipe.stopReceivingFromUnicast();

            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

    }

    private void makeCall() {

        Network.Client b = null;
        for (Network.Client c : list) {
            if (c.name == lsClient.getSelectedValue().toString() && c.id != client.getID()) {
                b = c;
            }
        }
        if (b.id != client.getID()) {
            caller(b);
        } else {
            String infoMessage = "You cannot Dial Yourself or Same IP";
            String titleBar = "Select Another User";
            JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
        }

    }

    private void refreshList(Network.Room[] rooms) {
        int i;
        System.out.println("ROOMS:");
        DefaultListModel roomModel = new DefaultListModel();
        i = 0;
        for (Network.Room r : rooms) {
            roomModel.add(i, r.name);
            System.out.println("ROOM: " + r.name);
            for (Network.Client c : r.clients) {
                System.out.println("-- " + c.name);
            }

            i++;
        }
        lsRoom.setModel(roomModel);
    }

    private void closeDialog(Window w) {
        if (w != null && w.isActive()) {
            w.dispose();
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdCall;
    private javax.swing.JButton cmdJoinRoom;
    private javax.swing.JButton cmdRefresh;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTree jTree1;
    private javax.swing.JList lsClient;
    private javax.swing.JList lsRoom;
    private javax.swing.JList lsRoomClient;
    // End of variables declaration//GEN-END:variables
}
