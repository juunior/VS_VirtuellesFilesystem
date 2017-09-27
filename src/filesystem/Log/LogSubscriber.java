package filesystem.Log;

public interface LogSubscriber {

    void publish(LogEntry log);

}
