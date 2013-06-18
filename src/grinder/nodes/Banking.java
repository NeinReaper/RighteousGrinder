package grinder.nodes;

import grinder.Vars;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;


public class Banking extends Node {
	
	private int invCount;

    @Override
    public boolean activate() {
		return !Inventory.contains(Vars.UNCRUSHED);
    }

    @Override
    public void execute() {
    	if (Bank.isOpen()) {
    		if(Inventory.contains(Vars.CRUSHED)) {
    			Vars.status = "Despositing Items";
    			invCount = Inventory.getCount(Vars.CRUSHED);
    			if(Bank.depositInventory()) Vars.numCrushed += invCount;
    			Task.sleep(500);
    		} else if (Bank.getItemCount(Vars.UNCRUSHED) > 0) {
    			Vars.status = "Withdrawing Items";
    			Bank.withdraw(Vars.UNCRUSHED, 0);
    		} else if (Bank.getItemCount(Vars.UNCRUSHED) < 1) {
    			Vars.status = "Logging Out";
    			while (Game.isLoggedIn()) {
    				if (Bank.isOpen()) {
    					Bank.close();
    				} else {
    					Game.logout(true);
    				}
    			}
    			Vars.done = true;
    		}
    	} else {
    		Vars.status = "Opening Bank";
    		if (NPCs.getNearest(Bank.BANK_NPC_IDS) != null && NPCs.getNearest(Bank.BANK_NPC_IDS).isOnScreen()) {
    		Bank.open();
    		} else if (NPCs.getNearest(Bank.BANK_NPC_IDS) != null){
    			Camera.turnTo(NPCs.getNearest(Bank.BANK_NPC_IDS));
    			if (NPCs.getNearest(Bank.BANK_NPC_IDS) != null && !NPCs.getNearest(Bank.BANK_NPC_IDS).isOnScreen()) {
    				Walking.walk(NPCs.getNearest(Bank.BANK_NPC_IDS));
    			}
    		}
    	}
    }
    
}