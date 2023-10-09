public class SumatorioRecursivo
{
    public static int sumatorio(int numInicial, int numFinal) {
        if (numInicial<=numFinal) {
            return numInicial+sumatorio(numInicial+1,numFinal);                
        } else {
            return 0;
        }
    }
    
    public static void main(String args[]) {
        long tiempoInicial=System.nanoTime();
        
        System.out.print(sumatorio(1,1000)+",");
        System.out.print(sumatorio(1001,2000)+",");
        System.out.println(sumatorio(2001,3000));
        
        long tiempoFinal=System.nanoTime();
        
        System.out.print("El programa se ha ejecutado en "+(tiempoFinal-tiempoInicial)+" nanosegundos");
    }
}
