package com.ironhack.Homework3.creatingObjects;

import com.ironhack.Homework3.Repository.AccountRepository;
import com.ironhack.Homework3.Repository.ContactRepository;
import com.ironhack.Homework3.Repository.OpportunityRepository;
import com.ironhack.Homework3.enums.Industry;
import com.ironhack.Homework3.model.Account;
import com.ironhack.Homework3.model.Contact;
import com.ironhack.Homework3.model.Opportunity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
public class CreatingAccount {
    Scanner scanner = new Scanner(System.in);
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    OpportunityRepository opportunityRepository;

    //CREATION METHODS
    public Account creatingAccount(){
        Account account = new Account(selectIndustry(), employeeCount(), writeCity(),
                writeCounty());
        for (Account account1 : accountRepository.findAll()){
            if (account1.equals(account)){
                System.out.println("This ACCOUNT has been created before");
                return account;
            }
        }
        accountRepository.save(account);
        System.out.println("New ACCOUNT created with id " + account.getId());
        return account;
    }

    //READ INPUT METHODS
    public Industry selectIndustry() throws IllegalArgumentException {
        System.out.println("Please write the industry of the company: PRODUCE, " +
                "ECOMMERCE, MANUFACTURING, MEDICAL, OTHER");
        scanner = new Scanner(System.in);
        String industry = scanner.next();
        industry = industry.trim().toUpperCase();
        while (!selectIndustryCondition(industry)){
            System.out.println("Please write a correct product type");
            industry = scanner.nextLine();
            industry = industry.trim().toUpperCase();
        }
        return Industry.valueOf(industry);
    }

    public int employeeCount() {
        System.out.println("Please write the number of employees in the company ");
        scanner = new Scanner(System.in);
        String employeeCount = scanner.nextLine();
        employeeCount = employeeCount.trim();
        while (!employeeCountCondition(employeeCount)) {
            System.out.println("Please insert a correct number of employees");
            employeeCount = scanner.nextLine();
        }
        return Integer.parseInt(employeeCount);
    }

    public String writeCity(){
        System.out.println("Please write the City of the company");
        scanner = new Scanner(System.in);
        String cityName = scanner.nextLine();
        cityName = cityName.trim();
        while (!writeCityCondition(cityName)){
            System.out.println("Please insert a correct city");
            cityName = scanner.nextLine();
        }
        return cityName;
    }

    public String writeCounty(){
        System.out.println("Please write the Country of the company");
        scanner = new Scanner(System.in);
        String countryName = scanner.nextLine();
        countryName = countryName.trim();
        while (!writeCountryCondition(countryName)){
            System.out.println("Please insert a correct country");
            countryName = scanner.nextLine();
        }
        return countryName;
    }

    //VALIDATE INPUT METHODS
    public boolean selectIndustryCondition(String industry){
        industry = industry.trim().toUpperCase();
        if(!industry.equals("PRODUCE") && !industry.equals("ECOMMERCE") && !industry.equals("MANUFACTURING")
                && !industry.equals("MEDICAL") && !industry.equals("OTHER")){
            return false;
        }
        return true;
    }

    public boolean employeeCountCondition(String employeeCount){
        employeeCount = employeeCount.trim();
        if(!employeeCount.matches("\\d+")){
            return false;
        }
        return true;
    }

    public boolean writeCityCondition(String cityName){
        cityName = cityName.trim();
        if(!cityName.matches("[a-zA-Z ]+")){
            return false;
        }
        return true;
    }

    public boolean writeCountryCondition(String countryName){
        countryName = countryName.trim();
        if(!countryName.matches("[a-zA-Z ]+")){
            return false;
        }
        return true;
    }

}
