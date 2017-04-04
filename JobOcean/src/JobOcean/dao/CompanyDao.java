package JobOcean.dao;

import java.util.ArrayList;
import java.util.List;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;

import JobOcean.meta.CompanyModelMeta;
import JobOcean.meta.JobRequestModelMeta;
import JobOcean.model.CompanyModel;
import JobOcean.model.JobRequestModel;


public class CompanyDao{
    
    public List<CompanyModel> getAllCompanies(){
        System.out.println("CompanyService.insertCompany " + "start");
 
        List<CompanyModel> resultList = new ArrayList<CompanyModel>();
        
        CompanyModelMeta meta = CompanyModelMeta.get();
        resultList = Datastore.query(meta).asList();
        
        System.out.println("CompanyService.insertCompany " + "end");
        return resultList;
    }
    
    public void insertCompany(CompanyModel companyModel) {        
        System.out.println("CompanyDao.insertCompany " + "start");
        
        Transaction transaction = Datastore.beginTransaction();
        // creating key and ID for the new entity
        Key parentKey = KeyFactory.createKey("Company", companyModel.getCompanyName());
        Key key = Datastore.allocateId(parentKey, "CompanyModel");
        
        // setting the 'key' and 'id' of the model
        companyModel.setKey(key);
        companyModel.setId(key.getId());
        
        // inserting the item to the datastore
        Datastore.put(companyModel);
        transaction.commit();
        
        System.out.println("CompanyDao.insertCompany " + "end");
    }
    
    public CompanyModel getCompanyById(CompanyModel inputCompany) {
        System.out.println("CompanyDao.getCompanyById " + "start");
        // container for the query result
        CompanyModel companyModel = new CompanyModel();
        CompanyModelMeta meta = CompanyModelMeta.get();
        companyModel = Datastore.query(meta)
                         .filter(meta.id.equal(inputCompany.getId()))
                         .asSingle();
        
        
        System.out.println("CompanyDao.getCompanyById " + "end");
        return companyModel;
    }
    
    /*public CompanyModel getJobRequestList(String email, String password){
        CompanyModel companyModel = new CompanyModel();
        CompanyModelMeta meta = CompanyModelMeta.get();
        companyModel = Datastore.query(meta)
                .filter(meta.email.equal(email), meta.password.equal(password))
                .asSingle();
        return companyModel;
    }*/
    
    public CompanyModel getCompanyByEmail(CompanyModel inputCompany) {
        System.out.println("CompanyDao.getCompanyById " + "start");
        // container for the query result
        CompanyModel companyModel = new CompanyModel();
        CompanyModelMeta meta = CompanyModelMeta.get();
        companyModel = Datastore.query(meta)
                         .filter(meta.email.equal(inputCompany.getEmail()))
                         .asSingle();
        
        
        System.out.println("CompanyDao.getCompanyById " + "end");
        return companyModel;
    }
    
    public void updateCompanyProfile(CompanyModel inputCompany) {
        System.out.println("CompanyDao.updateCompany " + "start");

        Transaction transaction = Datastore.beginTransaction();
        Datastore.put(inputCompany);
        transaction.commit();
        
        System.out.println("CompanyDao.updateCompany " + "end");
    }
    
    public boolean isCompanyNameAlreadyTaken(String companyName){
        System.out.println("CompanyDao.isCompanyNameAlreadyTaken " + "start");
        // container for the query result
        CompanyModel result = new CompanyModel();
        CompanyModelMeta meta = CompanyModelMeta.get();
        result = Datastore.query(meta)
                .filter(meta.companyName.equal(companyName))
                .asSingle();
        if(result != null){
            System.out.println("CompanyDao.isCompanyNameAlreadyTaken " + "end");
            return true;
        }
        else{
            System.out.println("CompanyDao.isCompanyNameAlreadyTaken " + "end");
            return false;
        }
        
    }
    
    public boolean isCompanyEmailAlreadyTaken(String email){
        System.out.println("CompanyDao.isCompanyNameAlreadyTaken " + "start");
        // container for the query result
        CompanyModel result = new CompanyModel();
        CompanyModelMeta meta = CompanyModelMeta.get();
        result = Datastore.query(meta)
                .filter(meta.email.equal(email))
                .asSingle();
        
        if(result != null){
            System.out.println("CompanyDao.isCompanyNameAlreadyTaken " + "end");
            return true;
        }
        else{
            System.out.println("CompanyDao.isCompanyNameAlreadyTaken " + "end");
            return false;
        }
    }
}
