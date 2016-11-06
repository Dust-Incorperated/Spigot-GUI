package net.dusterthefirst.simplespigot.gui;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.MenuSelectionManager;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicCheckBoxMenuItemUI;

import net.dusterthefirst.simplespigot.PluginClass.BIT;
import net.dusterthefirst.simplespigot.util.MasterWindowListener;

@SuppressWarnings("serial")
public class MasterWindow extends JFrame {

	private JPanel contentPane;
	/**
	 * Public Components
	 */
	public JTree commandTree;
	public JList<String> pluginList;
	public JEditorPane pluginYML;
	public JList<String> playerList;
	public JButton btnKick;
	public JCheckBox chckbxBanned;
	public JCheckBox chckbxOp;
	public JTextField txtUuid;
	public JCheckBox chckbxWhitelisted;
	public JEditorPane properties;
	public JCheckBox notificationsEnabled;
	public Choice notifType;
	public JEditorPane spigot;
	public JEditorPane bukkit;
	public JEditorPane help;
	public JList<String> simplePluginsList;
	public JList<String> simpleWorldsList;
	public JList<String> simplePlayersList;
	public JButton btnSaveFiles;
	public JLabel motd;
	public JLabel motd2;
	public JMenuItem mntmBugz;
	public JMenuItem mntmReloadServer;
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int width = (int) screenSize.getWidth();
	int height = (int) screenSize.getHeight();
	

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
	 * Warns The User About Closing The Window
	 * @return If They Close
	 */
	public int warnClose(){
		return JOptionPane.showConfirmDialog(this, "Would You Like To Stop The Server?", "Confirm Stopping Server", JOptionPane.YES_NO_CANCEL_OPTION);
	}
	
	public static void main(String[] args) {
		new MasterWindow().setVisible(true);
	}
	
	/**
	 * Create the frame.
	 */
	public MasterWindow() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.getLookAndFeel().provideErrorFeedback(null);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		//MENU BAR
		JMenuBar menuBar = new JMenuBar();
			JMenu mnServer = new JMenu("Server");
				mntmReloadServer = new JMenuItem("Reload Server");
			JMenu mnHelp = new JMenu("Help");
				mntmBugz = new JMenuItem("Send Bug Report");
				
		menuBar.add(mnServer);
		menuBar.add(mnHelp);
		menuBar.setBorderPainted(false);
			mnServer.add(mntmReloadServer);
			mnHelp.add(mntmBugz);
		
			
		//Content Pane
		contentPane = new JPanel();
			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
				JPanel tabInfo = new JPanel();
					JPanel Infoinfo = new JPanel();
						JPanel motdPane = new JPanel();
							motd = new JLabel();
							motd2 = new JLabel();
						JPanel playersPane = new JPanel();
							JLabel playersTitle = new JLabel("Players");
							simplePlayersList = new JList<String>();
							JScrollPane simplePlayerPane = new JScrollPane(simplePlayersList);
						JPanel worldPane = new JPanel();
							JLabel worldTitle = new JLabel("Worlds");
							simpleWorldsList = new JList<String>();
							JScrollPane simpleWorldPane = new JScrollPane(simpleWorldsList);
						JPanel pluginsPane = new JPanel();
							JLabel pluginsTitle = new JLabel("Plugins");
							simplePluginsList = new JList<String>();
							JScrollPane simplePluginsPane = new JScrollPane(simplePluginsList);
				JPanel tabCmdHeirarchy = new JPanel();
					commandTree = new JTree();
					JScrollPane scrollPane_1 = new JScrollPane(commandTree);
				JPanel tabRngPlugins = new JPanel();
					JSplitPane pluginz = new JSplitPane();
							pluginList = new JList<>();
							JScrollPane pluginPane = new JScrollPane(pluginList);
						JPanel plugin = new JPanel();
							pluginYML = new JEditorPane();
							JScrollPane scrollPane = new JScrollPane(pluginYML);
							JCheckBox chckbxNewCheckBox = new JCheckBox("Plugin Enabled");
				JPanel tabPlayers = new JPanel();
					JSplitPane playerz = new JSplitPane();
						playerList = new JList<String>();
						JScrollPane playersPaneS = new JScrollPane(playerList);
						JPanel playerOptions = new JPanel();
							btnKick = new JButton("Kick");
						JPanel playerOptions1 = new JPanel();
							chckbxBanned = new JCheckBox("Banned");
							chckbxOp = new JCheckBox("Op");
						JPanel playerOptions2 = new JPanel();
							txtUuid = new JTextField();
							chckbxWhitelisted = new JCheckBox("Whitelisted");
				JPanel tabServerFiles = new JPanel();
					JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
					btnSaveFiles = new JButton("Save Files");
						properties = new JEditorPane();
						spigot = new JEditorPane();
						bukkit = new JEditorPane();
						help = new JEditorPane();
						JScrollPane scrollpane_4 = new JScrollPane(spigot);
						JScrollPane scrollpane_5 = new JScrollPane(bukkit);
						JScrollPane scrollpane_6 = new JScrollPane(help);
						JScrollPane scrollpane_3 = new JScrollPane(properties);
				JPanel tabOptions = new JPanel();
					JPanel notif = new JPanel();
						JPanel notifTop = new JPanel();
							notificationsEnabled = new JCheckBox("Notifications");
							notificationsEnabled.setSelected(true);
							notifType = new Choice();
					JPanel footer = new JPanel();
						JLabel footerLbl = new JLabel("Dusterthefirst 2016");
		
		
		
		//Adds Tabs
		tabbedPane.addTab("Info", null, tabInfo, null);
		tabbedPane.addTab("Commands", null, tabCmdHeirarchy, "Command Heirarchy");
		tabbedPane.addTab("Plugins", null, tabRngPlugins, "Running Plugins");
		tabbedPane.addTab("Players", null, tabPlayers, "Online And Offline Players");
		tabbedPane.addTab("Server Files", null, tabServerFiles, "Server Config Files");
		tabbedPane.addTab("Settings", null, tabOptions, "GUI Settings");
		
		//Settings
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(tabbedPane, BorderLayout.CENTER);
				tabInfo.setLayout(new BorderLayout(0, 0));
				tabInfo.add(Infoinfo, BorderLayout.CENTER);
				tabInfo.add(motdPane, BorderLayout.SOUTH);
				motdPane.setLayout(new BorderLayout(0, 0));
				motdPane.add(motd, BorderLayout.CENTER);
				motdPane.add(motd2, BorderLayout.SOUTH);
					motd2.setHorizontalAlignment(SwingConstants.CENTER);
					motd2.setToolTipText("Servers MOTD");
					motd.setHorizontalAlignment(SwingConstants.CENTER);
					motd.setToolTipText("Servers MOTD");
					Infoinfo.setLayout(new BoxLayout(Infoinfo, BoxLayout.X_AXIS));
					Infoinfo.add(playersPane);
					Infoinfo.add(worldPane);
					Infoinfo.add(pluginsPane);
						playersPane.add(simplePlayerPane, BorderLayout.CENTER);
						playersPane.setBorder(new LineBorder(new Color(204, 204, 255), 2));
						playersPane.setLayout(new BorderLayout(0, 0));
						playersPane.add(playersTitle, BorderLayout.NORTH);
						playersPane.add(simplePlayerPane, BorderLayout.CENTER);
							simplePlayerPane.setBorder(null);
							playersTitle.setHorizontalAlignment(SwingConstants.CENTER);
							simplePlayersList.setBackground(SystemColor.menu);
							simplePlayersList.setSelectionModel(new DisabledItemSelectionModel());
						worldPane.setBorder(new LineBorder(new Color(204, 204, 255), 2));
						worldPane.setLayout(new BorderLayout(0, 0));
						worldPane.add(worldTitle, BorderLayout.NORTH);
						worldPane.add(simpleWorldPane, BorderLayout.CENTER);
							simpleWorldPane.setBorder(null);
							worldTitle.setHorizontalAlignment(SwingConstants.CENTER);
							simpleWorldsList.setBackground(SystemColor.menu);
							simpleWorldsList.setSelectionModel(new DisabledItemSelectionModel());
						pluginsPane.setBorder(new LineBorder(new Color(204, 204, 255), 2));
						pluginsPane.setLayout(new BorderLayout(0, 0));
						pluginsPane.add(pluginsTitle, BorderLayout.NORTH);
						pluginsPane.add(simplePluginsPane, BorderLayout.CENTER);
							simplePluginsPane.setBorder(null);
							pluginsTitle.setHorizontalAlignment(SwingConstants.CENTER);
							simplePluginsList.setBackground(SystemColor.menu);
							simplePluginsList.setSelectionModel(new DisabledItemSelectionModel());
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
				pluginz.setLeftComponent(pluginPane);
				pluginz.setRightComponent(plugin);
					plugin.setLayout(new BorderLayout(0, 0));
					chckbxNewCheckBox.setSelected(true);
					plugin.add(chckbxNewCheckBox, BorderLayout.NORTH);
					plugin.add(scrollPane, BorderLayout.CENTER);
					pluginYML.setMinimumSize(new Dimension(1, 1));
					pluginYML.setText("Select A Plugin");
			tabPlayers.setBackground(new Color(255, 255, 255));
			tabPlayers.setToolTipText("Online Players");
			tabPlayers.setLayout(new BorderLayout(0, 0));
			tabPlayers.add(playerz, BorderLayout.CENTER);
				playerz.setLeftComponent(playersPaneS);
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
			tabServerFiles.setBackground(new Color(255, 255, 255));
			tabServerFiles.setLayout(new BorderLayout(0, 0));
				tabServerFiles.add(btnSaveFiles, BorderLayout.NORTH);
				tabServerFiles.add(tabbedPane_1, BorderLayout.CENTER);
					properties.setText("Your Server Doesnt Have One Of These");
					spigot.setText("Your Server Doesnt Have One Of These");
					bukkit.setText("Your Server Doesnt Have One Of These");
					help.setEditable(false);
					help.setText("Your Server Doesnt Have One Of These");
					tabbedPane_1.addTab("server.properties", null, scrollpane_3, null);
					tabbedPane_1.addTab("spigot.yml", null, scrollpane_4, null);
					tabbedPane_1.addTab("bukkit.yml", null, scrollpane_5, null);
					tabbedPane_1.addTab("help.yml", null, scrollpane_6, null);
			tabOptions.setBackground(SystemColor.menu);
			tabOptions.setToolTipText("Server And GUI Options");
			tabOptions.setLayout(new BorderLayout(0, 0));
			tabOptions.add(notif, BorderLayout.NORTH);
			tabOptions.add(footer, BorderLayout.SOUTH);
				footer.setLayout(new BorderLayout(0, 0));
				footer.add(footerLbl, BorderLayout.NORTH);
				notif.setLayout(new BorderLayout(0, 0));
				notif.add(notifTop, BorderLayout.NORTH);
					footerLbl.setHorizontalAlignment(SwingConstants.CENTER);
					notifTop.setLayout(new BorderLayout(0, 0));
					notifTop.add(notificationsEnabled, BorderLayout.NORTH);
					notifTop.add(notifType, BorderLayout.CENTER);
					notifType.setBackground(SystemColor.menu);
					notifType.add("");
					notifType.add("Tray Popup");
					notifType.add("Sound");
					notifType.add("Tray And Sound");
			
			//Frame Dimensions
			int 
			Jwidth = 550, 
			Jheight = 400;
					
			//Windows Settings
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			setTitle("Simple Spigot");
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			setBounds((width/2) - (Jwidth/2), (height/2) - (Jheight/2), 450, 300);
			setContentPane(contentPane);
			setJMenuBar(menuBar);
			setIconImage(Toolkit.getDefaultToolkit().getImage(MasterWindow.class.getResource("/net/dusterthefirst/res/icon.png")));
			addWindowListener(new MasterWindowListener());
			
			//Anything That Does Math With Screen Size
			playerz.setDividerLocation((int) this.getSize().getWidth() / 3);
			pluginz.setDividerLocation((int) this.getSize().getWidth() / 3);
			simplePluginsPane.setPreferredSize(new Dimension(Jwidth/3, Jheight));
			simpleWorldPane.setPreferredSize(new Dimension(Jwidth/3, Jheight));
			simplePlayerPane.setPreferredSize(new Dimension(Jwidth/3, Jheight));
	}

}

@SuppressWarnings("serial")
class DisabledItemSelectionModel extends DefaultListSelectionModel {

    @Override
    public void setSelectionInterval(int index0, int index1) {super.setSelectionInterval(-1, -1);}
    
}

class StayOpenCheckBoxMenuItemUI extends BasicCheckBoxMenuItemUI {

   @Override
   protected void doClick(MenuSelectionManager msm){menuItem.doClick(0);}
   public static ComponentUI createUI(JComponent c){return new StayOpenCheckBoxMenuItemUI();}

}