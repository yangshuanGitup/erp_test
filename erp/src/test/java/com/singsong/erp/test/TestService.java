package com.singsong.erp.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.singsong.erp.business.system.config.template.entity.EmailTemplate;
import com.singsong.erp.business.system.config.template.service.EmailTemplateService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/spring/*.xml" })
@Transactional
public class TestService {
	@Resource
	private EmailTemplateService emailTemplateService;

	public void find() {
		try {
			List<Object> ids=new ArrayList<Object>();
			ids.add("F3CAE477-E931-4377-ADAA-C4022BE82B0C");
			ids.add("A440A064-6D81-4E5B-A3CA-E5B70495E027");
			ids.add("DAA21FA0-4DD7-4D0E-A5FC-E37FC687B7A9");
			List<EmailTemplate> list=emailTemplateService.findByPkBatch(ids);
			for(EmailTemplate e:list){
				System.out.println(e.toString());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void delete() {
		List<Object> ids=new ArrayList<Object>();
		ids.add("A440A064-6D81-4E5B-A3CA-E5B70495E027");
		ids.add("A440A064-6D81-4E5B-A3CA-E5B70495E027");
		ids.add("7E4946E6-193E-4315-B825-C585F1D8DA92");
		ids.add("DAA21FA0-4DD7-4D0E-A5FC-E37FC687B7A9");
		int count=emailTemplateService.deleteByPkBatch(ids);
		System.out.println(count);
	}
}
