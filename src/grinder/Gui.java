package grinder;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.ItemSelectable;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.util.net.GeItem;

public class Gui extends JFrame {

	private JPanel contentPane;
	public static boolean start = false;
	public static boolean fast = false;
	public static boolean veryFast = false;

	public Gui() {
		setTitle("Righteous Grinder");

		setResizable(false);
		setBounds(100, 100, 464, 103);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		
		JLabel lblNewLabel = new JLabel("Grind This:");
		panel.add(lblNewLabel);
		
		final JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Chocolate Bar", "Blue Dragon Scales", "Unicorn Horn", "Kebbit Teeth", "Desert Goat Horn"}));
		panel.add(comboBox);
		
		JLabel lblMouseSpeed = new JLabel("Mouse Speed:");
		panel.add(lblMouseSpeed);
		
		final JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Normal", "Fast", "Very Fast"}));
		panel.add(comboBox_1);
		
		final JCheckBox chckbxAntiban = new JCheckBox("Antiban");
		panel.add(chckbxAntiban);
		chckbxAntiban.setSelected(true);
		
		JButton btnStart = new JButton("Start");
		panel.add(btnStart);
        btnStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramActionEvent) {
             if (comboBox.getSelectedItem().equals("Chocolate Bar")) {
            	 Vars.UNCRUSHED = Vars.CHOCOLATE_BAR;
            	 Vars.CRUSHED = Vars.CHOCOLATE_DUST;
            	 Vars.ACTION = "Powder";
             }
             if (comboBox.getSelectedItem().equals("Blue Dragon Scales")) {
            	 Vars.UNCRUSHED = Vars.BD_SCALES;
            	 Vars.CRUSHED = Vars.DRAGON_SCALE_DUST;
             }
             if (comboBox.getSelectedItem().equals("Unicorn Horn")) {
            	 Vars.UNCRUSHED = Vars.UNICORN_HORN;
            	 Vars.CRUSHED = Vars.UNICORN_DUST;
             }
             if (comboBox.getSelectedItem().equals("Kebbit Teeth")) {
            	 Vars.UNCRUSHED = Vars.KEBBIT_TEETH;
            	 Vars.CRUSHED = Vars.KEBBIT_TEETH_DUST;
             }
             if (comboBox.getSelectedItem().equals("Desert Goat Horn")) {
            	 Vars.UNCRUSHED = Vars.DESERT_GOAT_HORN;
            	 Vars.CRUSHED = Vars.GOAT_HORN_DUST;
             }
             
             if (comboBox_1.getSelectedItem().toString().matches("Fast")) fast = true;
             if (comboBox_1.getSelectedItem().toString().matches("Very Fast")) veryFast = true;
             
             if (!chckbxAntiban.isSelected()) Vars.antibanOn = false;
             
             Vars.PROFIT = GeItem.lookup(Vars.CRUSHED).getPrice() - GeItem.lookup(Vars.UNCRUSHED).getPrice();
             start = true; 
             Gui.this.setVisible(false);
            }
        });
	}

}
