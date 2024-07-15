package com.proyect.conversordemonedas.principal;

import com.proyect.conversordemonedas.service.ConsumoApi;
import com.proyect.conversordemonedas.service.ConvierteDatos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {

        ConsumoApi consumoApi = new ConsumoApi();
        ConvierteDatos conversor = new ConvierteDatos();
        Scanner teclado = new Scanner(System.in);

        String menu = "********************************************\n"
                + "Sea bienvenido/a al Conversor de Monedas =]\n"
                + "\n"
                + "1) DOLAR =>> QUETZAL GUATEMALTECO\n"
                + "2) QUETZAL GUATEMALTECO =>> DOLAR\n"
                + "3) DOLAR =>> MONEDA RUSA\n"
                + "4) MONEDA RUSA =>> DOLAR\n"
                + "5) DOLAR =>> MONEDA COLOMBIANA\n"
                + "6) MONEDA COLOMBIANA =>> DOLAR\n"
                + "7) SALIR\n"
                + "ELIJE LA OPCION QUE DESEAS\n"
                + "**********************************************";
        String solicitudAConvertir = "INGRESE EL VALOR QUE DESEA CONVERTIR";

        while (true){

            try {
                System.out.println(menu);
                var solicitudUsuario = teclado.nextInt();
                //System.out.println(solicitudUsuario);

                if (solicitudUsuario == 7) {
                    break;
                }if (solicitudUsuario <= 0){
                    System.out.println("OPCION INVALIDA");
                    break;
                }if (solicitudUsuario >= 8){
                    System.out.println("OPCION INVALIDA");
                    break;
                }

                System.out.println(solicitudAConvertir);
                var montoUsuario = teclado.nextDouble();
                //System.out.println(montoUsuario);

                String urlBase = "https://v6.exchangerate-api.com/v6/";
                String apiKey = "acfdd74252c10f291bf75d77";
                String urlRespuesta = "/pair/";
                String monedaBase = "";
                String monedaDestino = "";

                switch (solicitudUsuario) {
                    case 1:
                        monedaBase = "USD";
                        monedaDestino = "GTQ";
                        break;
                    case 2:
                        monedaBase = "GTQ";
                        monedaDestino = "USD";
                        break;
                    case 3:
                        monedaBase = "USD";
                        monedaDestino = "RUB";
                        break;
                    case 4:
                        monedaBase = "RUB";
                        monedaDestino = "USD";
                        break;
                    case 5:
                        monedaBase = "USD";
                        monedaDestino = "COP";
                        break;
                    case 6:
                        monedaBase = "COP";
                        monedaDestino = "USD";
                        break;
                }

                URI direccion = URI.create(urlBase + apiKey + urlRespuesta + monedaBase + "/" + monedaDestino + "/" + montoUsuario);

                String json = consumoApi.obtenerDatosApi(direccion);
                //System.out.println("Json: " + json);

                var conversion = conversor.convierteDatos(json);

                BigDecimal resultado = BigDecimal.valueOf(montoUsuario * conversion.conversion_rate());
                System.out.println("EL VALOR DE: $" + montoUsuario + " ["
                        + monedaBase + "] CORRESPONDE AL VALOR DE =>>> $"
                        + resultado.setScale(2, RoundingMode.HALF_UP) + " [" + monedaDestino + "].");

                var comprobacionResultado = conversion.conversion_result();
                //System.out.println("Comprobacion del valor de: $"+montoUsuario+" ["+monedaBase+"] corresponde al valor final de =>>> $"+comprobacionResultado+" ["+monedaDestino+"].");

            } catch (InputMismatchException e) {
                System.out.println("SOLO SE ACEPTAN VALORES NUMERICOS");
                break;
            }
        }
    }
}
