package semantic_web_final;
import java.util.HashMap;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.*;
import java.util.Scanner;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;
import com.db4o.query.Query;
class Helper {
   public static void front() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException
   {
	   int choice,yachoice,yayachoice;
	   String name,subject,predicate,object;
	   Scanner sc = new Scanner(System.in);
	   BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
	   do {
	   System.out.println("********************************************WELCOME***********************************************");
	   System.out.println("1. Retrieve existing data from db4o");
	   System.out.println("2. Inferencing Engine");
	   choice=sc.nextInt();
	   switch(choice)
	   {
	   case 1:  System.out.println("1 Retrieve all Instance of a particular class");
	            System.out.println("2.Retrieve all Triples with a specific object property");
	            System.out.println("3.Check whether a particular Triple is present");
	            yachoice=sc.nextInt();
	            switch(yachoice)
	            {
	            case 1: System.out.println("Enter the name of the class");
	                    name=buff.readLine();
	                    getInstancesFromClass(name);
	                    break;
	            case 2: System.out.println("Enter the name of the Object Property");
	                    name=buff.readLine();
	                    getTriplesByPredicate(name);
	                    break;
	            case 3: System.out.println("Enter Subject"); 
	                    subject=buff.readLine();
	                    System.out.println("Enter Predicate");
	                    predicate=buff.readLine();
	                    System.out.println("Enter Object");
	                    object=buff.readLine();
	                    printTriples(subject,predicate,object);
	                    break;
	                    
	            default: System.out.println("You entered the wrong choice");
	            System.out.println("Exiting....");        
	            }
	            choice=3;
	            break;
	   case 2: System.out.println("1. Inverse Properties");
	           System.out.println("2. Transitive Properties");
	           System.out.println("3. Inherite");
	           System.out.println("4. Symmetric Properties");
	           yachoice=sc.nextInt();
	           switch(yachoice)
	           {
	           case 1: System.out.println("*********Inverse Properties*****************");
	                   System.out.println("Choose one");
	                   System.out.println("1.teaches");
	                   System.out.println("2.isEnrolledBy");
	                   System.out.println("3.coordinates");
	                   System.out.println("4.publishes");
	                   System.out.println("5.isTaughtBy");
	                   System.out.println("6.isPublishedBy");
	                   System.out.println("7.isCoordinatedBy");
	                   System.out.println("8.coordinates");
	                   yayachoice=sc.nextInt();
	                   switch(yayachoice)
	                   {
	                   case 1: System.out.println("Enter Subject");
	                           subject=buff.readLine();
	                           System.out.println("Enter Object");
	                           object=buff.readLine();
	                           inverse(subject,"teaches",object);
	                           break;
	                   case 2: System.out.println("Enter Subject");
                       		   subject=buff.readLine();
                       		   System.out.println("Enter Object");
                       		   object=buff.readLine();
                       		   inverse(subject,"isEnrolledBy",object);
                       		   break;
	                   case 3: System.out.println("Enter Subject");
                       		   subject=buff.readLine();
                       		   System.out.println("Enter Object");
                       		   object=buff.readLine();
                       		   inverse(subject,"coordinates",object);
                       		   break;
	                   case 4: System.out.println("Enter Subject");
                       	  	   subject=buff.readLine();
                       	  	   System.out.println("Enter Object");
                       	  	   object=buff.readLine();
                       	  	   inverse(subject,"publishes",object);
                       	  	   break; 
	                   case 5: System.out.println("Enter Subject");
	                   			subject=buff.readLine();
	                   			System.out.println("Enter Object");
	                   			object=buff.readLine();
	                   			inverse(subject,"isTaughtBy",object);
	                   			break;
	                    	  	 
	                   case 6: System.out.println("Enter Subject");
	                   			subject=buff.readLine();
	                   			System.out.println("Enter Object");
	                   			object=buff.readLine();
	                   			inverse(subject,"isPublishedBy",object);
	                   			break;
	                    	  	   
	                   case 7: System.out.println("Enter Subject");
	                   			subject=buff.readLine();
	                   			System.out.println("Enter Object");
	                   			object=buff.readLine();
	                   			inverse(subject,"isCoordinatedBy",object);
	                   			break;
	                   case 8: System.out.println("Enter Subject");
	                   			subject=buff.readLine();
	                   			System.out.println("Enter Object");
	                   			object=buff.readLine();
	                   			inverse(subject,"coordinates",object);
	                   			break;		
	                           
	                   }
	        	      break;
	           case 2:  System.out.println("************Transitive Properties*****************");
                        System.out.println("1.isLocatedin");
                        yayachoice=sc.nextInt();
                        switch(yayachoice)
                        {
                        case 1: System.out.println("Enter Subject");
                        subject=buff.readLine();
                        System.out.println("Enter Object");
                        object=buff.readLine();
                        addtransitiveTriple(subject,"isLocatedIn",object); 
                        }
                       break;
	           case 3: System.out.println("************Chains*****************");
	        	        break;
	           case 4: System.out.println("**************Symmetric Properties*****************");
	                   System.out.println("1. isClassmateOf");
	                   System.out.println("2. isColleagueOf");
	                   yayachoice=sc.nextInt();
	                   switch(yayachoice)
	                   {	    
                        	case 1: System.out.println("Enter Subject");
                        			subject=buff.readLine();
                        			System.out.println("Enter Object");
                        			object=buff.readLine();
                        			symmetric(subject,"isClassmateOf",object);
                        			break;
                        	case 2:  System.out.println("Enter Subject");
                					 subject=buff.readLine();
                					 System.out.println("Enter Object");
                					 object=buff.readLine();
                					 symmetric(subject,"isColleagueOf",object);
                					 break;			
	                   }
	        	   break;
	           case 5:System.out.println("**************Disjoint Properties*****************");
	        	   break;
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

	 static final HashMap<String,String> invmap = new HashMap<>(); 
	static {
		    invmap.put("teaches","isTaughtBy");
	        invmap.put("isTaughtBy","teaches");
	        invmap.put("isEnrolledBy", "enrolls");
	        invmap.put("enrolls", "isEnrolledBy");
	        invmap.put("publishes","isPublishedBy");
	        invmap.put("isPublishedBy","publishes");
	        invmap.put("coordinates","isCoordinatedBy");
	        invmap.put("isCoordinatedBy","coordinates");
	}
	//function to get all the Triples with a specific predicate
   public static void getTriplesByPredicate(String predicate)
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
   //function to get all the instances of the specified class
public static void getInstancesFromClass(String name) throws ClassNotFoundException {
	String defaultPrefix = "http://www.semanticweb.org/deepakd/ontologies/2019/3/university#";
	ObjectContainer database = Db4o.openFile("resources/Triples");
	Query query = database.query();
	Class c = Class.forName("semantic_web_final."+name);
	query.constrain(c);
	ObjectSet<?> objset = query.execute();
	for(Object o :objset)
	{
		System.out.println(o);
	}
	database.close();
}
//GENERIC function to deal with inverse properties
public static void inverse(String subject, String predicate, String object) throws ClassNotFoundException, InstantiationException, IllegalAccessException
{
	String defaultPrefix = "http://www.semanticweb.org/deepakd/ontologies/2019/3/university#";
	int flag= isTriplePresent(subject,predicate,object);
	String inv = invmap.get(predicate);
	int outerflag=isTriplePresent(object,inv,subject);
	predicate= ":"+predicate;
	if(flag==0)
		System.out.println("Enter Valid Triple!");
	else
	{
	     if(outerflag==1)	
	    	 System.out.println("Triple Already Present");
	     else
	     { 
	    	    ObjectContainer database = Db4o.openFile("resources/Triples");
	    	    Query query = database.query();
	    		query.constrain(Triple.class);
	    		query.descend("predicate").descend("propertyname").constrain(predicate).and(query.descend("subject").descend("name").constrain(subject)).and(query.descend("object").descend("name").constrain(object));
	    		ObjectSet<Triple> objset= query.execute();
	    		for(Triple t:objset)
	    		{
	    		    Object o1= t.getSubject();
	    		    Object o2= t.getObject();
	    		    ObjectProperty invprop = new ObjectProperty(defaultPrefix+inv,":"+inv);
	    		    Triple<Object,ObjectProperty,Object> triple = new Triple<Object,ObjectProperty,Object>(o2,invprop,o1);
	    		    database.store(triple);
	    		}
	    		database.close();
	    		System.out.println("Triple Added");
	     }
	}
	
}
//function to print a specific triple(if present)
public static void printTriples(String subject, String predicate, String object)
{
	ObjectContainer database = Db4o.openFile("resources/Triples");
	predicate=":"+predicate;
	String defaultPrefix = "http://www.semanticweb.org/deepakd/ontologies/2019/3/university#";
	Query query = database.query();
	query.constrain(Triple.class);
	query.descend("predicate").descend("propertyname").constrain(predicate).and(query.descend("subject").descend("name").constrain(subject)).and(query.descend("object").descend("name").constrain(object));
	ObjectSet<Triple> objset= query.execute();
	if (objset.size()==0)
		System.out.println("Triple not found");
	else
	{
		System.out.println("Triple found");
		System.out.println("Printing triple...");
		for(Triple tr:objset)
			System.out.println(tr);
	}
	database.close();
}
//function to check whether a specific triple is present or not
public static int isTriplePresent(String subject, String predicate, String object)
{
	ObjectContainer database = Db4o.openFile("resources/Triples");
	predicate=":"+predicate;
	String defaultPrefix = "http://www.semanticweb.org/deepakd/ontologies/2019/3/university#";
	Query query = database.query();
	query.constrain(Triple.class);
	query.descend("predicate").descend("propertyname").constrain(predicate).and(query.descend("subject").descend("name").constrain(subject)).and(query.descend("object").descend("name").constrain(object));
	ObjectSet<Triple> objset= query.execute();
	if (objset.size()==0)
	{
		database.close();
		return 0;
	}
	else
		{
		database.close();
		return 1;
		}
	
}
//GENERIC function to deal with Symmetric Properties
public static void symmetric(String subject, String predicate, String object) throws IOException
{
	String defaultPrefix = "http://www.semanticweb.org/deepakd/ontologies/2019/3/university#";
	int flag=  isTriplePresent(subject,predicate,object);
	int symflag=isTriplePresent(object,predicate,subject);
	String pred=predicate;
	predicate=":"+predicate;
	if(flag==0)
		System.out.println("Enter valid Triple");
	else
	{
		if(symflag==1)
			{
			System.out.println("Symmetric Triple Already Present");
            System.out.println("Printing Triple");
            printTriples(object,pred,subject);      
			}
		else
		{
			
			ObjectContainer database = Db4o.openFile("resources/Triples");
    	    Query query = database.query();
    		query.constrain(Triple.class);
    		query.descend("predicate").descend("propertyname").constrain(predicate).and(query.descend("subject").descend("name").constrain(subject)).and(query.descend("object").descend("name").constrain(object));
    		ObjectSet<Triple> objset= query.execute();
    		for(Triple t:objset)
    		{
    		    Object o1= t.getSubject();
    		    Object o2= t.getObject();
    		    ObjectProperty prop = new ObjectProperty(defaultPrefix+pred,predicate);
    		    Triple<Object,ObjectProperty,Object> triple = new Triple<Object,ObjectProperty,Object>(o2,prop,o1);
    		    database.store(triple);
    		}
    		database.close();
    		System.out.println("Triple Added");
		}
		     
		    
	}
}
//To deal with Transitive Property
public static void addtransitiveTriple(String Subject,String Predicate,String Object)
{
	Predicate = ":"+Predicate;
	//first check whether triple exists
	String defaultPrefix = "http://www.semanticweb.org/deepakd/ontologies/2019/3/university#";
	ObjectContainer database = Db4o.openFile("resources/Triples");
	
	Query exists = database.query();
	exists.constrain(Triple.class);
	exists.descend("subject").descend("name").constrain(Subject).and(exists.descend("predicate").descend("propertyname").constrain(Predicate)).and(exists.descend("object").descend("name").constrain(Object)).or(exists.descend("subject").descend("name").constrain(Subject).and(exists.descend("predicate").descend("propertyname").constrain(Predicate).and(exists.descend("object").descend("name").constrain(Object))));
	ObjectSet<Triple> object_set = exists.execute();
	
	if(!(object_set.isEmpty()))
	{
		System.out.println("Entry already exists");
		for(Triple tr : object_set)
		{
			System.out.println(tr);
			//
		}
	}
			
	else
	{
		int flag = 0;
		Query query1 = database.query();
		query1.constrain(Triple.class);
		query1.descend("object").descend("name").constrain(Object);
		ObjectSet<Triple> objset1 = query1.execute();
		
		for(Triple tr : objset1)
		{
			//System.out.println(tr.getObject().toString());
			String object_name =((Region) tr.getSubject()).getname();
			Query query2 = database.query();
			query2.constrain(Triple.class);
			query2.descend("object").descend("name").constrain(object_name);
			ObjectSet<Triple> obj_set = query2.execute();
			for(Triple triple : obj_set)
			{
				String subject_name = (triple.getSubject() instanceof University) ? (((University)triple.getSubject()).getname()) : (((Region)triple.getSubject()).getname());
				
				//System.out.println(triple);
				if(Subject.equals(subject_name))
				{
					flag=1;
					System.out.println("Given Triple can be added.");
					Object new_subject = triple.getSubject();
					ObjectProperty new_predicate = new ObjectProperty(defaultPrefix+"isLocatedIn",":isLocatedIn");
					Object new_object = tr.getObject();
					Triple<Object,ObjectProperty,Object> new_triple = new Triple<Object,ObjectProperty,Object>(new_subject,new_predicate,new_object);
					System.out.println(new_triple);
					database.store(new_triple);
					break;
				}
			}
			if(flag == 1)
				break;
			else
				continue;
		}
		if(flag == 0)
			System.out.println("Such entry cannot be added");
	
		
		} 
	database.close();
	}
}