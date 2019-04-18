import java.util.*;

class Museum{
    boolean isOpen;
    ArrayList<Visitor> visitors;
    Theater theater;

    public Museum(int theaterCapacity, long theaterSessionLength){
        this.isOpen = false;
        this.visitors = new ArrayList<>();
        this.theater = new Theater(theaterCapacity, theaterSessionLength);
    }

    synchronized boolean isOpen(){
        return this.isOpen;
    }

    synchronized void closeMuseum(){
        theater.endSession();
        this.isOpen = false;
    }
    
    synchronized void openMuseum(){
        this.isOpen = true;
    }

    synchronized void addVisitor(Visitor v){
        visitors.add(v);
    }

    synchronized int getVisitorCount(){
        return this.visitors.size();
    }
}