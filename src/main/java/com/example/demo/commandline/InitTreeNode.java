package com.example.demo.commandline;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.example.demo.bean.TreeNode;
import com.example.demo.repository.TreeNodeRepository;
import com.xiaoleilu.hutool.CollectionUtil;

@Service
public class InitTreeNode implements CommandLineRunner {
	
	private AtomicInteger orderNum = new AtomicInteger(0);
	
	@Autowired
	private TreeNodeRepository treeNodeRepository;

	@Override
	public void run(String... args) throws Exception {
		List<TreeNode> levelOneList = treeNodeRepository.findAll();
		
		if(CollectionUtil.isEmpty(levelOneList)) {
			System.err.println("init Tree Node...");
			levelOneList = new LinkedList<>();
			List<TreeNode>  levelTwoList= new LinkedList<>();
			List<TreeNode>  levelThreeList = new LinkedList<>();
			
			String arr[] = new String[] {"管理层","销售层","开发层"};
			for (int i = 0; i < arr.length; i++) {
				TreeNode levelOne = new TreeNode(orderNum.getAndIncrement(), arr[i], null);
				for(int j=0; j<3; j++) {
					TreeNode levelTwo = new TreeNode(orderNum.getAndIncrement(), arr[i]+"-"+ j, levelOne);
					levelTwoList.add(levelTwo);
					
					if(j==0) {
						for(int k=0; k<2; k++) {
							TreeNode levelThree = new TreeNode(orderNum.getAndIncrement(), arr[i]+"-"+ j+"-"+k, levelTwo);
							levelThreeList.add(levelThree);
						}
					}
					levelTwo.setChildren(levelThreeList);
				}
				
				levelOne.setChildren(levelTwoList);
				levelOneList.add(levelOne);//第一层的树
			}
			
			
			try {
				List<TreeNode> save = treeNodeRepository.save(levelOneList);
				
				System.out.println(save);
				
				List<TreeNode> treeNodeList = treeNodeRepository.findAll();
				System.out.println(treeNodeList);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}

}
