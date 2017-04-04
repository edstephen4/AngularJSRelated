package JobOcean.controller;

import java.util.ArrayList;
import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.repackaged.org.json.JSONArray;
import org.slim3.repackaged.org.json.JSONObject;

import JobOcean.common.GlobalConstants;
import JobOcean.dto.CompanyDto;
import JobOcean.dto.ContractDto;
import JobOcean.dto.ContractListDto;
import JobOcean.dto.JobRequestDto;
import JobOcean.dto.JobRequestListDto;
import JobOcean.service.CompanyService;
import JobOcean.service.JobRequestService;

public class JobRequestToEditController extends Controller {

    JobRequestService jobRequestService = new JobRequestService();    
    
    @Override
    public Navigation run() throws Exception {
        
        System.out.println("JobRequestToEdit.run " + "start");
       
        JobRequestDto jobRequestDto = new JobRequestDto();
        ContractListDto contractListDto = new ContractListDto();
        /**
         * Holds the information to be passed in the response.
         */
        JSONObject json = null;
        JSONArray contracts = new JSONArray();
        try {
            json = new JSONObject(this.request.getReader().readLine());
            
            // getting all the requests from AJAX call using JSON object
            String jobRequest = json.getString("jobRequestNumber");
            System.out.print(jobRequest);
            contracts = json.getJSONArray("contracts");
            System.out.print(contracts);
            // getting the Job Request to be edited
            jobRequestDto = jobRequestService.getJobRequestToEdit(jobRequest);
            
            // adding the objects to the json that will be passed as response.
            json.put("jobRequestNumber", jobRequestDto.getJobRequestNumber());
            json.put("description", jobRequestDto.getDescription());
            json.put("startDate", jobRequestDto.getStartDate());
            json.put("endDate", jobRequestDto.getEndDate());
            json.put("status", jobRequestDto.getStatus());
            
            // set contract numbers after instantiating contract dto
            List<ContractDto> contractDtos = new ArrayList<ContractDto>();
            for(int i = 0; i < contracts.length(); i++){
                ContractDto contractDto = new ContractDto();
                System.out.print(contracts.getString(i));
                contractDto.setContractNumber(contracts.getString(i));
                contractDtos.add(contractDto);
            }
            
            // get all contracts of the job request
            contractListDto = jobRequestService.getContractsOfJobRequest(contractDtos);

            // adding the list to the json that will be passed as response.
            json.put("contractsOfJobRequest", contractListDto.getEntries());
            
        } catch (Exception e) {
            System.out.println(e.toString());
            // Adds an error message if there exists.
            jobRequestDto.addError(GlobalConstants.ERR_SERVER_CONTROLLER_PREFIX + e.getMessage());
            // initialize the json object that will be passed as response.
            if (null == json) {
                json = new JSONObject();
            }
        }
        // add the error message to the json object.
        json.put("errorList", jobRequestDto.getErrorList());
        
        // set the type of response.
        response.setContentType(GlobalConstants.SYS_CONTENT_TYPE_JSON);
        // send the response back to the JS file.
        response.getWriter().write(json.toString());
        
        System.out.println("JobRequestToEdit.run " + "end");
        return null;
    }
}
