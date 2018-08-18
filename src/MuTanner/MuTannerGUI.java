package MuTanner;

import MuTanner.data.Hide;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MuTannerGUI extends JFrame {

    private JComboBox hideComboBox;
    private JButton initiate;

    public MuTannerGUI() {
        super("MuTanner configuration");

        setLayout(new FlowLayout());

        hideComboBox = new JComboBox(Hide.values());
        initiate = new JButton("Initiate");

        add(hideComboBox);
        add(initiate);

        initiate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MuTanner.hide = (Hide) hideComboBox.getSelectedItem();
                setVisible(false);
            }
        });

        setDefaultCloseOperation(HIDE_ON_CLOSE);
        pack();
    }
}
