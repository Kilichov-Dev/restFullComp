package uz.pdp.task_1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.task_1.entity.Company;
import uz.pdp.task_1.entity.Department;
import uz.pdp.task_1.payload.ApiResponse;
import uz.pdp.task_1.payload.DepartmentDto;
import uz.pdp.task_1.repository.CompanyRepository;
import uz.pdp.task_1.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;
@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    CompanyRepository companyRepository;

    public List<Department> getDepartment() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Integer id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        return optionalDepartment.orElse(null);
    }

    public ApiResponse addDepartment(DepartmentDto departmentDto) {
        Department department = new Department();
        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent()) {
            return new ApiResponse("Company not found!", false);
        }
        if (departmentRepository.existsByNameAndCompanyId(departmentDto.getName(), departmentDto.getCompanyId())) {
            return new ApiResponse("This company such department already exists!", false);
        }
        department.setCompany(optionalCompany.get());
        department.setName(departmentDto.getName());
        departmentRepository.save(department);
        return new ApiResponse("Department added!", true);

    }

    public ApiResponse editDepartment(Integer id, DepartmentDto departmentDto) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (optionalDepartment.isPresent()) {
            Department department = optionalDepartment.get();
            Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
            if (!optionalCompany.isPresent()) {
                return new ApiResponse("Company not found!", false);
            }
            if (departmentRepository.existsByNameAndCompanyId(departmentDto.getName(), departmentDto.getCompanyId())) {
                return new ApiResponse("This company such department already exists!", false);
            }
            department.setCompany(optionalCompany.get());
            department.setName(departmentDto.getName());
            departmentRepository.save(department);
            return new ApiResponse("Department editing!", true);
        }
        return new ApiResponse("Department not found!", false);
    }

    public ApiResponse deleteDepartment(Integer id) {
        try {
            departmentRepository.deleteById(id);
            return new ApiResponse("Department deleted!", true);
        } catch (Exception e) {
            return new ApiResponse("Department not found!", false);
        }


    }
}
