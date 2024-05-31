package model;

/** Keeps track of expired contracts when time changes.
 * 
 */
public interface TimeChangedObserver {
  Boolean timeAdvanced(Integer newDay);
}
