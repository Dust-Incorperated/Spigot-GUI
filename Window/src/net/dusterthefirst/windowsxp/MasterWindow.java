package net.dusterthefirst.windowsxp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.PopupMenu;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.MenuSelectionManager;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicCheckBoxMenuItemUI;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import net.dusterthefirst.simplespigot.Master.BIT;
import net.ftb.util.OSUtils.OS;

@SuppressWarnings("serial")
public class MasterWindow extends JFrame {

	private JPanel contentPane;
	private JTextPane console;
	private JList<?> plugins, players;

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
		setIconImage(Toolkit.getDefaultToolkit().getImage(MasterWindow.class.getResource("/net/dusterthefirst/windowsxp/icon.png")));
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
			tabPlayers.add(playerz);
			
			JList<?> playerList = new JList<Object>();
			playerList.setMinimumSize(new Dimension(1, 1));
			playerList.setMaximumSize(new Dimension(10000, 10000));
			playerz.setLeftComponent(playerList);
			
			JPanel playerOptions = new JPanel();
			playerz.setRightComponent(playerOptions);
			
			JPanel tabStart = new JPanel();
			tabStart.setBackground(new Color(255, 255, 255));
			tabStart.setToolTipText("Server Start Settings");
			tabbedPane.addTab("Quick Start", null, tabStart, null);
			
			JButton start = new JButton("Start");
			start.setPreferredSize(new Dimension(100, 23));
			JButton stop = new JButton("Stop");
			
			tabStart.setLayout(new BorderLayout(0, 0));
			
			JSplitPane splitPane = new JSplitPane();
			splitPane.setLeftComponent(start);
			splitPane.setRightComponent(stop);
			tabStart.add(splitPane, BorderLayout.SOUTH);
			
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

