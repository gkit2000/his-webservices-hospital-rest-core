package org.openmrs.module.hospitalrestcore.controller;

import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rest/" + RestConstants.VERSION_1 + HospitalRestCoreController.HOSPITAL_REST_CORE_NAMESPACE)
public class HospitalRestCoreController extends MainResourceController {
	
	public static final String HOSPITAL_REST_CORE_NAMESPACE = "/hospitalrestcore";
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController#getNamespace()
	 */
	@Override
	public String getNamespace() {
		return RestConstants.VERSION_1 + HospitalRestCoreController.HOSPITAL_REST_CORE_NAMESPACE;
	}
	
}
