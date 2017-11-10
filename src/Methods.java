import java.util.ArrayList;

public class Methods {
    private ArrayList<Integer> listTime ;
    private ArrayList<Integer> listCountException;
    private ArrayList<Double> listK;
    private static final double EPS = 0.0001;

    public Methods(ArrayList<Integer> listTime,ArrayList<Integer> listCountException, ArrayList<Double> K) {
        this.listTime = listTime;
        this.listCountException = listCountException;
        this.listK = K;
    }

    public double getN0(double K){
        double N0=0;
        double value_ch=0;
        double value_zn=0;
        double valueK = K;
        for (int i = 0; i < listTime.size(); i++) {
            value_ch += listCountException.get(i)*listTime.get(i)*Math.exp(-1*valueK*listTime.get(i));
            value_zn +=listTime.get(i)*Math.exp(-2*valueK*listTime.get(i));
        }
        N0 = value_ch/(valueK*value_zn);
        return N0;
    }

    public double getK(){
        double kLeft =0;
        double kRight =0;
        for (double k: listK) {
            double value1L=0;double value2L=0;double value3L_zn=0;
            double value1R=0;
            double decLR=0;
            for (int i = 0; i < listTime.size(); i++) {
                value1L += Math.exp(-2 * k * listTime.get(i));
                value2L += listCountException.get(i) * Math.exp(-1 * k * listTime.get(i)) * listTime.get(i);
                value3L_zn += Math.exp(-2 * k * listTime.get(i)) * listTime.get(i);
                value1R += listCountException.get(i) * Math.exp(-1 * k * listTime.get(i));
            }
            kLeft = value1L * value2L / value3L_zn;
            kRight = value1R;
            decLR = Math.abs(kLeft - kRight);
            if (EPS > decLR) {
                return k;
            }
        }
        return 0;
    }

    public ArrayList<Double> getDifN(double k, double N0){
        ArrayList<Double> listDifN = new ArrayList<>();
        for (int i = 0; i < listTime.size(); i++) {
            double exp = Math.exp(-1*k*listTime.get(i));
            listDifN.add(N0*k*exp);
        }
        return listDifN;
    }
    //TODO доделать линейную апроксимацию
//    public double getLinearAproximation(){
//        for (int i = 0; i < listTime.size(); i++) {
//
//        }
//    }

    public ArrayList<Double> getPNO(ArrayList<Double> difN,double K){
        ArrayList<Double> Pn0 = new ArrayList<>();
        for (double v:difN) {
            Pn0.add(1-(v/K));
        }
        return Pn0;
    }
}
