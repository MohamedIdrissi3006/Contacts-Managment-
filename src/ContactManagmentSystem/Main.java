package ContactManagmentSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {




    public static void main(String[] args) throws SQLException {


        new ContactsList(new Database());
    }


    }

