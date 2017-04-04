package JobOcean.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.repackaged.org.json.JSONArray;
import org.slim3.repackaged.org.json.JSONObject;

import JobOcean.common.GlobalConstants;
import JobOcean.dto.CompanyDto;
import JobOcean.dto.ContractListDto;
import JobOcean.dto.JobRequestListDto;
import JobOcean.service.CompanyService;
import JobOcean.service.ContractService;
import JobOcean.service.JobRequestService;

/**
 * Controller used to update a 'bento' to the datastore.
 * @author Ed Stephen Z. Koh
 * @version 0.01
 * Version History
 * [08/30/2016] 0.01 - Ed Stephen Z. Koh - Initial codes.
 */

public class UpdateJobRequestStatusController extends Controller {

    JobRequestService jobRequestService = new JobRequestService();    
    
    @Override
    public Navigation run() throws Exception {
        
        System.out.println("LoginController.run " + "start");
        /**
         * Holds the information to be passed in the response.
         */
        JSONObject json = null;
        JSONArray closed = new JSONArray();
        JSONArray inProgress = new JSONArray();
        JSONArray statusNew = new JSONArray();
        try {
            json = new JSONObject(this.request.getReader().readLine());
            closed =  json.getJSONArray("updateClosedJobRequestStatus");
            inProgress = json.getJSONArray("updateInProgressJobRequestStatus");
            statusNew = json.getJSONArray("updateNewJobRequestStatus");
            String closedDates = json.getString("closedDate");
            // getting the list of items
            ArrayList<String> closedStatus = new ArrayList<String>();
            ArrayList<String> inProgressStatus = new ArrayList<String>();
            ArrayList<String> newStatus = new ArrayList<String>();
            Date closeddate = new SimpleDateFormat("yyyy-MM-dd").parse(closedDates);
            for(int i = 0; i < closed.length(); i++){
                closedStatus.add(closed.getJSONObject(i).getString("jobRequestNumber"));
            }
            for(int i = 0; i < inProgress.length(); i++){
                inProgressStatus.add(inProgress.getJSONObject(i).getString("jobRequestNumber"));
            }
            for(int i = 0; i < statusNew.length(); i++){
                newStatus.add(statusNew.getJSONObject(i).getString("jobRequestNumber"));
            }
            
            jobRequestService.updateJobRequestStatus(closedStatus, inProgressStatus, newStatus, closeddate);
                    
            // adding the list to the json that will be passed as response.
        } catch (Exception e) {
            System.out.println(e.toString());
            // Adds an error message if there exists.
            //contractListDto.addError(GlobalConstants.ERR_SERVER_CONTROLLER_PREFIX + e.getMessage());
            // initialize the json object that will be passed as response.
            if (null == json) {
                json = new JSONObject();
            }
        }
        // add the error message to the json object.
        //json.put("errorList", contractListDto.getErrorList());
        
        // set the type of response.
        response.setContentType(GlobalConstants.SYS_CONTENT_TYPE_JSON);
        // send the response back to the JS file.
        response.getWriter().write(json.toString());
        
        System.out.println("LoginController.run " + "end");
        return null;
    }
}
