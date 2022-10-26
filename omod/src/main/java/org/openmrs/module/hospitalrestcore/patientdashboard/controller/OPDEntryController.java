package org.openmrs.module.hospitalrestcore.patientdashboard.controller;

import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Mujuzi Moses
 *
 */

@Controller
@RequestMapping(value = "/rest" + RestConstants.VERSION_1 + "/OpdEntry")
public class OPDEntryController extends BaseRestController {


}
