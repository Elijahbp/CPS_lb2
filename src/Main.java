import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Integer [] arrayTime= new Integer[]{
                1,2,3,4,5,6,7
        };

        Integer [] arrayCountException= new Integer[]{
                5,4,2,2,1,1,0
        };

        ArrayList<Integer> listTime = new ArrayList<>();
        listTime.addAll(Arrays.asList(arrayTime));

        ArrayList<Integer> listCountException = new ArrayList<>();
        listCountException.addAll(Arrays.asList(arrayCountException));

        ArrayList<Double> listK =new ArrayList<>();
        double i=0;
        while (i<=1){
            listK.add(i);
            i+=0.000005;
        }
        Methods methods = new Methods(listTime,listCountException,listK);
        double K = methods.getK();
        double N0 = methods.getN0();

        System.out.println("-----N0 = "+N0+"-----K = "+K+"-------");
        for (int j = 0; j < listK.size(); j++) {
            if (listK.get(j)<0.0001) {
                System.out.println("K[" + j + "] =" + listK.get(j));
            }else break;
        }

        System.out.println("Полученные точки:");
        double divN0=N0;
        ArrayList<Double> bufArrays = methods.getListDifN();
        for (int j = 0; j < bufArrays.size(); j++) {
            System.out.println("x="+j+" deltNi="+bufArrays.get(j));
            divN0-=bufArrays.get(j);
        }
        System.out.println("Невязки в точках:");
        double sumSquareDif=0;
        for (int j = 0; j < bufArrays.size(); j++) {
            double value = (Math.abs(listCountException.get(j)-bufArrays.get(j)));
            System.out.println("Невязка №"+j+"="+value);
            System.out.println("Квадрат Невязки №"+j+"= "+Math.pow(value,2));
            sumSquareDif += Math.pow(value,2);
        }
        System.out.println("Сумма квадратов невязок ="+sumSquareDif);
        System.out.println("Кол-во оставшихся ошибок: "+divN0+"\n");

        System.out.println("Апроксимация линейной функции: ");
        ArrayList<Double> bufAproxArray = methods.getListAproximation();
        System.out.println("A= "+methods.getA());
        System.out.println("B= "+methods.getB());
        System.out.println("Невязки в точках:");
        for (int x = 0; x < bufAproxArray.size(); x++) {
            System.out.println("x="+x+" deltNi="+bufAproxArray.get(x));
        }
        System.out.println("Полученные точки:");
        sumSquareDif=0;
        for (int j = 0; j < bufAproxArray.size(); j++) {
            double value = (Math.abs(listCountException.get(j)-bufAproxArray.get(j)));
            System.out.println("Невязка №"+j+"= "+value);
            System.out.println("Квадрат Невязки №"+j+"= "+Math.pow(value,2));
            sumSquareDif += Math.pow(value,2);
        }
        System.out.println("Сумма квадратов невязок ="+sumSquareDif+"\n");

        double[] bufArrayDeltNi = {
            0.0001,0.0002,0.0003,0.0004,0.0005,
                0.001,0.002,0.003,0.004,0.005,
                0.01,0.02,0.03,0.04,0.05,
                0.1,0.2,0.3,0.4,0.5
        };


        ArrayList<Double> arrayListPN0 = methods.getListPN0( bufArrayDeltNi);
        for (int j = 0; j < arrayListPN0.size(); j++) {
            System.out.println("P"+j+"= "+ arrayListPN0.get(j)+ "  dNi"+bufArrayDeltNi[j]);
        }
        System.out.println();

    }
}
