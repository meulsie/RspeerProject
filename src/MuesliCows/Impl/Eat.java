package MuesliCows.Impl;

import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;

import static MuesliCows.MuesliCows.FOOD;
import static MuesliCows.MuesliCows.waitDelay;

public class Eat extends Task {

    private static final String EAT_ACTION = "Eat";

    @Override
    public boolean validate(){
        return Players.getLocal().getHealthPercent() <= 20 && Inventory.contains(FOOD);
    }

    @Override
    public int execute(){
        Item foodInv = Inventory.getFirst(FOOD);
        if(foodInv != null) {
            foodInv.interact(EAT_ACTION);
            Time.sleep(waitDelay);
        }
        return 300;
    }
}
