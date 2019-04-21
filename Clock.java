
class Clock extends Thread{
    Museum museum;
    long movieSessionLength;
    static long time = System.currentTimeMillis();

    public Clock(Museum m, long movieSessionLength){
        this.museum = m;
        this.movieSessionLength = movieSessionLength;
        this.setName("Clock");
    }

    public void msg(String m) {
        System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
    }

    public void run(){
        museum.openMuseum();

        while(museum.theater.sessionCount < 4){
            while((!museum.theater.theaterFilled() && 
                  museum.visitorCount() != museum.theater.numOfTotalViewers()) || 
                  museum.theater.overcrowded()
                  ){}
            this.msg("Next theater session started!");
            museum.theater.startSession();

            try{
                Thread.sleep(4000);
            }catch(InterruptedException e){
                this.msg("Wake up visitors!"); //Change to speaker
            }

            for(Visitor v: museum.theater.viewers){
                v.interrupt();
            }
            while(museum.theater.numOfOccupants() > 0){}

            if(museum.theater.sessionCount == 4){
                this.msg("Ending current session and closing museum...");
                museum.closeMuseum();
            }
            else{
                this.msg("Ending current theater session.");
                museum.theater.endSession();
            }        
        }
    }
}