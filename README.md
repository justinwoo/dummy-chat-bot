# dummy-chat-bot
a stupid chatbot that uses RxJava to do stuff

looks like this:

![](https://s3.amazonaws.com/f.cl.ly/items/1F010R2J1K2e0R422U16/Image%202015-05-16%20at%2012.24.02%20PM.png)

## how's it work??

currently exposes two methods for registering "plugins":

```java
    public void subscribeToInputStream(Action1 observer) {
        inputStream.subscribe(observer);
    }

    public void subscribeToOutputStream(Action1 observer) {
        outputStream.subscribe(observer);
    }
```

## "plugin" "design"

just a simple rx.functions.Action1 because what is good design/foresight??

```java
public class Printer {
    public static Action1 create() {
        return new Action1<String>() {
            public void call(String s) {
                System.out.println(s);
            }
        };
    }
}
```
