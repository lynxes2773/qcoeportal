package com.ampcus.qcoeportal.core.entity;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="SOURCE_TYP_MSTR")
public class SourceType
  implements Serializable
{
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="SRC_TYP_ID", updatable=false, nullable=false)
  private Integer sourceTypeId;
  @Column(name="SRC_TYP_NM", updatable=true, nullable=false)
  private String sourceTypeName;
  
  public SourceType() {}
  
  public Integer getSourceTypeId()
  {
    return sourceTypeId;
  }
  
  public void setSourceTypeId(Integer sourceTypeId) { this.sourceTypeId = sourceTypeId; }
  


  public String getSourceTypeName()
  {
    return sourceTypeName;
  }
  
  public void setSourceTypeName(String sourceTypeName) { this.sourceTypeName = sourceTypeName; }
  

  @OneToMany(mappedBy="sourceType", orphanRemoval=true, cascade={javax.persistence.CascadeType.DETACH}, fetch=FetchType.EAGER)
  private Set<RequirementSourceDocument> reqSrcDocs = new LinkedHashSet();
  
  public Set<RequirementSourceDocument> getReqSrcDocs() { return reqSrcDocs; }
  
  public void setReqSrcDocs(Set<RequirementSourceDocument> reqSrcDocs) {
    this.reqSrcDocs = reqSrcDocs;
  }
}
