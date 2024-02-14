package com.example.ValentineBot.Repository;

import com.example.ValentineBot.Status;
import com.example.ValentineBot.Entity.Valentine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;


@Repository
public interface ValentineRepository extends JpaRepository<Valentine, Integer> {


    Optional<Valentine> findById(Integer id);
    Valentine findValentineById(Integer id);


    Valentine save(Valentine valentine);

    ArrayList<Valentine> findAllBySender(String sender);

    Valentine findValentineByStatusAndOwner(Status status, String owner);
}
