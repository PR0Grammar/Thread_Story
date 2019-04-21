
class Visitor extends Thread{
    private boolean hasSeenPresentation;
    private int visitorId;
    Museum museum;
    public static long time = System.currentTimeMillis();


    public Visitor(int visitorId, Museum m){
        this.hasSeenPresentation = false;
        this.visitorId = visitorId;
        this.museum = m;

        this.setName("Visitor-" + visitorId);
    }

    public void msg(String m) {
        System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
    }

    public void run(){      
      while(!hasSeenPresentation && museum.isOpen()){
        while(museum.theater.inSession() || museum.theater.theaterFilled()){}; //Busy wait

        if(!museum.isOpen()){ continue; };

        this.setPriority(Thread.MAX_PRIORITY);
        
        this.msg("has entered the theater!");
        museum.theater.enterTheater();

        try{
            Thread.sleep(300);
            this.setPriority(Thread.NORM_PRIORITY);
        }catch(InterruptedException e){
            this.msg("has been interrupted");
        }

        if(museum.theater.fillSeat(this)){
            this.msg("got a seat for the presentation!");

            this.hasSeenPresentation = true;
            try{
                Thread.sleep(Long.MAX_VALUE);
            }catch(InterruptedException e){
                this.msg("has woken up!");
            }

            this.msg("has left the theater after the presentation");            
            museum.theater.leaveTheater(this);
        }
        else{
            Thread.yield();
            Thread.yield();
            this.msg("has left the theater to wait for next viewing");
            museum.theater.leaveTheater(this);
        }

      }

      this.msg("has left the museum and is headed home.");
    }


    
}