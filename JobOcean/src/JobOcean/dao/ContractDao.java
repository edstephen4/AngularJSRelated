package JobOcean.dao;

import java.util.ArrayList;
import java.util.List;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;

import JobOcean.meta.ContractModelMeta;
import JobOcean.meta.JobRequestModelMeta;
import JobOcean.model.ContractModel;
import JobOcean.model.JobRequestModel;

/**
 * Controller that will accessed when 'localhost:8888' is entered.
 * @author Ed Stephen Koh
 * @version 0.01
 * Version History
 * [08/30/2016] 0.01 - Ed Stephen Koh - Initial codes.
 */

public class ContractDao {
    public void insertContract(ContractModel contractModel){
        System.out.println("JobrequestDao.insertJobRequest " + "start");
        
        Transaction transaction = Datastore.beginTransaction();
        // creating key and ID for the new entity
        Key parentKey = KeyFactory.createKey("Contract", contractModel.getContractNumber());
        Key key = Datastore.allocateId(parentKey, "ContractModel");
        
        // setting the 'key' and 'id' of the model
        contractModel.setKey(key);
        contractModel.setId(key.getId());
        
        // inserting the item to the datastore
        Datastore.put(contractModel);
        transaction.commit();
        
        System.out.println("JobrequestDao.insertJobRequest " + "end");
    }
    
    public List<ContractModel> getContractList(){
        List<ContractModel> contractModelList = new ArrayList<ContractModel>();
        ContractModelMeta meta = ContractModelMeta.get();
        contractModelList = Datastore.query(meta)
                .asList();
        return contractModelList;
    }
    
    public ContractModel getContractById(ContractModel inputContract) {
        System.out.println("ContractDao.getContractById " + "start");
        // container for the query result
        ContractModel contractModel = new ContractModel();
        
        ContractModelMeta meta = ContractModelMeta.get();
        // retrieve a 'bento' with same name and id
        contractModel = Datastore.query(meta)
                         .filter(meta.contractNumber.equal(inputContract.getContractNumber()))
                         .asSingle();
        
        
        System.out.println("ContractDao.getContractById " + "end");
        return contractModel;
    }
    
    public ContractModel getContractByContractNumber(ContractModel inputContract) {
        System.out.println("ContractDao.getContractByContractNumber " + "start");
        // container for the query result
        ContractModel contractModel = new ContractModel();
        
        ContractModelMeta meta = ContractModelMeta.get();
        contractModel = Datastore.query(meta)
                         .filter(meta.contractNumber.equal(inputContract.getContractNumber()))
                         .asSingle();
        
        
        System.out.println("ContractDao.getContractByContractNumber " + "end");
        return contractModel;
    }
    
    public void updateContract(ContractModel inputContract) {
        System.out.println("ContractDao.updateContract " + "start");

        Transaction transaction = Datastore.beginTransaction();
        Datastore.put(inputContract);
        transaction.commit();
        
        System.out.println("ContractDao.updateContract " + "end");
    }
    public void updateContractStatus(ContractModel inputContract){
        System.out.println("ContractDao.updateContract " + "start");

        Transaction transaction = Datastore.beginTransaction();
        Datastore.put(inputContract);
        transaction.commit();
        
        System.out.println("ContractDao.updateContract " + "end");
    }

    
    public void updateJobRequestContract(String jobRequestNumber, String contractNumber) {
        System.out.println("ContractDao.updateContract " + "start");

        JobRequestModel jobRequestModel = new JobRequestModel();
        
        JobRequestModelMeta meta = JobRequestModelMeta.get();
        // retrieve a 'bento' with same name and id
        jobRequestModel = Datastore.query(meta)
                         .filter(meta.jobRequestNumber.equal(jobRequestNumber))
                         .asSingle();
        
        ArrayList<String> contracts = jobRequestModel.getContracts();
        contracts.add(contractNumber);
        jobRequestModel.setContracts(contracts);
        
        Transaction transaction = Datastore.beginTransaction();
        Datastore.put(jobRequestModel);
        transaction.commit();
        
        System.out.println("ContractDao.updateContract " + "end");
    }
    
    public void getJobRequestContractsToUpdate(ArrayList<String> closedStatus, ArrayList<String> inProgressStatus) {
        System.out.println("ContractDao.updateJobRequestContractStatus " + "start");

        List<ContractModel> contractModelList = new ArrayList<ContractModel>();
        ContractModelMeta meta = ContractModelMeta.get();
        ContractModel contractModel = new ContractModel();
        for(String status : closedStatus){
            contractModel = Datastore.query(meta).filter(meta.contractNumber.equal(status))
                    .asSingle();
            if(contractModel != null){
                contractModel.setStatus("Closed");
                
                Transaction transaction = Datastore.beginTransaction();
                Datastore.put(contractModel);
                transaction.commit();
            }
        }
        
        for(String status : inProgressStatus){
            contractModel = Datastore.query(meta).filter(meta.contractNumber.equal(status))
                    .asSingle();
            if(contractModel != null){
                contractModel.setStatus("In Progress");
                
                Transaction transaction = Datastore.beginTransaction();
                Datastore.put(contractModel);
                transaction.commit();
            }
        }
       
        System.out.println("ContractDao.updateJobRequestContractStatus " + "end");
    }
    
    public void deleteContract(ContractModel resultModel) {
        System.out.println("JobRequestDao.deleteJobRequest " + "start");

        Transaction transaction = Datastore.beginTransaction();
        Datastore.delete(resultModel.getKey());
        transaction.commit();
        
        System.out.println("JobRequestDao.deleteJobRequest " + "end");
    }

    public void updateJobRequestContracts(JobRequestModel resultModel) {
        // TODO Auto-generated method stub
        Transaction transaction = Datastore.beginTransaction();
        Datastore.put(resultModel);
        transaction.commit();
    }
    
}
