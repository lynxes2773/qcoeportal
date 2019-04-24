package com.ampcus.qcoeportal.core.dao;

import com.ampcus.qcoeportal.core.entity.Client;
import com.ampcus.qcoeportal.core.entity.Project;
import com.ampcus.qcoeportal.core.entity.ProjectRelease;
import com.ampcus.qcoeportal.core.entity.Requirement;
import com.ampcus.qcoeportal.core.entity.RequirementSourceDocument;
import com.ampcus.qcoeportal.core.entity.RequirementSourceDocumentVersion;
import com.ampcus.qcoeportal.core.entity.SourceType;

import java.io.PrintStream;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository("rtmDAOProvider")
@Transactional
public class RTMDaoHibernateImpl
  implements RTMDao
{
  private SessionFactory sessionFactory;
  private JdbcTemplate jdbcTemplate;
  
  public RTMDaoHibernateImpl() {}
  
  public int testConnection()
  {
    int result = 0;
    Session session = sessionFactory.openSession();
    Transaction tx = null;
    try
    {
      tx = session.getTransaction();
      tx.begin();
      Query query = session.createQuery("from Client");
      List<Client> resultList = query.getResultList();
      Client client = (Client)resultList.get(0);
      System.out.println("client name: " + client.getName());
      result = query.getResultList().size();
      tx.commit();
    }
    catch (HibernateException he)
    {
      if (tx != null) {
        tx.rollback();
        he.printStackTrace();
      }
    }
    finally
    {
      session.close();
    }
    
    return result;
  }
  
  public boolean saveNewRequirement(String title, byte[] image)
  {
    boolean result = false;
    Session session = sessionFactory.openSession();
    Transaction tx = null;
    try
    {
      tx = session.getTransaction();
      tx.begin();
      

      Query qry = session.createQuery("from Client");
      
      List<Client> clients = qry.getResultList();
      Client client = (Client)clients.get(0);
      
      qry = session.createQuery("from Project prj where prj.client.clientId=:clientId");
      qry.setParameter("clientId", client.getClientId());
      
      List<Project> projects = qry.getResultList();
      Project project = (Project)projects.get(0);
      
      qry = session.createQuery("from ProjectRelease pr where pr.project.projectId=:projectId");
      qry.setParameter("projectId", project.getProjectId());
      List<ProjectRelease> releases = qry.getResultList();
      ProjectRelease release = (ProjectRelease)releases.get(0);
      
      qry = session.createQuery("from RequirementSourceDocument rsd where rsd.release.releaseId=:releaseId");
      qry.setParameter("releaseId", release.getReleaseId());
      List<RequirementSourceDocument> documents = qry.getResultList();
      RequirementSourceDocument document = (RequirementSourceDocument)documents.get(0);
      
      qry = session.createQuery("from RequirementSourceDocumentVersion rsdv where rsdv.requirementSourceDoc.reqSrcDocId=:reqSrcDocId");
      qry.setParameter("reqSrcDocId", document.getReqSrcDocId());
      List<RequirementSourceDocumentVersion> documentVersions = qry.getResultList();
      RequirementSourceDocumentVersion documentVersion = (RequirementSourceDocumentVersion)documentVersions.get(0);
      
      Requirement requirement = new Requirement();
      requirement.setReqSrcDocVersion(documentVersion);
      requirement.setRequirementText(title);
      requirement.setReqImageContent(image);
      session.save(requirement);
      
      tx.commit();
    }
    catch (HibernateException he)
    {
      if (tx != null) {
        tx.rollback();
        he.printStackTrace();
      }
    }
    finally
    {
      session.close();
    }
    
    return result;
  }
  
  public Requirement getRequirement(Integer requirementId)
  {
    Requirement requirement = null;
    Session session = sessionFactory.openSession();
    Transaction tx = null;
    try
    {
      tx = session.getTransaction();
      tx.begin();
      
      Query qry = session.createQuery("from Requirement req where req.requirementId=:requirementId");
      qry.setParameter("requirementId", requirementId);
      List<Requirement> results = qry.getResultList();
      requirement = (Requirement)results.get(0);
      tx.commit();
    }
    catch (HibernateException he)
    {
      if (tx != null) {
        tx.rollback();
        he.printStackTrace();
      }
    }
    finally
    {
      session.close();
    }
    
    return requirement;
  }
  
  public List getRequirementsForSrcDocVersion(Integer reqSrcDocVerId)
  {
    List requirements = null;
    Session session = sessionFactory.openSession();
    Transaction tx = null;
    try
    {
      tx = session.getTransaction();
      tx.begin();
      
      Query qry = session.createQuery("from Requirement req where req.reqSrcDocVersion.reqSrcDocVerId=:reqSrcDocVerId");
      qry.setParameter("reqSrcDocVerId", reqSrcDocVerId);
      requirements = qry.getResultList();
      tx.commit();
    }
    catch (HibernateException he)
    {
      if (tx != null) {
        tx.rollback();
        he.printStackTrace();
      }
    }
    finally
    {
      session.close();
    }
    
    return requirements;
  }
  
  public List<Client> getClients()
  {
	  List<Client> clients = null;
	  Session session = sessionFactory.openSession();
	  Transaction tx = null;
	  try
	  {
	      tx = session.getTransaction();
	      tx.begin();
	      Query qry = session.createQuery("from Client");
	      clients = qry.getResultList();
	      tx.commit();
	  }
	  catch (HibernateException he)
	  {
	      if (tx != null) {
	        tx.rollback();
	        he.printStackTrace();
	      }
	  }
	  finally
	  {
	      session.close();
	  }

	  return clients;
  }
  
  public List<Project> getProjects(Client client)
  {
	  List projects = null;
      Session session = sessionFactory.openSession();
	  Transaction tx = null;
	  try
	  {
	      tx = session.getTransaction();
	      tx.begin();
	      Query qry = session.createQuery("from Project prj where prj.client.clientId=:clientId");
	      qry.setParameter("clientId", client.getClientId());
	      projects = qry.getResultList();	
	      tx.commit();
	  }
	  catch (HibernateException he)
	  {
	      if (tx != null) {
	        tx.rollback();
	        he.printStackTrace();
	      }
	  }
	  finally
	  {
	      session.close();
	  }
	  
	  return projects;
  }
  
  public List<ProjectRelease> getProjectReleases(Project project)
  {
	  List releases = null;
      Session session = sessionFactory.openSession();
	  Transaction tx = null;
	  try
	  {
	      tx = session.getTransaction();
	      tx.begin();
	      Query qry = session.createQuery("from ProjectRelease pr where pr.project.projectId=:projectId");
	      qry.setParameter("projectId", project.getProjectId());
	      releases = qry.getResultList();
	      tx.commit();
	  }
	  catch (HibernateException he)
	  {
	      if (tx != null) {
	        tx.rollback();
	        he.printStackTrace();
	      }
	  }
	  finally
	  {
	      session.close();
	  }
	  
	  return releases;
  }
  
  public List<RequirementSourceDocument> getRequirementSourceDocuments(ProjectRelease release)
  {
	  List documents = null;
      Session session = sessionFactory.openSession();
	  Transaction tx = null;
	  try
	  {
	      tx = session.getTransaction();
	      tx.begin();
	      Query qry = session.createQuery("from RequirementSourceDocument rsd where rsd.release.releaseId=:releaseId");
	      qry.setParameter("releaseId", release.getReleaseId());
	      documents = qry.getResultList();	
	      tx.commit();
	  }
	  catch (HibernateException he)
	  {
	      if (tx != null) {
	        tx.rollback();
	        he.printStackTrace();
	      }
	  }
	  finally
	  {
	      session.close();
	  }
	  
	  return documents;
  }
  
  public List<RequirementSourceDocumentVersion> getRequirementSourceDocumentVersions(RequirementSourceDocument document)
  {
	  List documentVersions = null;
      Session session = sessionFactory.openSession();
	  Transaction tx = null;
	  try
	  {
	      tx = session.getTransaction();
	      tx.begin();
	      Query qry = session.createQuery("from RequirementSourceDocumentVersion rsdv where rsdv.requirementSourceDoc.reqSrcDocId=:reqSrcDocId");
	      qry.setParameter("reqSrcDocId", document.getReqSrcDocId());
	      documentVersions = qry.getResultList();
	      tx.commit();
	  }
	  catch (HibernateException he)
	  {
	      if (tx != null) {
	        tx.rollback();
	        he.printStackTrace();
	      }
	  }
	  finally
	  {
	      session.close();
	  }
	  
	  return documentVersions;
  }
  
  public List<SourceType> getSourceTypes()
  {
	  List sourceTypes=null;
      Session session = sessionFactory.openSession();
	  Transaction tx = null;
	  try
	  {
	      tx = session.getTransaction();
	      tx.begin();
	      Query qry = session.createQuery("from SourceType");
	      sourceTypes = qry.getResultList();
	      tx.commit();
	  }
	  catch (HibernateException he)
	  {
	      if (tx != null) {
	        tx.rollback();
	        he.printStackTrace();
	      }
	  }
	  finally
	  {
	      session.close();
	  }
	  
	  
	  return sourceTypes;
  }
  
  
  
  @Autowired
  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }
  
  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }
}
