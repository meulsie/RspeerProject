import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;

@ScriptMeta(developer = "Yasper", desc = "Testing purposes", name = "Test script")
public class TestScript extends Script {
    @Override
    public int loop() {
        return 0;
    }
}