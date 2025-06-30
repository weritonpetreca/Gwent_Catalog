package com.petreca.gwent_catalog.console;

import com.petreca.gwent_catalog.model.Faction;
import com.petreca.gwent_catalog.model.Rarity;
import com.petreca.gwent_catalog.util.ConsoleColors;

import java.util.Scanner;

public class InputValidator {

    private static final Scanner scanner = new Scanner(System.in);

    public static int getValidInteger(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println(ConsoleColors.error("Por favor, digite um número entre " + min + " e " + max));
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColors.error("Por favor, digite um número válido"));
            }
        }
    }

    public static String getValidString(String prompt, boolean allowEmpty) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (!input.isEmpty() || allowEmpty) {
                return input.isEmpty() ? null : input;
            }

            System.out.println(ConsoleColors.error("Este campo é obrigatório"));
        }
    }

    public static Faction getValidFaction(String prompt) {
        while (true) {
            System.out.println(prompt);
            System.out.println("Facções disponíveis:");
            for (int i = 0; i < Faction.values().length; i++) {
                System.out.printf("%d - %s%n", i +1, Faction.values()[i].getApiValue());
            }
            System.out.print("Digite o número da Facção: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice >= 1 && choice <= Faction.values().length) {
                    return Faction.values()[choice -1];
                }
                System.out.println(ConsoleColors.error("Opção inválida."));
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColors.error("Por favor, digite um número válido."));
            }
        }
    }

    public static Rarity getValidRarity(String prompt) {
        while (true) {
            System.out.println(prompt);
            System.out.println("Raridades disponíveis:");
            for (int i = 0; i < Rarity.values().length; i++){
                System.out.printf("%d - %s%n", i + 1, Rarity.values()[i].getApiValue());
            }
            System.out.print("Digite o número da Raridade: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice >= 1 && choice <= Rarity.values().length) {
                    return Rarity.values()[choice - 1];
                }
                System.out.println(ConsoleColors.error("Opção inválida."));
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColors.error("Por favor, digite um número válido."));
            }
        }
    }

    public static boolean getYesNoConfirmation(String prompt) {
        while (true) {
            System.out.print(prompt + "(s/n): ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("s") || input.equals("sim") || input.equals("y") || input.equals("yes")) {
                return true;
            } else if (input.equals("n") || input.equals("não") || input.equals("nao") || input.equals("no")) {
                return false;
            }

            System.out.println(ConsoleColors.error("Por favor, digite 's' para sim ou 'n' para não."));
        }
    }
}
