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

public class AdminTransLedger extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	JLabel l_transaction_ledger;
	JTable t_transaction_ledger;
	JButton b_back;
	JPanel p_transaction_ledger, p_buttons, p_table;

	public AdminTransLedger() throws SQLException {

		/* Banner Configuration */
		this.l_transaction_ledger = new JLabel("Transactions Ledger");
		this.l_transaction_ledger.setFont(new Font("Arial", Font.BOLD, 50));
		p_transaction_ledger = new JPanel();
		p_transaction_ledger.add(l_transaction_ledger, BorderLayout.CENTER);
		p_transaction_ledger.setBorder(new EmptyBorder(40, 0, 0, 0));

		/* Back Button Configuration */
		this.b_back = new JButton("Back");
		this.b_back.addActionListener(this);
		this.b_back.setFont(new Font("Serif", Font.BOLD, 20));
		this.b_back.setBackground(Color.white);
		this.b_back.setForeground(Color.black);
		p_buttons = new JPanel();
		p_buttons.add(b_back);

		/* Table Configuration */
		this.t_transaction_ledger = new JTable();
		JScrollPane tableSP = new JScrollPane(this.t_transaction_ledger);
		t_transaction_ledger.setModel(fill_table());
		t_transaction_ledger.setSize(400, 600);
		this.p_table = new JPanel(new GridLayout());
		(this.p_table).add(tableSP);

		/* Window Layout Configuration */
		GridLayout global_layout = new GridLayout();
		global_layout.setVgap(25);
		JPanel global_panel = new JPanel(global_layout);
		global_panel.setBorder(new EmptyBorder(1, 1, 100, 1));
		global_panel.add(p_transaction_ledger);
		global_panel.add(p_table);
		global_panel.add(p_buttons);

		/* Window Properties */
		setTitle("Pay Me!");
		setSize(1400, 900);
		setLocationRelativeTo(null);
		setVisible(true);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(p_transaction_ledger, BorderLayout.NORTH);
		getContentPane().add(p_buttons, BorderLayout.SOUTH);
		getContentPane().add(global_panel, BorderLayout.CENTER);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public DefaultTableModel fill_table() throws SQLException {
		DefaultTableModel model = new DefaultTableModel(new String[]
								{"Transaction ID",
								 "Date",
								 "From Account",
								 "To Account",
								 "Amount (INR)"}, 0);
		MysqlConnection connection = new MysqlConnection();
		String q_detst = "SELECT * FROM transaction" +
				 " ORDER BY timestamp DESC";
		ResultSet rs_detst = connection.statement.executeQuery(q_detst);
		while (rs_detst.next()) {
			String t_id = rs_detst.getString("transactionID");
			String t_date = rs_detst.getString("timestamp");
			String t_from = rs_detst.getString("from_account_number");
			String t_to = rs_detst.getString("to_account_number");
			String t_am = rs_detst.getString("amount");
			model.addRow(new Object[]{t_id, t_date, t_from, t_to, t_am});
		}
		connection.connection.close();
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
				new AdminTransaction();
				this.setVisible(false);
			}
		}
	}
}