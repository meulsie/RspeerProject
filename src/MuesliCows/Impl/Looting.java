package MuesliCows.Impl;

import static MuesliCows.MuesliCows.targetDied;

import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Pickable;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.Pickables;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.event.listeners.DeathListener;
import org.rspeer.runetek.event.types.DeathEvent;
import org.rspeer.runetek.providers.RSPathingEntity;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class Looting extends Task implements DeathListener {

    private static final String RANGE_AMMO = "Steel dart";
    int randomLoot;
    //private static final Predicate<Identifiable> loot = item -> item.getName().toLowerCase().contains(RANGE_AMMO);

    @Override
    public boolean validate(){
        Pickable ammoPickable = Pickables.getNearest(item -> item.getName().contains(RANGE_AMMO) && item.isPositionInteractable());
        if (ammoPickable == null)
            targetDied = false;
        //Log.info("death validate: " + String.valueOf(targetDied));
        return !Players.getLocal().isMoving() && targetDied && (!Inventory.isFull() || Inventory.contains(RANGE_AMMO))&& ammoPickable != null; //if this returns false but deathEvent triggered targetDied will be stuck as true
    }

    @Override
    public int execute(){
        Pickable ammoPickable = Pickables.getNearest(item -> item.getName().contains(RANGE_AMMO) && item.isPositionInteractable());
        Log.info("Executing looting");
        if (ammoPickable != null){
            Log.info("ammo is reachable, looting...");
            ammoPickable.interact("Take");
            Item ammoItem = Inventory.getFirst(RANGE_AMMO);
            if ((ammoItem != null) && (ammoItem.getStackSize() > Random.nextInt(50,150))){
                ammoItem.interact("Wield");
            }
        } else {
            Log.info("Not looting, random value was: " + randomLoot);
            targetDied = false;
        }
        return 300;
    }

    @Override
    public void notify(DeathEvent e){
        RSPathingEntity n = e.getSource();
        Log.info("at DeathEvent");
        if (Players.getLocal().getTarget() != null)
        {
            if(n == Players.getLocal().getTarget()){
                Log.info("our target died, rolling for loot");
                //Position deathPos = new Position(e.getSource().getPosition().getX(),e.getSource().getPosition().getY());
                randomLoot = Random.nextInt(3);
                if (randomLoot == 1) {
                    Log.info("Looting passes, random was: " + randomLoot);
                    targetDied = true;
                } else
                    Log.info("Not looting, random was: " + randomLoot);
            }
        }
    }
}
