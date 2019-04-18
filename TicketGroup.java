import java.util.*;

// TODO:
class TicketGroup{
    int partyTicketSize;
    ArrayList<ArrayList<Visitor>> groups;
    int numOfVisitorsGrouped;

    public TicketGroup(int partyTicketSize){
        this.partyTicketSize = partyTicketSize;
        this.groups = new ArrayList<ArrayList<Visitor>>();
        this.numOfVisitorsGrouped = 0;
    }

    synchronized int groupVisitor(Visitor v){
        if(this.groups.size() < 0){
            this.groups.add(new ArrayList<Visitor>());
        }
        this.numOfVisitorsGrouped++;
    }
}