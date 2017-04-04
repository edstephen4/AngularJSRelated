/* ------------------------------------------------------------------------------
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Copyright (C) Rococo Global Technologies, Inc - All Rights Reserved 2016
 * --------------------------------------------------------------------------- */
package JobOcean.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.repackaged.org.json.JSONObject;

import JobOcean.dto.JobRequestDto;
import JobOcean.service.JobRequestService;


/**
 * Controller used to update a 'bento' to the datastore.
 * @author Ed Stephen Z. Koh
 * @version 0.01
 * Version History
 * [08/30/2016] 0.01 - Ed Stephen Z. Koh - Initial codes.
 */

public class UpdateJobRequestController extends Controller {
    
    /**
     * Service object that will be used to call the insert function to datastore.
     */
    JobRequestService jobRequestService = new JobRequestService();
    
    /**
     * The funtion that will be ran first upon entering this controller.
     */
    @Override
    protected Navigation run() throws Exception {
        System.out.println("UpdateJobRequestController.run " + "start");
        /**
         * Used to store the information from the request and send to the
         * service class.
         */
        JobRequestDto jobRequestDto = new JobRequestDto();

        JSONObject jsonObject = null;
        try {

            jsonObject = new JSONObject(this.request.getReader().readLine());
            String jobRequestNumber = jsonObject.getString("jobRequestNumber");
            String description = jsonObject.getString("description");
            String startDate = jsonObject.getString("startDate");
            String endDate = jsonObject.getString("endDate");
            String status = jsonObject.getString("status");
            Long id = jsonObject.getLong("id");


            Date startdate = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
            Date enddate = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
            
            jobRequestDto.setCompanyId(id);
            jobRequestDto.setJobRequestNumber(jobRequestNumber);
            jobRequestDto.setDescription(description);
            jobRequestDto.setStartDate(startdate);
            jobRequestDto.setEndDate(enddate);
            jobRequestDto.setStatus(status);
            
            
            jobRequestService.updateJobRequest(jobRequestDto);
        } catch (Exception e) {
            System.out.println("UpdateBentoController.run.exception " + e.toString());
        }  
        
        System.out.println("UpdateJobRequestController.run " + "end");
        // screen redirection.
        return null;
    }

}
