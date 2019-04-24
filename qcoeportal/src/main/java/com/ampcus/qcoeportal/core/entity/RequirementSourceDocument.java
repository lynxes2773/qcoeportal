package com.ampcus.qcoeportal.core.entity;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.Table(name="REQ_SRC_DOC")
public class RequirementSourceDocument implements Serializable
{
  @javax.persistence.Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="REQ_SRC_DOC_ID", updatable=false, nullable=false)
  private Integer reqSrcDocId;
  @javax.persistence.OneToOne
  @javax.persistence.PrimaryKeyJoinColumn
  private ProjectRelease release;
  @ManyToOne
  @JoinColumn(name="SRC_TYP_ID")
  private SourceType sourceType;
  @Column(name="SRC_DOC_TITLE", updatable=true, nullable=false)
  private String sourceDocTitle;
  
  public RequirementSourceDocument() {}
  
  public Integer getReqSrcDocId()
  {
    return reqSrcDocId;
  }
  
  public void setReqSrcDocId(Integer reqSrcDocId) { this.reqSrcDocId = reqSrcDocId; }
  



  public ProjectRelease getRelease()
  {
    return release;
  }
  
  public void setRelease(ProjectRelease release) { this.release = release; }
  



  public SourceType getSourceType()
  {
    return sourceType;
  }
  
  public void setSourceType(SourceType sourceType) { this.sourceType = sourceType; }
  



  public String getSourceDocTitle()
  {
    return sourceDocTitle;
  }
  
  public void setSourceDocTitle(String sourceDocTitle) { this.sourceDocTitle = sourceDocTitle; }
  

  @javax.persistence.OneToMany(targetEntity=RequirementSourceDocumentVersion.class, orphanRemoval=true, cascade={javax.persistence.CascadeType.DETACH}, fetch=FetchType.EAGER)
  @JoinColumn(name="REQ_SRC_DOC_ID")
  private Set<RequirementSourceDocumentVersion> reqSourceDocVersions = new LinkedHashSet();
  
  public Set<RequirementSourceDocumentVersion> getReqSourceDocVersions() { return reqSourceDocVersions; }
  
  public void setReqSourceDocVersions(Set<RequirementSourceDocumentVersion> reqSourceDocVersions) {
    this.reqSourceDocVersions = reqSourceDocVersions;
  }
}