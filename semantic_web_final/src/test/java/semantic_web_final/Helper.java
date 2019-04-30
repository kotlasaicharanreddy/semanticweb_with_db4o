package semantic_web_final;
import java.util.List;
import java.lang.reflect.*;
import java.util.Scanner;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;
import com.db4o.query.Query;
class Helper {
   public static void front() throws ClassNotFoundException
   {
	   int choice,yachoice;
	   String name;
	   Scanner sc = new Scanner(System.in);
	   do {
	   System.out.println("******************************************WELCOME*********************************************");
	   System.out.println("1. Retrieve existing data from db4o");
	   System.out.println("2. Inferencing Engine");
	   choice=sc.nextInt();
	   switch(choice)
	   {
	   case 1:  System.out.println("1 Retrieve all Instance of a particular class");
	            System.out.println("2.Retrieve all Triples with a specific object property");
	            yachoice=sc.nextInt();
	            switch(yachoice)
	            {
	            case 1: System.out.println("Enter the name of the class");
	                    name=sc.next();
	                    getInstancesFromClass(name);
	                    break;
	            case 2: System.out.println("Enter the name of the Object Property");
	                    name=sc.next();
	                    getTuplesByPredicate(name);
	                    break;
	            default: System.out.println("You entered the wrong choice");
	            System.out.println("Exiting....");        
	            }
	            choice=3;
	            break;
	   case 2: System.out.println("1.Inverse Properties");
	           System.out.println("2. Transitive Properties");
	           System.out.println("3. Chains");
	           System.out.println("4. Symmetric Properties");
	           System.out.println("5. Disjoint Properties");
	           yachoice=sc.nextInt();
	           switch(yachoice)
	           {
	           case 1: break;
	           case 2: break;
	           case 3: break;
	           case 4: break;
	           case 5: break;
	           default: System.out.println("You entered the wrong choice");
	                    System.out.println("Exiting....");
	           }
	           choice=3;
	           
	           
	           
	           break;
	           
	   default:System.out.println("You entered the wrong choice");
       System.out.println("Exiting....");
	   }
	   
   } while(choice!=1||choice!=2);
}

   public static void getTuplesByPredicate(String predicate)
	{
	   int flag=0;
	    predicate = ":"+predicate;
		String defaultPrefix = "http://www.semanticweb.org/deepakd/ontologies/2019/3/university#";
		ObjectContainer database = Db4o.openFile("resources/Triples");
		Query query = database.query();
		query.constrain(Triple.class);
		query.descend("predicate").descend("propertyname").constrain(predicate);
		ObjectSet<Triple> objset= query.execute();
		for(Triple tr: objset)
		{
			System.out.println(tr);
			flag=1;
		}
		if(flag==0)
		{
			System.out.println("No entries found for property: "+predicate);
			System.out.println("Existing....");
		}
		database.close();
	}
public static void getInstancesFromClass(String name) throws ClassNotFoundException {
	String defaultPrefix = "http://www.semanticweb.org/deepakd/ontologies/2019/3/university#";
	ObjectContainer database = Db4o.openFile("resources/Triples");
	Query query = database.query();
	Class c = Class.forName("semantic_web_final."+name);
	 //String s = c.getSimpleName();
  /*  System.out.println(c);
	System.out.println(c.getClass());
	System.out.println(c.getClass().getName());*/
	query.constrain(c);
	ObjectSet<?> objset = query.execute();
	for(Object o :objset)
	{
		System.out.println(o);
	}
	database.close();
	
}
}
