class Movie{
    long movieStart;
    long movieLength;
    boolean inProgress;

    public Movie(long startTime, long length){
        this.movieStart = startTime;
        this.movieLength = length;
        this.inProgress = true;
    }

    //Returns if movie has ended, only done by endMovie()
    synchronized boolean movieInProgress(){
        return this.inProgress;
    }

    synchronized void endMovie(){
        this.inProgress = false;
    }
}