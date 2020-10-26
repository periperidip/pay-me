import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import javax.swing.plaf.synth.SynthStyle;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.util.*;
import java.sql.*;

public class FundTransfer extends JFrame implements ActionListener{
    JTextField toAccountNo,amount;
    JPasswordField pin;
    JButton cancel,transfer;
    JLabel l_fund_transfer,l_toAccounNo,l_amount,l_pin;
    JCheckBox showpassword;
    String u_ID = "";
    JPanel p_fund_transfer,p_toAccountNo,p_amount,p_pin,p_buttons,p_showpassword;

    Border blackline = BorderFactory.createLineBorder(Color.black);
    FundTransfer(){

    }
    FundTransfer(String User_ID){
        u_ID = User_ID;

        setTitle("Fund Transfer");
		setSize(1200, 900);
		setLocationRelativeTo(null);
        setVisible(true);
        
        l_fund_transfer=new JLabel("TRANSFER MONEY");
        l_fund_transfer.setFont(new Font("System",Font.BOLD,48));

        l_toAccounNo= new JLabel("TO A/C #");
        l_toAccounNo.setFont(new Font("System",Font.BOLD,25));

        l_amount = new JLabel("AMOUNT");
        l_amount.setFont(new Font("System",Font.BOLD,25));

        l_pin = new JLabel("PIN");
        l_pin.setFont( new Font("System",Font.BOLD,25));

        toAccountNo = new JTextField(15);
        toAccountNo.setFont(new Font("Raleway", Font.BOLD, 28));
        
        amount = new JTextField(15);
        amount.setFont(new Font("Raleway", Font.BOLD, 28));
        
        pin = new JPasswordField(15);
        pin.setFont(new Font("Raleway", Font.BOLD, 28));

        transfer = new JButton("TRANSFER");
        transfer.setFont(new Font("System", Font.BOLD, 25));
        transfer.setBackground(Color.white);
        transfer.setForeground(Color.black);
    
        cancel = new JButton("CANCEL");
        cancel.setFont(new Font("System", Font.BOLD, 25));
        cancel.setBackground(Color.white);
        cancel.setForeground(Color.black);
        
        showpassword = new JCheckBox("Show Password");
		showpassword.setFont(new Font("Serif", Font.BOLD, 20));
		pin.setEchoChar('*');
        
        showpassword.addActionListener((ae) -> {
			JCheckBox value = (JCheckBox) ae.getSource();
            pin.setEchoChar(value.isSelected() ? (char) 0 : '*');
        });
        
        p_fund_transfer = new JPanel();
        p_toAccountNo = new JPanel();
        p_amount = new JPanel();
        p_pin = new JPanel();
        p_buttons = new JPanel();
        p_showpassword = new JPanel();

        p_fund_transfer.setLayout(new BorderLayout());
        p_toAccountNo.setLayout(new BorderLayout());
        p_amount.setLayout(new BorderLayout());
        p_pin.setLayout(new BorderLayout());
        p_buttons.setLayout(new BorderLayout());
        p_showpassword.setLayout(new BorderLayout());

        GridLayout button_layout = new GridLayout(1, 2);
		button_layout.setHgap(200);
        p_buttons.setLayout(button_layout);
        
        p_toAccountNo.add(l_toAccounNo, BorderLayout.WEST);
        p_toAccountNo.add(toAccountNo, BorderLayout.EAST);

        p_amount.add(l_amount, BorderLayout.WEST);
        p_amount.add(amount, BorderLayout.EAST);

        p_pin.add(l_pin, BorderLayout.WEST);
        p_pin.add(pin, BorderLayout.EAST);

        p_showpassword.add(showpassword, BorderLayout.EAST);

        p_fund_transfer.add(l_fund_transfer, BorderLayout.CENTER);
        p_fund_transfer.setBorder(new EmptyBorder(100, 360, 0, 260));

        p_buttons.add(transfer);
        p_buttons.add(cancel);

        GridLayout global_layout = new GridLayout(6, 1);
		global_layout.setVgap(50);
		JPanel global_panel = new JPanel(global_layout);
        global_panel.setBorder(new EmptyBorder(150, 260, 100, 260));

        global_panel.add(p_fund_transfer);
        global_panel.add(p_toAccountNo);
        global_panel.add(p_amount);
        global_panel.add(p_pin);
        global_panel.add(p_showpassword);
        global_panel.add(p_buttons);
        
        

        getContentPane().setLayout(new BorderLayout());
		getContentPane().add(p_fund_transfer, BorderLayout.NORTH);
		getContentPane().add(global_panel, BorderLayout.CENTER);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
       
        transfer.addActionListener(this);
        cancel.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent ae){
        try {

            String a = toAccountNo.getText();
            String b = amount.getText();
            String c = new String(pin.getPassword());
            long money = Long.parseLong(b);

            if(ae.getSource()==transfer){
                if(a.equals("")){
                    JOptionPane.showMessageDialog(null, "Please enter the account no. to which you want to transfer money");
                }
                else if(b.equals("")){
                    JOptionPane.showMessageDialog(null, "Please enter the amount you want to tranfer");
                }
                else if(c.equals("")){
                    JOptionPane.showMessageDialog(null, "Please enter your pin number");
                }

                MysqlConnection c1 = new MysqlConnection();
                ResultSet rs =null;
                String getData = "select pin, balance, account_number from accounts where userID = '"+u_ID+"' ";
                rs = c1.statement.executeQuery(getData);

                String correct_pin = "";
                long available_balance = 0;
                String from_account_no = "";
                while (rs.next()) {
                    correct_pin =  (String) rs.getObject(1);
                    available_balance = rs.getLong("balance");
                    from_account_no = rs.getString("account_number");
                }
                //System.out.println(correct_pin + " " + available_balance + " " + a);

                if(c.equals(correct_pin) ){
                    if(available_balance < money){
                        JOptionPane.showMessageDialog(null, "Not enough balance in your account");
                    }
                    else{
                        MysqlConnection c2 = new MysqlConnection();
                        ResultSet rs1 = null;
                        long total_balance = -1; // current account balance to whom we are sending money;
                        String check_user_exists = "select balance from accounts where account_number = '"+a+"' ";
                        rs1 = c2.statement.executeQuery(check_user_exists);
                        while (rs1.next()) {
                            total_balance = rs1.getLong("balance");
                            //System.out.println(total_balance);
                        }
                        
                        if(total_balance >= 0){
                              
                            total_balance = total_balance + money;
                            available_balance = available_balance - money;
                            String q1 = "update accounts set balance = '"+available_balance+"' where userID = '"+u_ID+"' ";
                            String q2 = "update accounts set balance =  '"+total_balance+"' where account_number = '"+a+"' ";
                            c1.statement.executeUpdate(q1);
                            c1.statement.executeUpdate(q2);

                            String transaction_Id = getTransactionID();
                            String to_account_no = a;
                            long amount = money;
                            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                            String q3 = "insert into transaction values('"+transaction_Id+"','"+to_account_no+"','"+from_account_no+"','"+amount+"','"+timestamp+"')";
                                        
                            c1.statement.executeUpdate(q3);
                            setVisible(false);
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "no user exists with given account no.");
                        }
                        
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Enter the correct PIN no.");
                }
            

            }
            else if(ae.getSource()==cancel){
                setVisible(false);
            }
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println("error in amount transfer");
            System.out.println("error: "+e);
        }
    }

    public static void main(String[] args){
        new FundTransfer("90001").setVisible(true);
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
    
    public static String getTransactionID() throws SQLException {
		MysqlConnection mysqlConnection = new MysqlConnection();

		String check_empty = "SELECT transactionID from transaction";
		ResultSet rs = mysqlConnection.statement.executeQuery(check_empty);

		if (rs.next()) {
			String max_transaction_id_query = "SELECT MAX(transactionID) FROM transaction";
			ResultSet max_transaction_id_number = mysqlConnection.statement.executeQuery(max_transaction_id_query);
			if (max_transaction_id_number.next())
				return increment(max_transaction_id_number.getString(1));
			else
				return "15897840001";
		} else
			return "15897840001";
	}
}
