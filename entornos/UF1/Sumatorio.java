public class Sumatorio
{
    public static void main(String args[]) {
        long tiempoInicial=System.nanoTime();
        
        int resultado=0;
        for (int contador=1;contador<=1000;contador++) {
            resultado+=contador;
        }
        System.out.print(resultado+",");
        
        resultado=0;
        for (int contador=1001;contador<=2000;contador++) {
            resultado+=contador;
        }
        System.out.print(resultado+",");
        
        resultado=0;
        for (int contador=2001;contador<=3000;contador++) {
            resultado+=contador;
        }
        System.out.println(resultado);
        
        long tiempoFinal=System.nanoTime();
        
        System.out.print("La programa se ha ejecutado en "+(tiempoFinal-tiempoInicial)+" nanosegundos");
    }
}
