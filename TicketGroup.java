class TicketGroup{
    private int capacity;
    private Visitor[] groupMembers;
    int numOfMembers;

    public TicketGroup(int capacity){
        this.capacity = capacity;
        this.groupMembers = new Visitor[capacity];
        this.numOfMembers = 0;
    }

    synchronized void addMember(Visitor v){
        this.groupMembers[numOfMembers++] = v; 
    }

    synchronized boolean isFull(){
        return this.numOfMembers == this.capacity;
    }

    synchronized Visitor[] getMembers(){
        return this.groupMembers;
    }

    synchronized Visitor getMember(int i){
        return this.groupMembers[i];
    }
}