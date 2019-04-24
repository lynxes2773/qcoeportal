package com.ampcus.qcoeportal.core.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@javax.persistence.Entity
@javax.persistence.Table(name="REQUIREMENT")
public class Requirement implements java.io.Serializable
{
  @javax.persistence.Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="REQ_ID", updatable=false, nullable=false)
  private Integer requirementId;
  @javax.persistence.ManyToOne
  @javax.persistence.JoinColumn(name="REQ_SRC_DOC_VER_ID")
  private RequirementSourceDocumentVersion reqSrcDocVersion;
  @Column(name="REQ_TXT_CONTENT", updatable=true, nullable=false)
  private String requirementText;
  @Column(name="REQ_IMG_CONTENT", updatable=true, nullable=false)
  private byte[] reqImageContent;
  
  public Requirement() {}
  
  public Integer getRequirementId()
  {
    return requirementId;
  }
  
  public void setRequirementId(Integer requirementId) { this.requirementId = requirementId; }
  



  public RequirementSourceDocumentVersion getReqSrcDocVersion()
  {
    return reqSrcDocVersion;
  }
  
  public void setReqSrcDocVersion(RequirementSourceDocumentVersion reqSrcDocVersion) { this.reqSrcDocVersion = reqSrcDocVersion; }
  


  public String getRequirementText()
  {
    return requirementText;
  }
  
  public void setRequirementText(String requirementText) { this.requirementText = requirementText; }
  


  public byte[] getReqImageContent()
  {
    return reqImageContent;
  }
  
  public void setReqImageContent(byte[] reqImageContent) { this.reqImageContent = reqImageContent; }
}
