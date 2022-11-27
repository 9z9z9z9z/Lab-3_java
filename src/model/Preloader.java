package model;


import java.io.IOException;
import java.util.logging.*;
import java.util.Date;


public class Preloader {
    private static Logger logger = null;

    public static void logging(Object obj, String mes) throws IOException{
        logger = Logger.getLogger(obj.getClass().getName());
        logger.setUseParentHandlers(false);
        FileHandler FH;

        try{
            FH = new FileHandler("config.log");
            logger.addHandler(FH);
            FH.setFormatter(new SimpleFormatter() {
                private static final String format = "{%1$tF %1$tT} {%2$-7s} %3$s %n";

                @Override
                public synchronized String format(LogRecord LR){
                    String ret = String.format(obj.getClass().getName().toUpperCase() + "|" + format, 
                    new Date(LR.getMillis()),
                    LR.getLevel(),
                    LR.getMessage());
                    return ret;
                }

            });
        } catch (SecurityException ex){
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    
        logger.log(Level.INFO, "\n\t>>" + mes);
    }
}
