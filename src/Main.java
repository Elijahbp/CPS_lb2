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
                3,4,2,1,1,1,0
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
        double N0 = methods.getN0(K);

        System.out.println("-----N0 = "+N0+"-----K = "+K+"-------");
        for (int j = 0; j < listK.size(); j++) {
            if (listK.get(j)<0.0001) {
                System.out.println("K[" + j + "] =" + listK.get(j));
            }else break;
        }

        System.out.println("Полученные точки:");
        double divN0=N0;
        ArrayList<Double> bufArrays = methods.getDifN(K,N0);
        for (int j = 0; j < bufArrays.size(); j++) {
            System.out.println("n"+j+"= "+bufArrays.get(j));
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
        System.out.println("Кол-во оставшихся ошибок: "+divN0);

        ArrayList<Double> doubles = methods.getPNO(bufArrays,K);
        for (int j = 0; j < doubles.size(); j++) {
            System.out.println("P"+j+"= "+ doubles.get(j));
        }
        System.out.println();

    }
}
