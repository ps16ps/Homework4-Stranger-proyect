package com.ironhack.Homework3.creatingObjects;

import com.ironhack.Homework3.Repository.*;
import com.ironhack.Homework3.model.*;
import com.ironhack.Homework3.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CRMActions {
    Scanner scanner = new Scanner(System.in);
    @Autowired
    private LeadRepository leadRepository;
    @Autowired
    private SalesRepRepository salesRepRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private OpportunityRepository opportunityRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CreatingObject creatingObject;
    @Autowired
    CreatingOpportunity creatingOpportunity;
    @Autowired
    CreatingAccount creatingAccount;

    public void newLeadCommand() {
        //Creates a new Lead and adds it to the leadMap
        creatingObject.creatingLead();
    }

    public void newSalesRepCommand() {
        //Creates a new Lead and adds it to the leadMap
        creatingObject.creatingSalesRep();
    }

    public String showLeadsCommand() {
        //Shows the id and the name of all the leads present in the leadMap
        if(leadRepository.count()>=1){
            String message = "";
            for (Lead lead : leadRepository.findAll()) {
                message += (lead.showLead()) + "\n";
            }
            return message;
        }
        return "There are no Leads yet"; //If the leadMap is empty
    }

    public String showSalesRepCommand() {
        //Shows the id and the name of all the salesRep present in the salesRepMap
        if(salesRepRepository.count()>=1){
            String message = "";
            for (SalesRep salesRep : salesRepRepository.findAll()) {
                message += (salesRep.showSalesRep()) + "\n";
            }
            return message;
        }
        return "There are no SalesReps yet"; //If the salesRepMap is empty
    }

    public String showContactsCommand() {
        //Shows the id and the name of all the contacts present in the contactMap
        if(contactRepository.count()>=1){
            String message = "";
            for (Contact contact : contactRepository.findAll()) {
                message += (contact.showContact()) + "\n";
            }
            return message;
        }
        return "There are no Contacts yet"; //If the contactMap is empty
    }

    public String showOpportunitiesCommand() {
        //Shows the id and the name of the decision maker of all the opportunities present in the opportunityMap
        if(opportunityRepository.count()>=1) {
            String message = "";
            for (Opportunity opportunity : opportunityRepository.findAll()) {
                message += opportunity.showOpportunity() + "\n";
            }
            return message;
        }
        return "There are no Opportunities yet"; //If the opportunityMap is empty
    }

    public String showAccountsCommand() {
        //Shows the id and the name of the first contact of all the accounts present in the accountMap
        if(accountRepository.count()>=1){
            String message = "";
            for (Account account : accountRepository.findAll()) {
                message += account.showAccount() + "\n";
            }
            return message;
        }
        return "There are no Accounts yet"; //If the accountMap is empty
    }

    public String  lookUpLeadIdCommand(Long id) {
        if(leadRepository.count()<1){
            return "There are no Leads yet"; //If the Map is empty
        }
        // Check if id exists in the Map
        boolean isIdPresent = leadRepository.existsById(id);
        if (!isIdPresent) {
            return "Id not found, try again"; //If id not present in the Map
        }
        Optional<Lead> optionalLead = leadRepository.findById(id);
        return optionalLead.get().toString();
    }

    public String lookUpAccountIdCommand(Long id) {
        if(accountRepository.count()<1){
            return "There are no Accounts yet"; //If the Map is empty
        }
        // Check if id exists in the Map
        boolean isIdPresent = accountRepository.existsById(id);
        if (!isIdPresent) {
            return "Id not found, try again"; //If id not present in the Map
        }
        Optional<Account> optionalAccount = accountRepository.findById(id);
        return optionalAccount.get().toString();
    }

    public String lookUpOpportunityIdCommand(Long id) {
        if(opportunityRepository.count()<1){
            return "There are no Opportunities yet"; //If the Map is empty
        }
        // Check if id exists in the Map
        boolean isIdPresent = opportunityRepository.existsById(id);
        if (!isIdPresent) {
            return "Id not found, try again"; //If id not present in the Map
        }
        Optional<Opportunity> optionalOpportunity = opportunityRepository.findById(id);
        return optionalOpportunity.get().toString();
    }

    public String lookUpContactIdCommand(Long id) {
        if(contactRepository.count()<1){
            return "There are no Contacts yet"; //If the Map is empty
        }
        // Check if id exists in the Map
        boolean isIdPresent = contactRepository.existsById(id);
        if (!isIdPresent) {
            return "Id not found, try again"; //If id not present in the Map
        }
        Optional<Contact> optionalContact = contactRepository.findById(id);
        return optionalContact.get().toString();
    }

    public void convertIdCommand(Long id) throws InterruptedException {
        scanner = new Scanner(System.in);
        if(leadRepository.count()<1){
            System.out.println("There are no Leads yet"); //If the Map is empty
        } else {
            // Check if id exists in the Map
            boolean isIdPresent = leadRepository.existsById(id);
            if (isIdPresent) {
                Optional<Lead> optionalLead = leadRepository.findById(id);
                //Create Account to the Map
                System.out.println("Would you like to create a new Account? (Y/N)");
                String answer = scanner.nextLine();
                answer = answer.toLowerCase();
                while (!answer.equals("y") && !answer.equals("n")){
                    System.out.println("This is not a correct option. Please try again");
                    answer = scanner.nextLine();
                    answer = answer.toLowerCase();
                }
                Account account = new Account();
                switch (answer){
                    case "y":
                        account = creatingAccount.creatingAccount();
                        break;
                    case "n":
                        if (accountRepository.count()<1){
                            System.out.println("There are no Accounts yet, a new Account will be created");
                            account = creatingAccount.creatingAccount();
                        }else {
                            System.out.println("Please indicate the ID of the Account to which you want to " +
                                    "associate the Contact and Opportunity");
                            Long accountId = getId();
                            boolean isAccountPresent = accountRepository.existsById(accountId);
                            while (!isAccountPresent) {
                                System.out.println("Id not found, try again"); //If id not present in the Map
                                accountId = getId();
                                isAccountPresent = accountRepository.existsById(accountId);
                            }
                            Optional<Account> optionalAccount = accountRepository.findById(accountId);
                            account = optionalAccount.get();
                        }
                        break;
                }
                Thread.sleep(500);
                //Create Contact and add it to the List and the Map
                Contact contact = creatingObject.creatingContact(optionalLead.get(),account);
                //Create Opportunity to the List and the Map
                Thread.sleep(500);
                Opportunity opportunity = creatingOpportunity.creatingOpportunity(contact, optionalLead.get(), account);
                //Remove Lead
                Thread.sleep(1000);
                leadRepository.delete(optionalLead.get());
                System.out.println("Lead successfully converted and removed from the CRM system");
            } else {
                System.out.println("Id not found, try again"); //If id not present in the Map
            }
        }
    }

    public Long getId(){
        Long accountId = -1L;
        while (accountId < 0) {
            try {
                accountId = scanner.nextLong();
            } catch (InputMismatchException e) {
                System.out.println("Please insert a correct ID");
            }
        }
        return accountId;
    }

    public String closeLostIdCommand(Long id) {
        if(opportunityRepository.count()<1){
            return "There are no Opportunities yet"; //If the Map is empty
        }
        // Check if id exists in the Map
        boolean isIdPresent = opportunityRepository.existsById(id);
        if (isIdPresent) {
            Optional<Opportunity> optionalOpportunity = opportunityRepository.findById(id);
            optionalOpportunity.get().setStatus(Status.CLOSED_LOST);
            opportunityRepository.save(optionalOpportunity.get());
            return "Status of Opportunity " + id + " changed to CLOSED_LOST";
        }
        return "Id not found, try again";
    }

    public String closeWonIdCommand(Long id) {
        if(opportunityRepository.count()<1){
            return "There are no Opportunities yet"; //If the Map is empty
        }
        // Check if id exists in the Map
        boolean isIdPresent = opportunityRepository.existsById(id);
        if (isIdPresent) {
            Optional<Opportunity> optionalOpportunity = opportunityRepository.findById(id);
            optionalOpportunity.get().setStatus(Status.CLOSED_WON);
            opportunityRepository.save(optionalOpportunity.get());
            return "Status of Opportunity " + id + " changed to CLOSED_WON";
        }
        return "Id not found, try again";
    }

    public String helpCommand() {
        return ("=== Possible Commands ===\n"+
                "NEW LEAD -> Add Lead to the CRM system\n" +
                "NEW SALESREP -> Add SalesRep to the CRM system\n" +
                "SHOW LEADS -> Display a list of all the LEADS' id and name\n" +
                "SHOW SALESREP -> Display a list of all the SalesRep' id and name\n"+
                "SHOW CONTACTS -> Display a list of all the CONTACTS' id and name\n" +
                "SHOW OPPORTUNITIES -> Display a list of all the OPPORTUNITIES' id and name of the decision maker\n" +
                "SHOW ACCOUNTS -> Display a list of all the ACCOUNTS id and name of the first contact\n" +
                "LOOKUP LEAD <ID> -> Display the selected LEAD's details with the indicated Id Number\n" +
                "LOOKUP ACCOUNT <ID> -> Display the selected ACCOUNT's details with the indicated Id Number\n" +
                "LOOKUP OPPORTUNITY <ID> -> Display the selected OPPORTUNITY's details with the indicated Id Number\n" +
                "LOOKUP CONTACT <ID> -> Display the selected CONTACT's details with the indicated Id Number\n" +
                "CONVERT <ID> -> Converts the selected LEAD in CONTACT, OPPORTUNITY and ACCOUNT " +
                "and removes it from the system\n" +
                "CLOSE LOST <ID> -> Changes the selected OPPORTUNITY status to CLOSE-LOST\n" +
                "CLOSE WON <ID> -> Changes the selected OPPORTUNITY status to CLOSE-WON\n" +
                "REPORT LEAD BY SALESREP -> A count of Leads by SalesRep\n" +
                "REPORT OPPORTUNITY BY SALESREP -> A count of all Opportunities by SalesRep\n" +
                "REPORT CLOSED-WON BY SALESREP -> A count of all CLOSED_WON Opportunities by SalesRep\n" +
                "REPORT CLOSED-LOST BY SALESREP -> A count of all CLOSED_LOST Opportunities by SalesRep\n" +
                "REPORT OPEN BY SALESREP -> A count of all OPEN Opportunities by SalesRep\n" +
                "REPORT OPPORTUNITY BY THE PRODUCT -> A count of all Opportunities by the product\n" +
                "REPORT CLOSED-WON BY THE PRODUCT -> A count of all CLOSED_WON Opportunities\n" +
                "REPORT CLOSED-LOST THE PRODUCT -> A count of all CLOSED_LOST Opportunities\n" +
                "REPORT OPEN BY THE PRODUCT -> A count of all OPEN Opportunities by the product\n" +
                "REPORT OPPORTUNITY BY COUNTRY -> A count of all Opportunities by country\n" +
                "REPORT CLOSED-WON BY COUNTRY -> A count of all CLOSED_WON Opportunities by country\n" +
                "REPORT CLOSED-LOST BY COUNTRY -> A count of all CLOSED_LOST Opportunities by country\n" +
                "REPORT OPEN BY COUNTRY -> A count of all OPEN Opportunities\n" +
                "REPORT OPPORTUNITY BY CITY -> A count of all Opportunities by the city\n" +
                "REPORT CLOSED-WON BY CITY -> A count of all CLOSED_WON Opportunities by the city\n" +
                "REPORT CLOSED-LOST BY CITY -> A count of all CLOSED_LOST Opportunities by the city\n" +
                "REPORT OPEN BY CITY -> A count of all OPEN Opportunities by the city\n" +
                "REPORT OPPORTUNITY BY INDUSTRY -> A count of all Opportunities by industry\n" +
                "REPORT CLOSED-WON BY INDUSTRY -> A count of all CLOSED_WON Opportunities by industry\n" +
                "REPORT CLOSED-LOST BY INDUSTRY -> A count of all CLOSED_LOST Opportunities by industry\n" +
                "REPORT OPEN BY INDUSTRY -> A count of all OPEN Opportunities by industry\n" +
                "MEAN EMPLOYEECOUNT -> The mean employeeCount\n" +
                "MEDIAN EMPLOYEECOUNT -> The median employeeCount\n" +
                "MAX EMPLOYEECOUNT -> The max employeeCount\n" +
                "MIN EMPLOYEECOUNT -> The min employeeCount\n" +
                "MEAN QUANTITY -> The mean quantity of products order\n" +
                "MEDIAN QUANTITY -> The median quantity of products order\n" +
                "MAX QUANTITY -> The max quantity of products order\n" +
                "MIN QUANTITY -> The min quantity of products order\n" +
                "MEAN OPPS PER ACCOUNT -> The mean number of Opportunities associated with an Account\n" +
                "MEDIAN OPPS PER ACCOUNT -> The median number of Opportunities associated with an Account\n" +
                "MAX OPPS PER ACCOUNT -> The maximum number of Opportunities associated with an Account\n" +
                "MIN OPPS PER ACCOUNT -> The minimum number of Opportunities associated with an Account\n" +
                "QUIT -> Terminates the CRM system");
    }

    public void quitCommand() {
        System.out.println("Thank you for using the CRM system. Have a nice day.");
        scanner.close();
        System.exit(0);
    }
}
