package ContactManagmentSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContactsList {

    private  GridLayout gridLayout;
    private  JPanel table;
    private  Database database;

    public ContactsList(final Database database) throws SQLException {
        this.database = database;

        ArrayList<Contact> contacts = database.getContacts();
        JFrame frame = new JFrame("Contacts managment System");
        frame.setLayout(new BorderLayout());
        frame.setSize(1200, 700);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.white);

        JPanel top = new JPanel(new BorderLayout());
        top.setBorder(BorderFactory.createEmptyBorder(77, 50, 50, 50));
        top.setBackground(null);

        JLabel titel = new JLabel("Welcome to Contacts managment");
        titel.setFont(new Font("Calibri", Font.BOLD, 35));
        titel.setHorizontalAlignment(JLabel.CENTER);
        titel.setVerticalAlignment(JLabel.CENTER);
        top.add(titel, BorderLayout.CENTER);

        JButton newContact = new JButton("new Contact");
        newContact.setFont(new Font("Tahoma", Font.BOLD, 20));
        newContact.setBackground(new Color(88, 179, 88));
        newContact.setForeground(Color.white);
        newContact.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        newContact.setCursor(new Cursor(Cursor.HAND_CURSOR));
        newContact.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new OpenContact(new Contact(),"create",database,ContactsList.this);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });


        top.add(newContact, BorderLayout.EAST);
        frame.add(top, BorderLayout.NORTH);

        gridLayout = new GridLayout(8, 1, 0, 0);
        table = new JPanel(gridLayout);
        table.setBackground(Color.white);
        refresh(contacts);

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createEmptyBorder(0, 50, 50, 50));
        sp.setBackground(null);
        frame.add(sp, BorderLayout.CENTER);


        frame.setVisible(true);
    }
    public void refresh(final ArrayList<Contact> contacts) {
        table.removeAll();
        table.repaint();
        table.revalidate();
        int rows = contacts.size();
        if(rows < 8) rows = 8;
        gridLayout.setRows(rows);
        for (int i = 0; i < contacts.size(); i++) {
           final Contact c = contacts.get(i);
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
            panel.setBackground(null);
            panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
            if (i % 2 == 0) panel.setBackground(Color.decode("#e5e5e5"));
            panel.setPreferredSize(new Dimension(10, 55));
            panel.add(GUI.label(c.getFirstname() + " " + c.getLastname()));
            panel.add(GUI.label(c.getPhoneNumber()));
            panel.add(GUI.label(c.getEmail()));
            JButton view = GUI.button("view", new Color(88, 179, 99));
            view.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        new OpenContact(c,"view",database,ContactsList.this);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });
            panel.add(view);
            JButton edit = GUI.button("edit", new Color(63, 134, 196));
            edit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        new OpenContact(c,"edit",database,ContactsList.this);
                        refresh(database.getContacts());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });
            panel.add(edit);
            JButton delete = GUI.button("delete", new Color(208, 11, 3));
            delete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        database.deleteContact(c);
                        refresh(database.getContacts());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });
            panel.add(delete);
            table.add(panel);
        }
}

}
