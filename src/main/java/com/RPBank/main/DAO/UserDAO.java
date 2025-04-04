package com.RPBank.main.DAO;

import com.RPBank.main.Beans.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDAO extends JpaRepository<User,Long> {

    Boolean existsByPrimaryPhoneNumber(String phone);

    Boolean existsByPrimaryEmail(String email);

    Boolean existsByAccountDTO_AccountNumber(String accountNumber);

    Boolean existsByCustomerId(long customerId);

    Boolean existsByAccountDTO_AccountName(String name);


    List<User> findAllByAccountDTO_AccountName(String name);

    User findByAccountDTO_AccountNumber(String accountNumber);


}
