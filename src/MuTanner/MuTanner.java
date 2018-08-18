package MuTanner;

import MuTanner.data.Hide;
import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptCategory;
import org.rspeer.script.ScriptMeta;
import org.rspeer.ui.Log;

@ScriptMeta(name = "MuTanner", version = 0.1, desc = "Tans hide in Al Kharid", developer = "Muesli", category = ScriptCategory.CRAFTING)

public class MuTanner extends Script {
    private static final String COINS = "Coins";
    private static final Area BANK_AREA = Area.rectangular(new Position(3269, 3163), new Position(3271, 3170));
    private static final Area TAN_AREA = Area.rectangular(new Position(3270, 3193), new Position(3277, 3189));

    public static Hide hide;

    @Override
    public void onStart(){
        Log.info("***MuTanner starting***");
        MuTannerGUI gui = new MuTannerGUI();
        gui.setVisible(true);
        //Log.info("We'll be tanning: " + hide.getName());
    }

    @Override
    public int loop() {
        Player local = Players.getLocal();
        int runEnergy = Movement.getRunEnergy();
        if (MuTanner.hide != null) {
            if (!Inventory.contains(hide.getName()) || !Inventory.contains(COINS)) {
                if (!Bank.isOpen()) {
                    if (local.distance(BANK_AREA.getCenter()) > Random.nextInt(3,5)) {
                        if (!local.isMoving() && !local.isAnimating() || Movement.getDestinationDistance() < Random.nextInt(2,7)) {
                            Log.info("Walking to bank");
                            Movement.walkToRandomized(BANK_AREA.getCenter());
                            if (!Movement.isRunEnabled() && (runEnergy > Random.nextInt(25, 50))) {
                                Movement.toggleRun(true);
                            }
                        }
                    } else {
                        Log.info("Opening bank");
                        Bank.open();
                    }
                } else {
                    if(!Inventory.isEmpty())
                        Bank.depositAllExcept(COINS);
                    if (!Inventory.contains(COINS)) {
                        if (Bank.contains(COINS)) {
                            Bank.withdrawAll(COINS);
                        } else {
                            return -1;
                        }
                    }
                    if (Bank.contains(hide.getName())) {
                        Bank.withdrawAll(hide.getName());
                    } else {
                        return -1;
                    }
                }
            } else if (!Interfaces.isOpen(324)) {
                if (local.distance(TAN_AREA.getCenter()) > 5) {
                    if (!local.isMoving() && !local.isAnimating() || Movement.getDestinationDistance() < Random.nextInt(2,7)) {
                        Log.info("Walking to tan area");
                        Movement.walkToRandomized(TAN_AREA.getCenter());
                        if (!Movement.isRunEnabled() && (runEnergy > Random.nextInt(25, 50))) {
                            Movement.toggleRun(true);
                        }
                    }
                } else {
                    Log.info("Trading Ellis");
                    Npcs.getNearest("Ellis").interact("Trade");
                }
            } else {
                Log.info("Tanner interface is open, interacting...");
                InterfaceComponent tanIComponent = Interfaces.getComponent(324, hide.getHideComponent());
                if (tanIComponent != null) {
                    Log.info("tan interface component is not null");
                    tanIComponent.interact("Tan All");
                    String[] craftActions = tanIComponent.getActions();
                    for (String s : craftActions) {
                        Log.info(s);
                    }
                }
            }
        }
        return Random.nextInt(500, 1500);
    }

    @Override
    public void onStop(){
        Log.info("MuTanner stopping - RIP");
    }
}
