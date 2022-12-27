package com.group31.scms.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "case_paper")
public class CasePaper {

	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(generator = "case-paper-sequence-generator")
	@GenericGenerator(name = "case-paper-sequence-generator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@Parameter(name = "sequence_name", value = "case_paper_sequence"),
			@Parameter(name = "initial_value", value = "1000"), @Parameter(name = "increment_size", value = "1") })
	private Long sysCasePaperId;

	@Basic(optional = false)
	@Column(unique=true)
	private String casePaperNumber;
	
	@Basic(optional = false)
	private String firstName;
	
	@Basic(optional = false)
	private String lastName;
	
	private String gender;
	private String occupation;
	private Integer age;
	private Long contactNo;
	private String address;

	@CreationTimestamp
	private Date createdOn;

	@UpdateTimestamp
	private Date updatedOn;

//	@OneToMany(mappedBy = "casePaper", cascade = CascadeType.ALL, orphanRemoval = true)
//	private Set<FollowUp> followUps;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "sysCasePaperId")
	@OrderBy(value = "createdOn DESC")
	private List<FollowUp> followUps;

	public String getCasePaperNumber() {
		return casePaperNumber!= null ? casePaperNumber:"";
	}

	public void setCasePaperNumber(String casePaperNumber) {
		this.casePaperNumber = casePaperNumber;
	}

	public Long getSysCasePaperId() {
		return sysCasePaperId;
	}

	public void setSysCasePaperId(Long sysCasePaperId) {
		this.sysCasePaperId = sysCasePaperId;
	}

	public String getFirstName() {
		return firstName!=null ? firstName : "";
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName != null ? lastName:"";
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender!=null ? gender:"";
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getOccupation() {
		return occupation != null ? occupation:"";
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Long getContactNo() {
		return contactNo;
	}

	public void setContactNo(Long contactNo) {
		this.contactNo = contactNo;
	}

	public String getAddress() {
		return address !=null ? address:"";
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public List<FollowUp> getFollowUps() {
		return followUps;
	}

	public void setFollowUps(List<FollowUp> followUps) {
		this.followUps = followUps;
	}

	public void mergeFollowup(CasePaper casePaper) {
		this.setCasePaperNumber(casePaper.getCasePaperNumber());
		this.setCreatedOn(casePaper.getCreatedOn());

		if(this.getFollowUps() == null) {
			this.setFollowUps(casePaper.getFollowUps());
		}else {
			this.getFollowUps().addAll(casePaper.getFollowUps());
		}

	}

	@Override
	public String toString() {
		return "CasePaper [sysCasePaperId=" + sysCasePaperId + ", casePaperNumber=" + casePaperNumber + ", firstName="
				+ firstName + ", lastName=" + lastName + ", gender=" + gender + ", occupation=" + occupation + ", age="
				+ age + ", contactNo=" + contactNo + ", address=" + address + ", createdOn=" + createdOn
				+ ", updatedOn=" + updatedOn + ", followUps=" + followUps + "]";
	}
	
}