package JobOcean.service;

import java.util.List;

import JobOcean.common.GlobalConstants;
import JobOcean.dao.CompanyDao;
import JobOcean.dto.CompanyDto;
import JobOcean.dto.CompanyListDto;
import JobOcean.dto.JobRequestDto;
import JobOcean.dto.JobRequestListDto;
import JobOcean.model.CompanyModel;
import JobOcean.model.JobRequestModel;


public class CompanyService {

 CompanyDao companyDao = new CompanyDao();
    
    public boolean insertCompany(CompanyDto companyDto){
        System.out.println("CompanyService.insertCompany " + "start");
        boolean flag = true;
        
        CompanyModel companyModel = new CompanyModel();
        
        companyModel.setId(companyDto.getId());
        companyModel.setAddress(companyDto.getAddress());
        companyModel.setCompanyName(companyDto.getCompanyName());
        companyModel.setCompanyUrl(companyDto.getCompanyUrl());
        companyModel.setCreationDate(companyDto.getCreationDate());
        companyModel.setModificationDate(companyDto.getModificationDate());
        companyModel.setEmail(companyDto.getEmail());
        companyModel.setPassword(companyDto.getPassword());
        companyModel.setTelNumber(companyDto.getTelNumber());
        
        
        try {
            
            CompanyModel resultModel = companyDao.getCompanyById(companyModel);
            if(resultModel != null){
                flag =  false;
            }
            else{
                companyDao.insertCompany(companyModel);
            }
        } catch (Exception e) {
            System.out.println("Exception in updating job request: " + e.toString());
        }
        System.out.println("CompanyService.insertCompany " + "end");
        return flag;
    }
    
    public void updateCompanyProfile(CompanyDto companyDto){
        System.out.println("CompanyProfileService.updatingCompanyProfile" + "start");
        
        CompanyModel companyModel = new CompanyModel();
        try {
            companyModel.setId(companyDto.getId());
            CompanyModel resultModel = companyDao.getCompanyById(companyModel);
            if(resultModel != null){
                System.out.println("Company exist.");
                companyModel.setCompanyName(companyDto.getCompanyName());
                companyModel.setCompanyUrl(companyDto.getCompanyUrl());
                companyModel.setCreationDate(resultModel.getCreationDate());
                companyModel.setAddress(companyDto.getAddress());
                companyModel.setPassword(resultModel.getPassword());
                companyModel.setTelNumber(companyDto.getTelNumber());
                companyModel.setKey(resultModel.getKey());
                companyModel.setEmail(resultModel.getEmail());
                
                companyDao.updateCompanyProfile(companyModel);
            }
            else
                System.out.println("Company does not exist!");
            
        } catch (Exception e) {
            System.out.println("Exception in updating company profile: " + e.toString());
        }
        System.out.println("CompanyProfileService.updatingCompanyProfile" + "end");
    }
    
    public CompanyDto confirmUser(CompanyDto inputCompanyDto){
        System.out.println("CompanyService.insertCompany " + "start");
        //boolean flag = false;
        CompanyDto companyDto = new CompanyDto();
        
        CompanyModel companyModel = new CompanyModel();
        companyModel.setEmail(inputCompanyDto.getEmail());
        companyModel.setPassword(inputCompanyDto.getPassword());
        
        try {
            // get the list of todos with the given status. 
            CompanyModel resultModel = companyDao.getCompanyByEmail(companyModel);
            if(resultModel != null){
                companyDto.setId(resultModel.getId());
                companyDto.setCompanyName(resultModel.getCompanyName());
                companyDto.setAddress(resultModel.getAddress());
                companyDto.setEmail(resultModel.getEmail());
                companyDto.setPassword(resultModel.getPassword());
                companyDto.setTelNumber(resultModel.getTelNumber());
                companyDto.setCompanyUrl(resultModel.getCompanyUrl());
                companyDto.setCreationDate(resultModel.getCreationDate());
                companyDto.setModificationDate(resultModel.getModificationDate());
            }
        }catch(Exception e) {
            companyDto.addError(GlobalConstants.ERR_ENTRY_NOT_FOUND);
        }
        
        System.out.println("CompanyService.insertCompany " + "end");
        return companyDto;
    }
    
    public CompanyListDto getAllCompanies(){
        System.out.println("CompanyService.insertCompany " + "start");
        CompanyListDto resultList = new CompanyListDto();
        try{
            List<CompanyModel> companyList = companyDao.getAllCompanies();
            
            for(CompanyModel c : companyList){
                CompanyDto companyDto = new CompanyDto();

                companyDto.setId(c.getId());
                companyDto.setAddress(c.getAddress());
                companyDto.setCompanyName(c.getCompanyName());
                companyDto.setCompanyUrl(c.getCompanyUrl());
                companyDto.setCreationDate(c.getCreationDate());
                companyDto.setModificationDate(c.getModificationDate());
                companyDto.setEmail(c.getEmail());
                companyDto.setPassword(c.getPassword());
                companyDto.setTelNumber(c.getTelNumber());
                resultList.getEntries().add(companyDto);
            }
        }catch(Exception e){
            resultList.addError(GlobalConstants.ERR_ENTRY_NOT_FOUND);
        }
        
        System.out.println("CompanyService.insertCompany " + "end");
        
        return resultList;
    }
    
    public boolean isCompanyNameTaken(String companyName){        
        return companyDao.isCompanyNameAlreadyTaken(companyName);
    }

    public int loginStatus(CompanyDto inputCompanyDto) {
        System.out.println("CompanyService.loginStatus " + "start");
        int flag = 0;
        CompanyModel companyModel = new CompanyModel();
        
        companyModel.setEmail(inputCompanyDto.getEmail());
        companyModel.setPassword(inputCompanyDto.getPassword());
        
        // flag = 0 .. correct
        // flag = 1 .. email does not exist
        // flag = 2 .. password is incorrect
        
        CompanyModel resultModel = companyDao.getCompanyByEmail(companyModel);
        if(resultModel != null){
            if (resultModel.getPassword().equals(inputCompanyDto.getPassword()))
            {
                flag = 0;
            }
            else 
            {
                flag = 2;
            }
        }
        else
        {
            flag = 1;
        }
        
        System.out.println("CompanyService.loginStatus " + "end");
        return flag;
    }
    
    public boolean isEmailTaken(String email) {
        return companyDao.isCompanyEmailAlreadyTaken(email);
    }

    public Long getCompanyIdByEmail(String email) {
        CompanyModel companyModel = new CompanyModel();
        companyModel.setEmail(email);
        return companyDao.getCompanyByEmail(companyModel).getId();
    }
}
