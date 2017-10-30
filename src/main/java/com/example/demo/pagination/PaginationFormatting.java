package com.example.demo.pagination;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.bean.Persons;
import com.example.demo.repository.PersonsRepository;
import com.example.demo.utils.SpringUtil;


interface Types {

    public Page<Persons> query();

    public Integer getCount();

    public Integer getPageNumber();

    public Long getTotal();

    public Object getContent();
}

class BasePaginationInfo {

    public Pageable pageable;

    public PersonsRepository instance = SpringUtil.getBean(PersonsRepository.class);

    public String sex, email;

    public BasePaginationInfo(String sexName, String emailName, Pageable pageable) {

        this.pageable = pageable;

        this.sex = sexName;

        this.email = emailName;
    }
}

class AllType extends BasePaginationInfo implements Types {


    public AllType(String sexName, String emailName, Pageable pageable) { //String sexName, String emailName,

        super(sexName, emailName, pageable);

    }

    public Page<Persons> query() {

        return this.instance.findAll(

                this.pageable

        );
    }

    public Integer getCount() {
        return this.query().getSize();
    }

    public Integer getPageNumber() {

        return this.query().getNumber();

    }

    public Long getTotal() {
        return this.query().getTotalElements();
    }

    public Object getContent() {
        return this.query().getContent();
    }
}

class SexEmailType extends BasePaginationInfo implements Types {

    public SexEmailType(String sexName, String emailName, Pageable pageable) {

        super(sexName, emailName, pageable);

    }

    public Page<Persons> query() {

        return this.instance.findBySexAndEmailContains(

                this.sex,

                this.email,

                this.pageable
        );
    }

    public Integer getCount() {
        return this.query().getSize();
    }

    public Integer getPageNumber() {

        return this.query().getNumber();

    }

    public Long getTotal() {
        return this.query().getTotalElements();
    }

    public Object getContent() {
        return this.query().getContent();
    }


}

class SexType extends BasePaginationInfo implements Types {

    public SexType(String sexName, String emailName, Pageable pageable) { //String sexName, String emailName,

        super(sexName, emailName, pageable);
    }

    public Page<Persons> query() {

        return this.instance.findBySex(

                this.sex,

                this.pageable
        );
    }

    public Integer getCount() {
        return this.query().getSize();
    }

    public Integer getPageNumber() {

        return this.query().getNumber();

    }

    public Long getTotal() {
        return this.query().getTotalElements();
    }

    public Object getContent() {
        return this.query().getContent();
    }
}


public class PaginationFormatting {

    private PaginationMultiTypeValuesHelper multiValue = new PaginationMultiTypeValuesHelper();

    private Map<String, PaginationMultiTypeValuesHelper> results = new HashMap<>();

    public Map<String, PaginationMultiTypeValuesHelper> filterQuery(String sex, String email, Pageable pageable) {

        Types typeInstance;

        if (sex.length() == 0 && email.length() == 0) {

            typeInstance = new AllType(sex, email, pageable);

        } else if (sex.length() > 0 && email.length() > 0) {

            typeInstance = new SexEmailType(sex, email, pageable);

        } else {
            typeInstance = new SexType(sex, email, pageable);
        }

        this.multiValue.setCount(typeInstance.getCount());

        this.multiValue.setPage(typeInstance.getPageNumber() + 1);

        this.multiValue.setResults(typeInstance.getContent());

        this.multiValue.setTotal(typeInstance.getTotal());

        this.results.put("data", this.multiValue);

        return results;
    }

}