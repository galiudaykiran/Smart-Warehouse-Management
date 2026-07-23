
package com.smart_warehouse_management.Product_And_Inventory.dto;

import lombok.Data;

@Data
public class SupplierResponseDTO {

    private Long supplierId;
    private String companyName;
    private String contactPerson;
    private String mobile;
    private String email;
    private String city;
    private String gstNumber;
    private Boolean active;
	
}