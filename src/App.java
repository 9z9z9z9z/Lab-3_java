import controller.Controller;
import model.*;
import view.View;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

public class App {
    public static Model model;
    static {
        try {
            model = new Model();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static View view = new View();
    public static Controller ctrl = new Controller(model, view);
    public static Properties prop;

    public static void main(String[] args) {
        boolean isRunning = false; // true - app is running, false - app is stopped

        App app = new App();
        app.run(model, view, ctrl, prop, isRunning);
    }

    public void run(Model model, View view, Controller controller, Properties prop, boolean isRunning) {
        isRunning = true;

        while (isRunning) {
            if (model.getGroup().equals(Model.Groups.admin.toString())) {
                int function = View.menu(View.Menus.developer);

                switch (function) {
                    case 1:
                        view.print_info(model);
                        break;
                    case 2:
                        ctrl.inputWS();
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        PrintWriter writer = null;
                        try {
                            writer = new PrintWriter(model.getConfigFileName());
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        writer.print("");
                        writer.close();

                        boolean isLogin = false;
                        function = View.menu(View.Menus.auth);
                        while (!isLogin)
                        {
                            if (function == 1)
                            {
                                if (!model.login()) {
                                    if (ctrl.inputInt("1) Продолжить попытки ввода(по умолчанию)\n2) Регистрация\n>>> ") == 2) {
                                        model.registration();
                                        isLogin = true;
                                    }
                                }
                                else {
                                    isLogin = true;
                                }
                            }
                            else if (function == 2) {
                                model.registration();
                                isLogin = true;
                            }
                        }
                        break;
                    case 6:
                        model.fromWMtoXML();
                        System.exit(0);
                        break;
                }
            }
            else if (model.getGroup().equals(Model.Groups.user.toString())) {
                int function = View.menu(View.Menus.user);

                switch (function) {
                    case 1:
                        view.print_info(model);
                        break;
                    case 2:
                        ctrl.inputWS();
                        break;
                    case 3:
                        PrintWriter writer = null;
                        try {
                            writer = new PrintWriter(model.getConfigFileName());
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        writer.print("");
                        writer.close();

                        boolean isLogin = false;
                        function = View.menu(View.Menus.auth);
                        while (!isLogin)
                        {
                            if (function == 1)
                            {
                                if (!model.login()) {
                                    if (ctrl.inputInt("1) Продолжить попытки ввода(по умолчанию)\n2) Регистрация\n>>> ") == 2) {
                                        model.registration();
                                        isLogin = true;
                                    }
                                }
                                else {
                                    isLogin = true;
                                }
                            }
                            else if (function == 2) {
                                model.registration();
                                isLogin = true;
                            }
                        }
                        break;
                    case 4:
                        model.fromWMtoXML();
                        System.exit(0);
                        break;
                }
            }

        }
    }
}