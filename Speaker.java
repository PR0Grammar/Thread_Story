class Speaker extends Thread{
    Museum museum;
    private static long time = System.currentTimeMillis();

    public Speaker(Museum m){
        this.museum = m;
        this.setName("Speaker");
    }

    public void msg(String m) {
        System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
    }

    public void run(){
        while(museum.theater.sessionCount < 4){
            while(!museum.theater.inSession() || museum.theater.movieIsPlaying()){} //Busy wait

            this.msg("Time for everyone to get into groups!");
            for(Visitor v : museum.theater.viewers){
                v.interrupt();
            }
            //Busy wait till all visitors are in groups
            while(museum.theater.getNumOfVisitorsGrouped() < museum.theater.numOfViewers()){}
            
            this.msg("Time to hand out party tickets! I'll call groups one by one");

            //Give party tickets to groups one by one
            for(int i = 0; i < museum.theater.numOfGroups(); i++){
                this.msg("Group " + i +", come up for your tickets!");
                museum.theater.distributeTickets(i);
            }

            this.msg("Done distributing tickets to all groups. Feel free to leave whenever!");
            //Wait till session is finally over
            while(museum.theater.inSession()){};
        }
    }

}