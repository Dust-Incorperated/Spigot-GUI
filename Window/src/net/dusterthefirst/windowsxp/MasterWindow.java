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
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.MenuSelectionManager;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicCheckBoxMenuItemUI;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;

@SuppressWarnings("serial")
public class MasterWindow extends JFrame {

	private JPanel contentPane;
	private JTextPane console;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MasterWindow frame = new MasterWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MasterWindow() {
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
			JComboBox comboCommands = new JComboBox();
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
		tabCmdHeirarchy.setBorder(new EmptyBorder(5, 5, 5, 5));
		tabbedPane.addTab("Commands", null, tabCmdHeirarchy, "Command Heirarchy");
		tabCmdHeirarchy.setLayout(new BorderLayout(0, 0));
		
			//Command Heirarcy
			JTree commandTree = new JTree();
			commandTree.setRootVisible(false);
			commandTree.setModel(new DefaultTreeModel(
				new DefaultMutableTreeNode("Commands") {
					{
						DefaultMutableTreeNode node_1;
						node_1 = new DefaultMutableTreeNode("Minecraft");
							node_1.add(new DefaultMutableTreeNode("/help"));
						add(node_1);
						node_1 = new DefaultMutableTreeNode("Spigot");
							node_1.add(new DefaultMutableTreeNode("/reload"));
						add(node_1);
					}
				}
			));
			commandTree.setToggleClickCount(1);
			commandTree.setDragEnabled(true);
			commandTree.setBackground(new Color(255, 255, 255));
			tabCmdHeirarchy.add(commandTree);
		
		//Running Plugins Panel
		JPanel tabRngPlugins = new JPanel();
		tabRngPlugins.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("Plugins", null, tabRngPlugins, "Running Plugins");
		tabRngPlugins.setLayout(new BorderLayout(0, 0));
			
			//Plugins List
			DefaultListModel listModel = new DefaultListModel();
			listModel.addElement("Hello");
			listModel.addElement("Hello");
			listModel.addElement("Hello");
			listModel.addElement("Hello");
			listModel.addElement("Hello");
			listModel.addElement("Hello");
			listModel.addElement("Hello");
			listModel.addElement("Hello");
			JList pluginList = new JList(listModel);
			pluginList.setMaximumSize(new Dimension(10000, 10000));
			pluginList.setMinimumSize(new Dimension(1, 1));
			pluginList.add(new PopupMenu("Hek"));
			pluginList.addMouseListener(new MouseAdapter() {
			    public void mouseClicked(MouseEvent evt) {
			        JList list = (JList)evt.getSource();
			        if (evt.getClickCount() == 2) {
			            // Double-click detected
			            int index = list.locationToIndex(evt.getPoint());
			            System.out.println(index);
			            
			        }
			    }
			});
			
			JEditorPane dtrpnYML = new JEditorPane();
			dtrpnYML.setMinimumSize(new Dimension(1, 1));
			dtrpnYML.setText("Select A Plugin");
			
			JSplitPane splitPane = new JSplitPane();
			splitPane.setLeftComponent(pluginList);
			splitPane.setRightComponent(dtrpnYML);
			tabRngPlugins.add(splitPane, BorderLayout.CENTER);
			
			JPanel tabPlayers = new JPanel();
			tabPlayers.setBackground(new Color(255, 255, 255));
			tabPlayers.setToolTipText("Online Players");
			tabbedPane.addTab("Players", null, tabPlayers, null);
			
			JPanel tabStart = new JPanel();
			tabStart.setBackground(new Color(255, 255, 255));
			tabStart.setToolTipText("Server Start Settings");
			tabbedPane.addTab("Quick Start", null, tabStart, null);
			
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
