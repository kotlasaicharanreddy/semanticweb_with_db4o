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
	                    getTuplesByPredicate(name);
	                    break;
	            case 3: System.out.println("Enter Subject"); 
	                    subject=buff.readLine();
	                    predicate=buff.readLine();
	                    object=buff.readLine();
	                    printTuples(subject,predicate,object);
	                    break;
	                    
	            default: System.out.println("You entered the wrong choice");
	            System.out.println("Exiting....");        
	            }
	            choice=3;
	            break;
	   case 2: System.out.println("1. Inverse Properties");
	           System.out.println("2. Transitive Properties");
	           System.out.println("3. Chains");
	           System.out.println("4. Symmetric Properties");
	           System.out.println("5. Disjoint Properties");
	           yachoice=sc.nextInt();
	           switch(yachoice)
	           {
	           case 1: System.out.println("*********Inverse Properties*****************");
	                   System.out.println("Choose one");
	                   System.out.println("1.teaches");
	                   System.out.println("2.isEnrolledBy");
	                   System.out.println("3.coordinates");
	                   System.out.println("4.published");
	                   System.out.println("5.isTaughtBy");
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
                       	  	   inverse(subject,"published",object);
                       	  	   break; 
	                   case 5: System.out.println("Enter Subject");
	                   			subject=buff.readLine();
	                   			System.out.println("Enter Object");
	                   			object=buff.readLine();
	                   			inverse(subject,"isTaughtBy",object);
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
	                   System.out.println("2. isCollegueOf");
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
                					 symmetric(subject,"isCollegueOf",object);
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
	query.constrain(c);
	ObjectSet<?> objset = query.execute();
	for(Object o :objset)
	{
		System.out.println(o);
	}
	database.close();
}
public static void inverse(String subject, String predicate, String object) throws ClassNotFoundException, InstantiationException, IllegalAccessException
{
	String defaultPrefix = "http://www.semanticweb.org/deepakd/ontologies/2019/3/university#";
	int flag= isTuplePresent(subject,predicate,object);
	String inv = invmap.get(predicate);
	int outerflag=isTuplePresent(object,inv,subject);
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
	     }
	}
	
}
public static void printTuples(String subject, String predicate, String object)
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
public static int isTuplePresent(String subject, String predicate, String object)
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
public static void symmetric(String subject, String predicate, String object) throws IOException
{
	String defaultPrefix = "http://www.semanticweb.org/deepakd/ontologies/2019/3/university#";
	int flag=  isTuplePresent(subject,predicate,object);
	int symflag=isTuplePresent(object,predicate,subject);
	String pred=predicate;
	predicate=":"+predicate;
	if(flag==0)
		System.out.println("Enter valid Triple");
	else
	{
		if(symflag==1)
			{
			System.out.println("Symmetric Triple Already Present");
		    System.out.println("Want to print? (y/n)");
			BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
            if(buff.readLine()=="y")
            {
            	printTuples(object,predicate,subject);
            }
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
		}
		    
	}
}
public static void addtransitiveTriple(String Subject,String Predicate,String Object)
{
	Predicate = ":"+Predicate;
	//first check whether triple exists
	String defaultPrefix = "http://www.semanticweb.org/deepakd/ontologies/2019/3/university#";
	ObjectContainer database = Db4o.openFile("resources/Triples");
	
	Query exists = database.query();
	exists.constrain(Triple.class);
	exists.descend("subject").descend("universityName").constrain(Subject).and(exists.descend("predicate").descend("propertyname").constrain(Predicate)).and(exists.descend("object").descend("regionName").constrain(Object)).or(exists.descend("subject").descend("regionName").constrain(Subject).and(exists.descend("predicate").descend("propertyname").constrain(Predicate).and(exists.descend("object").descend("regionName").constrain(Object))));
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
		query1.descend("object").descend("regionName").constrain(Object);
		ObjectSet<Triple> objset1 = query1.execute();
		
		for(Triple tr : objset1)
		{
			//System.out.println(tr.getObject().toString());
			String object_name =((Region) tr.getSubject()).getname();
			Query query2 = database.query();
			query2.constrain(Triple.class);
			query2.descend("object").descend("regionName").constrain(object_name);
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
	}/*
public static void isClassmateOf(String subject, String predicate, String object)
{	
	
	String defaultPrefix = "http://www.semanticweb.org/deepakd/ontologies/2019/3/university#";
	ObjectContainer database = Db4o.openFile("resources/Triples");
	
	Query exists = database.query();
	exists.constrain(Triple.class);
	exists.descend("subject").descend("name").constrain(object).and(exists.descend("predicate").descend("propertyname").constrain(predicate)).and(exists.descend("object").descend("name").constrain(subject));
	ObjectSet<Triple> obj_set = exists.execute();
	
	if(!(obj_set.isEmpty())) {
		System.out.println("Entry already exists");
		for(Triple tr: obj_set) {
			System.out.println(tr);
		}
	}
	else {
		Query query = database.query();
		query.constrain(Triple.class);
		query.descend("predicate").descend("propertyname").constrain(predicate);
		ObjectSet<Triple> objset = query.execute();
		int flag = 0;
		for (Triple tr : objset) {
			Student stud1 = (Student) tr.getSubject();
			Student stud2 = (Student) tr.getObject();
			if (stud1.getName().equals(subject) && stud2.getName().equals(object)) {
				flag = 1;
				ObjectProperty isClassmateOf = new ObjectProperty(defaultPrefix + "isClassmateOf",
						":isClassmateOf");
				Triple<Student, ObjectProperty, Student> triple = new Triple<Student, ObjectProperty, Student>(
						stud2, isClassmateOf, stud1);
				database.store(triple);
				System.out.println(stud2.getName() + "  " + isClassmateOf.getPropertyname() + "  " + stud1.getName()
						+ "is successfully added in DB");
			}
		}
		if (flag == 0)
			System.out.println("No such Entry");
	}
	database.close();
}

public static void colleagueOf(String Subject, String predicate, String Object)
{
	String defaultPrefix = "http://www.semanticweb.org/deepakd/ontologies/2019/3/university#";
	ObjectContainer database = Db4o.openFile("resources/Triples");
	
	Query exists = database.query();
	exists.constrain(Triple.class);
	exists.descend("subject").descend("name").constrain(Object).and(exists.descend("predicate").descend("propertyname").constrain(predicate)).and(exists.descend("object").descend("name").constrain(Subject));
	ObjectSet<Triple> obj_set = exists.execute();
	
	if(!(obj_set.isEmpty())) {
		//System.out.println("Entry already exists:");
		for(Triple tr: obj_set) {
			System.out.println("Entry already exists:"+tr);
		}
	}
	
	else {
		Query query = database.query();
		query.constrain(Triple.class);
		query.descend("predicate").descend("propertyname").constrain(predicate);
		ObjectSet<Triple> objset = query.execute();
		int flag = 0;
		for (Triple tr : objset) {
			Professor prof1 = (Professor) tr.getSubject();
			Professor prof2 = (Professor) tr.getObject();
			if (prof1.getName().equals(Subject) && prof2.getName().equals(Object)) {
				flag = 1;
				ObjectProperty colleagueOf = new ObjectProperty(defaultPrefix + "colleagueOf", ":colleagueOf");
				Triple<Professor, ObjectProperty, Professor> triple = new Triple<Professor, ObjectProperty, Professor>(
						prof2, colleagueOf, prof1);
				database.store(triple);
				System.out.println(prof2.getName() + "  " + colleagueOf.getPropertyname() + "  " + prof1.getName()
						+ "is successfully added in DB");
			}
		}
		if (flag == 0)
			System.out.println("No such Entry");
	}
	database.close();
}*/
}
