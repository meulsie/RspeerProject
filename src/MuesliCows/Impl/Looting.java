package MuesliCows.Impl;

import org.rspeer.runetek.adapter.Positionable;
import org.rspeer.runetek.adapter.scene.Pickable;
import org.rspeer.runetek.api.commons.Identifiable;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Pickables;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.event.listeners.DeathListener;
import org.rspeer.runetek.event.types.DeathEvent;
import org.rspeer.script.task.Task;

import java.util.function.Predicate;

public class Looting extends Task implements DeathListener {

    private boolean targetDied;
    private Position deathPos;
    private static final String RANGE_AMMO = "Iron dart";

    @Override
    public boolean validate(){
        return !Players.getLocal().isMoving() && targetDied && (!Inventory.isFull() || Inventory.contains(RANGE_AMMO));
    }

    @Override
    public int execute(){
        Pickable ammoPickable = Pickables.getNearest(item -> item.getName().toLowerCase().contains(RANGE_AMMO) && item.isPositionInteractable());
        if (ammoPickable != null){
            ammoPickable.interact("Take");
        } else {
            targetDied = false;
        }
        return 300;
    }

    @Override
    public void notify(DeathEvent e){
        targetDied = true;
        deathPos = new Position(Players.getLocal().getTarget().getX(), Players.getLocal().getTarget().getY());
    }
}
