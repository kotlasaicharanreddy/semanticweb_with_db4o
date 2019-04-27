package semantic_web_final;

import java.io.IOException;
import java.util.Scanner;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

import com.db4o.Db4o;
import com.db4o.query.Query;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

public class Ontology_test {

	public static void main(String[] args) throws OWLOntologyCreationException, IOException {
		// TODO Auto-generated method stub
		OwlApi_Class owl = new OwlApi_Class("resources/University.owl");
		owl.get_Classes();
		
		Scanner sc =new Scanner(System.in);
;		addtransitiveTriple("IIIT Banglore", ":"+"isLocatedIn", "Karnataka");
	
		
	}
	public static void isTaughtBy(String prof_name, String predicate, String course)
	{
		String defaultPrefix = "http://www.semanticweb.org/deepakd/ontologies/2019/3/university#";
		ObjectContainer database = Db4o.openFile("resources/Triples");
		Query query = database.query();
		query.constrain(Triple.class);
		query.descend("predicate").descend("propertyname").constrain(predicate);
		ObjectSet<Triple> objset= query.execute();
		int flag = 0;
		for(Triple tr : objset)
		{
			
			Professor prof = (Professor) tr.getSubject();
			Course cour = (Course) tr.getObject();
			if(prof.getName().equals(prof_name) && cour.getCourseName().equals(course))
			{	flag = 1;
				ObjectProperty isTaughtBy = new ObjectProperty(defaultPrefix+"isTaughtBy",":isTaughtBy");
				Triple<Course,ObjectProperty,Professor> triple = new Triple<Course,ObjectProperty,Professor>(cour,isTaughtBy,prof);
				System.out.println(triple);
				database.store(triple);
			}
			
		}
		if(flag == 0)
			System.out.println("No such Entry");
		database.close();
	}
	
	public static void addtransitiveTriple(String Subject,String Predicate,String Object)
	{
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
				String object_name =((Region) tr.getSubject()).getRegionName();
				Query query2 = database.query();
				query2.constrain(Triple.class);
				query2.descend("object").descend("regionName").constrain(object_name);
				ObjectSet<Triple> obj_set = query2.execute();
				for(Triple triple : obj_set)
				{
					String subject_name = (triple.getSubject() instanceof University) ? (((University)triple.getSubject()).getUniversityName()) : (((Region)triple.getSubject()).getRegionName());
					
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
	

