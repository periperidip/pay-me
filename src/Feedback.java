import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import javax.swing.plaf.synth.SynthStyle;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

//import org.graalvm.compiler.lir.LIRInstruction.Use;

import java.sql.*;

public class Feedback extends JFrame implements ActionListener{
    
    private static final long serialVersionUID = 1L;
    JTextArea body_text, title_text;
    JButton cancel,submit;
    JLabel l_feedback, l_title, l_body;
    JPanel p_title,p_title_text,p_feedback,p_body,p_body_text,p_buttons;
    String u_ID = "";

    Border blackline = BorderFactory.createLineBorder(Color.black);

    Feedback(){}

    Feedback(String User_ID){
        u_ID = User_ID;

        setTitle("Pay-Me");
        setSize(1400, 900);
        setResizable(false);
		setLocationRelativeTo(null);
        setVisible(true);


        l_feedback = new JLabel("SHARE YOUR FEEDBACK");
        l_feedback.setFont(new Font("System",Font.BOLD,48));

        l_title= new JLabel("Title");
        l_title.setFont(new Font("System",Font.BOLD,25));

        l_body = new JLabel("Body");
        l_body.setFont(new Font("System",Font.BOLD,25));

        title_text = new JTextArea(1,40);
        title_text.setDocument
        (new JTextFieldLimit(64));
        title_text.setFont(new Font("Raleway", Font.PLAIN, 28));
        title_text.setLineWrap(true);
        title_text.setWrapStyleWord(true);
        

        body_text = new JTextArea(2,70);
        body_text.setDocument
        (new JTextFieldLimit(256));
        body_text.setFont(new Font("Raleway", Font.PLAIN, 16));
        body_text.setLineWrap(true);
        body_text.setWrapStyleWord(true);
        // JScrollPane scrolltxt = new JScrollPane();
        // scrolltxt.setViewportView(body_text);
        // scrolltxt.setWheelScrollingEnabled(true);
        
       

        cancel = new JButton("CANCEL");
        cancel.setFont(new Font("System", Font.BOLD, 25));
        cancel.setBackground(Color.white);
        cancel.setForeground(Color.black);

        submit = new JButton("Submit");
        submit.setFont(new Font("System", Font.BOLD, 25));
        submit.setBackground(Color.white);
        submit.setForeground(Color.black);


        p_title = new JPanel();
        p_title_text = new JPanel();
        p_body = new JPanel();
        p_body_text = new JPanel();
        p_feedback = new JPanel();
        p_buttons = new JPanel();
        p_buttons.setBorder(new EmptyBorder(50,100,0,100));

        p_title.setLayout(new BorderLayout());
        p_title_text.setLayout(new BorderLayout());
        p_body.setLayout(new BorderLayout());
        p_body_text.setLayout(new BorderLayout());
        p_feedback.setLayout(new BorderLayout());
        p_buttons.setLayout(new BorderLayout());
        
        GridLayout button_layout = new GridLayout(1, 2);
		button_layout.setHgap(350);
        p_buttons.setLayout(button_layout);

        
        p_title.add(l_title, BorderLayout.WEST);

        p_body.add(l_body, BorderLayout.WEST);

        p_title_text.add(title_text, BorderLayout.WEST);

        p_body_text.add(body_text, BorderLayout.WEST);

        p_feedback.add(l_feedback, BorderLayout.CENTER);
        p_feedback.setBorder(new EmptyBorder(100, 420, 0, 260));

        p_buttons.add(submit);
        p_buttons.add(cancel);
        


        GridLayout global_layout = new GridLayout(6, 1);
		global_layout.setVgap(10);
		JPanel global_panel = new JPanel(global_layout);
        global_panel.setBorder(new EmptyBorder(50, 260, 50, 260));

        global_panel.add(p_feedback);
        global_panel.add(p_title);
        global_panel.add(p_title_text);
        global_panel.add(p_body);
        global_panel.add(p_body_text);
        //global_panel.add(scrolltxt,BorderLayout.EAST);
        global_panel.add(p_buttons);
       // global_panel.add(scrollPane);

        getContentPane().setLayout(new BorderLayout());
		getContentPane().add(p_feedback, BorderLayout.NORTH);
        getContentPane().add(global_panel, BorderLayout.CENTER);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
       
        submit.addActionListener(this);
        cancel.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae){
        try {

            String a = title_text.getText();
            String b = body_text.getText();

            if(ae.getSource()==submit){
                if(a.equals("")){
                    JOptionPane.showMessageDialog(null, "Please enter the title of feedback");
                }
                else if(b.equals("")){
                    JOptionPane.showMessageDialog(null, "Please enter the body of feedback");
                }
                else{
                    int solved = 0;
                    String report_number = getReportNumber();

                    MysqlConnection c1 = new MysqlConnection();
                    
                    String q3 = "insert into report values('"+report_number+"','"+a+"','"+u_ID+"','"+b+"','"+solved+"')";

                    c1.statement.executeUpdate(q3);
                    c1.connection.close();
                    JOptionPane.showMessageDialog(null, "Thanks for your feedback");
                    new Transaction(u_ID).setVisible(true);
                    setVisible(false);
                }
                

            }else if(ae.getSource()==cancel){
                new Transaction(u_ID).setVisible(true);
                setVisible(false);
            }

        } catch (Exception e) {
            System.out.println("error in feedback submission");
            System.out.println("error: "+e);
        }
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

    public static String getReportNumber() throws SQLException {
		MysqlConnection mysqlConnection = new MysqlConnection();

		String check_empty = "SELECT report_number from report";
		ResultSet rs = mysqlConnection.statement.executeQuery(check_empty);

		if (rs.next()) {
			String max_transaction_id_query = "SELECT MAX(report_number) FROM report";
			ResultSet max_transaction_id_number = mysqlConnection.statement.executeQuery(max_transaction_id_query);
			if (max_transaction_id_number.next())
				return increment(max_transaction_id_number.getString(1));
			else
				return "121201";
		} else
			return "121201";
    }
}