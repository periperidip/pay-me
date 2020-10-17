import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import javax.swing.border.Border;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.*;

import java.util.Random;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Register extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	String name, date_of_birth, email_address;

	JLabel l_address, l_pin_code, l_phone_number, l_gender, l_aadhar_number, l_pan_number, l_password,
			l_confirm_password;

	JLabel l_registration_form;

	JTextField t_address, t_pin_code, t_phone_number, t_aadhar_number, t_pan_number;

	ButtonGroup gender_selection;

	JRadioButton r_male, r_female;

	JPasswordField pf_password, pf_confirm_password;

	JButton b_register, b_back, b_clear;

	JCheckBox r_show_password;

	JPanel p_address, p_pin_code, p_phone_number, p_gender, p_aadhar_number, p_pan_number, p_password,
			p_confirm_password, p_show_password, p_buttons, p_gender_values, p_registration_form;

	Border blackline = BorderFactory.createLineBorder(Color.black);

	public Register(String full_name, String dob, String email) {

		name = full_name;
		date_of_birth = dob;
		email_address = email;

		setTitle("Pay-Me");
		setSize(1400, 900);
		setLocationRelativeTo(null);
		setResizable(false);

		l_address = new JLabel("Address:");
		l_address.setFont(new Font("Serif", Font.BOLD, 20));

		l_pin_code = new JLabel("Pin Code:");
		l_pin_code.setFont(new Font("Serif", Font.BOLD, 20));

		l_phone_number = new JLabel("Phone Number:");
		l_phone_number.setFont(new Font("Serif", Font.BOLD, 20));

		l_gender = new JLabel("Gender:");
		l_gender.setFont(new Font("Serif", Font.BOLD, 20));

		l_aadhar_number = new JLabel("Aadhar Number:");
		l_aadhar_number.setFont(new Font("Serif", Font.BOLD, 20));

		l_pan_number = new JLabel("PAN Number:");
		l_pan_number.setFont(new Font("Serif", Font.BOLD, 20));

		l_password = new JLabel("Password:");
		l_password.setFont(new Font("Serif", Font.BOLD, 20));

		l_confirm_password = new JLabel("Confirm Password:");
		l_confirm_password.setFont(new Font("Serif", Font.BOLD, 20));

		l_registration_form = new JLabel("Registration Form", SwingConstants.CENTER);
		l_registration_form.setFont(new Font("TimesRoman", Font.BOLD, 50));

		t_address = new JTextField(15);
		t_address.setFont(new Font("Serif", Font.BOLD, 20));

		t_pin_code = new JTextField(15);
		t_pin_code.setFont(new Font("Serif", Font.BOLD, 20));

		t_phone_number = new JTextField(15);
		t_phone_number.setFont(new Font("Serif", Font.BOLD, 20));

		r_male = new JRadioButton("male");
		r_male.setFont(new Font("Serif", Font.PLAIN, 20));
		r_female = new JRadioButton("female");
		r_female.setFont(new Font("Serif", Font.PLAIN, 20));

		gender_selection = new ButtonGroup();
		gender_selection.add(r_male);
		gender_selection.add(r_female);

		t_aadhar_number = new JTextField(15);
		t_aadhar_number.setFont(new Font("Serif", Font.BOLD, 20));

		t_pan_number = new JTextField(15);
		t_pan_number.setFont(new Font("Serif", Font.BOLD, 20));

		pf_password = new JPasswordField(15);
		pf_password.setFont(new Font("Serif", Font.BOLD, 20));

		pf_confirm_password = new JPasswordField(15);
		pf_confirm_password.setFont(new Font("Serif", Font.BOLD, 20));

		b_register = new JButton("Register");
		b_register.setFont(new Font("Serif", Font.BOLD, 20));
		b_register.setBackground(Color.white);
		b_register.setForeground(Color.black);

		b_clear = new JButton("Clear");
		b_clear.setFont(new Font("Serif", Font.BOLD, 20));
		b_clear.setBackground(Color.white);
		b_clear.setForeground(Color.black);

		b_back = new JButton("Back");
		b_back.setFont(new Font("Serif", Font.BOLD, 20));
		b_back.setBackground(Color.white);
		b_back.setForeground(Color.black);

		r_show_password = new JCheckBox("Show Password");
		r_show_password.setFont(new Font("Serif", Font.BOLD, 20));
		pf_password.setEchoChar('*');
		pf_confirm_password.setEchoChar('*');

		r_show_password.addActionListener((ae) -> {
			JCheckBox value = (JCheckBox) ae.getSource();
			pf_password.setEchoChar(value.isSelected() ? (char) 0 : '*');
			pf_confirm_password.setEchoChar(value.isSelected() ? (char) 0 : '*');
		});

		p_address = new JPanel();
		p_pin_code = new JPanel();
		p_phone_number = new JPanel();
		p_gender = new JPanel();
		p_aadhar_number = new JPanel();
		p_pan_number = new JPanel();
		p_password = new JPanel();
		p_confirm_password = new JPanel();
		p_show_password = new JPanel();
		p_buttons = new JPanel();
		p_gender_values = new JPanel();
		p_registration_form = new JPanel();

		p_address.setLayout(new BorderLayout());
		p_pin_code.setLayout(new BorderLayout());
		p_phone_number.setLayout(new BorderLayout());
		p_gender.setLayout(new BorderLayout());
		p_aadhar_number.setLayout(new BorderLayout());
		p_pan_number.setLayout(new BorderLayout());
		p_password.setLayout(new BorderLayout());
		p_confirm_password.setLayout(new BorderLayout());
		p_show_password.setLayout(new BorderLayout());
		p_gender_values.setLayout(new GridLayout(1, 2));
		p_registration_form.setLayout(new BorderLayout());

		GridLayout button_layout = new GridLayout(1, 3);
		button_layout.setHgap(40);
		p_buttons.setLayout(button_layout);

		p_address.add(l_address, BorderLayout.WEST);
		p_address.add(t_address, BorderLayout.EAST);

		p_pin_code.add(l_pin_code, BorderLayout.WEST);
		p_pin_code.add(t_pin_code, BorderLayout.EAST);

		p_phone_number.add(l_phone_number, BorderLayout.WEST);
		p_phone_number.add(t_phone_number, BorderLayout.EAST);

		p_gender_values.add(r_male);
		p_gender_values.add(r_female);

		p_gender.add(l_gender, BorderLayout.WEST);
		p_gender.add(p_gender_values, BorderLayout.EAST);
		p_gender.setBorder(new EmptyBorder(0, 0, 0, 70));

		p_aadhar_number.add(l_aadhar_number, BorderLayout.WEST);
		p_aadhar_number.add(t_aadhar_number, BorderLayout.EAST);

		p_pan_number.add(l_pan_number, BorderLayout.WEST);
		p_pan_number.add(t_pan_number, BorderLayout.EAST);

		p_password.add(l_password, BorderLayout.WEST);
		p_password.add(pf_password, BorderLayout.EAST);

		p_confirm_password.add(l_confirm_password, BorderLayout.WEST);
		p_confirm_password.add(pf_confirm_password, BorderLayout.EAST);

		p_show_password.add(r_show_password, BorderLayout.EAST);

		p_buttons.add(b_register);
		p_buttons.add(b_clear);
		p_buttons.add(b_back);

		p_registration_form.add(l_registration_form, BorderLayout.CENTER);
		p_registration_form.setBorder(new EmptyBorder(40, 0, 0, 0));

		b_register.addActionListener(this);
		b_back.addActionListener(this);
		b_clear.addActionListener(this);

		GridLayout global_layout = new GridLayout(10, 1);
		global_layout.setVgap(25);
		JPanel global_panel = new JPanel(global_layout);
		global_panel.setBorder(new EmptyBorder(60, 350, 100, 350));
		// global_panel.setPreferredSize(new Dimension(200, 200));

		global_panel.add(p_address);
		global_panel.add(p_pin_code);
		global_panel.add(p_phone_number);
		global_panel.add(p_gender);
		global_panel.add(p_aadhar_number);
		global_panel.add(p_pan_number);
		global_panel.add(p_password);
		global_panel.add(p_confirm_password);
		global_panel.add(p_show_password);
		global_panel.add(p_buttons);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(p_registration_form, BorderLayout.NORTH);
		getContentPane().add(global_panel, BorderLayout.CENTER);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == b_clear) {
			t_address.setText("");
			t_pin_code.setText("");
			t_phone_number.setText("");
			t_aadhar_number.setText("");
			t_pan_number.setText("");
			pf_password.setText("");
			pf_confirm_password.setText("");
			gender_selection.clearSelection();
			r_show_password.setSelected(false);
			pf_password.setEchoChar('*');
			pf_confirm_password.setEchoChar('*');
		} else if (ae.getSource() == b_back) {
			UIManager.put("OptionPane.messageFont", new Font("Serif", Font.PLAIN, 20));
			UIManager.put("OptionPane.buttonFont", new Font("Serif", Font.BOLD, 20));
			UIManager.put("Button.background", Color.white);
			UIManager.put("Button.foreground", Color.black);

			int result = JOptionPane.showOptionDialog(new JFrame(), "Are you sure want to quit?", "QUIT WINDOW",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] { "Yes", "No" },
					JOptionPane.YES_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				new HomeScreen();
				setVisible(false);
			} else if (result == JOptionPane.NO_OPTION) {
			} else if (result == JOptionPane.CLOSED_OPTION) {
				System.out.println("Window closed without selecting!");
			}
		} else if (ae.getSource() == b_register) {
			// string (name, date_of_birth, email_address) available globally
			String address = t_address.getText();
			String pin_code = t_pin_code.getText();
			String phone_number = t_phone_number.getText();
			String aadhar_number = t_aadhar_number.getText();
			String pan_number = t_pan_number.getText();
			boolean male = r_male.isSelected();
			boolean female = r_female.isSelected();
			String password = new String(pf_password.getPassword());
			String confirm_password = new String(pf_confirm_password.getPassword());
			String gender = "";
			if (male)
				gender = "male";
			if (female)
				gender = "female";

			if (checkEmptyFields(address, pin_code, phone_number, aadhar_number, pan_number, password, confirm_password,
					male, female))
				JOptionPane.showMessageDialog(null, "Please fill all details");

			else if (!checkPasswordMatch(password, confirm_password))
				JOptionPane.showMessageDialog(null, "Passwords do not match");

			else if (pin_code.length() != 6 || !pin_code.chars().allMatch(Character::isDigit))
				JOptionPane.showMessageDialog(null, "Invalid Pin Code");

			else if (password.length() <= 6)
				JOptionPane.showMessageDialog(null, "Password length must be greater than 6");

			else if (phone_number.length() != 10 || !phone_number.chars().allMatch(Character::isDigit))
				JOptionPane.showMessageDialog(null, "Invalid Phone Number");

			else if (aadhar_number.length() != 12 || !aadhar_number.chars().allMatch(Character::isDigit))
				JOptionPane.showMessageDialog(null, "Invalid Aadhar Number");

			else if (pan_number.length() != 10)
				JOptionPane.showMessageDialog(null, "Invalid Pan Number");

			else if (address.length() > 50)
				JOptionPane.showMessageDialog(null, "Address must be less than 50 characters");
			else { // If everything is correct

				String insert_users_query, insert_account_query;
				int userid_to_assign;
				String account_number_to_assign, password_to_assign, account_pin_to_assign;

				Random random = new Random();

				try {
					MysqlConnection mysqlConnection = new MysqlConnection();

					userid_to_assign = getNewUserId();
					account_number_to_assign = getNewAccountNumber();
					password_to_assign = getHash(password);
					account_pin_to_assign = String.format("%04d", random.nextInt(10000));

					insert_users_query = "INSERT INTO users VALUES('" + userid_to_assign + "', '" + name + "', '" + address + "', '" + phone_number + "', '" + email_address + "', '" + aadhar_number + "', '" + password_to_assign + "', '" + date_of_birth + "'," + pin_code + ", '" + gender + "', '" + pan_number + "')";

					insert_account_query = "INSERT INTO accounts VALUES ('" + userid_to_assign + "', '"
						+ account_number_to_assign + "', '" + 1500 + "', '" + account_pin_to_assign + "')";

					mysqlConnection.statement.executeUpdate(insert_users_query);
					mysqlConnection.statement.executeUpdate(insert_account_query);

					String display_info = "Your UserID is: " + userid_to_assign + "\nYour Account Number is: "
							+ account_number_to_assign + "\nYour Account PIN is: " + account_pin_to_assign;

					JOptionPane.showMessageDialog(null, display_info);

					new HomeScreen();
					setVisible(false);
				} catch (Exception exception) {
					System.out.println(exception);
				}
			}
		}

	}

	public static boolean checkEmptyFields(String address, String pin_code, String phone_number, String aadhar_number, String pan_number, String password, String confirm_password, boolean male, boolean female){
		return address.equals("") || pin_code.equals("") || phone_number.equals("") || aadhar_number.equals("") || pan_number.equals("") || password.equals("") || confirm_password.equals("") || (!male && !female);
	}

	public static boolean checkPasswordMatch(String password, String confirm_password) {
		return password.equals(confirm_password);
	}

	public static String increment(String str) {
		int carry = 1;
		int sum = 0;
		String ret = "";
		for (int i = str.length() - 1; i >= 0; i--) {
			sum = str.charAt(i) - '0' + carry;
			carry = sum / 10;
			sum = sum % 10;
			ret = (char) (sum + '0') + ret;
		}
		if (carry != 0)
			ret = (char) (carry) + ret;
		return ret;
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

	public static String getHash(String input) throws NoSuchAlgorithmException {
		return toHexString(getSHA(input));
	}

	public static int getNewUserId() throws SQLException {
		MysqlConnection mysqlConnection = new MysqlConnection();
		String max_userid_query = "SELECT MAX(userID) FROM users";

		ResultSet max_user_id = mysqlConnection.statement.executeQuery(max_userid_query);
		if (max_user_id.next())
			return max_user_id.getInt(1) + 1;
		else
			return 90001;
	}

	public static String getNewAccountNumber() throws SQLException {
		MysqlConnection mysqlConnection = new MysqlConnection();
		String max_accountnumber_query = "SELECT MAX(account_number) FROM accounts";
		ResultSet max_account_number = mysqlConnection.statement.executeQuery(max_accountnumber_query);
		if (max_account_number.next())
			return increment(max_account_number.getString(1));
		else
			return "28460100990001";
	}
}