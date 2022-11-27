package view;

import controller.*;
import model.Model;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class View {
    public enum Menus {
        auth,
        developer,
        user
    }
    private static List<String> authMenu = new ArrayList<String>(Arrays.asList(
            "1) Login",
            "2) Registration"
    ));

    private static List<String> adminMenu = new ArrayList<String>(Arrays.asList(
            "1) Print laundry info",
            "2) Add new machine",
            "3) Debug",
            "4) Autotests",
            "5) Exit"
    ));

    private static List<String> userMenu = new ArrayList<String>(Arrays.asList(
            "1) Print machine info",
            "2) Load linens",
            "3) Exit"
    ));

    private static List<String> laundry_info = new ArrayList<String>(Arrays.asList(
            "1) All",
            "2) Empty machines"
    ));

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";

    //text
    public static void send_text(String message) {
        System.out.println(message);
    }

    //info
    public static void send_info(String message) {
        System.out.println(ANSI_GREEN + "INFO" + " : " + message + ANSI_RESET);
    }

    //error
    public static void send_error(String message) {
        System.out.println(ANSI_RED + "ERROR" + " : " + message + ANSI_RESET);
    }

    //debug
    public static void send_debug(String message) {
        System.out.println(ANSI_YELLOW + "DEBUG" + " : " + message + ANSI_RESET);
    }

    //warning
    public static void send_warning(String message) {
        System.out.println(ANSI_CYAN + "WARNING" + " : " + message + ANSI_RESET);
    }
    static Controller ctrl;
    public static int menu(Menus menu_type) {
        int user_choice = 0;
        switch (menu_type) {
            case auth:
                for (String item: authMenu)
                    System.out.println(item);
                user_choice = ctrl.inputInt(">> ");
                break;
            case developer:
                for (String item: adminMenu)
                    System.out.println(item);
                user_choice = ctrl.inputInt(">> ");
                break;
            case user:
                for (String item: userMenu)
                    System.out.println(item);
                user_choice = ctrl.inputInt(">> ");
                break;
        }

        return user_choice;
    }

    public void print_info(Model model) {
        String text = "";
        for (String item: laundry_info)
            text += item + "\n";
        int user_choice = ctrl.inputInt(text);

        switch (user_choice) {
            case 1:
                System.out.println(model.getWMs().toString());
                break;
        }
    }
}
