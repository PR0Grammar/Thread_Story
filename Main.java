class Main{

    public static void main(String[] args){
        int theaterCap = 5;
        int numVisitors = 23;
        int theaterSessionLength = 10000;

        Museum m = new Museum(theaterCap, theaterSessionLength);
        Clock c = new Clock(m, 3000);
        Speaker s = new Speaker(m);
        c.start();
        s.start();

        for(int i = 0; i < numVisitors; i++){
            Visitor v = new Visitor(i, m);
            m.addVisitor(v);            
        }

        for(Visitor v : m.visitors){
            v.start();
        }
    }
}