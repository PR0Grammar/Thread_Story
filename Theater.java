import java.util.*; 

class Theater{
    Set<Visitor> viewers;
    int totalViewersToday;
    int occupants;
    int sessionCount;
    int capacity;
    boolean inSession;

    public Theater(int capacity){
        this.viewers = new HashSet<>();
        this.sessionCount = 0;
        this.capacity = capacity;
        this.inSession = false;
        this.occupants = 0;
        this.totalViewersToday = 0;
    }
    
    synchronized void startSession(){
        if(this.inSession){
            throw new Error("Must end session before starting the next");
        }
        this.inSession = true;
        this.sessionCount++;
    }

    synchronized void endSession(){
        this.inSession = false;
    }

    synchronized boolean inSession(){
        return this.inSession;
    }

    synchronized boolean fillSeat(Visitor v){
        if(viewers.size() == this.capacity){
            return false;
        }
        viewers.add(v);
        this.totalViewersToday++;
        return true;
    }

    synchronized void enterTheater(){
        this.occupants++;
    }

    synchronized void leaveTheater(Visitor v){
        this.occupants--;
        this.viewers.remove(v);
    }

    synchronized boolean theaterFilled(){
        return this.viewers.size() == this.capacity;
    }

    synchronized int numOfViewers(){
        return this.viewers.size();
    }

    synchronized int numOfOccupants(){
        return this.occupants;
    }

    synchronized int numOfTotalViewers(){
        return this.totalViewersToday;
    }

    synchronized boolean overcrowded(){
        return this.occupants > this.capacity;
    }
}
