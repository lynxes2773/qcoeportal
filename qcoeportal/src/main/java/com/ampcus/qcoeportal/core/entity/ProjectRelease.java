package com.ampcus.qcoeportal.core.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="PROJECT_RELEASE")
public class ProjectRelease
  implements Serializable
{
  private Integer releaseId;
  private ProjectRelease parentRelease;
  
  public ProjectRelease() {}
  
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="RELEASE_ID", updatable=false, nullable=false)
  public Integer getReleaseId()
  {
    return releaseId;
  }
  
  public void setReleaseId(Integer releaseId) { this.releaseId = releaseId; }
  

  @ManyToOne(cascade={javax.persistence.CascadeType.ALL})
  @JoinColumn(name="parent_release_id")
  public ProjectRelease getParentRelease()
  {
    return parentRelease;
  }
  
  public void setParentRelease(ProjectRelease parentRelease) { this.parentRelease = parentRelease; }
  

  private Set<ProjectRelease> childReleases = new HashSet();
  
  @OneToMany(mappedBy="parentRelease", fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.ALL}, orphanRemoval=true)
  public Set<ProjectRelease> getChildReleases() { return childReleases; }
  
  public void setChildReleases(Set<ProjectRelease> childReleases) {
    this.childReleases = childReleases;
  }
  
  private Project project;
  private String customerReleaseNumber;
  @ManyToOne
  @JoinColumn(name="PROJECT_ID")
  public Project getProject() { return project; }
  

  public void setProject(Project project) { this.project = project; }
  
  private String releaseTitle;
  private RequirementSourceDocument reqSrcDoc;
  @Column(name="CUST_RELEASE_NUM", updatable=true, nullable=false)
  public String getCustomerReleaseNumber() {
    return customerReleaseNumber;
  }
  
  public void setCustomerReleaseNumber(String customerReleaseNumber) { this.customerReleaseNumber = customerReleaseNumber; }
  

  @Column(name="RELEASE_TITLE", updatable=true, nullable=false)
  public String getReleaseTitle()
  {
    return releaseTitle;
  }
  
  public void setReleaseTitle(String releaseTitle) { this.releaseTitle = releaseTitle; }
  

  @OneToOne(mappedBy="release", cascade={javax.persistence.CascadeType.ALL})
  public RequirementSourceDocument getReqSrcDoc()
  {
    return reqSrcDoc;
  }
  
  public void setReqSrcDoc(RequirementSourceDocument reqSrcDoc) { this.reqSrcDoc = reqSrcDoc; }
}