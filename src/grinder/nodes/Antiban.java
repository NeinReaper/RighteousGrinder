package grinder.nodes;

import grinder.Vars;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;

public class Antiban extends Node {

    @Override
    public boolean activate() {
		return Vars.antibanOn 
			&& !Players.getLocal().isIdle() 
			&& Random.nextInt(0, 500) == 250;
    }

    @Override
    public void execute() {
    	int rand = Random.nextInt(1, 3);
    	Vars.status = "Antiban " + rand;
    	switch (rand) {
    		case 1:
    			Camera.setAngle(Random.nextInt(0, 360));
    			Vars.status = "Grinding";
    			break;
    		case 2:
    	    		Tabs.STATS.open();
    	    		Task.sleep(3000, 5000);
    	    		Vars.status = "Grinding";
    			break;
    		case 3:
    			Mouse.move(Random.nextInt(0, 770), Random.nextInt(0, 559));
    			Vars.status = "Grinding";
    			break;
    	}
    }
    
}
