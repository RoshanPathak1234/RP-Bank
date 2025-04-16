package com.RPBank.main.DAO;

import com.RPBank.main.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDAO extends JpaRepository<User,Long> {

    Boolean existsByPrimaryPhoneNumber(String phone);

    Boolean existsByPrimaryEmail(String email);

    Boolean existsByAccountDTO_AccountNumber(String accountNumber);

    Boolean existsByCustomerId(long customerId);

    Boolean existsByAccountDTO_AccountName(String name);

    Boolean existsByLoginCredentials_UserName(String username);


    Optional<User> findByPrimaryEmail(String email);

    List<User> findAllByAccountDTO_AccountName(String name);

    User findByAccountDTO_AccountNumber(String accountNumber);

    User findByLoginCredentials_UserName(String username);


}
