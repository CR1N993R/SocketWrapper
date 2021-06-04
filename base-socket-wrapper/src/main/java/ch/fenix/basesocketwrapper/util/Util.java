package ch.fenix.basesocketwrapper.util;

public class Util {
    public static void notNull(Object... objects){
        for (Object object : objects) {
            if (object == null){
                throw new NullPointerException();
            }
        }
    }
}
