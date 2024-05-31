package model;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;


/**
 * Time for the app.
 * 
 */
@SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "Model setters are package protected.")
public class Time {
  private ArrayList<TimeChangedObserver> subscribers;
  private Integer day;

  public Time() {
    day = 0;
    subscribers = new ArrayList<>();
  }

  public Integer getDay() {
    return day;
  }

  void dayUp() {
    day++;
    notifySubscribers();
  }

  /**
   * Get a copy list of subscribers.
   *
   * @return subscribers
   */
  ArrayList<TimeChangedObserver> getSubscribers() {
    ArrayList<TimeChangedObserver> subscribers = new ArrayList<>();
    for (TimeChangedObserver subscriber : this.subscribers) {
      subscribers.add(subscriber);
    }
    return subscribers;
  }

  Boolean addSubscriber(TimeChangedObserver subscriber) {
    return subscribers.add(subscriber);
  }

  void removeSubscriber(TimeChangedObserver subscriber) {
    subscribers.remove(subscriber);
  }

  private void notifySubscribers() {
    for (int i = 0; i < subscribers.size(); i++) {
      if (subscribers.get(i).timeAdvanced(getDay())) {
        removeSubscriber(subscribers.get(i));
        i--;
      }
    }
  }
}
