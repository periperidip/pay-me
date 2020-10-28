import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import javax.swing.UIManager;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JPasswordField;

public class Transaction extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	JLabel l_transactions;
	JButton b_deposit,b_pin,b_mini,b_detailed,b_balance,b_send,b_back;
	JPanel p1,p2,p3,p4,p5;

	String user_id = "";

	Border blackline = BorderFactory.createLineBorder(Color.black);

	public Transaction()
	{

	}

	public Transaction(String id)       //constructor
	{
		setTitle("Pay-Me");
		setSize(1400, 900);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		user_id  = id;
		l_transactions = new JLabel("Select your transaction");
		l_transactions.setFont(new Font("Ariel",Font.BOLD,50)); 

		b_deposit = new JButton("Deposit");
		b_deposit.setFont(new Font("Serif",Font.BOLD,25));
		b_deposit.setBackground(Color.WHITE);
		b_deposit.setForeground(Color.BLACK);

		b_pin = new JButton("Change PIN");
		b_pin.setFont(new Font("Serif",Font.BOLD,25));
		b_pin.setBackground(Color.WHITE);
		b_pin.setForeground(Color.BLACK);

		b_mini = new JButton("Mini Statement");
		b_mini.setFont(new Font("Serif",Font.BOLD,25));
		b_mini.setBackground(Color.WHITE);
		b_mini.setForeground(Color.BLACK);

		b_detailed = new JButton("Detailed Statement");
		b_detailed.setFont(new Font("Serif",Font.BOLD,25));
		b_detailed.setBackground(Color.WHITE);
		b_detailed.setForeground(Color.BLACK);

		b_balance = new JButton("Balance Enquiry");
		b_balance.setFont(new Font("Serif",Font.BOLD,25));
		b_balance.setBackground(Color.WHITE);
		b_balance.setForeground(Color.BLACK);

		b_send = new JButton("Send Money");
		b_send.setFont(new Font("Serif",Font.BOLD,25));
		b_send.setBackground(Color.WHITE);
		b_send.setForeground(Color.BLACK);

		b_back = new JButton("Logout");
		b_back.setFont(new Font("Serif",Font.BOLD,25));
		b_back.setBackground(Color.WHITE);
		b_back.setForeground(Color.BLACK);
		
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		p4 = new JPanel();
		p5 = new JPanel();

		GridLayout button_layout = new GridLayout(1, 2);
		button_layout.setHgap(100);

		p1.setLayout(new BorderLayout());
		p2.setLayout(button_layout);
		p3.setLayout(button_layout);
		p4.setLayout(button_layout);
		p5.setLayout(new BorderLayout());

		p1.add(l_transactions, BorderLayout.CENTER);
		p1.setBorder(new EmptyBorder(100, 420, 0, 260)); 

		p2.add(b_deposit);
		p2.add(b_pin);

		p3.add(b_balance);
		p3.add(b_send);

		p4.add(b_mini);
		p4.add(b_detailed);

		p5.add(b_back, BorderLayout.LINE_END);

		
		b_deposit.addActionListener(this);
		b_pin.addActionListener(this);
		b_mini.addActionListener(this);
		b_detailed.addActionListener(this);
		b_balance.addActionListener(this);
		b_send.addActionListener(this);
		b_back.addActionListener(this);

		GridLayout global_layout = new GridLayout(6, 1);
		global_layout.setVgap(40);
		JPanel global_panel = new JPanel(global_layout);
		global_panel.setBorder(new EmptyBorder(100, 360, 100, 360));
		
		
		global_panel.add(p1);
		global_panel.add(p2);
		global_panel.add(p3);
		global_panel.add(p4);
		global_panel.add(p5);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(p1, BorderLayout.NORTH);
		getContentPane().add(global_panel, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent ae)
	{
		try
		{
			if(ae.getSource()==b_balance)
			{
				JPanel panel = new JPanel();
				JLabel label = new JLabel("Enter your PIN");
				JPasswordField pass = new JPasswordField(4);

				GridLayout button_layout2 = new GridLayout(1, 2);
				button_layout2.setHgap(30);

				panel.setLayout(button_layout2);

				panel.add(label);
				panel.add(pass);
				String[] options = new String[]{"OK", "Cancel"};
				int option = JOptionPane.showOptionDialog(null, panel, "PIN",
						 JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE,
						 null, options, options[1]);
				char[] password = {};
				if(option == 0) // pressing OK button
				{
					password = pass.getPassword();
					String input_password = new String(password);
				
					MysqlConnection c = new MysqlConnection();
					ResultSet rs = null;
					String q_getPin = "SELECT pin, balance from accounts where userID = '"+user_id+"' ";
					rs =  c.statement.executeQuery(q_getPin);

					String db_pin = "";
					Long db_balance= (long) 0;

					if(rs.next())
					{
						db_pin = (String)rs.getObject(1);
						db_balance = rs.getLong("balance");
					}

					c.connection.close();
				
					if(input_password.equals(db_pin))
					{
						JOptionPane.showMessageDialog(null, "Your account balance is Rs. "+db_balance+" .");
					}
				
					else if(input_password.length()==4)
					{
						JOptionPane.showMessageDialog(null, "The pin must be 4 digits.", "Incorrect!", JOptionPane.ERROR_MESSAGE);
					}

					else
					{
						JOptionPane.showMessageDialog(null, "Your pin is incorrect", "Incorrect!", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			
			else if(ae.getSource()==b_deposit)
			{
				String newDeposit = JOptionPane.showInputDialog(null, "Enter the amount you want to deposit");
				Long amount = Long.parseLong(newDeposit);
				MysqlConnection c = new MysqlConnection();
				ResultSet rs = null;
				String q_balance = "SELECT balance from accounts where userID = '"+user_id+"' ";
				rs = c.statement.executeQuery(q_balance);
				Long prevBalance=0L;
				while(rs.next())
				{
					prevBalance = rs.getLong("balance");
				}
				Long newBalance = prevBalance + amount;
				String u_deposit = "update accounts set balance = '" + newBalance +"'where userID ='"+user_id+"' ";
				c.statement.executeUpdate(u_deposit);
				JOptionPane.showMessageDialog(null, "The amount has been deposited.", "Success!", JOptionPane.INFORMATION_MESSAGE);
				c.connection.close();
			}

			else if(ae.getSource()==b_send)
			{
				new FundTransfer(user_id).setVisible(true);
				setVisible(false);
			}

			else if(ae.getSource()==b_pin)
			{
				new PIN_change(user_id).setVisible(true);
				setVisible(false);
			}

			else if(ae.getSource()==b_mini)
			{
				new MiniStatement(user_id).setVisible(true);
				setVisible(false);
			}

			else if(ae.getSource()==b_detailed)
			{
				new DetailedStatement(user_id).setVisible(true);
				setVisible(false);
			}

			else if(ae.getSource()==b_back)
			{
				UIManager.put("OptionPane.messageFont", new Font("Serif", Font.PLAIN, 20));
				UIManager.put("OptionPane.buttonFont", new Font("Serif", Font.BOLD, 20));
				UIManager.put("Button.background", Color.white);
				UIManager.put("Button.foreground", Color.black);

				int result = JOptionPane.showOptionDialog(new JFrame(), "Are you sure want to Sign out?", "QUIT WINDOW",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] { "Yes", "No" },
					JOptionPane.YES_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					new HomeScreen();
					setVisible(false);
				} else if (result == JOptionPane.NO_OPTION) {
			} else if (result == JOptionPane.CLOSED_OPTION) {
				System.out.println("Window closed without selecting!");
				}
			}

		} 
		catch (Exception e) {
			
			System.out.println("Could not perform the selected transaction.");
			
		}
	}
}


