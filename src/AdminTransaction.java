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

public class AdminTransaction extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    JButton b_detailed, b_deposit, b_userDetailed, b_reports, b_back;
    JLabel l_heading;
    // String accountNumber="";
    JPanel p1,p2,p3,p4;

    Border blackline = BorderFactory.createLineBorder(Color.black);


    
    public AdminTransaction()
    {
        setTitle("Pay-Me");
		setSize(1400, 900);
		setResizable(false);
		setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // accountNumber = accno;

        b_deposit = new JButton("Deposit");
		b_deposit.setFont(new Font("Serif",Font.BOLD,25));
		b_deposit.setBackground(Color.WHITE);
        b_deposit.setForeground(Color.BLACK);
        
        b_detailed = new JButton("Detailed Statement");
		b_detailed.setFont(new Font("Serif",Font.BOLD,25));
		b_detailed.setBackground(Color.WHITE);
        b_detailed.setForeground(Color.BLACK);
        
        b_userDetailed = new JButton("User Statement");
		b_userDetailed.setFont(new Font("Serif",Font.BOLD,25));
		b_userDetailed.setBackground(Color.WHITE);
		b_userDetailed.setForeground(Color.BLACK);

        b_back = new JButton("Sign Out");
		b_back.setFont(new Font("Serif",Font.BOLD,25));
		b_back.setBackground(Color.WHITE);
        b_back.setForeground(Color.BLACK);

        b_reports = new JButton("Check Customers Reports");
		b_reports.setFont(new Font("Serif",Font.BOLD,25));
		b_reports.setBackground(Color.WHITE);
        b_reports.setForeground(Color.BLACK);
        
        GridLayout button_layout = new GridLayout(1, 2);
		button_layout.setHgap(70);

        p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		p4 = new JPanel();

        p1.setLayout(new BorderLayout());
		p2.setLayout(button_layout);
		p3.setLayout(button_layout);
        p4.setLayout(new BorderLayout());

        l_heading = new JLabel("Select your Service");
        l_heading.setFont(new Font("Ariel",Font.BOLD,50));
        
        p1.add(l_heading, BorderLayout.CENTER);
		p1.setBorder(new EmptyBorder(100, 450, 0, 260)); 

		p2.add(b_deposit);
		p2.add(b_detailed);

		p3.add(b_userDetailed);
		p3.add(b_reports);

		

		p4.add(b_back, BorderLayout.LINE_END);

		
		b_deposit.addActionListener(this);
		b_detailed.addActionListener(this);
		b_userDetailed.addActionListener(this);
		b_reports.addActionListener(this);
		b_back.addActionListener(this);

		GridLayout global_layout = new GridLayout(6, 1);
		global_layout.setVgap(40);
		JPanel global_panel = new JPanel(global_layout);
		global_panel.setBorder(new EmptyBorder(100, 300, 100, 300));
		
		
		// global_panel.add(p1);
		global_panel.add(p2);
		global_panel.add(p3);
		global_panel.add(p4);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(p1, BorderLayout.NORTH);
		getContentPane().add(global_panel, BorderLayout.CENTER);

    }


    @Override
    public void actionPerformed(ActionEvent ae)
    {
        try
        {
            if(ae.getSource()==b_deposit)
            {
                String accountNumber= "-1";
                accountNumber = JOptionPane.showInputDialog(null, "Enter account number.");

                
				MysqlConnection c = new MysqlConnection();
				// ResultSet rs = null;
				String q_balance = "SELECT balance from accounts where account_number = '"+accountNumber+"' ";
				ResultSet rs = c.statement.executeQuery(q_balance);
				Long prevBalance=0L;
				if(rs.next() && !accountNumber.equals("-1"))
				{
                    String newDeposit = JOptionPane.showInputDialog(null, "Enter the amount you want to deposit");
				    Long amount = Long.parseLong(newDeposit);
					prevBalance = rs.getLong("balance");
                    Long newBalance = prevBalance + amount;
                    String u_deposit = "update accounts set balance = '" + newBalance +"'where account_number ='"+accountNumber+"' ";
                    c.statement.executeUpdate(u_deposit);
                    c.statement.close();
                    JOptionPane.showMessageDialog(null, "The amount has been deposited.", "Success!", JOptionPane.INFORMATION_MESSAGE);
                }
                else if(!accountNumber.equals("-1"))
                {
                    JOptionPane.showMessageDialog(null, "The account number does not exist.", "Failed!", JOptionPane.ERROR_MESSAGE);
                }
				
            }

            else if(ae.getSource()==b_detailed)
            {
                new AdminTransLedger().setVisible(true);
                setVisible(false);
            }

            else if(ae.getSource()==b_userDetailed)
            {
                new AdminTransHistory().setVisible(true);
                setVisible(false);
            }

            else if(ae.getSource() == b_reports)
            {
                new AdminReport().setVisible(true);
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
        
        catch (Exception e)
        {
            
        }
    }

    public static void main(String[] args) {
        new AdminTransaction().setVisible(true);
    }
}
