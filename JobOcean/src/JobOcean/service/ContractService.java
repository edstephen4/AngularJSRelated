package JobOcean.service;

import java.util.ArrayList;
import java.util.List;

import JobOcean.common.GlobalConstants;
import JobOcean.dao.ContractDao;
import JobOcean.dao.JobRequestDao;
import JobOcean.dto.ContractDto;
import JobOcean.dto.ContractListDto;
import JobOcean.dto.JobRequestDto;
import JobOcean.dto.JobRequestListDto;
import JobOcean.model.ContractModel;
import JobOcean.model.JobRequestModel;

/**
 * Controller that will accessed when 'localhost:8888' is entered.
 * @author Ed Stephen Koh
 * @version 0.01
 * Version History
 * [08/30/2016] 0.01 - Ed Stephen Koh - Initial codes.
 */

public class ContractService {
    ContractDao contractDao = new ContractDao();
    JobRequestDao jobRequestDao = new JobRequestDao();
    public void insertContract(ContractDto contractDto, String jobRequestNumber){
        System.out.println("ContractService.insertContract " + "start");
        
        ContractModel contractModel = new ContractModel();
        JobRequestModel jobRequestModel = new JobRequestModel();
        
        contractModel.setContractNumber(contractDto.getContractNumber());
        contractModel.setType(contractDto.getType());
        contractModel.setContractorName(contractDto.getContractorName());
        contractModel.setStartDate(contractDto.getStartDate());
        contractModel.setEndDate(contractDto.getEndDate());
        contractModel.setStatus(contractDto.getStatus());
        
        try{
            contractDao.insertContract(contractModel);
            contractDao.updateJobRequestContract(jobRequestNumber, contractDto.getContractNumber());
        }
        catch(Exception e){
            System.out.println("Exception in inserting contract: " + e.toString());
        }
        System.out.println("ContractService.insertContract " + "end");
    }
    
    
    public void updateContract(ContractDto inputContract) {
        System.out.println("ContractService.updateContract " + "start, end");
        /**
         * Used to store the data from the DTO object to the model.
         * Used as parameters in passing to the DAO layer.
         */
        ContractModel contractModel = new ContractModel();
        
        contractModel.setContractNumber(inputContract.getContractNumber());
        

        try {
            
            ContractModel resultModel = contractDao.getContractById(contractModel);
            if(resultModel != null){
                System.out.print("Contract exist.");
                resultModel.setContractorName(inputContract.getContractorName());
                resultModel.setStartDate(inputContract.getStartDate());
                resultModel.setEndDate(inputContract.getEndDate());
                resultModel.setType(inputContract.getType());
                contractDao.updateContract(resultModel);
            }
            else{
                System.out.print("Contract not found!");
            }
        } catch (Exception e) {
            System.out.println("Exception in updating bento: " + e.toString());
        }

        System.out.println("ContractService.updateContract " + "start, end");
    }
    
    public void updateContractStatus(ContractDto inputContract) {
        System.out.println("ContractService.updateContract " + "start, end");
        /**
         * Used to store the data from the DTO object to the model.
         * Used as parameters in passing to the DAO layer.
         */
        ContractModel contractModel = new ContractModel();
        
        contractModel.setContractNumber(inputContract.getContractNumber());
     
        try {
            
            ContractModel resultModel = contractDao.getContractByContractNumber(contractModel);
            if(resultModel != null){
                System.out.print("Contract exist.");
                resultModel.setClosedDate(inputContract.getClosedDate());
                resultModel.setStatus(inputContract.getStatus());
                contractDao.updateContract(resultModel);
            }
            else{
                System.out.print("Contract not found!");
            }
        } catch (Exception e) {
            System.out.println("Exception in updating bento: " + e.toString());
        }

        System.out.println("ContractService.updateContract " + "start, end");
    }
    
    public ContractListDto getContractList() {
        System.out.println("PersonListDto.getTodoList " + "start");
        // initializing the dto to hold the list of todos.
        ContractListDto contractListDto =  new ContractListDto();
        
        try {
            // get the list of todos with the given status. 
            List<ContractModel> contractList = contractDao.getContractList();
            if (contractList != null) {
                // convert each todoModel from the todoList into TodoDto
                for (ContractModel resultModel : contractList) {
                    ContractDto contractDto = new ContractDto();
                    
                    contractDto.setContractNumber(resultModel.getContractNumber());
                    contractDto.setContractorName(resultModel.getContractorName());
                    contractDto.setType(resultModel.getType());
                    contractDto.setStartDate(resultModel.getStartDate());
                    contractDto.setClosedDate(resultModel.getClosedDate());
                    contractDto.setEndDate(resultModel.getEndDate());
                    contractDto.setStatus(resultModel.getStatus());
                    contractDto.setCreationDate(resultModel.getCreationDate());
                    contractDto.setModificationDate(resultModel.getModificationDate());
                    
                    // adding the dto to the list
                    contractListDto.getEntries().add(contractDto);
                }
            }
        }catch(Exception e) {
            contractListDto.addError(GlobalConstants.ERR_ENTRY_NOT_FOUND);
        }
        System.out.println("PersonListDto.getTodoList " + "end");
        return contractListDto;
    }
    
    public void getUpdatedContractList(ArrayList<String> closedStatus, ArrayList<String> inProgressStatus) {
        System.out.println("PersonListDto.getTodoList " + "start");
        // initializing the dto to hold the list of todos.
        
        try {
            // get the list of todos with the given status. 
            
            for(String closedStatuses : closedStatus){
                ContractModel contractModel = new ContractModel();
                contractModel.setContractNumber(closedStatuses);
                
                contractModel = contractDao.getContractByContractNumber(contractModel);
                contractModel.setStatus("Closed");
                contractDao.updateContract(contractModel);
                
            }
            
            for(String inProgressStatuses : inProgressStatus){
                ContractModel contractModel = new ContractModel();
                contractModel.setContractNumber(inProgressStatuses);
                
                contractModel = contractDao.getContractByContractNumber(contractModel);
                contractModel.setStatus("In Progress");
                contractDao.updateContract(contractModel);
                
            }
           
        }catch(Exception e) {
        }
        System.out.println("PersonListDto.getTodoList " + "end");
    }
    
    public void deleteContract(ContractDto contractDto) {
        // TODO Auto-generated method stub
        ContractModel contractModel = new ContractModel();
        
        contractModel.setContractNumber(contractDto.getContractNumber());     

        try {
            
            ContractModel resultModel = contractDao.getContractById(contractModel);
            if(resultModel != null){
                //  jobRequestModel.setKey(resultModel.getKey());
                System.out.print("Job Request exists.");
                contractDao.deleteContract(resultModel);
            }
            else{
                System.out.print("Job Request not found!");
            }
        } catch (Exception e) {
            System.out.println("Exception in deleting job request: " + e.toString());
        }
    }
    
    public void deleteContractInJobrequest(ContractDto contractDto) {
        // TODO Auto-generated method stub
           
        List<JobRequestModel> jobRequestList= jobRequestDao.getJobRequestList();
        JobRequestModel resultModel = new JobRequestModel();
       
        try {
            for(JobRequestModel jobReqModel : jobRequestList){
                ArrayList<String> contracts = jobReqModel.getContracts();
                int i = 0;
                for(String s : contracts){
                    if(s.equals(contractDto.getContractNumber())){
                        contracts.remove(i);
                        resultModel = jobReqModel;
                        break;
                    }
                    i++;
                }
            }
            if(resultModel != null){
                //  jobRequestModel.setKey(resultModel.getKey());
                System.out.print("Job Request exists.");
                contractDao.updateJobRequestContracts(resultModel);
            }
            else{
                System.out.print("Job Request not found!");
            }
        } catch (Exception e) {
            System.out.println("Exception in deleting job request: " + e.toString());
        }
    }
}
