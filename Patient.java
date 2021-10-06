package com.rk.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="patient_tab")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="pat_first_name_col")
	private String firstName;
	
	@Column(name="pat_last_name_col")
	private String lastName;
	
	@Column(name="pat_gen_col")
	private String gender;
	
	@Column(name="pat_mob_col")
	private String mobile;
	
	@Column(name="pat_dob_col")
	@DateTimeFormat(iso= ISO.DATE)
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;
	
	@Column(name="pat_ms_col")
	private String marialStatus;
	
	@Column(name="pat_paddr_col")
	private String presentAddress;
	
	@Column(name="patcaddr_col")
	private String commAddress;
	
	@ElementCollection
	@CollectionTable(
					name="pat_medi_hist_tab",
					joinColumns= @JoinColumn(name="pat_medi_hst_id_fk_col")
					)
	@Column(name="pat_medi_hist_col")
	private Set<String> mediHistory;
	
	@Column(name="pat_other_col")
	private String ifOther;
	
	@Column(name="pat_note_col")
	private String note;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getMarialStatus() {
		return marialStatus;
	}

	public void setMarialStatus(String marialStatus) {
		this.marialStatus = marialStatus;
	}

	public String getPresentAddress() {
		return presentAddress;
	}

	public void setPresentAddress(String presentAddress) {
		this.presentAddress = presentAddress;
	}

	public String getCommAddress() {
		return commAddress;
	}

	public void setCommAddress(String commAddress) {
		this.commAddress = commAddress;
	}

	public Set<String> getMediHistory() {
		return mediHistory;
	}

	public void setMediHistory(Set<String> mediHistory) {
		this.mediHistory = mediHistory;
	}

	public String getIfOther() {
		return ifOther;
	}

	public void setIfOther(String ifOther) {
		this.ifOther = ifOther;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}	

}
