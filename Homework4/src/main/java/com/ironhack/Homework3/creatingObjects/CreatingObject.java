package com.ironhack.Homework3.creatingObjects;

import com.ironhack.Homework3.Repository.ContactRepository;
import com.ironhack.Homework3.Repository.LeadRepository;
import com.ironhack.Homework3.Repository.SalesRepRepository;
import com.ironhack.Homework3.model.Account;
import com.ironhack.Homework3.model.Contact;
import com.ironhack.Homework3.model.Lead;
import com.ironhack.Homework3.model.SalesRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.lang.management.OperatingSystemMXBean;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CreatingObject {
    Scanner scanner = new Scanner(System.in);
    @Autowired
    private LeadRepository leadRepository;
    @Autowired
    private SalesRepRepository salesRepRepository;
    @Autowired
    private ContactRepository contactRepository;

    //CREATION METHODS
    public void creatingLead(){
        Lead lead = new Lead(writeName(), writePhoneNumber(), writeEmail(), writeCompanyName(),
                getSalesRep());
        for (Lead lead1 : leadRepository.findAll()){
            if (lead1.equals(lead)){
            System.out.println("This LEAD has been created before");
            return;
            }
        }
        leadRepository.save(lead);
        System.out.println("New LEAD created with id " + lead.getId());
    }

    public Contact creatingContact(Lead lead, Account account){
        Contact contact = new Contact(lead.getName(), lead.getPhoneNumber(),
                lead.getEmail(), lead.getCompanyName(),account);
        contactRepository.save(contact);
        System.out.println("New CONTACT created with id " + contact.getId());
        return contact;
    }

    public void creatingSalesRep(){
        SalesRep salesRep = new SalesRep(writeName());
        for (SalesRep salesRep1 : salesRepRepository.findAll()) {
            if (salesRep1.equals(salesRep)) {
                System.out.println("This SALESREP has been created before");
                return;
            }
        }
        salesRepRepository.save(salesRep);
        System.out.println("New SALESREP created with id " + salesRep.getId());
    }

    //READ INPUT METHODS
    public String writeName(){
        System.out.println("Please write the first name and last name");
        scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        name = name.trim();
        while (!objectName(name)){
            System.out.println("Please insert a correct name");
            name = scanner.nextLine();
        }
        return name;
    }

    public String writePhoneNumber(){
        System.out.println("Please write the phone number (format +34 654321987)");
        scanner = new Scanner(System.in);
        String phoneNumber = scanner.nextLine();
        phoneNumber = phoneNumber.trim();
        while (!objectPhoneNumber(phoneNumber)){
            System.out.println("Please insert a correct phone number");
            phoneNumber = scanner.nextLine();
        }
        return phoneNumber;
    }

    public String writeEmail(){
        System.out.println("Please write your email");
        scanner = new Scanner(System.in);
        String email = scanner.nextLine();
        email = email.trim();
        while (!objectEmail(email)) {
            System.out.println("Please insert a correct email");
            email = scanner.nextLine();
        }
        return email;
    }

    public String writeCompanyName(){
        System.out.println("Please write your company name");
        scanner = new Scanner(System.in);
        String companyName = scanner.nextLine();
        companyName = companyName.trim();
        while(!objectCompanyName(companyName)){
            System.out.println("Please write a correct company name");
            companyName = scanner.nextLine();
        }
        return companyName;
    }

    public SalesRep getSalesRep() {
        System.out.println("Please introduce the id of the SalesRep");
        scanner = new Scanner(System.in);
        String salesRepId = scanner.nextLine();
        salesRepId = salesRepId.trim();
        Long id = getSalesRepId(salesRepId);
        Optional<SalesRep> optionalSalesRep = salesRepRepository.findById(id);
        return optionalSalesRep.get();
    }

    private Long getSalesRepId(String salesRepId) {
        Long id = -1L;
        try {
            id = Long.parseLong(salesRepId);
        } catch (Exception e){
            id = -1L;
        }
        while (!salesRepRepository.existsById(id)){
            System.out.println("Please write a correct SalesRep Id");
            scanner = new Scanner(System.in);
            salesRepId = scanner.nextLine();
            salesRepId = salesRepId.trim();
            id = getSalesRepId(salesRepId);
        }
        return id;
    }

    //VALIDATE INPUT METHODS
    public Boolean objectName(String name){
        name = name.trim();
        String[] arrayName = name.split(" ");
        if(!name.matches("[a-zA-Z ]+") || arrayName.length < 2 ){
            return false;
        }
        return true;
    }

    public Boolean objectPhoneNumber(String phoneNumber){
        phoneNumber = phoneNumber.trim();
        final String regex = "^\\+\\d{1,3}\s\\d{6,12}"; // Has a + at the beginning, followed by 2-3 numbers (prefix),
        // a blanc space and 6-12 numbers (number)
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
       if (!matcher.matches()){
           return false;
        }
        return true;
    }

    public Boolean objectEmail(String email){
        email = email.trim();
        if (email.contains("@") && email.contains(".") && !email.contains(" ")) {
            String[] email1 = email.split("@");
            if (email1[1].contains(".")) {
                return true;
            }
        }
        return false;
    }

    public Boolean objectCompanyName(String companyName){
        companyName = companyName.trim();
        if(companyName.isEmpty()){
            return false;
        }
        return true;
    }
}
