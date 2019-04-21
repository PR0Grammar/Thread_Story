
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

        if(!museum.isOpen()){ 
            continue; 
        };

        //Simulate rushing in for seats!
        this.setPriority(Thread.MAX_PRIORITY);

        this.msg("has entered the theater!");
        museum.theater.enterTheater();

        try{
            Thread.sleep(300);
            this.setPriority(Thread.NORM_PRIORITY);
        }catch(InterruptedException e){
            this.msg("has been interrupted!");
        }

        if(museum.theater.fillSeat(this)){
            this.msg("got a seat for the presentation!");

            this.hasSeenPresentation = true;
            //Wait(sleep) till the movie portion of the presentation is over, which the speaker will announce
            try{
                Thread.sleep(Long.MAX_VALUE);
            }catch(InterruptedException e){
                this.msg("has woken up!");
            }

            //Visitors with smaller id leave first
            for(Visitor v: museum.visitors){
                if(v.isAlive() && v.hasSeenPresentation && v.visitorId < this.visitorId){
                    try{
                        v.join();
                    }catch(InterruptedException e){
                        this.msg("has been interrupted!");
                    }
                }
            }
            this.msg("has left the theater after the presentation");            
            museum.theater.leaveTheater(this);
        }
        else{
            //"Pardon" other visitors while leaving
            Thread.yield();
            Thread.yield();
            this.msg("has left the theater to wait for next viewing");
            museum.theater.leaveTheater(this);
        }

      }

      this.msg("has left the museum and is headed home.");
    }

}