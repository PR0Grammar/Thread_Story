
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
        while(museum.theater.theaterInSession() || !museum.theater.isEmpty()){}; //Busy wait

        if(!museum.isOpen()){
            continue;
        }
                
        this.setPriority(Thread.MAX_PRIORITY);
        museum.theater.enterTheater();
        this.msg("has entered the theater");

        try{
            Thread.sleep(12);
        }catch(InterruptedException e){

        }

        this.setPriority(Thread.NORM_PRIORITY);

        if(museum.theater.grabSeat(this)){
            this.msg("got a seat!");
            this.hasSeenPresentation = true;
            try{
                Thread.sleep(Long.MAX_VALUE);
            }catch(InterruptedException e){

            }
            //Group, etc
            //Ticket,etc
            //Busy/wait till session ends
            while(museum.theater.theaterInSession()){};
            //Empty out
            museum.theater.leaveTheater(this);
            this.msg("has left the theater after its viewing");
        }
        else{
            Thread.yield();
            Thread.yield();
            this.msg("didn't get a seat, so they left the theater.");
            museum.theater.leaveTheater(this);
            continue;
        }

      }

      this.msg("has left the museum and is headed home.");
    }


    
}