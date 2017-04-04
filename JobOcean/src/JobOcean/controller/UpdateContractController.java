package JobOcean.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.repackaged.org.json.JSONObject;

import JobOcean.dto.ContractDto;
import JobOcean.service.ContractService;


/**
 * Controller that will accessed when 'localhost:8888' is entered.
 * @author Ed Stephen Z. Koh
 * @version 0.01
 * Version History
 * [08/30/2016] 0.01 - Ed Stephen Z. Koh - Initial codes.
 */

public class UpdateContractController extends Controller {

    ContractService contractService = new ContractService();
    
    @Override
    public Navigation run() throws Exception {
        System.out.println("UpdateContractController.run " + "start");
        /**
         * Used to store the information from the request and send to the
         * service class.
         */
        ContractDto contractDto = new ContractDto();

        JSONObject jsonObject = null;
        try {

                jsonObject = new JSONObject(this.request.getReader().readLine());
                String contractNumber = jsonObject.getString("contractNumber");
                String type = jsonObject.getString("type");
                String contractorName = jsonObject.getString("contractorName");
                String startDate = jsonObject.getString("startDate");
                String endDate = jsonObject.getString("endDate");
                
                Date startdate = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
                Date enddate = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
                
                contractDto.setType(type);
                contractDto.setContractNumber(contractNumber);
                contractDto.setContractorName(contractorName);
                contractDto.setStartDate(startdate);
                contractDto.setEndDate(enddate);
                
                contractService.updateContract(contractDto);
        } catch (Exception e) {
            System.out.println("UpdateContractController.run.exception " + e.toString());
        }  
        
        System.out.println("UpdateContractController.run " + "end");
        // screen redirection.
        return forward("/html/company.html");
    }
}
