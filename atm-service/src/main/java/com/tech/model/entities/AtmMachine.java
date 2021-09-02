package com.tech.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "atm_machine")
public class AtmMachine {

	@Id
	private String type;

	@Column(nullable = false)
	private Long count;

	@Column(nullable = false)
	private Long value;

	public AtmMachine() {

	}

	public AtmMachine(String type, Long count, Long value) {
		this.type = type;
		this.count = count;
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

}
