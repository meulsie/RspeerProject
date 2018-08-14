package MuesliCows.Impl;

import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;

import static MuesliCows.MuesliCows.BANK_AREA;
import static MuesliCows.MuesliCows.COWS_AREA;
import static MuesliCows.MuesliCows.FOOD;

public class Traverse extends Task {

    @Override
    public boolean validate(){
        return traverseToBank() || traverseToCows();
    }

    @Override
    public int execute(){
        Movement.walkTo(traverseToBank() ? BANK_AREA.getCenter() : COWS_AREA.getCenter());
        return 300;
    }

    private boolean traverseToBank() {
        return Players.getLocal().getHealthPercent() <= 20 && !Inventory.contains(FOOD) && !BANK_AREA.contains(Players.getLocal()); //May need to change the health percent part
    }

    private boolean traverseToCows() {
        return Players.getLocal().getHealthPercent() > 20 && Inventory.contains(FOOD) && !COWS_AREA.contains(Players.getLocal());
    }
}
