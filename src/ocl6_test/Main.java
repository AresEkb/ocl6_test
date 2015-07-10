package ocl6_test;

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ocl.pivot.ExpressionInOCL;
import org.eclipse.ocl.pivot.utilities.OCL;
import org.eclipse.ocl.pivot.utilities.OCLHelper;
import org.eclipse.ocl.pivot.utilities.ParserException;
import org.eclipse.ocl.xtext.essentialocl.EssentialOCLStandaloneSetup;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Signal;
import org.eclipse.uml2.uml.UMLPackage;

public class Main {

    public static void main(String[] args) {
        final String input = "model/My.uml";
        final String input2 = "model/ISO20022_BusinessProcessCatalogue.uml";
        
        System.out.println("Initialization");
        ResourceSet rs = new ResourceSetImpl();
        rs.setURIConverter(new CustomURIConverter());
        
        EssentialOCLStandaloneSetup.doSetup();
        org.eclipse.ocl.pivot.uml.UMLStandaloneSetup.init();
        
        OCL ocl = OCL.newInstance(rs);

        System.out.println("Loading UML model " + input);
        Resource resource = ocl.getResourceSet().getResource(createFileURI(input), true);

        Model uml = (Model)EcoreUtil.getObjectByType(resource.getContents(), UMLPackage.eINSTANCE.getModel());
        System.out.println("Root: " + uml);
        
        System.out.println("\n\nTest 1\n");
        try {
            Class person = (Class)uml.getPackagedElement("Person");
            System.out.println("Person: " + person);

            org.eclipse.ocl.pivot.Class personAS = ocl.getMetamodelManager().getASOf(org.eclipse.ocl.pivot.Class.class, person);
            System.out.println("PersonAS: " + personAS);

            OCLHelper helper = ocl.createOCLHelper(personAS);
            System.out.println("Helper: " + helper);
            System.out.println("Context: " + helper.getContextClass());
            ExpressionInOCL expr = ocl.createInvariant(personAS, "age2 > 0");
            System.out.println("Expression: " + expr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("\n\nTest 2\n");
        try {
            Class messageClass = (Class)uml.getPackagedElement("MessageClass");
            System.out.println("MessageClass: " + messageClass);

            org.eclipse.ocl.pivot.Class messageAS = ocl.getMetamodelManager().getASOf(org.eclipse.ocl.pivot.Class.class, messageClass);
            System.out.println("MessageClassAS: " + messageAS);

            OCLHelper helper = ocl.createOCLHelper(messageAS);
            System.out.println("Helper: " + helper);
            System.out.println("Context: " + helper.getContextClass());
            ExpressionInOCL expr = ocl.createInvariant(messageAS, "Header.Id > 0");
            System.out.println("Expression: " + expr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("\n\nTest 3\n");
        try {
            Signal messageSignal = (Signal)uml.getPackagedElement("MessageSignal");
            System.out.println("MessageSignal: " + messageSignal);

            org.eclipse.ocl.pivot.Signal messageAS = ocl.getMetamodelManager().getASOf(org.eclipse.ocl.pivot.Signal.class, messageSignal);
            System.out.println("MessageSignalAS: " + messageAS);

            OCLHelper helper = ocl.createOCLHelper(messageAS);
            System.out.println("Helper: " + helper);
            System.out.println("Context: " + helper.getContextClass());
            ExpressionInOCL expr = ocl.createInvariant(messageAS, "Header.Id > 0");
            System.out.println("Expression: " + expr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("\n\nTest 4\n");
        try {
            Class messageClass = (Class)uml.getPackagedElement("MessageClass");
            System.out.println("MessageClass: " + messageClass);

            for (Constraint rule : messageClass.getOwnedRules()) {
                ExpressionInOCL expr = getExpressionInOCL(ocl, rule);
                System.out.println("Expression: " + expr);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("\n\nTest 5\n");
        try {
            Signal messageSignal = (Signal)uml.getPackagedElement("MessageSignal");
            System.out.println("MessageSignal: " + messageSignal);

            for (Constraint rule : messageSignal.getOwnedRules()) {
                ExpressionInOCL expr = getExpressionInOCL(ocl, rule);
                System.out.println("Expression: " + expr);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println("Loading UML model " + input2);
        Resource resource2 = ocl.getResourceSet().getResource(createFileURI(input2), true);

        Model uml2 = (Model)EcoreUtil.getObjectByType(resource2.getContents(), UMLPackage.eINSTANCE.getModel());
        System.out.println("Root2: " + uml2);
        
        System.out.println("\n\nTest 6\n");
        try {
            Class message = (Class)uml2.getPackagedElement("CustomerCreditTransferInitiationV03");
            System.out.println("Message: " + message);

            org.eclipse.ocl.pivot.Class messageAS = ocl.getMetamodelManager().getASOf(org.eclipse.ocl.pivot.Class.class, message);
            System.out.println("MessageAS: " + messageAS);

            OCLHelper helper = ocl.createOCLHelper(messageAS);
            System.out.println("Helper: " + helper);
            System.out.println("Context: " + helper.getContextClass());
            ExpressionInOCL expr = ocl.createInvariant(messageAS, "GroupHeader.NumberOfTransactions = PaymentInformation.CreditTransferTransactionInformation->size()");
            System.out.println("Expression: " + expr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("\n\nTest 7\n");
        try {
            Class message = (Class)uml2.getPackagedElement("CustomerCreditTransferInitiationV03");
            System.out.println("Message: " + message);

            for (Constraint rule : message.getOwnedRules()) {
                ExpressionInOCL expr = getExpressionInOCL(ocl, rule);
                System.out.println("Expression: " + expr);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("\n\nTest 8\n");
        try {
            Class person = (Class)uml.getPackagedElement("Person");
            System.out.println("Person: " + person);

            org.eclipse.ocl.pivot.Class personAS = ocl.getMetamodelManager().getASOf(org.eclipse.ocl.pivot.Class.class, person);
            System.out.println("PersonAS: " + personAS);

            OCLHelper helper = ocl.createOCLHelper(personAS);
            System.out.println("Helper: " + helper);
            System.out.println("Context: " + helper.getContextClass());
            ExpressionInOCL expr = ocl.createInvariant(personAS, "custom.size() <= 70");
            System.out.println("Expression: " + expr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("\n\nTest 9\n");
        try {
            Class person = (Class)uml.getPackagedElement("Person");
            System.out.println("Person: " + person);

            for (Constraint rule : person.getOwnedRules()) {
                ExpressionInOCL expr = getExpressionInOCL(ocl, rule);
                System.out.println("Expression: " + expr);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        ocl.dispose();
    }

    private static URI createFileURI(String relativePath)
    {
        return URI.createFileURI(new File(relativePath).getAbsolutePath());
    }

    public static ExpressionInOCL getExpressionInOCL(OCL ocl, Constraint constraint) throws ParserException
    {
        org.eclipse.ocl.pivot.Constraint asConstraint = ocl.getMetamodelManager().getASOf(org.eclipse.ocl.pivot.Constraint.class, constraint);
        return ocl.getSpecification(asConstraint);
    }
}
