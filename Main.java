class Main{

    public static void main(String[] args){
        int theaterCap = 11;
        int numVisitors = 23;

        Museum m = new Museum(theaterCap);
        Clock c = new Clock(m, 3000);

        for(int i = 0; i < numVisitors; i++){
            Visitor v = new Visitor(i, m);
            m.addVisitor(v);            
        }
        c.start();
        for(Visitor v : m.visitors){
            v.start();
        }
    }
}