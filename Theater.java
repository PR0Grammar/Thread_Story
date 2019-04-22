import java.util.*; 

class Theater{
    ArrayList<TicketGroup> ticketGroups;
    Set<Visitor> viewers;
    int totalViewersToday;
    int partyTicketSize;
    int occupants;
    int sessionCount;
    int capacity;
    boolean inSession;
    boolean movieIsPlaying;
    int numOfVisitorsGrouped;

    public Theater(int capacity, int partyTicketSize){
        this.viewers = new HashSet<>();
        this.ticketGroups = new ArrayList<>();
        this.sessionCount = 0;
        this.capacity = capacity;
        this.inSession = false;
        this.occupants = 0;
        this.totalViewersToday = 0;
        this.partyTicketSize = partyTicketSize;
        this.movieIsPlaying = false;
        this.numOfVisitorsGrouped = 0;
    }
    
    synchronized void startSession(){
        if(this.inSession){
            throw new Error("Must end session before starting the next");
        }
        this.inSession = true;
        this.movieIsPlaying = true;
        this.sessionCount++;
    }

    synchronized void endSession(){
        this.inSession = false;
        this.movieIsPlaying = false;
        this.numOfVisitorsGrouped = 0;
        this.ticketGroups = new ArrayList<>();
    }

    synchronized void endMovie(){
        this.movieIsPlaying = false;
    }

    synchronized boolean inSession(){
        return this.inSession;
    }

    synchronized boolean movieIsPlaying(){
        return this.movieIsPlaying;
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
    
    synchronized void groupVisitor(Visitor v){
        int lastGroupIndx = this.ticketGroups.size() - 1;

        if(lastGroupIndx == -1 || this.ticketGroups.get(lastGroupIndx).isFull()){
            this.ticketGroups.add(new TicketGroup(this.partyTicketSize));
            this.ticketGroups.get(lastGroupIndx + 1).addMember(v);
        }
        else{
            this.ticketGroups.get(lastGroupIndx).addMember(v);
        }
        this.numOfVisitorsGrouped++;
    }
    synchronized int numOfGroups(){
        return this.ticketGroups.size();
    }

    synchronized void distributeTickets(int groupNum){
        TicketGroup tg = this.ticketGroups.get(groupNum);

        if(tg == null){
            return;
        }

        for(int i = 0; i < tg.numOfMembers; i++){
            tg.getMember(i).handPartyTicket();
        }
    }
    
    synchronized int getNumOfVisitorsGrouped(){
        return this.numOfVisitorsGrouped;
    }
}
