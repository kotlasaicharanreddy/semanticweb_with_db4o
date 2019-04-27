package semantic_web_final;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.HermiT.ReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.ConsoleProgressMonitor;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.search.EntitySearcher;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;

import uk.ac.manchester.cs.owl.owlapi.OWLDataPropertyImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectPropertyImpl;

public class OwlApi_Class {
	
	TripleStore triplestore;
	OWLOntologyManager manager;
	File file;
	ArrayList<Professor> professorlist;
	ArrayList<ObjectProperty> predicatelist;
	ArrayList<Course> courselist;
	ArrayList<Student> studentlist;
	ArrayList<DegreeProgram> degreelist;
	ArrayList<Region> regionlist;
	ArrayList<Class> classlist;
	ObjectContainer database;
	public OwlApi_Class(String filepath)
	{
		manager = OWLManager.createOWLOntologyManager();
		file = new File(filepath);
		System.out.println("Created OWL Ontology Manager and file object");
		triplestore = new TripleStore();
		professorlist = new ArrayList<Professor>();
		courselist = new ArrayList<Course>();
		studentlist = new ArrayList<Student>();
		degreelist = new ArrayList<DegreeProgram>();
		regionlist = new ArrayList<Region>();
		classlist = new ArrayList<Class>();
		database = Db4o.openFile("resources/Triples");
	}
	
	public void get_Classes() throws OWLOntologyCreationException, IOException
	{
		//Load ontology into memory
		OWLOntology localUni = manager.loadOntologyFromOntologyDocument(file);
		System.out.println("Loaded ontology: "+ localUni.getOntologyID());
		IRI location = manager.getOntologyDocumentIRI(localUni);
		System.out.println("\tfrom:"+ location);
		
		//get and configure HermiT reasoner
		OWLReasonerFactory reasonerFactory = new ReasonerFactory();
		ConsoleProgressMonitor progressMonitor = new ConsoleProgressMonitor();
		OWLReasonerConfiguration config = new SimpleConfiguration(progressMonitor);
		
		//create the reasoner instance, classify and compute inferences
		OWLReasoner reasoner = reasonerFactory.createReasoner(localUni, config);
		reasoner.precomputeInferences(InferenceType.values());
		
		DefaultPrefixManager pm = new DefaultPrefixManager(null,null,"http://www.semanticweb.org/deepakd/ontologies/2019/3/university#");
		
		System.out.println("Setup phase is over");
		
		//get all the classes from the ontology
		OWLDataFactory fac = manager.getOWLDataFactory();
				
		OWLClass Professors = fac.getOWLClass(IRI.create(pm.getDefaultPrefix(), "Teacher"));
		//get the instances of the Professors OWL Class
		NodeSet<OWLNamedIndividual> individualNodeSet = reasoner.getInstances(Professors, false);
		//Store the obtained INDIVIDUALS in Set which is iterable
		Set<OWLNamedIndividual> individualSet = individualNodeSet.getFlattened();
		
		for (OWLNamedIndividual professor : individualSet)
		{
			//get professor properties and store them in professor object
			//IRI of the instance
			String namespace = professor.toStringID();
			//name of the professor
			OWLDataProperty person_name = new OWLDataPropertyImpl(IRI.create(pm.getDefaultPrefix(), "personName"));
			Collection<OWLLiteral> name = EntitySearcher.getDataPropertyValues(professor, person_name, localUni);
			Object Name[] = name.toArray();
			String profname = Name[0].toString();
			String prof_name = profname.substring(1, profname.length()-1);
			//email of the professor
			OWLDataProperty person_email = new OWLDataPropertyImpl(IRI.create(pm.getDefaultPrefix(), "email"));
			Collection<OWLLiteral> email = EntitySearcher.getDataPropertyValues(professor, person_email, localUni);
			Object Email[] = email.toArray();
			String profemail = Email[0].toString();
			String prof_email = profemail.substring(1, profemail.length()-1);
			//gender of the professor
			OWLDataProperty person_gender = new OWLDataPropertyImpl(IRI.create(pm.getDefaultPrefix(), "personGender"));
			Collection<OWLLiteral> gender = EntitySearcher.getDataPropertyValues(professor, person_gender, localUni);
			Object Gender[] = gender.toArray();
			String profgender = Gender[0].toString();
			String prof_Gender = profgender.substring(1, profgender.length()-1);
			
			OWLDataProperty person_id = new OWLDataPropertyImpl(IRI.create(pm.getDefaultPrefix(), "personId"));
			Collection<OWLLiteral> id = EntitySearcher.getDataPropertyValues(professor, person_id, localUni);
			Object ID[] = id.toArray();
			String profid = ID[0].toString();
			String prof_id = profid.substring(1, profid.length()-1);
			
			Professor prof = new Professor(namespace, prof_name, prof_email, prof_Gender, prof_id);
			
			professorlist.add(prof);
			//System.out.println(prof);
			//Professor teaches course
			OWLObjectPropertyImpl object_prop = new OWLObjectPropertyImpl(IRI.create(pm.getDefaultPrefix(),"teaches"));
			
			Set<OWLIndividual> courses = new HashSet<>(EntitySearcher.getObjectPropertyValues(professor, object_prop, localUni));
			for(OWLIndividual course : courses)
			{
				ObjectProperty teaches = new ObjectProperty(object_prop.toStringID(), pm.getShortForm(object_prop));
				OWLDataProperty course_credits = new OWLDataPropertyImpl(IRI.create(pm.getDefaultPrefix(), "courseCredits"));
				OWLDataProperty course_hours = new OWLDataPropertyImpl(IRI.create(pm.getDefaultPrefix(), "courseHours"));
				OWLDataProperty course_name = new OWLDataPropertyImpl(IRI.create(pm.getDefaultPrefix(), "courseName"));
				
				Collection<OWLLiteral> credits = EntitySearcher.getDataPropertyValues(course, course_credits, localUni);
				Collection<OWLLiteral> chours = EntitySearcher.getDataPropertyValues(course, course_hours, localUni);
				Collection<OWLLiteral> cname = EntitySearcher.getDataPropertyValues(course, course_name, localUni);
				
				Object CName[] = cname.toArray();
				String coursename = CName[0].toString();
				String cn = coursename.substring(1, coursename.length()-1);
				
				Object CID[] = chours.toArray();
				String coursehours = CID[0].toString();
				//String ch = coursehours.substring(1, 3);
				
				Object Credits[] = credits.toArray();
				//System.out.println(Credits[0]);
				String coursecredits = Credits[0].toString();
				//String sub = str.substring(1, 2);
				
				//for each Course now create a Course object
				Course cour = new Course(coursecredits, coursehours, cn, course.toStringID());
				
				courselist.add(cour);
				//System.out.println("\tteaches course:"+cour);
				Triple<Professor, ObjectProperty, Course> tr1 = new Triple<Professor, ObjectProperty, Course>(prof, teaches, cour);
				database.store(tr1);
				triplestore.addTriple(tr1);
				//get enrolled students (isEnrolledBy object property)
				OWLObjectPropertyImpl object_prop2 = new OWLObjectPropertyImpl(IRI.create(pm.getDefaultPrefix(),"isEnrolledBy"));
				//Set<OWLIndividual> isEnrolledBy =new HashSet<>(EntitySearcher.getObjectPropertyValues(course, op2, localUni));
				Set<OWLNamedIndividual> students = reasoner.getObjectPropertyValues((OWLNamedIndividual) course, object_prop2).getFlattened();
				for(OWLIndividual student : students)
				{
					ObjectProperty isEnrolledBy = new ObjectProperty(object_prop2.toStringID(),pm.getShortForm(object_prop2));
					//System.out.println("\t\t\tis Enrolled by: "+ pm.getShortForm((OWLEntity)student));
					String stu_namespace = student.toStringID();
					//name of the professor
					//OWLDataProperty person_name = new OWLDataPropertyImpl(IRI.create(pm.getDefaultPrefix(), "personName"));
					Collection<OWLLiteral> student_name = EntitySearcher.getDataPropertyValues(student, person_name, localUni);
					Object student_Name[] = student_name.toArray();
					String studentname = student_Name[0].toString();
					String stu_name = studentname.substring(1, studentname.length()-1);
					//email of the professor
					//OWLDataProperty student_email = new OWLDataPropertyImpl(IRI.create(pm.getDefaultPrefix(), "email"));
					Collection<OWLLiteral> student_email = EntitySearcher.getDataPropertyValues(student, person_email, localUni);
					Object student_Email[] = student_email.toArray();
					String studentemail = student_Email[0].toString();
					String stu_email = studentemail.substring(1, studentemail.length()-1);
					//gender of the professor
					//OWLDataProperty person_gender = new OWLDataPropertyImpl(IRI.create(pm.getDefaultPrefix(), "personGender"));
					Collection<OWLLiteral> student_gender = EntitySearcher.getDataPropertyValues(student, person_gender, localUni);
					Object student_Gender[] = student_gender.toArray();
					String studentgender = student_Gender[0].toString();
					String stu_gender = studentgender.substring(1, studentgender.length()-1);
					
					//OWLDataProperty person_id = new OWLDataPropertyImpl(IRI.create(pm.getDefaultPrefix(), "personId"));
					Collection<OWLLiteral> student_id = EntitySearcher.getDataPropertyValues(student, person_id, localUni);
					Object student_ID[] = student_id.toArray();
					String studentid = student_ID[0].toString();
					String stu_id = studentid.substring(1, studentid.length()-1);
					
					OWLDataProperty student_cgpa = new OWLDataPropertyImpl(IRI.create(pm.getDefaultPrefix(),"studentCGPA"));
					Collection<OWLLiteral> studentcgpa = EntitySearcher.getDataPropertyValues(student, student_cgpa, localUni);
					Object student_Cgpa[] = studentcgpa.toArray();
					String stu_cgpa = student_Cgpa[0].toString();
					
					
					Student stu = new Student(stu_namespace, stu_name, stu_email, stu_gender, stu_id, stu_cgpa);
				
					if(studentlist.isEmpty() == true || !(studentlist.contains(stu)))
					{
						studentlist.add(stu);
						Triple<Object, ObjectProperty, Object> tr2 = new Triple<Object, ObjectProperty, Object>(cour, isEnrolledBy, stu);
						database.store(tr2);
						triplestore.addTriple(tr2);
						OWLObjectPropertyImpl object_prop3 = new OWLObjectPropertyImpl(IRI.create(pm.getDefaultPrefix(),"enrollsDegree"));
						Set<OWLNamedIndividual> degrees = reasoner.getObjectPropertyValues((OWLNamedIndividual) student, object_prop3).getFlattened();
						for(OWLIndividual degree : degrees)
						{
							ObjectProperty enrollsDegree = new ObjectProperty(object_prop3.toStringID(), pm.getShortForm(object_prop3));
							
							String degreeIRI = degree.toStringID();
							
							OWLDataProperty degree_name = new OWLDataPropertyImpl(IRI.create(pm.getDefaultPrefix(), "degreeName"));
							Collection<OWLLiteral> degreename = EntitySearcher.getDataPropertyValues(degree, degree_name, localUni);
							Object degree_Name[] = degreename.toArray();
							String degreeName = degree_Name[0].toString();
							String deg_name = degreeName.substring(1, degreeName.length()-1);
							
							OWLDataProperty degree_duration = new OWLDataPropertyImpl(IRI.create(pm.getDefaultPrefix(), "degreeDuration"));
							Collection<OWLLiteral> degreeduration = EntitySearcher.getDataPropertyValues(degree, degree_duration, localUni);
							Object degree_Duration[] = degreeduration.toArray();
							String degreeDuration = degree_Duration[0].toString();
							//String deg_duration = degreeDuration.substring(1, degreeDuration.length()-1);
							
							DegreeProgram deg = new DegreeProgram(degreeIRI, degreeDuration, deg_name);
							
							if(degreelist.isEmpty() == true || !(degreelist.contains(deg)))
							{
								degreelist.add(deg);
								Triple<Student, ObjectProperty, DegreeProgram> tr3 = new Triple<Student, ObjectProperty, DegreeProgram>(stu, enrollsDegree, deg);
								database.store(tr3);
								triplestore.addTriple(tr3);
							}
							else 
								{
									int index = degreelist.indexOf(deg);
									DegreeProgram get_deg = degreelist.get(index);
									Triple<Student, ObjectProperty, DegreeProgram> tr3 = new Triple<Student, ObjectProperty, DegreeProgram>(stu, enrollsDegree, get_deg);
									database.store(tr3);
									triplestore.addTriple(tr3);
								}
						}
					}
					else 
						{
							int index = studentlist.indexOf(stu);
							Student get_stu = studentlist.get(index);
							Triple<Course, ObjectProperty, Student> tr2 = new Triple<Course, ObjectProperty, Student>(cour, isEnrolledBy, get_stu);
							database.store(tr2);
							triplestore.addTriple(tr2);
						}

				}
			}
			
		}
		
		OWLClass University = fac.getOWLClass(IRI.create(pm.getDefaultPrefix(), "University"));
		NodeSet<OWLNamedIndividual> individualset = reasoner.getInstances(University, false);
		Set<OWLNamedIndividual> universities = individualset.getFlattened();
		
		for(OWLNamedIndividual university : universities)
		{
			//System.out.println("University name:"+pm.getShortForm(university));
			String uni_namespace = university.toStringID();
			
			OWLDataProperty university_name = new OWLDataPropertyImpl(IRI.create(pm.getDefaultPrefix(), "universityName"));
			Collection<OWLLiteral> name = EntitySearcher.getDataPropertyValues(university, university_name, localUni);
			Object Name[] = name.toArray();
			String universityname = Name[0].toString();
			String uni_name = universityname.substring(1, universityname.length()-1);
			
			University uni = new University(uni_namespace, uni_name);
			OWLObjectPropertyImpl object_prop4 = new OWLObjectPropertyImpl(IRI.create(pm.getDefaultPrefix(),"offersDegree"));
			Set<OWLNamedIndividual> degrees = reasoner.getObjectPropertyValues((OWLNamedIndividual) university, object_prop4).getFlattened();
			
			for(OWLIndividual degree : degrees)
			{
				ObjectProperty offersDegree = new ObjectProperty(object_prop4.toStringID(), pm.getShortForm(object_prop4));
				String getdegree = degree.toStringID();
				
				OWLDataProperty degree_name = new OWLDataPropertyImpl(IRI.create(pm.getDefaultPrefix(), "degreeName"));
				Collection<OWLLiteral> degreename = EntitySearcher.getDataPropertyValues(degree, degree_name, localUni);
				Object degree_Name[] = degreename.toArray();
				String degreeName = degree_Name[0].toString();
				String deg_name = degreeName.substring(1, degreeName.length()-1);
				
				OWLDataProperty degree_duration = new OWLDataPropertyImpl(IRI.create(pm.getDefaultPrefix(), "degreeDuration"));
				Collection<OWLLiteral> degreeduration = EntitySearcher.getDataPropertyValues(degree, degree_duration, localUni);
				Object degree_Duration[] = degreeduration.toArray();
				String degreeDuration = degree_Duration[0].toString();
				//String deg_duration = degreeDuration.substring(1, degreeDuration.length()-1);
				
				DegreeProgram deg = new DegreeProgram(getdegree, degreeDuration, deg_name);
				
				int index = degreelist.indexOf(deg);
				DegreeProgram get_deg = degreelist.get(index);
				
				Triple<Object, ObjectProperty, Object> tr4 = new Triple<Object, ObjectProperty, Object>(uni, offersDegree, get_deg);
				database.store(tr4);
				triplestore.addTriple(tr4);
				
				OWLObjectPropertyImpl object_prop5 = new OWLObjectPropertyImpl(IRI.create(pm.getDefaultPrefix(),"offersCourse"));
				Set<OWLNamedIndividual> courses = reasoner.getObjectPropertyValues((OWLNamedIndividual) degree, object_prop5).getFlattened();
				
				for(OWLIndividual course : courses)
				{
					ObjectProperty offersCourse = new ObjectProperty(object_prop5.toStringID(), pm.getShortForm(object_prop5));
					
					OWLDataProperty course_credits = new OWLDataPropertyImpl(IRI.create(pm.getDefaultPrefix(), "courseCredits"));
					OWLDataProperty course_hours = new OWLDataPropertyImpl(IRI.create(pm.getDefaultPrefix(), "courseHours"));
					OWLDataProperty course_name = new OWLDataPropertyImpl(IRI.create(pm.getDefaultPrefix(), "courseName"));
					
					Collection<OWLLiteral> credits = EntitySearcher.getDataPropertyValues(course, course_credits, localUni);
					Collection<OWLLiteral> chours = EntitySearcher.getDataPropertyValues(course, course_hours, localUni);
					Collection<OWLLiteral> cname = EntitySearcher.getDataPropertyValues(course, course_name, localUni);
					
					Object CName[] = cname.toArray();
					String coursename = CName[0].toString();
					String cn = coursename.substring(1, coursename.length()-1);
					
					Object CID[] = chours.toArray();
					String coursehours = CID[0].toString();
					//String ch = coursehours.substring(1, 3);
					
					Object Credits[] = credits.toArray();
					//System.out.println(Credits[0]);
					String coursecredits = Credits[0].toString();
					//String sub = str.substring(1, 2);
					
					//for each Course now create a Course object
					Course cour = new Course(coursecredits, coursehours, cn, course.toStringID());
					
					int index1 = courselist.indexOf(cour);
					Course get_cour = courselist.get(index1);
					Triple<DegreeProgram, ObjectProperty, Course> tr5 = new Triple<DegreeProgram, ObjectProperty, Course>(get_deg, offersCourse, get_cour);
					database.store(tr5);
					triplestore.addTriple(tr5);
					
				}
				
			}
			OWLObjectPropertyImpl object_prop6 = new OWLObjectPropertyImpl(IRI.create(pm.getDefaultPrefix(),"isLocatedIn"));
			Set<OWLNamedIndividual> regions = reasoner.getObjectPropertyValues((OWLNamedIndividual) university, object_prop6).getFlattened();

			for(OWLIndividual region : regions)
			{
				ObjectProperty isLocatedIn = new ObjectProperty(object_prop6.toStringID(),pm.getShortForm(object_prop6));
				String namespace = region.toStringID();
				OWLDataProperty regionName = new OWLDataPropertyImpl(IRI.create(pm.getDefaultPrefix(), "regionName"));
				Collection<OWLLiteral> region_Name = EntitySearcher.getDataPropertyValues(region, regionName, localUni);
				
				Object rName[] = region_Name.toArray();
				String regionname = rName[0].toString();
				String rn = regionname.substring(1, regionname.length()-1);
				Region reg = new Region(namespace, rn);
				Triple<University, ObjectProperty, Region> tr6 = new Triple<University, ObjectProperty, Region>(uni, isLocatedIn, reg);
				database.store(tr6);
				triplestore.addTriple(tr6);
				regionlist.add(reg);
				
			}
			
			
		}
		
		//Banglore isLocatedIn Karnataka
		Region reg = regionlist.get(0);
		OWLNamedIndividual region = fac.getOWLNamedIndividual(IRI.create(reg.getNamespace()));
		
		OWLObjectPropertyImpl object_prop6 = new OWLObjectPropertyImpl(IRI.create(pm.getDefaultPrefix(),"isLocatedIn"));
		Set<OWLNamedIndividual> regions = reasoner.getObjectPropertyValues((OWLNamedIndividual) region, object_prop6).getFlattened();
		
		for(OWLNamedIndividual location1 : regions)
		{
			ObjectProperty isLocatedIn = new ObjectProperty(object_prop6.toStringID(),pm.getShortForm(object_prop6));
			String namespace = location1.toStringID();
			OWLDataProperty regionName = new OWLDataPropertyImpl(IRI.create(pm.getDefaultPrefix(), "regionName"));
			Collection<OWLLiteral> region_Name = EntitySearcher.getDataPropertyValues(location1, regionName, localUni);
			
			Object rName[] = region_Name.toArray();
			String regionname = rName[0].toString();
			String rn = regionname.substring(1, regionname.length()-1);
			Region loc = new Region(namespace, rn);
			Triple<Region, ObjectProperty, Region> tr6 = new Triple<Region, ObjectProperty, Region>(reg, isLocatedIn, loc);
			database.store(tr6);
			triplestore.addTriple(tr6);
			regionlist.add(loc);
			
		}
		//Karnataka isLocatedIn India
		reg = regionlist.get(1);
		region = fac.getOWLNamedIndividual(IRI.create(reg.getNamespace()));
		regions = reasoner.getObjectPropertyValues((OWLNamedIndividual) region, object_prop6).getFlattened();
		
		for(OWLNamedIndividual location1 : regions)
		{
			ObjectProperty isLocatedIn = new ObjectProperty(object_prop6.toStringID(),pm.getShortForm(object_prop6));
			String namespace = location1.toStringID();
			OWLDataProperty regionName = new OWLDataPropertyImpl(IRI.create(pm.getDefaultPrefix(), "regionName"));
			Collection<OWLLiteral> region_Name = EntitySearcher.getDataPropertyValues(location1, regionName, localUni);
			
			Object rName[] = region_Name.toArray();
			String regionname = rName[0].toString();
			String rn = regionname.substring(1, regionname.length()-1);
			Region loc = new Region(namespace, rn);
			Triple<Region, ObjectProperty, Region> tr6 = new Triple<Region, ObjectProperty, Region>(reg, isLocatedIn, loc);
			database.store(tr6);
			triplestore.addTriple(tr6);
			regionlist.add(loc);
			
		}
		
		database.close();
		System.out.println("Everything is stored as Triples");
		System.out.println("----------------------------------------------------------------------------------------------------------------");
	}

}