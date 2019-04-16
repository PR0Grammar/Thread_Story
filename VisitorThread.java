
class VisitorThread extends Thread{
    private boolean hasWatchedMovie;
    private int visitorId;
    public static long time = System.currentTimeMillis();


    public VisitorThread(int visitorId){
        this.hasWatchedMovie = false;
        this.visitorId = visitorId;
        this.setName("Visitor-" + visitorId);
    }

    public void run(){
      while(!hasWatchedMovie){
          //While movie in session, busy/wait
          
          //Rush in 
      }

      this.msg("has left the theater and is headed home.");
    }

    public void msg(String m) {
        System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
    }
    
}