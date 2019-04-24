package com.ampcus.qcoeportal.core.dao;

import com.ampcus.qcoeportal.core.entity.Requirement;
import java.util.List;

public abstract interface RTMDao
{
  public abstract int testConnection();
  
  public abstract boolean saveNewRequirement(String paramString, byte[] paramArrayOfByte);
  
  public abstract Requirement getRequirement(Integer paramInteger);
  
  public abstract List getRequirementsForSrcDocVersion(Integer paramInteger);
}
