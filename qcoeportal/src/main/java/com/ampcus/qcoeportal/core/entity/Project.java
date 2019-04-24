package com.ampcus.qcoeportal.core.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@javax.persistence.Table(name="PROJECT")
public class Project implements Serializable
{
  private Integer projectId;
  private Client client;
  private String projectName;
  private Date startDate;
  private Date endDate;
  
  public Project() {}
  
  @javax.persistence.Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="PROJECT_ID", updatable=false, nullable=false)
  public Integer getProjectId()
  {
    return projectId;
  }
  
  public void setProjectId(Integer projectId) { this.projectId = projectId; }
  

  @ManyToOne
  @JoinColumn(name="CLIENT_ID")
  public Client getClient()
  {
    return client;
  }
  
  public void setClient(Client client) { this.client = client; }
  

  @Column(name="PROJECT_NAME", updatable=true, nullable=false)
  public String getProjectName()
  {
    return projectName;
  }
  
  public void setProjectName(String projectName) { this.projectName = projectName; }
  

  @Column(name="START_DT", updatable=true, nullable=false)
  public Date getStartDate()
  {
    return startDate;
  }
  
  public void setStartDate(Date startDate) { this.startDate = startDate; }
  

  @Column(name="END_DT", updatable=true, nullable=false)
  public Date getEndDate()
  {
    return endDate;
  }
  
  public void setEndDate(Date endDate) { this.endDate = endDate; }
  

  private Set<ProjectRelease> projectReleases = new LinkedHashSet();
  
  @OneToMany(targetEntity=ProjectRelease.class, orphanRemoval=true, cascade={javax.persistence.CascadeType.DETACH}, fetch=FetchType.EAGER)
  @JoinColumn(name="PROJECT_ID")
  public Set<ProjectRelease> getProjectReleases() { return projectReleases; }
  
  public void setProjectReleases(Set<ProjectRelease> projectReleases) {
    this.projectReleases = projectReleases;
  }
}