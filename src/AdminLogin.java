import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AdminLogin implements ActionListener {

    String LABEL_FONT1 = "Century Schoolbook L";

    int FONT1 = 1;
    int FONT2 = 1;
    int FONT_SIZE = 27;
    int L_FONT_SIZE = 30;

    JPanel p_panel, p_logIn, p_temp, p_buttons;
    GridLayout l_temp;
    JLabel l_logIn, l_adminId, l_password, l_head;
    JButton b_logIn, b_back;
    JTextField t_adminId;
    JPasswordField p_passField;
    JCheckBox checkbox;
    JFrame frame;

    AdminLogin() {

        addComponentToPane();
    }

    private JLabel getLlogin() {
        JLabel lLogin = new JLabel("Admin Access");

        return lLogin;
    }

    private void addComponentToPane() {

        frame = new JFrame("Pay Me");

        p_panel = new JPanel();
        p_logIn = new JPanel();

        l_logIn = getLlogin();
        l_adminId = new JLabel("Admin ID :");
        l_password = new JLabel("Password :");

        b_logIn = new JButton("Sign In");
        b_back = new JButton("Back");

        checkbox = new JCheckBox("Show Password                      ", false);
        checkbox.setFont(new Font(LABEL_FONT1, FONT2, 16));

        p_logIn.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1d;
        gbc.gridwidth = 3;
        gbc.gridx = 0;
        gbc.gridy = 0;

        l_logIn.setFont(new Font(LABEL_FONT1, FONT2, 55));
        p_logIn.add(l_logIn, gbc);
        gbc.gridy++;

        l_head = new JLabel(" ");
        l_head.setFont(new Font(LABEL_FONT1, FONT2, 30));
        p_logIn.add(l_head, gbc);
        gbc.gridy++;

        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 3;
        gbc.gridy++;

        l_adminId.setFont(new Font(LABEL_FONT1, FONT2, FONT_SIZE));
        p_logIn.add(l_adminId, gbc);

        gbc.gridx++;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 70);
        t_adminId = new JTextField("");
        t_adminId.setFont(new Font(LABEL_FONT1, FONT1, FONT_SIZE));

        p_logIn.add(t_adminId, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;

        l_password.setFont(new Font(LABEL_FONT1, FONT2, FONT_SIZE));
        p_logIn.add(l_password, gbc);

        gbc.gridx++;
        gbc.gridwidth = 2;

        p_passField = new JPasswordField("");
        p_passField.setFont(new Font(LABEL_FONT1, FONT1, FONT_SIZE));
        p_passField.setEchoChar('*');
        p_logIn.add(p_passField, gbc);

        gbc.gridx = 1;
        gbc.gridy++;
        gbc.gridwidth = 2;

        p_logIn.add(checkbox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 3;

        p_logIn.add(new JLabel(), gbc);
        gbc.gridy++;
        p_logIn.add(new JLabel(), gbc);
        gbc.gridy++;
        gbc.gridwidth = 3;

        b_logIn.setBackground(Color.WHITE);
        b_logIn.setForeground(Color.BLACK);
        b_logIn.setFont(new Font(LABEL_FONT1, Font.BOLD, 25));
        b_back.setBackground(Color.WHITE);
        b_back.setForeground(Color.BLACK);
        b_back.setFont(new Font(LABEL_FONT1, Font.BOLD, 25));

        GridLayout button_layout = new GridLayout(1, 2);
        button_layout.setHgap(80);
        p_buttons = new JPanel();
        p_buttons.setLayout(button_layout);
        p_buttons.setBorder(new EmptyBorder(0, 30, 0, 30));

        p_buttons.add(b_logIn);
        p_buttons.add(b_back);
        p_logIn.add(p_buttons, gbc);

        p_panel.add(p_logIn);

        l_head = new JLabel("  ");
        l_head.setHorizontalAlignment(JLabel.CENTER);
        l_head.setFont(new Font(LABEL_FONT1, Font.BOLD, 50));

        p_temp = new JPanel(new BorderLayout());

        p_temp.add(l_head, BorderLayout.CENTER);
        l_head = new JLabel(" ");
        l_head.setHorizontalAlignment(JLabel.CENTER);
        l_head.setFont(new Font(LABEL_FONT1, Font.BOLD, 10));
        p_temp.add(l_head, BorderLayout.SOUTH);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(p_temp, BorderLayout.NORTH);

        frame.getContentPane().add(p_panel, BorderLayout.CENTER);
        frame.pack();
        frame.setSize(1400, 900);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        checkbox.addActionListener(this);
        b_logIn.addActionListener(this);
        b_back.addActionListener(this);
    }

    private static boolean isEmptyFields(String adminname, String password) {

        return (adminname.equals("") || password.equals(""));

    }

    private static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c))
                return false;
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        try {

            if (ae.getSource() == checkbox) {
                if (checkbox.isSelected()) {
                    p_passField.setEchoChar((char) 0);
                } else {
                    p_passField.setEchoChar('*');
                }
            }

            MysqlConnection connection = new MysqlConnection();
            if (ae.getSource() == b_logIn) {

                String s_adminId = t_adminId.getText();
                String password = String.valueOf(p_passField.getPassword());

                if (isEmptyFields(s_adminId, password)) {
                    JOptionPane.showMessageDialog(null, "Please Fill all the details!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else if (!isNumeric(s_adminId)) {
                    JOptionPane.showMessageDialog(null, "Admin Id should be numeric!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    int adminId = Integer.parseInt(s_adminId);

                    String q1 = "SELECT * FROM admin WHERE id = '" + adminId + "'";

                    ResultSet rs_logIn = connection.statement.executeQuery(q1);
                    if (rs_logIn.next()) {
                        String DB_password = rs_logIn.getString("password");
                        if (password.equals(DB_password)) {

                            new AdminTransaction().setVisible(true);
                            connection.connection.close();
                            new AdminTransaction().setVisible(true);
                            frame.setVisible(false);
                        } else {
                            JOptionPane.showMessageDialog(null, "Incorrect Password!", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect adminID!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            if (ae.getSource() == b_back) {
                connection.connection.close();
                new HomeScreen();
                frame.setVisible(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
