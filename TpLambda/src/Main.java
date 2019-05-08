import java.util.ArrayList;
import java.util.Calendar;
//import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date(100));
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(new Date(200));
		Calendar cal3 = Calendar.getInstance();
		cal3.setTime(new Date(300));
		Calendar cal0 = Calendar.getInstance();
		cal0.setTime(new Date(50));
		Task tache1 = new Task("description de tache1",cal1,10);
		Task tache2 = new Task("description de tache2",cal2,20);
		Task tache3 = new Task("description de tache3",cal3,30);
		Task tache0 = new Task("description de tache0",cal0,40);
		ArrayList<Task> list = new ArrayList<Task>();
		list.add(tache1);list.add(tache2);list.add(tache3);list.add(tache0);
		
		//comparaison des tâches selon leur date butoir
		Comparator<Task> CompaDateButoir = (task1,task2)->task1.getDueDate().compareTo(task2.getDueDate());
		TreeSet<Task> SortedTasks = new TreeSet<Task>(CompaDateButoir);
		SortedTasks.addAll(list);
		//Collections.sort(list,CompaDateButoir);  //ou tri directement avec Collections.sort
		System.out.println("--- Comparaison sur les DueDate ---");
		SortedTasks.forEach(task->System.out.println(task.toString()));
		//autre façon d'afficher les tâches triées (utilisation d'un iterator plutôt que forEach)
		//Iterator<Task> it = SortedTasks.iterator();
		//while(it.hasNext()) {
	    //    System.out.println(it.next().toString());
		//}
		
		//comparaison des tâches selon leur durée estimée
		Comparator<Task> CompaEstimatedDate = (task1,task2)->Integer.compare(task1.getEstimatedTime(), task2.getEstimatedTime());
		TreeSet<Task> SortedDates = new TreeSet<Task>(CompaEstimatedDate);
		SortedDates.addAll(list);
		System.out.println("--- Comparaison sur les EstimatedTime ---");
		SortedDates.forEach(task->System.out.println(task.toString()));
		
		System.out.println("--- Affichage du label ---");
		list.forEach(task->System.out.println(task.getLabel()));
		
		System.out.println("--- Affichage de l'ID et de la durée estimée ---");
		list.forEach(task->System.out.println(task.getId()+":"+task.getEstimatedTime()));
		
		System.out.println("--- Test tâche terminée ---");
		Boolean IsOneFinished = list.stream().anyMatch(task->task.isDone());
		//Boolean IsOneFinished2 = list.stream().anyMatch(Task::isDone); //autre syntaxe
		System.out.println(IsOneFinished);
		tache1.done();
		IsOneFinished = list.stream().anyMatch(task->task.isDone());
		System.out.println(IsOneFinished);
		
		System.out.println("--- Nb de tâches non terminées ---");
		tache3.done();
		Predicate<Task> pred = task->!task.isDone(); //tâches non terminées
		List<Task> taches = list.stream().filter(pred).collect(Collectors.toList());
		System.out.println(taches.size());
		taches.forEach(task->System.out.println(task.toString()));
		
		System.out.println("--- Nb de tâches non terminées et contenant 2 ---");
		Predicate<Task> pred2 = task->(!task.isDone())&&task.getLabel().contains("2");
		List<Task> taches2 = list.stream().filter(pred2).collect(Collectors.toList());
		System.out.println(taches2.size());
		taches2.forEach(task->System.out.println(task.toString()));
		
		System.out.println("--- Afficher les labels ---");
		list.forEach(task->System.out.println(task.getLabel()));
		
		System.out.println("--- ID des tâches ayant une durée estimée > 20 ---");
		Predicate<Task> pred3 = task->task.getEstimatedTime()>20;
		List<Task> taches3 = list.stream().filter(pred3).collect(Collectors.toList());
		taches3.forEach(task->System.out.println(task.getId()));
		
		System.out.println("--- Tâche la plus proche de la date courante ---");
		Date today = new Date();
		long ecart = Math.abs(today.getTime()-list.get(0).getDueDate().getTime().getTime());
		Task plusproche = list.get(0);
		Iterator<Task> it = list.iterator();
			while(it.hasNext()) {
				Task mytask = it.next();
				if(Math.abs(today.getTime()-mytask.getDueDate().getTime().getTime())<ecart) {
					ecart=Math.abs(today.getTime()-mytask.getDueDate().getTime().getTime());
					plusproche = mytask;
				}
			}
		System.out.println(plusproche.toString());
		
		//pareil avec une lambda
			Optional<Task> plusproche2 = list.stream().min((t1,t2)->{
			long dif1 = Math.abs(today.getTime()-t1.getDueDate().getTime().getTime());
			long dif2 = Math.abs(today.getTime()-t2.getDueDate().getTime().getTime());
			return Long.compare(dif1,dif2);
		});
		System.out.println(plusproche2.toString());
		
		System.out.println("--- Chaîne constituée de tous les labels ---");
		List<String> listlabels = new ArrayList<String>();
		list.forEach(task->listlabels.add(task.getLabel()));
		Optional<String> labels = listlabels.stream().reduce((str1,str2)->str1 + " - " + str2);
		System.out.println(labels);
		
		System.out.println("--- Total durées estimées des tâches non terminées ---");
		Function<Task,Integer> getTempsRestant = task->task.getEstimatedTime();
		List<Integer> durees = new ArrayList<Integer>();
		durees = taches.stream().map(getTempsRestant).collect(Collectors.toList());
		Optional<Integer> duree = durees.stream().reduce((duree1,duree2)->duree1+duree2);
		System.out.println(duree);
		
		System.out.println("--- Taches satisfaisant le prédicat //taskWhile ---");
		List<Task> listExemple = tache1.taskWhile(list, pred);
		listExemple.forEach(task->System.out.println(task.toString()));
		
		System.out.println("--- Strings satisfaisant le prédicat //takeWhile ---");
		List<String> liste2 = new ArrayList<String>();
		liste2.add("poire"); liste2.add("pomme"); liste2.add("banane");
		Predicate<String> pred4 = str->str.startsWith("p");
		List<String> listExemple2 = tache1.takeWhile(liste2, pred4);
		listExemple2.forEach(str->System.out.println(str));
		
		System.out.println("--- Test //tasksToMap ---");
		Function<Task,Integer> functionkey = task->task.getId();
		Function<Task,String> functionvalue = task->task.getLabel();
		Map<Integer,String> mymap = new HashMap<Integer,String>();
		mymap = Task.tasksToMap(list, functionkey, functionvalue);
		Set<Integer> keys = mymap.keySet();
		for(Integer i : keys) {
			System.out.println("key : " + i + " - value : "+ mymap.get(i));
		}
		
	}
}
