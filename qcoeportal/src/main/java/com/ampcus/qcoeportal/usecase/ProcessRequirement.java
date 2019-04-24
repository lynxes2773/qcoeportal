package com.ampcus.qcoeportal.usecase;

import com.ampcus.qcoeportal.core.dao.RTMDao;
import com.ampcus.qcoeportal.core.entity.Requirement;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
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
  @Autowired
  private HashMap appConfigMap;
  private RTMDao rtmDao;
  
  @Autowired
  public void setRtmDao(RTMDao rtmDao)
  {
    this.rtmDao = rtmDao;
  }
  
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
}
