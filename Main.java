class Main{

    public static void main(String[] args){
        int numVisitors = Integer.parseInt(args[0]);
        int theaterCap = 5;
        int partyTicketSize = 3;

        Museum m = new Museum(theaterCap, partyTicketSize);
        Clock c = new Clock(m, 3000);
        Speaker s = new Speaker(m);

        for(int i = 0; i < numVisitors; i++){
            Visitor v = new Visitor(i, m);
            m.addVisitor(v);            
        }

        //Start all threads
        c.start();
        s.start();
        for(Visitor v : m.visitors){
            v.start();
        }
    }
}