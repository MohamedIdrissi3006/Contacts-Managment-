package ContactManagmentSystem;

import java.sql.*;
import java.util.ArrayList;

public class Database {
    String dbUrl = System.getenv("DB_URL");
    String user = System.getenv("USER");
    String pwd = System.getenv("PASS");
    private Statement statement;

    public Database() throws SQLException {
        Connection connection = DriverManager.getConnection(dbUrl,user,pwd);
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        System.out.println("✅ Database connection successful!");
    }
    public ArrayList<Contact> getContacts() throws SQLException{
        ArrayList<Contact> contacts = new ArrayList<Contact>();
        String select =  "SELECT * FROM `contacts`;";
        ResultSet rs = statement.executeQuery(select);
        while(rs.next()){
            Contact c = new Contact();
            c.setID(rs.getInt("ID"));
            c.setFirstname(rs.getString("firstName"));
            c.setLastname(rs.getString("lastName"));
            c.setPhoneNumber(rs.getString("phoneNumber"));
            c.setEmail(rs.getString("email"));
            contacts.add(c);
        }
        return contacts;

    }

    public void insertContact(Contact c) throws SQLException {
        String insert = "INSERT INTO `contacts`(`ID`, `firstName`, `lastName`, `phoneNumber`, `email`) VALUES " +
                "('"+c.getID()+"','"+
                c.getFirstname()+"','"+c.getLastname()+"','"
                +c.getPhoneNumber()+"','"+c.getEmail()+"')"+";";
    statement.execute(insert);
    }
    public void updateContact(Contact c) throws SQLException {
        String update = "UPDATE `contacts` SET `firstName`='"+c.getFirstname()+
                "',`lastName`='"+c.getLastname()+
                "',`phoneNumber`='"+c.getPhoneNumber()+
                "',`email`='"+c.getEmail()+
                "' WHERE `ID`="+c.getID()+";";
        statement.execute(update);
    }
    public void deleteContact(Contact c) throws SQLException {
        String delete = "DELETE FROM `contacts` WHERE `ID`="+c.getID()+";";
        statement.execute(delete);
    }

    public int getNextID() throws SQLException {
        int id =0;
        ArrayList<Contact> contacts = getContacts();
        if(contacts.size()!=0){
            Contact last = contacts.get(contacts.size()-1);
            id = last.getID()+1;

        }
        return id;
    }

}
