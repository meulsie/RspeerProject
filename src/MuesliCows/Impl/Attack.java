package MuesliCows.Impl;

import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import static MuesliCows.MuesliCows.targetDied;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

import static MuesliCows.MuesliCows.COWS_AREA;
import static MuesliCows.MuesliCows.waitDelay;

public class Attack extends Task{
    private static final String COW_NAME = "Cow";

    @Override
    public boolean validate(){
        //Log.info(String.valueOf(playerHasNoTarget()));
        return !targetDied && Players.getLocal().getHealthPercent() > 20 && COWS_AREA.contains(Players.getLocal()) && playerHasNoTarget()&& attackingMe() == null;
    }

    @Override
    public int execute(){
        Log.info("Executing Attack");
        final Npc cow = Npcs.getNearest(npc -> COWS_AREA.contains(npc) && npc.getName().equals(COW_NAME) && npc.getTarget() == null && npc.isPositionInteractable());
        if(cow != null) {
            cow.interact("Attack"); //attack cows
            Time.sleep(waitDelay);
        }
        return 300;
    }

    private boolean playerHasNoTarget() {
        Player local = Players.getLocal();
        return !local.isMoving() && !local.isAnimating() && (local.getTargetIndex() == -1 || local.getTarget().getHealthPercent() == 0);
    }

    private Npc attackingMe() {
        return Npcs.getNearest(npc -> npc.getName().equals(COW_NAME) && npc.getTarget() != null && npc.getTarget().equals(Players.getLocal()) && npc.getHealthPercent() > 0);
    }
}
