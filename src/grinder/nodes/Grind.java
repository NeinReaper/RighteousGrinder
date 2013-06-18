package grinder.nodes;

import grinder.Vars;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.util.Timer;

public class Grind extends Node {

    @Override
    public boolean activate() {
		return Players.getLocal().isIdle() 
				&& Inventory.contains(Vars.UNCRUSHED);
    }

    @Override
    public void execute() {
		if (!Bank.isOpen()) {
			if (!Widgets.get(1370).validate()) {
				Vars.status = "Clicking Item";
				if (Inventory.getItem(Vars.UNCRUSHED).getWidgetChild().interact(Vars.ACTION)) {
					Timer timer = new Timer(3000);
					while (timer.isRunning() && !Widgets.get(1370).validate()) {
						if (Widgets.get(1370).validate()) timer.reset();
						sleep(100, 300);
					}
				}
			} else if (Widgets.get(1370).validate() && Widgets.get(1370).getChild(40).getChild(0) != null) {
				Vars.status = "Clicking Grind";
				 if(Widgets.get(1370).getChild(40).getChild(0).click(true)) {
					Timer timer = new Timer(3000);
					while (timer.isRunning() && Players.getLocal().isIdle()) {
						if (!Players.getLocal().isIdle()) timer.reset();
						sleep(100, 300);
					}
					Vars.status = "Grinding";
				 }
				Task.sleep(500);
			}
		} else {
			Vars.status = "Closing Bank";
			Bank.close();
		}
    }
}
