import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import javax.swing.*;
import org.jdesktop.swingx.JXDatePicker;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;
import java.text.DateFormat;
import java.util.Date;
import java.util.Calendar;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HomeScreen implements ActionListener {

    String LABEL_FONT1 = "Century Schoolbook L";
    int FONT1 = 0;
    int FONT2 = 1;
    int FONT_SIZE = 25;
    int L_FONT_SIZE = 30;

    JPanel p_container, p_panel, p_logIn, p_signUp, p_temp;
    GridLayout l_gridLayout1, l_temp;
    JLabel l_logIn, l_userId, l_password, l_signUp, l_userName, l_DOB, l_email, l_head;
    JButton b_logIn, b_signUp;
    JTextField t_email, t_userName, t_userId;
    JPasswordField p_passField;
    JCheckBox checkbox;
    JXDatePicker picker;
    JFrame frame;

    HomeScreen() {

        addComponentToPane();
    }

    public void addComponentToPane() {

        frame = new JFrame("Payme");

        p_signUp = new JPanel();
        p_logIn = new JPanel();

        l_logIn = new JLabel("Existing User");
        l_userId = new JLabel("User ID :        ");
        l_password = new JLabel("Password :        ");
        l_signUp = new JLabel("New User");
        l_userName = new JLabel("Name :        ");
        l_DOB = new JLabel("Date of Birth :        ");
        l_email = new JLabel("Email :        ");

        b_logIn = new JButton("Sign In");
        b_signUp = new JButton("Sign Up");

        checkbox = new JCheckBox("Show Password      ", false);
        checkbox.setFont(new Font(LABEL_FONT1, FONT2, 16));
        picker = new JXDatePicker();

        p_container = new JPanel(new GridLayout(1, 1));
        p_container.setBorder(BorderFactory.createEmptyBorder(70, 50, 250, 50));

        l_gridLayout1 = new GridLayout(1, 2);
        l_gridLayout1.setHgap(2);
        p_panel = new JPanel(l_gridLayout1);
        p_panel.setBackground(Color.BLACK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = 3;
        gbc.gridx = 0;
        gbc.gridy = 0;

        l_logIn.setFont(new Font(LABEL_FONT1, FONT2, L_FONT_SIZE));
        p_logIn.add(l_logIn, gbc);

        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 3;
        gbc.gridy++;

        p_logIn.add(new JLabel(), gbc);
        gbc.gridy++;
        p_logIn.add(new JLabel(), gbc);
        gbc.gridy++;
        gbc.gridwidth = 1;

        l_userId.setFont(new Font(LABEL_FONT1, FONT2, FONT_SIZE));
        p_logIn.add(l_userId, gbc);

        gbc.gridx++;
        gbc.gridwidth = 2;
        t_userId = new JTextField("");
        t_userId.setFont(new Font(LABEL_FONT1, FONT1, FONT_SIZE));

        p_logIn.add(t_userId, gbc);

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
        b_logIn.setFont(new Font(LABEL_FONT1, Font.PLAIN, FONT_SIZE));

        p_temp = new JPanel();
        p_temp.add(b_logIn);

        p_logIn.add(p_temp, gbc);

        p_signUp.setLayout(new GridBagLayout());

        GridBagConstraints gbc2 = new GridBagConstraints();

        gbc2.insets = new Insets(10, 10, 10, 10);
        gbc2.gridwidth = 3;
        gbc2.gridx = 0;
        gbc2.gridy = 0;

        l_signUp.setFont(new Font(LABEL_FONT1, FONT2, L_FONT_SIZE));
        p_signUp.add(l_signUp, gbc2);

        gbc2.anchor = GridBagConstraints.CENTER;
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.gridwidth = 3;
        gbc2.gridy++;

        p_signUp.add(new JLabel(), gbc2);
        gbc2.gridy++;
        p_signUp.add(new JLabel(), gbc2);
        gbc2.gridy++;
        gbc2.gridwidth = 1;

        l_userName.setFont(new Font(LABEL_FONT1, FONT2, FONT_SIZE));
        p_signUp.add(l_userName, gbc2);

        gbc2.gridx++;
        gbc2.gridwidth = 2;
        t_userName = new JTextField("");
        t_userName.setFont(new Font(LABEL_FONT1, FONT1, FONT_SIZE));
        p_signUp.add(t_userName, gbc2);

        gbc2.gridx = 0;
        gbc2.gridy++;
        gbc2.gridwidth = 1;

        picker.setDate(new Date());
        picker.setFont(new Font(LABEL_FONT1, FONT1, FONT_SIZE));
        picker.setFormats(new SimpleDateFormat("dd/MM/yyyy"));

        l_DOB.setFont(new Font(LABEL_FONT1, FONT2, FONT_SIZE));
        p_signUp.add(l_DOB, gbc2);
        gbc2.gridx++;
        gbc2.gridwidth = 2;
        p_signUp.add(picker, gbc2);

        gbc2.gridx = 0;
        gbc2.gridy++;
        gbc2.gridwidth = 1;

        l_email.setFont(new Font(LABEL_FONT1, FONT2, FONT_SIZE));
        p_signUp.add(l_email, gbc2);
        gbc2.gridx++;
        gbc2.gridwidth = 2;

        t_email = new JTextField("");
        t_email.setFont(new Font(LABEL_FONT1, FONT1, FONT_SIZE));
        p_signUp.add(t_email, gbc2);

        gbc2.gridx = 0;
        gbc2.gridy++;
        p_signUp.add(new JLabel(), gbc2);
        gbc2.gridy++;
        p_signUp.add(new JLabel(), gbc2);

        gbc2.gridx = 0;
        gbc2.gridy++;
        gbc2.gridwidth = 3;

        b_signUp.setBackground(Color.WHITE);
        b_signUp.setForeground(Color.BLACK);
        b_signUp.setFont(new Font(LABEL_FONT1, Font.PLAIN, FONT_SIZE));

        p_temp = new JPanel();
        p_temp.add(b_signUp);
        p_signUp.add(p_temp, gbc2);

        p_panel.add(p_logIn);
        p_panel.add(p_signUp);
        p_container.add(p_panel);

        l_head = new JLabel("  ");
        l_head.setHorizontalAlignment(JLabel.CENTER);
        l_head.setFont(new Font(LABEL_FONT1, Font.BOLD, 50));

        p_temp = new JPanel(new BorderLayout());

        p_temp.add(l_head, BorderLayout.CENTER);
        l_head = new JLabel("WELCOME");
        l_head.setHorizontalAlignment(JLabel.CENTER);
        l_head.setFont(new Font(LABEL_FONT1, Font.BOLD, 50));
        p_temp.add(l_head, BorderLayout.SOUTH);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(p_temp, BorderLayout.NORTH);

        frame.getContentPane().add(p_container, BorderLayout.CENTER);
        frame.pack();
        frame.setSize(1400, 900);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        checkbox.addActionListener(this);
        b_logIn.addActionListener(this);
        b_signUp.addActionListener(this);

    }

    public static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c))
                return false;
        }
        return true;
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                + "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public static String dateFormatter(String inDate) {

        DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
        Date date = null;
        try {
            date = formatter.parse(inDate);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String formatedDate = cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH) + 1) + "/"
                + cal.get(Calendar.YEAR);
        return formatedDate;

    }

    public static byte[] getSHA(String input) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-256");

        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String toHexString(byte[] hash) {

        BigInteger number = new BigInteger(1, hash);

        StringBuilder hexString = new StringBuilder(number.toString(16));

        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    public static boolean isEmptyFields(String username, String password) {

        return (username.equals("") || password.equals(""));

    }

    public static boolean isEmptyFields(String username, String DOB, String email) {

        return (username.equals("") || DOB.equals("") || email.equals(""));

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

            if (ae.getSource() == b_logIn) {

                MysqlConnection connection = new MysqlConnection();

                String s_userId = t_userId.getText();
                String password = String.valueOf(p_passField.getPassword());

                if (isEmptyFields(s_userId, password)) {
                    JOptionPane.showMessageDialog(null, "Please Fill all the details!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else if (!isNumeric(s_userId)) {
                    JOptionPane.showMessageDialog(null, "UserId should be numeric!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    int userId = Integer.parseInt(s_userId);
                    password = toHexString(getSHA(password));

                    String q1 = "SELECT * FROM Users WHERE userID = '" + userId + "'";

                    ResultSet rs_logIn = connection.statement.executeQuery(q1);
                    if (rs_logIn.next()) {
                        String DB_password = rs_logIn.getString("hash");
                        if (password.equals(DB_password)) {
                            // TODO uncomment this line
                            // new Transactions(int userId).setVisible(true);
                            JOptionPane.showMessageDialog(null, "GO Ahead!");
                            frame.setVisible(false);
                        } else {
                            JOptionPane.showMessageDialog(null, "Incorrect Password!", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect UserID!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
            if (ae.getSource() == b_signUp) {

                String user_name = t_userName.getText();
                String DOB = "";
                try {
                    DOB = picker.getDate().toString();
                } catch (Exception e) {

                    JOptionPane.showMessageDialog(null, "Invalid Date of birth!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                String email = t_email.getText();
                String formattedDate;

                if (isEmptyFields(user_name, DOB, email)) {

                    JOptionPane.showMessageDialog(null, "Please Fill all the details!", "Error",
                            JOptionPane.ERROR_MESSAGE);

                } else {

                    formattedDate = dateFormatter(DOB);
                    if (picker.getDate().compareTo(new Date()) > 0) {
                        JOptionPane.showMessageDialog(null, "Invalid Date of birth!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    } else if (!isValidDate(formattedDate)) {

                        JOptionPane.showMessageDialog(null, "Invalid Date of Birth!", "Error",
                                JOptionPane.ERROR_MESSAGE);

                    } else if (!isValidEmail(email)) {

                        JOptionPane.showMessageDialog(null, "Invalid Email!", "Error", JOptionPane.ERROR_MESSAGE);

                    } else {

                        new Register(user_name, formattedDate, email).setVisible(true);
                        frame.setVisible(false);

                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) {

        new HomeScreen();
    }

}