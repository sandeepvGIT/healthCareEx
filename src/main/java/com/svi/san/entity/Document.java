package com.svi.san.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="doc_tab")
@Data
public class Document {
	
	@Id
	@Column(name="id")
	private Long id;
	@Column(name="doc_name_col")
	private String docName;
	@Lob
	@Column(name="doc_data_col")
	private byte[] docData;

}
