package com.ironhack.Homework3.creatingObjects;

import com.ironhack.Homework3.Repository.*;
import com.ironhack.Homework3.enums.Industry;
import com.ironhack.Homework3.enums.Product;
import com.ironhack.Homework3.model.*;
import com.ironhack.Homework3.enums.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
public class CRMMenu {
    Scanner scanner = new Scanner(System.in);
    @Autowired
    private CRMActions crmActions;
    @Autowired
    private LeadRepository leadRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private OpportunityRepository opportunityRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private SalesRepRepository salesRepRepository;

    public void menu () throws InterruptedException {
        System.out.println("\n=== Welcome to the CRM system ===\n");
        System.out.println("Introduce a command, if you don't know what to introduce you can choose the HELP command");
        Command commandEnum;
        //It keeps waiting for an input until the introduced command is QUIT
        do {
            System.out.println(">>>");
            String command = scanner.nextLine();

            //Get the String part of the introduced command and later check if is one of the possible commands
            String commandString = getCommandString(command);
            //Get the numeric part of the introduced command and use it as the id of the Object
            Long id = getCommandId(command);

            //Checking if the introduced command is one of the possible commands
            commandEnum = Command.CONTINUE; //To mantain inside the loop and not restart the menu if wrong command
            try {
                commandEnum = Command.valueOf(commandString);
                if (commandEnum.equals(Command.CONTINUE)){
                    System.out.println("This is not a correct command"); //To indicate that continue is established
                                                                         // for the loop but it has no functionality
                }
            } catch (IllegalArgumentException e) {
                System.out.println("This is not a correct command");
                continue;
            }

            //Call the different possible actions depending on the command and the id introduced
            menuActions(commandEnum,id);

        scanner = new Scanner(System.in); //To clean the scanner before the next loop
        } while (!commandEnum.equals(Command.QUIT));
    }

    public String getCommandString(String command) {
        command = command.trim();
        String commandString = command.replaceAll("[^A-Za-z ]", ""); //To get only the string part
        commandString = commandString.trim().replace(" ", "_").toUpperCase();
        commandString = commandString.replace("-","_");
        return commandString;
    }

    public Long getCommandId(String command) {
        command = command.trim();
        String commandId = command.replaceAll("[^0-9]", ""); //To get only the numeric part (id)
        commandId = commandId.trim();
        Long id = -1L;
        try {
            id = Long.parseLong(commandId);
        } catch (Exception e) {
            id = -1L;
        }
        return id;
    }

    public void menuActions(Command commandEnum, Long id) throws InterruptedException {
        //It calls all the different methods needed depending on the introduced command
        switch (commandEnum) {
            case NEW_LEAD:
                crmActions.newLeadCommand();
                break;
            case NEW_SALESREP:
                crmActions.newSalesRepCommand();
                break;
            case SHOW_LEADS:
                System.out.println(crmActions.showLeadsCommand());
                break;
            case SHOW_SALESREPS:
                System.out.println(crmActions.showSalesRepCommand());
                break;
            case SHOW_CONTACTS:
                System.out.println(crmActions.showContactsCommand());
                break;
            case SHOW_OPPORTUNITIES:
                System.out.println(crmActions.showOpportunitiesCommand());
                break;
            case SHOW_ACCOUNTS:
                System.out.println(crmActions.showAccountsCommand());
                break;
            case LOOKUP_LEAD:
                System.out.println(crmActions.lookUpLeadIdCommand(id));
                break;
            case LOOKUP_ACCOUNT:
                System.out.println(crmActions.lookUpAccountIdCommand(id));
                break;
            case LOOKUP_OPPORTUNITY:
                System.out.println(crmActions.lookUpOpportunityIdCommand(id));
                break;
            case LOOKUP_CONTACT:
                System.out.println(crmActions.lookUpContactIdCommand(id));
                break;
            case CONVERT:
                crmActions.convertIdCommand(id);
                break;
            case CLOSE_LOST:
                System.out.println(crmActions.closeLostIdCommand(id));
                break;
            case CLOSE_WON:
                System.out.println(crmActions.closeWonIdCommand(id));
                break;
            case HELP:
                System.out.println(crmActions.helpCommand());
                break;
            case REPORT_LEAD_BY_SALESREP:
                System.out.println("Sales Rep id | Number of Leads");
                List<Object[]> reportLeadSalesRep =  leadRepository.findLeadsBySalesRep();
                for (Object[] objects : reportLeadSalesRep){
                    System.out.println(((SalesRep) objects[0]).getId() + "|" + objects[1]);
                }
                break;
            case REPORT_OPPORTUNITY_BY_SALESREP:
                System.out.println("Sales Rep id | Number of Opportunities");
                List<Object[]> reportOppSalesRep =  opportunityRepository.findOpportunitiesBySalesRep();
                for (Object[] objects : reportOppSalesRep){
                    System.out.println(((SalesRep) objects[0]).getId() + "|" + objects[1]);
                }
                break;
            case REPORT_CLOSED_LOST_BY_SALESREP:
                System.out.println("Sales Rep id | Number of Closed-Lost Opportunities");
                List<Object[]> reportOppCLSalesRep =  opportunityRepository.findOpportunitiesClosedLostBySalesRep();
                for (Object[] objects : reportOppCLSalesRep){
                    System.out.println(((SalesRep) objects[0]).getId() + "|" + objects[1]);
                }
                break;
            case REPORT_CLOSED_WON_BY_SALESREP:
                System.out.println("Sales Rep id | Number of Closed-Won Opportunities");
                List<Object[]> reportOppCWSalesRep =  opportunityRepository.findOpportunitiesClosedWonBySalesRep();
                for (Object[] objects : reportOppCWSalesRep){
                    System.out.println(((SalesRep) objects[0]).getId() + "|" + objects[1]);
                }
                break;
            case REPORT_OPEN_BY_SALESREP:
                System.out.println("Sales Rep id | Number of Open Opportunities");
                List<Object[]> reportOppOSalesRep =  opportunityRepository.findOpportunitiesOpenBySalesRep();
                for (Object[] objects : reportOppOSalesRep){
                    System.out.println(((SalesRep) objects[0]).getId() + "|" + objects[1]);
                }
                break;
            case REPORT_CLOSED_LOST_BY_THE_PRODUCT:
                System.out.println("Product name | Number of Closed-Lost Opportunities");
                List<Object[]> reportOppCLProduct =  opportunityRepository.findOpportunitiesClosedLostByProduct();
                for (Object[] objects : reportOppCLProduct){
                    System.out.println(objects[0] + "|" + objects[1]);
                }
                break;
            case REPORT_CLOSED_WON_BY_THE_PRODUCT:
                System.out.println("Product name | Number of Closed-Won Opportunities");
                List<Object[]> reportOppCWProduct =  opportunityRepository.findOpportunitiesClosedWonByProduct();
                for (Object[] objects : reportOppCWProduct){
                    System.out.println(objects[0] + "|" + objects[1]);
                }
                break;
            case REPORT_OPEN_BY_THE_PRODUCT:
                System.out.println("Product name | Number of Open Opportunities");
                List<Object[]> reportOppOProduct =  opportunityRepository.findOpportunitiesOpenByProduct();
                for (Object[] objects : reportOppOProduct){
                    System.out.println(objects[0] + "|" + objects[1]);
                }
                break;
            case REPORT_OPPORTUNITY_BY_THE_PRODUCT:
                System.out.println("Product name | Number of Opportunities");
                List<Object[]> reportOppProduct =  opportunityRepository.findOpportunitiesByProduct();
                for (Object[] objects : reportOppProduct){
                    System.out.println(objects[0] + "|" + objects[1]);
                }
                break;
            case REPORT_CLOSED_WON_BY_COUNTRY:
                System.out.println("Country name | Number of Closed-Won Opportunities");
                List<Object[]> reportOppCWCountry =  opportunityRepository.findOpportunitiesClosedWonByCountry();
                for (Object[] objects : reportOppCWCountry){
                    System.out.println(objects[0] + "|" + objects[1]);
                }
                break;
            case REPORT_CLOSED_LOST_BY_COUNTRY:
                System.out.println("Country name | Number of Closed-Lost Opportunities");
                List<Object[]> reportOppCLCountry =  opportunityRepository.findOpportunitiesClosedLostByCountry();
                for (Object[] objects : reportOppCLCountry){
                    System.out.println(objects[0] + "|" + objects[1]);
                }
                break;
            case REPORT_OPEN_BY_COUNTRY:
                System.out.println("Country name | Number of Open Opportunities");
                List<Object[]> reportOppOCountry =  opportunityRepository.findOpportunitiesOpenByCountry();
                for (Object[] objects : reportOppOCountry){
                    System.out.println(objects[0] + "|" + objects[1]);
                }
                break;
            case REPORT_OPPORTUNITY_BY_COUNTRY:
                System.out.println("Country name | Number of Opportunities");
                List<Object[]> reportOppCountry =  opportunityRepository.findOpportunitiesByCountry();
                for (Object[] objects : reportOppCountry){
                    System.out.println(objects[0] + "|" + objects[1]);
                }
                break;
            case REPORT_CLOSED_LOST_BY_CITY:
                System.out.println("City name | Number of Closed-Lost Opportunities");
                List<Object[]> reportOppCLCity =  opportunityRepository.findOpportunitiesClosedLostByCity();
                for (Object[] objects : reportOppCLCity){
                    System.out.println(objects[0] + "|" + objects[1]);
                }
                break;
            case REPORT_CLOSED_WON_BY_CITY:
                System.out.println("City name | Number of Closed-Won Opportunities");
                List<Object[]> reportOppCWCity =  opportunityRepository.findOpportunitiesClosedWonByCity();
                for (Object[] objects : reportOppCWCity){
                    System.out.println(objects[0] + "|" + objects[1]);
                }
                break;
            case REPORT_OPEN_BY_CITY:
                System.out.println("City name | Number of Open Opportunities");
                List<Object[]> reportOppOCity =  opportunityRepository.findOpportunitiesOpenByCity();
                for (Object[] objects : reportOppOCity){
                    System.out.println(objects[0] + "|" + objects[1]);
                }
                break;
            case REPORT_OPPORTUNITY_BY_CITY:
                System.out.println("City name | Number of Opportunities");
                List<Object[]> reportOppCity =  opportunityRepository.findOpportunitiesByCity();
                for (Object[] objects : reportOppCity){
                    System.out.println(objects[0] + "|" + objects[1]);
                }
                break;
            case REPORT_CLOSED_LOST_BY_INDUSTRY:
                System.out.println("Industry name | Number of Closed-Lost Opportunities");
                List<Object[]> reportOppCLIndustry =  opportunityRepository.findOpportunitiesClosedLostByIndustry();
                for (Object[] objects : reportOppCLIndustry){
                    System.out.println(objects[0] + "|" + objects[1]);
                }
                break;
            case REPORT_CLOSED_WON_BY_INDUSTRY:
                System.out.println("Industry name | Number of Closed-Won Opportunities");
                List<Object[]> reportOppCWIndustry =  opportunityRepository.findOpportunitiesClosdeWonByIndustry();
                for (Object[] objects : reportOppCWIndustry){
                    System.out.println(objects[0] + "|" + objects[1]);
                }
                break;
            case REPORT_OPEN_BY_INDUSTRY:
                System.out.println("Industry name | Number of Open Opportunities");
                List<Object[]> reportOppOIndustry =  opportunityRepository.findOpportunitiesOpenByIndustry();
                for (Object[] objects : reportOppOIndustry){
                    System.out.println(objects[0] + "|" + objects[1]);
                }
                break;
            case REPORT_OPPORTUNITY_BY_INDUSTRY:
                System.out.println("Industry name | Number of Opportunities");
                List<Object[]> reportOppIndustry =  opportunityRepository.findOpportunitiesByIndustry();
                for (Object[] objects : reportOppIndustry){
                    System.out.println(objects[0] + "|" + objects[1]);
                }
                break;
            case MAX_EMPLOYEECOUNT:
                System.out.println("Maximum number of Employee count: " + accountRepository.MaxEmployeeCount());

                break;
            case MEAN_EMPLOYEECOUNT:
                System.out.println("Mean number of Employee count: " + accountRepository.AvgEmployeeCount());
                break;
            case MEDIAN_EMPLOYEECOUNT:
                List<Integer> medianEmployeeCountList = accountRepository.MedEmployeeCount_firstStep();
                double medianEmployeeCount = median_secondStep(medianEmployeeCountList);
                System.out.println("Median number of Employee count: " + medianEmployeeCount);
                break;
            case MIN_EMPLOYEECOUNT:
                System.out.println("Minimum number of Employee count: " + accountRepository.MinEmployeeCount());
                break;
            case MAX_QUANTITY:
                System.out.println("Maximum quantity of products: " + opportunityRepository.MaxQuantity());
                break;
            case MEAN_QUANTITY:
                System.out.println("Mean quantity of products: " + opportunityRepository.AvgQuantity());
                break;
            case MEDIAN_QUANTITY:
                List<Integer> medianQuantityList = opportunityRepository.MedQuantity_firstStep();
                double medianQuantity = median_secondStep(medianQuantityList);
                System.out.println("Median quantity of products: " + medianQuantity);
                break;
            case MIN_QUANTITY:
                System.out.println("Minimum quantity of products: " + opportunityRepository.MinQuantity());
                break;
            case MAX_OPPS_PER_ACCOUNT:
                System.out.println("Maximum number of Opportunities associated with an Account: " +
                        opportunityRepository.findMaxOpportunitiesPerAccount());
                break;
            case MEAN_OPPS_PER_ACCOUNT:
                System.out.println("Mean number of Opportunities associated with an Account: " +
                        opportunityRepository.findMeanOpportunitiesPerAccount());
                break;
            case MEDIAN_OPPS_PER_ACCOUNT:
                List<Integer> medOpportunitiesPerAccountList  =
                        opportunityRepository.medOpportunitiesPerAccount_firstStep();
                double medOpportunitiesPerAccount = median_secondStep(medOpportunitiesPerAccountList);
                System.out.println("Median number of Opportunities associated with an Account: "
                        + medOpportunitiesPerAccount);
                break;
            case MIN_OPPS_PER_ACCOUNT:
                System.out.println("Minimum number of Opportunities associated with an Account: " +
                        opportunityRepository.findMinOpportunitiesPerAccount());
                break;
            case QUIT:
                crmActions.quitCommand();
                break;
        }
    }

    private double median_secondStep(List<Integer> medianList) {
        int size = medianList.size();
        int medIndex = size/2;
        double median = 0;
        if (size%2 != 0){
            median = medianList.get(medIndex);
        } else {
            double median1 = medianList.get(medIndex-1);
            double median2 = medianList.get(medIndex);
            median = (median1+median2)/2;
        }
        return median;
    }
}





