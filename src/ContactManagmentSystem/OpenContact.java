package ContactManagmentSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class OpenContact {

    public OpenContact(final Contact c, String oper, final Database database, final ContactsList contacts) throws SQLException {
        final JFrame frame = new JFrame("Contacts Management System");
        frame.setLayout(new BorderLayout());
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.white);
        JPanel table = new JPanel(new GridLayout(6, 2, 15, 15));
        table.setBackground(Color.white);
        table.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        table.add(GUI.label("ID : "));
        final JLabel id = GUI.label(String.valueOf(c.getID()));
        table.add(id);
        table.add(GUI.label("first name : "));
        final JTextField firstname = GUI.textField(String.valueOf(c.getFirstname()));
        table.add(firstname);
        table.add(GUI.label("Last name : "));
        final JTextField lastname = GUI.textField(String.valueOf(c.getLastname()));
        table.add(lastname);
        table.add(GUI.label("Phone Number : "));
        final JTextField phoneNumber = GUI.textField(String.valueOf(c.getPhoneNumber()));
        table.add(phoneNumber);
        table.add(GUI.label("Email : "));
        final JTextField email = GUI.textField(String.valueOf(c.getEmail()));
        table.add(email);

        JButton cancel = GUI.button("Cancel", new Color(208, 11, 3));
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        table.add(cancel);
        JButton save = GUI.button("Save", new Color(12, 131, 32));
        table.add(save);

        frame.add(table, BorderLayout.CENTER);


        if (oper.equals("create")) {
            final int ID = database.getNextID();
            id.setText(String.valueOf(ID));
            save.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    c.setID(ID);
                    c.setFirstname(firstname.getText());
                    c.setLastname(lastname.getText());
                    c.setPhoneNumber(phoneNumber.getText());
                    c.setEmail(email.getText());
                    try {
                        database.insertContact(c);
                        frame.dispose();
                        contacts.refresh(database.getContacts());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                }
            });

        }

        else if (oper.equals("edit")) {


            save.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    c.setFirstname(firstname.getText());
                    c.setLastname(lastname.getText());
                    c.setPhoneNumber(phoneNumber.getText());
                    c.setEmail(email.getText());
                    try {
                        database.updateContact(c);
                        frame.dispose();
                        contacts.refresh(database.getContacts());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                }
            });


        }
        else if (oper.equals("view")) {

save.setVisible(false);

          cancel.setVisible(false);
            firstname.setEditable(false);


        }


        frame.setVisible(true);
    }
}
