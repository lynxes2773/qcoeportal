package com.ampcus.qcoeportal.core.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CLIENT")
public class Client implements Serializable
{
  private Integer clientId;
  private String name;
  
  public Client() {}
  
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="client_id", updatable=false, nullable=false)
  public Integer getClientId()
  {
    return clientId;
  }
  
  public void setClientId(Integer clientId) {
    this.clientId = clientId;
  }
  
  @Column(name="client_name", updatable=true, nullable=false)
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
}