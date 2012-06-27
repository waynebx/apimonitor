package service.impl

import models.Bean.TestCase
import play.api.libs.json.Json
import service.AbstractService
import service.TestCaseService
import util.JSONUtil

class TestCaseServiceImpl extends TestCaseService with AbstractService{
  

  def getTestCaseList(start: Int, size: Int): List[TestCase] = {

    return testCaseDAO.findLimit(start, size)
  }

}