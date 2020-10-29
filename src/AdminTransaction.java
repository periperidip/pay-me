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

public class AdminTransaction extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    JButton b_detailed, b_deposit, b_userDetailed, b_back;
    JLabel l_heading;
    int accountNumber=0;

    public AdminTransaction(){}
    
    public AdminTransaction(int accno)
    {
        setTitle("Pay-Me");
		setSize(1400, 900);
		setResizable(false);
		setLocationRelativeTo(null);
        setVisible(true);
        
        accountNumber = accno;

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

        

    }


    @Override
    public void actionPerformed(ActionEvent ae)
    {
        try
        {
            if(ae.getSource()==b_deposit)
            {
                String newDeposit = JOptionPane.showInputDialog(null, "Enter the amount you want to deposit");
				Long amount = Long.parseLong(newDeposit);
				MysqlConnection c = new MysqlConnection();
				ResultSet rs = null;
				String q_balance = "SELECT balance from accounts where account_number = '"+accountNumber+"' ";
				rs = c.statement.executeQuery(q_balance);
				Long prevBalance=0L;
				while(rs.next())
				{
					prevBalance = rs.getLong("balance");
				}
				Long newBalance = prevBalance + amount;
				String u_deposit = "update accounts set balance = '" + newBalance +"'where account_number ='"+accountNumber+"' ";
                c.statement.executeUpdate(u_deposit);
                c.statement.close();
				JOptionPane.showMessageDialog(null, "The amount has been deposited.", "Success!", JOptionPane.INFORMATION_MESSAGE);
            }

            else if(ae.getSource()==b_detailed)
            {

            }

            else if(ae.getSource()==b_userDetailed)
            {
                
            }
        } 
        
        catch (Exception e)
        {
            
        }
    }
}
