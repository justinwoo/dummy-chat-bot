package bot;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.BehaviorSubject;

import java.io.*;
import java.net.Socket;

public class Bot {

    public final String nick;
    public final String login;
    private final BehaviorSubject<String> inputStream;
    private final BehaviorSubject<String> outputStream;
    private Socket socket;
    private BufferedWriter writer;
    private BufferedReader reader;
    private Subscription outputStreamSubscription;

    public Bot(String nick, String login) {
        this.nick = nick;
        this.login = login;

        this.inputStream = BehaviorSubject.create();
        this.outputStream = BehaviorSubject.create();
    }

    public void connect(String address, int port) throws IOException {
        // make our connection and prepare our buffers
        this.socket = new Socket(address, port);
        this.writer = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream()));
        this.reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

        // do our login routine
        writer.write("NICK " + this.nick + "\n");
        writer.write("USER " + this.login + " 8 * : Bot Botterson\n");
        writer.flush();

        // set up the output stream subscription accordingly
        this.outputStreamSubscription = this.outputStream.subscribe(new Action1<String>() {
            public void call(String s) {
                try {
                    writer.write(s + "\n");
                    writer.flush();
                } catch (IOException e) {
                    System.err.println("OMG SOMETHING WENT WRONG!!!");
                    e.printStackTrace();
                }
            }
        });
    }

    public void listen() throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("PING ")) {
                this.outputStream.onNext("PONG " + line.substring(5));
            }
            inputStream.onNext(line);
        }
    }

    public void disconnect() {
        this.outputStreamSubscription.unsubscribe();
    }

    public void join(String channel) {
        this.send("JOIN " + channel + "\n");
    }

    public void send(String message) {
        this.outputStream.onNext(message);
    }

    public void subscribeToInputStream(Action1 observer) {
        inputStream.subscribe(observer);
    }

    public void subscribeToOutputStream(Action1 observer) {
        outputStream.subscribe(observer);
    }

}
