package semantic_web_final;

import java.io.File;

import org.semanticweb.HermiT.ReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.ConsoleProgressMonitor;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;



public class Ontology {
	

	//create Ontology Manager
	public OWLOntologyManager createOntologyManager()
	{
		OWLOntologyManager mn = OWLManager.createOWLOntologyManager();
		return mn;
	}
	//Load Ontology into memory
	public OWLOntology loadOntology(String path, OWLOntologyManager manager) throws OWLOntologyCreationException
	{
		File file = new File(path);
		OWLOntology localUniversity = manager.loadOntologyFromOntologyDocument(file);
		System.out.println("Loaded ontology: "+ localUniversity.getOntologyID());
		IRI location = manager.getOntologyDocumentIRI(localUniversity);
		System.out.println("\tfrom:"+ location);
		
		return localUniversity;
	}
	
	//Configure and create HermiT reasoner
	public OWLReasoner configureReasoner(OWLOntology localUni)
	{
		OWLReasonerFactory reasonerFactory = new ReasonerFactory();
		ConsoleProgressMonitor progressMonitor = new ConsoleProgressMonitor();
		OWLReasonerConfiguration config = new SimpleConfiguration(progressMonitor);
		
		OWLReasoner reasoner = reasonerFactory.createReasoner(localUni, config);
		return reasoner;
	}
	
}
