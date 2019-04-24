package com.ampcus.qcoeportal.core.dao;

import com.ampcus.qcoeportal.core.entity.Client;
import com.ampcus.qcoeportal.core.entity.Project;
import com.ampcus.qcoeportal.core.entity.ProjectRelease;
import com.ampcus.qcoeportal.core.entity.Requirement;
import com.ampcus.qcoeportal.core.entity.RequirementSourceDocument;
import com.ampcus.qcoeportal.core.entity.RequirementSourceDocumentVersion;
import com.ampcus.qcoeportal.core.entity.SourceType;

import java.util.List;

public abstract interface RTMDao
{
  public abstract int testConnection();
  
  public abstract boolean saveNewRequirement(String paramString, byte[] paramArrayOfByte);
  
  public abstract Requirement getRequirement(Integer paramInteger);
  
  public abstract List getRequirementsForSrcDocVersion(Integer paramInteger);
  
  public abstract List<Client> getClients();
  
  public abstract List<Project> getProjects(Client client);
  
  public abstract List<ProjectRelease> getProjectReleases(Project project);
  
  public abstract List<RequirementSourceDocument> getRequirementSourceDocuments(ProjectRelease releases);
  
  public abstract List<RequirementSourceDocumentVersion> getRequirementSourceDocumentVersions(RequirementSourceDocument document);
  
  public abstract List<SourceType> getSourceTypes();
}
