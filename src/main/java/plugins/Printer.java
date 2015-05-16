package plugins;

import rx.functions.Action1;

public class Printer {

    public static Action1 create() {
        return new Action1<String>() {
            public void call(String s) {
                System.out.println(s);
            }
        };
    }

}
