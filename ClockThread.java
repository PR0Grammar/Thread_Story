
class ClockThread extends Thread{
    private long endOfLastMovie;
    private long startOfNextMovie;
    private int numOfShows;
    private boolean movieInSession;

    public ClockThread(){
        endOfLastMovie = null;
        startOfNextMovie = System.currentTimeMillis();
        numOfShows = 0;
        movieInSession = false;
    }

    public void run(){

        //While not all movies have been show
            //While movieInSession -> go to sleep
            
            //
    }
}