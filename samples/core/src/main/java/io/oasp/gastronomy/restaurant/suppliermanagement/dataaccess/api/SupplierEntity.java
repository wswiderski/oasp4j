package io.oasp.gastronomy.restaurant.suppliermanagement.dataaccess.api;

import io.oasp.gastronomy.restaurant.general.dataaccess.api.ApplicationPersistenceEntity;
import io.oasp.gastronomy.restaurant.suppliermanagement.common.api.Supplier;

import javax.persistence.*;

@Entity
@Table(name = "Supplier")
public class SupplierEntity extends ApplicationPersistenceEntity implements Supplier {

  private static final long serialVersionUID = 1L;

  private String name;

  private String description;

  private int rate;

  public SupplierEntity() {
    super();
  }

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getRate() {
    return rate;
  }

  public void setRate(int rate) {
    this.rate = rate;
  }
}
