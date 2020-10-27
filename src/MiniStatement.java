import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.UIManager;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Font;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class MiniStatement extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	JLabel l_ministatement;
	JTable t_mini_statement;
	JButton b_back;
	JPanel p_ministatement, p_buttons, p_table;
	String u_ID;

	public MiniStatement() {/* Default Constructor */}

	public MiniStatement(String u_ID) throws SQLException {
		this.u_ID = u_ID;

		/* Banner Configuration */
		this.l_ministatement = new JLabel("Mini Statement");
		this.l_ministatement.setFont(new Font("Arial", Font.BOLD, 50));
		p_ministatement = new JPanel();
		p_ministatement.add(l_ministatement, BorderLayout.CENTER);
		p_ministatement.setBorder(new EmptyBorder(40, 0, 0, 0));

		/* Back Button Configuration */
		this.b_back = new JButton("Back");
		this.b_back.addActionListener(this);
		this.b_back.setFont(new Font("Serif", Font.BOLD, 20));
		this.b_back.setBackground(Color.white);
		this.b_back.setForeground(Color.black);
		p_buttons = new JPanel();
		p_buttons.add(b_back);

		/* Table Configuration */
		this.t_mini_statement = new JTable();
		JScrollPane tableSP = new JScrollPane(this.t_mini_statement);
		t_mini_statement.setModel(fill_table());
		t_mini_statement.setSize(400, 600);
		this.p_table = new JPanel(new GridLayout());
		(this.p_table).add(tableSP);

		/* Window Layout Configuration */
		GridLayout global_layout = new GridLayout();
		global_layout.setVgap(25);
		JPanel global_panel = new JPanel(global_layout);
		global_panel.setBorder(new EmptyBorder(1, 1, 100, 1));
		global_panel.add(p_ministatement);
		global_panel.add(p_table);
		global_panel.add(p_buttons);

		/* Window Properties */
		setTitle("Mini Statement");
		setSize(1400, 900);
		setLocationRelativeTo(null);
		setVisible(true);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(p_ministatement, BorderLayout.NORTH);
		getContentPane().add(p_buttons, BorderLayout.SOUTH);
		getContentPane().add(global_panel, BorderLayout.CENTER);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public DefaultTableModel fill_table() throws SQLException {
		int ms_r = 5;
		DefaultTableModel model = new DefaultTableModel(new String[]
								{"Transaction ID",
								 "CR/DR", "Amount (INR)"}, 0);
		MysqlConnection connection = new MysqlConnection();
		String q_user_accno = "SELECT account_number FROM accounts " +
				      "WHERE userID = '" + u_ID + "'";
		ResultSet rs_acc = connection.statement.executeQuery(q_user_accno);
		String t_acc = "";
		while (rs_acc.next()) {t_acc = rs_acc.getString("account_number");}
		String q_minist = "SELECT * FROM transaction WHERE" +
				  " from_account_number = '" + t_acc + "'" +
				  " OR to_account_number = '" + t_acc + "'" +
				  " LIMIT " + Integer.toString(ms_r);
		ResultSet rs_minist = connection.statement.executeQuery(q_minist);
		while(rs_minist.next()) {
			String t_id = rs_minist.getString("transactionID");
			String t_from = rs_minist.getString("from_account_number");
			String t_am = rs_minist.getString("amount");
			String t_cr_dr = (t_acc.equals(t_from)) ? "DR" : "CR";
			model.addRow(new Object[]{t_id, t_cr_dr, t_am});
		}
		return model;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == b_back) {
			int result;
			UIManager.put("OptionPane.messageFont",
				      new Font("Serif", Font.PLAIN, 20));
			UIManager.put("OptionPane.buttonFont",
				      new Font("Serif", Font.BOLD, 20));
			UIManager.put("Button.background", Color.white);
			UIManager.put("Button.foreground", Color.black);

			result = JOptionPane.showOptionDialog(new JFrame(),
				 "Are you sure want to quit?", "QUIT WINDOW",
				 JOptionPane.YES_NO_OPTION,
				 JOptionPane.QUESTION_MESSAGE,
				 null, new Object[] {"Yes", "No"},
				 JOptionPane.YES_OPTION);

			if (result == JOptionPane.YES_OPTION) {
				new Transaction(u_ID);
				this.setVisible(false);
			}
		}
	}
}
