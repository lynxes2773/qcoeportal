package com.ampcus.qcoeportal.usecase;

import com.ampcus.qcoeportal.core.dao.RTMDao;
import com.ampcus.qcoeportal.core.entity.Client;
import com.ampcus.qcoeportal.core.entity.Project;
import com.ampcus.qcoeportal.core.entity.ProjectRelease;
import com.ampcus.qcoeportal.core.entity.Requirement;
import com.ampcus.qcoeportal.core.entity.RequirementSourceDocument;
import com.ampcus.qcoeportal.core.entity.RequirementSourceDocumentVersion;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Component("rtmProcessRequirementService")
@Service
@Scope("request")
public class ProcessRequirement implements Serializable
{
	
	private static String CLIENT_ID = "CLIENT_ID";
	private static String CLIENT_NAME = "CLIENT_NAME";
	private static String PROJECT_ID = "PROJECT_ID";
	private static String PROJECt_NAME = "PROJECt_NAME";
	
	private static String PROJECT_START_DATE = "PROJECT_START_DATE";
	private static String PROJECT_END_DATE = "PROJECT_END_DATE";
	private static String RELEASE_ID = "RELEASE_ID";
	private static String RELEASE_NUMBER = "RELEASE_NUMBER";
	private static String RELEASE_TITLE = "RELEASE_TITLE";
	private static String SRC_DOC_ID = "SRC_DOC_ID";
	private static String SRC_DOC_TITLE = "SRC_DOC_TITLE";
	private static String SRC_DOC_TYPE_ID = "SRC_DOC_TYPE_ID";
	private static String SRC_DOC_TYPE_NAME = "SRC_DOC_TYPE_NAME";
	private static String SRC_DOC_VER_ID = "SRC_DOC_VER_ID";
	private static String SRC_DOC_VER_NUM = "SRC_DOC_VER_NUM";
	private static String SRC_DOC_VER_FILENAME = "SRC_DOC_VER_FILENAME";
	
	
  @Autowired
  private HashMap appConfigMap;
  private RTMDao rtmDao;
  
  @Autowired
  public void setRtmDao(RTMDao rtmDao)
  {
    this.rtmDao = rtmDao;
  }
  
  @Autowired
  public void setAppConfigMap(HashMap appConfigMap) {
    this.appConfigMap = appConfigMap;
  }
  

  public ProcessRequirement() {}
  

  public boolean saveNewRequirement(String title, BufferedImage image)
  {
    boolean result = false;
    try
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ImageIO.write(image, "jpg", baos);
      baos.flush();
      byte[] imageInBytes = baos.toByteArray();
      result = rtmDao.saveNewRequirement(title, imageInBytes);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return result;
  }
  
  @Transactional
  public List getAllRequirements(Integer reqSrcDocVerId)
  {
    List requirements = rtmDao.getRequirementsForSrcDocVersion(reqSrcDocVerId);
    return requirements;
  }
  
  @Transactional
  public Requirement getRequirement(Integer requirementId)
  {
    Requirement requirement = rtmDao.getRequirement(requirementId);
    return requirement;
  }
  
  public List<Map> getAllClients()
  {
	  List<Map> clientsData = new ArrayList();
	  
	  List<Client> clientsList = rtmDao.getClients();
	  
	  for(int i=0;i<clientsList.size();i++)
	  {
		  Map client = new HashMap();
		  Client instance = clientsList.get(i);
		  client.put(appConfigMap.get(this.CLIENT_ID), instance.getClientId());
		  client.put(this.CLIENT_NAME, instance.getName());
		  clientsData.add(client);
	  }
	  
	  return clientsData;
  }
  
  public List<Map> getProjectsForClient(String clientId)
  {
	  List<Map> projects = new ArrayList();
	  
	  List<Project> projectsList = rtmDao.getProjects(clientId);
	  for(int i=0; i<projectsList.size();i++)
	  {
		  Map project = new HashMap();
		  Project instance = projectsList.get(i);
		  project.put(this.PROJECT_ID, instance.getProjectId());
		  project.put(this.PROJECt_NAME, instance.getProjectName());
		  project.put(this.PROJECT_START_DATE, instance.getStartDate());
		  project.put(this.PROJECT_END_DATE, instance.getEndDate());
		  projects.add(project);
	  }
	  
	  return projects;
  }
  
  
  public List<Map> getReleasesForProject(String projectId)
  {
	  List<Map> releases = new ArrayList();
	  
	  List<ProjectRelease> releaseList = rtmDao.getProjectReleases(projectId);
	  for(int i=0;i<releaseList.size();i++)
	  {
		  Map release = new HashMap();
		  ProjectRelease instance = releaseList.get(i);
		  release.put(this.RELEASE_ID, instance.getReleaseId());
		  release.put(this.RELEASE_NUMBER, instance.getCustomerReleaseNumber());
		  release.put(this.RELEASE_TITLE, instance.getReleaseTitle());
		  releases.add(release);
	  }
	  return releases;
  }
  
  public List<Map> getSourceDocuments(String releaseId)
  {
	  List<Map> documents = new ArrayList();
	  
	  List<RequirementSourceDocument> documentList = rtmDao.getRequirementSourceDocuments(releaseId);
	  for(int i=0;i<documentList.size();i++)
	  {
		  Map document = new HashMap();
		  RequirementSourceDocument instance = documentList.get(i);
		  document.put(this.SRC_DOC_ID, instance.getReqSrcDocId());
		  document.put(this.SRC_DOC_TITLE, instance.getSourceDocTitle());
		  document.put(this.SRC_DOC_TYPE_ID, instance.getSourceType().getSourceTypeId());
		  document.put(this.SRC_DOC_TYPE_NAME, instance.getSourceType().getSourceTypeName());
	  }
	  
	  return documents;
  }
  
  public List<Map> getSourceDocumentVersions(String documentId)
  {
	  List<Map> documentVersions = new ArrayList();
	  
	  List<RequirementSourceDocumentVersion> versionsList = rtmDao.getRequirementSourceDocumentVersions(documentId);
	  for(int i=0;i<versionsList.size();i++)
	  {
		  Map documentVersion = new HashMap();
		  RequirementSourceDocumentVersion instance = versionsList.get(i);
		  documentVersion.put(this.SRC_DOC_VER_ID, instance.getReqSrcDocVerId());
		  documentVersion.put(this.SRC_DOC_VER_NUM, instance.getVersionNumber());
		  documentVersion.put(this.SRC_DOC_VER_FILENAME, instance.getFileName());
	  }
	  
	  return documentVersions;
  }
}
