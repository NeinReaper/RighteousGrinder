package grinder;

import grinder.nodes.Antiban;
import grinder.nodes.Banking;
import grinder.nodes.Grind;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.ItemSelectable;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.core.event.listeners.PaintListener;

@Manifest(authors = {"Right"}, name = "Righteous Grinder", description = "Grinds")
public class RighteousGrinder extends ActiveScript implements PaintListener{
	
	public void onStart() {
		Gui gui = new Gui();
		gui.setVisible(true);
		while (gui.isVisible()) {
			sleep(500);
		}
		startTime = System.currentTimeMillis();
	}
    
    public long startTime;
    
	@Override
    public void onRepaint(Graphics g1) {
		
	  long millis = System.currentTimeMillis() - startTime;
	  long hours = millis / (1000 * 60 * 60);
	  millis -= hours * (1000 * 60 * 60);
	  long minutes = millis / (1000 * 60);
	  millis -= minutes * (1000 * 60);
	  long seconds = millis / 1000;
	      
          int runTime = (int) (System.currentTimeMillis() - startTime);
          int profitPerHour = (int) ((Vars.PROFIT*Vars.numCrushed) * 3600000.0 / runTime);
          int crushedPerHour = (int) (Vars.numCrushed * 3600000.0 / runTime);
          
	  Graphics2D g = (Graphics2D)g1;  
	  g.setColor(Color.BLACK);
	  g.fillRect(362, 53, 155, 46);
  	  g.setColor(Color.WHITE);
	  g.drawRect(362, 53, 155, 46);
          g.drawString("Time Running:  " + hours + ": " + minutes + ": " + seconds, 365, 64);
          g.drawString("Status:  " + Vars.status, 365, 75);
          g.drawString("Profit:  " + Vars.PROFIT*Vars.numCrushed + " (" + profitPerHour + "/ph)", 365, 86);
          g.drawString("# Crushed:  " + Vars.numCrushed + " (" + crushedPerHour + "/ph)", 365, 97);
          g.setColor(Color.RED);
          g.drawLine((Mouse.getLocation().x - 8), (Mouse.getLocation().y), (Mouse.getLocation().x + 8), (Mouse.getLocation().y));
          g.drawLine((Mouse.getLocation().x - 7), (Mouse.getLocation().y + 1), (Mouse.getLocation().x + 7), (Mouse.getLocation().y + 1));
          g.drawLine((Mouse.getLocation().x - 7), (Mouse.getLocation().y - 1), (Mouse.getLocation().x + 7), (Mouse.getLocation().y - 1));
          g.drawLine((Mouse.getLocation().x), (Mouse.getLocation().y - 8), (Mouse.getLocation().x), (Mouse.getLocation().y + 8));
          g.drawLine((Mouse.getLocation().x + 1), (Mouse.getLocation().y - 7), (Mouse.getLocation().x + 1), (Mouse.getLocation().y + 7));
          g.drawLine((Mouse.getLocation().x - 1), (Mouse.getLocation().y - 7), (Mouse.getLocation().x - 1), (Mouse.getLocation().y + 7));
          
  }
    


	private final Node[] jobs = { new Antiban(), new Banking(), new Grind()};
	
	@Override
	public int loop() {
		if (Gui.start) {
			for (Node job : jobs) {
				if (job.activate()) {
					job.execute();
					return 300;
				}
			}
		}
		if (Vars.done) stop();
		return 300;
}
}
