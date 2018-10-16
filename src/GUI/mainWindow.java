package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import mainLib.Calculator;
import mainLib.CurrencyList;
import mainLib.DataDownloader;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

public class mainWindow extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainWindow frame = new mainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public mainWindow() {
		setTitle("EarlySignals 1.0");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 606, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(10, 11, 570, 344);
		contentPane.add(textPane);
		
		JLabel lblStatus = new JLabel("Status: Stopped");
		lblStatus.setBounds(129, 366, 156, 14);
		contentPane.add(lblStatus);
		
		JLabel lblIteration = new JLabel("Iteration: 0");
		lblIteration.setBounds(129, 386, 128, 14);
		contentPane.add(lblIteration);
		
		JButton btnNewButton = new JButton("Start");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CurrencyList currencyList = new CurrencyList();
				Calculator calc = new Calculator();
				Thread thread = new Thread(() -> {
					Integer iteration = 0;
					textPane.setText("Welcome in Early Signals!\n" +
							"Version: 1.0\n" + 
							"Market: OKex" +
							"\nThe notifications about pumps will be shown under:");	
					while(true){
						DataDownloader.download(currencyList, textPane);
						calc.searchForPump(currencyList, textPane);
						iteration++;
						lblIteration.setText("Iteration: " + iteration.toString());
					}
				});
				thread.start();	
				
				lblStatus.setText("Status: Working");
				btnNewButton.setEnabled(false);
				btnNewButton.setText("Working");
			}
		});
		btnNewButton.setBounds(10, 366, 109, 34);
		contentPane.add(btnNewButton);
		
		
		
	}
}
