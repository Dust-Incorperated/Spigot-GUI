package net.dusterthefirst.simplespigot.gui;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.MenuSelectionManager;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicCheckBoxMenuItemUI;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import net.dusterthefirst.simplespigot.Master.BIT;
import net.dusterthefirst.simplespigot.util.OpenFileFilter;
import net.ftb.util.OSUtils.OS;

@SuppressWarnings("serial")
public class MasterWindow extends JFrame {

	private JPanel contentPane;
	private JTextPane console;
	public JComboBox<String> comboCommands;
	public JTree commandTree;
	public JList<String> pluginList;
	public JEditorPane pluginYML;
	public JList<Object> playerList;
	public JButton btnKick;
	public JCheckBox chckbxBanned;
	public JCheckBox chckbxOp;
	public JTextField txtUuid;
	public JCheckBox chckbxWhitelisted;
	public JSlider ramSlider;
	public JCheckBox chckbxWhitelist;
	public JButton stop;
	public JButton start;
	public JButton reboot;
	public JEditorPane properties;
	public JTextField cpuInfo;
	public JTextField ramInfo;
	public JTextField osInfo;
	public JCheckBox notificationsEnabled;
	public Choice notifType;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MasterWindow frame = new MasterWindow(OS.WINDOWS, 32, 21);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Warns If Wrong Bit Version Of Java On Your Computer
	 * @param bitness
	 */
	public boolean warnBits(BIT bitness) {
		switch (bitness) {
		case a64on64:
			return true;
		case a32on32:
			JOptionPane.showMessageDialog(contentPane,
				    "It Is Not Reccomended To Run A Minecraft Server On A 32bit OS.\n"
				    + "Your Ram Will Be Limited To 1.5G\n"
				    + "Please Think About Using Another Computer",
				    "32 Bit OS Warning",
				    JOptionPane.WARNING_MESSAGE);
			return true;
		case a64on32:
			JOptionPane.showMessageDialog(contentPane,
				    "You Have A 64 Bit Java Version On A 32 Bit OS\n"
				    + "Please Download A 32 Bit Version\n"
				    + "Java Will Not Run",
				    "64 Bit Java On 32 Bit OS",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		case a32on64:
			JOptionPane.showMessageDialog(contentPane,
					"You Have A 32 Bit Java Version On A 64 Bit OS\n"
					+ "Please Download A 64 Bit Version\n"
					+ "You Will Be Limited To 1.5G Of Ram",
				    "32 Bit Java On 64 Bit OS",
				    JOptionPane.WARNING_MESSAGE);
			return true;
		default:
			return true;
		}
	}
	
	/**
	 * Gets Console
	 */
	public JTextPane getConsole(){
		return console;
	}
	
	/**
	 * Writes To The Console
	 * @param msg Message
	 * @param red Color
	 */
	public void writeToConsole(String msg, Color color)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);

        int len = console.getDocument().getLength();
        console.setCaretPosition(len);
        console.setCharacterAttributes(aset, false);
        console.replaceSelection(msg);
    }

	/**
	 * Create the frame.
	 * @param cores 
	 * @param oSmem 
	 * @param os 
	 * @param bitness 
	 */
	public MasterWindow(OS os, long oSmem, int cores) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		//MENU BAR
		JMenuBar menuBar = new JMenuBar();
			JMenu mnEdit = new JMenu("Edit");
				JMenuItem mntmCopyConsole = new JMenuItem("Copy Console");
				JMenuItem mntmSaveConsoleTo = new JMenuItem("Save Console To File");
			JMenu mnView = new JMenu("View");
				JCheckBoxMenuItem chckbxmntmAdvancedConsole = new JCheckBoxMenuItem("Advanced Console");
				JMenu mnMode = new JMenu("Mode");
					JRadioButtonMenuItem rdbtnmntmNight = new JRadioButtonMenuItem("Night");
					JRadioButtonMenuItem rdbtnmntmDay = new JRadioButtonMenuItem("Day");
					ButtonGroup group = new ButtonGroup();
					
		
		//Settings
		chckbxmntmAdvancedConsole.setUI(new StayOpenCheckBoxMenuItemUI());
		rdbtnmntmNight.setUI(new StayOpenCheckBoxMenuItemUI());
		rdbtnmntmDay.setSelected(true);
		rdbtnmntmDay.setUI(new StayOpenCheckBoxMenuItemUI());
		menuBar.setBorderPainted(false);
		
		//Adds All Parts
		menuBar.add(mnEdit);
			mnEdit.add(mntmCopyConsole);
			mnEdit.add(mntmSaveConsoleTo);
		menuBar.add(mnView);
			mnView.add(chckbxmntmAdvancedConsole);
			mnView.add(mnMode);
				mnMode.add(rdbtnmntmNight);
				mnMode.add(rdbtnmntmDay);
					group.add(rdbtnmntmDay);
					group.add(rdbtnmntmNight);
			
		//Content Pane
		contentPane = new JPanel();
			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
				JPanel tabConsole = new JPanel();
					console = new JTextPane();
					JScrollPane scrollPane = new JScrollPane(console);
					comboCommands = new JComboBox<String>();
				JPanel tabCmdHeirarchy = new JPanel();
					commandTree = new JTree();
					JScrollPane scrollPane_1 = new JScrollPane(commandTree);
				JPanel tabRngPlugins = new JPanel();
					JSplitPane pluginz = new JSplitPane();
						DefaultListModel<String> listModel = new DefaultListModel<String>();
						pluginList = new JList<String>(listModel);
						pluginYML = new JEditorPane();
						JScrollPane scrollpane_2 = new JScrollPane(pluginYML);
				JPanel tabPlayers = new JPanel();
					JSplitPane playerz = new JSplitPane();
						playerList = new JList<Object>();
						JPanel playerOptions = new JPanel();
							btnKick = new JButton("Kick");
						JPanel playerOptions1 = new JPanel();
							chckbxBanned = new JCheckBox("Banned");
							chckbxOp = new JCheckBox("Op");
						JPanel playerOptions2 = new JPanel();
							txtUuid = new JTextField();
							chckbxWhitelisted = new JCheckBox("Whitelisted");
				JPanel tabStart = new JPanel();
					JPanel settings = new JPanel();
						JComboBox<?> comboBox = new JComboBox<Object>();
						JButton btnBrowse = new JButton("Browse...");
					JPanel settings_2 = new JPanel();
						JTextField ram = new JTextField();
						ramSlider = new JSlider();
					JPanel settings_3 = new JPanel();
						chckbxWhitelist = new JCheckBox("Whitelist Enabled");
					JPanel buttons = new JPanel();
						stop = new JButton("Stop");
						JSplitPane splitPane = new JSplitPane();
							start = new JButton("Start");
							reboot = new JButton("Reboot");
					JPanel tabServerOptions = new JPanel();
						properties = new JEditorPane();
						JScrollPane scrollpane_3 = new JScrollPane(properties);
					JPanel tabOptions = new JPanel();
						JPanel computerInfo = new JPanel();
							cpuInfo = new JTextField();
							ramInfo = new JTextField();
							osInfo = new JTextField();
						JPanel notif = new JPanel();
							JPanel notifTop = new JPanel();
								notificationsEnabled = new JCheckBox("Notifications");
								notifType = new Choice();
						JPanel footer = new JPanel();
							JLabel footerLbl = new JLabel("Dusterthefirst 2016");
				
		//Adds Tabs
		tabbedPane.addTab("Console", null, tabConsole, "Server Console");
		tabbedPane.addTab("Commands", null, tabCmdHeirarchy, "Command Heirarchy");
		tabbedPane.addTab("Plugins", null, tabRngPlugins, "Running Plugins");
		tabbedPane.addTab("Players", null, tabPlayers, "Online Players");
		tabbedPane.addTab("Quick Start", null, tabStart, "Server Start And Settings");
		tabbedPane.addTab("Server Options", null, tabServerOptions, "Server Properties");
		tabbedPane.addTab("Settings", null, tabOptions, "GUI Settings");
		
		//Settings
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(tabbedPane, BorderLayout.CENTER);
			tabConsole.setBackground(new Color(255, 255, 255));
			tabConsole.setLayout(new BorderLayout(0, 0));
			tabConsole.add(comboCommands, BorderLayout.SOUTH);
			tabConsole.add(scrollPane, BorderLayout.CENTER);
				comboCommands.setEditable(true);
				comboCommands.addItem("");
				console.setForeground(Color.BLACK);
			tabCmdHeirarchy.setBackground(new Color(255, 255, 255));
			tabCmdHeirarchy.setLayout(new BorderLayout(0, 0));
			tabCmdHeirarchy.add(scrollPane_1, BorderLayout.CENTER);
				commandTree.setBorder(new EmptyBorder(5, 5, 5, 5));
				commandTree.setRootVisible(false);
				commandTree.setToggleClickCount(1);
				commandTree.setBackground(new Color(255, 255, 255));
			tabRngPlugins.setBackground(new Color(255, 255, 255));
			tabRngPlugins.setLayout(new BorderLayout(0, 0));
			tabRngPlugins.add(pluginz, BorderLayout.CENTER);
				pluginz.setLeftComponent(pluginList);
				pluginz.setRightComponent(scrollpane_2);
					pluginList.setMinimumSize(new Dimension(1, 1));
					pluginYML.setMinimumSize(new Dimension(1, 1));
					pluginYML.setText("Select A Plugin");
			tabPlayers.setBackground(new Color(255, 255, 255));
			tabPlayers.setToolTipText("Online Players");
			tabPlayers.setLayout(new BorderLayout(0, 0));
			tabPlayers.add(playerz, BorderLayout.CENTER);
				playerz.setLeftComponent(playerList);
				playerz.setRightComponent(playerOptions);
					playerList.setMinimumSize(new Dimension(1, 1));
					playerList.setMaximumSize(new Dimension(10000, 10000));
					playerOptions.setLayout(new BorderLayout(0, 0));
					playerOptions.add(btnKick, BorderLayout.SOUTH);
					playerOptions.add(playerOptions1, BorderLayout.NORTH);
						playerOptions1.setLayout(new BorderLayout(0, 0));
						playerOptions1.add(chckbxBanned, BorderLayout.NORTH);
						playerOptions1.add(chckbxOp, BorderLayout.CENTER);
						playerOptions1.add(playerOptions2, BorderLayout.SOUTH);
							playerOptions2.setLayout(new BorderLayout(0, 0));
							playerOptions2.add(chckbxWhitelisted, BorderLayout.CENTER);
							playerOptions2.add(txtUuid, BorderLayout.SOUTH);
								txtUuid.setBorder(new EmptyBorder(0, 5, 0, 0));
								txtUuid.setToolTipText("Players UUID");
								txtUuid.setEditable(false);
								txtUuid.setText("UUID:");
								txtUuid.setColumns(10);
			tabStart.setBackground(new Color(255, 255, 255));
			tabStart.setToolTipText("Server Start Settings");
			tabStart.setLayout(new BorderLayout(0, 0));
			tabStart.add(settings, BorderLayout.NORTH);
			tabStart.add(buttons, BorderLayout.SOUTH);

				settings.setLayout(new BorderLayout(0, 0));
				settings.add(comboBox, BorderLayout.CENTER);
				settings.add(btnBrowse, BorderLayout.EAST);
				settings.add(settings_2, BorderLayout.SOUTH);
				buttons.add(splitPane, BorderLayout.SOUTH);
				buttons.setLayout(new BorderLayout(0, 0));
				buttons.add(stop, BorderLayout.NORTH);
					btnBrowse.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							JFileChooser fc = new JFileChooser();
							fc.setFileFilter(new OpenFileFilter("jar", "Java Jar File"));
							fc.setAcceptAllFileFilterUsed(false);
							int returnVal = fc.showOpenDialog(MasterWindow.this);
				
					        if (returnVal == JFileChooser.APPROVE_OPTION) {
					            File file = fc.getSelectedFile();
					            comboBox.getEditor().setItem(file.getAbsolutePath());
					        }
						}
					});
					start.setPreferredSize(new Dimension(100, 23));
					splitPane.setLeftComponent(start);
					splitPane.setRightComponent(reboot);
					comboBox.setEditable(true);
					settings_2.setLayout(new BorderLayout(0, 0));
					settings_2.add(ram, BorderLayout.EAST);
					settings_2.add(ramSlider, BorderLayout.CENTER);
					settings_2.add(settings_3, BorderLayout.SOUTH);
						ram.setBackground(new Color(255, 255, 255));
						ram.setEditable(false);
						ram.setText("1.00G");
						ram.setColumns(10);
						ramSlider.setBackground(new Color(255, 255, 255));
						ramSlider.setMinorTickSpacing(25);
						ramSlider.setMinimum(100);
						ramSlider.setMaximum(100);
						ramSlider.setMajorTickSpacing(100);
						ramSlider.setPaintTicks(true);
						ramSlider.setSnapToTicks(true);
						ramSlider.setValue(100);
						ramSlider.addChangeListener(new ChangeListener() {
							@Override
							public void stateChanged(ChangeEvent e) {
								 JSlider source = (JSlider)e.getSource();
								 double value = (double) source.getValue()/100;
								 ram.setText(value + "G");
							}
						});
						settings_3.setLayout(new BorderLayout(0, 0));
						settings_3.add(chckbxWhitelist, BorderLayout.NORTH);
							chckbxWhitelist.setBackground(new Color(255, 255, 255));
			tabServerOptions.setBackground(new Color(255, 255, 255));
			tabServerOptions.setLayout(new BorderLayout(0, 0));
			tabServerOptions.add(scrollpane_3, BorderLayout.CENTER);
			tabOptions.setBackground(new Color(255, 255, 255));
			tabOptions.setToolTipText("Server And GUI Options");
			tabOptions.setLayout(new BorderLayout(0, 0));
			tabOptions.add(computerInfo, BorderLayout.NORTH);
			tabOptions.add(notif, BorderLayout.CENTER);
			tabOptions.add(footer, BorderLayout.SOUTH);
				footer.setLayout(new BorderLayout(0, 0));
				footer.add(footerLbl, BorderLayout.NORTH);
				notif.setLayout(new BorderLayout(0, 0));
				notif.add(notifTop, BorderLayout.NORTH);
				computerInfo.setLayout(new BorderLayout(0, 0));
				computerInfo.add(cpuInfo, BorderLayout.NORTH);
				computerInfo.add(ramInfo, BorderLayout.CENTER);
				computerInfo.add(osInfo, BorderLayout.SOUTH);
					footerLbl.setHorizontalAlignment(SwingConstants.CENTER);
					notifTop.setLayout(new BorderLayout(0, 0));
					notifTop.add(notificationsEnabled, BorderLayout.NORTH);
					notifTop.add(notifType, BorderLayout.CENTER);
					cpuInfo.setEditable(false);
					cpuInfo.setText("CPU: ");
					cpuInfo.setColumns(10);
					ramInfo.setEditable(false);
					ramInfo.setText("RAM: ");
					ramInfo.setColumns(10);
					osInfo.setEditable(false);
					osInfo.setText("OS: ");
					osInfo.setColumns(10);
					notifType.setBackground(SystemColor.menu);
					notifType.add("Tray Popup");
					notifType.add("Sound");
			
			//Windows Settings
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			setIconImage(Toolkit.getDefaultToolkit().getImage(MasterWindow.class.getResource("/net/dusterthefirst/res/icon.png")));
			setTitle("Simple Spigot");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 450, 300);
			setContentPane(contentPane);
			setJMenuBar(menuBar);
			
	}

}

class StayOpenCheckBoxMenuItemUI extends BasicCheckBoxMenuItemUI {

	   @Override
	   protected void doClick(MenuSelectionManager msm) {
	      menuItem.doClick(0);
	   }

	   public static ComponentUI createUI(JComponent c) {
	      return new StayOpenCheckBoxMenuItemUI();
	   }

}