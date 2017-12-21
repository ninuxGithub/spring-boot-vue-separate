package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.bean.TreeNode;

@Repository
public interface TreeNodeRepository extends JpaRepository<TreeNode, String>{

}
