import java.util.Calendar;
import java.util.HashMap;
//import java.util.ArrayList;
//import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Task {
	protected static int idCounter = 0;
	protected int id;
	protected String label;
	protected Calendar dueDate;
	protected int estimatedTime;
	protected boolean done;
	
	public Task(String label, Calendar dueDate, int estimatedTime) {
			this.id = idCounter++;
			this.label = label;
			this.dueDate = dueDate;
			this.estimatedTime = estimatedTime;
			this.done = false;
	}

	public int getId() { return id; }

	public String getLabel() { return label; }

	public Calendar getDueDate() { return dueDate; }

	public int getEstimatedTime() { return estimatedTime; }

	public boolean isDone() { return done; }

	public void done() { done = true; }

	@Override
	public String toString() {
		return id + " " + label + " : " +
				dueDate.get(Calendar.DAY_OF_MONTH) + "/" +
				dueDate.get(Calendar.MONTH) + "/" +
				dueDate.get(Calendar.YEAR);
	}
	
	public List<Task> taskWhile (List<Task> tasks, Predicate<Task> pred){
		return tasks.stream().filter(pred).collect(Collectors.toList());
	}
	
	//autre façon - plus longue
	//public List<Task> taskWhile (List<Task> tasks, Predicate<Task> pred){
		//List<Task> liste = new ArrayList<Task>();
		//Iterator<Task> it = tasks.iterator();
		//while(it.hasNext()) {
			//Task ta = it.next();
			//if(pred.test(ta)) {
				//liste.add(ta);
			//}
		//}
		//return liste;
	//}
	
	public <T> List<T> takeWhile (List<T> list, Predicate<T> pred){
		return list.stream().filter(pred).collect(Collectors.toList());
	}
	
	public static <K,V> Map<K,V> tasksToMap(List<Task> tasks, Function<Task,K> f1, Function<Task,V> f2){
		Map<K,V> mymap = new HashMap<K,V>();
		tasks.forEach(task->mymap.put(f1.apply(task), f2.apply(task)));
		return mymap;
	}
	
	public static <T,K,V> Map<K,V> listToMap(List<T> liste, Function<T,K> f1, Function<T,V> f2){
		Map<K,V> mymap = new HashMap<K,V>();
		liste.forEach(element->mymap.put(f1.apply(element), f2.apply(element)));
		return mymap;
	}
	
}