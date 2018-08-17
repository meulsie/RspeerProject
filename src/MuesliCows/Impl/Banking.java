package MuesliCows.Impl;

import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

import static MuesliCows.MuesliCows.BANK_AREA;
import static MuesliCows.MuesliCows.FOOD;
import static MuesliCows.MuesliCows.waitDelay;

public class Banking extends Task {

    @Override
    public boolean validate(){
        return BANK_AREA.contains(Players.getLocal()) && !Inventory.contains(FOOD);
    }

    @Override
    public int execute(){
        Log.info("Executing Banking");
        if(Bank.isOpen()){
            Bank.withdrawAll(FOOD);
        } else {
            Bank.open(); //Open bank
            Time.sleep(waitDelay);
        }
        return 300;
    }
}
