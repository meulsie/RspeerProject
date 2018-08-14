package MuesliCows.Impl;

import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;

import java.util.function.Predicate;

import static MuesliCows.MuesliCows.COWS_AREA;

public class Attack extends Task {
    private static final String COW_NAME = "Cow";

    @Override
    public boolean validate(){
        return Players.getLocal().getHealthPercent() > 20 && COWS_AREA.contains(Players.getLocal()) && !playerHasTarget()&& attackingMe() == null;
    }

    @Override
    public int execute(){

        return 300;
    }

    private boolean playerHasTarget() {
        Player local = Players.getLocal();
        return !local.isMoving() && !local.isAnimating() && (local.getTargetIndex() == -1 || local.getTarget().getHealthPercent() == 0);
    }

    private Predicate<Npc> attackingMe() {
        return npc -> npc.getName().equals(COW_NAME) && npc.getTarget() != null && npc.getTarget().equals(Players.getLocal()) && npc.getHealthPercent() > 0; //missing Npcs.getNearest()
    }
}
