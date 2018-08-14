package MuesliCows;

import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.ScriptCategory;
import org.rspeer.script.task.TaskScript;


@ScriptMeta(name = "Muesli Cows",  desc = "Kills cows in Lumbridge", developer = "Muesli", category = ScriptCategory.COMBAT)

public class MuesliCows extends TaskScript {

    public static final Area COWS_AREA = Area.rectangular(new Position(3247, 3296, 0), new Position(3265, 3255, 0));
    public static final Area BANK_AREA = Area.rectangular(new Position(3208, 3220, 2), new Position(3210, 3218, 2));

    public static final String FOOD = "Tuna";
    public static final int waitDelay = 200;
    private static final String EAT_ACTION = "Eat"; //can be removed
    private static final String COW_NAME = "Cow"; //can be removed

    @Override
    public void onStart(){

    }

    @Override
    public int loop() {
        Player local = Players.getLocal();
        final Npc attackingMe = Npcs.getNearest(npc -> npc.getName().equals(COW_NAME) && npc.getTarget() != null && npc.getTarget().equals(local) && npc.getHealthPercent() > 0); //can be removed
        if (local.getHealthPercent() > 20) {
            if(COWS_AREA.contains(local)) {
                if(!local.isMoving() && !local.isAnimating() && (local.getTargetIndex() == -1 || local.getTarget().getHealthPercent() == 0) && attackingMe == null) {
                    final Npc cow = Npcs.getNearest(npc -> COWS_AREA.contains(npc) && npc.getName().equals(COW_NAME) && npc.getTarget() == null && npc.isPositionInteractable());
                    if(cow != null) {
                        cow.interact("Attack"); //attack cows
                        Time.sleep(waitDelay);
                    }
                }
            } else {
                Movement.walkTo(COWS_AREA.getCenter()); //walk to cows
            }
        } else {
            if(Inventory.contains(FOOD)) {
                Item foodInv = Inventory.getFirst(FOOD);
                if(foodInv != null) {
                    foodInv.interact(EAT_ACTION);
                    Time.sleep(waitDelay);
                }
            } else {
                if(BANK_AREA.contains(local)) {
                    if(Bank.isOpen()){
                        Bank.withdrawAll(FOOD);
                    } else {
                        Bank.open(); //Open bank
                        Time.sleep(waitDelay);
                    }
                } else {
                    Movement.walkTo(BANK_AREA.getCenter());//walk to bank
                }
            }
        }
        //if health is > than 20% {
            //if not interacting or moving {
                //if at cows {
                    //attack cows
                //} else {
                    //walk to cows
                //}
            //}
        //} else {
            //if food > 0 {
                //eat food
            //} else {
                //if I am at the bank {
                    //Banking
                //} else {
                    //walk to bank
                //}
            //}
        return 300;
    }

    public void onStop(){

    }

}
