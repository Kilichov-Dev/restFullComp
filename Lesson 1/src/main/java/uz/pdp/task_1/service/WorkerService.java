package uz.pdp.task_1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.task_1.entity.Address;
import uz.pdp.task_1.entity.Department;
import uz.pdp.task_1.entity.Worker;
import uz.pdp.task_1.payload.ApiResponse;
import uz.pdp.task_1.payload.WorkerDto;
import uz.pdp.task_1.repository.AddressRepository;
import uz.pdp.task_1.repository.DepartmentRepository;
import uz.pdp.task_1.repository.WorkerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {
    @Autowired
    WorkerRepository workerRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    public List<Worker> getWorker() {
        List<Worker> all = workerRepository.findAll();
        return all;
    }

    public Worker getWorkerById(Integer id) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        return optionalWorker.orElse(null);
    }

    public ApiResponse addWorker(WorkerDto workerDto) {

        boolean number = workerRepository.existsByPhoneNumber(workerDto.getPhoneNumber());

        if (number) {
            return new ApiResponse("This phone number already exists!", false);
        }

        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartment());
        if (!optionalDepartment.isPresent()) {
            return new ApiResponse("Department id not found!", false);
        }

        if (addressRepository.existsByHomeNumberAndStreet(workerDto.getHomeNumber(), workerDto.getStreet())) {
            return new ApiResponse("This address already exists!", false);
        }

        Address address = new Address();
        address.setHomeNumber(workerDto.getHomeNumber());
        address.setStreet(workerDto.getStreet());
        Address save = addressRepository.save(address);

        Worker worker = new Worker();
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        worker.setName(workerDto.getName());
        worker.setAddress(save);
        worker.setDepartment(optionalDepartment.get());

        workerRepository.save(worker);
        return new ApiResponse("Worker added!", true);
    }

    public ApiResponse editWorker(Integer id, WorkerDto workerDto) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (optionalWorker.isPresent()) {
            Worker worker = optionalWorker.get();
            worker.setName(workerDto.getName());
            boolean number = workerRepository.existsByPhoneNumber(workerDto.getPhoneNumber());

            if (number) {
                return new ApiResponse("This phone number already exists!", false);
            }

            worker.setPhoneNumber(workerDto.getPhoneNumber());
            Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartment());
            if (!optionalDepartment.isPresent()) {
                return new ApiResponse("Department id not found!", false);
            }
            worker.setDepartment(optionalDepartment.get());
            Address address = worker.getAddress();
            if (addressRepository.existsByHomeNumberAndStreet(workerDto.getHomeNumber(), workerDto.getStreet())) {
                return new ApiResponse("This address already exists!", false);
            }
            address.setHomeNumber(workerDto.getHomeNumber());
            address.setStreet(workerDto.getStreet());


            worker.setAddress(address);
            workerRepository.save(worker);
            return new ApiResponse("Worker editing!", true);
        }
        return new ApiResponse("Worker not found!", false);
    }

    public ApiResponse deleteWorker(Integer id) {
        try {
            Optional<Worker> optionalWorker = workerRepository.findById(id);
            Worker worker = optionalWorker.get();
            Address address = worker.getAddress();
            workerRepository.deleteById(id);
            addressRepository.deleteById(address.getId());
            return new ApiResponse("Worker deleted!", true);
        } catch (Exception e) {
            return new ApiResponse("Worker not found!", false);
        }
    }
}
