
class Clock extends Thread{
    Museum museum;
    long movieSessionLength;
    private static long time = System.currentTimeMillis();

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
            this.msg("Next theater session started and movie has started!");
            museum.theater.startSession();

            //Sleep for the length of the movie, then "wake up" the speaker
            try{
                Thread.sleep(this.movieSessionLength);
            }catch(InterruptedException e){
                this.msg("has been interrupted!");
            }
            
            this.msg("Movie portion of presentation has ended.");
            museum.theater.endMovie();

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
        this.msg("Clock has terminated!");
    }
}