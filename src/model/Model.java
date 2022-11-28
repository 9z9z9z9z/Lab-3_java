package model;

import controller.Controller;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import view.View;

import java.io.*;
import java.util.*;


public class Model {
    Controller ctrl;
    public enum Groups{
        admin ("admin"),
        user ("user");

        private final String name;
        private Groups(String name){
            this.name = name;
        }
        public boolean equalsName(String otherName){
            return name.equals(otherName);
        }
    }

    private List<WashingMachine> machines = new ArrayList<WashingMachine>();
    final private static String usersFile = "Users.ini";
    final private static String configFile = "Config.ini";
    final private static String MachinesXMLFile = "Machines.xml";
    final private static String logFile = "Log.txt";
    private static String login;
    private static String password;
    private static String group;
    private static boolean debug;
    private static boolean autotest;
    private Map<String, String> database = new HashMap<String, String>();
    private static Properties usersProp = new Properties();
    private static Properties configProp = new Properties();

    // Constructor
    public Model() throws IOException {
        try {
            FileWriter fw = new FileWriter("logs.log", true);

            fw.append("\n\t{New Session}\n\n");
            fw.close();
        } catch (IOException ex) {
            throw new RuntimeException();
        }
        try {
            getConfigFile(usersFile, usersProp);
            getConfigFile(configFile, configProp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadUsers();
        loadCurrentUsers();
        boolean flag = true;
        if (!loadUserConfig()){
            int function = View.menu(View.Menus.auth);
            while (flag){
                if (function == 1){
                    if ( !login() ){
                        if(ctrl.inputInt("1) Continue entering:\n2)Registration:\n>>>\t") == 2){
                            registration();
                            flag = false;
                        }
                    }
                    else{
                        flag = false;
                    }
                }
                else{
                    registration();
                    flag = false;
                }
            }
        }
        for (WashingMachine item : machines) {
            item = fromXMLtoWM();
            if (item == null){
                item = new WashingMachine();
                machines.add(item);
                break;
            }
            else { machines.add(item); }
        }
        try{
            Preloader.logging(this, "Machine indeed");
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public List<WashingMachine> getWMs(){ return this.machines; }
    public void setWM(List<WashingMachine> wms){ this.machines = wms; }
    public String getLogin() { return login; }
    public String getPassword() { return password; }
    public Properties getProperties() { return usersProp; }
    public  String getConfigFileName() { return configFile; }
    public String getGroup() { return group; }

    public boolean login() {
        boolean islogin = false;
        String logTmp = "";
        String passwordTmp = "";

        while (true){
            System.out.println(database);
            logTmp = ctrl.inputString("Authentication:\nLogin:\n");
            passwordTmp = ctrl.inputString("\nInput password:\n");
            for (String loginItem : database.keySet()){
                if (logTmp.equals(loginItem)){
                    if (passwordTmp.equals(database.get(loginItem))){
                        login = logTmp;
                        password = passwordTmp;
                        group = database.get(login + "_group");
                        islogin = true;
                        configProp.put (login, password);
                        configProp.put(login + "_group", group);
                        saveConfigFile(configFile, configProp);
                    }
                }
            }

            if (!islogin){
                boolean ans = Controller.inputBoolean("User not found.\n If you want to register, tap true|false:\t");
                if (ans){
                    registration();
                    islogin = true;
                }
            }
            else{ break; }
        }

        try{
            Preloader.logging(this, login);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return islogin;
    }

    public void registration(){
        boolean logFlag = true;
        boolean passwordFlag = true;

        while (logFlag && passwordFlag){
            String loginField = ctrl.inputString("Enter your login:\t");
            String passwordField = ctrl.inputString("Enter your password:\t");
            String grouField = ctrl.inputString("Enter your group:\t");

            if (!Groups.admin.equalsName(grouField) && !Groups.user.equalsName(grouField)){
                System.out.println("\nInvalid group!\n");
                continue;
            }
            if (!database.keySet().contains(loginField)){
                usersProp.put(logFlag, passwordField);
                usersProp.put(loginField + "_group", passwordField);
                configProp.put(loginField, passwordField);
                configProp.put(loginField + "_group", passwordField);
                login = loginField;
                password = passwordField;
                group = grouField;
                logFlag = false;
                passwordFlag = false;
                saveConfigFile(usersFile, usersProp);
                saveConfigFile(configFile, configProp);
                loadUsers();
            }
            else{ System.out.println("THis user is already exists."); }
        }
        try{
            Preloader.logging(this, "Registration");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadUsers(){
        PrintWriter PW = new PrintWriter(System.out);

        try{
            for (final String name: usersProp.stringPropertyNames()){ database.put(name, usersProp.getProperty(name)); }
            PW.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try{
            Preloader.logging(this, "Loadding users:\n\tProps:\n\t" + database.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void loadCurrentUsers(){
        PrintWriter PW = new PrintWriter(System.out);
        try{
            for (final String name : usersProp.stringPropertyNames()){
                if (!name.equals("_group")){
                    login = name;
                    password = configProp.getProperty(name);
                    group = configProp.getProperty(name + "_group");
                }
            }
            PW.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try{
            Preloader.logging(this, "Loadding users:\n\t Prop:" + database.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean loadUserConfig(){
        PrintWriter PW = new PrintWriter(System.out);
        boolean isload = true;
        try{
            for (final String name : configProp.stringPropertyNames()){
                if (!name.contains("_group")){
                    login = name;
                    password = configProp.getProperty(name);
                    group = configProp.getProperty(name + "_group");
                }
            }
            PW.flush();
            if (configProp.isEmpty()){
                isload = false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            Preloader.logging(this, "Load users config:\n\tProperties:\t" + database.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return isload;
    }
    public void saveUsers(){
        for (String logVal : database.keySet()){
            usersProp.put(logVal, database.get(logVal));
        }
        saveConfigFile(usersFile, usersProp);

        try{
            Preloader.logging(this, "Saving users");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveConfigFile(String filename, Properties props){
        FileOutputStream FOS = null;
        try{
            FOS = new FileOutputStream("./saves/" + filename);
            props.store(FOS, null);
            FOS.close();
        } catch(IOException IOex){
            throw new RuntimeException(IOex);
        }
        try {
            Preloader.logging(this, "Saved config file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public WashingMachine fromXMLtoWM(){
        try{
            JAXBContext context = JAXBContext.newInstance(WashingMachine.class);
            Unmarshaller unmarsh = context.createUnmarshaller();
            if (new File(MachinesXMLFile).length() <= 60){
                return new WashingMachine();
            }
            else{
                return (WashingMachine)unmarsh.unmarshal(new File(MachinesXMLFile));
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        try {
            Preloader.logging(this, "From XML to Washing machine");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void fromWMtoXML(){
        try{
            JAXBContext context = JAXBContext.newInstance(WashingMachine.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(String.valueOf(machines), new File(MachinesXMLFile));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        try{
            Preloader.logging(this, "From WashingMachine to XML");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getConfigFile(String fileName, Properties props) throws Exception {
        InputStream filestream;
        try{
            File file = new File("./saves/" + fileName);
            if (file.exists()){
                filestream = new FileInputStream(file);
            }
            else{
                filestream = Model.class.getResourceAsStream(fileName);
            }

            props.load(filestream);

            assert  filestream != null;
            filestream.close();
        } catch (Exception FNex){
            throw new RuntimeException(FNex);
        }

        try{
            Preloader.logging(this, "Get config file:\n\t");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getFieldValue(Object obj, String fieldname){
        Class clas = obj.getClass();
        try{
            java.lang.reflect.Field field = clas.getDeclaredField(fieldname);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            return null;
        }
    }
    public void runDebug(){
        debug = true;

        try{
            Preloader.logging(this, "Debug");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void runAutotes(){
        autotest = true;
        try{
            Preloader.logging(this, "Autotests");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


