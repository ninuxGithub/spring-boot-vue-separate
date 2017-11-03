package com.example.demo.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student")
public class Student implements Serializable {

	public Student(String id, String name, String number, Double score) {
		this.id=id;
		this.name=name;
		this.number=number;
		this.score=score;
				
	}
	private static final long serialVersionUID = 2281693583701708108L;
	/** 学生ID */
	@Id
	private String id;
	/** 学生姓名 */
	private String name;
	/** 序号 */
	private String number;
	/** 分数 */
	private Double score;
	/** 创建时间 */
	@Column(name="create_time")
	private Date createTime;
	/** 爱好 */
	private String hobby;
	/**是否激活*/
	private Integer active;
	

}
