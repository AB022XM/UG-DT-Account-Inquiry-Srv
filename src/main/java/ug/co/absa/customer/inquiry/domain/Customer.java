package ug.co.absa.customer.inquiry.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_type")
    private String customerType;

    @Column(name = "customer_status")
    private String customerStatus;

    @Column(name = "customer_status_code")
    private String customerStatusCode;

    @Column(name = "free_field_1")
    private String freeField1;

    @Column(name = "free_field_2")
    private String freeField2;

    @Column(name = "free_field_3")
    private String freeField3;

    @Column(name = "free_field_4")
    private String freeField4;

    @Column(name = "free_field_5")
    private String freeField5;

    @Column(name = "free_field_6")
    private String freeField6;

    @Column(name = "free_field_7")
    private String freeField7;

    @Column(name = "free_field_8")
    private String freeField8;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Customer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public Customer customerId(String customerId) {
        this.setCustomerId(customerId);
        return this;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public Customer customerName(String customerName) {
        this.setCustomerName(customerName);
        return this;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerType() {
        return this.customerType;
    }

    public Customer customerType(String customerType) {
        this.setCustomerType(customerType);
        return this;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerStatus() {
        return this.customerStatus;
    }

    public Customer customerStatus(String customerStatus) {
        this.setCustomerStatus(customerStatus);
        return this;
    }

    public void setCustomerStatus(String customerStatus) {
        this.customerStatus = customerStatus;
    }

    public String getCustomerStatusCode() {
        return this.customerStatusCode;
    }

    public Customer customerStatusCode(String customerStatusCode) {
        this.setCustomerStatusCode(customerStatusCode);
        return this;
    }

    public void setCustomerStatusCode(String customerStatusCode) {
        this.customerStatusCode = customerStatusCode;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public Customer freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public Customer freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public Customer freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public String getFreeField4() {
        return this.freeField4;
    }

    public Customer freeField4(String freeField4) {
        this.setFreeField4(freeField4);
        return this;
    }

    public void setFreeField4(String freeField4) {
        this.freeField4 = freeField4;
    }

    public String getFreeField5() {
        return this.freeField5;
    }

    public Customer freeField5(String freeField5) {
        this.setFreeField5(freeField5);
        return this;
    }

    public void setFreeField5(String freeField5) {
        this.freeField5 = freeField5;
    }

    public String getFreeField6() {
        return this.freeField6;
    }

    public Customer freeField6(String freeField6) {
        this.setFreeField6(freeField6);
        return this;
    }

    public void setFreeField6(String freeField6) {
        this.freeField6 = freeField6;
    }

    public String getFreeField7() {
        return this.freeField7;
    }

    public Customer freeField7(String freeField7) {
        this.setFreeField7(freeField7);
        return this;
    }

    public void setFreeField7(String freeField7) {
        this.freeField7 = freeField7;
    }

    public String getFreeField8() {
        return this.freeField8;
    }

    public Customer freeField8(String freeField8) {
        this.setFreeField8(freeField8);
        return this;
    }

    public void setFreeField8(String freeField8) {
        this.freeField8 = freeField8;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", customerId='" + getCustomerId() + "'" +
            ", customerName='" + getCustomerName() + "'" +
            ", customerType='" + getCustomerType() + "'" +
            ", customerStatus='" + getCustomerStatus() + "'" +
            ", customerStatusCode='" + getCustomerStatusCode() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", freeField4='" + getFreeField4() + "'" +
            ", freeField5='" + getFreeField5() + "'" +
            ", freeField6='" + getFreeField6() + "'" +
            ", freeField7='" + getFreeField7() + "'" +
            ", freeField8='" + getFreeField8() + "'" +
            "}";
    }
}
