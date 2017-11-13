import java.util.ArrayList;

public class Methods {
    private ArrayList<Integer> listTime ;
    private ArrayList<Integer> listCountException;
    private ArrayList<Double> listK;
    private ArrayList<Double> listDifN = new ArrayList<>();
    private double K;
    private double N0;
    private static final double EPS = 0.0001;

    public Methods(ArrayList<Integer> listTime,ArrayList<Integer> listCountException, ArrayList<Double> listK) {
        this.listTime = listTime;
        this.listCountException = listCountException;
        this.listK = listK;
        setK();
        setN0();
        setListDifN();
    }

    private void setK(){
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
                K = k;
            }
        }
    }

    public double getK() {
        return this.K;
    }

    public void setN0(){
        double value_ch=0;
        double value_zn=0;
        for (int i = 0; i < listTime.size(); i++) {
            value_ch += listCountException.get(i)*listTime.get(i)*Math.exp(-1*this.K*listTime.get(i));
            value_zn +=listTime.get(i)*Math.exp(-2*this.K*listTime.get(i));
        }
        this.N0 = value_ch/(this.K*value_zn);
    }

    public double getN0() {
        return N0;
    }

    public void setListDifN(){
        for (int i = 0; i < listTime.size(); i++) {
            double exp = Math.exp(-1*this.K*listTime.get(i));
            listDifN.add(this.N0*this.K*exp);
        }
    }

    public ArrayList<Double> getListDifN() {
        return listDifN;
    }

    public double[] getLinearAproximation(){
        int [] valuesX = new int[listDifN.size()];
        for (int i = 0; i <valuesX.length; i++) {
            valuesX[i] =i;
        }
        double sumX=0;
        double sumX2=0;
        double sumY=0;
        double sumXY=0;
        int n = valuesX.length;
        for (int i = 0; i < n; i++) {
            sumX += valuesX[i];
            sumX2 += Math.pow(valuesX[i],2);
            sumY += listDifN.get(i);
            sumXY += valuesX[i] * listDifN.get(i);
        }
        double a = (n * sumXY - (sumX*sumY))/(n * sumX2 - sumX*sumX);
        double b = (sumY - a * sumX)/ n;

        double[] valueFunction = new double[valuesX.length];
        for (int i = 0; i < n; i++) {
            valueFunction[i] = a * valuesX[i] + b;
        }

        return valueFunction;
    }

    public ArrayList<Double> getListPN0(double[] listDeltNi){
        ArrayList<Double> listPN0 = new ArrayList<>();
        double bufPN0=0;
        for (Double dNi: listDeltNi) {
            bufPN0 =((this.K-2*dNi)/(this.K-dNi));
            listPN0.add(bufPN0);
        }
        return listPN0;
    }
}
