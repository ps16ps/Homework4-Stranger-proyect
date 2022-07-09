package com.ironhack.Homework3.creatingObjects;

import com.ironhack.Homework3.Repository.OpportunityRepository;
import com.ironhack.Homework3.enums.Product;
import com.ironhack.Homework3.model.*;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
public class CreatingOpportunity {
    Scanner scanner = new Scanner(System.in);
    @Autowired
    private OpportunityRepository opportunityRepository;

    //CREATION METHODS
    public Opportunity creatingOpportunity(Contact contact, Lead lead, Account account){
        Opportunity opportunity = new Opportunity(selectProduct(), writeQuantity(), contact,
                lead.getSalesRep(), account);
//        for (Opportunity opportunity1 : opportunityRepository.findAll()){
//            if (opportunity1.equals(opportunity)){
//                System.out.println("This OPPORTUNITY has been created before");
//                return opportunity;
//            }
//        }
        opportunityRepository.save(opportunity);
        System.out.println("New OPPORTUNITY created with id " + opportunity.getId());
        return opportunity;
    }

    //READ INPUT METHODS
    public Product selectProduct() {
        System.out.println("Please write the product type: HYBRID, FLATBED, or BOX");
        scanner = new Scanner(System.in);
        String product = scanner.nextLine();
        product = product.trim().toUpperCase();
        while (!selectProductBoolean(product)){
            System.out.println("Please write a correct product type");
            product = scanner.nextLine();
            product = product.trim().toUpperCase();
        }
        return Product.valueOf(product);
    }

    public int writeQuantity() {
        System.out.println("Please write the number of trucks being considered for purchase");
        scanner = new Scanner(System.in);
        String quantity = scanner.nextLine();
        quantity = quantity.trim();
        while (!writeQuantityBoolean(quantity)){
            System.out.println("Please write a correct quantity");
            quantity = scanner.nextLine();
        }
        return Integer.parseInt(quantity);
    }

    //VALIDATE INPUT METHODS
    public boolean selectProductBoolean(String product){
        product = product.trim().toUpperCase();
        if (!product.equals("HYBRID") && !product.equals("FLATBED") && !product.equals("BOX")){
            return false;
        }
        return true;
    }

    public boolean writeQuantityBoolean(String quantity){
        quantity = quantity.trim();
        if (!quantity.matches("\\d+")){
            return false;
        }
        return true;
    }
}
