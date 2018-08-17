package MuesliCows;

import MuesliCows.Impl.*;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.ScriptCategory;
import org.rspeer.script.task.Task;
import org.rspeer.script.task.TaskScript;


@ScriptMeta(name = "Muesli Cows", version = 1.1, desc = "Kills cows in Lumbridge", developer = "Muesli", category = ScriptCategory.COMBAT)

public class MuesliCows extends TaskScript {

    private static final Task[] TASKS = { new Attack(), new Banking(), new Eat(), new Looting(), new Traverse() };

    public static final Area COWS_AREA = Area.rectangular(new Position(3247, 3296, 0), new Position(3265, 3255, 0));
    public static final Area BANK_AREA = Area.rectangular(new Position(3208, 3220, 2), new Position(3210, 3218, 2));

    public static final String FOOD = "Tuna";
    public static final int waitDelay = 200;

    @Override
    public void onStart(){
        submit(TASKS);
    }


    public void onStop(){

    }

}
