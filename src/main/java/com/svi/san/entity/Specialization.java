package com.svi.san.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "specialization_tab")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Specialization {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "spec_id_col")
	private Long id;
	@Column(name = "spec_code_col",length = 12,nullable = false,unique = true)
	private String specCode;
	@Column(name = "spec_name_col",length = 60,  unique = true,nullable = false)
	private String specName;
	@Column(name = "spec_note_col",length = 250,unique = true,nullable = false)
	private String specNote;

}
