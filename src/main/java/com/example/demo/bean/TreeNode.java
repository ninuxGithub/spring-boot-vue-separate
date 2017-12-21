package com.example.demo.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

/**
 * 构建树形结构
 * 
 * JAP 对象自己引用自己的Demo
 *
 */

@Entity
@Table(name = "tree_node")
public class TreeNode implements Serializable {

	private static final long serialVersionUID = 6685482681822159077L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid") // 这个是hibernate的注解
	@GeneratedValue(generator = "idGenerator") // 使用uuid的生成策略
	private String id;

	@Column(name="tree_order")
	private Integer treeOrder;

	private String label;

	@OneToMany(fetch = FetchType.EAGER,targetEntity = TreeNode.class, cascade = { CascadeType.ALL }, mappedBy = "parent")
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("tree_order")
	private List<TreeNode> children;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pid")
	private TreeNode parent;

	public TreeNode() {
	}

	public TreeNode(Integer treeOrder, String label, TreeNode parent) {
		this.treeOrder = treeOrder;
		this.label = label;
		this.parent = parent;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public TreeNode getParent() {
		return parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}
	
	


	public Integer getTreeOrder() {
		return treeOrder;
	}

	public void setTreeOrder(Integer treeOrder) {
		this.treeOrder = treeOrder;
	}

	

	@Override
	public String toString() {
//		System.out.println(this.id);
//		System.out.println(this.treeOrder);
//		System.out.println(this.label);
//		System.out.println(this.children);
		return "{id=" + id + ", treeOrder=" + treeOrder + ", label=" + label + ", children=" + children+ "}";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((children == null) ? 0 : children.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((treeOrder == null) ? 0 : treeOrder.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TreeNode other = (TreeNode) obj;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (treeOrder == null) {
			if (other.treeOrder != null)
				return false;
		} else if (!treeOrder.equals(other.treeOrder))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		return true;
	}

}
