import java.util.*; 

class Theater{
    Set<Visitor> viewers;
    int totalViewersToday;
    int currentOccupantCount;
    int sessionCount;
    Movie currentMovie;
    int capacity;
    long theaterSessionStartTime;
    long sessionLength;
    boolean inSession;

    public Theater(int capacity, long theaterSessionLength){
        this.viewers = new HashSet<>();
        this.sessionCount = 0;
        this.currentMovie = null;
        this.theaterSessionStartTime = -1;
        this.capacity = capacity;
        this.inSession = false;
        this.sessionLength = theaterSessionLength;
        this.currentOccupantCount = 0;
    }
    
    //Starts a new theater session (which includes the movie portion)
    synchronized void startSession(long movieSessionLength){
        if(this.inSession){
            throw new Error("Must end session before starting the next");
        }
        if(movieSessionLength > this.sessionLength){
            throw new Error("Movie cannot be longer than theater session.");
        }

        long startTime = System.currentTimeMillis();
        this.currentMovie = new Movie(startTime, movieSessionLength);
        this.theaterSessionStartTime = startTime;
        this.sessionCount++;
        this.inSession = true;
    }
    
    //End a session, if there is one going on
    synchronized void endSession(){
        if(!this.inSession){
            return;
        }
        this.inSession = false;
    }
    
    //Doesn't check by time, only once the endSession() is called will the current session end,
    //due to delays of people leaving the theater
    synchronized boolean theaterInSession(){
        return this.inSession;
    }

    //Different from theather session. This just returns the part from the movie within the theater session
    synchronized boolean movieInSession(){
        return true;
    }

    //Lets a visitor grab a seat
    synchronized boolean grabSeat(Visitor v){
        if(this.movieInSession() || this.viewers.size() == this.capacity){
            return false;
        }

        viewers.add(v);
        this.totalViewersToday++;
        return true;
    }

    synchronized void enterTheater(){
        this.currentOccupantCount++;
    }

    synchronized void leaveTheater(Visitor v){
        this.currentOccupantCount--;
        this.viewers.remove(v);
    }

    synchronized int getNumOfOccupants(){
        return this.currentOccupantCount;
    }

    synchronized int getNumOfViewers(){
        return this.viewers.size();
    }
    
    synchronized boolean isEmpty(){
        return this.viewers.size() == 0;
    }

    synchronized boolean seatsFilled(){
        return this.viewers.size() == this.capacity;
    }

    synchronized int getTotalViewersToday(){
        return this.totalViewersToday;
    }

    synchronized void endMovie(){
        this.currentMovie.endMovie();
    }
}