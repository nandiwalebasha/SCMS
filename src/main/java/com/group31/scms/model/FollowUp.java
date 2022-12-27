package com.group31.scms.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class FollowUp implements Comparable<FollowUp> {

	@Id
	@GeneratedValue(generator = "followup-sequence-generator")
	@GenericGenerator(name = "followup-sequence-generator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@Parameter(name = "sequence_name", value = "followup_sequence"),
			@Parameter(name = "initial_value", value = "1000"), @Parameter(name = "increment_size", value = "1") })
	private Long followUpID;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date followUpDate;

	private Long sysCasePaperId;
	private String complaint;
	private String diagnosis;
	private String medicine;
	private Integer weight;
	private String mind;
	private String head;
	private String hairs;
	private String sleep;
	private String dreams;
	private String eyes;
	private String ears;
	private String nose;
	private String mouth;
	private String back;
	private String urine;
	private String stool;
	private String extremities;
	private String skin;
	private String thermal;
	private String throat;
	private String chest;
	private String stomach;
	private String bloodReport;

	@CreationTimestamp
	private Date createdOn;

	@UpdateTimestamp
	private Date updatedOn;

	public Long getFollowUpID() {
		return followUpID;
	}

	public void setFollowUpID(Long followUpID) {
		this.followUpID = followUpID;
	}

	public Date getFollowUpDate() {
		return followUpDate;
	}

	public void setFollowUpDate(Date followUpDate) {
		this.followUpDate = followUpDate;
	}

	public String getComplaint() {
		return complaint != null ? complaint: "";
	}

	public void setComplaint(String complaint) {
		this.complaint = complaint;
	}

	public String getDiagnosis() {
		return diagnosis!=null ? diagnosis:"";
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public String getMedicine() {
		return medicine !=null ? medicine:"";
	}

	public void setMedicine(String medicine) {
		this.medicine = medicine;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getMind() {
		return mind !=null ? mind:"";
	}

	public void setMind(String mind) {
		this.mind = mind;
	}

	public String getHead() {
		return head !=null ? head:"";
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getHairs() {
		return hairs !=null ? hairs:"";
	}

	public void setHairs(String hairs) {
		this.hairs = hairs;
	}

	public String getSleep() {
		return sleep !=null ? sleep:"";
	}

	public void setSleep(String sleep) {
		this.sleep = sleep;
	}

	public String getDreams() {
		return dreams !=null ? dreams:"";
	}

	public void setDreams(String dreams) {
		this.dreams = dreams;
	}

	public String getEyes() {
		return eyes !=null ? eyes:"";
	}

	public void setEyes(String eyes) {
		this.eyes = eyes;
	}

	public String getEars() {
		return ears !=null ? ears:"";
	}

	public void setEars(String ears) {
		this.ears = ears;
	}

	public String getNose() {
		return nose !=null ? nose:"";
	}

	public void setNose(String nose) {
		this.nose = nose;
	}

	public String getMouth() {
		return mouth !=null ? mouth:"";
	}

	public void setMouth(String mouth) {
		this.mouth = mouth;
	}

	public String getBack() {
		return back !=null ? back:"";
	}

	public void setBack(String back) {
		this.back = back;
	}

	public String getUrine() {
		return urine !=null ? urine:"";
	}

	public void setUrine(String urine) {
		this.urine = urine;
	}

	public String getStool() {
		return stool !=null ? stool:"";
	}

	public void setStool(String stool) {
		this.stool = stool;
	}

	public String getExtremities() {
		return extremities !=null ? extremities:"";
	}

	public void setExtremities(String extremities) {
		this.extremities = extremities;
	}

	public String getSkin() {
		return skin !=null ? skin:"";
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}

	public String getThermal() {
		return thermal !=null ? thermal:"";
	}

	public void setThermal(String thermal) {
		this.thermal = thermal;
	}

	public String getThroat() {
		return throat !=null ? throat:"";
	}

	public void setThroat(String throat) {
		this.throat = throat;
	}

	public String getChest() {
		return chest !=null ? chest:"";
	}

	public void setChest(String chest) {
		this.chest = chest;
	}

	public String getStomach() {
		return stomach !=null ? stomach:"";
	}

	public void setStomach(String stomach) {
		this.stomach = stomach;
	}

	public String getBloodReport() {
		return bloodReport !=null ? bloodReport:"";
	}

	public void setBloodReport(String bloodReport) {
		this.bloodReport = bloodReport;
	}

	public Long getSysCasePaperId() {
		return sysCasePaperId;
	}

	public void setSysCasePaperId(Long sysCasePaperId) {
		this.sysCasePaperId = sysCasePaperId;
	}



	/**
	 * @return the createdOn
	 */
	public Date getCreatedOn() {
		return createdOn;
	}

	/**
	 * @param createdOn the createdOn to set
	 */
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * @return the updatedOn
	 */
	public Date getUpdatedOn() {
		return updatedOn;
	}

	/**
	 * @param updatedOn the updatedOn to set
	 */
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Override
	public int compareTo(FollowUp o) {
		return this.createdOn.compareTo(o.createdOn);
	}

	@Override
	public String toString() {
		return "FollowUp [followUpID=" + followUpID + ", followUpDate=" + followUpDate + ", complaints=" + complaint
				+ ", diagnosis=" + diagnosis + ", medicine=" + medicine + ", weight=" + weight + ", mind=" + mind
				+ ", head=" + head + ", hairs=" + hairs + ", sleep=" + sleep + ", dreams=" + dreams + ", eyes=" + eyes
				+ ", ears=" + ears + ", nose=" + nose + ", mouth=" + mouth + ", back=" + back + ", urine=" + urine
				+ ", stool=" + stool + ", extremities=" + extremities + ", skin=" + skin + ", thermal=" + thermal
				+ ", throat=" + throat + ", chest=" + chest + ", stomach=" + stomach + ", bloodReport=" + bloodReport
				+ "]";
	}

}