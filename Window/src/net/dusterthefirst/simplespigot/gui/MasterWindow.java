package net.dusterthefirst.simplespigot.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.PopupMenu;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
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
	private JList<?> plugins, players;
	private JTextField txtUuid;
	private JTextField ram;
	private JTextField txtWhitelist;

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
	
	public JList<?> getPlayers() {
		return players;
	}

	public void setPlayers(JList<?> players) {
		this.players = players;
	}

	public JList<?> getPlugins() {
		return plugins;
	}

	public void setPlugins(JList<?> plugins) {
		this.plugins = plugins;
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
		
		//SETTINGS
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		setIconImage(Toolkit.getDefaultToolkit().getImage(MasterWindow.class.getResource("/net/dusterthefirst/res/icon.png")));
		setTitle("Windowz XP");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		//MENU BAR
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.LIGHT_GRAY);
		menuBar.setBorderPainted(false);
		setJMenuBar(menuBar);
		
			//MENU ITEMS
			JMenu mnFile = new JMenu("File");
			menuBar.add(mnFile);
			
			JMenu mnEdit = new JMenu("Edit");
			menuBar.add(mnEdit);
			
			JMenu mnView = new JMenu("View");
			menuBar.add(mnView);
		
				//VEIW Items
				JCheckBoxMenuItem chckbxmntmAdvancedConsole = new JCheckBoxMenuItem("Advanced Console");
				chckbxmntmAdvancedConsole.setUI(new StayOpenCheckBoxMenuItemUI());
				mnView.add(chckbxmntmAdvancedConsole);
				
				JMenu mnMode = new JMenu("Mode");
				mnView.add(mnMode);
		
					//Modes
					JRadioButtonMenuItem rdbtnmntmNight = new JRadioButtonMenuItem("Night");
					rdbtnmntmNight.setUI(new StayOpenCheckBoxMenuItemUI());
					mnMode.add(rdbtnmntmNight);
					
					JRadioButtonMenuItem rdbtnmntmDay = new JRadioButtonMenuItem("Day");
					rdbtnmntmDay.setSelected(true);
					rdbtnmntmDay.setUI(new StayOpenCheckBoxMenuItemUI());
					mnMode.add(rdbtnmntmDay);
		
					ButtonGroup group = new ButtonGroup();
					group.add(rdbtnmntmDay);
					group.add(rdbtnmntmNight);
			
			JMenu mnHelp = new JMenu("Help");
			menuBar.add(mnHelp);
			
		//Pane
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		//TABS
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		//Console Panel
		JPanel tabConsole = new JPanel();
		tabConsole.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("Console", null, tabConsole, "Server Console");
			tabConsole.setLayout(new BorderLayout(0, 0));
		
			//Command Inp
			JComboBox<String> comboCommands = new JComboBox<String>();
			tabConsole.add(comboCommands, BorderLayout.SOUTH);
			comboCommands.setEditable(true);
			comboCommands.addItem("");
			comboCommands.addItem("help");
			
				//Console
				console = new JTextPane();
				console.setForeground(Color.BLACK);
			
				JScrollPane scrollPane = new JScrollPane(console);
				tabConsole.add(scrollPane, BorderLayout.CENTER);
		
		//Commands Panel
		JPanel tabCmdHeirarchy = new JPanel();
		tabCmdHeirarchy.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("Commands", null, tabCmdHeirarchy, "Command Heirarchy");
				tabCmdHeirarchy.setLayout(new BorderLayout(0, 0));
				
				//Command Heirarcy
				JTree commandTree = new JTree();
				commandTree.setBorder(new EmptyBorder(5, 5, 5, 5));
				commandTree.setRootVisible(false);
				commandTree.setToggleClickCount(1);
				commandTree.setBackground(new Color(255, 255, 255));
				JScrollPane scrollPane_1 = new JScrollPane(commandTree);
				tabCmdHeirarchy.add(scrollPane_1, BorderLayout.CENTER);
			
		
		//Running Plugins Panel
		JPanel tabRngPlugins = new JPanel();
		tabRngPlugins.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("Plugins", null, tabRngPlugins, "Running Plugins");
		tabRngPlugins.setLayout(new BorderLayout(0, 0));
			
			//Plugins List
			DefaultListModel<String> listModel = new DefaultListModel<String>();
			JList<String> pluginList = new JList<String>(listModel);
			pluginList.setMaximumSize(new Dimension(10000, 10000));
			pluginList.setMinimumSize(new Dimension(1, 1));
			pluginList.add(new PopupMenu("Hek"));
			pluginList.addMouseListener(new MouseAdapter() {
			    public void mouseClicked(MouseEvent evt) {
			        JList<?> list = (JList<?>)evt.getSource();
			        if (evt.getClickCount() == 2) {
			            // Double-click detected
			            int index = list.locationToIndex(evt.getPoint());
			            System.out.println(index);
			            
			        }
			    }
			});
			
			JEditorPane pluginYML = new JEditorPane();
			pluginYML.setMinimumSize(new Dimension(1, 1));
			pluginYML.setText("Select A Plugin");
			
			JSplitPane pluginz = new JSplitPane();
			pluginz.setLeftComponent(pluginList);
			pluginz.setRightComponent(pluginYML);
			tabRngPlugins.add(pluginz, BorderLayout.CENTER);
			
			JPanel tabPlayers = new JPanel();
			tabPlayers.setBackground(new Color(255, 255, 255));
			tabPlayers.setToolTipText("Online Players");
			tabbedPane.addTab("Players", null, tabPlayers, null);
			tabPlayers.setLayout(new BorderLayout(0, 0));
			
			JSplitPane playerz = new JSplitPane();
			tabPlayers.add(playerz, BorderLayout.CENTER);
			
			JList<?> playerList = new JList<Object>();
			playerList.setMinimumSize(new Dimension(1, 1));
			playerList.setMaximumSize(new Dimension(10000, 10000));
			playerz.setLeftComponent(playerList);
			
			JPanel playerOptions = new JPanel();
			playerz.setRightComponent(playerOptions);
			playerOptions.setLayout(new BorderLayout(0, 0));
			
			JButton btnKick = new JButton("Kick");
			playerOptions.add(btnKick, BorderLayout.SOUTH);
			
			JPanel panel = new JPanel();
			playerOptions.add(panel, BorderLayout.NORTH);
			panel.setLayout(new BorderLayout(0, 0));
			
			JCheckBox chckbxBanned = new JCheckBox("Banned");
			panel.add(chckbxBanned, BorderLayout.NORTH);
			
			JCheckBox chckbxOp = new JCheckBox("Op");
			panel.add(chckbxOp, BorderLayout.CENTER);
			
			JPanel panel_3 = new JPanel();
			panel.add(panel_3, BorderLayout.SOUTH);
			panel_3.setLayout(new BorderLayout(0, 0));
			
			JCheckBox chckbxWhitelisted = new JCheckBox("Whitelisted");
			panel_3.add(chckbxWhitelisted, BorderLayout.CENTER);
			
			txtUuid = new JTextField();
			panel_3.add(txtUuid, BorderLayout.SOUTH);
			txtUuid.setBorder(new EmptyBorder(0, 5, 0, 0));
			txtUuid.setToolTipText("Players UUID");
			txtUuid.setEditable(false);
			txtUuid.setText("UUID:");
			txtUuid.setColumns(10);
			
			JPanel tabStart = new JPanel();
			tabStart.setBackground(new Color(255, 255, 255));
			tabStart.setToolTipText("Server Start Settings");
			tabbedPane.addTab("Quick Start", null, tabStart, null);
			
			tabStart.setLayout(new BorderLayout(0, 0));
			
			JPanel settings = new JPanel();
			tabStart.add(settings, BorderLayout.NORTH);
			settings.setLayout(new BorderLayout(0, 0));
			
			JComboBox<?> comboBox = new JComboBox<Object>();
			comboBox.setEditable(true);
			settings.add(comboBox, BorderLayout.CENTER);
			
			JButton btnBrowse = new JButton("Browse...");
			settings.add(btnBrowse, BorderLayout.EAST);
			
			JPanel settings_2 = new JPanel();
			settings.add(settings_2, BorderLayout.SOUTH);
			settings_2.setLayout(new BorderLayout(0, 0));
			
			ram = new JTextField();
			ram.setBackground(new Color(255, 255, 255));
			ram.setEditable(false);
			ram.setText("0.00G");
			settings_2.add(ram, BorderLayout.EAST);
			ram.setColumns(10);
			
			JSlider slider = new JSlider();
			slider.setBackground(new Color(255, 255, 255));
			slider.setMinorTickSpacing(25);
			slider.setMinimum(100);
			settings_2.add(slider, BorderLayout.CENTER);
			slider.setMaximum(300);
			slider.setMajorTickSpacing(100);
			slider.setPaintTicks(true);
			slider.setSnapToTicks(true);
			slider.setValue(100);
			
			JPanel panel_4 = new JPanel();
			settings_2.add(panel_4, BorderLayout.SOUTH);
			panel_4.setLayout(new BorderLayout(0, 0));
			
			JCheckBox chckbxWhitelist = new JCheckBox("Whitelist Enabled");
			chckbxWhitelist.setBackground(new Color(255, 255, 255));
			panel_4.add(chckbxWhitelist, BorderLayout.NORTH);
			
			JPanel buttons = new JPanel();
			tabStart.add(buttons, BorderLayout.SOUTH);
			buttons.setLayout(new BorderLayout(0, 0));
			
			JButton start = new JButton("Start");
			start.setPreferredSize(new Dimension(100, 23));
			JButton reboot = new JButton("Reboot");
			
			JSplitPane splitPane = new JSplitPane();
			buttons.add(splitPane, BorderLayout.SOUTH);
			splitPane.setLeftComponent(start);
			splitPane.setRightComponent(reboot);
			
			JButton stop = new JButton("Stop");
			buttons.add(stop, BorderLayout.NORTH);
			slider.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					 JSlider source = (JSlider)e.getSource();
					 double value = (double) source.getValue()/100;
					 ram.setText(value + "G");
				}
			});
			
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
			
			JPanel tabServerOptions = new JPanel();
			tabServerOptions.setBackground(new Color(255, 255, 255));
			tabbedPane.addTab("Server Options", null, tabServerOptions, null);
			tabServerOptions.setLayout(new BorderLayout(0, 0));
			
			JEditorPane properties = new JEditorPane();
			tabServerOptions.add(properties, BorderLayout.CENTER);
			
			JPanel lists = new JPanel();
			tabServerOptions.add(lists, BorderLayout.SOUTH);
			lists.setLayout(new BorderLayout(0, 0));
			
			JPanel listsLeft = new JPanel();
			lists.add(listsLeft, BorderLayout.WEST);
			
			JPanel whitelistedPanel = new JPanel();
			listsLeft.add(whitelistedPanel);
			whitelistedPanel.setLayout(new BorderLayout(0, 0));
			
			txtWhitelist = new JTextField();
			txtWhitelist.setText("Whitelist");
			txtWhitelist.setEditable(false);
			whitelistedPanel.add(txtWhitelist, BorderLayout.NORTH);
			
			JPanel opPanel = new JPanel();
			listsLeft.add(opPanel);
			opPanel.setLayout(new BorderLayout(0, 0));
			
			JPanel listsRight = new JPanel();
			lists.add(listsRight, BorderLayout.EAST);
			
			JPanel bannedPanel = new JPanel();
			listsRight.add(bannedPanel);
			bannedPanel.setLayout(new BorderLayout(0, 0));
			
			JPanel bannedIPPanel = new JPanel();
			listsRight.add(bannedIPPanel);
			bannedIPPanel.setLayout(new BorderLayout(0, 0));
			
			JPanel tabOptions = new JPanel();
			tabOptions.setBackground(new Color(255, 255, 255));
			tabOptions.setToolTipText("Server And GUI Options");
			tabbedPane.addTab("Settings", null, tabOptions, null);
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

