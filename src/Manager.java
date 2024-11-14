import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Manager {

    private static List<Slot> slots;
    public static void setSlots() {

        Manager.slots = new ArrayList<>();
        for(int i=0;i<8;i++){
            Slot slot = new Slot();
            slot.setFree(true);
            slot.setHour(i+10);
            slot.setName("Free for reservation!");
            slots.add(slot);
        }
    }

    public static List<Slot> getSlots() {
        return slots;
    }
    public boolean checkHour(int h){
        if (h<=17&&h>=10){
            return slots.get(h-10).getFree();
        }
        return false;
    }
    public boolean makeRes(String name,int h){
        if (checkHour(h)){
            slots.get(h-10).setFree(false);
            slots.get(h-10).setName(name);
            return true;
        }
        return false;
    }
    public boolean delRes(String name,int h){
        if (h<=17&&h>=10){
            if(Objects.equals(name, slots.get(h - 10).getName())){
                slots.get(h-10).setFree(true);
                slots.get(h-10).setName("Free for reservation!");
                return true;
            }
            return false;

        }
        return false;
    }

}
