class Speaker extends Thread{
    Museum museum;

    public Speaker(Museum m){
        this.museum = m;
    }

    public void run(){
        while(museum.theater.sessionCount < 4){
            while(!museum.theater.movieInSession() || museum.theater.movieInSession()){} //Busy wait
            System.out.println("speaker wakeupo");

            for(Visitor v : museum.theater.viewers){
                v.interrupt();
            }

            while(museum.theater.getNumOfOccupants() > 0){};
        }
    }
}