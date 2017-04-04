/* ------------------------------------------------------------------------------
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Copyright (C) Rococo Global Technologies, Inc - All Rights Reserved 2016
 * --------------------------------------------------------------------------- */
package JobOcean.controller;


import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.repackaged.org.json.JSONObject;

import JobOcean.common.GlobalConstants;
import JobOcean.dto.JobRequestListDto;
import JobOcean.service.JobRequestService;



/**
 * Controller class used to get the list of todos.
 * @author Lehmar Cabrillos
 * @version 0.01
 * Version History
 * [03/11/2016] 0.01 – Lehmar Cabrillos  – Initial codes.
 */
public class ListJobRequestController extends Controller {
    
    /**
     * Instance of the TodoService that will be used to call the delete function.
     */
    private JobRequestService jobRequestService = new JobRequestService(); 
    
    /**
     * The function that is ran when the controller is called.
     */
    @Override
    public Navigation run() throws Exception {
        System.out.println("ListPersonController.run " + "start");
        /**
         * Use to store the information that will be passed to the service.
         */
        JobRequestListDto jobRequestListDto = new JobRequestListDto();
        /**
         * Holds the information to be passed in the response.
         */
        JSONObject json = null;
        try {
            json = new JSONObject();
            // getting the list of items
            jobRequestListDto = jobRequestService.getTodoList();
            // adding the list to the json that will be passed as response.
            json.put("jobRequestList", jobRequestListDto.getEntries());
            
        } catch (Exception e) {
            System.out.println(e.toString());
            // Adds an error message if there exists.
            jobRequestListDto.addError(GlobalConstants.ERR_SERVER_CONTROLLER_PREFIX + e.getMessage());
            // initialize the json object that will be passed as response.
            if (null == json) {
                json = new JSONObject();
            }
        }
        // add the error message to the json object.
        json.put("errorList", jobRequestListDto.getErrorList());
        
        // set the type of response.
        response.setContentType(GlobalConstants.SYS_CONTENT_TYPE_JSON);
        // send the response back to the JS file.
        response.getWriter().write(json.toString());
        
        System.out.println("ListPersonController.run " + "end");
        return null;
    }
}
