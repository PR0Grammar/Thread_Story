
class Clock extends Thread{
    Museum museum;
    long movieSessionLength;

    public Clock(Museum m, long movieSessionLength){
        this.museum = m;
        this.movieSessionLength = movieSessionLength;
    }

    public void run(){
        museum.openMuseum();

        while(museum.theater.sessionCount < 4){

            while((!museum.theater.seatsFilled() && 
                  !(museum.getVisitorCount() - museum.theater.getTotalViewersToday() == museum.theater.getNumOfViewers())) ||
                    museum.theater.getNumOfOccupants() > museum.theater.getNumOfViewers()                  
                  ){} //Busy wait

            museum.theater.startSession(this.movieSessionLength);
            System.out.println("Started next theater session.");
            try{
                Thread.sleep(this.movieSessionLength);
            }catch(InterruptedException e){

            }
            museum.theater.endMovie();

            while(museum.theater.sessionLength + museum.theater.theaterSessionStartTime >= System.currentTimeMillis()){}

            if(museum.theater.sessionCount == 4){
                museum.closeMuseum();
            }
            else{
                museum.theater.endSession();
            }

            while(museum.theater.getNumOfOccupants() > 0){}
        }
    }
}