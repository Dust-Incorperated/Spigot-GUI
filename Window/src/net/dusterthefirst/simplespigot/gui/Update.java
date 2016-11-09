package net.dusterthefirst.simplespigot.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import org.bukkit.ChatColor;

import net.dusterthefirst.simplespigot.PluginClass;

@SuppressWarnings("serial")
public class Update extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	/**
	 * Create the dialog.
	 * @param version Version Of The Update
	 * @param newThings New Items In The Update
	 */
	public Update(String version, List<String> newThings, URI URL) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.getLookAndFeel().provideErrorFeedback(null);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		setTitle(version);
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		try{
			setIconImage(PluginClass.window.getIconImage());
		}catch(Throwable t){}
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
			JLabel lblTitle = new JLabel("New Version " + version);
			lblTitle.setFont(new Font("Times New Roman", Font.PLAIN, 25));
			contentPanel.add(lblTitle, BorderLayout.NORTH);
			JTextPane txtpnContent = new JTextPane();
			txtpnContent.setEditable(false);
			txtpnContent.setFont(new Font("Tahoma", Font.PLAIN, 12));
			txtpnContent.setText(listToBullets(newThings));
			txtpnContent.setBackground(SystemColor.control);
			contentPanel.add(txtpnContent, BorderLayout.CENTER);
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
				JButton btnTakeMeThere = new JButton("Take Me There");
				buttonPane.add(btnTakeMeThere);
				btnTakeMeThere.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							java.awt.Desktop.getDesktop().browse(URL);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				});
				JButton btnRemindMeLater = new JButton("Remind Me Later");
				buttonPane.add(btnRemindMeLater);
				btnRemindMeLater.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
						PluginClass.sendConsoleAMsg(ChatColor.YELLOW + "Ignored Update Dialog");
					}
				});
				setVisible(true);
	}
	
	/**
	 * Convert List To Bulleted String List
	 * @param list List To Be Converted
	 * @return Converted String
	 */
	private String listToBullets(List<String> list) {
		String out = "";
		for(String item: list){
			out+= "\u2022  " + item + "\n";
		}
		return out;
	}

}
