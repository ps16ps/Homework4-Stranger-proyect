package com.ironhack.accountservice.repository;

import com.ironhack.accountservice.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    //The mean employeeCount
    @Query("SELECT AVG(a.employeeCount) FROM Account a")
    double AvgEmployeeCount();

    // The median employeeCount
    @Query("SELECT a.employeeCount FROM Account a ORDER BY a.employeeCount")
    List<Integer> MedEmployeeCount_firstStep();

    //The maximum employeeCount
    @Query("SELECT MAX(a.employeeCount) FROM Account a")
    int MaxEmployeeCount();

    //The minimum employeeCount
    @Query("SELECT MIN(a.employeeCount) FROM Account a")
    int MinEmployeeCount();
}
