package br.com.alura.conversor.principal;

import br.com.alura.conversor.model.Moedas;
import br.com.alura.conversor.service.ConsumoApi;
import com.google.gson.Gson;

import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {

        Scanner leitura = new Scanner(System.in);
        ConsumoApi consumoApi = new ConsumoApi();
        Gson gson = new Gson();

        String apiKey = "e08358db07a0cad336ef09d5";
        String urlBase = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/USD";

        while (true) {
            System.out.println("========================================");
            System.out.println("----------Conversor de Moedas----------");
            System.out.println("========================================");
            System.out.println("1) Dólar -> Peso Argentino");
            System.out.println("2) Peso argentino -> Dólar");
            System.out.println("3) Dólar -> Real brasileiro");
            System.out.println("4) Real brasileiro -> Dólar");
            System.out.println("5) Dólar -> Peso colombiano");
            System.out.println("6) Peso colombiano -> Dólar");
            System.out.println("7) Sair");
            System.out.println("Escolha uma opção valida: ");
            System.out.println("----------------------------------------");

            int opcao = leitura.nextInt();

            if (opcao == 7) {
                System.out.println("Programa finalizado.");
                break;
            }

            System.out.print("Digite o valor para conversão: ");
            double valor = leitura.nextDouble();

            String json = consumoApi.obterDados(urlBase);
            Moedas moedas = gson.fromJson(json, Moedas.class);

            double resultado = 0;

            switch (opcao) {
                case 1, 2 -> {
                    double taxa = moedas.getConversionRates().get("ARS");
                    resultado = (opcao == 1) ? valor * taxa : valor / taxa;
                }
                case 3, 4 -> {
                    double taxa = moedas.getConversionRates().get("BRL");
                    resultado = (opcao == 3) ? valor * taxa : valor / taxa;
                }
                case 5, 6 -> {
                    double taxa = moedas.getConversionRates().get("COP");
                    resultado = (opcao == 5) ? valor * taxa : valor / taxa;
                }
                default -> System.out.println("Opção inválida!");
            }

            String moedaOrigem;
            String moedaDestino;

            switch (opcao) {
                case 1 -> { moedaOrigem = "USD"; moedaDestino = "ARS"; }
                case 2 -> { moedaOrigem = "ARS"; moedaDestino = "USD"; }
                case 3 -> { moedaOrigem = "USD"; moedaDestino = "BRL"; }
                case 4 -> { moedaOrigem = "BRL"; moedaDestino = "USD"; }
                case 5 -> { moedaOrigem = "USD"; moedaDestino = "COP"; }
                case 6 -> { moedaOrigem = "COP"; moedaDestino = "USD"; }
                default -> { moedaOrigem = ""; moedaDestino = ""; }
            }
            System.out.println("Valor " + valor + " [" + moedaOrigem + "] corresponde ao valor final de -> "
                    + resultado + " [" + moedaDestino + "]");
        }
        leitura.close();
    }
}

