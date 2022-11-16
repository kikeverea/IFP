package ifp.kikeverea;

import ifp.kikeverea.edificios.Almacen;
import ifp.kikeverea.edificios.Edificio;
import ifp.kikeverea.edificios.Fabrica;
import ifp.kikeverea.edificios.Oficina;
import ifp.kikeverea.productos.Producto;
import ifp.kikeverea.productos.Puerta;
import ifp.kikeverea.productos.Silla;
import ifp.kikeverea.vehiculos.Vehiculo;
import ifp.kikeverea.vehiculos.VehiculoElectrico;
import ifp.kikeverea.vehiculos.VehiculoGasoil;

public class Start {

    public static void main(String[] args) {
        Empresa empresa = new Empresa();
        Vehiculo seat = new VehiculoElectrico("Seat Altea", "rojo", 20000, 3, 3000, 4000);
        Vehiculo renault = new VehiculoGasoil("Renault Kadjar", "azul", 28000, 3.5f, 4000);
        Edificio fabrica = new Fabrica(400, 400, 500);
        Edificio oficina = new Oficina(600, 400, 700);
        Edificio almacen = new Almacen(600, 400, 700);
        Producto silla1 = new Silla(1, "Silla N30", 1.0f, 0.5f, 0.5f);
        Producto silla2 = new Silla(2, "Silla M100", 1.5f, 0.5f, 1.5f);
        Producto puerta1 = new Puerta(3, "Puerta P9", 1.5f, 2.25f, 0.25f);
        Producto puerta2 = new Puerta(4, "Puerta F50", 1.25f, 1.25f, 0.25f);

        float precioPintarFabrica = fabrica.costePintura(30.0f);

        empresa.setEdificio1(fabrica);
        empresa.setEdificio2(oficina);
        empresa.setEdificio3(almacen);
        empresa.setVehiculo1(seat);
        empresa.setVehiculo2(renault);
        empresa.setProducto1(silla1);
        empresa.setProducto2(silla2);
        empresa.setProducto3(puerta1);
        empresa.setProducto4(puerta2);

        System.out.println();
        System.out.println("***************************");
        System.out.println("****** MATERIALCONTS ******");
        System.out.println("***************************");
        System.out.println(empresa.mostrarInfo());

        System.out.println("Precio para pintar la fábrica completa, con precio por metro 30€:");
        System.out.printf("%.2f%s\n", precioPintarFabrica, "€");
    }
}
