package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.bean.Persons;

@Repository
public interface PersonsRepository extends JpaRepository<Persons, Long>, JpaSpecificationExecutor<Persons> {

	@Query(value = "select DISTINCT sex from Persons p")
	List<Persons> findSex();

	Page<Persons> findAll(Pageable pageable);

	Page<Persons> findBySexAndEmailContains(String sexName, String emailName, Pageable pageable);

	Page<Persons> findBySex(String sexName, Pageable pageable);

	Persons findById(Long id);

}
