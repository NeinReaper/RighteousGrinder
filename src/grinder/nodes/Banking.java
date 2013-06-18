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
		return !Inventory.contains(Vars.unCrushed);
    }

    @Override
    public void execute() {
    	if (Bank.isOpen()) {
    		if(Inventory.contains(Vars.crushed)) {
    			Vars.status = "Despositing Items";
    			invCount = Inventory.getCount(Vars.crushed);
    			if(Bank.depositInventory()) Vars.numCrushed += invCount;
    			Task.sleep(500);
    		} else if (Bank.getItemCount(Vars.unCrushed) > 0) {
    			Vars.status = "Withdrawing Items";
    			Bank.withdraw(Vars.unCrushed, 0);
    		} else {
    			Vars.status = "Logging Out";
    			if (Bank.close()) Game.logout(true);;
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
