package com.RPBank.main.DAO;

import com.RPBank.main.Beans.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDAO extends JpaRepository<User,Long> {

    Boolean existsByPrimaryPhoneNumber(String phone);

    Boolean existsByPrimaryEmail(String email);

    Boolean existsByAccountInfo_AccountNumber(String accountNumber);

    Boolean existsByCustomerId(long customerId);

    Boolean existsByAccountInfo_AccountName(String name);


    List<User> findAllByAccountInfo_AccountName(String name);

    User findByAccountInfo_AccountNumber(String accountNumber);


}
