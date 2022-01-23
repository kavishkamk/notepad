package textChengeObserver;

/*
 * used to notify text area edit scene to objects(Observers), which must be a TextAreaChangersObserver 
 * Observers must be registered with object which is created using this interface
 * to initiate object which implemented this class should override all three methods with concrete implementation
 */

public interface TextAreaChangersObservable {
	
	/*
	 *  used to send notification to all observers
	 */
	void notfyCordinationsToObservers(int x, int y);
	
	/*
	 *  used to register observers
	 *  to register observer must be a TextAreaChengersObserver
	 */
	void registerObserver(TextAreaChangersObserver observer);
	
	/*
	 *  used to unregister observers
	 */
	void unregisterObserver(TextAreaChangersObserver observer);
	
}
