# CRM (Customer Relationship Management) system 
## Project developed by:
- Alba Mesa Garrido
- Marga Pineda
- Anastasia Prischep Chulannikova
- Paula Sánchez Sánchez
- Raquel Martínez Jiménez

## What is CRM?

 CRMs are a tool that almost every sales team uses to track prospective and existing customers through the sales process.
 
 CRMs should allow us to:
 
 - Track Leads (people whose contact info we have gathered but who may or may not be interested in our product).
 
 - Convert Leads into Opportunities (leads are converted into opportunities when they show interest in buying the product).
 
 - Associate an Opportunity with an Account.
    
 - Associate Contacts with an Opportunity.

## IMPORTANT
- When building the services, the first one will be config-service and then eureka-service, the rest can be built in any order.

- To pass the tests you must not have any project up (neither the service you are testing nor the config-service).

- The first thing you need to create is a SalesRep, then you can create a Lead and convert it to get a Contact, an Opportunity and an Account.

- When converting a Lead you can decide to associate this convertion with an existing Account or create a new Account. This will be detailed in the next section

## How does the program work?

It is very easy, you can call any of these **commands**:

**NEW SALESREP** -> Add SALESREP to the CRM system with type POST and URL: http://localhost:8080/sales-rep  

&emsp; Body example: ```{"name":"Pepa"}```

**NEW LEAD** -> Add LEAD to the CRM system with type POST and URL: http://localhost:8080/leads 

&emsp; Body example: 
```
{
 "name": "Lia",
 "phoneNumber": "62656256",
 "email":"lia@gmail.com",
 "companyName": "aaaa",
 "salesRepId": 1
}
```

**SHOW SALESREP** ->  Display a list of all the SALESREP' id and name with type GET and URL: http://localhost:8080/sales-rep

**SHOW LEADS** -> Display a list of all the LEADS' id and name with type GET and URL: http://localhost:8080/leads

**SHOW CONTACTS** -> Display a list of all the CONTACTS' id and name with type GET and URL: http://localhost:8080/contacts

**SHOW OPPORTUNITIES** -> Display a list of all the OPPORTUNITIES' id and name of the decision maker with type GET and URL: http://localhost:8080/opportunities

**SHOW ACCOUNTS** -> Display a list of all the ACCOUNTS id and name of the first contact with type GET and URL: http://localhost:8080/accounts

**LOOKUP SALESREP (ID)** -> Display the selected SALESREP's details with the indicated Id Number with type GET andURL: http://localhost:8080/sales-rep/{id}

**LOOKUP LEAD (ID)** -> Display the selected LEAD's details with the indicated Id Number with type GET and URL: http://localhost:8080/leads/{id}

**LOOKUP ACCOUNT (ID)** -> Display the selected ACCOUNT's details with the indicated Id Number with type GET and URL: http://localhost:8080/accounts/{id}

**LOOKUP OPPORTUNITY (ID)** -> Display the selected OPPORTUNITY's details with the indicated Id Number with type GET and URL: http://localhost:8080/opportunities/{id}

**LOOKUP CONTACT (ID)** -> Display the selected CONTACT's details with the indicated Id Number with type GET and URL: http://localhost:8080/contacts/{id}

**CONVERT (ID)** -> Converts the selected LEAD in CONTACT, OPPORTUNITY and ACCOUNT and removes it from the system with type POST and URL: http://localhost:8080/convert/{id}

&emsp; As it was indicated before, you can associate this convertion to an existing account or you can create a new one. An example of the two bodies needed is shown in the following lines

&emsp; Body example with existing account:

```
{
 "product": "BOX",
 "quantity": 2,
 "accountId": 1
}
```

&emsp; Body example with new account:

```
{
 "product": "BOX",
 "quantity": 2,
 "industry": "MEDICAL",
 "employeeCount":12,
 "city":"Barcelona",
 "country":"Spain"
}
```

**CLOSE LOST (ID)** -> Changes the selected ACCOUNT status to CLOSE-LOST with type PATCH and URL: http://localhost:8080/opportunities/{id}/close-lost

**CLOSE WON (ID)** -> Changes the selected ACCOUNT status to CLOSE-WON with type PATCH and URL: http://localhost:8080/opportunities/{id}/close-won

**REPORT LEAD BY SALESREP** -> A count of Leads by SalesRep with type GET and URL: http://localhost:8080/sales-rep/{id}

**REPORT OPPORTUNITY BY SALESREP** -> A count of all Opportunities by SalesRep with type GET and URL: http://localhost:8080/opportunity-sales-rep

**REPORT CLOSED-WON BY SALESREP** -> A count of all CLOSED_WON Opportunities by SalesRep with type GET and URL: http://localhost:8080/opportunity-sales-rep/closed-won

**REPORT CLOSED-LOST BY SALESREP** -> A count of all CLOSED_LOST Opportunities by SalesRep with type GET and URL: http://localhost:8080/opportunity-sales-rep/closed-lost

**REPORT OPEN BY SALESREP** -> A count of all OPEN Opportunities by SalesRep with type GET and URL: http://localhost:8080/opportunity-sales-rep/open

**REPORT OPPORTUNITY BY THE PRODUCT** -> A count of all Opportunities by the product with type GET and URL: http://localhost:8080/opportunity-products

**REPORT CLOSED-WON BY THE PRODUCT** -> A count of all CLOSED_WON Opportunities with type GET and URL: http://localhost:8080/opportunity-products/closed-won

**REPORT CLOSED-LOST THE PRODUCT** -> A count of all CLOSED_LOST Opportunities with type GET and URL: http://localhost:8080/opportunity-products/closed-lost

**REPORT OPEN BY THE PRODUCT** -> A count of all OPEN Opportunities by the product with type GET and URL: http://localhost:8080/opportunity-products/open

**REPORT OPPORTUNITY BY COUNTRY** -> A count of all Opportunities by country with type GET and URL: http://localhost:8080/opportunities-country

**REPORT CLOSED-WON BY COUNTRY** -> A count of all CLOSED_WON Opportunities by country with type GET and URL: http://localhost:8080/opportunities-country/closed-won

**REPORT CLOSED-LOST BY COUNTRY** -> A count of all CLOSED_LOST Opportunities by country with type GET and URL: http://localhost:8080/opportunities-country/closed-lost

**REPORT OPEN BY COUNTRY** -> A count of all OPEN Opportunities with type GET and URL: http://localhost:8080/opportunities-country/open

**REPORT OPPORTUNITY BY CITY** -> A count of all Opportunities by the city with type GET and URL: http://localhost:8080/opportunities-city

**REPORT CLOSED-WON BY CITY** -> A count of all CLOSED_WON Opportunities by the city with type GET and URL: http://localhost:8080/opportunities-city/closed-won

**REPORT CLOSED-LOST BY CITY** -> A count of all CLOSED_LOST Opportunities by the city with type GET and URL: http://localhost:8080/opportunities-city/closed-lost

**REPORT OPEN BY CITY** -> A count of all OPEN Opportunities by the city with type GET and URL: http://localhost:8080/opportunities-city/open

**REPORT OPPORTUNITY BY INDUSTRY** -> A count of all Opportunities by industry with type GET and URL: http://localhost:8080/opportunities-industry

**REPORT CLOSED-WON BY INDUSTRY** -> A count of all CLOSED_WON Opportunities by industry with type GET and URL: http://localhost:8080/opportunities-industry/closed-won

**REPORT CLOSED-LOST BY INDUSTRY** -> A count of all CLOSED_LOST Opportunities by industry with type GET and URL: http://localhost:8080/opportunities-industry/closed-lost

**REPORT OPEN BY INDUSTRY** -> A count of all OPEN Opportunities by industry with type GET and URL: http://localhost:8080/opportunities-industry/open

**MEAN EMPLOYEECOUNT** -> The mean employeeCount with type GET and URL: http://localhost:8080/account-employee/avg

**MEDIAN EMPLOYEECOUNT** -> The median employeeCount with type GET and URL: http://localhost:8080/account-employee/med

**MAX EMPLOYEECOUNT** -> The max employeeCount with type GET and URL: http://localhost:8080/account-employee/max

**MIN EMPLOYEECOUNT** -> The min employeeCount with type GET and URL: http://localhost:8080/account-employee/min

**MEAN QUANTITY** -> The mean quantity of products order with type GET and URL: http://localhost:8080/opportunity-quantity/avg

**MEDIAN QUANTITY** -> The median quantity of products order with type GET and URL: http://localhost:8080/opportunity-quantity/med

**MAX QUANTITY** -> The max quantity of products order with type GET and URL: http://localhost:8080/opportunity-quantity/max

**MIN QUANTITY** -> The min quantity of products order with type GET and URL: http://localhost:8080/opportunity-quantity/min

**MEAN OPPS PER ACCOUNT** -> The mean number of Opportunities associated with an Account with type GET and URL: http://localhost:8080/opportunity-account/avg

**MEDIAN OPPS PER ACCOUNT** -> The median number of Opportunities associated with an Account with type GET and URL: http://localhost:8080/opportunity-account/med

**MAX OPPS PER ACCOUNT** -> The maximum number of Opportunities associated with an Account with type GET and URL: http://localhost:8080/opportunity-account/max

**MIN OPPS PER ACCOUNT** -> The minimum number of Opportunities associated with an Account with type GET and URL: http://localhost:8080/opportunity-account/min
