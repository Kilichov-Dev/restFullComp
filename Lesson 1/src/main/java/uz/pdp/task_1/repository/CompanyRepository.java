package uz.pdp.task_1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.task_1.entity.Company;
import uz.pdp.task_1.payload.CompanyDto;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Integer> {
    boolean existsByCorpName(String corpName);

    void deleteAllByAddress_Id(Integer address_id);
}
