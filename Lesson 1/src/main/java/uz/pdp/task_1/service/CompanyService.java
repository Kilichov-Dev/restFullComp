package uz.pdp.task_1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.task_1.entity.Address;
import uz.pdp.task_1.entity.Company;
import uz.pdp.task_1.payload.ApiResponse;
import uz.pdp.task_1.payload.CompanyDto;
import uz.pdp.task_1.repository.AddressRepository;
import uz.pdp.task_1.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    AddressRepository addressRepository;

    public List<Company> getCompany() {
        return companyRepository.findAll();
    }

    public Company getCompanyById(Integer id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        return optionalCompany.orElse(null);
    }

    public ApiResponse addCompany(CompanyDto companyDto) {
        Address address = new Address();
        if (addressRepository.existsByHomeNumberAndStreet(companyDto.getHomeNumber(), companyDto.getStreet())) {
            return new ApiResponse("This address already exists!", false);
        }
        address.setHomeNumber(companyDto.getHomeNumber());
        address.setStreet(companyDto.getStreet());


        Company company = new Company();
        if (companyRepository.existsByCorpName(companyDto.getCorpName())) {
            return new ApiResponse("This is corpName already exists! ", false);
        }
        company.setCorpName(companyDto.getCorpName());


        Address save = addressRepository.save(address);
        company.setAddress(save);
        companyRepository.save(company);
        return new ApiResponse("Company added success!", true);
    }

    public ApiResponse editCompany(Integer id, CompanyDto companyDto) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isPresent()) {
            Company company = optionalCompany.get();
            if (companyRepository.existsByCorpName(companyDto.getCorpName())) {
                return new ApiResponse("This is corpName already exists! ", false);
            }
            company.setCorpName(companyDto.getCorpName());

            if (addressRepository.existsByHomeNumberAndStreet(companyDto.getHomeNumber(), companyDto.getStreet())) {
                return new ApiResponse("This address already exists!", false);
            }
            Address address = company.getAddress();
            address.setStreet(companyDto.getStreet());
            address.setHomeNumber(companyDto.getHomeNumber());

            company.setAddress(address);
            companyRepository.save(company);
            return new ApiResponse("Company editing!", true);
        }
        return new ApiResponse("Company not found!", false);
    }

    public ApiResponse deleteCompany(Integer id) {
        try {
            Optional<Company> optionalCompany = companyRepository.findById(id);

            Company company = optionalCompany.get();

            Address address = company.getAddress();

            companyRepository.deleteById(id);
            addressRepository.deleteById(address.getId());

            return new ApiResponse("Company deleted!", true);
        } catch (Exception e) {
            return new ApiResponse("Server error!!!", false);
        }
    }
}
