package ocl6_test;

import java.io.File;
import java.util.Iterator;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ocl.pivot.Element;
import org.eclipse.ocl.pivot.ExpressionInOCL;
import org.eclipse.ocl.pivot.resource.ASResource;
import org.eclipse.ocl.pivot.utilities.OCL;
import org.eclipse.ocl.pivot.utilities.OCLHelper;
import org.eclipse.ocl.pivot.utilities.ParserException;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.UMLPackage;

public class Main {

    public static void main(String[] args) {
        final String input = "model/My.uml";
        
        System.out.println("Initialization");
        ResourceSet rs = new ResourceSetImpl();
        rs.setURIConverter(new CustomURIConverter());
        
        org.eclipse.ocl.pivot.model.OCLstdlib.install();
        org.eclipse.ocl.pivot.uml.UMLStandaloneSetup.init();
        
        OCL ocl = OCL.newInstance(rs);

        System.out.println("Loading UML model " + input);
        Resource resource = ocl.getResourceSet().getResource(createFileURI(input), true);

        Model uml = (Model)EcoreUtil.getObjectByType(resource.getContents(), UMLPackage.eINSTANCE.getModel());
        System.out.println("Root: " + uml);
        
        Class person = (Class)uml.getPackagedElement("Person");
        System.out.println("Class: " + person);

        try {
            OCLHelper helper = ocl.createOCLHelper(person);
            System.out.println("Helper: " + helper);
            System.out.println("Context: " + helper.getContextClass());
            ExpressionInOCL expr = ocl.createInvariant(person, "age2 > 0");
            System.out.println("Expression: " + expr);
        }
        catch (ParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ocl.dispose();
    }

    private static URI createFileURI(String relativePath)
    {
        return URI.createFileURI(new File(relativePath).getAbsolutePath());
    }
}
