package semanticweb.model.resources;

import org.apache.jena.ontology.*;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.VCARD;

import java.io.InputStream;

public class JenaRequest {
    private static String ns = "http://www.semanticweb.org/grupo07/ontologies#";
    public static String topo = "http://data.ign.fr/def/topo#";
    private static String rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    private static String owl = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    private static String xsd = "http://www.w3.org/2001/XMLSchema#";
    private static String rdfs = "http://www.w3.org/2000/01/rdf-schema#";
    private static String stringTypeURI = "http://www.w3.org/2001/XMLSchema#string";
    private static String schema = "http://schema.org/";
    private static String lat = "<http://www.semanticweb.org/grupo07/ontologies/property#hasLatitude>";
    private static String lon = "<http://www.semanticweb.org/grupo07/ontologies/property#hasLongitud>";

    public static Park ParkRequest(String latitude, String longitude, Model model) {

        String queryString = "SELECT ?park ?name ?description ?bus ?under " +
                "WHERE { ?park a <http://www.semanticweb.org/grupo07/ontologies/class#Park> ; " +
                "<http://www.semanticweb.org/grupo07/ontologies/property#hasName> ?name ; " +
                "<http://www.semanticweb.org/grupo07/ontologies/property#hasDescription> ?description ; " +
                "<http://www.semanticweb.org/grupo07/ontologies/property#hasBus> ?bus ; " +
                "<http://www.semanticweb.org/grupo07/ontologies/property#hasUnderground> ?under ; " +
                "<http://www.semanticweb.org/grupo07/ontologies/property#hasLongitud> \"" + longitude + "\" ; " +
                "<http://www.semanticweb.org/grupo07/ontologies/property#hasLatitude> \"" + latitude + "\" }";

        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        ResultSet results = qexec.execSelect();
        String name = "";
        String transport = "";
        String description = "";
        while (results.hasNext()) {
            QuerySolution binding = results.nextSolution();
            Resource Subject = binding.getResource("park");
            System.out.println("Parque " + Subject.getURI());
            name = binding.getLiteral("name").getString();
            transport= "BUS: "+ binding.getLiteral("bus").getString()+"\n";
            transport+= "METRO: "+ binding.getLiteral("under").getString();
            description += binding.getLiteral("description").getString() + "\n";
        }

        Park park = new Park();
        park.setName(name);
        park.setDescription(description);
        park.setTransport(transport);
        park.setLatitude(latitude);
        park.setLongitude(longitude);
        return park;
    }

/*
    public static void main(String[] args) {
        String filename = "src/main/java/semanticweb/model/resources/parques-jardines-with-links.ttl";
        Model model = ModelFactory.createDefaultModel();
        InputStream in = FileManager.get().open(filename);
        if (in == null)
            throw new IllegalArgumentException("File: " + filename + " not found");

        model.read("src/main/java/semanticweb/model/resources/parques-jardines-with-links.ttl");

        Park p = ParkRequest("40.43346186779773", "-3.6785954778654113", model);
        System.out.println(p.getTransport());
        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
    } */
}
