package htw.Log;

import java.util.LinkedList;
import java.util.List;

public class NetworkLog {

    private List<LogSubscriber> subscribers = new LinkedList<>();

    public void log(LogEntry log) {
        for (LogSubscriber subscriber : subscribers) {
            subscriber.publish(log);
        }
    }

    public void addSubscriber(LogSubscriber subscriber) {
        subscribers.add(subscriber);
    }

}
