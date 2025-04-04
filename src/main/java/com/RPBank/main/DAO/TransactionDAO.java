package com.RPBank.main.DAO;

import com.RPBank.main.Beans.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDAO extends JpaRepository<Transaction , String> {
}
