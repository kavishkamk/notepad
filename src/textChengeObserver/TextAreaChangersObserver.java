package textChengeObserver;

/*
 * used to reserve notification about text area changers from object(Observable), which must be a TextAreaChangersObserver
 * to create object from this class, override textAreaChengeUpdate() method is required
 */

public interface TextAreaChangersObserver {
	/*
	 *  this method called by Observable to inform text area change
	 *  you can override this method with action when reserver change notification
	 */
	void textAreaChengeUpdate();

}
